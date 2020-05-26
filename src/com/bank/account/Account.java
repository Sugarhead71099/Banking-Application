package com.bank.account;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Account implements Serializable
{

	private static final long serialVersionUID;

	private final String id;
	private List<Statement> statements;
	private BigDecimal balance;
	private final LocalDate creationDate;

	static
	{
		serialVersionUID = 8182725005700856390L;
	}

	Account() {
		this.id = UUID.randomUUID().toString();
		this.balance = new BigDecimal("0");
		this.creationDate = LocalDate.now();
		this.statements = new ArrayList<Statement>();
	}

	Account(BigDecimal balance)
	{
		this();
		this.balance = balance;
	}

	Account(BigDecimal balance, LocalDate creationDate)
	{
		this.id = UUID.randomUUID().toString();
		this.balance = balance;
		this.creationDate = ( creationDate != null ? creationDate : LocalDate.now() );
		this.statements = new ArrayList<Statement>();
	}

	Account(BigDecimal balance, LocalDate creationDate, List<Statement> statements)
	{
		this(balance, creationDate);
		this.statements = statements;
	}

	public BigDecimal getBalance()
	{
		return balance;
	}

	public void setBalance(BigDecimal balance)
	{
		this.balance = balance;
	}

	public String getId()
	{
		return id;
	}

	public LocalDate getCreationDate()
	{
		return creationDate;
	}

	public void addStatement(Statement statement)
	{
		statements.add(statement);
	}

	public List<Statement> getStatements()
	{
		return statements;
	}

	public Statement getLastStatement()
	{
		Statement latestStatement = statements.get(0);

		for ( Statement statement : statements )
		{
			if ( statement.getCreationDate().compareTo(latestStatement.getCreationDate()) > 0 )
			{
				latestStatement = statement;
			}
		}

		return latestStatement;
	}

	@Override
	public String toString()
	{
		return "Account [id=" + id + ", statements=" + statements + ", balance=" + balance + ", creationDate="
				+ creationDate + "]";
	}

	public abstract Statement generateStatement();

	public abstract BigDecimal withdraw(BigDecimal amount) throws Exception;

	public abstract BigDecimal deposit(BigDecimal amount) throws Exception;

}
