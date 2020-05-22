package com.bank.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public final class DataFormatter
{

	/*
	 * public static enum StateMap {
	 * 
	 * ALABAMA("Alabama", "AL"), ALASKA("Alaska", "AK"),
	 * AMERICAN_SAMOA("American Samoa", "AS"), ARIZONA("Arizona", "AZ"),
	 * ARKANSAS("Arkansas", "AR"), CALIFORNIA("California", "CA"),
	 * COLORADO("Colorado", "CO"), CONNECTICUT("Connecticut", "CT"),
	 * DELAWARE("Delaware", "DE"), DISTRICT_OF_COLUMBIA("District of Columbia",
	 * "DC"), FEDERATED_STATES_OF_MICRONESIA("Federated States of Micronesia",
	 * "FM"), FLORIDA("Florida", "FL"), GEORGIA("Georgia", "GA"), GUAM("Guam",
	 * "GU"), HAWAII("Hawaii", "HI"), IDAHO("Idaho", "ID"), ILLINOIS("Illinois",
	 * "IL"), INDIANA("Indiana", "IN"), IOWA("Iowa", "IA"), KANSAS("Kansas", "KS"),
	 * KENTUCKY("Kentucky", "KY"), LOUISIANA("Louisiana", "LA"), MAINE("Maine",
	 * "ME"), MARYLAND("Maryland", "MD"), MARSHALL_ISLANDS("Marshall Islands",
	 * "MH"), MASSACHUSETTS("Massachusetts", "MA"), MICHIGAN("Michigan", "MI"),
	 * MINNESOTA("Minnesota", "MN"), MISSISSIPPI("Mississippi", "MS"),
	 * MISSOURI("Missouri", "MO"), MONTANA("Montana", "MT"), NEBRASKA("Nebraska",
	 * "NE"), NEVADA("Nevada", "NV"), NEW_HAMPSHIRE("New Hampshire", "NH"),
	 * NEW_JERSEY("New Jersey", "NJ"), NEW_MEXICO("New Mexico", "NM"),
	 * NEW_YORK("New York", "NY"), NORTH_CAROLINA("North Carolina", "NC"),
	 * NORTH_DAKOTA("North Dakota", "ND"),
	 * NORTHERN_MARIANA_ISLANDS("Northern Mariana Islands", "MP"), OHIO("Ohio",
	 * "OH"), OKLAHOMA("Oklahoma", "OK"), OREGON("Oregon", "OR"), PALAU("Palau",
	 * "PW"), PENNSYLVANIA("Pennsylvania", "PA"), PUERTO_RICO("Puerto Rico", "PR"),
	 * RHODE_ISLAND("Rhode Island", "RI"), SOUTH_CAROLINA("South Carolina", "SC"),
	 * SOUTH_DAKOTA("South Dakota", "SD"), TENNESSEE("Tennessee", "TN"),
	 * TEXAS("Texas", "TX"), UTAH("Utah", "UT"), VERMONT("Vermont", "VT"),
	 * VIRGIN_ISLANDS("Virgin Islands", "VI"), VIRGINIA("Virginia", "VA"),
	 * WASHINGTON("Washington", "WA"), WEST_VIRGINIA("West Virginia", "WV"),
	 * WISCONSIN("Wisconsin", "WI"), WYOMING("Wyoming", "WY"), UNKNOWN("Unknown",
	 * "Unknown");
	 * 
	 * private String name; private String abbreviation;
	 * 
	 * private StateMap(String name, String abbreviation) { this.name = name;
	 * this.abbreviation = abbreviation; }
	 * 
	 * public String getName() { return name; }
	 * 
	 * public String getAbbreviation() { return abbreviation; }
	 * 
	 * }
	 */
	
	private DataFormatter() {}
	
	public static String getFullName(String fn, String ln)
	{
		return fn + " " + ln;
	}

	public static int getDuration(LocalDate date, ChronoUnit unit)
	{
		return (int) date.until(LocalDate.now(), unit);
	}

	public static int getDuration(LocalDate firstDate, LocalDate secondDate, ChronoUnit unit)
	{
		return (int) firstDate.until(secondDate, unit);
	}
	
	public static boolean validatePhoneNumber(String phoneNumber)
	{
		if ( phoneNumber.matches("\\d{10}") )	// Validate phone numbers of format "1234567890"
		{
			return true;
		} else if ( phoneNumber.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}") )	// Validating phone number with -, . or spaces
		{
			return true;
		} else if ( phoneNumber.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}") )	// Validating phone number with extension length from 3 to 5
		{
			return true;
		} else if ( phoneNumber.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}") )		// Validating phone number where area code is in braces ()
		{
			return true;
		} else
		{
			return false;
		}
	}

	public static boolean validateEmail(String email)
	{
		return email.matches("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$");
	}
	
	public static boolean validateZipCode(String zip)
	{
		if ( zip != null && !zip.isEmpty() )
		{
			int zipcode = Integer.parseInt(zip);

			if ( zipcode > 500 && zipcode < 99951 )
			{
				return zip.matches("^[0-9]{5}$");
			}
		}

		return false;
	}

	public static String capitalize(String word)
	{
	   return Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase();
	}

	public static String getDisplayCurrencyValue(BigDecimal currency)
	{
		BigDecimal convertedVal = currency.setScale(2, RoundingMode.HALF_EVEN);
		NumberFormat usdCostFormat = NumberFormat.getCurrencyInstance(Locale.US);

		usdCostFormat.setMinimumFractionDigits(1);
		usdCostFormat.setMaximumFractionDigits(2);

		String displayCurrencyValue = usdCostFormat.format(convertedVal.doubleValue());

		return displayCurrencyValue;
	}

	public static String getDisplayCurrencyValue(BigDecimal currency, Locale currencyType)
	{
		BigDecimal convertedVal = currency.setScale(2, RoundingMode.HALF_EVEN);
		NumberFormat usdCostFormat = NumberFormat.getCurrencyInstance(currencyType);

		usdCostFormat.setMinimumFractionDigits(1);
		usdCostFormat.setMaximumFractionDigits(2);

		String displayCurrencyValue = usdCostFormat.format(convertedVal.doubleValue());

		return displayCurrencyValue;
	}

	public static boolean isEmpty(String str)
	{
		return str == null || str.trim().isEmpty();
	}

	static Object[] copy(Object[] arr)
	{
		int arrLen = arr.length;
		Object[] copiedArr = new Object[arrLen];
	
		for ( int i = 0; i < arrLen; i++ )
		{
			copiedArr[i] = arr[i];
		}
	
		return copiedArr;
	}

	static Object[] copy(Object[] arr, int arrLen)
	{
		Object[] copiedArr = new Object[arrLen];
	
		for ( int i = 0; i < arr.length; i++ )
		{
			copiedArr[i] = arr[i];
		}
	
		return copiedArr;
	}

	static Object[] copy(Object[] arr, int start, int end)
	{
		int arrLen = end - start;
		Object[] copiedArr = new Object[arrLen];
		int copiedArrIndex = 0;

		for ( int i = start; i < end; i++ )
		{
			copiedArr[copiedArrIndex++] = arr[i];
		}

		return copiedArr;
	}

	public static Object[] add(Object[] arr, Object val)
	{
		int arrLen = arr.length;
		int newArrLen = arrLen + 1;

		Object[] newArr = copy(arr, newArrLen);

		newArr[arrLen] = val;

		return newArr;
	}

	public static Object[] merge(Object[] arr1, Object[] arr2)
	{
		int arr1Len = arr1.length;
		int arr2Len = arr2.length;
		Object[] mergedArr = new Object[arr1Len + arr2Len];

		for( int i = 0; i < arr1Len; i++ )
		{
			mergedArr[i] = arr1[i];
		}

		int j = arr1Len;
		for ( int i = 0; i < arr2Len; i++ )
		{
			mergedArr[j++] = arr2[i];
		}

		return mergedArr;
	}

}
