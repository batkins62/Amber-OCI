package com.creepercountry.oci.main.config;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import com.creepercountry.oci.main.OCIInfo;
import com.creepercountry.oci.main.OCIPlugin;

public final class GlobalConfig
{
	public static String plugin_version, defcon1description, defcon2description, defcon3description, defcon4description, defcon5description,
					defcon1usage, defcon2usage, defcon3usage, defcon4usage, defcon5usage, defcon1permision, defcon2permision,
					defcon3permision, defcon4permision, defcon5permision;
	public static int config_version, defcon_current;
	public static long config_token;
	public static boolean plugin_running, debug, first_run, vault_depend, defcon_active;
	public static List<String> defcon1aliases, defcon2aliases, defcon3aliases, defcon4aliases, defcon5aliases;
	
    private final OCIPlugin plugin;
    public boolean ENABLED = false;
    
    public GlobalConfig(OCIPlugin instance)
    {
    	this.plugin = instance;
    	ENABLED = true;
    	load();
    }
    
    public boolean load()
    {
    	// Grab config, copy just the defaults
    	FileConfiguration config = plugin.getConfig();
        config.options().copyDefaults(true);
        
        // Load String variables
        plugin_version = config.getString(ConfigNode.PLUGIN_VERSION.getPath(), OCIInfo.FULL_VERSION.toString());
        defcon1description = config.getString(ConfigNode.DEFCON1_DESCRIPTION.getPath(), "defcon1");
        defcon2description = config.getString(ConfigNode.DEFCON2_DESCRIPTION.getPath(), "defcon2");
        defcon3description = config.getString(ConfigNode.DEFCON3_DESCRIPTION.getPath(), "defcon3");
        defcon4description = config.getString(ConfigNode.DEFCON4_DESCRIPTION.getPath(), "defcon4");
        defcon5description = config.getString(ConfigNode.DEFCON5_DESCRIPTION.getPath(), "defcon5");
        defcon1usage = config.getString(ConfigNode.DEFCON1_USAGE.getPath(), "ERROR! Defcon failed to initiate.");
        defcon2usage = config.getString(ConfigNode.DEFCON2_USAGE.getPath(), "ERROR! Defcon failed to initiate.");
        defcon3usage = config.getString(ConfigNode.DEFCON3_USAGE.getPath(), "ERROR! Defcon failed to initiate.");
        defcon4usage = config.getString(ConfigNode.DEFCON4_USAGE.getPath(), "ERROR! Defcon failed to initiate.");
        defcon5usage = config.getString(ConfigNode.DEFCON5_USAGE.getPath(), "ERROR! Defcon failed to initiate.");
        defcon1permision = config.getString(ConfigNode.DEFCON1_PERMISSION.getPath(), "oci.defcon.one");
        defcon2permision = config.getString(ConfigNode.DEFCON2_PERMISSION.getPath(), "oci.defcon.two");
        defcon3permision = config.getString(ConfigNode.DEFCON3_PERMISSION.getPath(), "oci.defcon.three");
        defcon4permision = config.getString(ConfigNode.DEFCON4_PERMISSION.getPath(), "oci.defcon.four");
        defcon5permision = config.getString(ConfigNode.DEFCON5_PERMISSION.getPath(), "oci.defcon.five");
        // Load int variables
        config_version = config.getInt(ConfigNode.CONFIG_VERSION.getPath(), 1);
        defcon_current = config.getInt(ConfigNode.DEFCON_CURRENT.getPath(), 5);
    	// Load long variables
        config_token = config.getLong(ConfigNode.CONFIG_TOKEN.getPath(), 0);
     	// Load boolean variables
        plugin_running = config.getBoolean(ConfigNode.PLUGIN_RUNNING.getPath(), false);
        debug = config.getBoolean(ConfigNode.DEBUG.getPath(), false);
        first_run = config.getBoolean(ConfigNode.FIRST_RUN.getPath(), true);
        vault_depend = config.getBoolean(ConfigNode.VAULT_DEPEND.getPath(), false);
        defcon_active = config.getBoolean(ConfigNode.DEFCON_ACTIVE.getPath(), false);
        // Load List variables
        defcon1aliases = config.getStringList(ConfigNode.DEFCON1_ALIAS.getPath());
        defcon2aliases = config.getStringList(ConfigNode.DEFCON2_ALIAS.getPath());
        defcon3aliases = config.getStringList(ConfigNode.DEFCON3_ALIAS.getPath());
        defcon4aliases = config.getStringList(ConfigNode.DEFCON4_ALIAS.getPath());
        defcon5aliases = config.getStringList(ConfigNode.DEFCON5_ALIAS.getPath());
        
    	return true;
    }
}