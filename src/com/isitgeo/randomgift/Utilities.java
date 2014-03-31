package com.isitgeo.randomgift;

public class Utilities {

	private RandomGift plugin;

	public Utilities(RandomGift plugin) {
		this.plugin = plugin;
	}

	Integer getInt(String string) {
		return Integer.parseInt((string).replaceAll("[^0-9]", ""));
	}
	
	void resetCooldown() {
		plugin.cooldown = System.currentTimeMillis() - plugin.cooldownTime;
	}
}
