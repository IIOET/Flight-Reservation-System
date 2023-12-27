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
                System.out.println("\nExiting...");
                System.out.println("The program has successfully terminated.");
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
                        passengerMenu(accountType);
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
                System.out.print("\nEnter your choice: ");
                choice = input.nextInt();
                input.nextLine();
                System.out.println();
                return choice;
            } catch (InputMismatchException e) {
                System.out.println(e + " has occurred. Enter a valid input.");
                input.nextLine();
            }
        }
    }

    public static void printMainMenu() {
        System.out.println("\nMain Menu: ");
        System.out.println("1. Sign Up");
        System.out.println("2. Sign In");
        System.out.println("3. Continue as Guest");
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
        System.out.println("Select account type:\n1. Admin\n2. Passenger\n");
        while (true) {
            int accountType = getChoice();
            File file;
            FileOutputStream fos;
            String fileName;
            try {
                if (accountType == 1) {
                    fileName = "D:\\admin.txt";
                    file = new File(fileName);
                    fos = new FileOutputStream(fileName, true);
                } else if (accountType == 2) {
                    fileName = "D:\\passenger.txt";
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
                        return;
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
                            return;
                        } else {
                            System.out.println("An account with this name already exists. You will be redirected to the main menu.\n");
                            return;
                        }
                    }
                    break;
                }
            } catch (IOException e) {
                System.out.println(e + " has occurred.");
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
        System.out.println("Select account type:\n1. Admin\n2. Passenger");
        int accountType = getChoice();
        boolean flag;
        while (true) {
            flag = false;
            File file;
            try {
                if (accountType == 1) {
                    file = new File("D:\\admin.txt");
                } else if (accountType == 2) {
                    file = new File("D:\\passenger.txt");
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
                System.out.println(e + " has occurred.");
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
                System.out.println("\nAdmin Menu: ");
                System.out.println("1. Add a New Flight");
                System.out.println("2. View All Flights");
                System.out.println("3. Delete Flight");
                System.out.println("4. Delete Account");
                System.out.println("0. Log Out\n");
                break;
            case 2:
                System.out.println("\nPassenger Menu:");
                System.out.println("1. Search for Flights");
                System.out.println("2. Make a Reservation");
                System.out.println("3. View my Reservations");
                System.out.println("4. Delete Account");
                System.out.println("0. Log Out\n");
                break;
            case 3:
                System.out.println("\nGuest Menu:");
                System.out.println("1. Create Account");
                System.out.println("2. Search for Flights");
                System.out.println("3. View Flight Details");
                System.out.println("0. Log Out\n");
                break;
        }
    }

    public static void logOut() {
        System.out.println("You have logged out successfully. \n");

    }

    public static void deleteAccount(int accountType) {
        boolean flag = false;
        System.out.println("Enter name and password for confirmation:");
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
                    System.out.println("Wrong information. Deletion aborted. You will be redirected to the main menu.");
                    return;
                }
                FileOutputStream fos = new FileOutputStream(file);
                PrintStream ps = new PrintStream(fos);
                for (int i = 0; i < names.size(); i++) {
                    ps.println(names.get(i));
                    ps.println(passwords.get(i));
                }
                ps.close();
                System.out.println("Account has been deleted successfully.");
            } catch (IOException e) {
                System.out.println(e + " has occurred.");
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
                    addingFlight();
                    break;
                case 2:
                    viewFlightDetails();
                    break;
                case 3:
                    deletingFlight();
                    break;
                case 4:
                    deleteAccount(accountType);
                    return 0;
                default:
                    System.out.println("Invalid option. Please retry.\n");
            }
        }
    }

    public static int passengerMenu(int accountType) {
        while (true) {
            printAccountOptions(accountType);
            int passengerMenuChoice = getChoice();
            switch (passengerMenuChoice) {
                case 0:
                    logOut();
                    return 0;
                case 1:
                    searchingFlight();
                    break;
                case 2:
                    makeReservation();
                    break;
                case 3:
                    viewReservations();
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
        while (true) {
            printAccountOptions(accountType);
            int guestMenuChoice = getChoice();
            switch (guestMenuChoice) {
                case 0:
                    System.out.println("You have logged out successfully. Redirecting to the main menu.");
                    return 0;
                case 1:
                    createAccount();
                    return 0;
                case 2:
                    searchingFlight();
                    break;
                case 3:
                    viewFlightDetails();
                    break;
                default:
                    System.out.println("Invalid option. Please retry.\n");
            }
        }
    }

    public static void viewFlightDetails() {
        ArrayList<Integer> flightNumber = new ArrayList<>();
        ArrayList<String> departureTime = new ArrayList<>();
        ArrayList<String> departurePlace = new ArrayList<>();
        ArrayList<String> arrivalTime = new ArrayList<>();
        ArrayList<String> arrivalPlace = new ArrayList<>();
        ArrayList<Integer> seatNumbers = new ArrayList<>();
        try {
            File myFile = new File("D:\\FlightDetails.txt");
            Scanner reader = new Scanner(myFile);
            while (reader.hasNextLine()) {
                int flightNO = Integer.parseInt(reader.nextLine());
                String departTime = reader.nextLine();
                String departPlace = reader.nextLine();
                String arrTime = reader.nextLine();
                String arrPlace = reader.nextLine();
                int seatNum = Integer.parseInt(reader.nextLine());
                flightNumber.add(flightNO);
                departureTime.add(departTime);
                departurePlace.add(departPlace);
                arrivalPlace.add(arrPlace);
                arrivalTime.add(arrTime);
                seatNumbers.add(seatNum);
            }
            System.out.println("\nFlight Details: \n");
            for (int i = 0; i < flightNumber.size(); i++) {
                printFlightDetails(i, flightNumber, departureTime, departurePlace, arrivalTime, arrivalPlace, seatNumbers);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void printFlightDetails(int i, ArrayList<Integer> flightNumber, ArrayList<String> departureTime,
            ArrayList<String> departurePlace, ArrayList<String> arrivalTime,
            ArrayList<String> arrivalPlace, ArrayList<Integer> seatNumbers) {
        System.out.println("Flight number: " + flightNumber.get(i));
        System.out.println("Departure Time: " + departureTime.get(i));
        System.out.println("Arrival Time: \t" + arrivalTime.get(i));
        System.out.println("Departure From: " + departurePlace.get(i));
        System.out.println("Arrival Place:\t" + arrivalPlace.get(i));
        System.out.print("Available Seats: " + seatNumbers.get(i));
        System.out.println("\n");
    }

    public static void searchingFlight() {
        ArrayList<Integer> flightNumber = new ArrayList<>();
        ArrayList<String> departureTime = new ArrayList<>();
        ArrayList<String> departurePlace = new ArrayList<>();
        ArrayList<String> arrivalTime = new ArrayList<>();
        ArrayList<String> arrivalPlace = new ArrayList<>();
        ArrayList<Integer> seatNumbers = new ArrayList<>();
        try {
            File myFile = new File("D:\\FlightDetails.txt");
            Scanner reader = new Scanner(myFile);
            while (reader.hasNextLine()) {
                int flightNO = Integer.parseInt(reader.nextLine());
                String departTime = reader.nextLine();
                String departPlace = reader.nextLine();
                String arrTime = reader.nextLine();
                String arrPlace = reader.nextLine();
                int seatNum = Integer.parseInt(reader.nextLine());
                flightNumber.add(flightNO);
                departureTime.add(departTime);
                departurePlace.add(departPlace);
                arrivalPlace.add(arrPlace);
                arrivalTime.add(arrTime);
                seatNumbers.add(seatNum);
            }
            reader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("\nSearching for a flight: \n");
        System.out.println("1. Search by Departure Time");
        System.out.println("2. Search by Departure Place");
        System.out.println("3. Search by Arrival Time");
        System.out.println("3. Search by Arrival Place");
        System.out.println("0. Exit");
        int choice = getChoice();
        switch (choice) {
            case 1:
                while (true) {
                    System.out.print("Enter Departure Time: ");
                    String departTime = input.nextLine();
                    boolean found = false;
                    for (int i = 0; i < departureTime.size(); i++) {
                        if (departTime.equals(departureTime.get(i))) {
                            System.out.println("\nFlight Details: \n");
                            printFlightDetails(i, flightNumber, departureTime, departurePlace, arrivalTime, arrivalPlace, seatNumbers);
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                        break;
                    } else {
                        System.out.println("No flight found for the given departure time. You will be redirected to the menu.");
                        break;
                    }
                }
                break;

            case 2:
                while (true) {
                    System.out.print("Enter Departure Place: ");
                    String departPlace = input.nextLine();
                    boolean found = false;
                    for (int i = 0; i < departurePlace.size(); i++) {
                        if (departPlace.equals(departurePlace.get(i))) {
                            System.out.println("\nFlight Details: \n");
                            printFlightDetails(i, flightNumber, departureTime, departurePlace, arrivalTime, arrivalPlace, seatNumbers);
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                        break;
                    } else {
                        System.out.println("No flight found for the given departure Place. You will be redirected to the menu.");
                        break;
                    }
                }
                break;
            case 3:
                while (true) {
                    System.out.print("Enter Arrival Time: ");
                    String arrTime = input.nextLine();
                    boolean found = false;
                    for (int i = 0; i < departureTime.size(); i++) {
                        if (arrTime.equals(arrivalTime.get(i))) {
                            System.out.println("\nFlight Details: \n");
                            printFlightDetails(i, flightNumber, departureTime, departurePlace, arrivalTime, arrivalPlace, seatNumbers);
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                        break;
                    } else {
                        System.out.println("No flight found for the given arrival time. You will be redirected to the menu.");
                        break;
                    }
                }
                break;
            case 4:
                while (true) {
                    System.out.print("Enter Arrival Place: ");
                    String arrPlace = input.nextLine();
                    boolean found = false;
                    for (int i = 0; i < arrivalPlace.size(); i++) {
                        if (arrPlace.equals(arrivalPlace.get(i))) {
                            System.out.println("\nFlight Details: \n");
                            printFlightDetails(i, flightNumber, departureTime, departurePlace, arrivalTime, arrivalPlace, seatNumbers);
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                        break;
                    } else {
                        System.out.println("No flight found for the given arrival Place. You will be redirected to the menu.");
                        break;
                    }
                }
                break;
            default:
                System.out.println("You entered invalid choice");
        }
    }

    public static void addingFlight() {
        System.out.print("Enter number of flights you want to add: ");
        int n = input.nextInt();
        for (int i = 1; i <= n; i++) {
            System.out.print("Enter Flight number for flight# " + i + ": ");
            int flightNo = input.nextInt();
            System.out.print("Enter Departure time: ");
            String departTime = input.next();
            System.out.print("Enter Departure Place: ");
            String departPlace = input.next();
            System.out.print("Enter arrival time: ");
            String arrTime = input.next();
            System.out.print("Enter arrival place: ");
            String arrPlace = input.next();
            System.out.print("Enter number of seats: ");
            int numOfSeats = input.nextInt();
            System.out.println("\n");
            try (FileWriter myFile = new FileWriter("D:\\FlightDetails.txt", true); PrintWriter printWriter = new PrintWriter(myFile)) {
                printWriter.println(flightNo);
                printWriter.println(departTime);
                printWriter.println(departPlace);
                printWriter.println(arrTime);
                printWriter.println(arrPlace);
                printWriter.println(numOfSeats);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Flight successfully added.");

        }
    }

    public static void deletingFlight() {
        ArrayList<Integer> flightNumber = new ArrayList<>();
        ArrayList<String> departureTime = new ArrayList<>();
        ArrayList<String> departurePlace = new ArrayList<>();
        ArrayList<String> arrivalTime = new ArrayList<>();
        ArrayList<String> arrivalPlace = new ArrayList<>();
        ArrayList<Integer> numOfSeats = new ArrayList<>();
        try {
            File myFile = new File("D:\\FlightDetails.txt");
            Scanner reader = new Scanner(myFile);
            while (reader.hasNextLine()) {
                int flightNO = Integer.parseInt(reader.nextLine());
                int seats = 0;
                String departTime = reader.nextLine();
                String departPlace = reader.nextLine();
                String arrTime = reader.nextLine();
                String arrPlace = reader.nextLine();
                for (int i = 0; i < 3; i++) {
                    seats = Integer.parseInt(reader.nextLine());
                    numOfSeats.add(seats);
                }
                flightNumber.add(flightNO);
                departureTime.add(departTime);
                departurePlace.add(departPlace);
                arrivalPlace.add(arrPlace);
                arrivalTime.add(arrTime);
                numOfSeats.add(seats);
            }
            reader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        while (true) {
            System.out.print("Enter Flight number: ");
            int flightNo = input.nextInt();
            boolean found = false;
            for (int i = 0; i < flightNumber.size(); i++) {
                if (flightNo == flightNumber.get(i)) {
                    flightNumber.remove(i);
                    departureTime.remove(i);
                    departurePlace.remove(i);
                    arrivalTime.remove(i);
                    arrivalPlace.remove(i);
                    numOfSeats.remove(i);
                    found = true;
                    break;
                }
            }
            if (found) {
                break;
            } else {
                System.out.println("No flight found for entered flight number. Try again");
            }
        }

        try (FileWriter myFile = new FileWriter("D:\\FlightDetails.txt"); PrintWriter printWriter = new PrintWriter(myFile)) {
            for (int j = 0; j < flightNumber.size(); j++) {
                printWriter.println(flightNumber.get(j));
                printWriter.println(departureTime.get(j));
                printWriter.println(departurePlace.get(j));
                printWriter.println(arrivalTime.get(j));
                printWriter.println(arrivalPlace.get(j));
                printWriter.println(numOfSeats.get(j));
            }
            System.out.println("Flight successfully deleted.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void makeReservation() {
        System.out.println("\nRESERVATION PORTAL\n");
        boolean flightFound = false;
        int flightNum;
        ArrayList<Integer> flightNumber = new ArrayList<>();
        ArrayList<String> departureTime = new ArrayList<>();
        ArrayList<String> departurePlace = new ArrayList<>();
        ArrayList<String> arrivalTime = new ArrayList<>();
        ArrayList<String> arrivalPlace = new ArrayList<>();
        ArrayList<Integer> availableSeats = new ArrayList<>();
        try {
            File myFile = new File("D:\\FlightDetails.txt");
            Scanner reader = new Scanner(myFile);
            while (reader.hasNextLine()) {
                int flightNo = Integer.parseInt(reader.nextLine());
                String departTime = reader.nextLine();
                String departPlace = reader.nextLine();
                String arrTime = reader.nextLine();
                String arrPlace = reader.nextLine();
                int numSeats = Integer.parseInt(reader.nextLine());

                availableSeats.add(numSeats);
                flightNumber.add(flightNo);
                departureTime.add(departTime);
                departurePlace.add(departPlace);
                arrivalPlace.add(arrPlace);
                arrivalTime.add(arrTime);
            }
            reader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        while (true) {
            try {
                System.out.print("Enter flight number: ");
                flightNum = input.nextInt();
                if (flightNum <= 0) {
                    System.out.println("Invalid flight number. Please retry.");
                    continue;
                }
                input.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println(e + " has occurred. Please retry.");
            }
        }
        while (true) {
            for (int i = 0; i < flightNumber.size(); i++) {
                if (flightNum == flightNumber.get(i)) {
                    flightFound = true;

                    if (availableSeats.get(i) == 0) {
                        System.out.println("This flight has been fully reserved. You will be redirected to the menu.");
                        return;
                    }
                    System.out.print("Enter passenger name: ");
                    String name = input.nextLine();

                    System.out.println("\nPLANE TICKET PURCHASE: \n");
                    System.out.println("Name: " + name);
                    System.out.println("Flight Number: " + flightNumber.get(i));
                    System.out.println("Departure Time: " + departureTime.get(i));
                    System.out.println("Arrival Time: " + arrivalTime.get(i));
                    System.out.println("Departure Place: " + departurePlace.get(i));
                    System.out.println("Arrival Place: " + arrivalPlace.get(i));
                    if (!paymentMethod()) {
                        return;
                    }
                    System.out.print("\nPress Y to confirm your reservation or N to cancel: ");
                    char choice = input.next().toUpperCase().charAt(0);
                    if (choice == 'Y') {
                        System.out.println("\nYour reservation has been made successfully.\n");
                        int remainingSeats = availableSeats.get(i) - 1;
                        availableSeats.set(i, remainingSeats);

                        try (FileWriter fileWriter = new FileWriter("D:\\reservations.txt", true); PrintWriter printWriter = new PrintWriter(fileWriter)) {
                            printWriter.println(name);
                            printWriter.println(flightNumber.get(i));
                            printWriter.println(departureTime.get(i));
                            printWriter.println(departurePlace.get(i));
                            printWriter.println(arrivalTime.get(i));
                            printWriter.println(arrivalPlace.get(i));
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        try (FileWriter myFile = new FileWriter("D:\\FlightDetails.txt"); PrintWriter printWriter = new PrintWriter(myFile)) {
                            for (int j = 0; j < flightNumber.size(); j++) {
                                printWriter.println(flightNumber.get(j));
                                printWriter.println(departureTime.get(j));
                                printWriter.println(departurePlace.get(j));
                                printWriter.println(arrivalTime.get(j));
                                printWriter.println(arrivalPlace.get(j));
                                printWriter.println(availableSeats.get(j));
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    } else if (choice == 'N') {
                        System.out.println("\nReservation has been cancelled. You will be redirected to the menu.\n");
                        break;
                    } else {
                        System.out.println("\nInvalid input. You will be redirected to the menu.\n");
                        break;
                    }
                }
            }
            if (!flightFound) {
                System.out.println("\nFlight number " + flightNum + " does not exist. Try again.");
            } else {
                return;
            }
        }
    }

    public static boolean duplicateReservation(String name, int flightNumber) {
        try {
            File myFile = new File("D:\\reservations.txt");
            Scanner reader = new Scanner(myFile);
            while (reader.hasNextLine()) {
                String nameToCheck = reader.nextLine();
                int flightNoToCheck = Integer.parseInt(reader.nextLine());
                if (name.equals(nameToCheck) && flightNumber == flightNoToCheck) {
                    return true;
                }
                for (int i = 0; i < 4; i++) {
                    reader.nextLine();
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        return false;
    }

    public static void viewReservations() {
        int reservationCount = 1;
        boolean nameFound = false;
        System.out.print("Enter name to check your reservation: ");
        String nameToCheck = input.nextLine();
        try {
            File myFile = new File("D:\\reservations.txt");
            Scanner reader = new Scanner(myFile);
            while (reader.hasNextLine()) {
                String name = reader.nextLine();
                if (nameToCheck.equalsIgnoreCase(name)) {
                    nameFound = true;
                    int flightNo = Integer.parseInt(reader.nextLine());
                    String departTime = reader.nextLine();
                    String departPlace = reader.nextLine();
                    String arrTime = reader.nextLine();
                    String arrPlace = reader.nextLine();
                    System.out.println("\nReservation " + reservationCount + ":\n");
                    System.out.println("Name: " + name);
                    System.out.println("Flight Number: " + flightNo);
                    System.out.println("Departure Time: " + departTime);
                    System.out.println("Departure Place: " + departPlace);
                    System.out.println("Arrival Time: " + arrTime);
                    System.out.println("Arrival Place: " + arrPlace);
                    reservationCount++;
                } else {
                    for (int i = 0; i < 5; i++) {
                        reader.nextLine();
                    }
                }
            }
            System.out.println();
            reader.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        if (!nameFound) {
            System.out.println("No reservations under the name " + nameToCheck + " have been made. You will be redirected to the menu.\n");
        }
    }

    public static boolean paymentMethod() {
        while (true) {
            System.out.println("\nSelect Payment Method: ");
            System.out.println("1. Visa\n2. Mastercard\n3. American Express\n4. Discover\n0. Cancel process\n");
            int choice = getChoice();
            switch (choice) {
                case 0:
                    System.out.println("No payment method added. Reservation has been cancelled. You will be redirected to the menu. ");
                    return false;
                case 1:
                    System.out.print("Enter Visa card number: ");
                    String visaCard = input.nextLine();
                    if (visaCard.charAt(0) == '4' && (visaCard.length() == 13 || visaCard.length() == 16)) {
                        System.out.println("Your card number is valid and payment method has been added.");
                        return true;
                    }
                    System.out.println("Your card number is invalid. Please retry.");
                    break;
                case 2:
                    System.out.print("Enter Mastercard number: ");
                    String masterCard = input.nextLine();
                    if (masterCard.charAt(0) == '5' && masterCard.length() == 16) {
                        System.out.println("Your card number is valid and payment method has been added.");
                        return true;
                    }
                    System.out.println("Your card number is invalid. Please retry.");
                    break;
                case 3:
                    System.out.print("Enter American Express card number: ");
                    String americanExpressCard = input.nextLine();
                    if (americanExpressCard.charAt(0) == '3' && (americanExpressCard.charAt(1) == '4' || americanExpressCard.charAt(0) == '7') && americanExpressCard.length() == 15) {
                        System.out.println("Your card number is valid and payment method has been added.");
                        return true;
                    }
                    System.out.println("Your card number is invalid. Please retry.");
                    break;
                case 4:
                    System.out.print("Enter Discover card number: ");
                    String discoverCard = input.nextLine();
                    if (discoverCard.charAt(0) == '6' && discoverCard.length() == 16) {
                        System.out.println("Your card number is valid and payment method has been added.");
                        return true;
                    }
                    System.out.println("Your card number is invalid. Please retry.");
                    break;
                default:
                    System.out.println("Invalid choice. Please retry.");
            }
        }
    }
}
