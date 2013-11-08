package com.creepercountry.oci.object.defcon;

import org.bukkit.command.CommandSender;

import com.creepercountry.oci.main.OCIEngine;
import com.creepercountry.oci.main.OCIPlugin;
import com.creepercountry.oci.main.config.ConfigNode;
import com.creepercountry.oci.main.hooks.Vault;
import com.creepercountry.oci.utils.DateRanger;

public final class PluginDefcon
{
    private final OCIPlugin owningPlugin = OCIPlugin.getInstance();
    private DefconExecutor executor;
    private DefconLevels defconlevels;
    private Defcon current;

    /**
	 * Execute the defcon level returning its success.
	 * 
	 * @param sender <code>Player who started defcon</code>
	 * @param level <code></code>
	 * @param duration <code>how long it will last in seconds</code>
	 * @param live <code>if this is an exersize or the real event</code>
	 * @return true if defcon is active
	 */
    public boolean execute(CommandSender sender, int level, long duration, boolean live)
    {
        boolean success = false;
        
        if (!OCIEngine.DEFCONACTIVE)
        	return false;
        
        String permission = null;
        switch (level)
        {
        	case 1:	permission = ConfigNode.DEFCON1_PERMISSION.getPath();
        			defconlevels = DefconLevels.DEFCON1;
        			break;
        	case 2: permission = ConfigNode.DEFCON2_PERMISSION.getPath();
        			defconlevels = DefconLevels.DEFCON2;
        			break;
        	case 3: permission = ConfigNode.DEFCON3_PERMISSION.getPath();
        			defconlevels = DefconLevels.DEFCON3;
        			break;
        	case 4: permission = ConfigNode.DEFCON4_PERMISSION.getPath();
        			defconlevels = DefconLevels.DEFCON4;
        			break;
        	case 5: permission = ConfigNode.DEFCON5_PERMISSION.getPath();
        			defconlevels = DefconLevels.DEFCON5;
        			break;
        		
        	default: return false;
        }
        
        if (!owningPlugin.isEnabled() || !OCIEngine.ENABLED) {
            return false;
        }

        if (!Vault.perms.has(sender, permission))
        {
            return true;
        }

        success = executor.onRun(sender, defconlevels, new DateRanger(duration), live);

        return success;
    }

    /**
     * Sets the {@link DefconExecutor} to run when parsing this defcon
     *
     * @param executor New executor to run
     */
    public void setExecutor(DefconLevelExecutor executor)
    {
        this.executor = executor;
    }

    /**
     * Gets the {@link DefconLevelExecutor} associated with this command
     *
     * @return CommandExecutor object linked to this command
     */
    public DefconExecutor getExecutor()
    {
        return executor;
    }
    
    /**
     * Set the current defcon object
     * 
     * @param cur the object
     */
    public void setCurrent(Defcon cur)
    {
    	this.current = cur;
    }
    
    /**
     * @return current defcon object
     */
    public Defcon getCurrent()
    {
    	return current;
    }

    /**
     * Gets the owner of this PluginCommand
     *
     * @return Plugin that owns this command
     */
    public OCIPlugin getPlugin()
    {
        return owningPlugin;
    }

    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder(super.toString());
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append(", ").append(owningPlugin.getDescription().getFullName()).append(')');
        return stringBuilder.toString();
    }
}