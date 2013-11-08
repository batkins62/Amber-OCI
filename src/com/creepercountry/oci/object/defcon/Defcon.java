package com.creepercountry.oci.object.defcon;

import org.bukkit.command.CommandSender;

import com.creepercountry.oci.main.OCIPlugin;
import com.creepercountry.oci.main.config.GlobalConfig;
import com.creepercountry.oci.utils.Colors;
import com.creepercountry.oci.utils.DateRanger;
import com.creepercountry.oci.utils.Messager;
import com.creepercountry.oci.utils.StopWatch;

/**
* Abstract class representing a command. When run by the command manager (
* {@link CommandExecutor}), it pre-processes all the data into more useful forms.
* Extending classes should adjust required fields in their constructor
*
*/
public abstract class Defcon
{
	protected final OCIPlugin plugin = OCIPlugin.getInstance();
	protected StopWatch sw = plugin.getStopWatch();
	protected CommandSender sender;
	protected String excersizeterm;
	protected String color;
	protected String exersizeterm;
	protected DateRanger duration;
	// Commands below can be set by each individual command
	public String dconlevel;
	public boolean allowUpdate = true;

	/**
	 * Method called by the command manager in {@link OCIPlugin} to run the
	 * command. Arguments are processed into a list for easier manipulating.
	 * Argument lengths, permissions and sender types are all handled.
	 *
	 * @param csender
	 * {@link CommandSender} to send data to
	 * @param dur arguments to be expired
	 * @param level defcon to being executed
	 * @return true on success, false if there is an error in the checks or if
	 * the extending defcon returns false
	 */
	public boolean run(CommandSender csender, DefconLevels level, DateRanger dur, boolean live)
	{
		// Get the current time for StopWatch
		long start = System.nanoTime();
		
		sender = csender;
		dconlevel = level.toString();
		excersizeterm = level.getExcersizeTerm();
		color = level.getColor();
		duration = dur;
		
		if (level.getLevel() == GlobalConfig.defcon_current)
		{
			Messager.sendMessage(csender, Colors.Red, "Already in defcon " + GlobalConfig.defcon_current);
			
			if (allowUpdate)
			{
				Long seconds = plugin.getDefcon().getCurrent().duration.addDuration(duration.getDuration());
				Messager.sendMessage(csender, Colors.Gold, "Updating duration to: " + seconds);
			}
		}
		
		// set current defcon object
		plugin.getDefcon().setCurrent(this);
		
		// log to StopWatch
        sw.setLoad("defconbase", System.nanoTime() - start);
        
        if (live)
        	return execute();
        
        return exercise();
	}

	/**
	 * Runs the extending command. Should only be run by the Defcon after
	 * all pre-processing is done
	 *
	 * @return true on success, false otherwise
	 */
	public abstract boolean execute();

	/**
	 * Runs the extending command as an exercise. Should only be run by the Defcon after
	 * all pre-processing is done
	 *
	 * @return true on success, false otherwise
	 */
	public abstract boolean exercise();

	public abstract Defcon newInstance();
	
	/**
	 * Formats a string with a provided title and padding and centers title.
	 * 
	 * @param title
	 * @param fill
	 * @return
	 */
    protected String cleanTitle(String title, String fill)
    {
        int chatWidthMax = 53; 											// Vanilla client line character max
        int titleWidth = title.length() + 2; 							// Title's character width with 2 spaces padding
        int fillWidth = (int) ((chatWidthMax - titleWidth) / 2D); 		// Fill string calculation for padding either side
        String cleanTitle = "";
        
        for(int i = 0; i < fillWidth; i++)
            cleanTitle += fill;
        cleanTitle += " " + Colors.Gold + title + Colors.Rose + " ";
        for(int i = 0; i < fillWidth; i++)
            cleanTitle += fill;
        
        return cleanTitle;
    }
}