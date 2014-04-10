package com.isitgeo.randomgift;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
	private Utilities util;
	private FileConfiguration cfg;
	
	public long cooldown;
	
	public int cooldownTime, minimumPlayers, configVersion, latestConfig, deathModeChance, deathModeCountdown;
	
	public boolean enableBroadcastMessage, allPlayers, versionCheck, collectStats, enableDebug, adminNotifications, deathMode,
	updateAvailable = false;
	
	public String[] itemList;
	
	public String broadcastMessage,
	broadcastTag = ChatColor.GOLD + "[RandomGift] " + ChatColor.WHITE,
	playerBroadcastTag = ChatColor.GRAY + "[RandomGift] " + ChatColor.RESET + ChatColor.ITALIC,
	permissionError = ChatColor.GRAY + "[RandomGift] " + ChatColor.DARK_RED + "You don't have permission to do that!",
	commandError = ChatColor.GRAY + "[RandomGift] " + ChatColor.DARK_RED + "That command does not exist!",
	invalidCommand = ChatColor.GRAY + "[RandomGift] " + ChatColor.DARK_RED + "That command is invalid!",
	historicPlayer = "";
	
	Map<String, String> playerHitList = new HashMap<String, String>();
	
	
	@Override
	public void onEnable() {

		config = new File(getDataFolder(), "config.yml");
		
		if (!config.exists()) {
			getLogger().info("Creating new config...");
			this.saveDefaultConfig();
		}
		
		load();
		updater.checkForUpdate();
		stats.sendStats();
		
		getServer().getPluginManager().registerEvents(this, this);
		getCommand("randomgift").setExecutor(new CommandListener(this, giftGen, debug, util));
		
		getLogger().info("Enabled successfully!");
	}

	public void load() {
		
		util = new Utilities(this);
		debug = new Debugger(this);
		giftGen = new GiftGenerator(this, debug, util);
		notify = new Notifications(this);
		stats = new Statistics(this);
		updater = new Updater(this, notify);
		cfg = this.getConfig();
		
		itemList = cfg.getStringList("items").toArray(new String[0]);
		minimumPlayers = cfg.getInt("minimum-players");
		allPlayers = cfg.getBoolean("all-players");
		cooldownTime = cfg.getInt("cooldown-time") * 60 * 1000;
		deathMode = cfg.getBoolean("death-mode");
		deathModeChance = util.getInt(cfg.getString("death-mode-chance"));
		deathModeCountdown = cfg.getInt("death-mode-countdown");
		enableBroadcastMessage = cfg.getBoolean("enable-broadcast-message");
		broadcastMessage = cfg.getString("broadcast-message");
		versionCheck = cfg.getBoolean("version-check");
		adminNotifications = cfg.getBoolean("admin-notifications");
		enableDebug = cfg.getBoolean("debug-mode");
		collectStats = cfg.getBoolean("collect-statistics");
		
		if (cfg.contains("config-version")) {
			configVersion = util.getInt(cfg.getString("config-version"));
		} else {
			configVersion = 0;
		}
		
		latestConfig = util.getInt("1.0");
		cooldown = System.currentTimeMillis() - cooldownTime;;
		
		getLogger().info("Loaded configuration");
		
		notify.consoleOutdatedConfiguration();
		notify.consoleDebugEnabled();
	}
	
	@Override
	public void onDisable() {
		getLogger().info("Disabled successfully!");
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		player = event.getPlayer();
		
		debug.log(player.getName() + " has connected");

		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			@Override
			public void run() {
				
				if (adminNotifications) {
					if (player.hasPermission("randomgift.admin")) {
						debug.log(player.getName() + " is an admin");
						notify.playerUpdateAvailable(player);
						notify.playerOutatedConfiguration(player);
						notify.playerDebugEnabled(player);
					}
				}
				
				if (deathMode && playerHitList.containsKey(player.getName())) {
					debug.log(player.getName() + " is on player kill list");
					giftGen.killPlayer(player);
					playerHitList.remove(player);
					return; // Do not generate a new gift
				}
				
				giftGen.check(player);
			}
		}, 30L);
	}
}