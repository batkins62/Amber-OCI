package com.creepercountry.oci.utils.exceptions;

public class NotRegisteredException extends Exception
{
	private static final long serialVersionUID = 170005283391669005L;

	public NotRegisteredException()
	{
		super("Not registered.");
	}

	public NotRegisteredException(String message)
	{
		super(message);
	}
}