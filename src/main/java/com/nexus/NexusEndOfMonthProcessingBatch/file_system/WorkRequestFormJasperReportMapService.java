package com.nexus.NexusEndOfMonthProcessingBatch.file_system;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.custom.WithdrawalRequestDetails;
import com.nexus.NexusEndOfMonthProcessingBatch.logger.CustomLogger;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.entity_collection.EompSheet1EntityCollection;
import com.nexus.NexusEndOfMonthProcessingBatch.service.custom.WithdrawalRequestDetailsService;
import com.nexus.NexusEndOfMonthProcessingBatch.file_system.response.InvoiceDataResponse;
import com.nexus.NexusEndOfMonthProcessingBatch.file_system.response.WorkRequestFormResponse;
import com.nexus.NexusEndOfMonthProcessingBatch.utility.MyObjectUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 出金依頼書の生成用サービス
 */
@Service
public class WorkRequestFormJasperReportMapService {

    @Autowired
    WithdrawalRequestDetailsService withdrawalRequestDetailsService;

    @Autowired
    CustomLogger customLogger;

    public WorkRequestFormResponse createMap(
            EompSheet1EntityCollection eompSheet1EntityCollection,
            InvoiceDataResponse firstMap,
            InvoiceDataResponse invoiceDataJasperReportMap) {
        WithdrawalRequestDetails withdrawalRequestDetails = withdrawalRequestDetailsService.create(eompSheet1EntityCollection,
                firstMap,
                invoiceDataJasperReportMap);
        customLogger.error(MyObjectUtility.fieldsInHashMap(withdrawalRequestDetails).toString());
        WorkRequestFormResponse result = new WorkRequestFormResponse();
        result.set出金額(withdrawalRequestDetails.getWithdrawalAmount());
        result.set備考1(withdrawalRequestDetails.getNote1());
        result.set請求額(withdrawalRequestDetails.getInvoiceAmount());
        result.set相殺備考("");
        result.set適用先(withdrawalRequestDetails.getAppliedTo());
        result.set入金予定日(withdrawalRequestDetails.getExpectedDepositDate());
        result.set備考2("");
        result.set作成者(withdrawalRequestDetails.getCreatedBy());
        result.setその他(withdrawalRequestDetails.getOther());
        return result;
    }

}