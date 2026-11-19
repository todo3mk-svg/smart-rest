import java.util.Scanner;

public class ATM {
    private static final String CORRECT_PIN = "1234";
    private static final int MAX_PIN_ATTEMPTS = 3;
    private static final int INITIAL_HISTORY_CAPACITY = 10;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] transactions = new String[INITIAL_HISTORY_CAPACITY];
        int transactionCount = 0;
        double balance = 1000.00;

        if (!authenticate(scanner)) {
            System.out.println("Too many incorrect attempts. Your account is locked.");
            scanner.close();
            return;
        }

        boolean running = true;
        while (running) {
            System.out.println("\n--- ATM Menu ---");
            System.out.println("1. Check balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. View transactions");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    System.out.printf("Current balance: $%.2f%n", balance);
                    break;
                case "2": {
                    double amount = readAmount(scanner, "Enter deposit amount: $");
                    if (amount > Double.MAX_VALUE - balance) {
                        System.out.println("That amount exceeds the supported balance limit.");
                        break;
                    }
                    transactions = addTransaction(
                            transactions, transactionCount, "Deposit", amount);
                    transactionCount++;
                    balance += amount;
                    System.out.printf("Deposit successful. New balance: $%.2f%n", balance);
                    break;
                }
                case "3": {
                    double amount = readAmount(scanner, "Enter withdrawal amount: $");
                    if (amount > balance) {
                        System.out.println("Insufficient funds.");
                    } else {
                        transactions = addTransaction(
                                transactions, transactionCount, "Withdrawal", amount);
                        transactionCount++;
                        balance -= amount;
                        System.out.printf("Withdrawal successful. New balance: $%.2f%n", balance);
                    }
                    break;
                }
                case "4":
                    if (transactionCount == 0) {
                        System.out.println("No transactions yet.");
                    } else {
                        System.out.println("--- Transaction History ---");
                        for (int i = 0; i < transactionCount; i++) {
                            System.out.println((i + 1) + ". " + transactions[i]);
                        }
                    }
                    break;
                case "5":
                    running = false;
                    System.out.println("Thank you for using the ATM.");
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1 through 5.");
            }
        }

        scanner.close();
    }

    private static boolean authenticate(Scanner scanner) {
        for (int attempt = 1; attempt <= MAX_PIN_ATTEMPTS; attempt++) {
            System.out.print("Enter your PIN: ");
            String enteredPin = scanner.nextLine().trim();
            if (CORRECT_PIN.equals(enteredPin)) {
                System.out.println("Login successful.");
                return true;
            }

            int attemptsLeft = MAX_PIN_ATTEMPTS - attempt;
            if (attemptsLeft > 0) {
                System.out.println("Incorrect PIN. Attempts remaining: " + attemptsLeft);
            }
        }
        return false;
    }

    private static double readAmount(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                double amount = Double.parseDouble(input);
                if (Double.isFinite(amount) && amount > 0) {
                    return amount;
                }
            } catch (NumberFormatException exception) {
                System.out.println("Please enter a valid number.");
                continue;
            }
            System.out.println("Amount must be finite and greater than zero.");
        }
    }

    private static String[] addTransaction(
            String[] transactions, int transactionCount, String type, double amount) {
        if (transactionCount == transactions.length) {
            String[] expandedHistory = new String[transactions.length * 2];
            for (int i = 0; i < transactions.length; i++) {
                expandedHistory[i] = transactions[i];
            }
            transactions = expandedHistory;
        }

        transactions[transactionCount] = String.format("%s: $%.2f", type, amount);
        return transactions;
    }
}
