package io.spring.bulk.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Table(name = "TB_DAILY_TRANSACTION")
@Entity
@Getter
@ToString
@NoArgsConstructor
public class DailyTransaction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    // id
    private String sender;              // 송신인
    private String senderBank;          // 송신인 은행
    private String senderAccount;       // 송신인 계좌
    private String receiver;            // 수신인
    private String receiverBank;        // 수신인 은행
    private String receiverAccount;     // 수신인 계좌
    private Long amount;                // 금액
    private String amountRange;         // 금액 범위

    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime transferTime; // 송금 시간
}
