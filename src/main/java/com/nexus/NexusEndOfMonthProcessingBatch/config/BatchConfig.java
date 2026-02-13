package com.nexus.NexusEndOfMonthProcessingBatch.config;

import com.nexus.NexusEndOfMonthProcessingBatch.constant.BatchModeConstant;
import com.nexus.NexusEndOfMonthProcessingBatch.logger.CustomLogger;
import com.nexus.NexusEndOfMonthProcessingBatch.tasklet.ImportInformationFromFreeeTasklet;
import com.nexus.NexusEndOfMonthProcessingBatch.tasklet.RefreshTokenTasklet;
import com.nexus.NexusEndOfMonthProcessingBatch.tasklet.TestTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatContext;
import org.springframework.batch.repeat.exception.ExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

    //バッチモード
    @Value("${spring.batch.job.name}")
    String appMode;

    @Autowired
    CustomLogger customLogger;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    PlatformTransactionManager transactionManager;

    @Autowired
    TestTasklet testTasklet;

    @Autowired
    RefreshTokenTasklet refreshTokenTasklet;

    @Autowired
    ImportInformationFromFreeeTasklet importInformationFromFreeeTasklet;

    @Bean
    public Job job() {
        BatchModeConstant batchModeConstant = BatchModeConstant.valueOfKey(appMode);
        if(batchModeConstant!=null) {
            return switch (batchModeConstant) {
                case none -> createJob(batchModeConstant, testTasklet);
                case refreshToken -> createJob(batchModeConstant, refreshTokenTasklet);
                case importInfoFromFreeeApi -> createJob(batchModeConstant, importInformationFromFreeeTasklet);
            };
        }
        return createJob(BatchModeConstant.refreshToken, refreshTokenTasklet);
    }

    public Job createJob(BatchModeConstant batchModeConstant, Tasklet tasklet) {
        return new JobBuilder(batchModeConstant.getKey(), jobRepository)
                .start(createStep(tasklet))
                .build();
    }

    Step createStep(Tasklet tasklet) {
        return new StepBuilder(tasklet.getClass().getCanonicalName(), jobRepository)
                .tasklet(tasklet, transactionManager)
                .exceptionHandler(exceptionHandler())
                .build();
    }

    private ExceptionHandler exceptionHandler() {
        return new ExceptionHandler() {

            @Override
            public void handleException(RepeatContext context, Throwable throwable) throws Throwable {
                customLogger.print(throwable.getMessage());
                // 例外を投げず、終了する
                context.setTerminateOnly();
            }
        };
    }

}