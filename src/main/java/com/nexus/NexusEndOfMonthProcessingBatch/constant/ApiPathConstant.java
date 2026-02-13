package com.nexus.NexusEndOfMonthProcessingBatch.constant;

public class ApiPathConstant {

    //プルダウン検索API
    public static final String SEARCH_PULL_DOWN = "searchPullDown";

    public static final String INVOICE_DATA = "invoiceData";

    public static final String FREEE = "freee";

    public static final String HEALTH_CHECK = "healthCheck";

    public static class Freee {
        //FreeeApiアクセストークン取得
        public static final String ACCESS_TOKEN = "accessToken";

        //FreeeApiアクセストークン更新
        public static final String REFRESH_TOKEN = "refreshToken";

        //FreeeApiアクセストークン確認
        public static final String ACCESS_TOKEN_CHECK = "accessTokenCheck";

        //Freeeから情報取り込み
        public static final String IMPORT_INFO_FROM_FREEE_API = "importInfoFromFreeeApi";

        //FreeeApi確認
        public static final String FREEE_API_CHECK = "check";
    }
}