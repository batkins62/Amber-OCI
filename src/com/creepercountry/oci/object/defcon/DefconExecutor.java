package com.creepercountry.oci.object.defcon;

import org.bukkit.command.CommandSender;

import com.creepercountry.oci.utils.DateRanger;

public interface DefconExecutor
{
	/**
	 * The Defcon Executor
	 * 
	 * @param sender <code>Player who started defcon</code>
	 * @param level <code>the defcon level</code>
	 * @param duration <code>how long it will last/code>
	 * @param live <code>if this is an exersize or the real event</code>
	 * @return true if defcon is active
	 */
	public boolean onRun(CommandSender sender, DefconLevels level, DateRanger duration, boolean live);
}
