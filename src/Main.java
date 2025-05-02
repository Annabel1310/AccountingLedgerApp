import java.io.*;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void homeScreen() {
        System.out.println("""
                WELCOME TO HOME SCREEN
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
                    Ledger Menu:
                    A - Show All Entries
                    D - Show Deposits only
                    P - Show Payments only
                    R - Show Reports
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
    public static ArrayList<Transaction> getAllTransactions() {


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
        return transactions;
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
                    6) Go Back To Ledger Screen
                    """);
            System.out.print("Enter your choice: ");
            String Reportsection = scanner.nextLine().toUpperCase();

            switch (Reportsection) {
                case "1":
                    LocalDate today = LocalDate.now();
                    String yearMonth = today.toString().substring(0, 7);
                    ArrayList<Transaction> transactions = getAllTransactions();
                    System.out.println("Showing Month To Date!");
                    for (Transaction t : transactions){
                        if (t.getDate().startsWith(yearMonth)){
                            System.out.println(t);
                        }
                    }
                    break;
                case "2":
                    today = LocalDate.now();
                    LocalDate prevMonth = today.minusMonths(1);
                    String prevYearMonth = prevMonth.toString().substring(0, 7);
                    transactions = getAllTransactions();
                    System.out.println("Showing Previous Month!");
                    for (Transaction t : transactions) {
                        if (t.getDate().startsWith(prevYearMonth)) {
                            System.out.println(t);
                        }
                    }
                    break;
                case "3":
                    today = LocalDate.now();
                    String currentYear = today.toString().substring(0, 4);
                    transactions = getAllTransactions();
                    System.out.println(" Showing Year To Date!");
                    for (Transaction t : transactions) {
                        if (t.getDate().startsWith(currentYear)) {
                            System.out.println(t);
                        }
                    }
                    break;
                case "4":
                    today = LocalDate.now();
                    int previousYear = Integer.parseInt(today.toString().substring(0, 4)) - 1;
                    String prevYearStr = String.valueOf(previousYear);
                    transactions = getAllTransactions();
                    System.out.println("Showing Previous Year!");
                    for (Transaction t : transactions) {
                        if (t.getDate().startsWith(prevYearStr)) {
                            System.out.println(t);
                        }
                    }
                    break;
                case "5":
                    System.out.println("Search by Vendor!");
                    String vendor = scanner.nextLine();
                    transactions = getAllTransactions();
                    System.out.println("Entries for  the vendor: "+ vendor);
                    for (Transaction t : transactions) {
                        if (t.getVendor().equalsIgnoreCase(vendor)) {
                            System.out.println(t);
                        }
                    }
                case "0":
                    running = false;

                scanner.nextLine();
                default:
                    System.out.println("Invalid Option. Try Again");

            }
        }

                }

            }



















