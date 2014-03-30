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
			player.sendMessage(plugin.playerBroadcastTag + ChatColor.LIGHT_PURPLE + "An update is available for RandomGift!\nDownload it at http://dev.bukkit.org/bukkit-plugins/randomgift/");
		}
		
	}
	
	public void consoleUpdateAvailable() {
		plugin.getLogger().info("An update is available! Get it at http://dev.bukkit.org/bukkit-plugins/randomgift");
	}
	
	public void playerOutatedConfiguration(Player player) {		
		if (plugin.latestConfig > plugin.configVersion) {
			player.sendMessage(plugin.playerBroadcastTag + ChatColor.LIGHT_PURPLE + "You are running an out of date RandomGift configuration!\nPlease see http://bit.ly/RndmGiftOldCfg for more information.");
		}
	}
	
	public void consoleOutdatedConfiguration() {
		if (plugin.latestConfig > plugin.configVersion) {
			plugin.getLogger().info("Running outdated configuration - See http://bit.ly/RndmGiftOldCfg");
		}
	}
	
	public void playerDebugEnabled(Player player) {
		if (plugin.enableDebug) {
			player.sendMessage(plugin.playerBroadcastTag + ChatColor.LIGHT_PURPLE + "Debug mode is enabled - It is recommended you disable this feature (inside config.yml) unless you are currently debugging.");
		}
	}
	
	public void consoleDebugEnabled() {
		if (plugin.enableDebug) {
			plugin.getLogger().info("Debug mode is enabled - Disable inside config.yml");
		}
	}
}
