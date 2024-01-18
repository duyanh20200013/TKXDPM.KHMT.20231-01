package auth.impl;

import auth.IAuthSubSystem;

public class SimpleAuthSubSystem implements IAuthSubSystem {
    @Override
    public boolean isAdmin(String username, String password) {
        return "admin".equals(username) && "1".equals(password);
    }
}
