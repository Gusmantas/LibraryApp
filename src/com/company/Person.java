package com.company;

import java.io.Serializable;

public abstract class Person implements Serializable {
    private String name, password, email;

    public Person(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }


    @Override
    public String toString() {
        return "User: " + "Username: " + name + ", email: " + email;

    }
}
