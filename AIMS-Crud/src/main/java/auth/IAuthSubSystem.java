package auth;

public interface IAuthSubSystem {
    boolean isAdmin(String username, String password);
}
