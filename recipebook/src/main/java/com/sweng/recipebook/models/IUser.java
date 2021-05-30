package com.sweng.recipebook.models;

public interface IUser {
    String getFirstName();

    String getLastName();

    int getUserId();

    String getUserName();

    void login();
}
