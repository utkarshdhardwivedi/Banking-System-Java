package MainApp;
import java.sql.Connection;
import java.sql.Statement;
import Custom_Exceptions.AccountLimitExceededException;
import Custom_Exceptions.AgeRestrictionException;
import Custom_Exceptions.DatabaseConnectionTimeoutException;
import Custom_Exceptions.DatabaseOperationException;
import Custom_Exceptions.DataIntegrityException;
import Custom_Exceptions.DuplicatePhoneNumberException;
import Custom_Exceptions.InsufficientBalanceException;
import Custom_Exceptions.InvalidAccountNumberException;
import Custom_Exceptions.InvalidInputException;
import Custom_Exceptions.InvalidLoanAmountException;
import Custom_Exceptions.InvalidLoanTermException;
import Custom_Exceptions.InvalidNationalityException;
import Custom_Exceptions.LoanEligibilityException;
import Custom_Exceptions.LowCreditScoreException;
import Custom_Exceptions.OccupationRestrictionException;
import FileHandling_Loan.LoanApplication;
import User.Account;
import bankFunctions.Bank;
import bankFunctions.HomeLoan;
import bankFunctions.Loan;
import bankFunctions.PersonalLoan;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.IOException;
import JDBC_Connection.JDBC_Database;

public class BankingSystem {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java MainApp.BankingSystem <maxAccounts> <least_balance>");
            System.exit(1);
        }

        int maxAccounts = Integer.parseInt(args[0]);
        double least_balance = Double.parseDouble(args[1]);

        Bank bank = new Bank(maxAccounts, least_balance);
        Scanner scanner = new Scanner(System.in);

        try {
            JDBC_Database.loadDriver();
            Connection con = JDBC_Database.createConnection();
            Statement stmt = JDBC_Database.createStatement();
            JDBC_Database.createTable_accounts();
        } catch (DatabaseOperationException | DatabaseConnectionTimeoutException e) {
            System.out.println("Database error: " + e.getMessage());
            System.exit(1);
        }

        int accountNumber = -1;
        int choice;
        double loanAmount;
        int loanTerm;
        int loanType;

        while (true) {
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Check Balance");
            System.out.println("5. Display record of all users");
            System.out.println("6. Display record for a particular user");
            System.out.println("7. Apply for Loan");
            System.out.println("8. Display Loan Application of a particular user");
            System.out.println("9. Exit");
            try {
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer choice.");
                scanner.next();
                continue;
            }

            switch (choice) {
                case 1:
                    try {
                        System.out.print("Enter account holder's name: ");
                        String accountHolder = scanner.next();
                        if (!accountHolder.matches("[a-zA-Z]+")) {
                            throw new InvalidInputException("Invalid Name. Please enter only alphabets.");
                        }
                        System.out.print("Enter account holder's age: ");
                        int age = Integer.parseInt(scanner.next());
                        if (age < 18 || age > 70) {
                            throw new AgeRestrictionException("Age must be between 18 and 70.");
                        }
                        System.out.print("Enter account holder's nationality: ");
                        String nationality = scanner.next();
                        System.out.print("Enter account holder's gender(M/F/O): ");
                        String Gender = scanner.next().toUpperCase();
                        if (!Gender.matches("[MFO]")) {
                            throw new InvalidInputException("Invalid Gender. Please enter 'M' for Male, 'F' for Female, or 'O' for Other.");
                        }
                        System.out.print("Enter account holder's occupation: ");
                        String occupation = scanner.next();
                        System.out.print("Enter account holder's credit score: ");
                        int creditScore = Integer.parseInt(scanner.next());
                        if (creditScore < 300 || creditScore > 900) {
                            throw new LowCreditScoreException("Credit score must be between 300 and 900.");
                        }
                        System.out.print("Enter account holder's phone number: ");
                        String phoneNo = scanner.next();
                        if (!phoneNo.matches("\\d{10}") || phoneNo.charAt(0) == '0') {
                            throw new InvalidInputException("Invalid Phone Number. Must be 10 digits and not start with 0.");
                        }
                        System.out.print("Enter account holder's address: ");
                        String address = scanner.next();
                        scanner.nextLine();
                        System.out.print("Enter account holder's salary (per month): ");
                        double salary = Double.parseDouble(scanner.nextLine());
                        System.out.print("Enter account holder's work experience (in years): ");
                        double workExpYear = Double.parseDouble(scanner.nextLine());
                        System.out.print("Enter initial balance: ");
                        double initialBalance = Double.parseDouble(scanner.nextLine());

                        bank.createAccount(accountHolder, initialBalance, age, nationality, occupation, salary, creditScore, address, Gender, phoneNo, workExpYear);
                        JDBC_Database.insertData_accounts(accountHolder, initialBalance, age, nationality, occupation, salary, creditScore, address, Gender, phoneNo, workExpYear);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input: Enter a number.");
                    } catch (InvalidInputException | InsufficientBalanceException | AccountLimitExceededException | DuplicatePhoneNumberException | InvalidNationalityException | OccupationRestrictionException | AgeRestrictionException | LowCreditScoreException | DatabaseOperationException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 2:
                    try {
                        System.out.print("Enter account number: ");
                        accountNumber = scanner.nextInt();
                        while (true) {
                            System.out.print("Enter deposit amount (or -1 to finish): ");
                            double depositAmount = scanner.nextDouble();
                            if (depositAmount == -1) {
                                break;
                            }
                            bank.deposit(accountNumber, depositAmount);
                            JDBC_Database.updateBalanceAfterDeposit(accountNumber, depositAmount);
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                        scanner.next();
                    } catch (InvalidAccountNumberException | DatabaseOperationException | DataIntegrityException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 3:
                    try {
                        System.out.print("Enter account number: ");
                        accountNumber = scanner.nextInt();
                        System.out.print("Enter withdrawal amount: ");
                        double withdrawalAmount = scanner.nextDouble();
                        bank.withdraw(accountNumber, withdrawalAmount);
                        JDBC_Database.updateBalanceAfterWithdrawal(accountNumber, withdrawalAmount);
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                        scanner.next();
                    } catch (InvalidAccountNumberException | InsufficientBalanceException | DatabaseOperationException | DataIntegrityException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 4:
                    try {
                        System.out.print("Enter account number: ");
                        accountNumber = scanner.nextInt();
                        bank.checkBalance(accountNumber);
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid integer.");
                        scanner.next();
                    } catch (InvalidAccountNumberException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 5:
                    try {
                        JDBC_Database.extractData_AllUsers();
                    } catch (DatabaseOperationException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 6:
                    try {
                        System.out.print("Enter account number: ");
                        accountNumber = scanner.nextInt();
                        JDBC_Database.extractData_OneUser(accountNumber);
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid integer.");
                        scanner.next();
                    } catch (DatabaseOperationException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 7:
                    try {
                        System.out.print("Enter account number: ");
                        accountNumber = scanner.nextInt();
                        bank.getAccount(accountNumber); // Validate account number
                        System.out.print("Enter loan amount: ");
                        loanAmount = scanner.nextDouble();
                        System.out.print("Enter loan term (in months): ");
                        loanTerm = scanner.nextInt();
                        System.out.print("Enter loan type (1 for Personal Loan, 2 for Home Loan): ");
                        loanType = scanner.nextInt();

                        Loan loan = (loanType == 1) ? new PersonalLoan() : (loanType == 2) ? new HomeLoan() : null;
                        if (loan == null) {
                            throw new InvalidInputException("Invalid loan type.");
                        }

                        Account account = bank.getAccount(accountNumber);
                        loan.checkEligibility(account, loanAmount);
                        double monthlyInstallment = loan.calcMonthlyInstallments(loanAmount, loanTerm);
                        System.out.println("You are eligible for the loan.\nThe Monthly installment will be Rs. " + monthlyInstallment);
                        System.out.println("Do you want to apply for it (y/n):");
                        String applyForLoan = scanner.next();
                        if (applyForLoan.equalsIgnoreCase("y")) {
                            try {
                                LoanApplication fileHandler = new LoanApplication();
                                fileHandler.createFile("LoanApplication_" + accountNumber + ".txt");
                                String fileContent =
                                        "Account Holder's Name: " + account.getAccountHolder() + "\n" +
                                        "Account Number: " + accountNumber + "\n" +
                                        "Phone Number: " + account.getPhoneNo() + "\n" +
                                        "Credit Score: " + account.getCreditScore() + "\n" +
                                        "Income: " + account.getSalary() + "\n" +
                                        "Loan Amount: Rs. " + loanAmount + "\n" +
                                        "Loan Term: " + loanTerm + " months\n" +
                                        "Loan Type: " + (loanType == 1 ? "Personal Loan" : "Home Loan") + "\n" +
                                        "---------------------------------------";
                                fileHandler.writeFile(fileContent);
                                System.out.println("Loan application details saved to file.");
                            } catch (IOException e) {
                                System.out.println("Error writing to the file: " + e.getMessage());
                            }
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input: Enter a valid number.");
                        scanner.next();
                    } catch (InvalidInputException | InvalidAccountNumberException | InvalidLoanAmountException | InvalidLoanTermException | LowCreditScoreException | AgeRestrictionException | LoanEligibilityException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 8:
                    try {
                        System.out.print("Enter account number: ");
                        accountNumber = scanner.nextInt();
                        System.out.println("Loan Application for account number " + accountNumber);
                        LoanApplication loanApplication = new LoanApplication();
                        loanApplication.file = new File("C:/Users/RAJAT/OneDrive/Desktop/Loan_Applications" + accountNumber + ".txt");
                        loanApplication.readFile();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input: Enter a number.");
                        scanner.next();
                    } catch (FileNotFoundException e) {
                        System.out.println("Loan application file not found.");
                    } catch (IOException e) {
                        System.out.println("Error reading the loan application file: " + e.getMessage());
                    }
                    break;

                case 9:
                    try {
                        JDBC_Database.closeResources();
                        System.out.println("Exiting the banking system.");
                        System.exit(0);
                    } catch (DatabaseOperationException e) {
                        System.out.println("Error closing database resources: " + e.getMessage());
                        System.exit(1);
                    }
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}