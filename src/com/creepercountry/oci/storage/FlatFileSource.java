package com.creepercountry.oci.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.creepercountry.oci.main.OCIPlugin;
import com.creepercountry.oci.object.GlobalHandler;
import com.creepercountry.oci.object.User;
import com.creepercountry.oci.object.transmittion.Body;
import com.creepercountry.oci.object.transmittion.Classification;
import com.creepercountry.oci.object.transmittion.Code;
import com.creepercountry.oci.object.transmittion.Letter;
import com.creepercountry.oci.object.transmittion.Recipient;
import com.creepercountry.oci.object.transmittion.Recipient.Type;
import com.creepercountry.oci.utils.DebugMode;
import com.creepercountry.oci.utils.exceptions.AlreadyRegisteredException;

public class FlatFileSource extends DatabaseHandler
{
	protected final String newLine = System.getProperty("line.separator");
	protected String rootFolder = "";
	protected String dataFolder = FileMgmt.fileSeparator() + "data";

	@Override
	public void initialize(OCIPlugin plugin, GlobalHandler glob)
	{
		System.out.println("[OCI] Initialising dataSource for use...");
		this.global = glob;
		this.plugin = plugin;
		this.rootFolder = global.getRootFolder();

		// Create files and folders if non-existent
		try
		{
			FileMgmt.checkFolders(new String[]
				{ 
					rootFolder,
					rootFolder + dataFolder,
					rootFolder + dataFolder + FileMgmt.fileSeparator() + "users",
					rootFolder + dataFolder + FileMgmt.fileSeparator() + "users" + FileMgmt.fileSeparator() + "removed",
					rootFolder + dataFolder + FileMgmt.fileSeparator() + "letters",
					rootFolder + dataFolder + FileMgmt.fileSeparator() + "letters" + FileMgmt.fileSeparator() + "archive",
				});
				FileMgmt.checkFiles(new String[]
					{
						rootFolder + dataFolder + FileMgmt.fileSeparator() + "users.txt",
						rootFolder + dataFolder + FileMgmt.fileSeparator() + "letters.txt",
					});
		}
		catch (IOException e)
		{
			System.out.println("[OCI] Error: Could not create flatfile default files and folders.");
		}
	}
	
	public String getUserFilename(User user)
	{
		return rootFolder + dataFolder + FileMgmt.fileSeparator() + "users" + FileMgmt.fileSeparator() + user.getName() + ".txt";
	}
	
	public String getLetterFilename(Letter id)
	{
		return rootFolder + dataFolder + FileMgmt.fileSeparator() + "letters" + FileMgmt.fileSeparator() + id.getID() + ".txt";
	}
	
	@Override
	public boolean loadUserList()
	{
		DebugMode.log("Loading User List");
		String line;
		BufferedReader fin;

		try
		{
			fin = new BufferedReader(new FileReader(rootFolder + dataFolder + FileMgmt.fileSeparator() + "users.txt"));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			return false;
		}
		try
		{
			while ((line = fin.readLine()) != null)
				if (!line.equals(""))
					newUser(line);
		}
		catch (AlreadyRegisteredException e)
		{
			e.printStackTrace();
			confirmContinuation(e.getMessage() + " | Continuing will delete it's data.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		finally
		{
			try
			{
				fin.close();
			}
			catch (IOException e)
			{
				// Failed to close file.
			}
		}
		return true;
	}
	
	@Override
	public boolean loadLetterList()
	{
		DebugMode.log("Loading Letter List");
		String line;
		BufferedReader fin;

		try
		{
			fin = new BufferedReader(new FileReader(rootFolder + dataFolder + FileMgmt.fileSeparator() + "letters.txt"));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			return false;
		}
		try
		{
			while ((line = fin.readLine()) != null)
				if (!line.equals(""))
					newLetter(line);
		}
		catch (AlreadyRegisteredException e)
		{
			e.printStackTrace();
			confirmContinuation(e.getMessage() + " | Continuing will delete it's data.");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		finally
		{
			try
			{
				fin.close();
			}
			catch (IOException e)
			{
				// Failed to close file.
			}
		}
		return true;
	}
	
	/*
	 * Load individual town object
	 */
	
	@Override
	public boolean loadLetter(Letter letter)
	{
		String line;
		Type type = Type.BROADCAST;
		Classification option1;
		Code option2;
		String[] tokens;
		List<Recipient> rec = new ArrayList<Recipient>();
		String path = getLetterFilename(letter);
		File fileLetter = new File(path);
		
		if (fileLetter.exists() && fileLetter.isFile())
		{
			try
			{
				KeyValueFile kvFile = new KeyValueFile(path);
				line = kvFile.get("subject");
				if (line != null)
					letter.setSubject(line);
				
				line = kvFile.get("class");
				if (line != null)
				{
					if (line.equals("RESTRICTED"))
						option1 = Classification.RESTRICTED;
					else if (line.equals("CLASSIFIED"))
						option1 = Classification.CLASSIFIED;
					else if (line.equals("EYESONLYTOPSECRET"))
						option1 = Classification.EYESONLYTOPSECRET;
					else if (line.equals("SECRET"))
						option1 = Classification.SECRET;
					else
						option1 = Classification.NA;
					
					letter.setClassification(option1);
				}
				
				line = kvFile.get("code");
				if (line != null)
				{
					if (line.equals("RED"))
						option2 = Code.RED;
					else if (line.equals("BLACK"))
						option2 = Code.BLACK;
					else if (line.equals("GAMMA"))
						option2 = Code.BLACK;
					else
						option2 = Code.WHITE;
					
					letter.setCode(option2);
				}
				
				line = kvFile.get("date");
				if (line != null)
					letter.setDate(new Date(Long.parseLong(line)));
				
				line = kvFile.get("id");
				if (line != null)
					letter.setID(line);
				
				line = kvFile.get("type");
				if (line != null)
				{
					if (line.equals("P2P"))
						type = Recipient.Type.P2P;
					else if (line.equals("BROADCAST"))
						type = Recipient.Type.BROADCAST;
				}
				
				line = kvFile.get("fromname");;
				if (line != null)
					letter.setFrom(new Recipient(line, type));
				
				line = kvFile.get("body");
				if (line != null)
					letter.setContents(new Body(line));
				
				line = kvFile.get("to");
				if (line != null)
				{
					tokens = line.split(",");
					for (String token : tokens)
						if (!token.isEmpty())
							if (token != null)
								rec.add(new Recipient(token, type));
					
					letter.setTo(rec);
				}
			}
			catch (Exception e)
			{
				System.out.println("[OCI] Loading Error: Exception while reading Letter file " + letter.getID());
				return false;
			}

			return true;
		}
		else
			return false;
	}

	@Override
	public boolean loadUser(User user)
	{
		String line;
		String[] tokens;
		String path = getUserFilename(user);
		File fileUser = new File(path);
		
		if (fileUser.exists() && fileUser.isFile())
		{
			try
			{
				KeyValueFile kvFile = new KeyValueFile(path);
				
				line = kvFile.get("lastOnline");
				if (line != null)
					user.setLastOnline(Long.parseLong(kvFile.get("lastOnline")));
				
				line = kvFile.get("notes");
				if (line != null)
				{
					tokens = line.split(",");
					for (String token : tokens)
						if (!token.isEmpty())
							if (token != null)
								user.addNote(token);
				}
				
				line = kvFile.get("ip");
				if (line != null)
				{
					tokens = line.split(",");
					for (String token : tokens)
						if (!token.isEmpty())
							if (token != null)
								user.addIP(line);
				}
			}
			catch (Exception e)
			{
				System.out.println("[OCI] Loading Error: Exception while reading user file " + user.getName());
				return false;
			}

			return true;
		}
		else
			return false;
	}
	
	@Override
	public boolean saveLetterList()
	{
		try
		{
			BufferedWriter fout = new BufferedWriter(new FileWriter(rootFolder + dataFolder + FileMgmt.fileSeparator() + "letters.txt"));
			for (Letter letter : getLetters())
				fout.write((letter.getID()) + newLine);
			fout.close();
			return true;
		}
		catch (Exception e)
		{
			System.out.println("[OCI] Saving Error: Exception while saving Letter list file");
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean saveUserList()
	{
		try
		{
			BufferedWriter fout = new BufferedWriter(new FileWriter(rootFolder + dataFolder + FileMgmt.fileSeparator() + "users.txt"));
			for (User user : getUsers())
				fout.write((user.getName()) + newLine);
			fout.close();
			return true;
		}
		catch (Exception e)
		{
			System.out.println("[OCI] Saving Error: Exception while saving users list file");
			e.printStackTrace();
			return false;
		}
	}
	
	/*
	 * Save individual town objects
	 */

	@Override
	public boolean saveUser(User user)
	{
		BufferedWriter fout;
		String path = getUserFilename(user);
		try
		{
			fout = new BufferedWriter(new FileWriter(path));
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
		try
		{
			// Last Online
			fout.write("lastOnline=" + Long.toString(user.getLastOnline()) + newLine);
			// ipaddresses
			fout.write("ip=");
			for (String msg : user.getIps())
				fout.write(msg + ",");
			fout.write(newLine);
			// notes
			fout.write("notes=");
			for (String msg : user.getNotes())
				fout.write(msg + ",");
			fout.write(newLine);
			
			fout.close();
		}
		catch (Exception e)
		{
			try
			{
				fout.close();
			}
			catch (IOException ioe)
			{
			}
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	@Override
	public boolean saveLetter(Letter letter)
	{
		BufferedWriter fout;
		String path = getLetterFilename(letter);
		try
		{
			fout = new BufferedWriter(new FileWriter(path));
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
		try
		{
			// subject
			fout.write("subject=" + letter.getSubject() + newLine);
			// classification
			fout.write("class=" + letter.getClassification().toString() + newLine);
			// code
			fout.write("code=" + letter.getCode().toString() + newLine);
			// date
			fout.write("date=" + letter.getDate().getTime() + newLine);
			// id
			fout.write("id=" + letter.getID() + newLine);
			// type
			fout.write("type=" + letter.getFrom().getType().toString() + newLine);
			// from name
			fout.write("fromname=" + letter.getFrom().getName() + newLine);
			// body
			if (!letter.getBody().getBody().isEmpty())
				fout.write("body=" + letter.getBody().getContents() + newLine);
			// to
			fout.write("to=");
			for (Recipient name : letter.getTo())
				fout.write(name.getName() + ",");
			
			fout.close();
		}
		catch (Exception e)
		{
			try
			{
				fout.close();
			}
			catch (IOException ioe)
			{
			}
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	@Override
	public void deleteFile(String fileName)
	{
		File file = new File(fileName);
		if (file.exists())
			file.delete();
	}

	@Override
	public void deleteUser(User user)
	{		
		File file = new File(getUserFilename(user));
		if (file.exists())
		{
			try
			{
				FileMgmt.moveFile(file, ("removed"));
			}
			catch (IOException e)
			{
				System.out.println("[OCI] Error moving user txt file.");
			}
		}
	}

	@Override
	public void deleteLetter(Letter letter)
	{
		File file = new File(getLetterFilename(letter));
		if (file.exists())
		{
			try
			{
				FileMgmt.moveFile(file, ("archive"));
			}
			catch (IOException e)
			{
				System.out.println("[OCI] Error moving user txt file.");
			}
		}
		
	}
}
