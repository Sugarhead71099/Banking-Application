package com.bank.account;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public abstract class Statement implements Serializable
{

	private static final long serialVersionUID;

	private final String id;
	private final BigDecimal balance;
	private final LocalDate creationDate;

	static
	{
		serialVersionUID = 6108379079319060644L;
	}

	Statement()
	{
		this.id = UUID.randomUUID().toString();
		this.balance = new BigDecimal("0");
		this.creationDate = LocalDate.now();
	}

	Statement(BigDecimal balance)
	{
		this.id = UUID.randomUUID().toString();
		this.balance = balance;
		this.creationDate = LocalDate.now();
	}

	Statement(BigDecimal balance, LocalDate creationDate)
	{
		this.id = UUID.randomUUID().toString();
		this.balance = balance;
		this.creationDate = creationDate;
	}

	public String getId()
	{
		return id;
	}

	public BigDecimal getBalance()
	{
		return balance;
	}

	public LocalDate getCreationDate()
	{
		return creationDate;
	}

	@Override
	public String toString()
	{
		return "Statement [id=" + id + ", balance=" + balance + ", creationDate=" + creationDate + "]";
	}

}
