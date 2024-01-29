package io.spring.bulk.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "TB_MONTHLY_TRANSACTION")
@Entity
@Getter
@ToString
@NoArgsConstructor
public class MonthlyTransaction extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String transactionCount;
    private String senderBankCount;
    private String receiverBankCount;
    private String amount;
}
