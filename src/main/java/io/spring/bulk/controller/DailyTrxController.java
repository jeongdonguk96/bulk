package io.spring.bulk.controller;

import io.spring.bulk.dto.DailyTransactionDto;
import io.spring.bulk.repository.DailyTransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DailyTrxController {

    private final DailyTransactionRepository dailyTransactionRepository;

    @GetMapping("/trx")
    public List<DailyTransactionDto> getTrx(@RequestParam String receiverId,
                                            @RequestParam String transferDay,
                                            @RequestParam Long amount) {

        long startTime;
        long endTime;
        long timeDiff;
        double transactionTime;
        int pageSize = 10;
        int lastSeenAmount = 0;

        startTime = System.currentTimeMillis();
        log.info("========== TRX API START ==========");

        List<DailyTransactionDto> trxs = dailyTransactionRepository.getTrx(receiverId, transferDay, amount, pageSize, lastSeenAmount);
        log.info("========== TRXS SIZE = {} ==========", trxs.size());

        log.info("========== TRX API END ==========");
        endTime = System.currentTimeMillis();

        timeDiff = (endTime - startTime);
        transactionTime = timeDiff / 1000.0;
        log.info("========== TRX TIME = {}s ==========", transactionTime);

        return trxs;
    }

}
