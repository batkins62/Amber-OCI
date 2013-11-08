package com.creepercountry.oci.object.defcon;

import com.creepercountry.oci.utils.Colors;

public enum DefconLevels
{
	DEFCON1 (1, "COCKED PISTOL", Colors.White),
    DEFCON2 (2, "FAST PACE", Colors.Red),
    DEFCON3 (3, "ROUND HOUSE", Colors.Yellow),
    DEFCON4 (4, "DOUBLE TAKE", Colors.Green),
    DEFCON5 (5, "FADE OUT", Colors.Blue);

    private final String exersizeterm;
    private final String colors;
    private final int level;
    
    DefconLevels(int lvl, String term, String clr)
    {
        this.exersizeterm = term;
        this.colors = clr;
        this.level = lvl;
    }
    
    public String getExcersizeTerm()
    {
    	return this.exersizeterm;
    }
    
    public String getColor()
    {
    	return this.colors;
    }
    
    public int getLevel()
    {
    	return this.level;
    }
}
