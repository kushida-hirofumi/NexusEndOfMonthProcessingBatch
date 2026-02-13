package com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class NexusEmployeeEntity {

    //社員ID
    int employeeId;

    //所属会社ID
    int affiliatedCompanyId;

    //名字
    String familyName;

    //名前
    String firstName;

    //電話番号
    String phoneNumber;

    //PCメールアドレス
    String pcMailAddress;

    //携帯電話番号
    String mobilePhoneNumber;

    //携帯メールアドレス
    String mobileMailAddress;

    //部署ID
    int departmentId;

    //役職ID
    int positionId;

    //入社ステータスID
    int enteringStatusId;

    //入社日
    LocalDate enteringDate;

    //郵便番号
    String postalCode;

    //都道府県
    String prefectures;

    //市区町村
    String municipality;

    //番地
    String address;

    //建物名
    String buildingName;

    //住民票郵便番号
    String postalCode1;

    //住民票都道府県
    String prefectures1;

    //住民票市区町村
    String municipality1;

    //住民票番地
    String address1;

    //住民票建物名
    String buildingName1;

    //退職後郵便番号
    String postalCode2;

    //退職後都道府県
    String prefectures2;

    //退職後市区町村
    String municipality2;

    //退職後番地
    String address2;

    //退職後建物名
    String buildingName2;

    //扶養家族人数
    int numberOfDependents;

    //実家暮らし
    int livingAtHomeId;

    //国籍
    int countryOfCitizenshipId;

    //保険証記号番号
    String healthInsuranceCardCodeNumber;

    //総務メモ
    String generalAffairsMemo;

    //退職日
    LocalDate retirementDate;

    //退職理由
    int reasonForRetirementId;

    //退職詳細理由
    String detailsOfRetirementReason;

    //削除フラグ
    boolean deleteFlg;

    //登録ユーザーID
    Integer registeredUserId;

    //登録日時
    LocalDateTime registeredDate;

    //更新ユーザーID
    Integer updateUserId;

    //更新日時
    LocalDateTime updateDate;

    public String extractFullName() {
        return familyName + " " + firstName;
    }

}