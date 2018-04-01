package com.afifhalim.halimafifhusfi_1202151234_modul6.model;

import java.io.Serializable;

//Class model untuk Menerima output dari json Photo di firebase
//implements Serializable berfungsi supaya model dapat di passing menggunakan putExtra
public class PhotoModel implements Serializable {
    //Deklarasi variable
    public String key;
    public String image_url;
    public String title;
    public String desc;
    public String name;
    public String email;

    //konstruktor kosong *diperlukan oleh firebase
    public PhotoModel() {
    }

    //konstruktor
    public PhotoModel(String key, String image_url, String title, String desc, String name, String email) {
        this.key = key;
        this.image_url = image_url;
        this.title = title;
        this.desc = desc;
        this.name = name;
        this.email = email;
    }

    //getter setter semua variable
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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
