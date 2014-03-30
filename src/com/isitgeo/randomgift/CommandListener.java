package com.isitgeo.randomgift;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class CommandListener implements CommandExecutor {
	
	private RandomGift plugin;
	private GiftGenerator giftGen;
	private Debugger debug;

	public CommandListener(RandomGift plugin, GiftGenerator giftGen, Debugger debug) {
		this.plugin = plugin;
		this.giftGen = giftGen;
		this.debug = debug;
	}

	@Override
	public boolean onCommand(CommandSender sentby, Command command,	String label, String[] args) {
		if (command.getName().equalsIgnoreCase("randomgift")) {

			if (args.length == 0) {
				sentby.sendMessage(plugin.broadcastTag + "Running RandomGift v" + plugin.getDescription().getVersion() + " with configuration v" + plugin.getConfig().getString("config-version") + ".");
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
					long cooldownDifference = plugin.cooldownTime - (System.currentTimeMillis() - plugin.cooldown);

					if (!(cooldownDifference <= 60000)) {
						sentby.sendMessage(plugin.playerBroadcastTag + " About " + cooldownDifference / 60 / 1000 + " minutes remaining.");
					} else if (cooldownDifference <= 0) {
						sentby.sendMessage(plugin.playerBroadcastTag + "Ready and waiting!");
					} else {
						sentby.sendMessage(plugin.playerBroadcastTag + cooldownDifference / 1000	+ " seconds remaining.");
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
							giftGen.getPlayers(plugin.getServer().getPlayer(args[1]).getName(), true, true);
						} else {
							sentby.sendMessage(plugin.playerBroadcastTag + "Player not online!");
						}
					} else {
						
						if (sentby instanceof ConsoleCommandSender) {
							giftGen.getPlayers("Console", true, true);
						} else {
							giftGen.getPlayers(plugin.getServer().getPlayer(sentby.getName()).getName(), true, true);
						}
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
						sentby.sendMessage(plugin.playerBroadcastTag + "Last player to receive a gift was " + plugin.historicPlayer);
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
