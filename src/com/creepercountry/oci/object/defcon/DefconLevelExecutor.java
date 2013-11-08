package com.creepercountry.oci.object.defcon;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.CommandSender;

import com.creepercountry.oci.main.OCIPlugin;
import com.creepercountry.oci.utils.DateRanger;
import com.creepercountry.oci.utils.StopWatch;

public class DefconLevelExecutor implements DefconExecutor
{
	private StopWatch sw = OCIPlugin.getInstance().getStopWatch();
	private List<Defcon> defcons = new ArrayList<Defcon>();
	
	public DefconLevelExecutor()
	{
		// Get the current time for StopWatch
		long start = System.nanoTime();
		
		// Register commands
		defcons.add(new Defcon1());
		defcons.add(new Defcon2());
		defcons.add(new Defcon3());
		defcons.add(new Defcon4());
		defcons.add(new Defcon5());
		
		// log to StopWatch
        sw.setLoad("GenCommandExecutor", System.nanoTime() - start);
	}
	
	@Override
	public boolean onRun(CommandSender sender, DefconLevels level, DateRanger duration, boolean live)
	{
		// Get the current time for StopWatch
		long start = System.nanoTime();

		// Loop through commands to find match. Supports sub-commands
		Defcon dc;
		Defcon[] DconArray = defcons.toArray(new Defcon[defcons.size()]);
		int index = 0;

		while (index < DconArray.length)
		{
			dc = DconArray[index];
			if (level.toString().equalsIgnoreCase(dc.dconlevel))
			{					
				// log to StopWatch
				sw.setLoad("defconexector", System.nanoTime() - start);
					        
				return dc.newInstance().run(sender, level, duration, live);
			}
			else
			{
				index++;
			}
		}
				
		// log to StopWatch
		sw.setLoad("defconexector", System.nanoTime() - start);
		        
		return false;
	}

	/**
	 * @return the defcons
	 */
	public List<Defcon> getDefcons()
	{
		return defcons;
	}
}
