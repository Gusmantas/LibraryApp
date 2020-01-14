package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class User extends Person {

    private ArrayList<Book> borrowedBooks = new ArrayList<>();
    private transient Scanner scanner = new Scanner(System.in);

    public User(String name, String password, String email) {
        super(name, password, email);
    }


    private void showBorrowedBooks() {
        for (Book book : borrowedBooks) {
            System.out.println(book);
        }
    }

    public void userInfoMenu(User user) {
        System.out.println("[1] See my borrowed books");
        System.out.println("[2] See due time for a book");
        String userInput = scanner.nextLine();
        switch (userInput) {
            case "1":
                showBorrowedBooks();
                break;
            case "2":
                getDueTimeOfBook();
                break;
            default:
                System.out.println("Incorrect input.");
                break;

        }
    }

    private void getDueTimeOfBook(){
        try {
            showBorrowedBooks();
            System.out.println("Enter title of a book: ");
            String bookTitle = scanner.nextLine();
            System.out.println("Book to be returned on: ");
            findBookByTitleOrAuthor(bookTitle, borrowedBooks).showDueTime();
        }catch(Exception ex){
            System.out.println("Book title incorrect. Try again.");
        }
    }

    public Book findBookByTitleOrAuthor(String name, ArrayList<Book> arrayList) {
        for (Book book : arrayList) {
            if (book.getTitle().toLowerCase().contains(name.toLowerCase()) || book.getWriter().toLowerCase().contains(name.toLowerCase()))
                return book;
        }
        return null;
    }



    public void borrowBook(Book book) {
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
    }
}

