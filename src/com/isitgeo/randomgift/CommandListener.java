package com.isitgeo.randomgift;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandListener implements CommandExecutor {

	private RandomGift plugin;
	private GiftGenerator giftGen;

	public CommandListener(RandomGift plugin, GiftGenerator giftGen) {
		this.plugin = plugin;
		this.giftGen = giftGen;
	}

	@Override
	public boolean onCommand(CommandSender sentby, Command command,	String label, String[] args) {
		if (command.getName().equalsIgnoreCase("randomgift")) {

			if (args.length == 0) {
				sentby.sendMessage(plugin.broadcastTag + "Running v" + plugin.getDescription().getVersion() + " with configuration v" + plugin.getConfig().getString("config-version") + ".");
				return true;
			}
			
			
			if (args[0].equalsIgnoreCase("reload")) {
				
				if (args.length > 1) {
					sentby.sendMessage(plugin.invalidCommand);
					return true;
				}
				
				if (sentby.hasPermission("randomgift.reload")) {
					plugin.reloadConfig();
					plugin.load();
					sentby.sendMessage(plugin.playerBroadcastTag + "Configuration reloaded.");
				} else {
					sentby.sendMessage(plugin.permissionError);
				}
				return true;
			}
			
			
			if (args[0].equalsIgnoreCase("cooldown")) {
				
				if (args.length > 1) {
					sentby.sendMessage(plugin.invalidCommand);
					return true;
				}

				if (sentby.hasPermission("randomgift.cooldown")) {
					int difference = (int) (System.currentTimeMillis() - plugin.cooldown);
					int val = plugin.cooldownTime - difference;

					if (!(val <= 60000)) {
						sentby.sendMessage(plugin.playerBroadcastTag + " About " + val / 60 / 1000 + " minutes remaining.");
					} else if (val <= 0) {
						sentby.sendMessage(plugin.playerBroadcastTag + "Ready and waiting!");
					} else {
						sentby.sendMessage(plugin.playerBroadcastTag + val / 1000	+ " seconds remaining.");
					}
				} else {
					sentby.sendMessage(plugin.permissionError);
				}
				return true;
			}
			
			
			if (args[0].equalsIgnoreCase("reset")) {
				
				if (args.length > 1) {
					sentby.sendMessage(plugin.invalidCommand);
					return true;
				}

				if (sentby.hasPermission("randomgift.cooldown.reset")) {
					plugin.cooldown = System.currentTimeMillis() - plugin.cooldownTime;
					sentby.sendMessage(plugin.playerBroadcastTag + "Cooldown timer has been reset!");
				} else {
					sentby.sendMessage(plugin.permissionError);
				}
				return true;
			}
			
			
			if (args[0].equalsIgnoreCase("gift")) {
				
				if (args.length > 2) {
					sentby.sendMessage(plugin.invalidCommand);
					return true;
				}
				
				if (sentby.hasPermission("randomgift.gift")) {
					if (args.length == 2) {
						if (plugin.getServer().getPlayer(args[1]) != null) {
							giftGen.getPlayers(plugin.getServer().getPlayer(args[1]), true);
						} else {
							sentby.sendMessage(plugin.playerBroadcastTag + "Player not online!");
						}
					} else {
						sentby.sendMessage(plugin.playerBroadcastTag + "You didn't specify a trigger player!");
					}
				} else {
					sentby.sendMessage(plugin.permissionError);
				}
				return true;
			}
			
			
			if (args[0].equalsIgnoreCase("history")) {
				
				if (args.length > 1) {
					sentby.sendMessage(plugin.invalidCommand);
					return true;
				}
				
				if (sentby.hasPermission("randomgift.history")) {
					if (plugin.historicPlayer != "") {
						sentby.sendMessage(plugin.playerBroadcastTag + "Last player was " + plugin.historicPlayer);
					} else {
						sentby.sendMessage(plugin.playerBroadcastTag + "No gifts have been received since last server reload/restart.");
					}
				} else {
					sentby.sendMessage(plugin.permissionError);
				}
				return true;
			}
			
			
			// If command doesn't match any of the above...
			if (!args[0].isEmpty()) {
				sentby.sendMessage(plugin.commandError);
				return true;
			}
		}
		
		return false;
	}
}
