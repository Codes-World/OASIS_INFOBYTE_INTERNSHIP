import java.util.*;

class User {
    private final String id;
    private final String pin;

    public User(String id, String pin) {
        this.id = id;
        this.pin = pin;
    }

    public boolean authenticate(String id, String pin) {
        return this.id.equals(id) && this.pin.equals(pin);
    }
}

class Account {
    private double balance;

    public Account(double initialBalance) {
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public void withdraw(double amount) {
        if (amount > this.balance) {
            throw new IllegalArgumentException("Insufficient balance.");
        }
        this.balance -= amount;
    }

    public void transfer(double amount) {
        try{
            if (amount > this.balance) {
                System.out.print("Insufficient Balance");
            }
        }catch(Exception e){
            System.out.print("Insufficient Balance"+e);
        }
        finally {
            this.balance -= amount;
            // Suppose the recipient account is represented by an instance of the Account class as well.
            // In a real-world scenario, you would need to implement a way to find and update the recipient's account.
            Account recipient = new Account(0);
            recipient.deposit(amount);
        }

    }

    public double getBalance() {
        return balance;
    }
}

class TransactionHistory {
    private final List<Transaction> transactions = new ArrayList<>();

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void displayTransactionHistory() {
        System.out.println("Transaction history:");
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }
}

class Transaction {
    private final String type;
    private final double amount;
    private final long timestamp;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
        this.timestamp = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return String.format("%s: %.2f (at %d)", type, amount, timestamp);
    }
}
public class ATMProject {
    private static final Scanner scanner = new Scanner(System.in);
    private static final User user = new User("1234", "5678");

    private static final TransactionHistory transactions = new TransactionHistory();
    private static Account account;

    public static void main(String[] args) {
        System.out.println("Welcome to the ATM!");
        authenticateUser();
        performOperations();
    }

    private static void authenticateUser() {
        System.out.print("Enter your ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter your PIN: ");
        String pin = scanner.nextLine();

        if (user.authenticate(id, pin)) {
            System.out.println("Authentication successful!");
            account = new Account(1000.0);
        } else {
            System.out.println("Invalid credentials. Exiting...");
            System.exit(1);
        }
    }

    private static void performOperations() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nSelect an operation:");
            System.out.println("1. Balance");
            System.out.println("2. Transaction");
            System.out.println("3. Withdraw");
            System.out.println("4. Deposit");
            System.out.println("5. Transfer");
            System.out.println("6. Quit");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> balanceCheck();
                case 2 -> transaction();
                case 3 -> withdraw();
                case 4 -> deposit();
                case 5 -> transfer();
                case 6 -> exit = true;
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void balanceCheck() {
        System.out.printf("Current balance: %.2f\n", account.getBalance());
    }

    private static void transaction() {
        transactions.displayTransactionHistory();
    }

    private static void withdraw() {
        System.out.print("Enter the amount to withdraw: ");
        double amount = scanner.nextDouble();
        account.withdraw(amount);
        transactions.addTransaction(new Transaction("Withdraw", amount));
        System.out.printf("Withdrew %.2f. New balance: %.2f\n", amount, account.getBalance());
    }

    private static void deposit() {
        System.out.print("Enter the amount to deposit: ");
        double amount = scanner.nextDouble();
        account.deposit(amount);
        transactions.addTransaction(new Transaction("Deposit", amount));
        System.out.printf("Deposited %.2f. New balance: %.2f\n", amount, account.getBalance());
    }

    private static void transfer() {
        System.out.print("Enter the recipient's account number: ");
        int recipientAccountNumber = scanner.nextInt();
        System.out.print("Enter the amount to transfer: ");
        double amount = scanner.nextDouble();

        // In a real-world scenario, you would need to implement a way to find the recipient's account.
        // For simplicity, we assume the recipient's account exists and has a positive balance.
        if (recipientAccountNumber == 2 && amount <= account.getBalance()) {
            account.transfer(amount);
            transactions.addTransaction(new Transaction("Transfer", amount));
            System.out.printf("Transferred %.2f to account %d. New balance: %.2f\n", amount, recipientAccountNumber, account.getBalance());
        } else {
            System.out.println("Transfer failed.");
        }
    }
}



