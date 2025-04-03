package civ.jfx.library.controller;

import civ.jfx.library.model.User;

public class Gateway {

    private User user = new User();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
