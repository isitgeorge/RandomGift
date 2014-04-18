package com.isitgeo.randomgift;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class RandomGift extends JavaPlugin implements Listener {

	private File config;
	private Player player;
	private GiftGenerator giftGen;
	private KillGenerator killGen;
	private Notifications notify;
	private Statistics stats;
	private Updater updater;
	private Debugger debug;
	private Utilities util;
	private FileConfiguration cfg;
	
	public long cooldown;
	
	public int cooldownTime, minimumPlayers, configVersion, latestConfig, killModeChance, killModeCountdown;
	
	public boolean enableBroadcastMessage, allPlayers, versionCheck, collectStats, enableDebug, adminNotifications, killMode,
	updateAvailable = false;
	
	public String[] itemList;
	
	public String broadcastMessage,
	broadcastTag = ChatColor.GOLD + "[RandomGift] " + ChatColor.WHITE,
	playerBroadcastTag = ChatColor.GRAY + "[RandomGift] " + ChatColor.RESET + ChatColor.ITALIC,
	permissionError = ChatColor.GRAY + "[RandomGift] " + ChatColor.DARK_RED + "You don't have permission to do that!",
	commandError = ChatColor.GRAY + "[RandomGift] " + ChatColor.DARK_RED + "That command does not exist!",
	invalidCommand = ChatColor.GRAY + "[RandomGift] " + ChatColor.DARK_RED + "That command is invalid!",
	historicPlayer = "";
	
	Map<String, ArrayList<Object>> playerHitList = new HashMap<String, ArrayList<Object>>();
	
	
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
		killGen = new KillGenerator(this, debug, giftGen, util);
		giftGen = new GiftGenerator(this, killGen, debug, util);
		notify = new Notifications(this);
		stats = new Statistics(this);
		updater = new Updater(this, notify);
		cfg = this.getConfig();
		
		itemList = cfg.getStringList("items").toArray(new String[0]);
		minimumPlayers = cfg.getInt("minimum-players");
		allPlayers = cfg.getBoolean("all-players");
		cooldownTime = cfg.getInt("cooldown-time") * 60 * 1000;
		killMode = cfg.getBoolean("kill-mode");
		killModeChance = util.getInt(cfg.getString("kill-mode-chance"));
		killModeCountdown = cfg.getInt("kill-mode-countdown");
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
		final String playerName = player.getName();
		
		debug.log(playerName + " has connected");

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
				
				if (killMode && playerHitList.containsKey(playerName)) {
					debug.log(playerName + " is on player kill list");
					killGen.killPlayer(player);
					
					getServer().broadcastMessage(broadcastTag + playerName + " narrowly avoided being randomly killed. Scheduling for next login");
					
					/*
					 * Give gift ONLY once respawned - currently causes nullpointerex
					 */
					//giftGen.giveGift(player, (String) playerHitList.get(playerName).get(0), true, (ItemStack) playerHitList.get(playerName).get(2));
					//debug.log("");

					getServer().getScheduler().cancelTask((int) playerHitList.get(playerName).get(1));
					debug.log("Removed hitlist removal task for " + playerName);
					
					playerHitList.remove(playerName);
					debug.log("Expired " + playerName + " from hitlist");
					
					return; // Do not generate a new gift
				}
				
				giftGen.check(player);
			}
		}, util.secsToTicks(2));
	}
}