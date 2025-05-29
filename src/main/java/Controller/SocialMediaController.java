package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;
/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postAccountHandler);
        app.post("/login", this::postLoginHandler);
        app.post("/messages", this::postMessagesHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::patchMessageByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getMessageByAccountIdHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postAccountHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);

        Account addAccount = accountService.addAccount(account);
        if (addAccount != null)
            ctx.json(mapper.writeValueAsString(addAccount));
        else
            ctx.status(400);
    }

    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account loginAccount = mapper.readValue(ctx.body(), Account.class);
        
        Account account = accountService.getAccount(loginAccount);
        if (account != null)
            ctx.json(mapper.writeValueAsString(account));
        else
            ctx.status(401);
    }

    private void postMessagesHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);

        Message msg = messageService.addMessage(message);
        if(msg != null)
            ctx.json(mapper.writeValueAsString(msg));
        else
            ctx.status(400);
    }

    private void getAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    private void getMessageByIdHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int msgId = Integer.parseInt(ctx.pathParam("message_id"));

        Message message = messageService.getMessageById(msgId);
        if (message != null)
            ctx.json(mapper.writeValueAsString(message));
        else
            ctx.status(200);
    }

    private void deleteMessageByIdHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int msgId = Integer.parseInt(ctx.pathParam("message_id"));

        Message message = messageService.deleteMessageById(msgId);
        if (message != null)
            ctx.json(mapper.writeValueAsString(message));
        else
            ctx.status(200);
    }

    private void patchMessageByIdHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        int msgId = Integer.parseInt(ctx.pathParam("message_id"));
        Message msg = mapper.readValue(ctx.body(), Message.class);

        Message message = messageService.updateMessageById(msgId, msg.getMessage_text());
        if (message != null)
             ctx.json(mapper.writeValueAsString(message));
        else
            ctx.status(400);
    }

    private void getMessageByAccountIdHandler(Context ctx) {
        int accountId = Integer.parseInt(ctx.pathParam("account_id"));

        List<Message> messages = messageService.getMessagesByAccountId(accountId);
        ctx.json(messages);
    }
}