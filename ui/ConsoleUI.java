package ui;

import java.util.Scanner;

public class ConsoleUI {
    private final Scanner scanner = new Scanner(System.in);

    public int showMenuAndGetChoice() {
        System.out.println("\nChoose an action:");
        System.out.println("1 - Get pet by ID");
        System.out.println("2 - Add new pet");
        System.out.println("3 - Update pet");
        System.out.println("4 - Delete pet");
        System.out.println("0 - Exit");
        System.out.print("Your choice: ");
        return scanner.nextInt();
    }

    public long readLong(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextLong()) {
            System.out.print("Please enter a valid number: ");
            scanner.next();
        }
        long val = scanner.nextLong();
        scanner.nextLine(); // clear newline
        return val;
    }

    public String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public void close() {
        scanner.close();
    }
}