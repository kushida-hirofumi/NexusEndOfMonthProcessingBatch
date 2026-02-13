package com.nexus.NexusEndOfMonthProcessingBatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NexusEndOfMonthProcessingBatchApplication implements CommandLineRunner {

    private final JobLauncher jobLauncher;
    private final Job sampleJob;

    public NexusEndOfMonthProcessingBatchApplication(JobLauncher jobLauncher, Job sampleJob) {
        this.jobLauncher = jobLauncher;
        this.sampleJob = sampleJob;
    }

    public static void main(String[] args) {
        SpringApplication.run(NexusEndOfMonthProcessingBatchApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();

        jobLauncher.run(sampleJob, params);
    }
}
