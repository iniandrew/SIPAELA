package com.app.sipaela.models;

import javafx.beans.property.*;

/**
 * Created by Chrisdion Andrew on 11/01/22 13.51
 */
public class User {
    private IntegerProperty id;
    private StringProperty name, username, password, jabatan, status,created_at;

    public User(int id, String name, String username, String password, String status, String created_at) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.status = new SimpleStringProperty(status);
        this.created_at = new SimpleStringProperty(created_at);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public StringProperty statusProperty() {
        return status;
    }

    public StringProperty created_atProperty() {
        return created_at;
    }
}
