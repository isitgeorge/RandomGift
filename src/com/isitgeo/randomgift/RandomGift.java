package com.isitgeo.randomgift;

import java.io.File;
import java.io.IOException;

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

		getLogger().info("This plugin collects statistic data and sends it to http://mcstats.org/plugin/RandomGift");
		getLogger().info("You can opt-out by editing the PluginMetrics configuration located in the plugins folder.");
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

}
