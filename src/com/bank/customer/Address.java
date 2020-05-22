package com.bank.customer;

import java.io.Serializable;
import java.math.BigDecimal;

public class Address implements Serializable
{

	private static final long serialVersionUID;


	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String zipcode;
	private String ownershipType;
	private BigDecimal monthlyPaymentType;

	static
	{
		serialVersionUID = -8734818907122331400L;
	}

	Address() {}

	Address(String addressLine1, String city, String state, String ownershipType)
	{
		this.addressLine1 = addressLine1;
		this.city = city;
		this.state = state;
		this.ownershipType = ownershipType;
	}

	Address(
		String addressLine1, String addressLine2, String city, String state, String zipcode,
		String ownershipType, BigDecimal monthlyPaymentType
	)
	{
		this(addressLine1, city, state, ownershipType);

		this.addressLine1 = addressLine1;
		this.zipcode = zipcode;
		this.monthlyPaymentType = monthlyPaymentType;
	}

	public String getAddressLine1()
	{
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1)
	{
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2()
	{
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2)
	{
		this.addressLine2 = addressLine2;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public String getZipcode()
	{
		return zipcode;
	}

	public void setZipcode(String zipcode)
	{
		this.zipcode = zipcode;
	}

	public String getOwnershipType()
	{
		return ownershipType;
	}

	public void setOwnershipType(String ownershipType)
	{
		this.ownershipType = ownershipType;
	}

	public BigDecimal getMonthlyPaymentType()
	{
		return monthlyPaymentType;
	}

	public void setMonthlyPaymentType(BigDecimal monthlyPaymentType)
	{
		this.monthlyPaymentType = monthlyPaymentType;
	}

	@Override
	public String toString()
	{
		return "Address [addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2 + ", city=" + city
				+ ", state=" + state + ", zipcode=" + zipcode + ", ownershipType=" + ownershipType
				+ ", monthlyPaymentType=" + monthlyPaymentType + "]";
	}

}
