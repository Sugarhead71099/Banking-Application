package com.bank.customer;

import java.io.Serializable;
import java.math.BigDecimal;

public class EmploymentInformation implements Serializable
{

	private static final long serialVersionUID;
	private String profession;
	private String employer;
	private String creditRating;
	private BigDecimal grossMonthlyIncome;
	private BigDecimal supplementalIncomeAmount;

	static
	{
		serialVersionUID = -7334164515524285910L;
	}

	public EmploymentInformation() {}

	public EmploymentInformation(
		String profession, String employer, String creditRating, BigDecimal grossMonthlyIncome,
		BigDecimal supplementalIncomeAmount
	) {
		this.profession = profession;
		this.employer = employer;
		this.creditRating = creditRating;
		this.grossMonthlyIncome = grossMonthlyIncome;
		this.supplementalIncomeAmount = supplementalIncomeAmount;
	}

	public String getProfession()
	{
		return profession;
	}

	public void setProfession(String profession)
	{
		this.profession = profession;
	}

	public String getEmployer()
	{
		return employer;
	}

	public void setEmployer(String employer)
	{
		this.employer = employer;
	}

	public String getCreditRating()
	{
		return creditRating;
	}

	public void setCreditRating(String creditRating)
	{
		this.creditRating = creditRating;
	}

	public BigDecimal getGrossMonthlyIncome()
	{
		return grossMonthlyIncome;
	}

	public void setGrossMonthlyIncome(BigDecimal grossMonthlyIncome)
	{
		this.grossMonthlyIncome = grossMonthlyIncome;
	}

	public BigDecimal getSupplementalIncomeAmount()
	{
		return supplementalIncomeAmount;
	}

	public void setSupplementalIncomeAmount(BigDecimal supplementalIncomeAmount)
	{
		this.supplementalIncomeAmount = supplementalIncomeAmount;
	}

	@Override
	public String toString()
	{
		return "EmploymentInformation [profession=" + profession + ", employer=" + employer + ", creditRating="
				+ creditRating + ", grossMonthlyIncome=" + grossMonthlyIncome + ", supplementalIncomeAmount="
				+ supplementalIncomeAmount + "]";
	}

}
