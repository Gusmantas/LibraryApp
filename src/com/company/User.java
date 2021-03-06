package com.company;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class User extends Person {
    private ArrayList<Book> borrowedBooks = new ArrayList<>();

    public User(String name, String password, String email) {
        super(name, password, email);
    }

    public void userInfoMenu() {
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
                    showBorrowedBooks();
                    if (borrowedBooks.isEmpty()) {
                        break;
                    }
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

    public void getBookDueTimeReminder() {
        if (borrowedBooks != null) {
            for (Book book : borrowedBooks) {
                if (LocalDateTime.now().isAfter(book.getBookDeadline())) {
                    System.out.println("________________________________________________");
                    System.out.println("Due time for \"" + book.getTitle() + "\" is out.");
                    System.out.println("________________________________________________");
                }
            }
        }
    }

    private void returnBookToLibrary(Book book) {
        borrowedBooks.remove(book);
        book.setAvailable(true);
        System.out.println(book.getTitle() + " returned to library");
    }

    private void getDueTimeOfBook() {
        Scanner scanner = new Scanner(System.in);
        try {
            showBorrowedBooks();
            System.out.println("Enter title of a book: ");
            String bookTitle = scanner.nextLine();
            System.out.println("Book to be returned on: ");
            findBookByTitleOrAuthor(bookTitle, borrowedBooks).showDueTime();
        } catch (Exception ex) {
            System.out.println("Book title incorrect. Try again.");
        }
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

    public void borrowBook(Book book) {
        if (borrowedBooks.size() <= 3) {
            if (book != null && book.isAvailable()) {
                borrowedBooks.add(book);
                book.setAvailable(false);
                book.setBookDeadline();
                System.out.println("You have borrowed: " + book.getTitle());
                System.out.println(book.getTitle() + " Should be returned: ");
                book.showDueTime();
            } else {
                System.out.println("Error! Book could not be found.");
            }
        } else {
            System.out.println("Book limit reached. Return one of the books you borrowed book if you wish to borrow this one.");
        }
    }
}

