package com.creepercountry.oci.storage;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.bukkit.entity.Player;

import com.creepercountry.oci.main.OCIPlugin;
import com.creepercountry.oci.object.GlobalHandler;
import com.creepercountry.oci.object.User;
import com.creepercountry.oci.object.transmittion.Letter;
import com.creepercountry.oci.utils.DebugMode;
import com.creepercountry.oci.utils.exceptions.AlreadyRegisteredException;
import com.creepercountry.oci.utils.exceptions.NotRegisteredException;

public abstract class DataSource
{
	protected GlobalHandler global;
	protected OCIPlugin plugin;
	protected boolean firstRun = true;

	public void initialize(OCIPlugin plugin, GlobalHandler handler)
	{
		this.global = handler;
		this.plugin = plugin;
	}
	
	public void deleteUnusedResidentFiles()
	{

	}

	public boolean confirmContinuation(String msg)
	{
		Boolean choice = null;
		String input = null;
		while (choice == null) {
			System.out.println(msg);
			System.out.print(" Continue (y/n): ");
			Scanner in = new Scanner(System.in);
			input = in.next();
			input = input.toLowerCase();
			if (input.equals("y") || input.equals("yes"))
			{
				in.close();
				return true;
			}
			else if (input.equals("n") || input.equals("no"))
			{
				in.close();
				return false;
			}
		}
		System.out.println("[OCI] Error recieving input, exiting.");
		return false;
	}

	public boolean loadAll()
	{
		System.out.println("[OCI] Loading data...");
		return loadUserList() && loadUsers() && loadLetterList() && loadLetters();
	}

	public boolean saveAll()
	{
		return saveUserList() && saveUsers() && saveLetterList() && saveLetters();
	}

	abstract public boolean loadUserList();

	abstract public boolean loadUser(User resident);

	abstract public boolean saveUserList();

	abstract public boolean saveUser(User resident);

	abstract public void deleteUser(User resident);
	abstract public void deleteFile(String file);

	/*
	 * Load Functions
	 */
	
	public boolean loadLetters()
	{
		DebugMode.log("Loading Letters");
		for (Letter letter : getLetters())
			if (!loadLetter(letter))
			{
				System.out.println("[OCI] Loading Error: Could not read Letter data " + letter.getID() + "'.");
				return false;
			}
		return true;
	}

	public boolean loadUsers()
	{
		DebugMode.log("Loading Users");
		for (User user : getUsers())
			if (!loadUser(user))
			{
				System.out.println("[OCI] Loading Error: Could not read User data " + user.getName() + "'.");
				return false;
			}
		return true;
	}
	
	/*
	 * Save functions
	 */
	
	public boolean saveUsers()
	{
		DebugMode.log("Saving Users");
		for (User user : getUsers())
			saveUser(user);
		return true;
	}
	
	public boolean saveLetters()
	{
		DebugMode.log("Saving Letters");
		for (Letter letter : getLetters())
			saveLetter(letter);
		return true;
	}
	
	// Database functions
	abstract public List<User> getUsers(Player player, String[] names);
	abstract public List<User> getUsers();
	abstract public List<User> getUsers(String[] names);
	abstract public User getUser(String name) throws NotRegisteredException;
	abstract public void removeUserList(User resident);
	abstract public boolean hasUser(String name);
	abstract public void removeUser(User resident);
	abstract public void newUser(String name) throws AlreadyRegisteredException;
	abstract public Set<String> getUserKeys();
	
	abstract public void newLetter(String id) throws AlreadyRegisteredException;
	abstract public boolean loadLetter(Letter letter);
	abstract public boolean saveLetter(Letter letter);
	abstract public boolean loadLetterList();
	abstract public boolean saveLetterList();
	abstract public Letter getLetter(String id) throws NotRegisteredException;
	abstract public List<Letter> getLetters();
	abstract public void deleteLetter(Letter letter);
}