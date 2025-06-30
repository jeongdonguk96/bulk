package io.spring.bulk.sql;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
public class SingleThreadBulkQuery {

    private final DataSource dataSource;
    private static final int DAY_RANGE = 10;
    private static final int DATA_SIZE = 100_000;
    private static final int COMMIT_SIZE = 10_000;

    @PostConstruct
    public void batchInsert() {
        long startTime = System.currentTimeMillis();
        log.info("배치 인서트 시작");

        String[] sender = {"김민수", "박정민", "김휘성", "노현진", "마구성", "리석도", "박현민", "김현주", "박미선", "최유미"};
        String[] senderId = {"2024001", "2024002", "2024003", "2024004", "2024005", "2024006", "2024007", "2024008", "2024009", "20240010"};
        String[] sendBank = {"ABank", "BBank", "CBank", "DBank", "EBank", "FBank", "GBank", "HBank", "IBank", "JBank"};
        String[] sendBankId = {"001", "002", "003", "004", "005", "006", "007", "008", "009", "010"};
        String[] receiver = {"리시수낙", "벨루아", "미스키", "코레스", "피코리", "로리앙", "필리앙", "샤킬", "크몽", "소레아"};
        String[] receiverId = {"2023001", "2023002", "2023003", "2023004", "2023005", "2023006", "2023007", "2023008", "2023009", "20230010"};
        String[] receiverBank = {"1Bank", "2Bank", "3Bank", "4Bank", "5Bank", "6Bank", "7Bank", "8Bank", "9Bank", "10Bank"};
        String[] receiverBankId = {"101", "102", "103", "104", "105", "106", "107", "108", "109", "110"};
        String[] account = {"123456-123456", "159159-756345", "195378-418932", "159873-789312", "987138-798301", "315859-498878", "891231-159732", "897891-897411", "913215-711500", "040368-068888"};

        String sql = "INSERT INTO tb_daily_transaction" +
                " (sender, sender_id, sender_bank, sender_bank_id, sender_account, receiver, receiver_id, receiver_bank, receiver_bank_id, receiver_account, amount, transfer_time, transfer_day)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                Random random = new Random();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                int day = -1;

                for (int j = 0; j < DAY_RANGE; j++) {
                    long dailyStartTime = System.currentTimeMillis();
                    Calendar yesterday = Calendar.getInstance();
                    yesterday.add(Calendar.DAY_OF_MONTH, day);

                    for (int i = 1; i <= DATA_SIZE; i++) {
                        preparedStatement.setString(1, sender[i % sender.length]);
                        preparedStatement.setString(2, senderId[i % senderId.length]);
                        preparedStatement.setString(3, sendBank[i % sendBank.length]);
                        preparedStatement.setString(4, sendBankId[i % sendBankId.length]);
                        preparedStatement.setString(5, account[random.nextInt(account.length)]);
                        preparedStatement.setString(6, receiver[i % receiver.length]);
                        preparedStatement.setString(7, receiverId[i % receiverId.length]);
                        preparedStatement.setString(8, receiverBank[i % receiverBank.length]);
                        preparedStatement.setString(9, receiverBankId[i % receiverBankId.length]);
                        preparedStatement.setString(10, account[random.nextInt(account.length)]);
                        preparedStatement.setLong(11, (long) random.nextInt(999999) + 1);
                        preparedStatement.setTimestamp(12, new Timestamp(yesterday.getTimeInMillis()));
                        preparedStatement.setString(13, dateFormat.format(yesterday.getTime()));
                        preparedStatement.addBatch();

                        if (i % COMMIT_SIZE == 0) {
                            preparedStatement.executeBatch();
                            preparedStatement.clearBatch();
                            connection.commit();
                        }
                    }

                    preparedStatement.executeBatch();
                    preparedStatement.clearBatch();
                    connection.commit();

                    day--;
                    long dailyEndTime = System.currentTimeMillis();
                    double dailyDuration = (dailyEndTime - dailyStartTime) / 1000.0;
                    log.info("{}일 작업 시간 = {}초", yesterday.get(Calendar.DAY_OF_MONTH), dailyDuration);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        double duration = (endTime - startTime) / 1000.0;
        log.info("배치 인서트 종료");
        log.info("총 데이터 건 수 = {}", DATA_SIZE * DAY_RANGE);
        log.info("총 경과 시간 = {} 초", duration);
        log.info("하루당 평균 소요 시간 = {} 초", String.format("%.3f", duration / DAY_RANGE));
    }

}