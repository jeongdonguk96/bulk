package io.spring.bulk.batch.daily_bank_settlement_job;

import io.spring.bulk.dto.DailyTransactionDto;
import io.spring.bulk.repository.DailyTransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class DailyBankSettlementReader implements ItemReader<List<DailyTransactionDto>> {

    private final DailyTransactionRepository dailyTransactionRepository;

    String transferDay = "20240131";
    Long lastSeenKey = 0L;
    int offset = 0;
    int pageSize = 200000;

    // NoOffset 조회 쿼리
    @Override
    public List<DailyTransactionDto> read() {
        log.info("========== READER START ==========");
        log.info("lastSeenKey = " + lastSeenKey);
        long startTime = System.currentTimeMillis();

        List<DailyTransactionDto> dailyTransactionList = dailyTransactionRepository.getDailyTransactionWithNoOffset(transferDay, lastSeenKey, pageSize);
        log.info("dailyTransactionList.size() = " + dailyTransactionList.size());
        log.info("dailyTransaction = " + dailyTransactionList.get(0));

        if (!dailyTransactionList.isEmpty()) {
            // 다음 반복을 위해 lastSeenKey 업데이트
            lastSeenKey = dailyTransactionList.get(dailyTransactionList.size() - 1).getId();
        }

        long endTime = System.currentTimeMillis();
        long timeDiff = endTime - startTime;
        double trxTime = timeDiff / 1000.0;
        log.info("========== READER END ==========");
        log.info("========== TRXTIME TIME = {} ==========", trxTime);
        return dailyTransactionList;
    }

    // 기존 조회 쿼리
//    @Override
//    public List<DailyTransactionDto> read() {
//        log.info("========== READER START ==========");
//        log.info("offset = " + offset);
//        long startTime = System.currentTimeMillis();
//
//        List<DailyTransactionDto> dailyTransactionList = dailyTransactionRepository.getDailyTransactionWithOffset(transferDay, offset, pageSize);
//        log.info("dailyTransactionList.size() = " + dailyTransactionList.size());
//        log.info("dailyTransactionList.size() = " + dailyTransactionList.get(0));
//
//        if (!dailyTransactionList.isEmpty()) {
//            // 다음 반복을 위해 lastSeenKey 업데이트
//            offset = offset + pageSize;
//        }
//
//        long endTime = System.currentTimeMillis();
//        long timeDiff = endTime - startTime;
//        double trxTime = timeDiff / 1000.0;
//        log.info("========== READER END ==========");
//        log.info("========== TRXTIME TIME = {} ==========", trxTime);
//        return dailyTransactionList;
//    }
}
