package com.isitgeo.randomgift;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RandomGiftGen {

	private final RandomGift plugin;
	private ItemStack items;

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
					plugin.getLogger().log(Level.INFO, "Checking if {0} has randomgift.trigger: true", player.getName());
				}
			} else {
				if (plugin.debugMode == true){
					plugin.getLogger().log(Level.INFO, "Checking if {0} has randomgift.trigger: false", player.getName());
				}
			}

		} else {
			if (plugin.debugMode == true){
				int difference = (int) (System.currentTimeMillis() - plugin.cooldown);
				int val = plugin.cooldownTime - difference;

				if (!(val <= 60000)) {
					plugin.getLogger().log(Level.INFO, "Cooldown time remaining: {0} minutes", val / 60 / 1000);
				} else {
					plugin.getLogger().log(Level.INFO, "Cooldown time remaining: {0} seconds", val / 1000);
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
					plugin.getLogger().log(Level.INFO, "{0} has randomgift.receive, added to list.", p.getName());
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
		
		if (itemQuant.length > 2){
		    int args = itemQuant.length;
		    
		    for (int i=0; args>i; i++){
			int itemNumber;
			
			if (i == 0){
			    	if (!(itemQuant[0].contains(":"))) {
				    itemNumber = Integer.parseInt(itemQuant[0]);
				    items = new ItemStack(Material.getMaterial(itemNumber),itemQuantity);

				} else {
				    itemDV = itemQuant[0].split(":");
				    itemNumber = Integer.parseInt(itemDV[0]);
				    int itemDataV = Integer.parseInt(itemDV[1]);
				    items = new ItemStack(Material.getMaterial(itemNumber),itemQuantity, (short) itemDataV);
				    
				}
			}
			if (i > 1){
			    String[] enchant = itemQuant[i].split(":");
			    String itemString;
			    int enchantPower = Integer.parseInt(enchant[1]);
			    String enchantName = enchant[0];
			    enchantName = enchantName.toUpperCase();
			    ItemMeta itemMeta = items.getItemMeta();
			    itemMeta.addEnchant(Enchantment.getByName(enchantName), enchantPower, true);
			    items.setItemMeta(itemMeta);
			}

	        }
		rPlayer.getInventory().addItem(items);
	    }
	}
}
