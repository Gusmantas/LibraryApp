package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class User extends Person {

    private ArrayList<Book> borrowedBooks = new ArrayList<>();

    public User(String name, String password, String email) {
        super(name, password, email);
    }


    public void showBorrowedBooks() {
        if (!borrowedBooks.isEmpty()) {
            for (Book book : borrowedBooks) {
                System.out.println(book);
            }
        } else {
            System.out.println("There isn't any borrowed books yet.");
        }
    }


    public void userInfoMenu(User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("[1] See my borrowed books");
        System.out.println("[2] See due time for a book");
        System.out.println("[3] Return a book");
        String userInput = scanner.nextLine();
        switch (userInput) {
            case "1":
                showBorrowedBooks();
                break;
            case "2":
                getDueTimeOfBook();
                break;
            case "3":
                try {
                    System.out.println("Enter title of the book you wish to return: ");
                    String bookTitle = scanner.nextLine();
                    returnBookToLibrary(findBookByTitleOrAuthor(bookTitle, borrowedBooks));
                } catch (Exception ex) {
                    System.out.println("Book could not be found.");
                }
                break;
            default:
                System.out.println("Incorrect input.");
                break;
        }
    }

    public void returnBookToLibrary(Book book) {
        borrowedBooks.remove(book);
        book.setAvailable(true);
        System.out.println(book.getTitle() + " returned to library");
    }

    public Book searchBookByName(String name) {
        for (Book book : borrowedBooks) {
            if (name.equals(book.getTitle())) {
                return book;
            }
        }
        System.out.println(name + " not found.");
        return null;
    }

    private void getDueTimeOfBook() {
        Scanner scanner = new Scanner(System.in);
        try {
            showBorrowedBooks();
            System.out.println("Enter title of a book: ");
            String bookTitle = scanner.nextLine();
            System.out.println("Book to be returned on: ");
            findBookByTitleOrAuthor(bookTitle, borrowedBooks).showDueTime();
            //Not sure about this one. How to display reminder for an overdue book?
            findBookByTitleOrAuthor(bookTitle, borrowedBooks).startReminder();
        } catch (Exception ex) {
            System.out.println("Book title incorrect. Try again.");
        }
    }

    public Book findBookByTitleOrAuthor(String name, ArrayList<Book> arrayList) {
        for (Book book : arrayList) {
            if (book.getTitle().toLowerCase().contains(name.toLowerCase()) || book.getWriter().toLowerCase().contains(name.toLowerCase()))
                return book;
        }
        System.out.println(name + " not found.");
        return null;
    }

    public void borrowBook(Book book) {
        if(borrowedBooks.size() <= 3) {
            if (book != null) {
                borrowedBooks.add(book);
                book.setAvailable(false);
                book.startReminder();
                System.out.println("You have borrowed: " + book.getTitle());
                System.out.println(book.getTitle() + " Should be returned: ");
                book.showDueTime();
            } else {
                System.out.println("Error! Book could not be found.");
            }
        }else{
            System.out.println("Book limit reached. Return one of the books you borrowed book if you wish to borrow this one.");
        }
    }

    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

}

