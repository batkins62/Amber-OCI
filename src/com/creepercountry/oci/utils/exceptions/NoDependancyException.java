package com.creepercountry.oci.utils.exceptions;

public class NoDependancyException extends Exception
{
	private static final long serialVersionUID = -2821761201748544777L;

	public NoDependancyException()
	{
		super("unknown");
	}

	public NoDependancyException(String message)
	{
		super(message);
	}
}
