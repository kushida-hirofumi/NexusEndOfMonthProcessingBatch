package com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.InsuranceAndPensionTableEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.billing_amount_item.BillingAmountItemDataList;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.billing_amount_item.BillingAmountItemDataSource;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.custom.ExcessDeductionStatusEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.payroll_basic.PayrollDetailsBasic;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.payroll_basic.PayrollDetailsBasicInterface;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.payroll_basic.PayrollDetailsBasicSource;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.payroll_sub.PayrollDetailsSub;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.payroll_sub.PayrollDetailsSubSource;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.seed.PayrollSeed;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.seed.PayrollSeedInterface;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.seed.PayrollSeedSource;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.supplementary_information.EompEmployeeNameObject;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.to_create_invoices.ToCreateInvoicesInterfacePayroll;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.to_create_invoices.ToCreateInvoicesSubInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PayrollDetailsSource {

    @Autowired
    PayrollDetailsSubSource payrollDetailsSubSource;

    public PayrollDetailsBasic createPayrollDetailsBase(PayrollDetailsBasicInterface entity) {
        return PayrollDetailsBasicSource.create(entity);
    }

    public PayrollDetailsSub createPayrollDetailsSub0(PayrollInterface entity, PayrollDetailsBasic payrollDetailsBasic, InsuranceAndPensionTableEntity insuranceAndPensionTableEntity) {
        if(entity==null) return payrollDetailsSubSource.create(null, null, null);
        return payrollDetailsSubSource.create(entity, payrollDetailsBasic, insuranceAndPensionTableEntity);
    }

    public PayrollSeed createPayrollDetailsSeed(PayrollSeedInterface entity, PayrollDetailsSub payrollDetailsSub) {
        return PayrollSeedSource.create(entity, payrollDetailsSub);
    }

    public BillingAmountItemDataList createKazei(ToCreateInvoicesInterfacePayroll entity, ToCreateInvoicesSubInterface[] subEntityList, EompEmployeeNameObject eompEmployeeNameObject, ExcessDeductionStatusEntity excessDeductionStatusEntity) {
        /**
         * 利用している機能
         * 請求書PDF生成サービス
         */
        return BillingAmountItemDataSource.createKazei(entity, subEntityList, excessDeductionStatusEntity, eompEmployeeNameObject);
    }

    public BillingAmountItemDataList createHikazei(ToCreateInvoicesSubInterface[] subEntityList) {
        /**
         * 利用している機能
         * 請求書PDF生成サービス
         */
        return BillingAmountItemDataSource.createHikazei(subEntityList);

    }

    public BillingAmountItemDataList createThrewZengaku(ToCreateInvoicesInterfacePayroll entity, EompEmployeeNameObject eompEmployeeNameObject) {
        /**
         * 利用している機能
         * 請求書PDF生成サービス
         */
        return BillingAmountItemDataSource.createThrewZengaku(entity, eompEmployeeNameObject);
    }

    public BillingAmountItemDataList createZengakuMargin(ToCreateInvoicesInterfacePayroll entity, PayrollInterface nexusEndOfProcessingEntity, EompEmployeeNameObject eompEmployeeNameObject, InsuranceAndPensionTableEntity insuranceAndPensionTableEntity) {
        /**
         * 利用している機能
         * 請求書PDF生成サービス
         */
        PayrollSeed payrollDetailsSeed = createPayrollDetailsSeed(entity, createPayrollDetailsSub0(nexusEndOfProcessingEntity, createPayrollDetailsBase(nexusEndOfProcessingEntity), insuranceAndPensionTableEntity));
        return BillingAmountItemDataSource.createZengakuMargin(entity, payrollDetailsSeed, eompEmployeeNameObject);
    }

    public BillingAmountItemDataList createZengakuMarginNuki(ToCreateInvoicesInterfacePayroll entity, PayrollInterface nexusEndOfProcessingEntity, EompEmployeeNameObject eompEmployeeNameObject, InsuranceAndPensionTableEntity insuranceAndPensionTableEntity) {
        /**
         * 利用している機能
         * 請求書PDF生成サービス
         */
        PayrollSeed payrollDetailsSeed = createPayrollDetailsSeed(entity, createPayrollDetailsSub0(nexusEndOfProcessingEntity, createPayrollDetailsBase(nexusEndOfProcessingEntity), insuranceAndPensionTableEntity));
        return BillingAmountItemDataSource.createZengakuMarginNuki(entity, payrollDetailsSeed, eompEmployeeNameObject);
    }

    public BillingAmountItemDataList createSeppanMargin(ToCreateInvoicesInterfacePayroll entity, PayrollInterface nexusEndOfProcessingEntity, EompEmployeeNameObject eompEmployeeNameObject, InsuranceAndPensionTableEntity insuranceAndPensionTableEntity) {
        /**
         * 利用している機能
         * 請求書PDF生成サービス
         */
        PayrollSeed payrollDetailsSeed = createPayrollDetailsSeed(entity, createPayrollDetailsSub0(nexusEndOfProcessingEntity, createPayrollDetailsBase(nexusEndOfProcessingEntity), insuranceAndPensionTableEntity));
        return BillingAmountItemDataSource.createSeppanMargin(entity, payrollDetailsSeed, eompEmployeeNameObject);
    }

    public BillingAmountItemDataList createSeppanMarginNuki(ToCreateInvoicesInterfacePayroll entity, PayrollInterface nexusEndOfProcessingEntity, EompEmployeeNameObject eompEmployeeNameObject, InsuranceAndPensionTableEntity insuranceAndPensionTableEntity) {
        /**
         * 利用している機能
         * 請求書PDF生成サービス
         */
        PayrollSeed payrollDetailsSeed = createPayrollDetailsSeed(entity, createPayrollDetailsSub0(nexusEndOfProcessingEntity, createPayrollDetailsBase(nexusEndOfProcessingEntity), insuranceAndPensionTableEntity));
        return BillingAmountItemDataSource.createSeppanMarginNuki(entity, payrollDetailsSeed, eompEmployeeNameObject);
    }

}