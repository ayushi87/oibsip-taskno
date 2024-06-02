import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Book {
    private String title;
    private String author;
    private boolean isIssued;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isIssued = false;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isIssued() {
        return isIssued;
    }

    public void setIssued(boolean issued) {
        isIssued = issued;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isIssued=" + isIssued +
                '}';
    }
}

class User {
    private String name;
    private List<Book> issuedBooks;

    public User(String name) {
        this.name = name;
        this.issuedBooks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Book> getIssuedBooks() {
        return issuedBooks;
    }

    public void issueBook(Book book) {
        issuedBooks.add(book);
        book.setIssued(true);
    }

    public void returnBook(Book book) {
        issuedBooks.remove(book);
        book.setIssued(false);
    }
}

class Admin {
    private List<Book> books;

    public Admin() {
        this.books = new ArrayList<>();
    }

    public void addBook(String title, String author) {
        books.add(new Book(title, author));
    }

    public void removeBook(String title) {
        books.removeIf(book -> book.getTitle().equals(title));
    }

    public List<Book> getBooks() {
        return books;
    }
}

public class DigitalLibraryManagement {
    private static Admin admin = new Admin();
    private static List<User> users = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("1. Admin\n2. User\n3. Exit");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    adminMenu();
                    break;
                case 2:
                    userMenu();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 3);
    }

    private static void adminMenu() {
        int choice;

        do {
            System.out.println("Admin Menu");
            System.out.println("1. Add Book\n2. Remove Book\n3. View Books\n4. Back to Main Menu");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter book author: ");
                    String author = scanner.nextLine();
                    admin.addBook(title, author);
                    break;
                case 2:
                    System.out.print("Enter book title to remove: ");
                    title = scanner.nextLine();
                    admin.removeBook(title);
                    break;
                case 3:
                    List<Book> books = admin.getBooks();
                    if (books.isEmpty()) {
                        System.out.println("No books available.");
                    } else {
                        books.forEach(System.out::println);
                    }
                    break;
                case 4:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 4);
    }

    private static void userMenu() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        User user = findOrCreateUser(name);

        int choice;

        do {
            System.out.println("User Menu");
            System.out.println("1. View Books\n2. Issue Book\n3. Return Book\n4. View Issued Books\n5. Back to Main Menu");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    List<Book> books = admin.getBooks();
                    if (books.isEmpty()) {
                        System.out.println("No books available.");
                    } else {
                        books.forEach(System.out::println);
                    }
                    break;
                case 2:
                    System.out.print("Enter book title to issue: ");
                    String title = scanner.nextLine();
                    Book bookToIssue = findBookByTitle(title);
                    if (bookToIssue != null && !bookToIssue.isIssued()) {
                        user.issueBook(bookToIssue);
                        System.out.println("Book issued successfully.");
                    } else {
                        System.out.println("Book is either not available or already issued.");
                    }
                    break;
                case 3:
                    System.out.print("Enter book title to return: ");
                    String titleToReturn = scanner.nextLine();
                    Book bookToReturn = findBookByTitle(titleToReturn);
                    if (bookToReturn != null && bookToReturn.isIssued()) {
                        user.returnBook(bookToReturn);
                        System.out.println("Book returned successfully.");
                    } else {
                        System.out.println("Book is either not available or not issued.");
                    }
                    break;
                case 4:
                    List<Book> issuedBooks = user.getIssuedBooks();
                    if (issuedBooks.isEmpty()) {
                        System.out.println("No books issued.");
                    } else {
                        issuedBooks.forEach(System.out::println);
                    }
                    break;
                case 5:
                    System.out.println("Returning to main menu...");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 5);
    }

    private static User findOrCreateUser(String name) {
        for (User user : users) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        User newUser = new User(name);
        users.add(newUser);
        return newUser;
    }

    private static Book findBookByTitle(String title) {
        for (Book book : admin.getBooks()) {
            if (book.getTitle().equals(title)) {
                return book;
            }
        }
        return null;
    }
}
