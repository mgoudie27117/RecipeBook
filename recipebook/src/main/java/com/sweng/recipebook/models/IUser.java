package com.sweng.recipebook.models;

public interface IUser {
    void createUser();

    Boolean getAuthenticated();

    String getFirstName();

    String getLastName();

    int getUserId();

    String getUserName();

    void login();

    Boolean verifyDuplicateUsername();
}
