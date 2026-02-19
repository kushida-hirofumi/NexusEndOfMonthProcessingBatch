package com.nexus.NexusEndOfMonthProcessingBatch.freee_api.test_data;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusFreeeApiInfoEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * テストで利用するデータを生成するユーティリティ
 */
public class FreeeApiRestTemplateTestUtility {

    public static List<NexusFreeeApiInfoEntity> getNexusFreeeApiInfoEntity() {
        List<NexusFreeeApiInfoEntity> nexusFreeeApiInfoEntities = new ArrayList<>();
        NexusFreeeApiInfoEntity nexusFreeeApiInfoEntity = new NexusFreeeApiInfoEntity();
        nexusFreeeApiInfoEntity.setClientId("00001");
        nexusFreeeApiInfoEntity.setClientSecret("00001");
        nexusFreeeApiInfoEntity.setRefreshToken("Afgeag43t");
        nexusFreeeApiInfoEntity.setAccessToken("S34tujh3aa");
        nexusFreeeApiInfoEntity.setCompanyId(1);
        nexusFreeeApiInfoEntity.setCompanyName("TestCompany");
        nexusFreeeApiInfoEntities.add(nexusFreeeApiInfoEntity);
        return nexusFreeeApiInfoEntities;
    }

}