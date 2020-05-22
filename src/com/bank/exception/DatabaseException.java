package com.bank.exception;

public class DatabaseException
{

	public static class InvalidParameters extends RuntimeException
	{

		private static final long serialVersionUID;

		static
		{
			serialVersionUID = 5950366232631628083L;
		}

		public InvalidParameters(String message)
		{
			super(message);
		}

	}

}