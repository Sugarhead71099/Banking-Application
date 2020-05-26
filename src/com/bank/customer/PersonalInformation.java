package com.bank.customer;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.bank.helper.DataFormatter;
import com.bank.exception.ValidationException;

public class PersonalInformation implements Serializable
{

	private static final long serialVersionUID;

	private String firstName;
	private String lastName;
	private LocalDate dateOfBirth;
	private String email;

	protected Address Address;
	private String mobilePhone;
	private String homePhone;
	private String ssn;
	private int age;

	static
	{
		serialVersionUID = 5509953110068181155L;
	}

	@SuppressWarnings("unused")
	private PersonalInformation() {};

	public PersonalInformation(String firstName, String lastName, LocalDate dateOfBirth, String email)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.age = (
			this.dateOfBirth != null && this.dateOfBirth instanceof LocalDate ?
				DataFormatter.getDuration(this.dateOfBirth, ChronoUnit.YEARS) : 0
		);
	}
	

	public PersonalInformation(
		String firstName, String lastName, LocalDate dateOfBirth, String email,	Address Address,
		String mobilePhone, String homePhone, String ssn
	) {
		this(firstName, lastName, dateOfBirth, email);

		this.Address = Address;
		this.mobilePhone = mobilePhone;
		this.homePhone = homePhone;
		this.ssn = ssn;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public LocalDate getDateOfBirth()
	{
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth)
	{
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmail()
	{
		return email;
	}

	public void updateEmail(String email) throws ValidationException.InvalidEmailAddress
	{
		if ( DataFormatter.validateEmail(email) )
		{
			this.email = email;
		} else
		{
			throw new ValidationException.InvalidEmailAddress("Invalid email Address: \"" + email + "\" - email was not updated.");
		}
	}

	public Address getAddress()
	{
		return Address;
	}

	public void setAddress(Address Address)
	{
		this.Address = Address;
	}

	public String getMobilePhone()
	{
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) throws ValidationException.InvalidPhoneNumber
	{
		if ( DataFormatter.validatePhoneNumber(mobilePhone) )
		{
			this.mobilePhone = mobilePhone;
		} else
		{
			throw new ValidationException.InvalidPhoneNumber("Invalid phone number: \"" + mobilePhone + "\" - mobile phone number was not updated.");
		}
	}

	public String getHomePhone()
	{
		return homePhone;
	}

	public void setHomePhone(String homePhone) throws ValidationException.InvalidPhoneNumber
	{
		if ( DataFormatter.validatePhoneNumber(homePhone) )
		{
			this.homePhone = homePhone;
		} else
		{
			throw new ValidationException.InvalidPhoneNumber("Invalid phone number given: \"" + homePhone + "\" - home phone number was not updated.");
		}
	}

	public String getSsn()
	{
		return ssn;
	}

	public void setSsn(String ssn)
	{
		this.ssn = ssn;
	}

	public int getAge()
	{
		return age;
	}

	public void setAge(int age)
	{
		this.age = age;
	}


	@Override
	public String toString()
	{
		return "PersonalInformation [firstName=" + firstName + ", lastName=" + lastName + ", dateOfBirth=" + dateOfBirth
				+ ", email=" + email + ", Address=" + Address + ", mobilePhone=" + mobilePhone + ", homePhone="
				+ homePhone + ", ssn=" + ssn + ", age=" + age + "]";
	}

}
