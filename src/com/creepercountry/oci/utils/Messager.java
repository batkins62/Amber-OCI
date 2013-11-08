package com.creepercountry.oci.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.command.CommandSender;

import com.creepercountry.oci.main.OCIInfo;

public class Messager
{
	/**
	 * Logger static instance
	 */
	private static final Logger log = Logger.getLogger("ccTowns");
	
	/**
	 * Send a message to a CommandSender (can be a player or console).
	 *
	 * @param player sender to send to
	 * @param msg message to send
	 * @param clr color of message
	 */
	public static void sendMessage(CommandSender player, String clr, String msg)
	{
		if (player != null)
		{
			player.sendMessage(clr + msg);
		}
		// TODO: add in line-length checking, color wrapping etc
	}

	/**
	 * Send a severe level stacktrace to console
	 * includes: version
	 *
	 * @param msg
	 * @param ex
	 */
	public static void severe(String msg, NoSuchFieldError ex)
	{
		log.log(Level.SEVERE, "[ccTowns v" + OCIInfo.FULL_VERSION.toString() + "] " + msg + ":", ex);
	}

	/**
	 * Send an info level log message to console
	 * includes: version 
	 *
	 * @param msg
	 */
	public static void info(String msg)
	{
		final StringBuilder out = new StringBuilder();
		out.append("[ccTowns v" + OCIInfo.FULL_VERSION.toString() + "] ");
		out.append(msg);
		log.log(Level.INFO, out.toString());
	}
}
