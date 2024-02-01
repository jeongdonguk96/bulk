package io.spring.bulk.repository;

import io.spring.bulk.entity.DailyTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyTransactionRepository extends JpaRepository<DailyTransaction, Long>, DailyTransactionRepositoryCustom {
}
