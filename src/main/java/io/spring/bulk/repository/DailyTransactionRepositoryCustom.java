package io.spring.bulk.repository;

import io.spring.bulk.dto.DailyTransactionDto;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DailyTransactionRepositoryCustom {

    List<DailyTransactionDto> getDailyTransactionWithNoOffset(@Param("transferDay") String transferDay,
                                                              @Param("lastSeenKey") Long lastSeenKey,
                                                              @Param("pageSize") int pageSize);

    List<DailyTransactionDto> getTrx(@Param("receiverId") String receiverId,
                                     @Param("transferDay") String transferDay,
                                     @Param("amount") Long amount,
                                     @Param("pageSize") int pageSize,
                                     @Param("lastSeenAmount") int lastSeenAmount);
}
