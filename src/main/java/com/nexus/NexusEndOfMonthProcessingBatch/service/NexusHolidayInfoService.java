package com.nexus.NexusEndOfMonthProcessingBatch.service;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusHolidayInfoEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper.NexusHolidayInfoMapper;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.to_create_invoices.ToCreateInvoicesInterfacePayroll;
import com.nexus.NexusEndOfMonthProcessingBatch.utility.MyStringUtility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;

/**
 * 休日情報テーブルのサービス
 */
@Service
public class NexusHolidayInfoService {

    @AllArgsConstructor
    @Getter
    public enum DelayedPaymentDate {
        Before("前日"),
        After("翌日");
        final String text;
        public static DelayedPaymentDate valueOfText(String value) {
            if(StringUtils.isBlank(value)) return null;
            for(DelayedPaymentDate node : DelayedPaymentDate.values()) {
                if(node.text.equals(value)) return node;
            }
            return null;
        }
    }

    @Autowired
    NexusHolidayInfoMapper nexusHolidayInfoMapper;

    public NexusHolidayInfoEntity findByHolidayMonthDay(LocalDate date) {
        return nexusHolidayInfoMapper.findByHolidayMonthDay(date);
    }

    /**
     * 休日ではない日にちを探す
     * @param ld                     判定対象
     * @param delayedPaymentDate     日にちずらし
     * @return      休日ではない日付
     */
    public LocalDate findDayThatIsNotHoliday(LocalDate ld, DelayedPaymentDate delayedPaymentDate) {
        if(ld==null || delayedPaymentDate==null) return null;
        while (judgeFlg(ld)) {
            switch (delayedPaymentDate) {
                case Before:
                    //支払日ずらしが前日であれば1日前にずらす
                    ld = ld.minusDays(1);
                    break;
                case After:
                    //支払日ずらしが後日であれば1日後にずらす
                    ld = ld.plusDays(1);
                    break;
            }
        }
        return ld;
    }

    /**
     * 土日・祝日・年末年始判定を行う
     * @param ld                     判定対象
     * @return      該当する場合true
     */
    boolean judgeFlg(LocalDate ld) {
        LocalDate firstDate = LocalDate.of(ld.getYear(), 1, 1).with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDate = LocalDate.of(ld.getYear(), 12, 1).with(TemporalAdjusters.lastDayOfMonth());
        return
                //祝日判定
                findByHolidayMonthDay(ld)!=null
                        //土日判定
                        || ld.getDayOfWeek() == DayOfWeek.SATURDAY
                        || ld.getDayOfWeek() == DayOfWeek.SUNDAY
                        //年末年始判定
                        || ld.getDayOfYear() == firstDate.getDayOfYear() || ld.getDayOfYear() == firstDate.getDayOfYear() + 1 || ld.getDayOfYear() == firstDate.getDayOfYear() + 2
                        || ld.getDayOfYear() == lastDate.getDayOfYear();
    }

    /**
     * 振込期日抽出
     */
    public LocalDate paymentDueDate(ToCreateInvoicesInterfacePayroll entity) {
        if(entity.getWorkingDate()==null || StringUtils.isBlank(entity.getSite()) || StringUtils.isBlank(entity.getClosingDate())) return null;
        int nSite = Integer.parseInt(entity.getSite());
        //締日が末日の場合は30
        int nClosingDate = entity.getClosingDate().equals("末日") ? 30 : MyStringUtility.parseIntByString(entity.getClosingDate());
        //振込期日　（締日　+　サイト）
        int paymentDueDate = ( nSite + nClosingDate );

        LocalDateTime workingDate = entity.getWorkingDate();
        //作業月
        int year = workingDate.getYear();
        int month = workingDate.getMonthValue();
        LocalDate ld = LocalDate.of(year,
                Month.of(month),
                1);

        //振込期日を足す
        ld = ld.plusMonths(paymentDueDate / 30);

        int amari = paymentDueDate % 30;
        if(amari == 0) {
            //余りが算出できない場合
            //月を１つ繰り下げる
            ld = ld.minusMonths(1);
            //日にちを末日とする
            ld = ld.with(TemporalAdjusters.lastDayOfMonth());
        } else {
            //余りを振込期日の日にちとする
            ld = LocalDate.of(
                    ld.getYear(),
                    ld.getMonthValue(),
                    amari
            );
        }

        String delayedPaymentDate = entity.getDelayedPaymentDate();
        if(!StringUtils.isBlank(delayedPaymentDate)) {
            ld = findDayThatIsNotHoliday(ld, DelayedPaymentDate.valueOfText(delayedPaymentDate));
        }
        return ld;
    }

}