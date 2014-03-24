package com.isitgeo.randomgift;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Notifications {

	private RandomGift plugin;

	public Notifications(RandomGift plugin) {
		this.plugin = plugin;
	}
	
	public void playerUpdateAvailable(Player player) {
		
		if (plugin.updateAvailable == true) {
			player.sendMessage(ChatColor.LIGHT_PURPLE + "An update is available for RandomGift!\nDownload it at http://dev.bukkit.org/bukkit-plugins/randomgift/");
		}
		
	}
	
	public void consoleUpdateAvailable() {
		plugin.getLogger().info("An update is available! Get it at http://dev.bukkit.org/bukkit-plugins/randomgift");
	}
	
	public void outatedConfiguration(Player player) {		
		if (plugin.latestConfig > plugin.configVersion) {
			player.sendMessage(ChatColor.LIGHT_PURPLE + "You are running an out of date RandomGift configuration!\nPlease see http://bit.ly/RndmGiftOldCfg for more information.");
		}
	}
}
