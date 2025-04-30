# Banking System Application

## Overview
The **Banking System Application** is a Java-based console application designed to simulate core banking operations. It enables account management, transaction processing, loan applications, and data persistence using MySQL and file storage. Built with Object-Oriented Programming (OOP) principles, it serves as an educational prototype for fintech backends, demonstrating secure, scalable, and modular design.

Developed as a personal/academic project to showcase proficiency in Java, JDBC, and OOP.

## Problem Statement
The project addresses inefficiencies in manual banking processes by automating account management, loan processing, and data storage. It aligns with the $2.5 trillion digital banking market (projected by 2027), promoting financial inclusion, security, and compliance. The solution meets industry needs for robust, scalable backends and prepares developers for fintech roles.

## Features
- **Account Management**: Create and manage accounts with validations (e.g., unique phone numbers, minimum balance).
- **Transactions**: Process deposits, withdrawals, and balance inquiries.
- **Loan Processing**: Apply for personal/home loans, check eligibility (credit score, age), and calculate installments.
- **Data Persistence**: Store account data in MySQL via JDBC and loan applications in files.
- **Error Handling**: Custom exceptions (e.g., `AgeRestrictionException`, `LowCreditScoreException`) for precise feedback.
- **Security**: Uses `PreparedStatement` to prevent SQL injection.
- **OOP Design**: Implements encapsulation, abstraction, inheritance, and polymorphism for modularity.

## Uniqueness and Differentiation
- **Modular Loan System**: Abstract `Loan` class allows easy addition of new loan types, unlike rigid competitor prototypes.
- **Robust Error Handling**: Custom exceptions provide precise, user-friendly feedback, surpassing generic error systems.
- **Dual Storage**: Combines MySQL for accounts and file storage for loan applications, enhancing flexibility.
- **Educational Focus**: Demonstrates real-world banking logic, ideal for learning, unlike GUI-focused competitors.
- **Extensibility**: Backend design supports future GUI, cloud, or API integration, offering scalability.

## Prerequisites
- **Java**: JDK 8 or higher
- **MySQL**: Version 5.7 or higher
- **MySQL Connector/J**: JDBC driver for MySQL
- **IDE**: Eclipse, IntelliJ IDEA, or similar (optional)
- **OS**: Windows, macOS, or Linux

## Setup Instructions
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/your-username/banking-system-application.git
   cd banking-system-application
   ```

2. **Install MySQL**:
   - Install MySQL and set up a database named `BankingSystem`.
   - Update credentials in `JDBC_Database.java`:
     ```java
     public static final String USER = "your-username";
     public static final String PWD = "your-password";
     ```

3. **Add MySQL Connector/J**:
   - Download the MySQL Connector/J JAR from [MySQL's official site](https://dev.mysql.com/downloads/connector/j/).
   - Add the JAR to your project’s classpath (e.g., in Eclipse: Project > Properties > Java Build Path > Libraries > Add External JARs).

4. **Compile and Run**:
   - Compile the project:
     ```bash
     javac *.java
     ```
   - Run the main class:
     ```bash
     java BankingSystem
     ```

## Usage
1. Launch the application to access the console menu.
2. Choose options (1-7) to:
   - Create an account (e.g., enter name, age, initial balance).
   - Deposit/withdraw funds (e.g., specify account number, amount).
   - Check balance or view account details.
   - Apply for a personal/home loan (e.g., enter loan amount, term).
   - View all accounts (database-driven).
3. Follow prompts and handle errors (e.g., “Insufficient balance”) as displayed.

## Project Structure
```
banking-system-application/
├── Account.java                # Account class for data and operations
├── Bank.java                   # Manages accounts and transactions
├── Loan.java                   # Abstract class for loans
├── PersonalLoan.java           # Personal loan implementation
├── HomeLoan.java               # Home loan implementation
├── LoanApplication.java        # File handling for loan applications
├── JDBC_Database.java          # Database operations with MySQL
├── BankingSystem.java          # Main application with console interface
├── *.java                      # Custom exception classes
├── README.md                   # Project documentation
```

## Future Enhancements
- Add a web/mobile GUI using Spring Boot or JavaFX.
- Implement user authentication and data encryption.
- Integrate cloud databases (e.g., AWS RDS) and APIs for real-time features.
- Support transaction histories and notifications.

## Contributing
Contributions are welcome! To contribute:
1. Fork the repository.
2. Create a feature branch (`git checkout -b feature-name`).
3. Commit changes (`git commit -m "Add feature"`).
4. Push to the branch (`git push origin feature-name`).
5. Open a pull request with a detailed description.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contact
For questions or feedback, contact [Your Name] at [your.email@example.com] or open an issue on GitHub.

---
*Developed as a personal/academic project to demonstrate Java, OOP, and database integration.*