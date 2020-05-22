package com.bank;

import com.bank.customer.BankCustomer;
import com.bank.customer.BankCustomerBuilder;
import com.bank.helper.DBManager;
import com.bank.account.BankAccount;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Comparator;
/*import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;*/

public class Bank extends Thread
{

	protected static DBManager DB;
	private final List<BankCustomer> customers;

    public static Comparator<BankCustomer> customerComparator = new Comparator<BankCustomer>()
    {

		public int compare(BankCustomer customer1, BankCustomer customer2)
		{
		   return customer1.compareTo(customer2);
		}

	};

	Bank()
	{
		this.customers = new ArrayList<BankCustomer>();
	}

	public Bank(List<BankCustomer> customers)
	{
		this.customers = customers;
	}

	public void addCustomer(BankCustomer customer)
	{
		this.customers.add(customer);
	}

	public List<BankCustomer> getCustomers()
	{
		return customers;
	}

	public BankCustomer getCustomer(String customerId)
	{
		for ( BankCustomer customer : customers )
		{
			if ( customer.getId().equals(customerId) )
			{
				return customer;
			}
		}

		return null;
	}

	@Override
	public void run()
	{
		super.run();
		DB = new DBManager("localhost:1521", "orcl", "system", "system");
	}

	@Override
	public String toString()
	{
		return "Bank [customers=" + customers + "]";
	}

	public static void main(String[] args)
	{
		List<BankCustomer> customerSeeder = new ArrayList<BankCustomer>(
			Arrays.asList(
				new BankCustomerBuilder("John", "Doe", LocalDate.of(1985, Month.JANUARY, 1), "johnDoe@yahoo.com")
					.addBankAccount(BankAccount.createAccount(new BigDecimal("4500"), BankAccount.AccountType.CHECKING))
					.addBankAccount(BankAccount.createAccount(new BigDecimal("9000"), BankAccount.AccountType.SAVINGS))
					.build(),

//				new BankCustomerBuilder("Bob", "Thompson", LocalDate.of(2006, Month.JUNE, 1), "bobThompson@gmail.com")
//					.addBankAccount(BankAccount.createAccount(new BigDecimal("9000"), BankAccount.AccountType.SAVINGS))
//					.build(),

				new BankCustomerBuilder("Jack", "Raymond", LocalDate.of(2002, Month.APRIL, 1), "jackRaymond@outlook.com")
					.build()
			)
		);

		/*
		 * try { ObjectOutputStream oOS = new ObjectOutputStream(new
		 * FileOutputStream("BankCustomerSeeder.txt")); oOS.writeObject(customerSeeder);
		 * 
		 * ObjectInputStream oIS = new ObjectInputStream(new
		 * FileInputStream("BankCustomerSeeder.txt")); List<Customer> customerSeeder =
		 * (ArrayList<BankCustomer>) oIS.readObject();
		 * 
		 * System.out.println(customerSeeder + "\n"); } catch ( IOException e ) {
		 * e.printStackTrace(); } catch ( ClassNotFoundException e ) {
		 * e.printStackTrace(); } catch ( Exception e ) { e.printStackTrace(); }
		 */

		try
		{

			Bank bank = new Bank(customerSeeder);
			bank.start();
			System.out.println(bank);
			System.out.println("\n");

			List<BankAccount> customer1BankAccounts = bank.customers.get(0).getBankAccounts();
			System.out.println("Customer 1 Bank Accounts: " + customer1BankAccounts);
			System.out.println("\n");

			customer1BankAccounts.get(0).generateStatement();

			System.out.println("Customer 1 Bank Account 1 Statements:" + customer1BankAccounts.get(0).getStatements().get(0));
			System.out.println("\n");

			Collections.sort(bank.customers, Bank.customerComparator);

			System.out.println(bank.customers);

		} catch ( Exception e )
		{
			e.printStackTrace();
		}

	}

}
