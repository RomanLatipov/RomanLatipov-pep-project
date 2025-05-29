package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;

public class AccountDAO {
    public Account getAccountByUsername(String username) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM account WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);

            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()){
                int loginId = rs.getInt("account_id");
                String loginUsername = rs.getString("username");
                String loginPassword = rs.getString("password");

                return new Account(loginId, loginUsername, loginPassword);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
    
    public Account insertAccount(Account account) {
        Connection connection = ConnectionUtil.getConnection();

        try{
            String sql = "INSERT INTO account(username, password) VALUES(?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());

            preparedStatement.executeUpdate();

            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if (pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    public Account getAccountById(int id){
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM account WHERE account_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()){
                int loginId = rs.getInt("account_id");
                String loginUsername = rs.getString("username");
                String loginPassword = rs.getString("password");

                return new Account(loginId, loginUsername, loginPassword);
            }

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }
}
