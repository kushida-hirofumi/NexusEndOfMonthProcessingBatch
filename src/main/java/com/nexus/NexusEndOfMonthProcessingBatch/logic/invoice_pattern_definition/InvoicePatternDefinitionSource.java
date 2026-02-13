package com.nexus.NexusEndOfMonthProcessingBatch.logic.invoice_pattern_definition;

import com.nexus.NexusEndOfMonthProcessingBatch.constant.InvoicePatternDefinitionConstant;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusEompInvoicePatternDefinitionEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.payroll_invoice.PayrollDetailsInvoice;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.supplementary_information.EompSupplementaryInfoData;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.to_create_invoices.ToCreateInvoicesInterfacePayroll;
import com.nexus.NexusEndOfMonthProcessingBatch.service.NexusEompInvoicePatternDefinitionService;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 請求書の作成パターンの判定を行うコンポーネント
 * 判定に利用する情報はレコードに登録された以下の情報
 * ・契約元会社
 * ・EG所属会社
 * ・月末処理作成会社
 * ・折半所属
 */
@Component
public class InvoicePatternDefinitionSource {

    @Autowired
    NexusEompInvoicePatternDefinitionService nexusEompInvoicePatternDefinitionService;

    List<NexusEompInvoicePatternDefinitionEntity> nexusEompInvoicePatternDefinitionEntities;

    NexusEompInvoicePatternDefinitionEntity id24Pattern;

    @PostConstruct
    public void postConstruct() {
        nexusEompInvoicePatternDefinitionEntities = nexusEompInvoicePatternDefinitionService.findAll();
        id24Pattern = nexusEompInvoicePatternDefinitionEntities.stream().filter(node->node.getId()==24).findFirst().orElse(null);
    }

    /**
     * 月末処理データからパターン定義を出力する
     * @param   toCreateInvoicesInterfacePayroll  月末処理データ
     * @param   payrollDetailsInvoice   請求情報
     * @return  パターン定義
     */
    public List<InvoicePatternDefinitionResponse> getPatternDefinition(ToCreateInvoicesInterfacePayroll toCreateInvoicesInterfacePayroll, EompSupplementaryInfoData eompSupplementaryInfoData, PayrollDetailsInvoice payrollDetailsInvoice) {
        if(toCreateInvoicesInterfacePayroll==null || toCreateInvoicesInterfacePayroll.getContractorCompanyId()==null || StringUtils.isBlank(toCreateInvoicesInterfacePayroll.getEgCompanyName()) || StringUtils.isBlank(toCreateInvoicesInterfacePayroll.getMonthEndProcessingCreationCompanyName())) {
            return new ArrayList<>();
        }

        InvoicePatternDefinitionConstant.CompanyPatternEnum alpha = InvoicePatternDefinitionConstant.CompanyPatternEnum.A;
        InvoicePatternDefinitionConstant.CompanyPatternEnum companyPatternEnum;

        //契約元会社
        InvoicePatternDefinitionCompany contractorCompany = new InvoicePatternDefinitionCompany(toCreateInvoicesInterfacePayroll.getContractorCompanyId(), eompSupplementaryInfoData.getContractorCompanyName(), alpha);

        //EG所属会社名
        if(!eompSupplementaryInfoData.getContractorCompanyName().equals(toCreateInvoicesInterfacePayroll.getEgCompanyName())) {
            alpha = alpha.next();
        }
        InvoicePatternDefinitionCompany egCompany = new InvoicePatternDefinitionCompany(null, toCreateInvoicesInterfacePayroll.getEgCompanyName(), alpha);

        //月末処理作成会社名
        if (eompSupplementaryInfoData.getContractorCompanyName().equals(toCreateInvoicesInterfacePayroll.getMonthEndProcessingCreationCompanyName())) {
            companyPatternEnum = contractorCompany.getCompanyPatternEnum();
        } else if (toCreateInvoicesInterfacePayroll.getEgCompanyName().equals(toCreateInvoicesInterfacePayroll.getMonthEndProcessingCreationCompanyName())) {
            companyPatternEnum = egCompany.getCompanyPatternEnum();
        } else {
            alpha = alpha.next();
            companyPatternEnum = alpha;
        }
        InvoicePatternDefinitionCompany monthEndProcessingCreationCompany = new InvoicePatternDefinitionCompany(null, toCreateInvoicesInterfacePayroll.getMonthEndProcessingCreationCompanyName(), companyPatternEnum);

        //折半　所属
        if(StringUtils.isBlank(toCreateInvoicesInterfacePayroll.getHalfAffiliation())) {
            companyPatternEnum = InvoicePatternDefinitionConstant.CompanyPatternEnum.Empty;
        } else if (eompSupplementaryInfoData.getContractorCompanyName().equals(toCreateInvoicesInterfacePayroll.getHalfAffiliation())) {
            companyPatternEnum = contractorCompany.getCompanyPatternEnum();
        } else if (toCreateInvoicesInterfacePayroll.getEgCompanyName().equals(toCreateInvoicesInterfacePayroll.getHalfAffiliation())) {
            companyPatternEnum = egCompany.getCompanyPatternEnum();
        } else if (toCreateInvoicesInterfacePayroll.getMonthEndProcessingCreationCompanyName().equals(toCreateInvoicesInterfacePayroll.getHalfAffiliation())) {
            companyPatternEnum = monthEndProcessingCreationCompany.getCompanyPatternEnum();
        } else {
            alpha = alpha.next();
            companyPatternEnum = alpha;
        }
        InvoicePatternDefinitionCompany halfAffiliation = new InvoicePatternDefinitionCompany(null, toCreateInvoicesInterfacePayroll.getHalfAffiliation(), companyPatternEnum);

        //パターン定義の判定処理
        NexusEompInvoicePatternDefinitionEntity pattern = judge(
                payrollDetailsInvoice,
                contractorCompany, egCompany, monthEndProcessingCreationCompany, halfAffiliation
        );

        List<InvoicePatternDefinitionResponse> response = new ArrayList<>();
        if (pattern!=null) { // 該当するパターンが見つかった場合
            InvoicePatternDefinitionCompany[] companyInfos = {
                    contractorCompany,
                    egCompany,
                    monthEndProcessingCreationCompany,
                    halfAffiliation
            };
            List<InvoicePatternDefinitionConstant.CompanyMarginPatternEnum> companyMarginPatternEnumList = pattern.getInvoiceDetailsEnums();
            if(!companyMarginPatternEnumList.isEmpty()) {
                int index = 0;
                for(InvoicePatternDefinitionConstant.CompanyMarginPatternEnum companyMarginPatternEnum : companyMarginPatternEnumList) {
                    if(companyMarginPatternEnum.getMarginEnum()!=null) {
                        InvoicePatternDefinitionResponse node = create(companyMarginPatternEnum, companyInfos);
                        if(index==0 || index==1 || index==2) response.add(node);
                        index++;
                    }
                }
            }
        }
        return response;
    }

    NexusEompInvoicePatternDefinitionEntity judge(PayrollDetailsInvoice payrollDetailsInvoice, InvoicePatternDefinitionCompany contractorCompany, InvoicePatternDefinitionCompany egCompany, InvoicePatternDefinitionCompany monthEndProcessingCreationCompany, InvoicePatternDefinitionCompany halfAffiliation) {
        if(payrollDetailsInvoice!=null && payrollDetailsInvoice.getGenkaritu()>= InvoicePatternDefinitionConstant.ID24_CHECK_GENKARITU) {
            //原価率が９０%以上なら
            return id24Pattern;
        }
        for(NexusEompInvoicePatternDefinitionEntity pattern : nexusEompInvoicePatternDefinitionEntities) {
            if(pattern.getContractorCompanyFlg().equals(contractorCompany.getCompanyPatternEnum())
                    && pattern.getEgCompanyFlg().equals(egCompany.getCompanyPatternEnum())
                    && pattern.getMonthEndProcessingCreationCompanyFlg().equals(monthEndProcessingCreationCompany.getCompanyPatternEnum())
                    && pattern.getHalfAffiliationFlg().equals(halfAffiliation.getCompanyPatternEnum())) {
                if(pattern.getPresenceOfInvoiceEnum()==InvoicePatternDefinitionConstant.PresenceOfInvoiceEnum.YES) {
                    return pattern;
                }
                break;
            }
        }
        return null;
    }

    InvoicePatternDefinitionResponse create(InvoicePatternDefinitionConstant.CompanyMarginPatternEnum companyMarginPatternEnum, InvoicePatternDefinitionCompany[] companyInfos) {
        InvoicePatternDefinitionResponse node = new InvoicePatternDefinitionResponse();
        for(InvoicePatternDefinitionCompany companyInfo : companyInfos) {
            if(node.getBillingSource()==null && companyInfo.getCompanyPatternEnum().equals(companyMarginPatternEnum.getSource())) {
                node.setBillingSourceCompanyId(companyInfo.getCompanyId());
                node.setBillingSource(companyInfo.getCompanyName());
            }
            if(node.getBillingDestination()==null && companyInfo.getCompanyPatternEnum().equals(companyMarginPatternEnum.getDestination())) {
                node.setBillingDestination(companyInfo.getCompanyName());
            }
        }
        node.setMarginEnum(companyMarginPatternEnum.getMarginEnum());
        return node;
    }
}