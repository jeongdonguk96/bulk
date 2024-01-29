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
    private Long id;
    private String sender;
    private String senderBank;
    private String senderAccount;
    private String receiver;
    private String receiverBank;
    private String receiverAccount;
    private Long amount;
    private String amountRange;

    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime transferTime;
}
