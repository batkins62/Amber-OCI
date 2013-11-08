package com.creepercountry.oci.object.transmittion;

import java.util.Date;
import java.util.List;

public class Letter
{
	private String subject;
	private Classification classification;
	private Date date = new Date();
	private Code code;
	private Recipient from;
	private String id;
	private Body body;
	private List<Recipient> to;
	
	public Letter(String subject, Classification classification, Code code, Recipient from, List<Recipient> to)
	{
		this.subject = subject;
		this.classification = classification;
		this.code = code;
		this.date.setTime(System.currentTimeMillis());
		this.from = from;
		this.id = Order.next();
		this.to = to;
		this.body = new Body();
	}
	
	public Letter(String id)
	{
		this.subject = "";
		this.classification = Classification.NA;
		this.code = Code.WHITE;
		this.id = id;
		this.body = new Body();
	}
	
	public void setSubject(String subj)
	{
		this.subject = subj;
	}
	
	public void setClassification(Classification classification)
	{
		this.classification = classification;
	}
	
	public void setDate(Date date)
	{
		this.date = date;
	}
	
	public void setCode(Code code)
	{
		this.code = code;
	}
	
	public void setFrom(Recipient from)
	{
		this.from = from;
	}
	
	public void setID(String id)
	{
		this.id = id;
	}
	
	public void setTo(List<Recipient> to)
	{
		this.to = to;
	}
	
	public String getID()
	{
		return id;
	}
	
	public Body getBody()
	{
		return this.body;
	}
	
	public Classification getClassification()
	{
		return this.classification;
	}
	
	public Recipient getFrom()
	{
		return this.from;
	}
	
	public List<Recipient> getTo()
	{
		return this.to;
	}
	
	public Code getCode()
	{
		return this.code;
	}
	
	public String getSubject()
	{
		return this.subject;
	}
	
	public Date getDate()
	{
		return this.date;
	}
	
	public void setContents(Body msg)
	{
		this.body = msg;
	}
	
	public String returnLetter()
	{
		// create the object
		StringBuilder sb = new StringBuilder();
				
		// make the string
		sb.append("From: " + from.getRecipient() + "\n");
		for (Recipient out : to)
			sb.append("To: " + out + "\n");
		sb.append("Subject: " + subject + "\n");
		sb.append("Date: " + date.toString() + "\n");
		sb.append("X-Order Identifier: " + id.toString() + "\n");
		sb.append("Code: " + code.toString() + "\n");
		sb.append("Classification: " + classification.toString() + "\n");
		sb.append("\n");
		sb.append(body.getContents());
				
		// output as string
		return sb.toString();
	}
}