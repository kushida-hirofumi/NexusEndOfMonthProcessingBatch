package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity;

import com.nexus.NexusEndOfMonthProcessingBatch.logic.to_create_invoices.ToCreateInvoicesSubInterface;
import com.nexus.NexusEndOfMonthProcessingBatch.utility.MyNumberUtility;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

@Getter
@Setter
public class NexusEndOfProcessInvoiceEntrySubEntity extends ToCreateInvoicesSubInterface {
    //月末処理ID
    Integer parentId;
    //件名
    String subject;
    //数量
    String quantity;
    //単価
    Integer unitPrice;
    //課税
    boolean taxation;
    //登録ユーザーID
    Integer registeredUserId;
    //登録日時
    LocalDateTime registeredDate;
    //更新ユーザーID
    Integer updateUserId;
    //更新日時
    LocalDateTime updateDate;

    /**
     * 金額出力
     * @return  数量×単価
     */
    public double amountOfMoney() {
        if(StringUtils.isBlank(quantity) || unitPrice==null) return 0;
        return MyNumberUtility.calcStringByFormat("${quantity} * ${unitPrice}", this);
    }
}