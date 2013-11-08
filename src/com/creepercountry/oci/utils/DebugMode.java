package com.creepercountry.oci.utils;

import java.util.logging.Logger;

public class DebugMode
{
    private final Logger logger;
    public static DebugMode instance = null;
    
    /**
     * Constructor
     */
    public DebugMode()
    {
        this.logger = Logger.getLogger("OCI");
    }

    public static void go()
    {
    	//TODO: Throws null error
        //BukkitUtils.warning("Debug enabled. Disable via /towns debug off");
        DebugMode.instance = new DebugMode();
    }

    public static void log(String message)
    {
        if (DebugMode.instance != null)
        {
        	DebugMode.instance.logger.info("[OCI DEBUG] " + message);
        }
    }

    public static void stop()
    {
    	DebugMode.instance = null;
    }
}