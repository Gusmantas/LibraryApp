package com.company;

import java.util.ArrayList;

public class User extends Person {
    private ArrayList<Book> borrowedBooks = new ArrayList<>();
    public User(String name, String password, String email) {
        super(name, password, email);
    }
}
