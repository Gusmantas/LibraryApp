package com.company;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Library implements Serializable {
    private ArrayList<Book> booksInLibrary = new ArrayList<>();
    private ArrayList<Person> users = new ArrayList<>();
    private transient Scanner scanner = new Scanner(System.in);
    private User currentUser;
    private Admin currentAdmin;

    public Library() {
        loadProgress();
        logInOrRegisterMenu();
    }

    private void logInOrRegisterMenu() {
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("Welcome to BooK-Worms library!");
            System.out.println("______________________________");
            System.out.println("[1] Log in.");
            System.out.println("[2] Register user.");
            System.out.println("[3] Exit");
            String userInput = scanner.nextLine();
            switch (userInput) {
                case "1":
                    logInUser();
                    break;
                case "2":
                    registerUser();
                    break;
                case "3":
                    System.out.println("Bye!");
                    isRunning = false;
                    break;
                default:
                    System.out.println("Incorrect input. You must enter number '1' or '2'");
                    break;
            }
        }
    }

    private void mainMenu() {
        boolean isMenuOn = true;
        while (isMenuOn) {
            System.out.println("-----------------------");
            System.out.println("[1] Borrow a book");
            System.out.println("[2] See all books");
            System.out.println("[3] See available books");
            System.out.println("[4] Search for books");
            System.out.println("[5] Sort available books");
            System.out.println("[6] My page");
            System.out.println("[7] Log out ");
            String userInput = scanner.nextLine();
            switch (userInput) {
                case "1":
                    showAvailableBooks();
                    System.out.println("___________________________");
                    System.out.println("Enter book title or author:");
                    String titleOrAuthor = scanner.nextLine();
                    currentUser.findBookByTitleOrAuthor(titleOrAuthor, booksInLibrary);
                    currentUser.borrowBook(currentUser.findBookByTitleOrAuthor(titleOrAuthor, booksInLibrary));
                    break;
                case "2":
                    showAllBooks(booksInLibrary);
                    break;
                case "3":
                    showAvailableBooks();
                    break;
                case "4":
                    System.out.println("Enter book title or author: ");
                    String searchedBook = scanner.nextLine();
                    System.out.println(currentUser.findBookByTitleOrAuthor(searchedBook, booksInLibrary));
                    System.out.println("Summary: ");
                    System.out.println(currentUser.findBookByTitleOrAuthor(searchedBook, booksInLibrary).getSummary());
                    break;
                case "5":
                    System.out.println("[1] Sort books by title");
                    System.out.println("[2] Sort books by author");
                    String sortBooksByTitleOrAuthor = scanner.nextLine();
                    if(sortBooksByTitleOrAuthor.equals("1")){
                        Objects.requireNonNull(returnAvailableBooksList()).sort(Comparator.comparing(Book::getTitle));
                        printSortedBooks();
                    }else if(sortBooksByTitleOrAuthor.equals("2")){
                        Objects.requireNonNull(returnAvailableBooksList()).sort(Comparator.comparing(Book::getWriter));
                        printSortedBooks();
                    }else{
                        System.out.println("Incorrect input. Try again.");
                    }
                    break;
                case "6":
                    currentUser.userInfoMenu();
                    break;
                case "7":
                    FileUtility.writeObject(this, "saveLibrary.ser");
                    System.out.println("Hope to see you soon!");
                    currentUser = null;
                    isMenuOn = false;
                    //System.exit(0);
                    break;
                default:
                    System.out.println("Incorrect input.");
                    break;
            }
        }
    }

    private ArrayList<Book> returnAvailableBooksList(){
        ArrayList<Book> availableBooks = new ArrayList<>();
        for(Book book : booksInLibrary){
            if(book.isAvailable()){
                availableBooks.add(book);
            }
        }
        if(!availableBooks.isEmpty()) {
            return availableBooks;
        }
        System.out.println("There are no available books for the moment.");
        return null;
    }
    private void printSortedBooks(){
        for(Book book : Objects.requireNonNull(returnAvailableBooksList())){
            System.out.println(book);
        }
    }

    private void loadProgress() {
        Path file = new File("saveLibrary.ser").toPath();
        if (Files.exists(file)) {
            Library libraryFromFile = (Library) FileUtility.readObject("saveLibrary.ser");
            this.users = libraryFromFile.users;
            this.booksInLibrary = libraryFromFile.booksInLibrary;
        } else {
            addStartingBooksAndAdminToLibrary();
        }
    }

    private void showAvailableBooks() {
        for (Book book : booksInLibrary) {
            if (book.isAvailable()) {
                System.out.println(book);
            }
        }
    }

    private void registerUser() {
        String username, emailAddress, password, repeatedPassword;
        System.out.println("Enter username: ");
        username = scanner.nextLine();
        if (username.isEmpty()) {
            System.out.println("Username cannot be empty.");
        } else {
            System.out.println("Enter your email address: ");
            emailAddress = scanner.nextLine();
            String emailValidationRegex = "[\\w]{3,10}@[\\w]{3,10}\\.[a-zA-Z]{2,8}";
            Pattern pattern = Pattern.compile(emailValidationRegex);
            Matcher emailMatchesRegex = pattern.matcher(emailAddress);
            if (!emailMatchesRegex.matches()) {
                System.out.println("Email address is not valid. Try again.");
            } else {
                System.out.println("Enter your password: ");
                password = scanner.nextLine();
                System.out.println("Repeat password: ");
                repeatedPassword = scanner.nextLine();
                if (!password.equals(repeatedPassword)) {
                    System.out.println("Passwords do not match, try again.");
                } else {
                    users.add(new User(username, password, emailAddress));
                    System.out.println("User successfully registered!");
                    System.out.println(users);
                }
            }
        }
    }

    private void logInUser() {
        System.out.println("Enter username: ");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        for (Person user : users) {
            if (username.equals(user.getName()) && password.equals(user.getPassword())) {
                if (user instanceof Admin) {
                    currentAdmin = (Admin) user;
                    System.out.println("Welcome back, Librarian!");
                    currentAdmin.adminMenu(users, booksInLibrary);
                    FileUtility.writeObject(this, "saveLibrary.ser");
                    System.exit(0);
                    break;
                } else if (user instanceof User) {
                    System.out.println("Welcome back, " + username + "!");
                    currentUser = (User) user;
                    mainMenu();
                    break;
                }
            }
        }
        System.out.println("Password or username incorrect. Try again.");
    }

    private void showAllBooks(ArrayList<Book> books) {
        for (Book book : books) {
            System.out.println(book);
            System.out.println(book.getSummary());
            System.out.println("_________________________________________________");
        }
    }

    private void addStartingBooksAndAdminToLibrary() {
        booksInLibrary.add(new Book("10% Happier", "Dan Harris", "Practicing meditation and mindfulness will make you at least 10 percent happier."));
        booksInLibrary.add(new Book("The 10X Rule", "Grant Cardone", "10 Rules on how to achieve goals in life"));
        booksInLibrary.add(new Book("Mastermind Dinners", "Jayson Gaignard", "Hosting dinners with like-minded people is one of the most powerful way to build fantastic relationships in business and in life"));
        booksInLibrary.add(new Book("Cinderella", "Charles Perrault", "Cinderella is the story of jealousy, virtues and sufferings of Cinderella and hostility of her older sisters"));
        booksInLibrary.add(new Book("Romeo And Juliet", "William Shakespeare", "Romeo and Juliette is an epic love story whose plot is set in a small Italian city Verona."));
        booksInLibrary.add(new Book("A Christmas Carol", "Charles Dickens", "In the beginning, Scrooge is a grumpy miser with no Christmas spirit..."));
        booksInLibrary.add(new Book("The Reason I Jump", "Naoki Higashida", "This book is an autobiography written by a 13-year-old boy from Japan about what it is like to live with autism."));
        booksInLibrary.add(new Book("The Richest Man in Babylon", "George S. Clason", "Save at least 10 percent of everything you earn and do not confuse your necessary expenses with your desires."));
        booksInLibrary.add(new Book("Java For Dummies 7th Edition", "Barry Burd", "A new edition of the bestselling guide to Java If you want to learn to speak the world s most popular programming language like a native, Java For Dummies is your ideal companion"));
        users.add(new Admin("Admin", "admin", "admin@bookworms.com"));
        users.add(new User("Mantas", "zz", "mantas@gmail.com"));
    }
}