package atmmachine;

import java.util.Date;

/**
 * @author Muhammad Ali Hadi
 * Account Transaction Class
 * Holds all the Account tansaction routines
 */
public class AccountTransaction {
	
	private double amount;
	/**
	 * The time and date of this transaction.
	 */
	private Date timestamp; 
	/**
	 * a note for the transaction
	 */
	private String txReference; 
	private Account inAccount;
	
	/**
	 * @param amount
	 * @param inAccount
	 * create transaction
	 */
	public AccountTransaction(double amount, Account inAccount) { 
		
		this.amount = amount;
		this.inAccount = inAccount;
		this.timestamp = new Date();
		this.txReference = "";
		
	}
	
	/**
	 * @param amount
	 * @param txReference
	 * @param inAccount
	 * create tx with a note
	 */
	public AccountTransaction(double amount, String txReference, Account inAccount) {
		
		/**
		 *  call the single-arg constructor first
		 */
		this(amount, inAccount);
		
		this.txReference = txReference;
		
	}
	 /**
	  * get tx amount
	  * @return
	  */
	public double getAmount() {
		return this.amount;
	}
	/**
	 * @return a string summarizing the tx
	 */
	public String getSummaryLine() {  
		
		if (this.amount >= 0) {
			return String.format("%s, € %.02f : %s", 
					this.timestamp.toString(), this.amount, this.txReference);
		} else {
			return String.format("%s, € (%.02f) : %s", 
					this.timestamp.toString(), -this.amount, this.txReference);
		}
	}

}
