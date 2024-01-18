package controller;

import auth.IAuthSubSystem;
import ui.screen.LoginController;

public class LoginControllerAuthBridge implements LoginController {
    private final IAuthSubSystem authSubSystem;

    public LoginControllerAuthBridge(IAuthSubSystem authSubSystem) {
        this.authSubSystem = authSubSystem;
    }


    @Override
    public boolean login(String username, String password) {
        return authSubSystem.isAdmin(username, password);
    }
}
