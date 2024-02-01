package io.spring.bulk.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.spring.bulk.dto.DailyTransactionDto;
import io.spring.bulk.dto.QDailyTransactionDto;
import jakarta.persistence.EntityManager;

import java.util.List;

import static io.spring.bulk.entity.QDailyTransaction.dailyTransaction;

public class DailyTransactionRepositoryImpl implements DailyTransactionRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public DailyTransactionRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<DailyTransactionDto> getDailyTransactionWithNoOffset(String transferDay, Long lastSeenKey, int pageSize) {
        return queryFactory
                .select(new QDailyTransactionDto(
                        dailyTransaction.id,
                        dailyTransaction.sender,
                        dailyTransaction.senderId,
                        dailyTransaction.senderBank,
                        dailyTransaction.senderBankId,
                        dailyTransaction.senderAccount,
                        dailyTransaction.receiver,
                        dailyTransaction.receiverId,
                        dailyTransaction.receiverBank,
                        dailyTransaction.receiverBankId,
                        dailyTransaction.receiverAccount,
                        dailyTransaction.amount,
                        dailyTransaction.transferTime,
                        dailyTransaction.transferDay
                ))
                .from(dailyTransaction)
                .where(dailyTransaction.id.gt(lastSeenKey))
                .where(dailyTransaction.transferDay.eq(transferDay).and(dailyTransaction.id.gt(lastSeenKey)))
                .orderBy(dailyTransaction.id.asc())
                .limit(pageSize)
                .fetch();
    }

    @Override
    public List<DailyTransactionDto> getDailyTransactionWithOffset(String transferDay, int offset, int pageSize) {
        return queryFactory
                .select(new QDailyTransactionDto(
                        dailyTransaction.id,
                        dailyTransaction.sender,
                        dailyTransaction.senderId,
                        dailyTransaction.senderBank,
                        dailyTransaction.senderBankId,
                        dailyTransaction.senderAccount,
                        dailyTransaction.receiver,
                        dailyTransaction.receiverId,
                        dailyTransaction.receiverBank,
                        dailyTransaction.receiverBankId,
                        dailyTransaction.receiverAccount,
                        dailyTransaction.amount,
                        dailyTransaction.transferTime,
                        dailyTransaction.transferDay
                ))
                .from(dailyTransaction)
                .where(dailyTransaction.transferDay.eq(transferDay))
                .orderBy(dailyTransaction.id.asc())
                .offset(offset)
                .limit(pageSize)
                .fetch();
    }
}
