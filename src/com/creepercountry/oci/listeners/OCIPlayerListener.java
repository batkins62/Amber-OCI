package com.creepercountry.oci.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.creepercountry.oci.main.OCIEngine;
import com.creepercountry.oci.main.OCIPlugin;
import com.creepercountry.oci.utils.Colors;
import com.creepercountry.oci.utils.Messager;

public class OCIPlayerListener implements Listener
{
	/**
     * The plugin instance
     */
	private OCIPlugin plugin;
	
	/**
	 * The StopWatch object
	 */
	//TODO:private StopWatch sw;

	/**
	 * constructor
	 * @param plugin
	 */
	public OCIPlayerListener(OCIPlugin instance)
    {
		plugin = instance;
		//TODO:sw = instance.getStopWatch();
    }

	@EventHandler // EventPriority.NORMAL by default
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();

		if (!OCIEngine.ENABLED)
		{
			player.kickPlayer("Error x50OCIPL: count to 10 then try again.");
		}
		
		try
		{
			plugin.getGlobalHandler().onLogin(player);
		}
		catch (Exception x)
		{
			Messager.sendMessage(player, Colors.Red, x.getMessage());
		}
	}

	@EventHandler // EventPriority.NORMAL by default
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		plugin.getGlobalHandler().onLogout(event.getPlayer());
	}	
}
