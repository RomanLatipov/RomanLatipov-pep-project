package Service;

import DAO.MessageDAO;
import DAO.AccountDAO;
import Model.Message;
import Model.Account;

import java.util.List;

public class MessageService {
    public MessageDAO messageDAO;
    public AccountDAO accountDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public Message addMessage(Message message) {
        String text = message.getMessage_text();
        Account account = accountDAO.getAccountById(message.getPosted_by());

        if (!text.isBlank() && text.length() <= 255 && account != null) {
            return messageDAO.insertMessage(message);
        }

        return null;
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int id) {
        return messageDAO.getMessageById(id);
    }

    public Message deleteMessageById(int id) {
        return messageDAO.deleteMessageById(id);
    }

    public Message updateMessageById(int id, String text) {
        Message isMessage = getMessageById(id);

        if (!text.isBlank() && text.length() <= 255 && isMessage != null) {
            return messageDAO.updateMessageById(id, text);
        }

        return null;
    }

    public List<Message> getMessagesByAccountId(int id) {
        return messageDAO.getMessagesByAccountId(id);
    }
}
