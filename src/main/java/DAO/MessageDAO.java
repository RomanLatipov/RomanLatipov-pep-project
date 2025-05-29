package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    public Message insertMessage(Message message) {
        Connection connection = ConnectionUtil.getConnection();

        try{
            String sql = "INSERT INTO message(posted_by, message_text, time_posted_epoch) VALUES(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());

            preparedStatement.executeUpdate();

            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if (pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    public List<Message> getAllMessages() {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messagesList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                int id = rs.getInt("message_id");
                int posted_id = rs.getInt("posted_by");
                String text = rs.getString("message_text");
                long time = rs.getLong("time_posted_epoch");

                Message message = new Message(id, posted_id, text, time);
                messagesList.add(message);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return messagesList;
    }

    public Message getMessageById(int msgId) {
        Connection connection = ConnectionUtil.getConnection();

         try {
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, msgId);

            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) {
                int id = rs.getInt("message_id");
                int posted_id = rs.getInt("posted_by");
                String text = rs.getString("message_text");
                long time = rs.getLong("time_posted_epoch");

                return new Message(id, posted_id, text, time);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public Message deleteMessageById(int msgId) {
        Connection connection = ConnectionUtil.getConnection();
        Message message = getMessageById(msgId);
        
        try {
            String sql = "DELETE FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, msgId);
            preparedStatement.executeUpdate();
            
            return message;
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public Message updateMessageById(int msgId, String msgText) {
        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, msgText);
            preparedStatement.setInt(2, msgId);

            preparedStatement.executeUpdate();
            return getMessageById(msgId);
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
    
    public List<Message> getMessagesByAccountId(int acntId) {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messagesList = new ArrayList<>();

        try {
            String sql = "SELECT * FROM message WHERE posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, acntId);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                int id = rs.getInt("message_id");
                int posted_id = rs.getInt("posted_by");
                String text = rs.getString("message_text");
                long time = rs.getLong("time_posted_epoch");

                Message message = new Message(id, posted_id, text, time);
                messagesList.add(message);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return messagesList;
    }
}
