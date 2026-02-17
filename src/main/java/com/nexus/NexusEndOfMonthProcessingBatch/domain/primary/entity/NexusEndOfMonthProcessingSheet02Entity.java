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
 * 分類：月末処理シート
 */
@Getter
@Setter
public class NexusEndOfMonthProcessingSheet02Entity extends ToCreateInvoicesInterfacePayroll {
    //ID
    Integer id;
    //社員ID
    Integer employeeId;
    //Freee会計ID
    String freeeId;
    //所属会社略称
    String companyShortName;
    //名字
    String familyName;
    //名前
    String firstName;
    //メモ
    String memo;
    //作業日付
    LocalDateTime workingDate;
    //成果給
    Integer payForResults;
    //通勤手当
    Integer commutingAllowance;
    //資格手当
    Integer qualificationAllowance;
    //経費
    Integer expenses;
    //経費(客先請求)
    Integer customerBillingExpenses;
    //休み
    Double holiday;
    //時間外h
    Double overtimeHour;
    //休日出勤h
    Double holidayWorkHour;
    //夜勤h
    Double nightShift;
    //稼動時間
    Double workTime;
    //HGB金額
    Integer hgbMoney;
    //HGB会社
    String hgbCompany;
    //数量
    String quantity;
    //単価
    Integer unitPrice;
    //サイト
    String site;
    //締日
    String closingDate;
    //土日被り(前日/翌日）
    String delayedPaymentDate;
    //超過金額
    Integer excess;
    //控除金額
    Integer deduction;
    //下限時間
    Integer standardLowerLimit;
    //上限時間
    Integer standardUpperLimit;
    //上流会社
    String upstreamCompanyName;
    //契約元会社
    Integer contractorCompanyId;
    //EG所属会社名
    String egCompanyName;
    //月末処理作成会社名
    String monthEndProcessingCreationCompanyName;
    //作成者氏名
    Integer documentCreatorEmployeeId;
    //折半　所属
    Integer halfAffiliationCompanyId;
    //折半　氏名
    Integer halfEmployeeId;
    //窓口営業氏名
    Integer counterSalesEmployeeId;
    //有休付与日数
    Integer numberOfPaidDaysGranted;
    //有休残日数
    Double numberOfRemainingPaidHolidays;
    //有休使用数
    Double numberOfPaidUses;
    //給与計算用
    Boolean payrollFlag;
    //レコードロックフラグ
    Boolean recordLockFlag;
    //請求書ファイルID
    Integer invoiceFileId;
    //請求書ファイルの項目名差し替え(作業代)
    String changeFieldName0;
    //請求書ファイルの項目名差し替え(立替経費)
    String changeFieldName1;
    //請求書ファイルの項目非表示(立替経費)
    boolean hiddenColumnName1;
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
        return employeeId == null && StringUtils.isBlank(familyName) && StringUtils.isBlank(firstName);
    }

    @Override
    public String getEmployeeName() {
        return familyName + " " + firstName;
    }

    @Override
    public Integer getBusinessTripQuantity() {
        return customerBillingExpenses;
    }

    @Override
    public String getHalfAffiliation() {
        return "";
    }

    @Override
    public Boolean getEachMark() {
        return true;
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
        if(StringUtils.isBlank(this.quantity)) return 0;
        Fraction quantityFraction = Fraction.getFraction(this.quantity);
        if(quantityFraction!=null) {
            return Fraction.getFraction(quantityFraction.getNumerator() * this.getUnitPrice(), quantityFraction.getDenominator()).intValue();
        } else if(MyStringUtility.isNumeric(this.quantity)) {
            double quantityNumber = MyStringUtility.parseDoubleByString(this.quantity);
            return quantityNumber * this.getUnitPrice();
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