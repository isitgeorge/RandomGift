package com.isitgeo.randomgift;

import static com.isitgeo.randomgift.Enchantments.getEnchantment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GiftGenerator {

	private RandomGift plugin;
	private Debugger debug;
	private KillGenerator killGen;
	private Utilities util;
	
	private List<String> playerList = new ArrayList<String>();
	
	public GiftGenerator(RandomGift plugin, KillGenerator killGen, Debugger debug, Utilities util) {
		this.plugin = plugin;
		this.killGen = killGen;
		this.debug = debug;
		this.util = util;
	}

	public void check(Player player) {

		if (System.currentTimeMillis() - plugin.cooldown >= plugin.cooldownTime) {
			debug.log("Cooldown timer ready");
			
			if (player.hasPermission("randomgift.trigger")) {
				debug.log("Checking " + player.getName() + " has trigger permission");
				getPlayers(player.getName(), false, true);
				plugin.cooldown = System.currentTimeMillis();
			} else {
				debug.log(player.getName() + " does not have trigger permission");
			}

		} else {
			debug.log("Cooldown timer not ready");
		}
	}

	public void getPlayers(String triggerPlayer, Boolean ignoreMinPlayers, Boolean displayFrom) {
		playerList.clear();
		Player[] allPlayersList = plugin.getServer().getOnlinePlayers();
		
		for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()){
			
			if (onlinePlayer.hasPermission("randomgift.receive")){
				debug.log(onlinePlayer.getName() + " has randomgift.receive, added to list");
				playerList.add(onlinePlayer.getName());
				
			}
		}
		
		if (!ignoreMinPlayers) {

			if (plugin.allPlayers && allPlayersList.length < plugin.minimumPlayers) {
					debug.log("Not enough players currently online");
					util.resetCooldown();
					return;
			} else {
				if (playerList.size() < plugin.minimumPlayers) {
					debug.log("Not enough players currently online");
					util.resetCooldown();
					return;
				}
			}
		} 
		
		if (allPlayersList.length < 1 || playerList.size() < 1) {
			if (triggerPlayer != "Console") {
				plugin.getServer().getPlayer(triggerPlayer).sendMessage(plugin.playerBroadcastTag + "No eligible online players!");
			} else {
				plugin.getLogger().info("No eligible online players!");
			}
			
			util.resetCooldown();
			return;
		}

		Random playerSelector = new Random();
		int playerRandom = playerSelector.nextInt(playerList.size());

		Player randomPlayer = plugin.getServer().getPlayer(playerList.get(playerRandom));
		
		debug.log(randomPlayer.getName() + " has been selected for a gift");
		
		generateGift(randomPlayer, triggerPlayer, displayFrom);
	}

	@SuppressWarnings("deprecation")
	public void generateGift(final Player randomPlayer, final String triggerPlayer, Boolean displayFrom) {
		
		final String randomPlayerName = randomPlayer.getName();
		ItemStack giftItem = null;
		Random giftSelection = new Random();
		
		int randomSelection = giftSelection.nextInt(plugin.itemList.length);

		String[] giftArguments = plugin.itemList[randomSelection].split(" ");

		int giftQuantity = Integer.parseInt(giftArguments[1]);

		String[] giftDataValue;
		
		if (giftArguments.length > 1){
			
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
								util.resetCooldown();
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
			
			
			if (plugin.killMode == true) {
				Random deathSelect = new Random();
				if ((deathSelect.nextInt((100 - 1) + 1) + 1) > (100 - plugin.killModeChance)) {
					killGen.killPlayer(randomPlayer, randomPlayerName, triggerPlayer, giftItem);
				}
			} else {
				giveGift(randomPlayer, triggerPlayer, displayFrom, giftItem);
			}
			
	    } else {
	    	plugin.getLogger().warning("Invalid item - Please check your configuration.");
	    	util.resetCooldown();	    	
	    	return;
	    }
	}
	
	public void giveGift(Player randomPlayer, String triggerPlayer, Boolean displayFrom, ItemStack giftItem) {
		if (randomPlayer.getInventory().firstEmpty() == -1) {
			Location playerLocation = randomPlayer.getLocation();
			playerLocation.getWorld().dropItem(playerLocation, giftItem);
			randomPlayer.sendMessage(plugin.playerBroadcastTag + "Your RandomGift has been dropped near you because your inventory is full.");				
		} else {
			randomPlayer.getInventory().addItem(giftItem);
		}
		
		if (plugin.enableBroadcastMessage) {	
			plugin.getServer().broadcastMessage(plugin.broadcastTag + plugin.broadcastMessage.replace("%p", randomPlayer.getName()));
		}
		
		if (triggerPlayer != randomPlayer.getName() && displayFrom) {
			randomPlayer.sendMessage(plugin.playerBroadcastTag + "Be sure to thank " + triggerPlayer + " for your RandomGift!");
		}
	}
}
