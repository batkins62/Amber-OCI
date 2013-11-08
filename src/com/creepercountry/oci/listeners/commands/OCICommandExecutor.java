package com.creepercountry.oci.listeners.commands;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.creepercountry.oci.main.OCIPlugin;
import com.creepercountry.oci.utils.StopWatch;

import java.util.ArrayList;

public class OCICommandExecutor implements CommandExecutor
{
	private StopWatch sw = OCIPlugin.getInstance().getStopWatch();
	private List<BaseCommand> commands = new ArrayList<BaseCommand>();

	public OCICommandExecutor()
	{
		// Get the current time for StopWatch
		long start = System.nanoTime();
		
		// Register commands
		commands.add(new HelpCommand());
		commands.add(new OrderCommand());
		commands.add(new BodyCommand());
		commands.add(new InteligenceCommand());
		
		// log to StopWatch
        sw.setLoad("GenCommandExecutor", System.nanoTime() - start);
	}

	/**
	 * Command manager
	 *
	 * @param sender - {@link CommandSender}
	 * @param command - {@link Command}
	 * @param label command name
	 * @param args arguments
	 */
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		// Get the current time for StopWatch
		long start = System.nanoTime();
		
		// If no arg provided for command, set to help by default
		if (args.length == 0)
		{
			args = new String[]{"help"};
		}

		// Loop through commands to find match. Supports sub-commands
		BaseCommand townCmd;
		BaseCommand[] guardCmdArray = commands.toArray(new BaseCommand[commands.size()]);
		int index = 0;
		String[] tempArgs = args;

		while (index < guardCmdArray.length && tempArgs.length > 0)
		{
			townCmd = guardCmdArray[index];
			if(tempArgs[0].equalsIgnoreCase(townCmd.name))
			{
				if(townCmd.subCommands.size() > 0 && tempArgs.length > 1)
				{
					guardCmdArray = townCmd.subCommands.toArray(new BaseCommand[townCmd.subCommands.size()]);
					index = 0;
					tempArgs = (String[]) ArrayUtils.remove(tempArgs, 0);
				}
				else
				{
					tempArgs = (String[]) ArrayUtils.remove(tempArgs, 0);
					
					// log to StopWatch
			        sw.setLoad("genOnCommand", System.nanoTime() - start);
			        
					return townCmd.newInstance().run(sender, tempArgs, label);
				}
			}
			else
			{
				index++;
			}
		}

		new HelpCommand().run(sender, args, label);
		
		// log to StopWatch
        sw.setLoad("genOnCommand", System.nanoTime() - start);
        
		return true;
	}

	/**
	 * @return the commands
	 */
	public List<BaseCommand> getCommands()
	{
		return commands;
	}
}