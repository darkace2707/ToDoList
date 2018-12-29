package com.company.DB;

public interface UserRepository {
    void signUp(String login, String password);

    int logIn(String login, String password);
}
