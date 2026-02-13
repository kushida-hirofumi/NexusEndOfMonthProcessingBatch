package com.nexus.NexusEndOfMonthProcessingBatch.file_system;

import com.nexus.NexusEndOfMonthProcessingBatch.constant.EndOfMonthProcessingIdLinkAttributeEnum;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.*;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.billing_amount_item.BillingAmountItemData;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.billing_amount_item.BillingAmountItemDataList;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.entity_collection.EompSheet2EntityCollection;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.custom.ExcessDeductionStatusEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.invoice_pattern_definition.InvoicePatternDefinitionResponse;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.PayrollDetailsSource;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.supplementary_information.EompEmployeeNameObject;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.supplementary_information.EompSupplementaryInfoData;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.to_create_invoices.ToCreateInvoicesInterfacePayroll;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.to_create_invoices.ToCreateInvoicesSubInterface;
import com.nexus.NexusEndOfMonthProcessingBatch.service.NexusCompanyService;
import com.nexus.NexusEndOfMonthProcessingBatch.service.NexusEndOfMonthProcessingIdLinkService;
import com.nexus.NexusEndOfMonthProcessingBatch.service.NexusEndOfProcessingService;
import com.nexus.NexusEndOfMonthProcessingBatch.service.NexusHolidayInfoService;
import com.nexus.NexusEndOfMonthProcessingBatch.file_system.response.InvoiceDataResponse;
import com.nexus.NexusEndOfMonthProcessingBatch.utility.MyDateUtility;
import com.nexus.NexusEndOfMonthProcessingBatch.utility.MyStringUtility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 請求書の生成サービス
 */
@Service
public class InvoiceDataComputationService {

    static final String DATE_FORMAT = "Gy年M月d日";

    public static final int 項目数 = 13;

    @Autowired
    NexusEndOfProcessingService nexusEndOfProcessingService;

    @Autowired
    NexusEndOfMonthProcessingIdLinkService nexusEndOfMonthProcessingIdLinkService;

    @Autowired
    NexusCompanyService nexusCompanyService;

    @Autowired
    NexusHolidayInfoService nexusHolidayInfoService;

    @Autowired
    PayrollDetailsSource payrollDetailsSource;

    public InvoiceDataResponse createMapByBody(ToCreateInvoicesInterfacePayroll entity,
                                               ToCreateInvoicesSubInterface[] subEntityList,
                                               InsuranceAndPensionTableEntity insuranceAndPensionTableEntity,
                                               EompSupplementaryInfoData eompSupplementaryInfoData,
                                               InvoicePatternDefinitionResponse invoicePatternDefinitionDetails,
                                               ExcessDeductionStatusEntity excessDeductionStatusEntity) {

        //パラメーター、データソースの設定
        InvoiceDataResponse result = new InvoiceDataResponse();
        result.setInvoicePatternDefinitionResponse(invoicePatternDefinitionDetails);
        result.setLd作成年月(entity.extractBillingDate());
        result.set作成年月(MyDateUtility.localDateToJapaneseFormatText(result.getLd作成年月(), DATE_FORMAT));

        if(invoicePatternDefinitionDetails!=null) {
            result.set請求先会社(invoicePatternDefinitionDetails.getBillingDestination());
        } else {
            result.set請求先会社(entity.createBillingCompanyName());
        }

        NexusCompanyEntity nexusCompanyEntity = null;
        //請求元会社の情報を入力
        if(invoicePatternDefinitionDetails!=null) {
            if(invoicePatternDefinitionDetails.getBillingSourceCompanyId()!=null) {
                nexusCompanyEntity = nexusCompanyService.findById(invoicePatternDefinitionDetails.getBillingSourceCompanyId());
            } else {
                List<NexusCompanyEntity> companyEntities = nexusCompanyService.findByCompanyName(invoicePatternDefinitionDetails.getBillingSource());
                if(!companyEntities.isEmpty()) nexusCompanyEntity = companyEntities.get(0);
            }
        } else if(entity.getContractorCompanyId()!=null) {
            nexusCompanyEntity = nexusCompanyService.findById(entity.getContractorCompanyId());
        }
        if(nexusCompanyEntity!=null) {
            addCompanyInfo(entity, eompSupplementaryInfoData, nexusCompanyEntity, result);
        } else if(invoicePatternDefinitionDetails!=null) {
            result.set請求元会社(invoicePatternDefinitionDetails.getBillingSource());
        }

        //請求金額設定
        if(invoicePatternDefinitionDetails!=null) {
            NexusEndOfMonthProcessingIdLinkEntity nexusEndOfMonthProcessingIdLinkEntity = nexusEndOfMonthProcessingIdLinkService.findByAttributeAndDestId(EndOfMonthProcessingIdLinkAttributeEnum.INVOICE_ID_LINK, entity.getId());
            if(nexusEndOfMonthProcessingIdLinkEntity!=null) {
                NexusEndOfProcessingEntity nexusEndOfProcessingEntity = nexusEndOfProcessingService.findById(nexusEndOfMonthProcessingIdLinkEntity.getSrcId());
                BillingAmountItemDataList billingAmountItemDataList = new BillingAmountItemDataList();
                switch (invoicePatternDefinitionDetails.getMarginEnum()) {
                    case 全額マージンを抜いた額:
                        billingAmountItemDataList = payrollDetailsSource.createZengakuMarginNuki(entity, nexusEndOfProcessingEntity, eompSupplementaryInfoData.getEompEmployeeNameObject(), insuranceAndPensionTableEntity);
                        for(BillingAmountItemData node : billingAmountItemDataList.getNodeList()) {
                            if(node.getCategory() == BillingAmountItemData.Category.作業代) {
                                result.set件名(node.getSubject());
                                result.set数量(node.getQuantity());
                                result.set単価(node.parseUnitPrice());
                                result.set金額(node.parseAmountOfMoney());
                            }
                            if(node.getCategory() == BillingAmountItemData.Category.全額マージン) {
                                result.putKoumoku(0, node.getSubject(), node.getQuantity(), node.parseUnitPrice(), node.parseAmountOfMoney());
                            }
                        }
                        break;
                    case 全額マージン:
                        billingAmountItemDataList = payrollDetailsSource.createZengakuMargin(entity, nexusEndOfProcessingEntity, eompSupplementaryInfoData.getEompEmployeeNameObject(), insuranceAndPensionTableEntity);
                        for(BillingAmountItemData node : billingAmountItemDataList.getNodeList()) {
                            if(node.getCategory() == BillingAmountItemData.Category.全額マージン) {
                                result.set件名(node.getSubject());
                                result.set数量(node.getQuantity());
                                result.set単価(node.parseUnitPrice());
                                result.set金額(node.parseAmountOfMoney());
                            }
                        }
                        break;
                    case 折半マージン:
                        billingAmountItemDataList = payrollDetailsSource.createSeppanMargin(entity, nexusEndOfProcessingEntity, eompSupplementaryInfoData.getEompEmployeeNameObject(), insuranceAndPensionTableEntity);
                        for(BillingAmountItemData node : billingAmountItemDataList.getNodeList()) {
                            if(node.getCategory() == BillingAmountItemData.Category.折半マージン) {
                                result.set件名(node.getSubject());
                                result.set数量(node.getQuantity());
                                result.set単価(node.parseUnitPrice());
                                result.set金額(node.parseAmountOfMoney());
                            }
                        }
                        break;
                    case 折半マージンを抜いた額:
                        billingAmountItemDataList = payrollDetailsSource.createSeppanMarginNuki(entity, nexusEndOfProcessingEntity, eompSupplementaryInfoData.getEompEmployeeNameObject(), insuranceAndPensionTableEntity);
                        for(BillingAmountItemData node : billingAmountItemDataList.getNodeList()) {
                            if(node.getCategory() == BillingAmountItemData.Category.作業代) {
                                result.set件名(node.getSubject());
                                result.set数量(node.getQuantity());
                                result.set単価(node.parseUnitPrice());
                                result.set金額(node.parseAmountOfMoney());
                            }
                            if(node.getCategory() == BillingAmountItemData.Category.折半マージン) {
                                result.putKoumoku(0, node.getSubject(), node.getQuantity(), node.parseUnitPrice(), node.parseAmountOfMoney());
                            }
                        }
                        break;
                    case スルーして全額そのまま:
                        billingAmountItemDataList = payrollDetailsSource.createThrewZengaku(entity, eompSupplementaryInfoData.getEompEmployeeNameObject());
                        for(BillingAmountItemData node : billingAmountItemDataList.getNodeList()) {
                            if(node.getCategory() == BillingAmountItemData.Category.作業代) {
                                result.set件名(node.getSubject());
                                result.set数量(node.getQuantity());
                                result.set単価(node.parseUnitPrice());
                                result.set金額(node.parseAmountOfMoney());
                            }
                        }
                        break;
                }
                billingAmountItemDataMarginFunc(billingAmountItemDataList, insuranceAndPensionTableEntity, result);
            }
        } else {
            billingAmountItemDataFunc(entity, subEntityList, insuranceAndPensionTableEntity, eompSupplementaryInfoData.getEompEmployeeNameObject(), excessDeductionStatusEntity, result);
        }

        result.setLd振込期日(nexusHolidayInfoService.paymentDueDate(entity));
        result.set振込期日(MyDateUtility.localDateToJapaneseFormatText(result.getLd振込期日(), DATE_FORMAT));

        String dateText = MyDateUtility.localDateToString(entity.extractBillingDate(), "yyyyMM");
        String employeeName = MyStringUtility.deleteSpace(eompSupplementaryInfoData.getEompEmployeeNameObject().getEmployeeSimpleName());
        //【請求書】エンジニアフルネーム_yyyymm_契約元会社名
        result.setFileName0("【請求書】" + employeeName + "_" + dateText + "_" + result.get請求元会社() + ".pdf");

        /**
         * 【請求書】日付_取引先名（株式会社▲▲▲）_ 金額（税抜き ）_所属会社●●●エンジニアフルネーム
         * 金額（税抜き）に反映する金額は、請求書の【小計（10%対象）の行の金額】
         */
        employeeName = MyStringUtility.deleteSpace(eompSupplementaryInfoData.getEompEmployeeNameObject().getJointName());
        String dateText2 = MyDateUtility.localDateToString(entity.extractBillingDate(), "yyyyMMdd");
        result.setFileName1("【請求書】" + dateText2 + "_" + result.get請求先会社() + "_" + result.getSubTotal() + "_" + employeeName + ".pdf");


        return result;
    }

    void addCompanyInfo(ToCreateInvoicesInterfacePayroll entity, EompSupplementaryInfoData eompSupplementaryInfoData, NexusCompanyEntity nexusCompanyEntity, InvoiceDataResponse invoiceDataResponse) {
        if(nexusCompanyEntity==null) return;
        invoiceDataResponse.set請求元会社(nexusCompanyEntity.getCompanyName());
        invoiceDataResponse.set会社情報_登録番号(nexusCompanyEntity.getEligibleBillLocationNumber());
        invoiceDataResponse.set会社情報_郵便番号(nexusCompanyEntity.getPostalCode());
        invoiceDataResponse.set会社情報_所在地(nexusCompanyEntity.getCompanyAddress1() + nexusCompanyEntity.getBuildingName());
        invoiceDataResponse.set書類作成者(eompSupplementaryInfoData.getCounterSalesEmployeeName());
        invoiceDataResponse.set会社情報_TEL(nexusCompanyEntity.getPhoneNumber());
        invoiceDataResponse.set会社情報_FAX(nexusCompanyEntity.getFax());
        invoiceDataResponse.set会社情報_振込先(nexusCompanyEntity.getBankAccountNumber());
        invoiceDataResponse.set会社情報_口座名(nexusCompanyEntity.getBankAccountName());
        if(entity.getEachMark()!=null && entity.getEachMark() && !StringUtils.isBlank(nexusCompanyEntity.getSquareStamp())) {
            invoiceDataResponse.set角印パス(nexusCompanyEntity.getSquareStamp());
        }
    }

    /**
     * 請求額項目
     * @param entity
     * @param subEntityList
     * @param eompEmployeeNameObject
     * @param invoiceDataResponse
     */
    void billingAmountItemDataFunc(ToCreateInvoicesInterfacePayroll entity, ToCreateInvoicesSubInterface[] subEntityList, InsuranceAndPensionTableEntity insuranceAndPensionTableEntity, EompEmployeeNameObject eompEmployeeNameObject, ExcessDeductionStatusEntity excessDeductionStatusEntity, InvoiceDataResponse invoiceDataResponse) {
        //課税請求金額項目
        BillingAmountItemDataList kazeiBillingAmountItemDataList = payrollDetailsSource.createKazei(entity, subEntityList, eompEmployeeNameObject, excessDeductionStatusEntity);
        int index = 0;
        for(BillingAmountItemData node : kazeiBillingAmountItemDataList.getNodeList()) {
            if(node.getCategory() == BillingAmountItemData.Category.作業代) {
                invoiceDataResponse.set件名(node.getSubject());
                invoiceDataResponse.set数量(node.getQuantity());
                invoiceDataResponse.set単価(node.parseUnitPrice());
                invoiceDataResponse.set金額(node.parseAmountOfMoney());
            } else if(node.getCategory() == BillingAmountItemData.Category.超過控除) {
                invoiceDataResponse.set超過控除_件名(node.getSubject());
                invoiceDataResponse.set超過控除_数量(node.getQuantity());
                invoiceDataResponse.set超過控除_単価(node.parseUnitPrice());
                invoiceDataResponse.set超過控除_金額(node.parseAmountOfMoney());
            } else {
                invoiceDataResponse.putKoumokuCheckNumbers(index, node.getSubject(), node.getQuantity(), node.parseUnitPrice(), node.parseAmountOfMoney());
                index++;
            }
        }

        //消費税
        int syohizei = (int) insuranceAndPensionTableEntity.consumptionTaxCalculation(kazeiBillingAmountItemDataList.getTotal());
        //出張・交通費
        int businessTripQuantity = entity.getBusinessTripQuantity() != null ? entity.getBusinessTripQuantity() : 0;
        //消費税パーセンテージ
        int consumptionTaxPercent = (int) insuranceAndPensionTableEntity.getConsumptionTax();
        //非課税請求金額項目リスト
        BillingAmountItemDataList hikazeiBillingAmountItemDataList = payrollDetailsSource.createHikazei(subEntityList);
        int hikazeiIndex = (項目数 - 1) - 3 - hikazeiBillingAmountItemDataList.getNodeList().size();
        invoiceDataResponse.putKoumoku(hikazeiIndex,
                "小計 ( " + consumptionTaxPercent + "%対象 )", "", "",
                MyStringUtility.translateToNumberCommaByDouble(kazeiBillingAmountItemDataList.getTotal()));
        hikazeiIndex++;
        invoiceDataResponse.putKoumoku(hikazeiIndex,
                "消費税 ( 税率" + consumptionTaxPercent + "% )", "", "",
                MyStringUtility.translateToNumberCommaByDouble(syohizei));
        hikazeiIndex++;
        invoiceDataResponse.putKoumoku(hikazeiIndex,
                "立替経費（ 出張交通費 ）", "", "",
                MyStringUtility.translateToNumberCommaByInteger(businessTripQuantity));
        hikazeiIndex++;
        for(BillingAmountItemData node : hikazeiBillingAmountItemDataList.getNodeList()) {
            invoiceDataResponse.putKoumokuCheckNumbers(hikazeiIndex, node.getSubject(), node.getQuantity(), node.parseUnitPrice(), node.parseAmountOfMoney());
            hikazeiIndex++;
        }

        //合計
        int total = (kazeiBillingAmountItemDataList.getTotal() + hikazeiBillingAmountItemDataList.getTotal() + syohizei + businessTripQuantity);
        String totalText = MyStringUtility.translateToNumberCommaByInteger(total);
        invoiceDataResponse.set合計(totalText);
        invoiceDataResponse.setTotal(total);
        invoiceDataResponse.setSubTotal(kazeiBillingAmountItemDataList.getTotal());
    }

    void billingAmountItemDataMarginFunc(BillingAmountItemDataList billingAmountItemDataList, InsuranceAndPensionTableEntity insuranceAndPensionTableEntity, InvoiceDataResponse invoiceDataResponse) {
        //消費税
        int syohizei = (int) insuranceAndPensionTableEntity.consumptionTaxCalculation(billingAmountItemDataList.getTotal());
        //消費税パーセンテージ
        int consumptionTaxPercent = (int) insuranceAndPensionTableEntity.getConsumptionTax();
        int hikazeiIndex = (項目数 - 1) - 3;
        invoiceDataResponse.putKoumoku(hikazeiIndex,
                "小計 ( " + consumptionTaxPercent + "%対象 )", "", "",
                MyStringUtility.translateToNumberCommaByDouble(billingAmountItemDataList.getTotal()));
        hikazeiIndex++;
        invoiceDataResponse.putKoumoku(hikazeiIndex,
                "消費税", "", "",
                MyStringUtility.translateToNumberCommaByDouble(syohizei));

        //合計
        int total = (billingAmountItemDataList.getTotal() + syohizei);
        String totalText = MyStringUtility.translateToNumberCommaByInteger(total);
        invoiceDataResponse.set合計(totalText);
        invoiceDataResponse.setTotal(total);
        invoiceDataResponse.setSubTotal(billingAmountItemDataList.getTotal());
    }

    public int total(EompSheet2EntityCollection eompSheet2EntityCollection) {
        if(eompSheet2EntityCollection==null) return 0;

        NexusEndOfMonthProcessingSheet02Entity entity = eompSheet2EntityCollection.getNexusEndOfMonthProcessingSheet02Entity();
        List<NexusEndOfMonthProcessingSheet02SubIrregularEntity> subEntityList = eompSheet2EntityCollection.getIrregularEntities();
        InsuranceAndPensionTableEntity insuranceAndPensionTableEntity = eompSheet2EntityCollection.getInsuranceAndPensionTableEntity();

        ToCreateInvoicesSubInterface[] toCreateInvoicesSubInterfaces = subEntityList.toArray(new NexusEndOfMonthProcessingSheet02SubIrregularEntity[0]);

        EompEmployeeNameObject eompEmployeeNameObject = eompSheet2EntityCollection.getEompEmployeeNameObject();

        //課税請求金額項目
        BillingAmountItemDataList kazeiBillingAmountItemDataList = payrollDetailsSource.createKazei(entity, toCreateInvoicesSubInterfaces, eompEmployeeNameObject, eompSheet2EntityCollection.getExcessDeductionStatusEntity());
        //非課税請求金額項目リスト
        BillingAmountItemDataList hikazeiBillingAmountItemDataList = payrollDetailsSource.createHikazei(toCreateInvoicesSubInterfaces);
        //消費税
        int syohizei = (int) insuranceAndPensionTableEntity.consumptionTaxCalculation(kazeiBillingAmountItemDataList.getTotal());
        //出張・交通費
        int businessTripQuantity = entity.getBusinessTripQuantity() != null ? entity.getBusinessTripQuantity() : 0;
        //合計
        return (kazeiBillingAmountItemDataList.getTotal() + hikazeiBillingAmountItemDataList.getTotal() + syohizei + businessTripQuantity);
    }
}