package com.creepercountry.oci.object;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class User extends MainObject
{
	private List<String> ip = new ArrayList<String>();
	private List<String> note = new ArrayList<String>();
	private long lastOnline, lastAccessed;
	private String lastAccesser;

    public User(String name)
    {
    	setName(name);
    }

    public void setLastOnline(long lastOnline)
    {
    	this.lastOnline = lastOnline;
    }
    
    public long getLastOnline()
    {
    	return lastOnline;
    }
    
    public void setLastAccess(String player)
    {
    	this.lastAccesser = player;
    	this.lastAccessed = System.currentTimeMillis();
    }
    
    public String getLastAccesser()
    {
    	return this.lastAccesser;
    }
    
    public long getLastAccessed()
    {
    	return this.lastAccessed;
    }
    
	public List<String> getIps()
	{
		return ip;
	}
	
	public List<String> getNotes()
	{
		return note;
	}
	
	public void addNote(String notes)
	{
		note.add(notes);
	}
	
	public void addIP(InetSocketAddress isa)
	{
		ip.add(isa.toString());
	}
	
	public void addIP(String isa)
	{
		ip.add(isa.toString());
	}
	
	public boolean hasIP(InetSocketAddress isa)
	{
		return ip.contains(isa.toString());
	}

	public void clear()
	{
		note.clear();
		ip.clear();
	}
	
	public List<String> getTreeString(int depth)
	{
		List<String> out = new ArrayList<String>();
		out.add(getTreeDepth(depth) + "User ("+getName()+")");
		out.add(getTreeDepth(depth+1) + "Last Online: " + getLastOnline());
		return out;
	}
}