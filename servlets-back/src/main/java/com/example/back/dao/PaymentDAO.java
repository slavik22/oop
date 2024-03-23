package com.example.back.dao;

import com.example.back.model.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PaymentDAO {
    private static final Logger logger = LogManager.getLogger(PaymentDAO.class);

    public boolean makePayment(Payment payment) {
        String updateSenderSql = "UPDATE account SET sum = sum - ? WHERE number = ? AND sum >= ?";
        String updateReceiverSql = "UPDATE account SET sum = sum + ? WHERE number = ?";
        String insertPaymentSql = "INSERT INTO payments (from_account, to_account, sum, time) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement updateSenderStmt = connection.prepareStatement(updateSenderSql);
             PreparedStatement updateReceiverStmt = connection.prepareStatement(updateReceiverSql);
             PreparedStatement insertPaymentStmt = connection.prepareStatement(insertPaymentSql)) {

            connection.setAutoCommit(false);

            updateSenderStmt.setDouble(1, payment.getSum());
            updateSenderStmt.setString(2, payment.getFrom());
            updateSenderStmt.setDouble(3, payment.getSum());
            int rowsAffectedSender = updateSenderStmt.executeUpdate();

            updateReceiverStmt.setDouble(1, payment.getSum());
            updateReceiverStmt.setString(2, payment.getTo());
            int rowsAffectedReceiver = updateReceiverStmt.executeUpdate();

            insertPaymentStmt.setString(1, payment.getFrom());
            insertPaymentStmt.setString(2, payment.getTo());
            insertPaymentStmt.setDouble(3, payment.getSum());
            insertPaymentStmt.setDate(4, new java.sql.Date(payment.getTime().getTime()));
            int rowsAffectedPayment = insertPaymentStmt.executeUpdate();

            if (rowsAffectedSender > 0 && rowsAffectedReceiver > 0 && rowsAffectedPayment > 0) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
                logger.error("Transaction rollback: ");

                return false;
            }

        } catch (SQLException e) {
            logger.error("An error occurred: " + e.getMessage(), e);
            return false;
        }
    }
}
