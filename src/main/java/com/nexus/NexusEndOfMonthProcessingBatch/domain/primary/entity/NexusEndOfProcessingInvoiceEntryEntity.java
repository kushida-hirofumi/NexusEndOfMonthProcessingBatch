package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity;

import com.nexus.NexusEndOfMonthProcessingBatch.logic.to_create_invoices.ToCreateInvoicesInterfacePayroll;
import com.nexus.NexusEndOfMonthProcessingBatch.utility.MyStringUtility;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.Fraction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

/**
 * 月処理キット情報のエンティティ
 * 分類：請求Sシート
 */
@Getter
@Setter
public class NexusEndOfProcessingInvoiceEntryEntity extends ToCreateInvoicesInterfacePayroll {
    //ID
    Integer id;
    //社員ID
    Integer employeeId;
    //氏名
    String employeeName;
    //作業日付
    LocalDateTime workingDate;
    //稼働時間
    Double workTime;
    //出張(費用・経費)
    Integer businessTripQuantity;
    //数量
    String quantity;
    //HGB金額
    Integer hgbMoney;
    //HGB会社
    String hgbCompany;
    //単価
    Integer unitPrice;
    //サイト
    String site;
    //締め日
    String closingDate;
    //超過金額
    Integer excess;
    //控除金額
    Integer deduction;
    //下限時間
    Integer standardLowerLimit;
    //上限時間
    Integer standardUpperLimit;
    //土日被り(前日/翌日）
    String delayedPaymentDate;
    //上流会社
    String upstreamCompanyName;
    //契約元会社
    Integer contractorCompanyId;
    //EG所属会社名
    String egCompanyName;
    //月末処理作成会社名
    String monthEndProcessingCreationCompanyName;
    //折半　所属
    String halfAffiliation;
    //折半者の社員ID
    Integer halfEmployeeId;
    //担当者の社員ID
    Integer managerEmployeeId;
    //窓口営業の社員ID
    Integer counterSalesEmployeeId;
    //書類作成者
    Integer documentCreatorId;
    //月処理利用
    Boolean monthlyProcessingUsage;
    //角印
    Boolean eachMark;
    //PDFパス
    String pdfPath;
    //PDFファイル名
    String pdfFileName;
    //登録ユーザーID
    Integer registeredUserId;
    //登録日時
    LocalDateTime registeredDate;
    //更新ユーザーID
    Integer updateUserId;
    //更新日時
    LocalDateTime updateDate;

    /**
     * 社員情報が未入力であるか判定
     */
    public boolean isEmployeeInformationNotEntered() {
        return employeeId == null && StringUtils.isBlank(employeeName);
    }

    /**
     * 請求日抽出
     * @return  請求日
     */
    @Override
    public LocalDate extractBillingDate() {
        if(getWorkingDate()==null) return null;
        LocalDate ld = LocalDate.of(
                getWorkingDate().getYear(),
                getWorkingDate().getMonthValue(),
                MyStringUtility.isNumeric(getClosingDate()) ?
                        Integer.parseInt(getClosingDate()) : 1);
        if(!MyStringUtility.isNumeric(getClosingDate())) { //締日が「末日」の場合
            return ld.with(TemporalAdjusters.lastDayOfMonth());
        }
        return ld;
    }

    //合計金額
    @Override
    public double maximumPrice() {
        if(StringUtils.isBlank(this.quantity) || this.unitPrice == null) return 0;
        Fraction quantityFraction = Fraction.getFraction(this.quantity);
        if(quantityFraction!=null) {
            return Fraction.getFraction(quantityFraction.getNumerator() * this.unitPrice, quantityFraction.getDenominator()).intValue();
        }
        if(MyStringUtility.isNumeric(this.quantity)) {
            double quantityNumber = MyStringUtility.parseDoubleByString(this.quantity);
            return quantityNumber * this.unitPrice;
        }
        return 0;
    }

    //請求先会社名
    @Override
    public String createBillingCompanyName() {
        String result = this.upstreamCompanyName;
        if(!StringUtils.isBlank(result)) {
            if(result.contains("?http")) {
                result = result.substring(0, result.indexOf("?http"));
            }
        }
        return result;
    }
}