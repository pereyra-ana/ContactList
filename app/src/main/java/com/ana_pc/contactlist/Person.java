package com.ana_pc.contactlist;

import android.graphics.Bitmap;

/**
 * Created by Ana-pc on 19/02/2017.
 */

public class Person {

    public String name;
    public String lastName;
    public String emailAddress;
    public String phoneNumber;
    public Bitmap photo;

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public Person(String name, String lastname, String emailAddress, String phoneNumber, Bitmap photo)
    {
        this.name = name;
        this.lastName = lastname;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.photo = photo;
    }

    public void updatePerson(String name, String lastname, String emailAddress, String phoneNumber, Bitmap photo)
    {
        this.name = name;
        this.lastName = lastname;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.photo = photo;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
