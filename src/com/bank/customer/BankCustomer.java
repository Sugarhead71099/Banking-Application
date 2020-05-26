package com.bank.customer;

import com.bank.account.BankAccount;
import com.bank.exception.BankAccountException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BankCustomer extends Customer implements Comparable<Customer>
{

	private static final long serialVersionUID;
	private List<BankAccount> bankAccounts;

	static
	{
		serialVersionUID = -6894526271666415068L;
	}

	public BankCustomer()
	{
		super();

		this.bankAccounts = new ArrayList<BankAccount>();
	}

	public BankCustomer(
		PersonalInformation PersonalInformation,
		EmploymentInformation EmploymentInformation
	) throws BankAccountException.AgeRestriction
	{
		super(PersonalInformation, EmploymentInformation);

		this.bankAccounts = new ArrayList<BankAccount>();
	}

	public BankCustomer(
		PersonalInformation PersonalInformation, EmploymentInformation EmploymentInformation,
		List<BankAccount> bankAccounts
	) throws BankAccountException.AgeRestriction
	{
		super(PersonalInformation, EmploymentInformation);

		if ( bankAccounts != null && bankAccounts.size() > 0 )
		{
			for ( BankAccount account : bankAccounts )
			{
				int accountAgeRequirement = account.getType().AGE_REQUIREMENT;

				if ( PersonalInformation.getAge() < accountAgeRequirement )
				{
					System.out.println("Personal Information Age " + PersonalInformation.getAge());
					throw new BankAccountException.AgeRestriction("Age must be " + accountAgeRequirement + " or higher to add this type of bank account.");
				}
			}
		}

		this.bankAccounts = bankAccounts;
	}

	public List<BankAccount> getBankAccounts()
	{
		return bankAccounts;
	}

	public BankAccount getBankAccount(String accountId)
	{
		Stream<BankAccount> filteredAccounts = bankAccounts.stream().filter( account -> account.getId().equals(accountId) );
		List<BankAccount> accountResults = filteredAccounts.collect(Collectors.toList());

		return ( accountResults.size() > 0 ? accountResults.get(0) : null );
	}

	public void addBankAccount(BankAccount account) throws BankAccountException.AgeRestriction
	{
		int accountAgeRequirement = account.getType().AGE_REQUIREMENT;

		if ( super.PersonalInformation.getAge() < accountAgeRequirement )
		{
			throw new BankAccountException.AgeRestriction("Age must be " + accountAgeRequirement + " or higher to add this type of bank account.");
		}

		bankAccounts.add(account);
	}

	@Override
	public int compareTo(Customer customer)
	{
		return this.PersonalInformation.getAge() - customer.PersonalInformation.getAge();
	}

	@Override
	public boolean equals(Object obj)
	{
		if ( this == obj )
		{
			return true;
		} else if ( obj == null )
		{
			return false;
		} else if ( !(obj instanceof BankCustomer) )
		{
			return false;
		}

		BankCustomer other = (BankCustomer) obj;

		if ( super.getId().equals(other.getId()) )
		{
			return false;
		}

		return true;
	}

	@Override
	public String toString()
	{
		return "BankCustomer [id=" + super.getId() + ", PersonalInformation=" + super.PersonalInformation
				+ ", EmploymentInformation=" + super.EmploymentInformation + ", bankAccounts=" + bankAccounts + "]";
	}

}
