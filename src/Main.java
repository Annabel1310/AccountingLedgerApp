import java.io.*;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void homeScreen() {
        System.out.println("""
                Welcome to the home screen
                Enter an option to proceed:
                D - Add Deposit
                P - Make Payment
                L - Ledger
                X - Exit
                """);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            homeScreen();
            System.out.print("Enter your option: ");
            String option = scanner.nextLine().toUpperCase();

            switch (option) {
                case "D":
                    System.out.println("Option to add Deposit!");
                    writeTransaction("D");
                    break;
                case "P":
                    System.out.println("Option to add Payment!");
                    writeTransaction("P");
                    break;
                case "L":
                    System.out.println("Option to Display Ledger");
                    LedgerScreen();
                    break;
                case "X":
                    System.out.println("Option to Exit");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid Option. Try Again");
            }

        }
    }

    // When the user selects "D", we want to get the deposit info from the user then write to transactions.csv
    public static void writeTransaction(String option) {
        // get deposit info from the user
        Scanner scanner = new Scanner(System.in);
        // date|time|description|vendor|amount
        System.out.print("Enter date: ");
        String date = scanner.nextLine();
        System.out.print("Enter time: ");
        String time = scanner.nextLine();
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter vendor: ");
        String vendor = scanner.nextLine();

        System.out.print("Enter amount: ");
        double amount = Double.parseDouble(scanner.nextLine());
        // if it is a payment, make the amount negative
        if (option.equals("P")) {
            amount = -1 * amount;
        }

        String entry = date + "|" + time + "|" + description + "|" + vendor + "|" + amount;
        System.out.println(entry);

        //file writer
        // we have to import needed tools( import java.io)

        // we then open the file.
        try (FileWriter writer = new FileWriter("transactions.csv", true)) {
            //write the file
            writer.write(entry + "\n");
            System.out.println("Deposit added successfully!");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }

    }

    // ledger method
    public static void LedgerScreen() {
        Scanner scanner = new Scanner(System.in);
        boolean inLedgerMenu = true;
        while (inLedgerMenu) {
            System.out.println("""
                     ledger menu:
                    A - show all entries
                     D - show Deposits only
                    P - show Payments only
                     R - show Reports
                    H - Home
                    """);
            System.out.print("Enter your choice: ");
            String ledgerChoice = scanner.nextLine().toUpperCase();

            switch (ledgerChoice) {
                case "A":
                    System.out.println("show all entries!");
                    showAllTransactions();
                    break;
                case "D":
                    System.out.println("show deposits only!");
                    showDepositsonly();
                    break;
                case "P":
                    System.out.println("show payments only!");
                    showPaymentsonly();
                    break;
                case "R":
                    System.out.println("show Reports!");
                    ReportMenu();
                    break;
                case "H":
                    System.out.println("Home!");
                    inLedgerMenu = false;
                    break;
                default:
                    System.out.println("Invalid Option. Try Again");
            }
        }

    }

    public static ArrayList<Transaction> transactions = new ArrayList<>();

    // this method reads from the file and splits it, trims and then adds it to the ArrayList called transactions
    public static void getAllTransactions() {


        try (Scanner reader = new Scanner(new File("transactions.csv"))) {

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] fields = line.split("\\|");
                String date = fields[0].trim();
                String time = fields[1].trim();
                String description = fields[2].trim();
                String vendor = fields[3].trim();
                double amount = Double.parseDouble(fields[4].trim());
                transactions.add(new Transaction(date, time, description, vendor, amount));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void showAllTransactions() {
        getAllTransactions();
        for (Transaction transaction : transactions) {
            System.out.println(transaction.getDate() + "|" + transaction.getTime() + "|" + transaction.getDescription() + "|" + transaction.getVendor() + "|" + transaction.getAmount());


        }
    }

    public static void showDepositsonly() {
        getAllTransactions();
        for (Transaction transaction : transactions) {
            if (transaction.getAmount() > 0) {

                System.out.println(transaction.getDate() + "|" + transaction.getTime() + "|" + transaction.getDescription() + "|" + transaction.getVendor() + "|" + transaction.getAmount());
            }

        }
    }

    public static void showPaymentsonly() {
        getAllTransactions();
        for (Transaction transaction : transactions) {
            if (transaction.getAmount() < 0) {
                System.out.println(transaction.getDate() + "|" + transaction.getTime() + "|" + transaction.getDescription() + "|" + transaction.getVendor() + "|" + transaction.getAmount());
            }

        }
    }

    public static void ReportMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("""
                     Report menu:
                    1) Month To Date
                    2) Previous Month
                    3) Year To Date
                    4) Previous Year
                    5) Search by Vendor
                    """);
            System.out.print("Enter your choice: ");
            String Reportsection = scanner.nextLine().toUpperCase();

            switch (Reportsection) {
                case "1":
                    System.out.println("Showing Month To Date!");
                    //TODO: Add Month to Date logic
                    break;
                case "2":
                    System.out.println("Showing Previous Month!");
                    //TODO: Add previous Month logic
                    break;
                case "3":
                    System.out.println(" Showing Year To Date!");
                    //TODO: Add year to date logic
                    break;
                case "4":
                    System.out.println("Showing Previous Year!");
                    //TODO: Add previous year logic
                    break;
                case "5":
                    System.out.println("Search by Vendor!");
                    String vendor = scanner.nextLine();
                    System.out.println("searching for vendor");
                    //TODO:  Add search by Vendor logic
                case "0":
                    running = false;

                default:
                    System.out.println("Invalid Option. Try Again");

            }
        }

                }

            }



















