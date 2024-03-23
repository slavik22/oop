package com.example.back.dao;

import com.example.back.model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDAO {

    // Create account
    public boolean createAccount(Account account, int userId) {
        String createAccountSql = "INSERT INTO account (number, sum, is_locked) VALUES (?, ?, ?)";
        String linkUserAccountSql = "INSERT INTO user_account (user_id, account_number) VALUES (?, ?)";

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement createAccountStmt = connection.prepareStatement(createAccountSql);
             PreparedStatement linkUserAccountStmt = connection.prepareStatement(linkUserAccountSql)) {

            connection.setAutoCommit(false);

            createAccountStmt.setString(1, account.getNumber());
            createAccountStmt.setDouble(2, account.getSum());
            createAccountStmt.setBoolean(3, account.isLocked());
            createAccountStmt.executeUpdate();

            linkUserAccountStmt.setInt(1, userId);
            linkUserAccountStmt.setString(2, account.getNumber());
            linkUserAccountStmt.executeUpdate();

            connection.commit();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isAccountOwner(int userId, String accountNumber) {
        String sql = "SELECT COUNT(*) FROM account WHERE number = ? AND user_id = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, accountNumber);
            statement.setInt(2, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean blockAccount(String accountNumber) {
        return updateAccountStatus(accountNumber, true);
    }

    public boolean unblockAccount(String accountNumber) {
        return updateAccountStatus(accountNumber, false);
    }

    private boolean updateAccountStatus(String accountNumber, boolean isLocked) {
        String sql = "UPDATE account SET is_locked = ? WHERE number = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setBoolean(1, isLocked);
            preparedStatement.setString(2, accountNumber);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deposit(String accountNumber, double amount) {
        String sql = "UPDATE account SET sum = sum + ? WHERE number = ?";
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setDouble(1, amount);
            preparedStatement.setString(2, accountNumber);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception
            return false;
        }
    }
}
