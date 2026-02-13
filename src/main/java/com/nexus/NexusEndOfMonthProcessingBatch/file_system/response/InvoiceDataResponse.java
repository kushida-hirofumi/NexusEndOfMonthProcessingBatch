package com.nexus.NexusEndOfMonthProcessingBatch.file_system.response;

import com.nexus.NexusEndOfMonthProcessingBatch.logic.invoice_pattern_definition.InvoicePatternDefinitionResponse;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

/**
 * 請求書の情報のマップ
 */
@Getter
@Setter
public class InvoiceDataResponse {

	String 作成年月="";
    LocalDate ld作成年月=null;
    String 請求先会社 ="";
    String 請求元会社 ="";
    String 会社情報_登録番号="";
	String 会社情報_郵便番号="";
    String 会社情報_所在地="";
	String 書類作成者="";
    String 会社情報_TEL="";
    String 会社情報_FAX="";
    String 件名="";
    String 数量="";
    String 単価="";
    String 金額="";
    String 振込期日="";
    LocalDate ld振込期日=null;
    String 会社情報_振込先="";
    String 会社情報_口座名="";
    String 角印パス="";
    String 合計="";
    String 超過控除_件名="";
    String 超過控除_数量="";
    String 超過控除_単価="";
    String 超過控除_金額="";
    String[] 項目_件名=new String[12];
    String[] 項目_数量=new String[12];
    String[] 項目_単価=new String[12];
    String[] 項目_金額=new String[12];
    int total;
    int subTotal;
    String fileName0;
    String fileName1;
    InvoicePatternDefinitionResponse invoicePatternDefinitionResponse;

    public void putKoumoku(int index, String kenmei, String suuryou, String tanka, String kingaku) {
        if(index < 0 || index > 12) return;
        項目_件名[index] = kenmei;
        項目_数量[index] = suuryou;
        項目_単価[index] = tanka;
        項目_金額[index] = kingaku;
    }

    /**
     * 値登録
     * 数字を確認して０であれば非表示にする
     * @param index 項目インデックス
     * @param kenmei    項目の件名
     * @param suuryou   項目の数量
     * @param tanka     項目の単価
     * @param kingaku   項目の金額
     */
    public void putKoumokuCheckNumbers(int index, String kenmei, String suuryou, String tanka, String kingaku) {
        if(index < 0 || index > 12) return;
        項目_件名[index] = kenmei;
        項目_数量[index] = outputNumber(suuryou);
        項目_単価[index] = outputNumber(tanka);
        項目_金額[index] = outputNumber(kingaku);
    }

    String outputNumber(String value) {
        return isZeroOrBlank(value) ? "" : value;
    }

    boolean isZeroOrBlank(String value) {
        return StringUtils.isBlank(value) || "0".equals(value);
    }
}