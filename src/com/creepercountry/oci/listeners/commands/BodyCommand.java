package com.creepercountry.oci.listeners.commands;

import org.bukkit.command.CommandSender;

import com.creepercountry.oci.main.hooks.Vault;
import com.creepercountry.oci.object.GlobalHandler;
import com.creepercountry.oci.object.transmittion.Body;
import com.creepercountry.oci.object.transmittion.Letter;
import com.creepercountry.oci.utils.Colors;
import com.creepercountry.oci.utils.Messager;
import com.creepercountry.oci.utils.exceptions.NotRegisteredException;

public class BodyCommand extends BaseCommand
{
    public BodyCommand()
    {
        name = "body";
        usage = "<id> <message>";
        minArgs = 2;
    }

    @Override
    public boolean execute()
    {
    	try
    	{
    		Letter letter = GlobalHandler.getDataSource().getLetter(args.get(0));
    		Body body = letter.getBody();
    		StringBuilder sb = new StringBuilder();
    		
    		if (!body.getBody().isEmpty())
    		{
    			sb.append(body.getBody());
    		}
    		else
    		{
    			sb.append("\n");
    			sb.append("+-------------------- CONTENTS --------------------\n");
    			sb.append("\n");
    		}
    		
    		final int size = args.size();
    		for (String word : args.subList(1, (size - 2)))
    			sb.append(word + " ");
    		sb.append(args.get((size - 1)));
    		
    		body.newBody(sb.toString());
    		letter.setContents(body);
    		GlobalHandler.getDataSource().saveLetter(letter);
    		
    		Messager.sendMessage(sender, Colors.White, sb.toString());
    	}
    	catch (NotRegisteredException nre)
    	{
    		return false;
    	}
		
		return true;
    }
    
    @Override
    public void moreHelp()
    {
    	Messager.sendMessage(sender, Colors.Rose, "");
    }

    @Override
    public boolean permission(CommandSender csender)
    {
    	return Vault.perms.has(csender, "ct.command.body");
    }

    @Override
    public BaseCommand newInstance()
    {
        return new BodyCommand();
    }
}
