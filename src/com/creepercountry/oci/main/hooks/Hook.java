package com.creepercountry.oci.main.hooks;

import org.bukkit.plugin.Plugin;

import com.creepercountry.oci.main.OCIPlugin;

public interface Hook
{
	public void onEnable(OCIPlugin plugin);
	
	public void onDisable(OCIPlugin plugin);
	
	public int getUniqueID();
	
	public String getName();
	
	public String toString();
	
	public boolean isEnabled();
	
	public Plugin getPlugin();
}
