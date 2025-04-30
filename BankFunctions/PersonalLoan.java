package bankFunctions;
import User.Account;
import Custom_Exceptions.AgeRestrictionException;
import Custom_Exceptions.InvalidLoanAmountException;
import Custom_Exceptions.InvalidLoanTermException;
import Custom_Exceptions.LoanEligibilityException;
import Custom_Exceptions.LowCreditScoreException;

public class PersonalLoan extends Loan {
    private static final double MAX_LOAN_AMOUNT = 1000000;
    private static final int MIN_LOAN_TERM = 12;
    private static final int MAX_LOAN_TERM = 360;
    private static final int MIN_CREDIT_SCORE = 650;
    private static final int MIN_AGE = 21;
    private static final int MAX_AGE = 65;

    @Override
    public boolean checkEligibility(Account account, double loanAmount) throws InvalidLoanAmountException, LowCreditScoreException, AgeRestrictionException, LoanEligibilityException {
        if (loanAmount <= 0 || loanAmount > MAX_LOAN_AMOUNT) {
            throw new InvalidLoanAmountException("Loan amount must be between 1 and " + MAX_LOAN_AMOUNT + ".");
        }
        if (account.getCreditScore() < MIN_CREDIT_SCORE) {
            throw new LowCreditScoreException("Credit score must be at least " + MIN_CREDIT_SCORE + " for a personal loan.");
        }
        if (account.getAge() < MIN_AGE || account.getAge() > MAX_AGE) {
            throw new AgeRestrictionException("Age must be between " + MIN_AGE + " and " + MAX_AGE + " for a personal loan.");
        }
        if (account.getSalary() < 20000) {
            throw new LoanEligibilityException("Salary must be at least 20,000 for a personal loan.");
        }
        if (account.getBalance() < loanAmount * 0.5) {
            throw new LoanEligibilityException("Balance must be at least 50% of the loan amount.");
        }
        return true;
    }

    @Override
    public double calcMonthlyInstallments(double loanAmount, int loanTerm) throws InvalidLoanTermException {
        if (loanTerm < MIN_LOAN_TERM || loanTerm > MAX_LOAN_TERM) {
            throw new InvalidLoanTermException("Loan term must be between " + MIN_LOAN_TERM + " and " + MAX_LOAN_TERM + " months.");
        }
        double interestRate = 0.10;
        double years = loanTerm / 12.0;
        double payable_amount = loanAmount + (loanAmount * years * interestRate);
        double monthlyPayment = payable_amount / loanTerm;
        return monthlyPayment;
    }
}