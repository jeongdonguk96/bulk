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
import java.util.Calendar;
import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
public class Query {

    private final DataSource dataSource;

    @PostConstruct
    public void batchInsert() {
        long startTime = System.currentTimeMillis();
        log.info("배치 인서트 시작");

        String[] sender = {"김민수", "박정민", "김휘성", "노현진", "마구성", "리석도", "박현민", "김현주", "박미선", "최유미"};
        String[] receiver = {"리시수낙", "벨루아", "미스키", "코레스", "피코리", "로리앙", "필리앙", "샤킬", "크몽", "소레아"};
        String[] bank = {"ABank", "BBank", "CBank", "DBank", "EBank", "FBank", "GBank", "HBank", "IBank", "JBank"};
        String[] account = {"123456-123456", "159159-756345", "195378-418932", "159873-789312", "987138-798301", "315859-498878", "891231-159732", "897891-897411", "913215-711500", "040368-068888"};
        String[] range = {"BRONZE", "SILVER", "GOLD", "PLATINUM", "DIAMOND"};

        String sql = "INSERT INTO tb_daily_transaction" +
                " (sender, sender_bank, sender_account, receiver, receiver_bank, receiver_account, amount, amount_range, transfer_time)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                Random random = new Random();
                int day = -10;

                for (int j = 1; j < 20; j++) {
                    Calendar yesterday = Calendar.getInstance();
                    yesterday.add(Calendar.DAY_OF_MONTH, day);
                    log.info("오늘로부터 {}일자 작업", day);
                    log.info("작업날짜 = {}월 {}일", yesterday.get(Calendar.MONTH) + 1, yesterday.get(Calendar.DAY_OF_MONTH));

                    for (int i = 1; i < 1001000; i++) {
                        preparedStatement.setString(1, sender[random.nextInt(sender.length)]);
                        preparedStatement.setString(2, bank[random.nextInt(bank.length)]);
                        preparedStatement.setString(3, account[random.nextInt(account.length)]);
                        preparedStatement.setString(4, receiver[random.nextInt(receiver.length)]);
                        preparedStatement.setString(5, bank[random.nextInt(bank.length)]);
                        preparedStatement.setString(6, account[random.nextInt(account.length)]);
                        preparedStatement.setLong(7, (long) random.nextInt(999999) + 1);
                        preparedStatement.setString(8, range[random.nextInt(range.length)]);
                        preparedStatement.setTimestamp(9, new Timestamp(yesterday.getTimeInMillis()));
                        preparedStatement.addBatch();

                        if (i % 200000 == 0) {
                            preparedStatement.executeBatch();
                            connection.commit();
                            log.info("{}번째 데이터 생성 완료, DB INSERT 후 COMMIT 완료", i);
                        }
                    }

                    day--;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                connection.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        log.info("배치 인서트 종료");
        log.info("경과 시간 = {} 밀리초", duration);
    }

}