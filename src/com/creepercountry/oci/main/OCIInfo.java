package com.creepercountry.oci.main;

import com.creepercountry.oci.utils.Version;

public class OCIInfo
{
	/**
	 * The full version
	 */
    public static Version FULL_VERSION;

    /**
     * set plugin version
     * 
     * @param version
     */
    public static void setVersion(String version)
    {
        String implementationVersion = OCIPlugin.class.getPackage().getImplementationVersion();
        FULL_VERSION = new Version(version + " " + implementationVersion);
    }
}
