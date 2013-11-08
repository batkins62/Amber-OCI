package com.creepercountry.oci.object;

import java.util.Hashtable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.creepercountry.oci.main.OCIPlugin;
import com.creepercountry.oci.object.transmittion.Letter;
import com.creepercountry.oci.storage.DataSource;
import com.creepercountry.oci.storage.FileMgmt;
import com.creepercountry.oci.storage.FlatFileSource;
import com.creepercountry.oci.utils.exceptions.AlreadyRegisteredException;
import com.creepercountry.oci.utils.exceptions.NotRegisteredException;

public class GlobalHandler extends MainObject
{
	private static OCIPlugin plugin;
	
	protected Hashtable<String, Letter> letters = new Hashtable<String, Letter>();
	protected Hashtable<String, User> users = new Hashtable<String, User>();
	
	private static DataSource dataSource;
	private String rootFolder;
	
	public GlobalHandler()
	{
		setName("");
		rootFolder = "";
	}

	public GlobalHandler(String rootFolder)
	{
		setName("");
		this.rootFolder = rootFolder;
	}

	public GlobalHandler(OCIPlugin plugin)
	{
		setName("");
		GlobalHandler.plugin = plugin;
	}
	
	public void onLogin(Player player) throws AlreadyRegisteredException, NotRegisteredException
	{
		
	}
	
	public void onLogout(Player player)
	{
		
	}
	
	public static Player getPlayer(User user) throws Exception
	{
		for (Player player : Bukkit.getOnlinePlayers())
			if (player.getName().equals(user.getName()))
				return player;
		
		throw new Exception(String.format("%s is not online", user.getName()));
	}

	public boolean loadSettings()
	{
		System.out.println("[OCI] Loading Settings...");
		FileMgmt.checkFolders(new String[] { getRootFolder(), getRootFolder() });

		users.clear();
		letters.clear();
		System.out.println("[OCI] Cleared variables.");
		
		if (!loadDatabase("FlatFileSource"))
		{
			System.out.println("[OCI] Error: Failed to load!");
			return false;
		}

		return true;
	}
	
	public String getRootFolder()
	{
		if (plugin != null)
			return plugin.getDataFolder().getPath();
		else
			return rootFolder;
	}
	
	public boolean loadDatabase(String databaseType)
	{
		System.out.println("[OCI] Loading Database...");
		try
		{
			setDataSource(databaseType);
		}
		catch (UnsupportedOperationException e)
		{
			return false;
		}
		getDataSource().initialize(plugin, this);

		return getDataSource().loadAll();
	}

	public void setDataSource(String databaseType)
	{
		setDataSource(new FlatFileSource());
	}

	public void setDataSource(DataSource dataSource)
	{
		GlobalHandler.dataSource = dataSource;
	}
	
	public static DataSource getDataSource()
	{
		return dataSource;
	}
	
	/**
	 * @return Hashtable of Letters
	 */
	public Hashtable<String, Letter> getLetterMap()
	{
		return this.letters;
	}
	
	/**
	 * @return Hashtable of users
	 */
	public Hashtable<String, User> getUserMap()
	{
		return this.users;
	}
	
	public static OCIPlugin getPlugin()
	{
		return plugin;
	}

	public void setPlugin(OCIPlugin plugin)
	{
		GlobalHandler.plugin = plugin;
	}
}
