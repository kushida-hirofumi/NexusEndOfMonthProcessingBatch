package com.nexus.NexusEndOfMonthProcessingBatch.service;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.InsuranceAndPensionTableEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper.InsuranceAndPensionTableMapper;
import com.nexus.NexusEndOfMonthProcessingBatch.interface_code.EndOfMonthProcessingEntityPrimitive;
import com.nexus.NexusEndOfMonthProcessingBatch.utility.MyDateUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 保険年金情報のサービス
 */
@Service
public class InsuranceAndPensionTableService {

    @Autowired
    InsuranceAndPensionTableMapper insuranceAndPensionTableMapper;

    public List<InsuranceAndPensionTableEntity> findByEntityList(EndOfMonthProcessingEntityPrimitive[] endOfMonthProcessingEntityPrimitives) {
        if(endOfMonthProcessingEntityPrimitives==null || endOfMonthProcessingEntityPrimitives.length==0) return new ArrayList<>();
        List<Integer> yearList = new ArrayList<>();
        for(EndOfMonthProcessingEntityPrimitive endOfMonthProcessingEntityPrimitive : endOfMonthProcessingEntityPrimitives) {
            yearList.add(getYear(endOfMonthProcessingEntityPrimitive.getWorkingDate()));
        }
        return insuranceAndPensionTableMapper.findByYearList(yearList);
    }

    public InsuranceAndPensionTableEntity extract(List<InsuranceAndPensionTableEntity> entities, EndOfMonthProcessingEntityPrimitive endOfMonthProcessingEntityPrimitive) {
        if(entities==null || entities.isEmpty() || endOfMonthProcessingEntityPrimitive==null) return null;
        for(InsuranceAndPensionTableEntity entity : entities) {
            if(entity.getYear()==getYear(endOfMonthProcessingEntityPrimitive.getWorkingDate())) return entity;
        }
        return null;
    }

    // 年度を返す
    public int getYear(LocalDateTime ldt) {
        if(ldt==null) return 0;
        LocalDate ld = MyDateUtility.localDateTimeToLocalDate(ldt);
        Date day = MyDateUtility.localDateToDate(ld);

        int year;
        SimpleDateFormat sdf = new SimpleDateFormat("MM");

        // 月の取得
        String dateStr = sdf.format(day);

        // String→intへ型変換
        int month = Integer.parseInt(dateStr);

        // カレンダーオブジェクトを取得
        Calendar calendar = Calendar.getInstance();

        // 値をセット
        calendar.setTime(day);

        if (month < 4) {
            // 1～3月の場合

            // 西暦から-1する
            calendar.add(Calendar.YEAR, -1);

            // 年のみ取得
            year = calendar.get(Calendar.YEAR);

        }else{
            // 4～12月の場合

            // 年のみ取得
            year = calendar.get(Calendar.YEAR);

        }

        // 呼び出し元(mainメソッド)へ返す
        return year;

    }

}