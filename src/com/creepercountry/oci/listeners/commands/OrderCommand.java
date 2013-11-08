package com.creepercountry.oci.listeners.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import com.creepercountry.oci.main.hooks.Vault;
import com.creepercountry.oci.object.GlobalHandler;
import com.creepercountry.oci.object.User;
import com.creepercountry.oci.object.transmittion.Classification;
import com.creepercountry.oci.object.transmittion.Code;
import com.creepercountry.oci.object.transmittion.Letter;
import com.creepercountry.oci.object.transmittion.Order;
import com.creepercountry.oci.object.transmittion.Recipient;
import com.creepercountry.oci.object.transmittion.Recipient.Type;
import com.creepercountry.oci.utils.Colors;
import com.creepercountry.oci.utils.Messager;
import com.creepercountry.oci.utils.exceptions.AlreadyRegisteredException;
import com.creepercountry.oci.utils.exceptions.NotRegisteredException;

public class OrderCommand extends BaseCommand
{
	public OrderCommand()
    {
        name = "order";
        usage = "<subject> <classification 0-4> <code 0-3> <type 0-1> <to... to... to...>";
        minArgs = 5;
    }

    @Override
    public boolean execute()
    {
    	String id = Order.next();
    	try
    	{
			GlobalHandler.getDataSource().newLetter(id);
		}
    	catch (AlreadyRegisteredException are)
		{
    		try
    		{
				GlobalHandler.getDataSource().newLetter(Order.next());
			}
    		catch (AlreadyRegisteredException are2)
    		{
				return false;
			}
		}
    	
    	try
    	{
    		Letter letter = GlobalHandler.getDataSource().getLetter(id);
    		
    		// subject
    		letter.setSubject(args.get(0).intern());

    		// classification
    		Integer classdigit = Integer.valueOf(args.get(1));
    		if (classdigit.equals(classdigit))
    		{
	    		Classification classification;
	    		switch (classdigit)
	    		{
		    		case 1: classification = Classification.EYESONLYTOPSECRET;
		    				break;
		    		case 2: classification = Classification.SECRET;
		    				break;
		    		case 3: classification = Classification.CLASSIFIED;
		    				break;
		    		case 4: classification = Classification.RESTRICTED;
		    				break;
		    		default: classification = Classification.NA;
		    				break;
	    		}
	    		letter.setClassification(classification);
	    		Messager.sendMessage(sender, Colors.Gold, "Classification: " + classification.toString());
    		}
    		else
    			return false;
    		
    		// code
    		Integer codedigit = Integer.valueOf(args.get(2));
    		if (!classdigit.equals(null))
    		{
	    		Code code;
	    		switch (codedigit)
	    		{
		    		case 1: code = Code.GAMMA;
		    				break;
		    		case 2: code = Code.BLACK;
		    				break;
		    		case 3: code = Code.RED;
		    				break;
		    		default: code = Code.WHITE;
		    				break;
	    		}
	    		letter.setCode(code);
	    		Messager.sendMessage(sender, Colors.Gold, "Code: " + code.toString());
    		}
    		else
    			return false;
    		
    		// type
    		if (args.size() > 4)
    		{
    			Integer typedigit = Integer.valueOf(args.get(3));
    			Type type = Type.BROADCAST;
        		if (!typedigit.equals(null))
        		{
    	    		switch (typedigit)
    	    		{
    		    		case 1: type = Type.P2P;
    		    				break;
    		    		default: type = Type.BROADCAST;
    		    				break;
    	    		}
        		}
        		else
        			return false;
    			
        		// to
    			List<String> name = new ArrayList<String>();
    			List<Recipient> to = new ArrayList<Recipient>();
    			StringBuilder sb = new StringBuilder();
    			for (int amount = args.size() - 4; amount == 0; amount--)
    			{
    				name.add(args.get(amount + 4));
    			}
    			
    			Messager.sendMessage(sender, Colors.Gold, "Type: " + type.toString() +  " - Classification:");
    			for (String plr : name)
    			{
    				try
    				{
    					user = GlobalHandler.getDataSource().getUser(plr);
    					user.addNote(id);
    					GlobalHandler.getDataSource().saveUser(user);
    				}
    				catch (NotRegisteredException nre)
    				{
    					try
    					{
	    					GlobalHandler.getDataSource().newUser(plr);
	    					User user = GlobalHandler.getDataSource().getUser(plr);
	    					user.addNote(id);
	    					GlobalHandler.getDataSource().saveUser(user);
	    					GlobalHandler.getDataSource().saveUserList();
    					} catch (AlreadyRegisteredException are) {}
    				}
    				to.add(new Recipient(plr, type));
    				sb.append(plr + ",");
    			}
    			
    			Messager.sendMessage(sender, Colors.Gold, sb.toString());
    			letter.setTo(to);
    			name.clear();
    			to.clear();
    			if (GlobalHandler.getDataSource().saveLetterList())
    				GlobalHandler.getDataSource().saveLetter(letter);
    			
    			Messager.sendMessage(sender, Colors.LightGray, "+-");
    			Messager.sendMessage(sender, Colors.LightGray, "+- type /cc body " + letter.getID() + " <message> to form the body of the message");
    			Messager.sendMessage(sender, Colors.LightGray, "+- NOTE: dont worry if you cant fit it all inside 1 command, command will persist");
    			Messager.sendMessage(sender, Colors.LightGray, "+-");
    		}
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
    	return Vault.perms.has(csender, "ct.command.order");
    }

    @Override
    public BaseCommand newInstance()
    {
        return new OrderCommand();
    }
}
