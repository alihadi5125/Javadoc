package atmmachine;

import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author Muhammad Ali Hadi
 * Atm Class
 * This class implements Overdraft interface
 * This is main Class 
 *
 */
public class ATM implements Overdraft{
	/**
	 * @param args
	 * @throws FileNotFoundException
	 * Entry point of the program
	 */
	public static void main(String[] args) throws FileNotFoundException {
		
		
		Scanner sc = new Scanner(System.in);
		/**
		 * title of bank	
		 */
		Bank theBank = new Bank("Allied Bank Limited  ");	
		Customer defaultUser = theBank.addUser("Muhammad Ali", "Nowshera", "0000");
		 /**
		  *  add a default Current and Credit account for user
		  */
		Account newAccount = new Account("Current Account", defaultUser, theBank);
		defaultUser.addAccount(newAccount);
		theBank.addAccount(newAccount);
		
		Account newCreditAccount = new CreditAccount("Credit Account", defaultUser, theBank, 1000);
		defaultUser.addAccount(newCreditAccount);
		theBank.addAccount(newCreditAccount);
		
		/**
		 * create test user 2
		 */
		Customer overdraftCustomer = theBank.addUser("Imran", "Bunir", "1234"); 
		
		Account newCreditAccount2 = new CreditAccount("Credit Account", overdraftCustomer, theBank, 1000); // add a creditAccount for this user as well as a savings account
		defaultUser.addAccount(newCreditAccount2);
		theBank.addAccount(newCreditAccount2);
		
		Customer user;
		
		/**
		 * loop which runs until program closes/ is quit
		 */
		while (true) { 
			
			/**
			 *  stay in login prompt until successful login
			 */
			user = ATM.mainMenuPrompt(theBank, sc);
			
			/**
			 *  stay in main menu until user quits
			 */
			ATM.customerMenu(user, sc);
			
		}

	}
	
	/**
	 * @param theBank
	 * @param sc
	 * @return authorized User
	 * Print the ATM's login menu.
	 */
	public static Customer mainMenuPrompt(Bank theBank, Scanner sc) {
		
		String userID;
		String pin;
		Customer authUser;
		
		/**
		 *  prompt user for user ID/pin combo until a correct one is reached
		 */
		do {
			
			
			System.out.println(" _____________________Acme ATM Company___________________");			
			System.out.print("|\t\t\t\t\t\t\t| \n");			
			System.out.printf("|\tWelcome to %s\t\t|\n", theBank.getName());
			System.out.print("|\t\t\t\t\t\t\t| \n");
			System.out.println("|_______________________________________________________|");
			System.out.println("|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|");
			System.out.println("|_________________________Log In________________________|");
			System.out.print("|\tPlease log into your account to continue \t|\n");
			System.out.print("| Enter user ID: ");
			userID = sc.nextLine();
			System.out.print("|Enter pin: ");
			pin = sc.nextLine();
			
			/**
			 *  try to get user object corresponding to ID and pin combo
			 */
			authUser = theBank.userLogin(userID, pin); 
			if (authUser == null) {
				System.out.println("Incorrect user ID/pin combination. " + 
						"Please try again");
			}
			System.out.println("|_______________________________________________________|");
			System.out.println("|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|");
			System.out.println("|__________________Log In Success_______________________|");
			System.out.println("|xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx|");
			
			
		} 
		/**
		 *  continue looping until successful login by user or quit
		 */
		while(authUser == null); 	 
		
		
		return authUser;
		
	}
	
	/**
	 * @param theUser
	 * @param sc
	 * @throws FileNotFoundException
	 * Display Customer Menus
	 */
	public static void customerMenu(Customer theUser, Scanner sc) throws FileNotFoundException { //displays ATMS menu and available commands
		
		
		System.out.println("|_______________________________________________________|");
		/**
		 * print a summary of the user's accounts
		 */
		theUser.printAccountsSummary(); 
		
		int choice;
		/**
		 *  user menu
		 */
		
		do {
			
			System.out.println("|What would you like to do?");
			System.out.println("|  1) Deposit ");
			System.out.println("|  2) Withdraw");
			System.out.println("|  3) Transfer");
			System.out.println("|  4) Show account transaction history");
			System.out.println("|  5) Print account transaction history");
			System.out.println("|  6) Overdraft options");
			System.out.println("|  7) Logout of/Quit the Application");
			System.out.println();
			System.out.print("|Enter choice: ");
			choice = sc.nextInt();
			
			if (choice < 1 || choice > 7) {
				System.out.println("Invalid choice. Please choose 1-7.");
			}
			
		} while (choice < 1 || choice > 7);
		
		/**
		 * process choice
		 */
		switch (choice) { 
		
		case 1:
			ATM.depositFunds(theUser, sc);
			break;
			/**
			 * withdraw funds from account
			 */
		case 2:
			ATM.withdrawFunds(theUser, sc); 
			break;
			 /**
			  * transfer from one account to other
			  */
		case 3:
			ATM.transferFunds(theUser, sc);
			break;
			/**
			 * prints all transactions for user on screen
			 */
		case 4:
			ATM.showTransHistory(theUser, sc); 
			break;
			/**
			 * prints all transactions for user into a text file
			 */
		case 5:
			ATM.PrintTransHistory(theUser, sc); 
			break;
			/**
			 * overdraft options
			 */
		case 6:
			System.out.println("Please contact your branch to discuss available overdraft options \n"); 
			ATM.overdraftMenu(theUser,sc);
			break;
		
		case 7:
			sc.nextLine(); //flush buffer
			break;
		}
		
		
		if (choice != 7) {
			/**
			 * display menu until user wants to quit
			 */
			ATM.customerMenu(theUser, sc); 
		}
		
	}
	
	/**
	 * @param theUser
	 * @param sc
	 * @throws FileNotFoundException
	 * Shows overdraft Menu
	 */
	public static void overdraftMenu(Customer theUser, Scanner sc) throws FileNotFoundException{
		
		theUser.printAccountsSummary();
		
		
		int choice;
		int odAcct;
		
		/**
		 * overdraft menu
		 */
		do {
			
			System.out.println("What would you like to do?");
			System.out.println("  1) Check Overdraft Balance");
			System.out.println("  2) <Error payment features in progress>");
			System.out.println("  3) Go back");
			System.out.println();
			System.out.print("Enter choice: ");
			choice = sc.nextInt();
			
			if (choice < 1 || choice > 3) {
				System.out.println("Invalid choice. Please choose 1-5.");
			}
			
		} while (choice < 1 || choice > 3);
		
		/**
		 *  process the choice
		 */
		switch (choice) {
		
		case 1:
			System.out.println("Overdraft account balance ");
			/**
			 *  get account to work with overdraft from
			 */
			do {
				System.out.printf("Enter the number (1-%d) of the account to " + 
						"do overdraft functions with: ", theUser.numAccounts());
				odAcct = sc.nextInt()-1;
				if (odAcct < 0 || odAcct >= theUser.numAccounts()) {
					System.out.println("Invalid account. Please try again.");
				}
				/**
				 * if the account does not have overdraft give this message
				 */
				else if (Account.getName().equals("Credit Account") == false){ 
					System.out.println("Account is not eligble for overdraft, please contact your bank to remedy this.");
					/**
					 * 
					 */
					ATM.customerMenu(theUser, sc);
				}
				else{
					System.out.println("Overdraft balance is: ");
					theUser.getODRate();
					
				}
			} while (odAcct < 0 || odAcct >= theUser.numAccounts());
			
			break;
		case 2:
			System.out.println("Invalid choice. ");
			break;
			
		case 3:
			ATM.customerMenu(theUser, sc);
			break;
		}
		
		/**
		 *  display menu until user wants to quit
		 */
		if (choice != 3) {
			ATM.customerMenu(theUser, sc);
		}
		
		
	}
	

	/**
	 * @return
	 * used to access the abstract payOverDraft method with static
	 */
	public Object Start() { 
		payOverDraft();
		return null;
	}
	/**
	 * @param theUser
	 * @param sc
	 * Process transfer from one account to another.
	 */
	public static void transferFunds(Customer theUser, Scanner sc) { 
		
		int fromAcct;
		int toAcct;
		double amount;
		double acctBal;
		
		if (theUser.numAccounts()!=1){		
			/**
			 *  get account to transfer from
			 */
		do {
			System.out.printf("Enter the number (1-%d) of the account to " + 
					"transfer from: ", theUser.numAccounts());
			fromAcct = sc.nextInt()-1;
			if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		} while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
		}
		else{
			fromAcct=1;
		}
		
		acctBal = theUser.getAcctBalance(fromAcct);
		
		/**
		 * prompt for account to transfer to
		 */
		do {
			System.out.printf("Enter the number (1-%d) of the account to " + 
					"transfer to: ", theUser.numAccounts());
			toAcct = sc.nextInt()-1;
			if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		} while (toAcct < 0 || toAcct >= theUser.numAccounts());
		
		/**
		 *  prompt for amount to transfer
		 */
		do {
			System.out.printf("Enter the amount to transfer (max € %.02f): PKR", 
					acctBal);
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("Amount must be greater than zero.");
			} else if (amount > acctBal) {
				System.out.printf("Amount must not be greater than balance " +
						"of PKR.02f.\n", acctBal);
			}
		} while (amount < 0 || amount > acctBal);
		
		
		theUser.addAcctTransaction(fromAcct, -1*amount, String.format( 
				"Transfer to account %s", theUser.getAcctUUID(toAcct)));
		theUser.addAcctTransaction(toAcct, amount, String.format(
				"Transfer from account %s", theUser.getAcctUUID(fromAcct))); // do the transfer 
		
	}
	
	/**
	 * @param theUser
	 * @param sc
	 * process a withdrawl from an account
	 */
	public static void withdrawFunds(Customer theUser, Scanner sc) { 
		
		int withdrawAcct;
		double amount;
		double acctBal;
		String txReference;
		
		if (theUser.numAccounts()!=1){
			/**
			 * get account to withdraw from
			 */
		do {
			System.out.printf("Enter the number (1-%d) of the account to " + 
					"withdraw from: ", theUser.numAccounts());
			withdrawAcct = sc.nextInt()-1;
			if (withdrawAcct < 0 || withdrawAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		} while (withdrawAcct < 0 || withdrawAcct >= theUser.numAccounts());
		}
		else{
			withdrawAcct=1;
		}
		acctBal = theUser.getAcctBalance(withdrawAcct);
		
		/**
		 *  get amount to transfer
		 */
		do {
			System.out.printf("Enter the amount to withdraw (max € %.02f): € ", 
					acctBal);
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("Amount must be greater than zero.");
			} else if (amount > acctBal) {
				System.out.printf("Amount must not be greater than balance " +
						"of PKR%.02f.\n", acctBal);
			}
		} while (amount < 0 || amount > acctBal);
		
		/**
		 *  gobble up rest of previous input
		 */
		sc.nextLine();
		
		/**
		 *  get a memo
		 */
		System.out.print("Enter a memo: ");
		txReference = sc.nextLine();
		
		
		theUser.addAcctTransaction(withdrawAcct, -1*amount, txReference);
		
	}
	
	/**
	 * @param theUser
	 * @param sc
	 * process a deposit into an account
	 * Deposits Funds in account
	 */
	public static void depositFunds(Customer theUser, Scanner sc) { 
		
		int toAcct;
		double amount;
		String txReference;
		
		if (theUser.numAccounts()!=1){
		/**
		 * get account to deposit to
		 */
		do {
			System.out.printf("Enter the number (1-%d) of the account to " + 
					"deposit to: ", theUser.numAccounts());
			toAcct = sc.nextInt()-1;
			if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		} while (toAcct < 0 || toAcct >= theUser.numAccounts());
		}
		else{
			toAcct=1;
		}
		
		/**
		 *  get amount to transfer
		 */
		do {
			System.out.printf("Enter the amount to deposit: € ");
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("Amount must be greater than zero.");
			} 
		} while (amount < 0);
		
		/**
		 *  gobble up rest of previous input
		 */
		sc.nextLine();
		
		/**
		 *  get a note
		 */
		System.out.print("Enter a Reference: ");
		txReference = sc.nextLine();
		
		/**
		 *  do the deposit
		 */
		theUser.addAcctTransaction(toAcct, amount, txReference);
		
	}
	
	/**
	 * @param theUser
	 * @param sc
	 * Shows Transaction history
	 */
	public static void showTransHistory(Customer theUser, Scanner sc) {
		
		int theAcct=0;
		
		
		if (theUser.numAccounts()!=1){
		
		do {
			System.out.printf("Enter the number (1-%d) of the account\nwhose " + 
					"transactions you want to see: ", theUser.numAccounts());
			theAcct = sc.nextInt()-1;
			if (theAcct < 0 || theAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		} while (theAcct < 0 || theAcct >= theUser.numAccounts()); //
		}
		else{
			theAcct=1;
		}
		/**
		 * print the transaction history
		 */
		theUser.displayAcctHistory(theAcct); 
		
	}
	
	
	/**
	 * @param theUser
	 * @param sc
	 * @throws FileNotFoundException
	 * Print Transaction History
	 * 
	 */
	public static void PrintTransHistory(Customer theUser, Scanner sc)  throws FileNotFoundException{ //prints transactions to a file, if no file exists it creates a file
		
		int theAcct=0;
		/**
		 *prompt for which account if there are more than one
		 */
		if (theUser.numAccounts()!=1){ 
		do {
			System.out.printf("Enter the number (1-%d) of the account\nwhose " + 
					"transactions you want to print to file: ", theUser.numAccounts());
			theAcct = sc.nextInt()-1;
			if (theAcct < 0 || theAcct >= theUser.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		} while (theAcct < 0 || theAcct >= theUser.numAccounts());
		}
		else{
			theAcct=1;
		}
		/**
		 * creates file to print transactions to
		 */
		File transactionHistoryFile = new File ("Desktop/transactionHistory.txt"); 
		/**
		 * print transactions to file
		 */
		PrintWriter transPrinter = new PrintWriter("transactionHistory.txt");
		
		theUser.printAcctHistory(theAcct, transPrinter);
		
		
		transPrinter.close();
		
		
	}


	/**
	 *takes input from user on desired amount to pay, prompts to pick an account to subtract from, sum is taken 
	 */
	public  void payOverDraft(){
		
		
	
	}
	/**
	 *displays OD rates to customer
	 */
	public void checkOverDraftRate(){
		
	}


}
