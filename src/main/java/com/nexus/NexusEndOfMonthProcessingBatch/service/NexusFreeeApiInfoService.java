package com.nexus.NexusEndOfMonthProcessingBatch.service;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusFreeeApiInfoEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.mapper.NexusFreeeApiInfoMapper;
import com.nexus.NexusEndOfMonthProcessingBatch.exception.DataBaseErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * FreeeApiで利用する情報を管理するサービス
 */
@Service
public class NexusFreeeApiInfoService {

    @Autowired
    NexusFreeeApiInfoMapper nexusFreeeApiInfoMapper;

    /**
     * FreeeApi情報取得処理
     * @return  FreeeApi情報
     */
    public List<NexusFreeeApiInfoEntity> get() throws Exception {
        List<NexusFreeeApiInfoEntity> result = nexusFreeeApiInfoMapper.findAll();
        if(result.isEmpty()) {
            throw new DataBaseErrorException("FreeeApiの認証情報がDBに存在しないので終了");
        }
        for(NexusFreeeApiInfoEntity entity : result) {
            if(entity == null || StringUtils.isBlank(entity.getAccessToken()) || StringUtils.isBlank(entity.getRefreshToken())) {
                throw new DataBaseErrorException("一部レコードの情報が不足しています");
            }
        }
        return result;
    }

    public List<NexusFreeeApiInfoEntity> findAll() {
        return nexusFreeeApiInfoMapper.findAll();
    }

    public NexusFreeeApiInfoEntity findByCompanyName(String companyName) {
        return nexusFreeeApiInfoMapper.findByCompanyName(companyName);
    }

    /**
     * アクセストークン更新処理
     * @param entity    FreeeApi情報エンティティ
     * @return  更新成功・・true
     */
    public boolean updateAccessToken(NexusFreeeApiInfoEntity entity) {
        if(entity==null) return false;
        return nexusFreeeApiInfoMapper.updateAccessToken(entity) == 1;
    }

    /**
     * アクセストークン削除処理
     * @param entity    FreeeApi情報エンティティ
     */
    public void deleteToken(NexusFreeeApiInfoEntity entity) {
        nexusFreeeApiInfoMapper.delete(entity);
    }

}