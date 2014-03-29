package com.isitgeo.randomgift;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import java.util.logging.Level;
import static com.isitgeo.randomgift.Enchantments.getEnchantment;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GiftGenerator {

	private RandomGift plugin;
	private Debugger debug;

	private ItemStack giftItem;
	private String playerGifter;


	public GiftGenerator(RandomGift plugin, Debugger debug) {
		this.plugin = plugin;
		this.debug = debug;
	}

	public void check(Player player) throws IOException {

		if (System.currentTimeMillis() - plugin.cooldown >= plugin.cooldownTime) {
			debug.log("Cooldown timer ready");
			
			if (player.hasPermission("randomgift.trigger")) {
				debug.log("Checking " + player.getName() + " has trigger permission");
				getPlayers(player, false);
				plugin.cooldown = System.currentTimeMillis();
			} else {
				debug.log(player.getName() + " does not have trigger permission");
			}

		} else {
			debug.log("Cooldown timer not ready");
		}
	}

	public void getPlayers(Player player, Boolean ignoreMinPlayers) {
		Player[] pListTotal = plugin.getServer().getOnlinePlayers();
		String pList = "";
		
		for (Player p : plugin.getServer().getOnlinePlayers()){
			if (p.hasPermission("randomgift.receive")){
				debug.log(p.getName() + " has randomgift.receive, added to list");
				pList += p.getName() + " ";
			}
		}
		
		String[] pListArray = pList.split("\\s+");
		
		if (!ignoreMinPlayers) {

			if (plugin.allPlayers == true) {
				if (pListTotal.length < plugin.minimumPlayers) {
					debug.log("Not enough players currently online");
					resetCooldownTimer();
					return;
				}
			} else {
				if (pListArray.length < plugin.minimumPlayers) {
					debug.log("Not enough players currently online");
					resetCooldownTimer();
					return;
				}
			}
		}

		Random pSelect = new Random();
		int pRand = pSelect.nextInt(pListArray.length);

		Player rPlayer = plugin.getServer().getPlayer(pListArray[pRand]);
		
		debug.log(rPlayer.getName() + " has been selected for a gift");
		
		if (plugin.enableBroadcastMessage) {	
			plugin.getServer().broadcastMessage(plugin.broadcastTag + plugin.broadcastMessage.replace("%p", rPlayer.getName()));
		}
		
		if (player.getName() != rPlayer.getName()) {
			rPlayer.sendMessage(plugin.broadcastTag + "Be sure to thank " + player.getName() + " for your RandomGift!");
		}
		
		generateGift(rPlayer);
	}

	@SuppressWarnings("deprecation")
	public void generateGift(Player rPlayer) {

		Random giftSelection = new Random();
		int randomSelection = giftSelection.nextInt(plugin.itemList.length);

		String[] giftArguments = plugin.itemList[randomSelection].split(" ");

		int giftQuantity = Integer.parseInt(giftArguments[1]);

		String[] giftDataValue;
		
		if (giftArguments.length > 2){
			
			for (int i = 0; giftArguments.length > i; i++) {
			int giftItemID;
			
				if (i == 0){
					if (!(giftArguments[0].contains(":"))) {
						giftItemID = Integer.parseInt(giftArguments[0]);
						giftItem = new ItemStack(Material.getMaterial(giftItemID), giftQuantity);
					} else {
						giftDataValue = giftArguments[0].split(":");
						giftItemID = Integer.parseInt(giftDataValue[0]);
						giftItem = new ItemStack(Material.getMaterial(giftItemID), giftQuantity, (short) Integer.parseInt(giftDataValue[1])); 
					}
				}
			
			
				if (i > 1) {
					String[] enchantmentArgs = giftArguments[i].split(":");

					if (enchantmentArgs.length < 2) {
						plugin.getLogger().warning("Item \"" + plugin.itemList[randomSelection] + "\" is improperly defined, no gift given.");
						return;
					}
			    
					String enchantmentName = enchantmentArgs[0];
					enchantmentName = enchantmentName.toUpperCase();
			    
					switch (enchantmentName) {
						case "NAME": {
							String enchantmentNameName = enchantmentArgs[1].replace("_", " ");
							ItemMeta itemMeta = giftItem.getItemMeta();
							itemMeta.setDisplayName(enchantmentNameName);
							giftItem.setItemMeta(itemMeta);
							break;
						}
			    	
						case "LORE": {
							String[] enchantmentLoreName = enchantmentArgs[1].replace("_", " ").split("\\|");
							ItemMeta itemMeta = giftItem.getItemMeta();
							itemMeta.setLore(Arrays.asList(enchantmentLoreName));
							giftItem.setItemMeta(itemMeta);
							break;
						}
			    	
						default: {
							int enchantPower = Integer.parseInt(enchantmentArgs[1]);
							Enchantment enchantment = getEnchantment(enchantmentName);
							
							if (enchantment == null){
								plugin.getLogger().warning("Enchantment \"" + enchantmentName + "\" is not valid, no gift given.");
								resetCooldownTimer();
								return;
							}

							ItemMeta itemMeta = giftItem.getItemMeta();
							itemMeta.addEnchant(enchantment, enchantPower, true);
							giftItem.setItemMeta(itemMeta);
							break;
						}
					}
				}
	        }
			
		rPlayer.getInventory().addItem(giftItem);
		
		if (plugin.enableBroadcastMessage) {	
			plugin.getServer().broadcastMessage(plugin.broadcastTag + rPlayer.getName() + " has been given a random gift!");
		}
		
		plugin.historicPlayer = rPlayer.getName();

		rPlayer.sendMessage(plugin.playerBroadcastTag + "Be sure to thank " + playerGifter + " for your random gift!");
	    } else {
	    	plugin.getLogger().warning("Invalid item - Please check your configuration.");
	    	resetCooldownTimer();
	    	return;
	    }
	}
	
	private void resetCooldownTimer() {
		// If gift fails to be delivered, this method is called
		plugin.cooldown = System.currentTimeMillis() - plugin.cooldownTime;
	}
}


