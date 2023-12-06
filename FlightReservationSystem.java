package flightreservationsystem;

import java.util.*;

public class FlightReservationSystem {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int choice = 0;
        System.out.println("Flight Reservation System\n");
        String[] accountType = {"Admin", "User", "Guest"};
        String[] password = {"Admin", "User", "Guest"};
        do {
            printMainMenu();
            choice = getChoice();
            if (choice == 0) {
                System.out.println("Exiting the program.");
                break;
            } else if (choice < 0 || choice > 3) {
                System.out.println("Invalid option. Try again.");
                continue;
            }
            System.out.print("Enter password for " + accountType[choice - 1] + ": ");
            String enterPassword = input.nextLine();
            if (enterPassword.equals(password[choice - 1])) {
                System.out.println("You have successfully logged in as " + accountType[choice - 1] + "\n");
                switch (accountType[choice - 1]) {
                    case "Admin":
                        while (true) {
                            printAccountOptions(accountType[choice - 1]);
                            int adminMenuChoice = getChoice();
                            switch (adminMenuChoice) {
                                case 0:
                                    break;
                                case 1:
                                    break;
                                case 2:
                                    break;
                                case 3:
                                    break;
                                default:
                                    System.out.println("Invalid option.");
                                    printAccountOptions(accountType[choice - 1]);
                                    adminMenuChoice = getChoice();
                            }
                            if (adminMenuChoice == 0) {
                                logOut();
                                break;
                            }
                        }
                        break;

                    case "User":
                        while (true) {
                            printAccountOptions(accountType[choice - 1]);
                            int userMenuChoice = getChoice();
                            switch (userMenuChoice) {
                                case 0:
                                    break;
                                case 1:
                                    break;
                                case 2:
                                    break;
                                case 3:
                                    break;
                                default:
                                    System.out.println("Invalid option.");
                                    printAccountOptions(accountType[choice - 1]);
                                    userMenuChoice = getChoice();
                            }
                            if (userMenuChoice == 0) {
                                logOut();
                                break;
                            }
                        }
                        break;

                    case "Guest":
                        printAccountOptions(accountType[choice - 1]);
                        int guestMenuChoice = getChoice();
                        while (true) {
                            switch (guestMenuChoice) {
                                case 0:
                                    break;
                                case 1:
                                    break;
                                case 2:
                                    break;
                                case 3:
                                    break;
                                default:
                                    System.out.println("Invalid option.");
                                    printAccountOptions(accountType[choice - 1]);
                                    guestMenuChoice = getChoice();
                            }
                            if (guestMenuChoice == 0) {
                                logOut();
                                break;
                            }
                        }
                        break;
                }
            } else {
                System.out.println("Wrong password. Try again.");
            }
        } while (true);
    }

    public static int getChoice() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your choice: ");
        int choice = input.nextInt();
        return choice;
    }

    public static void printMainMenu() {
        System.out.println("Main Menu: ");
        System.out.println("1. Admin");
        System.out.println("2. User");
        System.out.println("3. Guest");
        System.out.println("0. Exit\n");
    }

    public static void printAccountOptions(String accountType) {
        System.out.println("Menu for " + accountType + ":");
        switch (accountType) {
            case "Admin":
                System.out.println("1. Add a new flight");
                System.out.println("2. View all flights");
                System.out.println("3. Manage reservations");
                System.out.println("0. Logout\n");
                break;
            case "User":
                System.out.println("1. Search for flights");
                System.out.println("2. Make a reservation");
                System.out.println("3. View my reservations");
                System.out.println("0. Logout\n");
                break;
            case "Guest":
                System.out.println("1. Search for flights");
                System.out.println("2. View flight details");
                System.out.println("0. Logout\n");
                break;
        }
    }

    public static void logOut() {
        System.out.println("You have logged out successfully. \n");

    }
}
