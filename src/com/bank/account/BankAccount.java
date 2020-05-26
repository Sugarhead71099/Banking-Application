package com.bank.account;

import java.math.BigDecimal;

import com.bank.Bank;
import com.bank.account.Account;

import com.bank.exception.BankAccountException;
import com.bank.exception.DatabaseException;
import com.bank.helper.DataFormatter;

public class BankAccount extends Account
{

	public static enum AccountType
	{
		CHECKING,
		SAVINGS(new BigDecimal("2000"), 18),
		MONEY_MARKET,
		CD(new BigDecimal("10000"), 18),
		IRA,
		BROKERAGE;

		public final BigDecimal REQUIRED_BALANCE;
		public final int AGE_REQUIREMENT;

		private AccountType()
		{
			this.REQUIRED_BALANCE = new BigDecimal("0");
			this.AGE_REQUIREMENT = 0;
		}

		private AccountType(BigDecimal requiredBalance)
		{
			this.REQUIRED_BALANCE = requiredBalance;
			this.AGE_REQUIREMENT = 0;
		}

		private AccountType(BigDecimal requiredBalance, int ageRequirement)
		{
			this.REQUIRED_BALANCE = requiredBalance;
			this.AGE_REQUIREMENT = ageRequirement;
		}
	}

	private static final long serialVersionUID;

	private AccountType type;
	private BigDecimal withdrawals;
	private BigDecimal deposits;

	private final String ASSOC_TABLE;

	static
	{
		serialVersionUID = 7852783786237721221L;
	}

	BankAccount(BigDecimal balance) throws BankAccountException.RequiredBalance
	{
		super(balance);

		this.type = AccountType.CHECKING;

		if ( balance.compareTo(type.REQUIRED_BALANCE) < 0 )
		{
			throw new BankAccountException.RequiredBalance("Balance must be " + type.REQUIRED_BALANCE + " or greater to open this type of account");
		}

		this.withdrawals = new BigDecimal("0");
		this.deposits = new BigDecimal("0");
	}
	
	BankAccount(BigDecimal balance, AccountType type) throws BankAccountException.RequiredBalance
	{
		super(balance);

		this.type = ( type != null ? type : AccountType.CHECKING );

		if ( balance.compareTo(this.type.REQUIRED_BALANCE) < 0 )
		{
			throw new BankAccountException.RequiredBalance("Balance must be " + type.REQUIRED_BALANCE + " or greater to open this type of account");
		}

		this.withdrawals = new BigDecimal("0");
		this.deposits = new BigDecimal("0");
	}

	{
		this.ASSOC_TABLE = "accounts";
	}

	public AccountType getType()
	{
		return type;
	}

	public void setType(AccountType type)
	{
		this.type = type;

		try
		{
			Bank.DB.update(
				ASSOC_TABLE,
				new String[] { "type" },
				new Object[] { type },
				"ID = ?",
				new Object[] { super.getId() }
			);
		} catch ( DatabaseException.InvalidParameters e )
		{
			e.printStackTrace();
		} catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	public BigDecimal getWithdrawals()
	{
		return withdrawals;
	}

	public void setWithdrawals(BigDecimal withdrawals)
	{
		this.withdrawals = withdrawals;

		try
		{
			Bank.DB.update(
				ASSOC_TABLE,
				new String[] { "withdrawals" },
				new Object[] { withdrawals },
				"ID = ?",
				new Object[] { super.getId() }
			);
		} catch ( DatabaseException.InvalidParameters e )
		{
			e.printStackTrace();
		} catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	public BigDecimal getDeposits()
	{
		return deposits;
	}

	public void setDeposits(BigDecimal deposits)
	{
		this.deposits = deposits;

		try
		{
			Bank.DB.update(
					ASSOC_TABLE,
				new String[] { "deposits" },
				new Object[] { deposits },
				"ID = ?",
				new Object[] { super.getId() }
			);
		} catch ( DatabaseException.InvalidParameters e )
		{
			e.printStackTrace();
		} catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	public static BankAccount createAccount(BigDecimal balance, AccountType type)
	{
		try
		{
			Account bankAccount = new BankAccount(balance, type);

			return (BankAccount) bankAccount;
		} catch (BankAccountException.RequiredBalance e)
		{
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	public static BigDecimal[] transferFunds(BankAccount from, BankAccount to, BigDecimal amount)
	{
		try
		{
			from.withdraw(amount);
			to.deposit(amount);

			return new BigDecimal[] { from.getBalance(), to.getBalance() };
		} catch ( BankAccountException.InsufficientFunds e )
		{
			e.printStackTrace();
		} catch ( IllegalArgumentException e )
		{
			e.printStackTrace();
		} catch ( Exception e )
		{
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public BankStatement generateStatement()
	{
		BigDecimal totalDeposits = deposits;
		BigDecimal totalWithdrawals = withdrawals;

		deposits = new BigDecimal("0");
		withdrawals = new BigDecimal("0");

		BankStatement newStatement = new BankStatement(super.getBalance(), totalDeposits, totalWithdrawals);

		super.addStatement(newStatement);

		Thread.yield();

		try
		{
			Bank.DB.update(
				ASSOC_TABLE,
				new String[] { "deposits", "withdrawals" },
				new Object[] { getDeposits(), getWithdrawals() },
				"ID = ?",
				new Object[] { super.getId() }
			);
		} catch ( DatabaseException.InvalidParameters e )
		{
			e.printStackTrace();
		} catch ( Exception e )
		{
			e.printStackTrace();
		}

		return newStatement;
	}

	@Override
	public synchronized BigDecimal withdraw(BigDecimal amount) throws IllegalArgumentException, BankAccountException.InsufficientFunds
	{
		if ( amount.compareTo(new BigDecimal("0")) <= 0 )
		{
			throw new IllegalArgumentException("Withdraw must be greater than " + DataFormatter.getDisplayCurrencyValue(amount));
		}

		BigDecimal newBalance = super.getBalance().subtract(amount);

		if ( newBalance.compareTo(type.REQUIRED_BALANCE) < 0 )
		{
			throw new BankAccountException.InsufficientFunds("Withdrawal leaves balance lower than the required minimum balance of " + DataFormatter.getDisplayCurrencyValue(type.REQUIRED_BALANCE) + " - Withdrawal not made.");
		}


		setWithdrawals(withdrawals.add(amount));

		super.setBalance(newBalance);

		try
		{
			Bank.DB.update(
					ASSOC_TABLE,
				new String[] { "balance", "withdrawals" },
				new Object[] { super.getBalance(), getWithdrawals() },
				"ID = ?",
				new Object[] { super.getId() }
			);
		} catch ( DatabaseException.InvalidParameters e )
		{
			e.printStackTrace();
		} catch ( Exception e )
		{
			e.printStackTrace();
		}

		return super.getBalance();
	}

	@Override
	public synchronized BigDecimal deposit(BigDecimal amount) throws IllegalArgumentException
	{
		if ( amount.compareTo(new BigDecimal("0")) <= 0 )
		{
			throw new IllegalArgumentException("Deposit must be greater than " + DataFormatter.getDisplayCurrencyValue(amount));
		}

		setDeposits(deposits.add(amount));

		BigDecimal newBalance = super.getBalance().add(amount);

		super.setBalance(newBalance);

		try
		{
			Bank.DB.update(
					ASSOC_TABLE,
				new String[] { "balance", "deposits" },
				new Object[] { super.getBalance(), getDeposits() },
				"ID = ?",
				new Object[] { super.getId() }
			);
		} catch ( DatabaseException.InvalidParameters e )
		{
			e.printStackTrace();
		} catch ( Exception e )
		{
			e.printStackTrace();
		}

		return super.getBalance();
	}

	@Override
	public String toString()
	{
		return "BankAccount [id=" + super.getId() + ", balance=" + super.getBalance() + ", creationDate=" + super.getCreationDate()
				+ ", statements=" + super.getStatements() + ", type=" + type + ", withdrawals=" + withdrawals
				+ ", deposits=" + deposits + "]";
	}
	

}
