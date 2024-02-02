package io.spring.bulk.batch.daily_bank_settlement_job;

import io.spring.bulk.dto.DailyTransactionDto;
import io.spring.bulk.repository.DailyTransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class DailyBankSettlementReader implements ItemReader<List<DailyTransactionDto>> {

    private final DailyTransactionRepository dailyTransactionRepository;

    @Value("${batch.dailyBankSettlementJob.pageSize}")
    int pageSize;
    Long lastSeenKey = 0L;

    @Override
    public List<DailyTransactionDto> read() {
//        String transferDay = DateUtil.getYesterDay();
        String transferDay = "20240131";
        log.info("작업날짜 = " + transferDay);
        log.info("마지막 키 ID = " + lastSeenKey);


        List<DailyTransactionDto> dailyTransactionList = dailyTransactionRepository.getDailyTransactionWithNoOffset(transferDay, lastSeenKey, pageSize);
        log.info("조회한 데이터 크기 = " + dailyTransactionList.size());
        log.info("가장 첫 번째 데이터 = " + dailyTransactionList.get(0));

        updateLastSeenKey(dailyTransactionList);

        return dailyTransactionList;
    }

    private void updateLastSeenKey(List<DailyTransactionDto> dailyTransactionList) {
        if (!dailyTransactionList.isEmpty()) {
            // 다음 반복을 위해 lastSeenKey 업데이트
//            lastSeenKey = dailyTransactionList.get(dailyTransactionList.size() - 1).getId();
            lastSeenKey = dailyTransactionList.stream().mapToLong(DailyTransactionDto::getId).max().orElse(lastSeenKey);
        }
    }

}
