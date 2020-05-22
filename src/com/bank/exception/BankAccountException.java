package com.bank.exception;

public class BankAccountException
{

	public static class InsufficientFunds extends RuntimeException
	{

		private static final long serialVersionUID;

		static
		{
			serialVersionUID = 7508312995816481227L;
		}

		public InsufficientFunds(String message)
		{
			super(message);
		}

	}

	public static class AgeRestriction extends RuntimeException
	{

		private static final long serialVersionUID;

		static
		{
			serialVersionUID = -2881084717040853138L;
		}

		public AgeRestriction(String message)
		{
			super(message);
		}

	}

	public static class RequiredBalance extends RuntimeException
	{

		private static final long serialVersionUID;

		static
		{
			serialVersionUID = 389139145352757004L;
		}

		public RequiredBalance(String message)
		{
			super(message);
		}

	}

}
