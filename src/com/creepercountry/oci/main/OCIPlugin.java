package com.creepercountry.oci.main;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.creepercountry.oci.listeners.OCIPlayerListener;
import com.creepercountry.oci.listeners.commands.OCICommandExecutor;
import com.creepercountry.oci.main.OCIEngine;
import com.creepercountry.oci.main.OCIInfo;
import com.creepercountry.oci.main.config.ConfigNode;
import com.creepercountry.oci.main.config.GlobalConfig;
import com.creepercountry.oci.main.hooks.DependancyHandler;
import com.creepercountry.oci.main.hooks.Hook;
import com.creepercountry.oci.main.hooks.Vault;
import com.creepercountry.oci.object.GlobalHandler;
import com.creepercountry.oci.object.defcon.DefconLevelExecutor;
import com.creepercountry.oci.object.defcon.PluginDefcon;
import com.creepercountry.oci.utils.Colors;
import com.creepercountry.oci.utils.DebugMode;
import com.creepercountry.oci.utils.Messager;
import com.creepercountry.oci.utils.StopWatch;
import com.creepercountry.oci.utils.Version;
import com.creepercountry.oci.utils.exceptions.NoDependancyException;

public class OCIPlugin extends JavaPlugin
{
    /**
     * Our Plugin instance
     */
    private static OCIPlugin instance;
    
    /**
     * The StopWatch object
     */
    private StopWatch sw;
    
    /**
     * The engine instance
     */
    private OCIEngine engine;
    
    /**
     * Handler list
     */
	private GlobalHandler globalHandler;
	private DependancyHandler dependHandler;
    
    /**
     * Main Config & config version
     */
    public static int cversion = 1;
    private GlobalConfig global;
    
    /**
     * The listeners
     */
    private OCIPlayerListener playerListener;
    
    /**
     * The Executors
     */
    private OCICommandExecutor commandExecutor;
    private DefconLevelExecutor dconExecutor;
    
    /**
     * Defcon instance
     */
    private PluginDefcon pd;
    
	@Override
    public void onEnable()
    {
		// Get the current time for StopWatch
		long start = System.nanoTime();
		
		// load our config. start the instance. start the StopWatch
		instance = this;
		sw = new StopWatch(this.getDescription().getName());
		if (!load())
			return;
		
		// Are we debuging?
		if (getConfig().getBoolean(ConfigNode.DEBUG.getPath()))
		{
			DebugMode.go();
		}
		
		// load the towns
		globalHandler = new GlobalHandler(this);
		if (globalHandler.loadSettings())
			Messager.info("loaded settings");
		
		// hook into depends
		pluginHooks();

		// register the listeners & executors
        try
        {
            registerEvents();
            registerCommands();
            registerDefcons();
        }
        catch (NoSuchFieldError e)
        {
        	Messager.severe("NoSuchFieldError", e);
        }
        
        // Are we using defcon?
     	if (getConfig().getBoolean(ConfigNode.DEFCON_ACTIVE.getPath()))
     	{
     		OCIEngine.DEFCONACTIVE = true;
     		int level = getConfig().getInt(ConfigNode.DEFCON_CURRENT.getPath(), 5);
     		pd.execute(getServer().getConsoleSender(), level, -1, true);
     	}

		// set version, get version, and display
		OCIInfo.setVersion(this.getDescription().getVersion());
		Version version = OCIInfo.FULL_VERSION;
		getConfig().set("plugin.pluginversion", version.toString());
		System.out.println("At version: " + version.toString());

		// Start the engine
		OCIEngine.ENABLED = true;
		
		// Re login anyone online. (In case of plugin reloading)
		for (Player player : getServer().getOnlinePlayers())
		{
			try
			{
				getGlobalHandler().onLogin(player);
			}
			catch (Exception x)
			{
				Messager.sendMessage(player, Colors.Red, x.getMessage());
			}
		}
		
		// output to StopWatch
		sw.setLoadNoChirp("onEnable", System.nanoTime() - start, false);
    }
     
	@Override
    public void onDisable()
    {
		// Get the current time for StopWatch
		long start = System.nanoTime();
				
		// Disable the plugin
		OCIEngine.ENABLED = false;
		
		// logout anyone online. (In case of plugin reloading)
		for (Player player : getServer().getOnlinePlayers())
		{
			getGlobalHandler().onLogout(player);
		}
		
		// unregister hooks
        for (Hook hook : dependHandler.getRegistered())
        {
        	hook.onDisable(this);
        	dependHandler.unregisterHook(hook.getName());
        }
		
		// Save our storage files
		GlobalHandler.getDataSource().saveAll();
		
		// Stop Debug
		DebugMode.stop();
		
		// cancel ALL tasks we created
        getServer().getScheduler().cancelTasks(this);
        
        // nullify the universe, releasing all of its memory
        globalHandler = null;
        
        // unregister our events
        HandlerList.unregisterAll(this);
        
        // Finalize the StopWatch then output data
        sw.setLoad("onDisable", System.nanoTime() - start);
        Messager.info(sw.output());
    }
	
    /**
     * Register all of the events used
     */
    private void registerEvents()
    {
    	// Get the current time for StopWatch
    	long start = System.nanoTime();
    			
        // Shared Objects
        playerListener = new OCIPlayerListener(this);
        
        // register event listeners
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(playerListener, this);
        
        // debug what we registered
        //TODO: log debug of loaded
        
        // log to StopWatch
        sw.setLoadNoChirp("registerEvents", (System.nanoTime() - start));
    }
    
    /**
     * Register all of the commands used, point them to executor
     */
    private void registerCommands()
    {
    	// Get the current time for StopWatch
    	long start = System.nanoTime();
    	
    	// Shared Ojects
    	commandExecutor = new OCICommandExecutor();
        
        // point commands to executor
        getCommand("intel").setExecutor(commandExecutor);
        
        // debug what we loaded
        //TODO: make this output what we registered
        
        // log to StopWatch
        sw.setLoadNoChirp("registerCommands", (System.nanoTime() - start));
    }
    
    /**
     * Register all the defcons levels, point them to the exector
     */
    private void registerDefcons()
    {
    	// Get the current time for StopWatch
    	long start = System.nanoTime();
    	
    	// Shared Ojects
    	dconExecutor = new DefconLevelExecutor();
    	
    	
    	
    	// log to StopWatch
        sw.setLoadNoChirp("registerDefcons", (System.nanoTime() - start));
    }

	/**
     * Check for required plugins to be loaded
     */
    private void pluginHooks()
    {
    	// Get the current time for StopWatch
    	long start = System.nanoTime();
    	
    	// Register the dependency
    	dependHandler = new DependancyHandler();
    	dependHandler.registerHook("Vault", new Vault());
        
        // Enable the dependencies
        for (Hook hook : dependHandler.getRegistered())
        	hook.onEnable(this);
        
        // log to StopWatch
        sw.setLoadNoChirp("pluginHooks", (System.nanoTime() - start));
    }
    
    /**
     *  load the config
     */
    public boolean load()
    {
    	global = new GlobalConfig(this);
    	
    	return true;
    }
    
    /**
     * checks if a player is online
     * 
     * @param playerName (string - use player.getName() if necessary)
     * @return false if player isnt online
     */
	public boolean isOnline(String playerName)
	{
		for (Player player : getServer().getOnlinePlayers())
			if (player.getName().equalsIgnoreCase(playerName))
				return true;

		return false;
	}
    
	/**
	 * @return TownUniverse
	 */
    public GlobalHandler getGlobalHandler()
    {
    	return globalHandler;
    }
    
    /**
     * @return the config
     */
    public GlobalConfig getConf()
    {
        return global;
    }
    
    /**
     * @return if we are using permissions
     */
    public boolean isPermissions()
    {
        return Vault.perms != null;
    }
    
    /**
     * @return the current plugin instance
     */
    public static OCIPlugin getInstance()
    {
        return instance;
    }
    
    /**
     * @return the engine instance
     */
    public OCIEngine getEngine()
    {
    	return engine;
    }

	/**
	 * @return the town command executor
	 */
	public OCICommandExecutor getOCICommandExecutor()
	{
		return commandExecutor;
	}
	
	/**
	 * @return the StopWatch object
	 */
	public StopWatch getStopWatch()
	{
		return sw;
	}
	
	/**
	 * @return the DependancyHandler object
	 */
	public DependancyHandler getDependancyHandler()
	{
		return dependHandler;
	}
	
	/**
	 * @return the PluginDefcon instance
	 */
	public PluginDefcon getDefcon()
	{
		return pd;
	}
	
	/**
	 * @return the Vault dependancy
	 */
	public Vault getVault()
	{
		try { return (Vault)dependHandler.getHook("Vault").getPlugin(); } catch (NoDependancyException e) { return null; }
	}
}
