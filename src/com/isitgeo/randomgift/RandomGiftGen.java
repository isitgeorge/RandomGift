package com.isitgeo.randomgift;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;

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
                    if (plugin.debugMode == true){
                        plugin.getLogger().info("Cooldown time remaining: 0");
                    }
			if (player.hasPermission("randomgift.trigger")) {
				getPlayers(player);
				plugin.cooldown = System.currentTimeMillis();
                                if (plugin.debugMode == true){
                                    plugin.getLogger().log(Level.INFO, "{0} has permission node randomgift.trigger", player);
                                }
			} else {
                                if (plugin.debugMode == true){
                                    plugin.getLogger().log(Level.INFO, "{0} does not have permission node randomgift.trigger", player);
                                }
			}

		} else {
                    if (plugin.debugMode == true){
                        int difference = (int) (System.currentTimeMillis() - plugin.cooldown);
					int val = plugin.cooldownTime - difference;

					if (!(val <= 60000)) {
						plugin.getLogger().log(Level.INFO, "Cooldown has about {0} minutes remaining.", val / 60 / 1000);
					} else {
						plugin.getLogger().log(Level.INFO, "Cooldown has {0} seconds remaining.", val / 1000);
                                        }
                                        
                    }
                }
	}

	public void getPlayers(Player player) {
		Player[] pListTotal = plugin.getServer().getOnlinePlayers();
		String pList = "";
		
		for (Player p : plugin.getServer().getOnlinePlayers()){
			if (p.hasPermission("randomgift.receive")){
                            if (plugin.debugMode == true){
                                plugin.getLogger().log(Level.INFO, "{0} has permission node randomgift.receive, added to list.", p);
                            }
				pList += p.getName() + " ";
			}
		}
		
		String[] pListArray = pList.split("\\s+");
		
		if (plugin.allPlayers == true){
			if (pListTotal.length < plugin.minimumPlayers) {
                            if (plugin.debugMode == true){
                                plugin.getLogger().info("Not enough players online.");
                            }
				return;
			}
		} else {
			if (pListArray.length < plugin.minimumPlayers){
                            if (plugin.debugMode == true){
                                plugin.getLogger().info("Not enough players online.");
                            }
				return;
			}
		}

		Random pSelect = new Random();
		int pRand = pSelect.nextInt(pListArray.length);

		Player rPlayer = plugin.getServer().getPlayer(pListArray[pRand]);
                if (plugin.debugMode == true){
                    plugin.getLogger().log(Level.INFO, "{0} has been selected for gift.", rPlayer);
                }
		
		if (plugin.broadcastMessage == true) {	
			plugin.getServer().broadcastMessage(plugin.broadcastTag + rPlayer.getName() + " has been given a random gift!");
		}

		rPlayer.sendMessage(plugin.broadcastTag + "Be sure to thank " + player.getName() + " for your random gift!");
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