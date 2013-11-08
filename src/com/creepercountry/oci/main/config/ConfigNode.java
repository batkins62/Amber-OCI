package com.creepercountry.oci.main.config;

public enum ConfigNode
{
	PLUGIN_VERSION ("version.plugin"),
	CONFIG_VERSION ("version.config"),
	CONFIG_TOKEN ("config-token"),
	PLUGIN_RUNNING ("plugin-running"),
	DEBUG ("debug"),
	FIRST_RUN ("first-run"),
	VAULT_DEPEND ("dependencies.required.vault"),
	DEFCON1_ALIAS ("defcon.one.aliases"),
	DEFCON2_ALIAS ("defcon.two.aliases"),
	DEFCON3_ALIAS ("defcon.three.aliases"),
	DEFCON4_ALIAS ("defcon.four.aliases"),
	DEFCON5_ALIAS ("defcon.five.aliases"),
	DEFCON1_DESCRIPTION ("defcon.one.description"),
	DEFCON2_DESCRIPTION ("defcon.two.description"),
	DEFCON3_DESCRIPTION ("defcon.three.description"),
	DEFCON4_DESCRIPTION ("defcon.four.description"),
	DEFCON5_DESCRIPTION ("defcon.five.description"),
	DEFCON1_USAGE ("defcon.one.usage"),
	DEFCON2_USAGE ("defcon.two.usage"),
	DEFCON3_USAGE ("defcon.three.usage"),
	DEFCON4_USAGE ("defcon.four.usage"),
	DEFCON5_USAGE ("defcon.five.usage"),
	DEFCON1_PERMISSION ("defcon.one.permission"),
	DEFCON2_PERMISSION ("defcon.two.permission"),
	DEFCON3_PERMISSION ("defcon.three.permission"),
	DEFCON4_PERMISSION ("defcon.four.permission"),
	DEFCON5_PERMISSION ("defcon.five.permission"),
	DEFCON_CURRENT ("defcon.current"),
	DEFCON_ACTIVE ("defcon.active");
	
	private final String path;
	ConfigNode(String path)
	{
		this.path = path;
	}
	
	public String getPath()
	{
		return path;
	}
	
	@Override
	public String toString()
	{
		String out = this.toString().toLowerCase();
		out.replace("_", " ");
		return out;
	}
}
