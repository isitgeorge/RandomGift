package com.isitgeo.randomgift;

public class Debugger {

	private RandomGift plugin;
		
	public Debugger(RandomGift plugin) {
		this.plugin = plugin;
	}
	
	String debugPrefix = "[DEBUG] ";
	
	void log(String log) {
		if (plugin.enableDebug) {
			plugin.getLogger().info(debugPrefix + log);
		}
	}
	
}
