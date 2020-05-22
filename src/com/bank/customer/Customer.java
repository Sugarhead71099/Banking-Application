package com.bank.customer;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public abstract class Customer implements Serializable
{

	private static final long serialVersionUID;

	private final String id;
	public final PersonalInformation PersonalInformation;
	public final EmploymentInformation EmploymentInformation;

	static
	{
		serialVersionUID = 1303703355969296613L;
	}

	public Customer()
	{
		this.id = UUID.randomUUID().toString();
		this.PersonalInformation = new PersonalInformation("", "", LocalDate.now(), "");
		this.EmploymentInformation = new EmploymentInformation();
	}

	public Customer(
		PersonalInformation PersonalInformation,
		EmploymentInformation EmploymentInformation
	) {
		this.id = UUID.randomUUID().toString();
		this.PersonalInformation = PersonalInformation;
		this.EmploymentInformation = EmploymentInformation;
	}

	public String getId()
	{
		return id;
	}

	@Override
	public String toString()
	{
		return "Customer [id=" + id + ", PersonalInformation=" + PersonalInformation + ", EmploymentInformation="
				+ EmploymentInformation + "]";
	}

}
