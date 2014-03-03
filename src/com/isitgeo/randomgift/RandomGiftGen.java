package com.isitgeo.randomgift;

import java.io.IOException;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RandomGiftGen {

	private RandomGift plugin;

	public RandomGiftGen(RandomGift plugin) {
		this.plugin = plugin;
	}

	public void check(Player player) throws IOException {

		if (System.currentTimeMillis() - plugin.cooldown >= plugin.cooldownTime) {
			if (player.hasPermission("randomgift.trigger")) {
				getPlayers(player);
				plugin.cooldown = System.currentTimeMillis();
			} else {
				return;
			}

		}
	}

	public void getPlayers(Player player) {
		Player[] pListTotal = plugin.getServer().getOnlinePlayers();
		String pList = "";
		
		for (Player p : plugin.getServer().getOnlinePlayers()){
			if (p.hasPermission("randomgift.receive")){
				pList += p.getName() + " ";
			}
		}
		
		String[] pListArray = pList.split("\\s+");
		
		if (plugin.allPlayers == true){
			if (pListTotal.length < plugin.minimumPlayers) {
				return;
			}
		} else {
			if (pListArray.length < plugin.minimumPlayers){
				return;
			}
		}
		
		Random pSelect = new Random();
		int pRand = pSelect.nextInt(pListArray.length);

		Player rPlayer = plugin.getServer().getPlayer(pListArray[pRand]);
		
		generateGift(rPlayer, player);
	}

	@SuppressWarnings("deprecation")
	public void generateGift(final Player rPlayer, Player player) {

		Random gSelect = new Random();
		int gRand = gSelect.nextInt(plugin.itemList.length);

		String[] itemQuant = plugin.itemList[gRand].split(" ");

		int itemQuantity = Integer.parseInt(itemQuant[1]);

		String[] itemDV;

		if (!(itemQuant[0].contains(":"))) {

			int itemNumber = Integer.parseInt(itemQuant[0]);

			rPlayer.getInventory().addItem(
					new ItemStack(Material.getMaterial(itemNumber),
							itemQuantity));

		} else {
			itemDV = itemQuant[0].split(":");

			int itemNumber = Integer.parseInt(itemDV[0]);
			int itemDataV = Integer.parseInt(itemDV[1]);

			rPlayer.getInventory().addItem(
					new ItemStack(Material.getMaterial(itemNumber),
							itemQuantity, (short) itemDataV));
		}
		
		if (plugin.broadcastMessage == true) {	
			plugin.getServer().broadcastMessage(plugin.broadcastTag + rPlayer.getName() + " has been given a random gift!");
		}
		
		if (plugin.deathMode == true) {
			Random deathSelect = new Random();
			if ((deathSelect.nextInt((100 - 1) + 1) + 1) > 90) {
				rPlayer.sendMessage(plugin.broadcastTag + "Be sure to thank " + player.getName() + " for your random gift!");
				rPlayer.sendMessage(plugin.broadcastTag + "Unfortunately as part of your gift, you will explode in 5 seconds...");
				
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						public void run() {
							// Check player still exists
							rPlayer.setHealth(0);
							// Add explosion effect
						}
				}, 100L);
				return;
			}
		}
		
		rPlayer.sendMessage(plugin.broadcastTag + "Be sure to thank " + player.getName() + " for your random gift!");
	}
}