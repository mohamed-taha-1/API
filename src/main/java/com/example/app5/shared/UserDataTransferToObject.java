package com.example.app5.shared;

import java.io.Serializable;

public class UserDataTransferToObject implements Serializable {

    private static final long serialVersionUID = 2321035895346670971L;

    private int id;

    private String firstName;

    private String userId;

    private String lastName;
   private  String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private  String email;

    private String encryptedPassword;



    private String emailVerficationToken;


    private  Boolean emailVerifcationStatus=false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getEmailVerficationToken() {
        return emailVerficationToken;
    }

    public void setEmailVerficationToken(String emailVerficationToken) {
        this.emailVerficationToken = emailVerficationToken;
    }

    public Boolean getEmailVerifcationStatus() {
        return emailVerifcationStatus;
    }

    public void setEmailVerifcationStatus(Boolean emailVerifcationStatus) {
        this.emailVerifcationStatus = emailVerifcationStatus;
    }
}
