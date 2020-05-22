package com.bank.exception;

public class ValidationException
{

	public static class InvalidPhoneNumber extends RuntimeException
	{

		private static final long serialVersionUID;

		static
		{
			serialVersionUID = 8446100448746442738L;
		}

		public InvalidPhoneNumber(String message)
		{
			super(message);
		}

	}

	public static class InvalidEmailAddress extends RuntimeException
	{

		private static final long serialVersionUID;

		static
		{
			serialVersionUID = -4255131984700469531L;
		}

		public InvalidEmailAddress(String message)
		{
			super(message);
		}

	}

	public static class RequiredValues extends RuntimeException
	{

		private static final long serialVersionUID;

		static
		{
			serialVersionUID = -1334477308872873018L;
		}

		public RequiredValues(String message)
		{
			super(message);
		}

	}

	public static class InvalidField extends RuntimeException
	{

		private static final long serialVersionUID;

		static
		{
			serialVersionUID = 5127865217564112751L;
		}

		public InvalidField(String message)
		{
			super(message);
		}

	}

}
