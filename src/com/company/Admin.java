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
            System.out.println("[1] See borrowed books");
            System.out.println("[2] Add new book");
            System.out.println("[3] Remove book");
            System.out.println("[4] See all users, or search for one");
            System.out.println("[5] See all borrowed books");
            System.out.println("[6] Remove user");
            System.out.println("[7] Exit");
            String adminInput = scanner.nextLine();
            switch (adminInput) {
                case "1":
                    seeBorrowedBooks(libraryBooks);
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
                    System.out.println("[1] Search for user and see his/hers borrowed books: ");
                    System.out.println("To exit, hit <enter>");
                    String adminChoice = scanner.nextLine();
                    if (adminChoice.equals("1")) {
                        System.out.println("Enter username: ");
                        String username = scanner.nextLine();
                        User user = findUserByName(username, listOfUsers);
                        System.out.println("____________________________");
                        System.out.println("Books borrowed by " + user.getName() + ": ");
                        user.showBorrowedBooks();
                        System.out.println("____________________________");
                    }
                    break;
                case "5":
                    printUsersAndTheirBorrowedBooks(listOfUsers);
                    break;
                    /*
                case "6":
                    break;
                case "7":
                    break;
                     */
                case "8":
                    System.out.println("Bye!");
                    isRunning = false;
                    break;
                default:
                    System.out.println("Incorrect input. Try again.");
                    break;
            }
        }
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

    public User findUserByName(String name, ArrayList<Person> persons) {
        ArrayList<User> users = new ArrayList<>();
        for (Person person : persons) {
            if (person instanceof User) {
                users.add((User) person);
            }for (User user : users) {
                if (user.getName().toLowerCase().contains(name.toLowerCase())) {
                    return user;
                }
            }
        }
        System.out.println(name + " not found.");
        return null;
    }

    //Duplicated method. Copy also found in User class
    public Book findBookByTitleOrAuthor(String name, ArrayList<Book> arrayList) {
        for (Book book : arrayList) {
            if (book.getTitle().toLowerCase().contains(name.toLowerCase()) || book.getWriter().toLowerCase().contains(name.toLowerCase()))
                return book;
        }
        System.out.println(name + " not found.");
        return null;
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
