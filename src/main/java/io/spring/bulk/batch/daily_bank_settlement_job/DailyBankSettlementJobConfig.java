package io.spring.bulk.batch.daily_bank_settlement_job;


import io.spring.bulk.batch.CustomJobListener;
import io.spring.bulk.dto.DailyTransactionDto;
import io.spring.bulk.repository.DailyTransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DailyBankSettlementJobConfig {

    private final PlatformTransactionManager transactionManager;
    private final JobRepository jobRepository;
    private final DailyTransactionRepository dailyTransactionRepository;

//    @Bean
    public Job dailyBankSettlementJob(Step dailyBankSettlementStep, JobExecutionListener customJobListener) {
        return new JobBuilder("dailyBankSettlementJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(dailyBankSettlementStep)
                .listener(customJobListener)
                .build();
    }

//    @Bean
    public Step dailyBankSettlementStep(ItemReader<List<DailyTransactionDto>> dailyBankSettlementReader,
                                        ItemWriter<Object> dailyBankSettlementWriter) {
        return new StepBuilder("dailyBankSettlementStep", jobRepository)
                .chunk(1, transactionManager)
                .reader(dailyBankSettlementReader)
                .writer(dailyBankSettlementWriter)
                .build();
    }

//    @Bean
    public ItemReader<List<DailyTransactionDto>> dailyBankSettlementReader() {
        return new DailyBankSettlementReader(dailyTransactionRepository);
    }

//    @Bean
    public ItemWriter<Object> dailyBankSettlementWriter() {
        return new ItemWriter<Object>() {
            @Override
            public void write(Chunk<?> chunk) {
            }
        };
    }

//    @Bean
    public JobExecutionListener customJobListener() {
        return new CustomJobListener();
    }

}
