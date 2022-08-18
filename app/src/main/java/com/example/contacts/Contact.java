package com.example.contacts;

import java.io.Serializable;

public class Contact implements Serializable {
    String name,phone,city,contactCreated;
    Integer id,imageResource;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContactCreated() {
        return contactCreated;
    }

    public void setContactCreated(String contactCreated) {
        this.contactCreated = contactCreated;
    }

    public Integer getImageResource() {
        return imageResource;
    }

    public void setImageResource(Integer imageResource) {
        this.imageResource = imageResource;
    }

    public Contact(Integer id,String name, String phone, String city, String contactCreated, Integer imageResource) {
        this.id=id;
        this.name = name;
        this.phone = phone;
        this.city = city;
        this.contactCreated = contactCreated;
        this.imageResource = imageResource;
    }
//    public Contact(String name, String phone, String city, String contactCreated) {
//        this.name = name;
//        this.phone = phone;
//        this.city = city;
//        this.contactCreated = contactCreated;
//        this.imageResource = imageResource;
//    }
}
