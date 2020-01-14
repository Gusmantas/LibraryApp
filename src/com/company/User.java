package com.company;

import java.util.ArrayList;

public class User extends Person {

    private ArrayList<Book> borrowedBooks = new ArrayList<>();

    public User(String name, String password, String email) {
        super(name, password, email);
    }

    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void borrowBook(Book book){
                if(book != null){
                borrowedBooks.add(book);
                book.setAvailable(false);
                System.out.println("You have borrowed: " + book.getTitle());
            }else{
                System.out.println("Error! Book could not be found.");
            }
        }
    }

