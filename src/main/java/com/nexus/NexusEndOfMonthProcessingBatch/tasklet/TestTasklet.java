package com.nexus.NexusEndOfMonthProcessingBatch.tasklet;

import com.nexus.NexusEndOfMonthProcessingBatch.logger.CustomLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestTasklet implements Tasklet {

    @Autowired
    CustomLogger customLogger;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        customLogger.print("テストタスク");
        return RepeatStatus.FINISHED;
    }
}