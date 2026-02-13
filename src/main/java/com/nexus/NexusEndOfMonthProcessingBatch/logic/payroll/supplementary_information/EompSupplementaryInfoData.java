package com.nexus.NexusEndOfMonthProcessingBatch.logic.payroll.supplementary_information;

import lombok.Getter;
import lombok.Setter;

/**
 * 月末処理レコードの補足情報
 */
@Getter
@Setter
public class EompSupplementaryInfoData {
    int recordId;
    //氏名
    String employeeName;
    //氏名(苗字)
    String familyName;
    //氏名(名前)
    String firstName;
    //契約元会社
    String contractorCompanyName;
    //窓口営業氏名
    String counterSalesEmployeeName;
    EompEmployeeNameObject eompEmployeeNameObject = null;
}