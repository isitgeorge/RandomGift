package com.isitgeo.randomgift;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class RandomGift extends JavaPlugin implements Listener {

	private File config;
	private Player player;
	private RandomGiftGen rGG;
	public long cooldown;
	public int cooldownTime;
	public String[] gList;
	public String broadcastTag = ChatColor.GOLD + "[RandomGift] "
			+ ChatColor.WHITE;

	@Override
	public void onEnable() {

		config = new File(getDataFolder(), "config.yml");

		if (!(config.exists())) {
			getLogger().info("Configuration not found...");
			this.saveDefaultConfig();
			getLogger().info("Created configuration!");
		}
		load();
		getServer().getPluginManager().registerEvents(this, this);

		try {
			MetricsLite metrics = new MetricsLite(this);
			metrics.start();
		} catch (IOException e) {
			// Failed to submit the stats :-(
		}

		getLogger().info("RandomGift enabled successfully!");
	}

	public void load() {
		gList = this.getConfig().getStringList("items").toArray(new String[0]);
		cooldownTime = this.getConfig().getInt("cooldown-time") * 60 * 1000;
		cooldown = 0;
		rGG = new RandomGiftGen(this);
	}

	@Override
	public void onDisable() {
		getLogger().info("RandomGift disabled successfully!");
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		player = event.getPlayer();

		getServer().getScheduler().scheduleSyncDelayedTask(this,
				new Runnable() {
					public void run() {
						try {
							rGG.check(player);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}, 30L);

	}

	public boolean onCommand(CommandSender sentby, Command command,
			String label, String[] args) {
		if (command.getName().equalsIgnoreCase("randomgift")) {

			if (args.length == 0) {
				sentby.sendMessage("RandomGift "
						+ this.getDescription().getVersion());
				sentby.sendMessage("Usage: /randomgift <command>");
				return true;
			}

			if (args[0].equalsIgnoreCase("cooldown")) {

				if (sentby.hasPermission("randomgift.cooldown")) {
					int difference = (int) (System.currentTimeMillis() - cooldown);
					int val = cooldownTime - difference;

					if (!(val <= 60000)) {
						sentby.sendMessage(broadcastTag + " About " + val / 60
								/ 1000 + " minutes remaining.");
					} else if (val <= 0) {
						sentby.sendMessage("Ready and waiting to be triggered!");
					} else {
						sentby.sendMessage(broadcastTag + val / 1000
								+ " seconds remaining.");
					}
				}

				if (args.length == 2) {
					if (args[1].equalsIgnoreCase("reset")) {

						if (sentby.hasPermission("randomgift.cooldown.reset")) {

							cooldown = System.currentTimeMillis()
									- cooldownTime;
							sentby.sendMessage("Cooldown timer has been reset!");
						}
					}
				}

				return true;
			}

			if (args[0].equalsIgnoreCase("gift")) {

				if (args.length == 2) {
					if (sentby.hasPermission("randomgift.gift")) {
						if (!(getServer().getPlayer(args[1]) == null)) {
							rGG.getPlayers(getServer().getPlayer(args[1]));
						} else {
							sentby.sendMessage("Player not online!");
						}
					} else {
						sentby.sendMessage("You didn't specify a player!");
					}
				}

				return true;
			}

		}

		return false;
	}

}
