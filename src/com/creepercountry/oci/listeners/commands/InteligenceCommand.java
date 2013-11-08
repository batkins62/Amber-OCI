package com.creepercountry.oci.listeners.commands;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.bukkit.command.CommandSender;

import com.creepercountry.oci.main.hooks.Vault;
import com.creepercountry.oci.object.GlobalHandler;
import com.creepercountry.oci.object.transmittion.Classification;
import com.creepercountry.oci.object.transmittion.Code;
import com.creepercountry.oci.object.transmittion.Letter;
import com.creepercountry.oci.object.transmittion.Recipient;
import com.creepercountry.oci.utils.Colors;
import com.creepercountry.oci.utils.Messager;
import com.creepercountry.oci.utils.exceptions.NotRegisteredException;

public class InteligenceCommand extends BaseCommand
{
    public InteligenceCommand()
    {
        name = "intel";
        usage = "[player <player]|id>";
        minArgs = 1;
        allowConsole = true;
    }

    @Override
    public boolean execute()
    {
    	List<Letter> letters = new ArrayList<Letter>();
    	LinkedHashSet<Letter> lhs = new LinkedHashSet<Letter>();
    	
    	// finding the letter
    	if (args.get(0).equalsIgnoreCase("player"))
    	{
    		// check for IOOB exception
    		if (!(args.size() <= 1))
    			return false;
    		
    		Messager.sendMessage(sender, Colors.Gold, "Outputting OCI Player Records for " + args.get(1));
    		try
    		{
    			for (String id : GlobalHandler.getDataSource().getUser(args.get(1)).getNotes())
    				letters.add(GlobalHandler.getDataSource().getLetter(id));
    		} catch (NotRegisteredException nre) { letters.iterator().remove(); }
    	}
    	else
    	{
    		try
    		{
    			letters.add(GlobalHandler.getDataSource().getLetter(args.get(0)));
    		} catch (NotRegisteredException nre) { letters.iterator().remove(); }
    	}
    	
    	// check for records before processing letters
    	if (letters.isEmpty())
    	{
    		Messager.sendMessage(sender, Colors.Red, "No OCI Records found");
    		return true;
    	}
    	
    	// assembling the letter
    	for (Letter letter : letters)
    	{
    		Classification classification = null;
    		if (letter.getCode().equals(Code.GAMMA) && Vault.perms.has(sender, "ct.transmition.gamma"))
    		{
    			classification = letter.getClassification();
    		}
    		
    		if (Vault.perms.has(sender, ("ct.transmition" + classification.toString()).toLowerCase()))
    		{
    			lhs.add(letter);
    		}
    	}
    	for (Letter letter : letters)
    	{
    		Classification classification = null;
    		if (letter.getCode().equals(Code.BLACK) && Vault.perms.has(sender, "ct.transmition.black"))
    		{
    			classification = letter.getClassification();
    		}
    		
    		if (Vault.perms.has(sender, ("ct.transmition" + classification.toString()).toLowerCase()))
    		{
    			lhs.add(letter);
    		}
    	}
    	for (Letter letter : letters)
    	{
    		Classification classification = null;
    		if (letter.getCode().equals(Code.RED) && Vault.perms.has(sender, "ct.transmition.red"))
    		{
    			classification = letter.getClassification();
    		}
    		
    		if (Vault.perms.has(sender, ("ct.transmition" + classification.toString()).toLowerCase()))
    		{
    			lhs.add(letter);
    		}
    	}
    	for (Letter letter : letters)
    	{
    		Classification classification = null;
    		if (letter.getCode().equals(Code.WHITE) && Vault.perms.has(sender, "ct.transmition.white"))
    		{
    			classification = letter.getClassification();
    		}
    		
    		if (Vault.perms.has(sender, ("ct.transmition" + classification.toString()).toLowerCase()))
    		{
    			lhs.add(letter);
    		}
    	}
    	
    	// outputting the letter
    	for (Letter letter : lhs)
    	{
    		StringBuilder sb = new StringBuilder();
            Messager.sendMessage(sender, Colors.LightGray, "+----------------------------------------------+");
            Messager.sendMessage(sender, Colors.Red, "Classification: " + Colors.Rose + letter.getClassification().toString());
            Messager.sendMessage(sender, Colors.Red, "Code: " + Colors.Rose + letter.getCode().toString());
            Messager.sendMessage(sender, Colors.Red, "Date: " + Colors.Rose + letter.getDate().toString());
            Messager.sendMessage(sender, Colors.Red, "Subject: " + Colors.Rose + letter.getSubject());
            Messager.sendMessage(sender, Colors.Red, "ID: " + Colors.Rose + letter.getID());
            Messager.sendMessage(sender, Colors.Red, "From: " + Colors.Rose + letter.getFrom().getName());
            for (Recipient rec : letter.getTo())
            	sb.append(rec.getName() + " ");
            Messager.sendMessage(sender, Colors.Red, "To: " + Colors.Rose + sb.toString());
            Messager.sendMessage(sender, Colors.Red, "Body: " + Colors.Rose + letter.getBody().getContents());
            Messager.sendMessage(sender, Colors.LightGray, "+----------------------------------------------+");
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
    	return Vault.perms.has(csender, "ct.command.read");
    }

    @Override
    public BaseCommand newInstance()
    {
        return new InteligenceCommand();
    }
}
