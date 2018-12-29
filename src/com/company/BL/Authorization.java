package com.company.BL;

import com.company.DB.MySQLConnection;
import com.company.DB.UserMySQLRepository;
import com.company.DB.UserRepository;

import java.sql.SQLOutput;
import java.util.Scanner;

public class Authorization {

    private int id;
    private UserRepository userRepository;

    public Authorization(UserRepository uRepo) {
        this.userRepository = uRepo;
    }

    public int logIn(String login, String password) {
        this.id = userRepository.logIn(login, password);
        return this.id;
    }


    public void signUp(String login, String password) {
        userRepository.signUp(login, password);
    }
}
