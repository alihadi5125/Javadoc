package atmmachine;

import java.util.ArrayList;

/**
 * @author Muhammad Ali Hadi
 * creditAccount
 * Hold the routines of creditAccount
 * Credit Account class
 * Contains All the info about the Creadit Account
 */
public class CreditAccount extends Account {
	

	
	private ArrayList<AccountTransaction> transactions;
	private double overDraftLimit;
	private String name;

	/**
	 * @param name
	 * @param holder
	 * @param theBank
	 * @param overDraftLimit
	 * Credit account method
	 */
	public CreditAccount(String name, Customer holder, Bank theBank,double overDraftLimit) {
		super(name, holder, theBank);
		
		
		this.name = name;
		/**
		 * can be negative or positive will allow a user to pay their over draft if their limit is minus 
		 * and check the interest rate on OD sums
		 */
		
		 this.overDraftLimit = overDraftLimit;	
		
		this.transactions = new ArrayList<AccountTransaction>();
		
		
	}
	
	/**
	 *get balance of this account by adding its transactions' values
	 *@return balance
	 */
	public double getBalance() { 
		
		double balance = 0;
		balance = overDraftLimit;
		return balance;
		
	}
	
	
	
	

}
