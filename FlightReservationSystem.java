package flightreservationsystem;

import java.util.*;
import java.io.*;

public class FlightReservationSystem {

    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Flight Reservation System\n");
        do {
            printMainMenu();
            int choice = getChoice();
            if (choice == 0) {
                System.out.println("Exiting the program.");
                break;
            } else if (choice < 0 || choice > 3) {
                System.out.println("Invalid option. Try again.");
                continue;
            }
            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    int accountType = login();
                    if (accountType == 1) {
                        adminMenu(accountType);
                    } else if (accountType == 2) {
                        userMenu(accountType);
                    } else if (accountType != 0) {
                        System.out.println("Invalid choice. You will be redirected to the main menu.\n");
                    }
                    break;
                case 3:
                    guestMenu(3);
                    break;
            }
        } while (true);
    }

    public static int getChoice() {
        int choice = 0;
        while (true) {
            try {
                System.out.print("Enter your choice: ");
                choice = input.nextInt();
                input.nextLine();
                return choice;
            } catch (InputMismatchException e) {
                System.out.println(e + " has occurred. Enter a valid input.");
                input.nextLine();
            }
        }
    }

    public static void printMainMenu() {
        System.out.println("Main Menu: ");
        System.out.println("1. Sign Up");
        System.out.println("2. Sign In");
        System.out.println("3. Continue as guest");
        System.out.println("0. Exit\n");
    }

    public static String getName() {
        System.out.print("Enter name: ");
        String name = input.nextLine();
        return name;
    }

    public static String getPassword() {
        System.out.print("Enter password: ");
        String password = input.nextLine();
        return password;
    }

    public static void createAccount() {
        System.out.println("Select account type:\n1. Admin\n2. User\n");
        while (true) {
            int accountType = getChoice();
            File file;
            FileOutputStream fos;
            String fileName;
            try {
                if (accountType == 1) {
                    fileName = "d:\\admin.txt";
                    file = new File(fileName);
                    fos = new FileOutputStream(fileName, true);
                } else if (accountType == 2) {
                    fileName = "d:\\passenger.txt";
                    file = new File(fileName);
                    fos = new FileOutputStream(fileName, true);

                } else {
                    System.out.println("Invalid choice. You will be redirected to the main menu.");
                    break;
                }
                PrintStream ps = new PrintStream(fos);
                while (true) {
                    String name = getName();

                    if (!validName(name)) {
                        System.out.println("Invalid name. Name requirement: min 5 char and max 15 char, first char cannot be a digit. Please retry.");
                        continue;
                    }
                    String password = getPassword();

                    if (!validPassword(password)) {
                        System.out.println("Invalid password. Password requirements: min 5 char and max 15 char, and special characters are not allowed. Please retry.");
                        continue;
                    }
                    Scanner reader = new Scanner(file);
                    if (!reader.hasNextLine()) {
                        ps.println(name);
                        ps.println(password);
                        System.out.println("Thank you for signing up. Account has been created successfully. You will be redirected to the main menu to sign in.\n");
                        break;
                    }
                    while (reader.hasNextLine()) {
                        String nameToAdd = reader.nextLine();
                        String passwordToAdd = reader.nextLine();
                        if (!nameAlreadyExists(name, nameToAdd)) {
                            ps.println(name);
                            ps.println(password);
                            ps.close();
                            reader.close();
                            System.out.println("Thank you for signing up. Account has been created successfully. You will be redirected to the main menu to sign in.\n");
                            break;
                        } else {
                            System.out.println("An account with this name already exists. You will be redirected to the main menu.\n");
                            break;
                        }
                    }
                    break;
                }
            } catch (IOException e) {
                System.out.println(e);
            }
            break;
        }
    }

    public static boolean validName(String str) {
        if (length(str) && checkFirstChar(str)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validPassword(String str) {
        if (length(str) && onlyLettersAndDigits(str)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean length(String str) {
        if (str.length() >= 5 && str.length() <= 15) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean onlyLettersAndDigits(String str) {
        for (int i = 0; i <= str.length() - 1; i++) {
            if (!Character.isLetterOrDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkFirstChar(String name) {
        if (!Character.isLetter(name.charAt(0))) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean nameVerification(String name, String nameToCheck) {
        if (name.equalsIgnoreCase(nameToCheck)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean passwordVerification(String password, String passwordToCheck) {
        if (password.equals(passwordToCheck)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean nameAlreadyExists(String name, String nameToAdd) {
        if (name.equalsIgnoreCase(nameToAdd)) {
            return true;
        } else {
            return false;
        }
    }

    public static int login() {
        System.out.println("Select account type:\n1. Admin\n2. User");
        int accountType = getChoice();
        boolean flag;
        while (true) {
            flag = false;
            File file;
            try {
                if (accountType == 1) {
                    file = new File("d:\\admin.txt");
                } else if (accountType == 2) {
                    file = new File("d:\\passenger.txt");
                } else {
                    return 0;
                }
                Scanner reader = new Scanner(file);
                String name = getName();
                String password = getPassword();
                while (reader.hasNextLine()) {

                    String nameToCheck = reader.nextLine();
                    String passwordToCheck = reader.nextLine();

                    if (nameVerification(name, nameToCheck) && (passwordVerification(password, passwordToCheck))) {
                        flag = true;
                        break;
                    }
                }
                reader.close();
            } catch (IOException e) {
                System.out.println(e);
                System.out.println("No account exists. Please sign up before attempting to sign in. You will be redirected to the main menu\n");
                return 0;
            }
            if (!flag) {
                System.out.println("An account with this name does not exist or the password is incorrect. You will be redirected to the main menu.\n");
                return 0;
            } else {
                System.out.println("You have logged in successfully.\n");
                return accountType;
            }
        }
    }

    public static void printAccountOptions(int accountType) {
        switch (accountType) {
            case 1:
                System.out.println("Admin Menu: ");
                System.out.println("1. Add a new flight");
                System.out.println("2. View all flights");
                System.out.println("3. Manage reservations");
                System.out.println("4. Delete account");
                System.out.println("0. Logout\n");
                break;
            case 2:
                System.out.println("User Menu:");
                System.out.println("1. Search for flights");
                System.out.println("2. Make a reservation");
                System.out.println("3. View my reservations");
                System.out.println("4. Delete account");
                System.out.println("0. Logout\n");
                break;
            case 3:
                System.out.println("Guest Menu:");
                System.out.println("1. Create account");
                System.out.println("2. Search for flights");
                System.out.println("3. View flight details");
                System.out.println("0. LogOut\n");
                break;
        }
    }

    public static void logOut() {
        System.out.println("You have logged out successfully. \n");

    }

    public static void deleteAccount(int accountType) {
        boolean flag = false;
        String nameToDelete = getName();
        String passwordToDelete = getPassword();
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<String> passwords = new ArrayList<String>();
        System.out.println("Press 1 to delete the account or any other number to cancel.");
        if (getChoice() == 1) {
            try {
                File file;
                if (accountType == 1) {
                    file = new File("d:\\admin.txt");
                } else {
                    file = new File("d:\\passenger.txt");
                }
                Scanner reader = new Scanner(file);
                while (reader.hasNextLine()) {
                    String name = reader.nextLine();
                    String password = reader.nextLine();
                    if (nameVerification(nameToDelete, name) && passwordVerification(passwordToDelete, password)) {
                        flag = true;
                    } else {
                        names.add(name);
                        passwords.add(password);
                    }
                }
                reader.close();
                if (!flag) {
                    System.out.println("Account not found. Deletion aborted.");
                    return;
                }
                FileOutputStream fos = new FileOutputStream(file);
                PrintStream ps = new PrintStream(fos);
                for (int i = 0; i < names.size(); i++) {
                    ps.println(names.get(i));
                    ps.println(passwords.get(i));
                }
                ps.close();
                names.clear();
                passwords.clear();
                System.out.println("Account has been deleted successfully.");
            } catch (IOException e) {
                System.out.println(e);
                return;
            }
        } else {
            System.out.println("Account deletion has been aborted.");
        }

    }

    public static int adminMenu(int accountType) {
        while (true) {
            printAccountOptions(accountType);
            int adminMenuChoice = getChoice();
            switch (adminMenuChoice) {
                case 0:
                    logOut();
                    return 0;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    deleteAccount(accountType);
                    return 0;
                default:
                    System.out.println("Invalid option. Please retry.\n");
            }
        }
    }

    public static int userMenu(int accountType) {
        while (true) {
            printAccountOptions(accountType);
            int userMenuChoice = getChoice();
            switch (userMenuChoice) {
                case 0:
                    logOut();
                    return 0;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    deleteAccount(accountType);
                    return 0;
                default:
                    System.out.println("Invalid option. Please retry.\n");
            }
        }
    }

    public static int guestMenu(int accountType) {
        printAccountOptions(accountType);
        while (true) {
            int guestMenuChoice = getChoice();
            switch (guestMenuChoice) {
                case 0:
                    System.out.println("You have logged out successfully. Redirecting to the main menu.");
                    return 0;
                case 1:
                    createAccount();
                    return 0;
                case 2:
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Invalid option. Please retry.\n");
            }
        }
    }
}
