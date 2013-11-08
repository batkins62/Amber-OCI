package com.creepercountry.oci.object.transmittion;

import java.util.Date;

import com.creepercountry.oci.utils.Colors;

public class Body
{
	private String contents, comments;
	
	public Body()
	{
		contents = "";
		comments = "";
	}
	
	public Body(String msg)
	{
		newBody(msg);
	}
	
	public void newBody(String msg)
	{
		
		this.contents = msg;
	}
	
	public void addComment(Recipient from, String comment)
	{
		
		Date date = new Date(System.currentTimeMillis());
		StringBuilder sb = new StringBuilder();
		sb.append("--------------------\n\n");
		sb.append(Colors.Gold + from.getRecipient());
		sb.append(Colors.White + "- [ " + Colors.Gray + date.toString() + Colors.White + " ]: \n");
		
		if (comment.length() > 52)
			for (String page : splitStringEvery(comment, 52))
				sb.append(Colors.White + page + "\n");
		else
			sb.append(comment);
		
		this.comments = comments + sb.toString();
	}
	
	public String getBody()
	{
		return this.contents;
	}
	
	public String getComments()
	{
		return this.comments;
	}
	
	public String getContents()
	{
		return this.contents + "\n" + this.comments;
	}
	
	private String[] splitStringEvery(String s, int interval)
	{
	    int arrayLength = (int) Math.ceil(((s.length() / (double)interval)));
	    String[] result = new String[arrayLength];

	    int j = 0;
	    int lastIndex = result.length - 1;
	    for (int i = 0; i < lastIndex; i++)
	    {
	        result[i] = s.substring(j, j + interval);
	        j += interval;
	    } //Add the last bit
	    result[lastIndex] = s.substring(j);

	    return result;
	}
}
