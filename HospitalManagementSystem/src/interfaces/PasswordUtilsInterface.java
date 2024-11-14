package interfaces;

import models.Account;

public interface PasswordUtilsInterface {
    String hashPassword(String password);
    boolean isValidNewPassword(String password);
    boolean isValidPassword(Account account, String password);
    boolean isFirstTimeLogin(Account account, String password);
    Account handleFirstTimeLogin(Account account);
}
