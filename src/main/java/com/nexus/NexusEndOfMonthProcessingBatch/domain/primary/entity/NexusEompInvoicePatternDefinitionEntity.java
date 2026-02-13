package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity;

import com.nexus.NexusEndOfMonthProcessingBatch.constant.InvoicePatternDefinitionConstant;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 月末処理における請求書パターン定義の情報
 */
@Data
public class NexusEompInvoicePatternDefinitionEntity {
    int id;
    //契約元会社
    InvoicePatternDefinitionConstant.CompanyPatternEnum contractorCompanyFlg;
    //EG所属会社名
    InvoicePatternDefinitionConstant.CompanyPatternEnum egCompanyFlg;
    //月末処理作成会社名
    InvoicePatternDefinitionConstant.CompanyPatternEnum monthEndProcessingCreationCompanyFlg;
    //折半　所属
    InvoicePatternDefinitionConstant.CompanyPatternEnum halfAffiliationFlg;
    //請求書の有無
    InvoicePatternDefinitionConstant.PresenceOfInvoiceEnum presenceOfInvoiceEnum;
    //請求書2
    InvoicePatternDefinitionConstant.CompanyMarginPatternEnum invoiceDetail2;
    //請求書3
    InvoicePatternDefinitionConstant.CompanyMarginPatternEnum invoiceDetail3;
    //請求書4
    InvoicePatternDefinitionConstant.CompanyMarginPatternEnum invoiceDetail4;

    public List<InvoicePatternDefinitionConstant.CompanyMarginPatternEnum> getInvoiceDetailsEnums() {
        List<InvoicePatternDefinitionConstant.CompanyMarginPatternEnum> companyMarginPatternEnumList = new ArrayList<>();
        if(invoiceDetail2!=null) companyMarginPatternEnumList.add(invoiceDetail2);
        if(invoiceDetail3!=null) companyMarginPatternEnumList.add(invoiceDetail3);
        if(invoiceDetail4!=null) companyMarginPatternEnumList.add(invoiceDetail4);
        return companyMarginPatternEnumList;
    }
}