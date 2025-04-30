package JDBC_Connection;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import Custom_Exceptions.DatabaseConnectionTimeoutException;
import Custom_Exceptions.DatabaseOperationException;
import Custom_Exceptions.DataIntegrityException;

public class JDBC_Database {
    public static Connection con;
    public static Statement stmt;
    public static PreparedStatement pstmt;
    public static final String DB_URL = "jdbc:mysql://localhost:3306/BankingSystem";
    public static final String USER = "root";
    public static final String PWD = "Rajat@9451";

    public static void loadDriver() throws DatabaseOperationException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver Loaded Successfully.");
        } catch (ClassNotFoundException e) {
            throw new DatabaseOperationException("Failed to load JDBC driver: " + e.getMessage());
        }
    }

    public static Connection createConnection() throws DatabaseOperationException, DatabaseConnectionTimeoutException {
        try {
            con = DriverManager.getConnection(DB_URL, USER, PWD);
            System.out.println("Connection Established.");
            System.out.println("Connected to: " + con.getCatalog());
        } catch (SQLException e) {
            if (e.getSQLState().startsWith("08")) {
                throw new DatabaseConnectionTimeoutException("Database connection timed out: " + e.getMessage());
            }
            throw new DatabaseOperationException("Failed to establish database connection: " + e.getMessage());
        }
        return con;
    }

    public static Statement createStatement() throws DatabaseOperationException {
        try {
            stmt = con.createStatement();
            System.out.println("Statement created.");
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to create statement: " + e.getMessage());
        }
        return stmt;
    }

    public static void createTable_accounts() throws DatabaseOperationException {
        String sqlquery = "create table if not exists Accounts(accountNumber int auto_increment, Name varchar(100), age int, nationality varchar(30), occupation varchar(30), salary decimal(10,2), creditScore int, balance double, address varchar(225), Gender varchar(1), phoneNo varchar(20), workExpYear double, primary key(accountNumber))";
        try {
            stmt.executeUpdate(sqlquery);
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to create table: " + e.getMessage());
        }
    }

    public static void insertData_accounts(String accountHolder, double balance, int age, String nationality, String occupation, double salary, int creditScore, String address, String Gender, String phoneNo, double workExpYear) throws DatabaseOperationException {
        String sqlquery = "insert into Accounts (Name, age, nationality, occupation, salary, creditScore, balance, address, Gender, phoneNo, workExpYear) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            pstmt = con.prepareStatement(sqlquery);
            pstmt.setString(1, accountHolder);
            pstmt.setInt(2, age);
            pstmt.setString(3, nationality);
            pstmt.setString(4, occupation);
            pstmt.setDouble(5, salary);
            pstmt.setInt(6, creditScore);
            pstmt.setDouble(7, balance);
            pstmt.setString(8, address);
            pstmt.setString(9, Gender);
            pstmt.setString(10, phoneNo);
            pstmt.setDouble(11, workExpYear);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to insert account data: " + e.getMessage());
        }
    }

    public static void extractData_AllUsers() throws DatabaseOperationException {
        String sqlquery = "select * from Accounts";
        try {
            ResultSet rs = stmt.executeQuery(sqlquery);
            System.out.println("Details of all users are as follows: ");
            System.out.println();
            while (rs.next()) {
                System.out.println("Account Number: " + rs.getInt("accountNumber"));
                System.out.println("Name: " + rs.getString("Name"));
                System.out.println("Age: " + rs.getInt("age"));
                System.out.println("Nationality: " + rs.getString("Nationality"));
                System.out.println("Occupation: " + rs.getString("occupation"));
                System.out.println("Salary: " + rs.getDouble("salary"));
                System.out.println("Credit Score: " + rs.getInt("creditScore"));
                System.out.println("Balance: " + rs.getDouble("balance"));
                System.out.println("Address: " + rs.getString("address"));
                System.out.println("Gender: " + rs.getString("Gender"));
                System.out.println("Phone Number: " + rs.getString("phoneNo"));
                System.out.println("workExpYear: " + rs.getInt("workExpYear"));
                System.out.println("--------------------------------");
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to extract all users: " + e.getMessage());
        }
    }

    public static void extractData_OneUser(int accountNumber) throws DatabaseOperationException {
        String sqlquery = "select * from Accounts where accountNumber = ?";
        try {
            pstmt = con.prepareStatement(sqlquery);
            pstmt.setInt(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("Details of user with account number " + accountNumber + " are as follows: ");
            System.out.println();
            while (rs.next()) {
                System.out.println("Account Number: " + rs.getInt("accountNumber"));
                System.out.println("Name: " + rs.getString("Name"));
                System.out.println("Age: " + rs.getInt("age"));
                System.out.println("Nationality: " + rs.getString("Nationality"));
                System.out.println("Occupation: " + rs.getString("occupation"));
                System.out.println("Salary: " + rs.getDouble("salary"));
                System.out.println("Credit Score: " + rs.getInt("creditScore"));
                System.out.println("Balance: " + rs.getDouble("balance"));
                System.out.println("Address: " + rs.getString("address"));
                System.out.println("Gender: " + rs.getString("Gender"));
                System.out.println("Phone Number: " + rs.getString("phoneNo"));
                System.out.println("workExpYear: " + rs.getInt("workExpYear"));
                System.out.println("--------------------------------");
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to extract user data: " + e.getMessage());
        }
    }

    public static void updateBalanceAfterDeposit(int accountNumber, double depositAmount) throws DatabaseOperationException, DataIntegrityException {
        String sqlQuery = "update Accounts set balance = balance + ? where accountNumber = ?";
        try {
            pstmt = con.prepareStatement(sqlQuery);
            pstmt.setDouble(1, depositAmount);
            pstmt.setInt(2, accountNumber);
            int noAffectedRows = pstmt.executeUpdate();
            if (noAffectedRows == 0) {
                throw new DatabaseOperationException("Deposit failed. Account not found.");
            }
            // Check for negative balance
            String checkQuery = "select balance from Accounts where accountNumber = ?";
            pstmt = con.prepareStatement(checkQuery);
            pstmt.setInt(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next() && rs.getDouble("balance") < 0) {
                throw new DataIntegrityException("Invalid operation: Balance cannot be negative after deposit.");
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to update balance after deposit: " + e.getMessage());
        }
    }

    public static void updateBalanceAfterWithdrawal(int accountNumber, double withdrawalAmount) throws DatabaseOperationException, DataIntegrityException {
        String sqlQuery = "update Accounts set balance = balance - ? where accountNumber = ?";
        try {
            pstmt = con.prepareStatement(sqlQuery);
            pstmt.setDouble(1, withdrawalAmount);
            pstmt.setInt(2, accountNumber);
            int noAffectedRows = pstmt.executeUpdate();
            if (noAffectedRows == 0) {
                throw new DatabaseOperationException("Withdrawal failed. Account not found.");
            }
            // Check for negative balance
            String checkQuery = "select balance from Accounts where accountNumber = ?";
            pstmt = con.prepareStatement(checkQuery);
            pstmt.setInt(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next() && rs.getDouble("balance") < 0) {
                throw new DataIntegrityException("Invalid operation: Balance cannot be negative after withdrawal.");
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to update balance after withdrawal: " + e.getMessage());
        }
    }

    public static void closeResources() throws DatabaseOperationException {
        try {
            if (pstmt != null) pstmt.close();
            if (stmt != null) stmt.close();
            if (con != null) con.close();
            System.out.println("Database resources closed.");
        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to close database resources: " + e.getMessage());
        }
    }
}