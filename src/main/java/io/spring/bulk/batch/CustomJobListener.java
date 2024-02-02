package io.spring.bulk.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

@Slf4j
public class CustomJobListener implements JobExecutionListener {

    long startTime = 0L;
    long endTime = 0L;
    long timeDiff = 0L;
    double transactionTime = 0;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("========== {} START ==========", jobExecution.getJobInstance().getJobName());
        startTime = System.currentTimeMillis();
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        endTime = System.currentTimeMillis();
        log.info("========== {} END ==========", jobExecution.getJobInstance().getJobName());

        timeDiff = (endTime - startTime);
        transactionTime = timeDiff / 1000.0;
        log.info("========== {} 소요 시간 = {} ==========", jobExecution.getJobInstance().getJobName(), transactionTime);
    }
}
