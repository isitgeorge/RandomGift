package com.isitgeo.randomgift;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

public class KillGenerator {
	
	private RandomGift plugin;
	private Debugger debug;
	private Utilities util;
	private GiftGenerator giftGen;

	KillGenerator(RandomGift plugin, Debugger debug, GiftGenerator giftGen, Utilities util) {
		this.plugin = plugin;
		this.debug = debug;
		this.giftGen = giftGen;
		this.util = util;
	}

	public void killPlayer(final Player randomPlayer, final String randomPlayerName, final String triggerPlayer, final ItemStack giftItem) {
		randomPlayer.sendMessage(plugin.playerBroadcastTag + "Unfortunately as part of your gift, you will explode in " + plugin.killModeCountdown + " seconds...");
		
		plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
			@Override
			public void run() {
				if (randomPlayer.isOnline()) {
					debug.log("Killed " + randomPlayerName);
					killPlayer(randomPlayer);
					
					/*
					 * Give item ONLY once respawned - currently causes a nullpointerex
					 */
					//giftGen.giveGift(randomPlayer, triggerPlayer, true, giftItem); // ON RESPAWN?
					//debug.log("");
				} else {
					debug.log(randomPlayerName + " quit before being killed, adding to hitlist");
					
					Random rand = new Random();
					
					// Task to remove the player if they do not log back in within 5-15 minutes
					BukkitTask scheduledKillId = plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {
						@Override
						public void run() {
							plugin.playerHitList.remove(randomPlayerName);
							debug.log(randomPlayerName + " expired from hitlist");
						}
					}, util.minsToTicks(rand.nextInt((15 - 5) + 1) + 5)); // Between 15 and 5 minutes
					
					ArrayList<Object> hitListParams = new ArrayList<Object>();
					hitListParams.add(triggerPlayer);
					hitListParams.add(scheduledKillId);
					hitListParams.add(giftItem);
					plugin.playerHitList.put(randomPlayerName, hitListParams);
				}
			}
		}, util.secsToTicks(plugin.killModeCountdown));
	}
	
	public void killPlayer(Player player) {
		Location loc = player.getLocation();
		player.getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), 20, false, false);
		player.setHealth(0);
	}
	
}
