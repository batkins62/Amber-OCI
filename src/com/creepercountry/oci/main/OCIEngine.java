package com.creepercountry.oci.main;

public class OCIEngine
{
    /**
     * If CTPlugin is currently enabled
     */
    public static boolean ENABLED = false;
    
    /**
     * If the virtual config has been writen to, but not hard saved
     */
    public static boolean CHANGEDCONFIG = false;
    
    /**
     * If the defcon is active, a non active state wont mater, even if level is 5
     */
    public static boolean DEFCONACTIVE = false;
}
