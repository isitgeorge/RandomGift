package com.isitgeo.randomgift;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class RandomGift extends JavaPlugin implements Listener {

	private File config;
	private Player player;
	private GiftGenerator giftGen;
	private Notifications notify;
	private Statistics stats;
	private Updater updater;
	private Debugger debug;
	private FileConfiguration cfg;
	
	public long cooldown;
	public int cooldownTime;
	public int minimumPlayers;
	public int configVersion;
	public int latestConfig;
	
	public boolean enableBroadcastMessage;
	public boolean allPlayers;
	public boolean versionCheck;
	public boolean collectStats;
	public boolean enableDebug;
	public boolean updateAvailable = false;
	public boolean adminNotifications;
	
	public String[] itemList;
	public String broadcastMessage; 
	public String broadcastTag = ChatColor.GOLD + "[RandomGift] " + ChatColor.WHITE;
	public String permError = ChatColor.DARK_RED + "You don't have permission to do that!";
	public String commandError = ChatColor.DARK_RED + "No such command!";
	
	
	@Override
	public void onEnable() {

		config = new File(getDataFolder(), "config.yml");
		
		if (!config.exists()) {
			getLogger().info("Creating new config...");
			this.saveDefaultConfig();
		}
		
		load();
		stats.sendStats();
		
		getServer().getPluginManager().registerEvents(this, this);
		getCommand("randomgift").setExecutor(new CommandListener(this, giftGen));
		
		getLogger().info("RandomGift enabled successfully!");
	}

	public void load() {
		
		cfg = this.getConfig();
		
		itemList = cfg.getStringList("items").toArray(new String[0]);
		minimumPlayers = cfg.getInt("minimum-players");
		allPlayers = cfg.getBoolean("all-players");
		cooldownTime = cfg.getInt("cooldown-time") * 60 * 1000;
		enableBroadcastMessage = cfg.getBoolean("enable-broadcast-message");
		broadcastMessage = cfg.getString("broadcast-message");
		versionCheck = cfg.getBoolean("version-check");
		adminNotifications = cfg.getBoolean("admin-notifications");
		enableDebug = cfg.getBoolean("debug-mode");
		collectStats = cfg.getBoolean("collect-statistics");
		
		if (cfg.contains("config-version")) {
			configVersion = Integer.parseInt(cfg.getString("config-version").replaceAll("[^0-9]", ""));
		} else {
			configVersion = 0;
		}
		
		latestConfig = Integer.parseInt(("1.0").replaceAll("[^0-9]", ""));
		cooldown = 0;
		
		debug = new Debugger(this);
		giftGen = new GiftGenerator(this, debug);
		notify = new Notifications(this);
		stats = new Statistics(this);
		updater = new Updater(this, notify);
		
		getLogger().info("Loaded configuration");
		debug.log("Debugger enabled! - It can be disabled inside config.yml");
		
		updater.checkForUpdate();
		notify.consoleOutdatedConfiguration();
	}
	
	@Override
	public void onDisable() {
		getLogger().info("RandomGift disabled successfully!");
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		player = event.getPlayer();
		
		debug.log(player.getName() + " has connected");

		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				try {
					giftGen.check(player);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				if (adminNotifications) {
					if (player.hasPermission("randomgift.admin")) {
						notify.playerUpdateAvailable(player);
						notify.playerOutatedConfiguration(player);
					}
				}
			}
		}, 30L);
	}
}