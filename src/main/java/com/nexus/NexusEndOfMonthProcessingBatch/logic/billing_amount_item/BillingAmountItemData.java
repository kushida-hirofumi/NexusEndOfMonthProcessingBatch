package com.nexus.NexusEndOfMonthProcessingBatch.logic.billing_amount_item;

import com.nexus.NexusEndOfMonthProcessingBatch.utility.MyStringUtility;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *  請求金額項目情報の管理用
 */
@AllArgsConstructor
@Getter
public class BillingAmountItemData {
    public enum Category {
        作業代,
        超過控除,
        全額マージン,
        折半マージン,
        その他,
        HGB金額
    }
    //請求金額の分類
    final Category category;
    //件名
    final String subject;
    //数量
    final String quantity;
    //単価
    final int unitPrice;
    //金額
    final double amountOfMoney;

    public String parseUnitPrice() {
        return MyStringUtility.translateToNumberCommaByInteger(unitPrice);
    }

    public String parseAmountOfMoney() {
        return MyStringUtility.translateToNumberCommaByDouble(amountOfMoney);
    }
}