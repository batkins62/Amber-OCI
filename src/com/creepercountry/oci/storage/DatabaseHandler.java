package com.creepercountry.oci.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.creepercountry.oci.object.User;
import com.creepercountry.oci.object.transmittion.Letter;
import com.creepercountry.oci.utils.Colors;
import com.creepercountry.oci.utils.DebugMode;
import com.creepercountry.oci.utils.Messager;
import com.creepercountry.oci.utils.exceptions.AlreadyRegisteredException;
import com.creepercountry.oci.utils.exceptions.NotRegisteredException;

public abstract class DatabaseHandler extends DataSource
{
	@Override
	public boolean hasUser(String name)
	{
		return global.getUserMap().containsKey(name.toLowerCase());
	}
	
	@Override
	public List<User> getUsers(Player player, String[] names)
	{
		List<User> invited = new ArrayList<User>();
		for (String name : names)
		{
			try
			{
				User target = getUser(name);
				invited.add(target);
			}
			catch (Exception x)
			{
				Messager.sendMessage((CommandSender)player, Colors.Red, x.getMessage());
			}
		}
		return invited;
	}

	@Override
	public List<User> getUsers(String[] names)
	{
		List<User> matches = new ArrayList<User>();
		for (String name : names)
		{
			try
			{
				matches.add(getUser(name));
			}
			catch (NotRegisteredException e)
			{
			}
		}
		return matches;
	}
	
	@SuppressWarnings("unused")
	@Override
	public Letter getLetter(String id) throws NotRegisteredException
	{
		Letter letter = global.getLetterMap().get(id);
		DebugMode.log("getLetter(" + letter.getID() + ")");
		if (letter == null)
			throw new NotRegisteredException("This Letter is not found.");
		
		return letter;
	}

	@Override
	public List<User> getUsers()
	{
		return new ArrayList<User>(global.getUserMap().values());
	}

	@Override
	public User getUser(String name) throws NotRegisteredException
	{
		User user = null;
		user = global.getUserMap().get(name.toLowerCase());
		
		if (user == null)
			throw new NotRegisteredException(String.format("The user '%s' is not registered.", name));

		return user;
	}
	
	@Override
	public List<Letter> getLetters()
	{
		return new ArrayList<Letter>(global.getLetterMap().values());
	}
	
	@Override
	public void removeUser(User user)
	{
		user.clear();
	}
	
	@Override
	public void newUser(String name) throws AlreadyRegisteredException
	{	
		if (global.getUserMap().containsKey(name.toLowerCase()))
			throw new AlreadyRegisteredException("A user with the name " + name + " is already in use.");

		global.getUserMap().put(name.toLowerCase(), new User(name));
	}
	
	@Override
	public void newLetter(String id) throws AlreadyRegisteredException
	{
		if (global.getLetterMap().containsKey(id.toLowerCase()))
			throw new AlreadyRegisteredException("A Letter already exsists under the id of " + id);
		
		global.getLetterMap().put(id.toLowerCase(), new Letter(id));
	}
	
	@Override
	public void removeUserList(User user)
	{
		String name = user.getName();

		//search and remove from all friends lists
		List<User> toSave = new ArrayList<User>();

		for (User toCheck : toSave)
			saveUser(toCheck);

		// Wipe and delete user
		user.clear();

		deleteUser(user);

		global.getUserMap().remove(name.toLowerCase());
		saveUserList();
	}
	
	@Override
	public Set<String> getUserKeys()
	{
		return global.getUserMap().keySet();
	}
}
