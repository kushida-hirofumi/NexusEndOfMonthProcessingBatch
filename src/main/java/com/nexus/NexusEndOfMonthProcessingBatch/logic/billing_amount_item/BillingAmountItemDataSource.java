package com.nexus.NexusEndOfMonthProcessingBatch.logic.billing_amount_item;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.custom.ExcessDeductionStatusEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.seed.PayrollSeed;
import com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.supplementary_information.EompEmployeeNameObject;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.apache.commons.lang3.StringUtils;

/**
 * 請求金額項目情報の生成クラス
 */
public class BillingAmountItemDataSource {
    public static BillingAmountItemDataList createKazei(BillingAmountItemDataInterface entity, BillingAmountItemDataSubInterface[] subEntityList, ExcessDeductionStatusEntity excessDeductionStatusEntity, EompEmployeeNameObject eompEmployeeNameObject) {

        BillingAmountItemDataList result = new BillingAmountItemDataList();
        if (entity==null) return result;

        addSagyoudai(result, eompEmployeeNameObject, entity);

        if(excessDeductionStatusEntity !=null) {
            double dSuuryou = excessDeductionStatusEntity.getQuantity();
            int nTanka = excessDeductionStatusEntity.getTanka();
            add(result, BillingAmountItemData.Category.超過控除,
                    "調整代（ " + excessDeductionStatusEntity.getStatus() + "分 ）",
                    String.valueOf(dSuuryou),
                    nTanka,
                    Math.floor(dSuuryou * nTanka));
        }

        if(subEntityList != null) {
            for(BillingAmountItemDataSubInterface subEntity : subEntityList) {
                if(!subEntity.isTaxation()) continue;
                addIrregularInput(result, subEntity);
            }
        }

        return result;
    }

    public static BillingAmountItemDataList createHikazei(BillingAmountItemDataSubInterface[] subEntityList) {
        BillingAmountItemDataList result = new BillingAmountItemDataList();
        if(subEntityList==null) return result;
        for(BillingAmountItemDataSubInterface subEntity : subEntityList) {
            if(subEntity.isTaxation()) continue;
            addIrregularInput(result, subEntity);
        }
        return result;
    }

    public static BillingAmountItemDataList createThrewZengaku(
            BillingAmountItemDataInterface entity,
            EompEmployeeNameObject eompEmployeeNameObject) {
        BillingAmountItemDataList result = new BillingAmountItemDataList();
        if (entity==null) return result;
        addSagyoudai(result, eompEmployeeNameObject, entity);
        return result;
    }

    public static BillingAmountItemDataList createZengakuMargin(
            BillingAmountItemDataInterface entity,
            PayrollSeed payrollDetailsSeed,
            EompEmployeeNameObject eompEmployeeNameObject) {
        BillingAmountItemDataList result = new BillingAmountItemDataList();
        if (entity==null) return result;

        String quantity = "1";
        double tanka = payrollDetailsSeed.getKanri_tesuuryo();
        Expression expression = new ExpressionBuilder(quantity + " * " + tanka).build();
        double kingaku = expression.evaluate();

        add(result,
                BillingAmountItemData.Category.全額マージン,
                eompEmployeeNameObject.getEmployeeSimpleName() + "(" + entity.getWorkingDate().getMonthValue() + "月分作業代)管理手数料",
                quantity,
                (int) tanka,
                (int) kingaku);

        return result;
    }

    public static BillingAmountItemDataList createZengakuMarginNuki(
            BillingAmountItemDataInterface entity,
            PayrollSeed payrollDetailsSeed,
            EompEmployeeNameObject eompEmployeeNameObject) {
        BillingAmountItemDataList result = new BillingAmountItemDataList();
        if (entity==null) return result;

        addSagyoudai(result, eompEmployeeNameObject, entity);

        String quantity = "-1";
        //課税小計 - 会社総負担額
        double tanka = payrollDetailsSeed.getKanri_tesuuryo();
        Expression expression = new ExpressionBuilder(quantity + " * " + tanka).build();
        double kingaku = expression.evaluate();

        add(
                result,
                BillingAmountItemData.Category.全額マージン,
                eompEmployeeNameObject.getEmployeeSimpleName() + "(" + entity.getWorkingDate().getMonthValue() + "月分作業代)管理手数料",
                quantity,
                (int) tanka,
                (int) kingaku);

        return result;
    }

    public static BillingAmountItemDataList createSeppanMargin(
            BillingAmountItemDataInterface entity,
            PayrollSeed payrollDetailsSeed,
            EompEmployeeNameObject eompEmployeeNameObject) {
        BillingAmountItemDataList result = new BillingAmountItemDataList();
        if (entity==null) return result;

        String quantity = "1";
        double tanka = payrollDetailsSeed.getSeppan_margin();
        Expression expression = new ExpressionBuilder(quantity + " * " + tanka).build();
        double kingaku = expression.evaluate();

        add(
                result,
                BillingAmountItemData.Category.折半マージン,
                eompEmployeeNameObject.getEmployeeSimpleName() + "(" + entity.getWorkingDate().getMonthValue() + "月分作業代)管理手数料",
                quantity,
                (int) tanka,
                (int) kingaku);

        return result;
    }

    public static BillingAmountItemDataList createSeppanMarginNuki(
            BillingAmountItemDataInterface entity,
            PayrollSeed payrollDetailsSeed,
            EompEmployeeNameObject eompEmployeeNameObject) {
        BillingAmountItemDataList result = new BillingAmountItemDataList();
        if (entity==null) return result;

        addSagyoudai(result,eompEmployeeNameObject, entity);

        String quantity = "-1";
        //課税小計 - 会社総負担額
        double tanka = payrollDetailsSeed.getSeppan_margin();
        Expression expression = new ExpressionBuilder(quantity + " * " + tanka).build();
        double kingaku = expression.evaluate();

        add(result,BillingAmountItemData.Category.折半マージン,
                eompEmployeeNameObject.getEmployeeSimpleName() + "(" + entity.getWorkingDate().getMonthValue() + "月分作業代)管理手数料",
                quantity,
                (int) tanka,
                (int) kingaku);

        return result;
    }

    /**
     * 通常項目追加
     * @param billingAmountItemDataList 請求金額項目情報のリスト
     * @param category  請求金額項目の分類
     * @param subject   請求金額項目のタイトル
     * @param quantity  請求金額項目の数量
     * @param unitPrice 請求金額項目の単価
     * @param amountOfMoney 請求金額項目の金額
     */
    static void add(
            BillingAmountItemDataList billingAmountItemDataList,
            BillingAmountItemData.Category category,
            String subject, String quantity,
            int unitPrice, double amountOfMoney) {
        billingAmountItemDataList.nodeList.add(
                new BillingAmountItemData(
                        category,
                        subject,
                        quantity,
                        unitPrice,
                        amountOfMoney));
        billingAmountItemDataList.total += (int) amountOfMoney;
    }

    /**
     * 作業代項目追加
     * @param billingAmountItemDataList 請求金額項目情報のリスト
     * @param eompEmployeeNameObject    社員名
     * @param entity                処理に利用するエンティティ
     */
    static void addSagyoudai(
            BillingAmountItemDataList billingAmountItemDataList,
            EompEmployeeNameObject eompEmployeeNameObject,
            BillingAmountItemDataInterface entity) {
        if(entity==null) return;
        add(billingAmountItemDataList,
                BillingAmountItemData.Category.作業代,
                eompEmployeeNameObject.getEmployeeSimpleName() + " ( " + entity.getWorkingDate().getMonthValue() + "月分作業代 )",
                entity.getQuantity(),
                entity.getUnitPrice(),
                entity.maximumPrice());
    }

    /**
     * イレギュラー項目追加
     * @param billingAmountItemDataList 請求金額項目情報のリスト
     * @param subEntity                 処理に利用するエンティティ
     */
    static void addIrregularInput(
            BillingAmountItemDataList billingAmountItemDataList,
            BillingAmountItemDataSubInterface subEntity) {
        if(subEntity==null) return;
        double amountOfMoney = subEntity.amountOfMoney();
        billingAmountItemDataList.nodeList.add(
                new BillingAmountItemData(
                        BillingAmountItemData.Category.その他,
                        StringUtils.isBlank(subEntity.getSubject()) ? null : subEntity.getSubject(),
                        StringUtils.isBlank(subEntity.getQuantity()) ? null : subEntity.getQuantity(),
                        subEntity.getUnitPrice()==null ? 0: subEntity.getUnitPrice(),
                        amountOfMoney));
        billingAmountItemDataList.total += (int) amountOfMoney;
    }

}