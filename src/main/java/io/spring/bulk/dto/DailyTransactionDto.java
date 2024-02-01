package io.spring.bulk.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ToString
public class DailyTransactionDto {
    private Long id;                    // id
    private String sender;              // 송신인
    private String senderId;            // 송신인 번호
    private String senderBank;          // 송신인 은행
    private String senderBankId;        // 송신인 은행 번호
    private String senderAccount;       // 송신인 계좌
    private String receiver;            // 수신인
    private String receiverId;          // 수신인 번호
    private String receiverBank;        // 수신인 은행
    private String receiverBankId;      // 송신인 은행 번호
    private String receiverAccount;     // 수신인 계좌
    private Long amount;                // 금액
    private LocalDateTime transferTime; // 송금 시간
    private String transferDay;         // 송금일

    @QueryProjection
    public DailyTransactionDto(Long id, String sender, String senderId, String senderBank, String senderBankId, String senderAccount, String receiver, String receiverId, String receiverBank, String receiverBankId, String receiverAccount, Long amount, LocalDateTime transferTime, String transferDay) {
        this.id = id;
        this.sender = sender;
        this.senderId = senderId;
        this.senderBank = senderBank;
        this.senderBankId = senderBankId;
        this.senderAccount = senderAccount;
        this.receiver = receiver;
        this.receiverId = receiverId;
        this.receiverBank = receiverBank;
        this.receiverBankId = receiverBankId;
        this.receiverAccount = receiverAccount;
        this.amount = amount;
        this.transferTime = transferTime;
        this.transferDay = transferDay;
    }
}
