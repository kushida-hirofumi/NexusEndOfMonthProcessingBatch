package com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.payroll_sub;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.InsuranceAndPensionTableEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.payroll_basic.PayrollDetailsBasic;
import com.nexus.NexusEndOfMonthProcessingBatch.service.NexusEompDependentDeductionTaxAmountPatternService;
import com.nexus.NexusEndOfMonthProcessingBatch.utility.InterestRateUtility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PayrollDetailsSubSource {

    @Autowired
    NexusEompDependentDeductionTaxAmountPatternService nexusEompDependentDeductionTaxAmountPatternService;

    public PayrollDetailsSub create(PayrollDetailsSubInterface entity, PayrollDetailsBasic payrollDetailsBasic, InsuranceAndPensionTableEntity insuranceAndPensionTableEntity) {
        PayrollDetailsSub payrollDetailsSub = new PayrollDetailsSub();
        if(entity==null || payrollDetailsBasic==null || insuranceAndPensionTableEntity == null) return payrollDetailsSub;
        //小計
        payrollDetailsSub.syoukei = entity.getUnitPrice()!=null ? entity.getUnitPrice() : 0;
        //支給額
        payrollDetailsSub.shikyuugaku = (payrollDetailsBasic.getBasicSalary() + payrollDetailsBasic.getGyoumuteate() + payrollDetailsBasic.getSeikakyuu() + payrollDetailsBasic.getKoujo())
                + (payrollDetailsBasic.getJikangai() + payrollDetailsBasic.getKyuujitu() + payrollDetailsBasic.getShinyakinmu() + payrollDetailsBasic.getSyokumuteate());
        payrollDetailsSub.soushikyuugaku = payrollDetailsSub.shikyuugaku + payrollDetailsBasic.getKoutuu() + payrollDetailsBasic.getKeihi() + payrollDetailsBasic.getShikaku_housyoukin() + payrollDetailsBasic.getShikaku_jukenhi();
        //介護保険の有無
        payrollDetailsSub.kaigohoken_no_umu = !StringUtils.isBlank(entity.getPresenceOrAbsenceOfLongTermCareInsurance()) && entity.getPresenceOrAbsenceOfLongTermCareInsurance().equals("あり");
        //健康保険
        //if(途中退職=●,0,if(介護保険=●,round(標準月額*(健康保険料率+介護保険料率)/2-0.1),round(標準月額*(健康保険料率)/2-0.1)
        payrollDetailsSub.kenkou_hoken = 0;
        if(!payrollDetailsBasic.isTotyuu_taisyoku()) {
            if(payrollDetailsSub.kaigohoken_no_umu) {
                // round(標準月額*(健康保険料率+介護保険料率)/2-0.1)
                payrollDetailsSub.kenkou_hoken = InterestRateUtility.roundUp(
                        InterestRateUtility.percent( payrollDetailsBasic.getHyoujun_getugaku(), (insuranceAndPensionTableEntity.getPrefecturalInsurancePremiumRates() + insuranceAndPensionTableEntity.getLongTermCareInsurancePremiumRates()) ) / 2 - 0.1
                        , 0);
            } else {
                // round(標準月額*(健康保険料率)/2-0.1)
                payrollDetailsSub.kenkou_hoken = InterestRateUtility.roundUp(
                        InterestRateUtility.percent( payrollDetailsBasic.getHyoujun_getugaku(), insuranceAndPensionTableEntity.getPrefecturalInsurancePremiumRates() ) / 2 - 0.1
                        , 0);
            }
        }
        //厚生年金
        //if(途中退職=●,0,round(標準月額*厚生年金保険料率/2-0.1)
        payrollDetailsSub.kousei_nenkin = 0;
        if(!payrollDetailsBasic.isTotyuu_taisyoku()) {
            payrollDetailsSub.kousei_nenkin = (float) InterestRateUtility.roundUp(
                    InterestRateUtility.percent( payrollDetailsBasic.getHyoujun_getugaku(), insuranceAndPensionTableEntity.getEmployeePensionInsurancePremiumRates() ) / 2 - 0.1
                    , 0);
        }
        //健康保険2
        payrollDetailsSub.kenkou_hoken_2 = 0;
        //厚生年金2
        payrollDetailsSub.kousei_nenkin_2 = 0;
        //雇用保険　上
        //ROUNDDOWN((${総支給額}-(${経費(非課税分）} + ${資格受験費・経費(非課税分）}))*${雇用保険　上}/1000,0)
        payrollDetailsSub.koyou_hoken = InterestRateUtility.roundDown(
                ( (payrollDetailsSub.soushikyuugaku - (payrollDetailsBasic.getKeihi() + payrollDetailsBasic.getShikaku_jukenhi()))
                        * insuranceAndPensionTableEntity.getEmployeePaidEmploymentInsurancePremiumRates() ) / 1000
                , 0);
        //このフォーマットにはないが、()でくくって右の数字を表示→支給額と資格報奨金の合計額から、健康保険、厚生年金、健康保険2、厚生年金2、雇用保険の合計を引いた金額
        payrollDetailsSub.syotokuzei = nexusEompDependentDeductionTaxAmountPatternService.extract(
                payrollDetailsBasic.getHuyousyasuu(),
                (payrollDetailsSub.shikyuugaku + payrollDetailsBasic.getShikaku_housyoukin()) - (payrollDetailsSub.kenkou_hoken + payrollDetailsSub.kousei_nenkin + payrollDetailsSub.kenkou_hoken_2 + payrollDetailsSub.kousei_nenkin_2 + payrollDetailsSub.koyou_hoken)
        );
        //差引支給額
        //支給額-(健康保険+厚生年金+雇用保険+所得税+住民税)+交通費+経費+資格報奨金+資格受験費　K18-(K24+K25+K29+K30+K31)+N19+N20+N21+N22
        payrollDetailsSub.sashikihi_shikyuugaku = (payrollDetailsSub.shikyuugaku - (payrollDetailsSub.kenkou_hoken + payrollDetailsSub.kousei_nenkin + payrollDetailsSub.koyou_hoken + payrollDetailsSub.syotokuzei + payrollDetailsBasic.getJuuminzei())) + payrollDetailsBasic.getKoutuu() + payrollDetailsBasic.getKeihi() + payrollDetailsBasic.getShikaku_housyoukin() + payrollDetailsBasic.getShikaku_jukenhi();
        //支払給与額
        //差引支給額ー前借交通費の金額
        payrollDetailsSub.shiharai_kyuuyogaku = payrollDetailsSub.sashikihi_shikyuugaku - payrollDetailsBasic.getMaegari_koutuuhi();
        //雇用保険　下
        //ROUNDDOWN((${総支給額}-(${経費(非課税分）} + ${資格受験費・経費(非課税分）})) * ${事業主負担の雇用保険料率(%)}/1000,0)
        payrollDetailsSub.koyou_hoken_2 = InterestRateUtility.roundDown(
                ( (payrollDetailsSub.soushikyuugaku - (payrollDetailsBasic.getKeihi() + payrollDetailsBasic.getShikaku_jukenhi()))
                        * insuranceAndPensionTableEntity.getEmployerPaidEmploymentInsurancePremiumRates()) / 1000
                , 0);
        //労災保険
        //( ( ${総支給額} - ${経費(非課税分）} ) / 1000 ) * ${労働保険料率}
        payrollDetailsSub.rousai_hoken = InterestRateUtility.roundDown(
                ( (payrollDetailsSub.soushikyuugaku - payrollDetailsBasic.getKeihi()) / 1000 ) * insuranceAndPensionTableEntity.getLaborInsurancePremiumRates()
                ,0);
        //児童福祉手当て拠出金
        //標準月額*児童福祉手当拠出金料率
        payrollDetailsSub.jidou_hukushi_teate_kyosyutukin = InterestRateUtility.roundDown(
                ( (double) payrollDetailsBasic.getHyoujun_getugaku() / 1000 ) * insuranceAndPensionTableEntity.getChildWelfareAllowanceContributionRates()
                , 0);
        //会社総負担額
        //総支給額+健康保険+厚生年金+健康保険2+厚生年金2+雇用保険+労災保険+児童福祉手当て拠出金　K23＋K35～K42
        payrollDetailsSub.kaisya_sou_hutangaku = payrollDetailsSub.soushikyuugaku + payrollDetailsSub.kenkou_hoken + payrollDetailsSub.kousei_nenkin + payrollDetailsSub.kenkou_hoken_2 + payrollDetailsSub.kousei_nenkin_2 + payrollDetailsSub.koyou_hoken_2 + payrollDetailsSub.rousai_hoken + payrollDetailsSub.jidou_hukushi_teate_kyosyutukin;
        //福利厚生費
        //K35～K42の合算
        payrollDetailsSub.hukuri_kouseihi = payrollDetailsSub.kenkou_hoken + payrollDetailsSub.kousei_nenkin + payrollDetailsSub.koyou_hoken_2 + payrollDetailsSub.rousai_hoken + payrollDetailsSub.jidou_hukushi_teate_kyosyutukin;
        return payrollDetailsSub;
    }
}