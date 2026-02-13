### 概要

    このアプリはNexusアプリの情報の自動登録・更新を行うバッチアプリケーションとなります。
    Freeeと呼ばれるアプリから情報を取り込む処理が存在します

### 各アプリ毎のデータベース

    このアプリはNexusアプリで利用するデータベースの他にTKSと呼ばれるアプリのデータベースとも連携しています

| アプリ名 | 種類
| ---- | ----
| Nexus | MYSQL
| TKS | SqlServer


### 環境変数の一覧

| 変数名                     | 役割
| ------------------------- | -----------------------------------
| BATCH_MODE                | バッチで行う処理を指定する
| DB_HOST                   | Nexusデータベースのホスト名
| DB_NAME_NEXUS             | Nexusデータベースのデータベース名
| DB_USER                   | Nexusデータベースのユーザー名
| DB_PASSWORD               | Nexusデータベースのパスワード
| TKS_DB_URL                | TKSデータベースのURL
| TKS_DB_USERNAME           | TKSデータベースのユーザー名
| TKS_DB_PASSWORD           | TKSデータベースのパスワード
| TKS_DB_DRIVER_CLASS_NAME  | TKSデータベースに接続するためのドライバー


### バッチ処理の種類

| 物理名 | 機能
|-----| -----------------------------------
| importInfoFromFreeeApi | FreeeAPIから読み込んだデータをNexusデータベースに取り込む処理を行う
| refreshToken | FreeeAPIを利用するためのアクセストークンの更新処理を行う


## 外部連携

FreeeAPI参考
https://developer.freee.co.jp/reference/accounting/reference
