package io.spring.bulk.repository;

import io.spring.bulk.dto.DailyTransactionDto;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DailyTransactionRepositoryCustom {

    List<DailyTransactionDto> getDailyTransactionWithNoOffset(@Param("transferDay") String transferDay,
                                                              @Param("lastSeenKey") Long lastSeenKey,
                                                              @Param("pageSize") int pageSize);

    List<DailyTransactionDto> getDailyTransactionWithOffset(@Param("transferDay") String transferDay,
                                                            @Param("offset") int offset,
                                                            @Param("pageSize") int pageSize);
}
