package bankFunctions;
import User.Account;
import Custom_Exceptions.AgeRestrictionException;
import Custom_Exceptions.InvalidLoanAmountException;
import Custom_Exceptions.InvalidLoanTermException;
import Custom_Exceptions.LoanEligibilityException;
import Custom_Exceptions.LowCreditScoreException;

public abstract class Loan {
    public abstract boolean checkEligibility(Account account, double loanAmount) throws InvalidLoanAmountException, LowCreditScoreException, AgeRestrictionException, LoanEligibilityException;
    public abstract double calcMonthlyInstallments(double loanAmount, int loanTerm) throws InvalidLoanTermException;
}