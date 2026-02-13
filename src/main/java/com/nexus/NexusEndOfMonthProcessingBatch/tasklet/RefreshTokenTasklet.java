package com.nexus.NexusEndOfMonthProcessingBatch.tasklet;

import com.nexus.NexusEndOfMonthProcessingBatch.domain.primary.entity.NexusFreeeApiInfoEntity;
import com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.public_model.dto.FreeeApiPublicTokenDto;
import com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.public_model.request_body.FreeeApiPublicAccessTokenRequestBody;
import com.nexus.NexusEndOfMonthProcessingBatch.infrastructure.freee_api.rest_template.FreeeApiRestTemplate;
import com.nexus.NexusEndOfMonthProcessingBatch.logger.BatchLogger;
import com.nexus.NexusEndOfMonthProcessingBatch.logger.CustomLogger;
import com.nexus.NexusEndOfMonthProcessingBatch.service.NexusFreeeApiInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * FreeeApiのアクセストークンの更新を行う
 * FreeeApiのアクセストークンは発行してから6時間ほどで利用期限が失効するので更新を行う必要がある
 */
@Component
@RequiredArgsConstructor
public class RefreshTokenTasklet implements Tasklet {

    @Autowired
    CustomLogger customLogger;

    @Autowired
    NexusFreeeApiInfoService nexusFreeeApiInfoService;

    @Autowired
    FreeeApiRestTemplate freeeApiRestTemplate;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        List<NexusFreeeApiInfoEntity> nexusFreeeApiInfoEntities = nexusFreeeApiInfoService.get();
        BatchLogger batchLogger = new BatchLogger(customLogger, "FreeeApiのトークンリフレッシュ", nexusFreeeApiInfoEntities.size());

        for(NexusFreeeApiInfoEntity nexusFreeeApiInfoEntity : nexusFreeeApiInfoEntities) {

            FreeeApiPublicAccessTokenRequestBody params = FreeeApiPublicAccessTokenRequestBody.createRefreshToken(nexusFreeeApiInfoEntity.getClientId(), nexusFreeeApiInfoEntity.getClientSecret(), nexusFreeeApiInfoEntity.getRefreshToken());

            try {
                FreeeApiPublicTokenDto freeeApiPublicTokenDto = freeeApiRestTemplate.accessToken(params);
                if (freeeApiPublicTokenDto != null) {
                    nexusFreeeApiInfoEntity.setAccessToken(freeeApiPublicTokenDto.getAccessToken());
                    nexusFreeeApiInfoEntity.setRefreshToken(freeeApiPublicTokenDto.getRefreshToken());
                    nexusFreeeApiInfoEntity.setUpdateUserId(0);
                    if (nexusFreeeApiInfoService.updateAccessToken(nexusFreeeApiInfoEntity)) batchLogger.addSuccessCounter();
                }
            } catch (Exception e) {
                nexusFreeeApiInfoService.deleteToken(nexusFreeeApiInfoEntity);
                throw e;
            }
        }

        batchLogger.finish();
        return RepeatStatus.FINISHED;
    }

}