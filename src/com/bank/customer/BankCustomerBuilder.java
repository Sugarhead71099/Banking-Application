package com.bank.customer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.bank.account.BankAccount;
import com.bank.exception.BankAccountException;
import com.bank.exception.ValidationException;
import com.bank.helper.DataFormatter;

public class BankCustomerBuilder
{

	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;
	private String email;

	private List<BankAccount> bankAccounts;
	public Address Address;
	private String mobilePhone;
	private String homePhone;
	private String ssn;

	private String profession;
	private String employer;
	private String creditRating;
	private BigDecimal grossMonthlyIncome;
	private BigDecimal supplementalIncomeAmount;
	private int age;

	@SuppressWarnings("unused")
	private BankCustomerBuilder() {}

	public BankCustomerBuilder(String firstName, String lastName, LocalDate dateOfBirth, String email)	
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.bankAccounts = new ArrayList<BankAccount>();
		this.Address = new Address();
		this.mobilePhone = "";
		this.homePhone = "";
		this.ssn = "";
		this.grossMonthlyIncome = new BigDecimal("0");
		this.supplementalIncomeAmount = new BigDecimal("0");

		this.age = (
			dateOfBirth != null && dateOfBirth instanceof LocalDate ?
				DataFormatter.getDuration(dateOfBirth, ChronoUnit.YEARS) : 0
		);
	}

	public BankCustomerBuilder withEmail(String email) throws ValidationException.InvalidEmailAddress
	{
		if ( ! DataFormatter.validateEmail(email) )
		{
			throw new ValidationException.InvalidEmailAddress("Invalid Email - Customer must supply a valid email.");
		}

		this.email = email;

		return this;
	}
	
	public BankCustomerBuilder withBankAccounts(List<BankAccount> bankAccounts) throws BankAccountException.AgeRestriction
	{
		for ( BankAccount account : bankAccounts )
		{
			int accountAgeRequirement = account.getType().AGE_REQUIREMENT;

			System.out.println("Age is " + this.age);
			System.out.println("Account Age Requirement " + accountAgeRequirement);
			
			if ( this.age < accountAgeRequirement )
			{
				throw new BankAccountException.AgeRestriction("Age must be " + accountAgeRequirement + " or higher to add this type of bank account.");
			}
		}

		this.bankAccounts = bankAccounts;

		return this;
	}
	
	public BankCustomerBuilder addBankAccount(BankAccount account) throws BankAccountException.AgeRestriction
	{
		int accountAgeRequirement = account.getType().AGE_REQUIREMENT;

		if ( this.age < accountAgeRequirement )
		{
			throw new BankAccountException.AgeRestriction("Age must be " + accountAgeRequirement + " or higher to add this type of bank account.");
		}

		this.bankAccounts.add(account);

		return this;
	}

	public BankCustomerBuilder withAddress(Address address)
	{
		this.Address = address;

		return this;
	}

	public BankCustomerBuilder withProfession(String profession)
	{
		this.profession = profession;

		return this;
	}

	public BankCustomerBuilder withMobilePhone(String mobilePhone) throws ValidationException.InvalidPhoneNumber
	{
		if ( DataFormatter.validatePhoneNumber(mobilePhone) )
		{
			this.mobilePhone = mobilePhone;
		} else
		{
			throw new ValidationException.InvalidPhoneNumber("Invalid phone number: \"" + mobilePhone + "\" - mobile phone number was not added to this builder.");
		}

		return this;
	}

	public BankCustomerBuilder withHomePhone(String homePhone) throws ValidationException.InvalidPhoneNumber
	{
		if ( DataFormatter.validatePhoneNumber(homePhone) )
		{
			this.homePhone = homePhone;
		} else
		{
			throw new ValidationException.InvalidPhoneNumber("Invalid phone number: \"" + homePhone + "\" - mobile phone number was not added to this builder.");
		}

		return this;
	}

	public BankCustomerBuilder withCreditRating(String creditRating)
	{
		this.creditRating = creditRating;

		return this;
	}

	public BankCustomerBuilder withGrossMonthlyIncome(BigDecimal grossMonthlyIncome)
	{
		this.grossMonthlyIncome = grossMonthlyIncome;

		return this;
	}

	public BankCustomerBuilder withSupplementalIncomeAmount(BigDecimal supplementalIncomeAmount)
	{
		this.supplementalIncomeAmount = supplementalIncomeAmount;

		return this;
	}

	public BankCustomerBuilder withSsn(String ssn)
	{
		this.ssn = ssn;

		return this;
	}

	public BankCustomer build() throws ValidationException.RequiredValues
	{
		if (
			DataFormatter.isEmpty(firstName) || DataFormatter.isEmpty(lastName) ||
			dateOfBirth == null || DataFormatter.isEmpty(email)
		) {
			throw new ValidationException.RequiredValues("First name, last name, date of birth and email are all required for creation of a customer - Please enter valid values for the listed items.");
		}

		try
		{
			PersonalInformation personInfo = new PersonalInformation(
				firstName, lastName, dateOfBirth, email,
				Address, mobilePhone, homePhone, ssn
			);

			EmploymentInformation empInfo = new EmploymentInformation(
				profession, employer, creditRating,
				grossMonthlyIncome, supplementalIncomeAmount
			);

			return new BankCustomer(personInfo, empInfo, bankAccounts);

		} catch(Exception exception)
		{
			exception.printStackTrace();
		}

		return null;
	}

}
