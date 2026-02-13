package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NexusCompanyEntity {
  //会社ID
  int companyId;
  //所属グループ会社のID
  Integer groupCompanyId;
  //会社名
  String companyName;
  //郵便番号
  String postalCode;
  //会社住所１
  String companyAddress1;
  //会社住所２
  String companyAddress2;
  //会社建物名
  String buildingName;
  //会社電話番号
  String phoneNumber;
  //FAX
  String fax;
  //会社ホームページURL
  String homepageUrl;
  //略称
  String abbreviation;
  //角印
  String squareStamp;
  //銀行口座番号
  String bankAccountNumber;
  //銀行口座名義
  String bankAccountName;
  //適格請求書事業所番号
  String eligibleBillLocationNumber;
  //会社代表のID
  Integer companyCeoId;
  //会社権限
  int companyRole;
  //登録ユーザーID
  Integer registeredUserId;
  //登録日時
  LocalDateTime registeredDate;
  //更新ユーザーID
  Integer updateUserId;
  //更新日時
  LocalDateTime updateDate;
}