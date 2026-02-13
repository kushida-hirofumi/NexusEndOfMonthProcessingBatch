package com.nexus.NexusEndOfMonthProcessingBatch.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * 請求書のパターン定義処理用定数をまとめたクラス
 */
public class InvoicePatternDefinitionConstant {

    //ID24のパターンにするか判定するための値
    //この原価率を超える場合はID24になる
    public static final double ID24_CHECK_GENKARITU = 90;

    /**
     * マージンパターン
     */
    @AllArgsConstructor
    @Getter
    public enum MarginEnum {
        折半マージン("折半マージン"),
        全額マージン("全額マージン"),
        全額マージンを抜いた額("全額マージンを抜いた額"),
        折半マージンを抜いた額("折半マージンを抜いた額"),
        スルーして全額そのまま("スルーして全額そのまま");
        final String type;
    }

    /**
     * パターン定義の判定処理で利用する
     * 会社をカテゴライズする
     */
    @AllArgsConstructor
    @Getter
    public enum CompanyPatternEnum {
        A("A"), B("B"), C("C"), D("D"), Empty("空白");
        final String pattern;
        public static CompanyPatternEnum valueOfPattern(String value) {
            if(StringUtils.isBlank(value)) return null;
            for(CompanyPatternEnum node : CompanyPatternEnum.values()) {
                if(node.pattern.equals(value)) return node;
            }
            return null;
        }
        public CompanyPatternEnum next() {
            switch (this) {
                case A:
                    return B;
                case B:
                    return C;
                case C:
                    return D;
                default:
                    return A;
            }
        }
    }

    /**
     * 請求書が複数あるかどうかの有無
     * YESはあり
     * NOは無し
     */
    @AllArgsConstructor
    @Getter
    public enum PresenceOfInvoiceEnum {
        //請求書複数あり
        YES("あり"),
        //請求書複数なし
        NO("なし");
        final String value;
        public static PresenceOfInvoiceEnum valueOfName(String value) {
            if(StringUtils.isBlank(value)) return PresenceOfInvoiceEnum.NO;
            for(PresenceOfInvoiceEnum node : PresenceOfInvoiceEnum.values()) {
                if(value.equals(node.value)) return node;
            }
            return PresenceOfInvoiceEnum.NO;
        }
    }

    /**
     * 会社マージンパターン
     */
    @AllArgsConstructor
    @Getter
    public enum CompanyMarginPatternEnum {
        NULL("なし", null, null, null),
        TYPE_B_A_MAR_SEPPAN("B→A(折半マージン)", MarginEnum.折半マージン, CompanyPatternEnum.B, CompanyPatternEnum.A),
        TYPE_B_A_MAR_ZENGAKU("B→A(全額マージン)", MarginEnum.全額マージン, CompanyPatternEnum.B, CompanyPatternEnum.A),
        TYPE_B_A_ZENGAKU("B→A(全額マージンを抜いた額)", MarginEnum.全額マージンを抜いた額, CompanyPatternEnum.B, CompanyPatternEnum.A),
        TYPE_B_A_SEPPAN("B→A(折半マージンを抜いた額)", MarginEnum.折半マージンを抜いた額, CompanyPatternEnum.B, CompanyPatternEnum.A),
        TYPE_B_A_THREW("B→A(スルーして全額そのまま)", MarginEnum.スルーして全額そのまま, CompanyPatternEnum.B, CompanyPatternEnum.A),

        TYPE_C_A_MAR_SEPPAN("C→A(折半マージン)", MarginEnum.折半マージン, CompanyPatternEnum.C, CompanyPatternEnum.A),
        TYPE_C_B_MAR_SEPPAN("C→B(折半マージン)", MarginEnum.折半マージン, CompanyPatternEnum.C, CompanyPatternEnum.B),
        TYPE_C_B_MAR_ZENGAKU("C→B(全額マージン)", MarginEnum.全額マージン, CompanyPatternEnum.C, CompanyPatternEnum.B),

        TYPE_D_B_MAR_SEPPAN("D→B(折半マージン)", MarginEnum.折半マージン, CompanyPatternEnum.D, CompanyPatternEnum.B);

        final String type;
        final MarginEnum marginEnum;
        final CompanyPatternEnum source;
        final CompanyPatternEnum destination;
        public static CompanyMarginPatternEnum valueOfType(String value) {
            if(StringUtils.isBlank(value)) return null;
            return Arrays.stream(CompanyMarginPatternEnum.values())
                    .filter(node -> node.type.equals(value))
                    .findFirst().orElse(null);
        }
    }

}