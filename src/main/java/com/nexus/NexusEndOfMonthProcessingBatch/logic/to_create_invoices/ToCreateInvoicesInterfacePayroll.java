package com.nexus.NexusEndOfMonthProcessingBatch.logic.to_create_invoices;

import com.nexus.NexusEndOfMonthProcessingBatch.interface_code.EndOfMonthProcessingEntityPrimitive;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.billing_amount_item.BillingAmountItemDataInterface;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.seed.PayrollSeedInterface;

import java.time.LocalDate;

/**
 * 請求書の生成に利用するエンティティの基底クラス
 */
public abstract class ToCreateInvoicesInterfacePayroll implements EndOfMonthProcessingEntityPrimitive, PayrollSeedInterface, BillingAmountItemDataInterface {
    public abstract Integer getId();
    //社員ID
    public abstract Integer getEmployeeId();
    //社員名
    public abstract String getEmployeeName();
    //サイト
    public abstract String getSite();
    //締日
    public abstract String getClosingDate();
    //土日被り(前日/翌日）
    public abstract String getDelayedPaymentDate();
    //出張(費用・経費)
    public abstract Integer getBusinessTripQuantity();
    //契約元会社
    public abstract Integer getContractorCompanyId();
    //EG所属会社名
    public abstract String getEgCompanyName();
    //月末処理作成会社名
    public abstract String getMonthEndProcessingCreationCompanyName();
    //折半所属会社名
    public abstract String getHalfAffiliation();
    //角印
    public abstract Boolean getEachMark();
    //請求日の抽出
    public abstract LocalDate extractBillingDate();
    //請求先会社名の生成
    public abstract String createBillingCompanyName();
}