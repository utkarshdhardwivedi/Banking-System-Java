package bankFunctions;
import User.Account;
import Custom_Exceptions.AccountLimitExceededException;
import Custom_Exceptions.DuplicatePhoneNumberException;
import Custom_Exceptions.InsufficientBalanceException;
import Custom_Exceptions.InvalidAccountNumberException;
import Custom_Exceptions.InvalidNationalityException;
import Custom_Exceptions.OccupationRestrictionException;

public class Bank {
    private Account[] accounts;
    public int accountNumberCounter;
    private final double least_balance;

    public Bank(int maxAccounts, double least_balance) {
        accounts = new Account[maxAccounts];
        accountNumberCounter = 1;
        this.least_balance = least_balance;
    }

    public void createAccount(String accountHolder, double initialBalance, int age, String nationality, String occupation, double salary, int creditScore, String address, String Gender, String phoneNo, double workExpYear) throws AccountLimitExceededException, InsufficientBalanceException, DuplicatePhoneNumberException, InvalidNationalityException, OccupationRestrictionException {
        if (accountNumberCounter >= accounts.length) {
            throw new AccountLimitExceededException("Maximum number of accounts reached.");
        }
        if (initialBalance < least_balance) {
            throw new InsufficientBalanceException("Initial balance must be at least " + least_balance);
        }
        if (isPhoneNumberUsed(phoneNo)) {
            throw new DuplicatePhoneNumberException("Phone number " + phoneNo + " is already associated with another account.");
        }
        if (!isValidNationality(nationality)) {
            throw new InvalidNationalityException("Nationality must be a valid country name (alphabets only).");
        }
        if (!isValidOccupation(occupation)) {
            throw new OccupationRestrictionException("Occupation must be a recognized profession (alphabets only).");
        }
        Account account = new Account(accountNumberCounter, accountHolder, initialBalance, age, nationality, occupation, salary, creditScore, address, Gender, phoneNo, workExpYear);
        accounts[accountNumberCounter] = account;
        System.out.println("Account created with account number: " + accountNumberCounter);
        accountNumberCounter++;
    }

    private boolean isPhoneNumberUsed(String phoneNo) {
        for (int i = 1; i < accountNumberCounter; i++) {
            if (accounts[i] != null && accounts[i].getPhoneNo().equals(phoneNo)) {
                return true;
            }
        }
        return false;
    }

    private boolean isValidNationality(String nationality) {
        return nationality.matches("[a-zA-Z]+");
    }

    private boolean isValidOccupation(String occupation) {
        return occupation.matches("[a-zA-Z]+");
    }

    public void deposit(int accountNumber, double... amounts) throws InvalidAccountNumberException {
        if (!ValidAccNum(accountNumber)) {
            throw new InvalidAccountNumberException("Account number " + accountNumber + " does not exist.");
        }
        Account account = accounts[accountNumber];
        for (double amount : amounts) {
            account.deposit(amount);
            System.out.println("Deposited " + amount + " into account " + accountNumber);
        }
    }

    public void withdraw(int accountNumber, double amount) throws InvalidAccountNumberException, InsufficientBalanceException {
        if (!ValidAccNum(accountNumber)) {
            throw new InvalidAccountNumberException("Account number " + accountNumber + " does not exist.");
        }
        Account account = accounts[accountNumber];
        if (account.withdraw(amount)) {
            System.out.println("Withdrawn " + amount + " from account " + accountNumber);
        } else {
            throw new InsufficientBalanceException("Insufficient balance for withdrawal.");
        }
    }

    public void checkBalance(int accountNumber) throws InvalidAccountNumberException {
        if (!ValidAccNum(accountNumber)) {
            throw new InvalidAccountNumberException("Account number " + accountNumber + " does not exist.");
        }
        Account account = accounts[accountNumber];
        System.out.println("Account " + accountNumber + " balance: " + account.getBalance());
    }

    public boolean ValidAccNum(int accountNumber) {
        return accountNumber >= 0 && accountNumber < accountNumberCounter;
    }

    public Account getAccount(int accountNumber) throws InvalidAccountNumberException {
        if (!ValidAccNum(accountNumber)) {
            throw new InvalidAccountNumberException("Account number " + accountNumber + " does not exist.");
        }
        return accounts[accountNumber];
    }

    public int check_age(int accountNumber) throws InvalidAccountNumberException {
        Account account = getAccount(accountNumber);
        return account.getAge();
    }

    public int check_CS(int accountNumber) throws InvalidAccountNumberException {
        Account account = getAccount(accountNumber);
        return account.getCreditScore();
    }

    public String check_nationality(int accountNumber) throws InvalidAccountNumberException {
        Account account = getAccount(accountNumber);
        return account.getNationality();
    }
}