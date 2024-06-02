import java.util.Scanner;
import java.util.ArrayList;

class User {
    private String userId;
    private String userPin;
    private double balance;
    private ArrayList<String> transactionHistory;

    public User(String userId, String userPin, double initialBalance) {
        this.userId = userId;
        this.userPin = userPin;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public String getUserPin() {
        return userPin;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add("Deposited: $" + amount);
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactionHistory.add("Withdrew: $" + amount);
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    public void transfer(User recipient, double amount) {
        if (amount <= balance) {
            balance -= amount;
            recipient.deposit(amount);
            transactionHistory.add("Transferred: $" + amount + " to " + recipient.getUserId());
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    public void showTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            for (String transaction : transactionHistory) {
                System.out.println(transaction);
            }
        }
    }
}

public class ATMInterface {
    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<User> users = new ArrayList<>();

    public static void main(String[] args) {
        // Creating some users for demonstration
        users.add(new User("user1", "1234", 1000));
        users.add(new User("user2", "5678", 2000));

        System.out.print("Enter user ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter user PIN: ");
        String userPin = scanner.nextLine();

        User currentUser = authenticateUser(userId, userPin);

        if (currentUser != null) {
            int choice;
            do {
                showMenu();
                choice = scanner.nextInt();
                scanner.nextLine();  // Consume newline left-over
                switch (choice) {
                    case 1:
                        currentUser.showTransactionHistory();
                        break;
                    case 2:
                        System.out.print("Enter amount to withdraw: ");
                        double withdrawAmount = scanner.nextDouble();
                        currentUser.withdraw(withdrawAmount);
                        break;
                    case 3:
                        System.out.print("Enter amount to deposit: ");
                        double depositAmount = scanner.nextDouble();
                        currentUser.deposit(depositAmount);
                        break;
                    case 4:
                        System.out.print("Enter recipient user ID: ");
                        String recipientId = scanner.nextLine();
                        System.out.print("Enter amount to transfer: ");
                        double transferAmount = scanner.nextDouble();
                        User recipient = findUserById(recipientId);
                        if (recipient != null) {
                            currentUser.transfer(recipient, transferAmount);
                        } else {
                            System.out.println("Recipient not found.");
                        }
                        break;
                    case 5:
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } while (choice != 5);
        } else {
            System.out.println("Authentication failed. Exiting.");
        }
    }

    private static User authenticateUser(String userId, String userPin) {
        for (User user : users) {
            if (user.getUserId().equals(userId) && user.getUserPin().equals(userPin)) {
                return user;
            }
        }
        return null;
    }

    private static User findUserById(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    private static void showMenu() {
        System.out.println("1. Transactions History");
        System.out.println("2. Withdraw");
        System.out.println("3. Deposit");
        System.out.println("4. Transfer");
        System.out.println("5. Quit");
        System.out.print("Enter your choice: ");
    }
}
