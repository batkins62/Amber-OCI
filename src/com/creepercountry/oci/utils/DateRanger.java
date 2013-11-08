package com.creepercountry.oci.utils;

import java.util.concurrent.TimeUnit;

public class DateRanger
{
	private long s, d, e;
	
	public DateRanger(long start, long seconds)
	{
		this.s = start;
		this.d = TimeUnit.MILLISECONDS.convert(seconds, TimeUnit.SECONDS);
		this.e = s + d;
	}
	
	public DateRanger(long seconds)
	{
		this.s = System.currentTimeMillis();
		this.d = TimeUnit.MILLISECONDS.convert(seconds, TimeUnit.SECONDS);
		this.e = s + d;
	}
	
	/**
	 * Add duration to the range.
	 * 
	 * @param seconds to be added
	 * @return the duration thats final
	 */
	public Long addDuration(long seconds)
	{
		long dur = TimeUnit.MILLISECONDS.convert(seconds, TimeUnit.SECONDS);
		this.d = d + dur;
		this.e = s + d;
		return this.d;
	}
	
	/**
	 * Is final after duration passed current time.
	 * 
	 * @return true if duration has passed start
	 */
	public boolean isFinal()
	{
		return (System.currentTimeMillis() >= e) ? true : false;
	}
	
	/**
	 * @return the start time in milliseconds
	 */
	public long getStart()
	{
		return this.s;
	}
	
	/**
	 * @return the end time in milliseconds
	 */
	public long getEnd()
	{
		return this.e;
	}
	
	/**
	 * @return the duration in milliseconds
	 */
	public long getDuration()
	{
		return this.d;
	}
}