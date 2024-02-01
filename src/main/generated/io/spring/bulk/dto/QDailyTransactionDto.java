package io.spring.bulk.dto;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * io.spring.bulk.dto.QDailyTransactionDto is a Querydsl Projection type for DailyTransactionDto
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QDailyTransactionDto extends ConstructorExpression<DailyTransactionDto> {

    private static final long serialVersionUID = 1703615624L;

    public QDailyTransactionDto(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<String> sender, com.querydsl.core.types.Expression<String> senderId, com.querydsl.core.types.Expression<String> senderBank, com.querydsl.core.types.Expression<String> senderBankId, com.querydsl.core.types.Expression<String> senderAccount, com.querydsl.core.types.Expression<String> receiver, com.querydsl.core.types.Expression<String> receiverId, com.querydsl.core.types.Expression<String> receiverBank, com.querydsl.core.types.Expression<String> receiverBankId, com.querydsl.core.types.Expression<String> receiverAccount, com.querydsl.core.types.Expression<Long> amount, com.querydsl.core.types.Expression<java.time.LocalDateTime> transferTime, com.querydsl.core.types.Expression<String> transferDay) {
        super(DailyTransactionDto.class, new Class<?>[]{long.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, long.class, java.time.LocalDateTime.class, String.class}, id, sender, senderId, senderBank, senderBankId, senderAccount, receiver, receiverId, receiverBank, receiverBankId, receiverAccount, amount, transferTime, transferDay);
    }

}

