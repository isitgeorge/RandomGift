package com.isitgeo.randomgift;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class RandomGift extends JavaPlugin implements Listener {

	private File config;
	private Player player;
	private RandomGiftGen rGG;
	private UpdateCheck updateCheck;
	public long cooldown;
	public int cooldownTime;
	public boolean broadcastMessage;
	public boolean versionCheck;
	public boolean statisticsCheck;
	public String[] gList;
	public String broadcastTag = ChatColor.GOLD + "[RandomGift] " + ChatColor.WHITE;
	public String permError = ChatColor.DARK_RED + "You don't have permission to do that!";
	public String commandError = ChatColor.DARK_RED + "No such command!";
	@Override
	public void onEnable() {

		config = new File(getDataFolder(), "config.yml");
	    this.getConfig().options().copyDefaults(true);
	    this.saveConfig();
		
		
		
		if (!(config.exists())) {
			getLogger().info("Configuration not found...");
			this.saveDefaultConfig();
			getLogger().info("Created configuration!");
		}
		
		load();
		updateCheck.check();
		
		getServer().getPluginManager().registerEvents(this, this);
		getCommand("randomgift").setExecutor(new CommandListener(this, rGG));
		
		if (statisticsCheck == true) {
			URL updateSend = null;
			try {
				updateSend = new URL("http://plugin-stats.isitgeo.com");
			} catch (MalformedURLException e) {
				
			}
			
			URLConnection updateSender = null;
			try {
				updateSender = updateSend.openConnection();
				updateSender.setRequestProperty("plugin-name", "RandomGift");
				updateSender.setRequestProperty("plugin-version", this.getDescription().getVersion().toString());
				updateSender.setReadTimeout(5000);
				updateSender.getInputStream();
			}  catch (IOException e) {
				// Failed to contact statistic server
			}
			
			try {
				MetricsLite metrics = new MetricsLite(this);
				metrics.start();
			} catch (IOException e) {
				// Failed to submit the stats :-(
			}
		}
		
		getLogger().info("RandomGift enabled successfully!");
	}

	public void load() {
		gList = this.getConfig().getStringList("items").toArray(new String[0]);
		cooldownTime = this.getConfig().getInt("cooldown-time") * 60 * 1000;
		cooldown = 0;
		broadcastMessage = this.getConfig().getBoolean("broadcast-message");
		versionCheck = this.getConfig().getBoolean("version-check");
		rGG = new RandomGiftGen(this);
		updateCheck = new UpdateCheck(this);
		statisticsCheck = this.getConfig().getBoolean("collect-statistics");
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
}
