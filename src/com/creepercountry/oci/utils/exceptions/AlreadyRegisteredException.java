package com.creepercountry.oci.utils.exceptions;

public class AlreadyRegisteredException extends Exception
{
	private static final long serialVersionUID = 4191684452690881161L;

	public AlreadyRegisteredException()
	{
		super("Already registered.");
	}

	public AlreadyRegisteredException(String message)
	{
		super(message);
	}
}