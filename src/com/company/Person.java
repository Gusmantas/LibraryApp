package com.company;

import java.io.Serializable;
import java.util.ArrayList;

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

    //This method is used by Admin and User classes.
    public Book findBookByTitleOrAuthor(String name, ArrayList<Book> arrayList) {
        for (Book book : arrayList) {
                if (book.getTitle().toLowerCase().contains(name.toLowerCase()) || book.getWriter().toLowerCase().contains(name.toLowerCase()))
                    return book;
        }
        return null;
    }

    @Override
    public String toString() {
        return "User: " + "Username: " + name + ", email: " + email;

    }
}
