package com.app.sipaela.models;

import javafx.beans.property.*;

/**
 * Created by Chrisdion Andrew on 11/01/22 13.51
 */
public class User {
    private IntegerProperty id;
    private StringProperty name, username, password, jabatan, status,created_at;

    public User(int id, String name, String username, String password, String jabatan, String status, String created_at) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.jabatan = new SimpleStringProperty(jabatan);
        this.status = new SimpleStringProperty(status);
        this.created_at = new SimpleStringProperty(created_at);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public String getJabatan() {
        return jabatan.get();
    }

    public StringProperty jabatanProperty() {
        return jabatan;
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public String getCreated_at() {
        return created_at.get();
    }

    public StringProperty created_atProperty() {
        return created_at;
    }
}
