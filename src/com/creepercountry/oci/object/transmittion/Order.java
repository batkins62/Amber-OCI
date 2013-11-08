package com.creepercountry.oci.object.transmittion;

import java.util.Random;

public class Order
{
	public static String next()
	{
		char[] chars = "a1b2c3d4e5f6g7h8i9j1k2l3m4n5o6p7q8r9s1t2u3v4w5x6y7z".toCharArray();
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 3; i++) {
		    char c = chars[random.nextInt(chars.length)];
		    sb.append(c);
		}
		sb.append("-");
		for (int i = 0; i < 5; i++) {
		    char c = chars[random.nextInt(chars.length)];
		    sb.append(c);
		}
		String output = sb.toString();
		return output;
	}
}
