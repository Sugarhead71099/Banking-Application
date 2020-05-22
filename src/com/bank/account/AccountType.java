package com.bank.account;

import java.math.BigDecimal;

public final class AccountType
{

	public static final AccountType CHECKING;
	public static final AccountType SAVINGS;
	public static final AccountType MONEY_MARKET;
	public static final AccountType CD;
	public static final AccountType IRA;
	public static final AccountType BROKERAGE;

	public final BigDecimal REQUIRED_BALANCE;
	public final String TYPE;
	public final int AGE_REQUIREMENT;

	static
	{
		CHECKING = new AccountType(new BigDecimal("0"), "CHECKING", 18);
		SAVINGS = new AccountType(new BigDecimal("2000"), "SAVINGS", 18);
		MONEY_MARKET = new AccountType(new BigDecimal("0"), "MONEY_MARKET", 18);
		CD = new AccountType(new BigDecimal("10000"), "CD", 18);
		IRA = new AccountType(new BigDecimal("0"), "IRA", 18);
		BROKERAGE = new AccountType(new BigDecimal("0"), "BROKERAGE", 18);
	}

	private AccountType()
	{
		this.REQUIRED_BALANCE = new BigDecimal("0");
		this.TYPE = null;
		this.AGE_REQUIREMENT = 0;
	}

	private AccountType(BigDecimal requiredBalance)
	{
		this.REQUIRED_BALANCE = requiredBalance;
		this.TYPE = null;
		this.AGE_REQUIREMENT = 0;
	}

	private AccountType(BigDecimal requiredBalance, String type)
	{
		this.REQUIRED_BALANCE = requiredBalance;
		this.TYPE = type;
		this.AGE_REQUIREMENT = 0;
	}

	private AccountType(BigDecimal requiredBalance, String type, int ageRequirement)
	{
		this.REQUIRED_BALANCE = requiredBalance;
		this.TYPE = type;
		this.AGE_REQUIREMENT = ageRequirement;
	}

	@Override
	public String toString()
	{
		return "AccountType [REQUIRED_BALANCE=" + REQUIRED_BALANCE + ", AGE_REQUIREMENT=" + AGE_REQUIREMENT + "]";
	}

}
