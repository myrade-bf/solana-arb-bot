package com.afifhalim.halimafifhusfi_1202151234_modul6.model;

import java.io.Serializable;

//Class model untuk Menerima output dari json Comment di firebase
//implements Serializable berfungsi supaya model dapat di passing menggunakan putExtra
public class CommentModel implements Serializable {
    //Deklarasi variable
    public String name;
    public String email;
    public String comment;

    //konstruktor kosong *diperlukan oleh firebase
    public CommentModel() {
    }

    //konstruktor
    public CommentModel(String name, String email, String comment) {
        this.name = name;
        this.email = email;
        this.comment = comment;
    }

    //getter setter semua variable
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
