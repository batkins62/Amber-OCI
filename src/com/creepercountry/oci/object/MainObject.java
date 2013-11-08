package com.creepercountry.oci.object;

import java.util.Arrays;

public abstract class MainObject
{
	private String name;

	public void setName(String name)
	{
        this.name = name;
	}

	public String getName()
	{
		return name;
	}

	@Override
	public String toString()
	{
		return getName();
	}
	
	public String getTreeDepth(int depth)
	{
		char[] fill = new char[depth*4];
		Arrays.fill(fill, ' ');
		if (depth > 0) {
			fill[0] = '|';
			int offset = (depth-1)*4;
			fill[offset] = '+';
			fill[offset+1] = '-';
			fill[offset+2] = '-';
		}
		return new String(fill);
	}
}
