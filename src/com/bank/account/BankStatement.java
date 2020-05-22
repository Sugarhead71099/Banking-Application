package com.bank.account;

import java.time.LocalDate;
import java.math.BigDecimal;

public class BankStatement extends Statement
{

	private static final long serialVersionUID;

	private BigDecimal deposits;
	private BigDecimal withdrawals;

	static
	{
		serialVersionUID = -3996316271166256190L;
	}

	BankStatement()
	{
		super();
	}

	BankStatement(BigDecimal balance, BigDecimal deposits, BigDecimal withdrawals)
	{
		super(balance);

		this.deposits = deposits;
		this.withdrawals = withdrawals;
	}

	BankStatement(BigDecimal balance, BigDecimal deposits, BigDecimal withdrawals, LocalDate creationDate)
	{
		super(balance, creationDate);

		this.deposits = deposits;
		this.withdrawals = withdrawals;
	}

	public BigDecimal getDeposits()
	{
		return deposits;
	}

	public void setDeposits(BigDecimal deposits)
	{
		this.deposits = deposits;
	}

	public BigDecimal getWithdrawals()
	{
		return withdrawals;
	}

	public void setWithdrawals(BigDecimal withdrawals)
	{
		this.withdrawals = withdrawals;
	}

	@Override
	public String toString()
	{
		return "BankStatement [id=" + super.getId() + ", creationDate=" + super.getCreationDate() + ", balance=" + super.getBalance()
				+ ", deposits=" + deposits + ", withdrawals=" + withdrawals + "]";
	}

}
