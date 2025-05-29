package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account addAccount(Account account) {
        if (!account.getUsername().isBlank() && account.getPassword().length() >= 4) {
            Account acnt = accountDAO.getAccountByUsername(account.getUsername());
            if (acnt == null)
                return accountDAO.insertAccount(account);
        }

        return null;
    }

    public Account getAccount(Account account) {
        String loginUsername = account.getUsername();
        String loginPassword = account.getPassword();

        Account acnt = accountDAO.getAccountByUsername(loginUsername);
        
        if(acnt != null && loginPassword.equals(acnt.getPassword())) {
            return acnt;
        }

        return null;
    }
}
