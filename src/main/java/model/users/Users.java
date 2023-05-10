package model.users;

import java.util.HashMap;

public class Users {
    private final HashMap<String, User> users = new HashMap<>();
    private User activeUser = null;

    public HashMap<String, User> getUsers() {
        return users;
    }

    public User getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(User activeUser) {
        this.activeUser = activeUser;
    }
}
