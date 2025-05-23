package model.users;

import model.Badge;
import model.Calendar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User implements Serializable {
    private String pseudo, mdp;
    private boolean connected;
    private Calendar calendarModel = null;

    public User(String pseudo, String mdp) {
        this.pseudo = pseudo;
        this.mdp = mdp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(pseudo, user.pseudo);
    }

    public boolean isCorrectPassword(String password) {
        return password.equals(mdp);
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public boolean isConnected() {
        return connected;
    }

    public String getPseudo() {
        return pseudo;
    }

    public Calendar getCalendarModel() {
        return calendarModel;
    }

    public void setCalendarModel(Calendar calendarModel) {
        this.calendarModel = calendarModel;
    }
}