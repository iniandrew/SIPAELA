package com.app.sipaela.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Chrisdion Andrew on 15/01/22 19.28
 */
public class Parking {
    private IntegerProperty id, biaya;
    private StringProperty nopol, category, type, waktu_masuk, waktu_keluar, status;

    public Parking(int id, String nopol, String category, String type, int biaya, String waktu_masuk, String waktu_keluar, String status) {
        this.id = new SimpleIntegerProperty(id);
        this.biaya = new SimpleIntegerProperty(biaya);
        this.nopol = new SimpleStringProperty(nopol);
        this.category = new SimpleStringProperty(category);
        this.type = new SimpleStringProperty(type);
        this.waktu_masuk = new SimpleStringProperty(waktu_masuk);
        this.waktu_keluar = new SimpleStringProperty(waktu_keluar);
        this.status = new SimpleStringProperty(status);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public IntegerProperty biayaProperty() {
        return biaya;
    }

    public StringProperty nopolProperty() {
        return nopol;
    }

    public StringProperty categoryProperty() {
        return category;
    }

    public StringProperty typeProperty() {
        return type;
    }

    public StringProperty waktu_masukProperty() {
        return waktu_masuk;
    }

    public StringProperty waktu_keluarProperty() {
        return waktu_keluar;
    }

    public StringProperty statusProperty() {
        return status;
    }
}
