package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Admin extends Person {

    public Admin(String name, String password, String email) {
        super(name, password, email);
    }

    public void adminMenu(ArrayList<Person> listOfUsers, ArrayList<Book> libraryBooks) {
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("------------------------------------");
            System.out.println("[1] See books");
            System.out.println("[2] Add new book");
            System.out.println("[3] Remove book");
            System.out.println("[4] See all users, or search for one");
            System.out.println("[5] See all borrowed books");
            System.out.println("[6] Remove user");
            System.out.println("[7] Exit");
            String adminInput = scanner.nextLine();
            switch (adminInput) {
                case "1":
                    System.out.println("[1] See all books");
                    System.out.println("[2] See borrowed books");
                    String chooseBooks = scanner.nextLine();
                    if (chooseBooks.equals("1")) {
                        printAllLibraryBooks(libraryBooks);
                    } else if (chooseBooks.equals("2")) {
                        seeBorrowedBooks(libraryBooks);
                    } else {
                        System.out.println("Incorrect input. Try again.");
                    }
                    break;
                case "2":
                    System.out.println("Enter Title: ");
                    String title = scanner.nextLine();
                    System.out.println("Enter Author: ");
                    String author = scanner.nextLine();
                    System.out.println("Enter Summary: ");
                    String summary = scanner.nextLine();
                    addBookToLibrary(libraryBooks, title, author, summary);
                    break;
                case "3":
                    printAllLibraryBooks(libraryBooks);
                    System.out.println("Enter Title: ");
                    String bookToRemoveTitle = scanner.nextLine();
                    removeBookFromLibrary(bookToRemoveTitle, libraryBooks);
                    break;
                case "4":
                    printAllUsers(listOfUsers);
                    System.out.println("__________________________________________________________________________");
                    System.out.println("[1] Search for user and see his/hers borrowed books. To exit, hit <ENTER> ");
                    String adminChoice = scanner.nextLine();
                    try {
                        if (adminChoice.equals("1")) {
                            System.out.println("Enter username: ");
                            String username = scanner.nextLine();
                            User user = getUserByName(listOfUsers, username);
                            System.out.println("____________________________");
                            System.out.println("Books borrowed by " + user.getName() + ": ");
                            user.showBorrowedBooks();
                            System.out.println("____________________________");
                        }
                    } catch (NullPointerException ex) {
                        System.out.println("User not found. Check spelling.");
                    }
                    break;
                case "5":
                    printUsersAndTheirBorrowedBooks(listOfUsers);
                    break;
                case "6":
                    printAllUsers(listOfUsers);
                    try {
                        System.out.println("_____________________________________________");
                        System.out.println("Enter username of a user you wish to remove: ");
                        String userToRemove = scanner.nextLine();
                        listOfUsers.remove(getUserByName(listOfUsers, userToRemove));
                        System.out.println(userToRemove + " removed successfully.");
                    } catch (Exception ex) {
                        System.out.println("User not found, check spelling.");
                    }
                    break;
                case "7":
                    System.out.println("Bye!");
                    isRunning = false;
                    break;
                default:
                    System.out.println("Incorrect input. Try again.");
                    break;
            }
        }
    }

    private User getUserByName(ArrayList<Person> persons, String name) {
        for (Person user : persons) {
            if (user.getName().equals(name)) {
                if (user instanceof User) {
                    return (User) user;
                }
            }
        }
        return null;
    }

    private void printUsersAndTheirBorrowedBooks(ArrayList<Person> users) {
        if (users.isEmpty()) {
            System.out.println("There are no registered users.");
        }
        for (Person user : users) {
            if (user instanceof User) {
                System.out.println(user.getName() + ": ");
                ((User) user).showBorrowedBooks();
            }
        }
    }

    private void printAllLibraryBooks(ArrayList<Book> books) {
        for (Book book : books) {
            System.out.println(book);
            System.out.println(book.getSummary());
            System.out.println("_________________________________________________");
        }
    }

    private void printAllUsers(ArrayList<Person> users) {
        if (users.isEmpty()) {
            System.out.println("There are no registered users yet.");
        }
        for (Person person : users) {
            if (person instanceof User) {
                System.out.println(person);
            }
        }
    }

    private void removeBookFromLibrary(String name, ArrayList<Book> libraryBooks) {
        Book book = findBookByTitleOrAuthor(name, libraryBooks);
        libraryBooks.remove(book);
        System.out.println(book.getTitle() + " is removed.");
    }

    private void addBookToLibrary(ArrayList<Book> books, String name, String writer, String summary) {
        books.add(new Book(name, writer, summary));
        System.out.println(name + " added to library collection");
    }

    private void seeBorrowedBooks(ArrayList<Book> books) {
        for (Book book : books) {
            if (!book.isAvailable()) {
                System.out.println(book);
            } else {
                System.out.println("No books has been borrowed.");
                break;
            }
        }
    }
}
