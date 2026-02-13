package com.nexus.NexusEndOfMonthProcessingBatch.service.custom;

import com.nexus.NexusEndOfMonthProcessingBatch.constant.InvoicePatternDefinitionConstant;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEmployeeEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEndOfProcessingEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.custom.WithdrawalRequestDetails;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper.NexusEmployeeMapper;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.secondary.entity.TksMasterCompanyEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.secondary.entity.TksMasterEmployeeEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.entity_collection.EompSheet1EntityCollection;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.invoice_pattern_definition.InvoicePatternDefinitionResponse;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.payroll_basic.PayrollDetailsBasic;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.seed.PayrollSeed;
import com.nexus.NexusEndOfMonthProcessingBatch.service.TksMasterCompanyService;
import com.nexus.NexusEndOfMonthProcessingBatch.service.TksMasterEmployeeService;
import com.nexus.NexusEndOfMonthProcessingBatch.file_system.response.InvoiceDataResponse;
import com.nexus.NexusEndOfMonthProcessingBatch.utility.InterestRateUtility;
import com.nexus.NexusEndOfMonthProcessingBatch.utility.MyStringUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WithdrawalRequestDetailsService {

    static final String MONEY_FORMAT = "¥%s-";

    @Autowired
    NexusEmployeeMapper nexusEmployeeMapper;

    @Autowired
    TksMasterEmployeeService tksMasterEmployeeService;

    @Autowired
    TksMasterCompanyService tksMasterCompanyService;

    public WithdrawalRequestDetails create(
            EompSheet1EntityCollection eompSheet1EntityCollection,
            InvoiceDataResponse firstMap,
            InvoiceDataResponse invoiceDataJasperReportMap) {
        if(eompSheet1EntityCollection == null) return null;
        NexusEndOfProcessingEntity nexusEndOfProcessingEntity = eompSheet1EntityCollection.getNexusEndOfProcessingEntity();
        PayrollDetailsBasic payrollDetailsBasic = eompSheet1EntityCollection.getPayrollDetailsBasic();
        PayrollSeed payrollDetailsSeed = eompSheet1EntityCollection.getPayrollDetailsSeed();
        if(nexusEndOfProcessingEntity==null || payrollDetailsBasic==null || payrollDetailsSeed==null) return null;

        TksMasterEmployeeEntity tksMasterEmployeeEntity = tksMasterEmployeeService.findById(nexusEndOfProcessingEntity.getEmployeeId());
        String employeeSimpleName;
        String employeeCompanyName = "";
        if(tksMasterEmployeeEntity!=null) {
            employeeSimpleName = tksMasterEmployeeEntity.getFamilyName() + " " + tksMasterEmployeeEntity.getFirstName();
            TksMasterCompanyEntity tksMasterCompanyEntity = tksMasterCompanyService.findById(tksMasterEmployeeEntity.getCompanyId());
            employeeCompanyName = tksMasterCompanyEntity.getCompanyName();
        } else {
            employeeSimpleName = nexusEndOfProcessingEntity.getEmployeeName();
        }

        //請求書パターン定義
        InvoicePatternDefinitionResponse invoicePatternDefinitionResponse = invoiceDataJasperReportMap.getInvoicePatternDefinitionResponse();

        NexusEmployeeEntity nexusEmployeeEntity = nexusEmployeeMapper.findById(nexusEndOfProcessingEntity.getDocumentCreatorEmployeeId());
        String creatorEmployeeName = "";
        if(nexusEmployeeEntity!=null) {
            creatorEmployeeName = nexusEmployeeEntity.extractFullName().split(" ")[0];
        }
        return WithdrawalRequestDetails.builder()
                .withdrawalAmount(String.format(MONEY_FORMAT, createSyukingaku(invoiceDataJasperReportMap, invoicePatternDefinitionResponse)))
                .note1(createMemo(employeeSimpleName, nexusEndOfProcessingEntity, invoiceDataJasperReportMap, invoicePatternDefinitionResponse))
                .invoiceAmount(String.format(MONEY_FORMAT, firstMap.get合計()))
                .appliedTo(invoiceDataJasperReportMap.get請求先会社())
                .expectedDepositDate(invoiceDataJasperReportMap.get振込期日())
                .createdBy(creatorEmployeeName)
                .other(employeeCompanyName)
                .build();
    }

    String createSyukingaku(InvoiceDataResponse invoiceDataJasperReportMap, InvoicePatternDefinitionResponse invoicePatternDefinitionResponse) {
        switch (invoicePatternDefinitionResponse.getMarginEnum()) {
            case 折半マージンを抜いた額:
            case 全額マージンを抜いた額:
            case スルーして全額そのまま:
                return invoiceDataJasperReportMap.get合計();
            case 全額マージン:
            case 折半マージン:
                double dSyukingaku = InterestRateUtility.roundDown((invoiceDataJasperReportMap.getTotal() * 1.1), 0);
                return MyStringUtility.translateToNumberCommaByDouble(dSyukingaku);
        }
        return "";
    }

    String createMemo(String employeeSimpleName, NexusEndOfProcessingEntity nexusEndOfProcessingEntity, InvoiceDataResponse invoiceDataJasperReportMap, InvoicePatternDefinitionResponse invoicePatternDefinitionResponse) {
        StringBuilder memo = new StringBuilder();
        StringBuilder target = new StringBuilder();
        StringBuilder kanriMargin = new StringBuilder();
        target.append("・").append(employeeSimpleName)
                .append("　：　").append(nexusEndOfProcessingEntity.getWorkingDate().getMonthValue()).append("月作業分");

        switch (invoicePatternDefinitionResponse.getMarginEnum()) {
            case 折半マージンを抜いた額:
            case 全額マージンを抜いた額:
            case 全額マージン:
            case 折半マージン:
                target.append("の管理手数料");
                switch (invoicePatternDefinitionResponse.getMarginEnum()) {
                    case 折半マージンを抜いた額:
                    case 折半マージン:
                        target.append("(折半)");
                        break;
                }
                break;
        }

        switch (invoicePatternDefinitionResponse.getMarginEnum()) {
            case 折半マージンを抜いた額:
            case 全額マージンを抜いた額:
                kanriMargin.append("・管理マージン　　　")
                        .append("¥")
                        .append(invoiceDataJasperReportMap.get項目_金額()[0])
                        .append("　　　を引いた額");
                if (invoicePatternDefinitionResponse.getMarginEnum() == InvoicePatternDefinitionConstant.MarginEnum.折半マージンを抜いた額) {
                    kanriMargin.append("(折半)");
                }
                break;
            case 全額マージン:
            case 折半マージン:
                kanriMargin.append("・管理マージン　　　")
                        .append("¥")
                        .append(invoiceDataJasperReportMap.get合計());
                break;
        }

        memo.append(target).append("\n");
        memo.append("・").append(invoiceDataJasperReportMap.get請求元会社()).append("に対する支払").append("\n");
        memo.append("・支払日　：　").append(invoiceDataJasperReportMap.get振込期日()).append("\n");
        memo.append(kanriMargin);
        return memo.toString();
    }

}