package com.creepercountry.oci.object.transmittion;

public class Recipient
{
	public enum Type
	{
		P2P, BROADCAST;
	}
	
	private String player;
	private Type type;
	
	public Recipient(String player, Type type)
	{
		this.player = player;
		this.type = type;
	}
	
	public Type getType()
	{
		return this.type;
	}
	
	public String getName()
	{
		return this.player;
	}
	
	public String getRecipient()
	{
		if (type.equals(Type.BROADCAST))
			return "\'" + player + "\'" +  "<BROADCAST_NOTIFIER@internal>";
		return "";
	}
}
