package com.isitgeo.randomgift;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiftGenerator {

	private RandomGift plugin;

	public GiftGenerator(RandomGift plugin) {
		this.plugin = plugin;
	}

	public void check(Player player) throws IOException {

		if (System.currentTimeMillis() - plugin.cooldown >= plugin.cooldownTime) {
			if (plugin.debug){
				plugin.getLogger().info("Cooldown time remaining: 0");
			}
			
			if (player.hasPermission("randomgift.trigger")) {
				getPlayers(player);
				plugin.cooldown = System.currentTimeMillis();
				
				if (plugin.debug){
					plugin.getLogger().info("Checking if" + player.getName() + "has randomgift.trigger: true");
				}
			} else {
				if (plugin.debug){
					plugin.getLogger().info("Checking if " + player.getName() + " has randomgift.trigger: false");
				}
			}

		} else {
			if (plugin.debug){
				int difference = (int) (System.currentTimeMillis() - plugin.cooldown);
				int val = plugin.cooldownTime - difference;

				if (!(val <= 60000)) {
					plugin.getLogger().info("Cooldown time remaining: " + val / 60 / 1000 + " minutes");
				} else {
					plugin.getLogger().info("Cooldown time remaining: " + val / 1000 + " seconds");
				}
                                        
			}
		}
	}

	public void getPlayers(Player player) {
		Player[] pListTotal = plugin.getServer().getOnlinePlayers();
		String pList = "";
		
		for (Player p : plugin.getServer().getOnlinePlayers()){
			if (p.hasPermission("randomgift.receive")){
				
				if (plugin.debug){
					plugin.getLogger().info(p.getName() + " has randomgift.receive, added to list.");
				}
				
				pList += p.getName() + " ";
			}
		}
		
		String[] pListArray = pList.split("\\s+");
		
		if (plugin.allPlayers == true){
			if (pListTotal.length < plugin.minimumPlayers) {
				
				if (plugin.debug){
					plugin.getLogger().info("Not enough players online.");
				}
				return;
			}
		} else {
			if (pListArray.length < plugin.minimumPlayers){
				if (plugin.debug){
					plugin.getLogger().info("Not enough players online.");
				}
				return;
			}
		}

		Random pSelect = new Random();
		int pRand = pSelect.nextInt(pListArray.length);

		Player rPlayer = plugin.getServer().getPlayer(pListArray[pRand]);
		
		if (plugin.debug){
			plugin.getLogger().info(rPlayer.getName() + " has been selected for gift.");
		}
		
		if (plugin.enableBroadcastMessage == true) {	
			plugin.getServer().broadcastMessage(plugin.broadcastTag + plugin.broadcastMessage.replace("%p", rPlayer.getName()));
		}

		rPlayer.sendMessage(plugin.broadcastTag + "Be sure to thank " + player.getName() + " for your RandomGift!");
		generateGift(rPlayer);
	}

	@SuppressWarnings("deprecation")
	public void generateGift(Player rPlayer) {

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
	}
}
