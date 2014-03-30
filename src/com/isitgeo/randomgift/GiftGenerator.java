package com.isitgeo.randomgift;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static com.isitgeo.randomgift.Enchantments.getEnchantment;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GiftGenerator {

	private RandomGift plugin;
	private Debugger debug;

	private ItemStack giftItem;
	private List<String> playerList = new ArrayList<String>();

	public GiftGenerator(RandomGift plugin, Debugger debug) {
		this.plugin = plugin;
		this.debug = debug;
	}

	public void check(Player player) {

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
		playerList.clear();
		Player[] allPlayersList = plugin.getServer().getOnlinePlayers();
		
		for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()){
			
			if (onlinePlayer.hasPermission("randomgift.receive")){
				debug.log(onlinePlayer.getName() + " has randomgift.receive, added to list");
				playerList.add(onlinePlayer.getName());
				
			}
		}
		
		if (!ignoreMinPlayers) {

			if (plugin.allPlayers == true) {
				if (allPlayersList.length < plugin.minimumPlayers) {
					debug.log("Not enough players currently online");
					resetCooldownTimer();
					return;
				}
			} else {
				if (playerList.size() < plugin.minimumPlayers) {
					debug.log("Not enough players currently online");
					resetCooldownTimer();
					return;
				}
			}
		}

		Random playerSelector = new Random();
		int playerRandom = playerSelector.nextInt(playerList.size());

		Player randomPlayer = plugin.getServer().getPlayer(playerList.get(playerRandom));
		
		debug.log(randomPlayer.getName() + " has been selected for a gift");
		
		generateGift(randomPlayer, player);
	}

	@SuppressWarnings("deprecation")
	public void generateGift(final Player randomPlayer, Player triggerPlayer) {

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
			
			if (plugin.enableBroadcastMessage) {	
				plugin.getServer().broadcastMessage(plugin.broadcastTag + plugin.broadcastMessage.replace("%p", randomPlayer.getName()));
			}
		
			if (triggerPlayer.getName() != randomPlayer.getName()) {
				randomPlayer.sendMessage(plugin.playerBroadcastTag + "Be sure to thank " + triggerPlayer.getName() + " for your RandomGift!");
			}
			
			if (randomPlayer.getInventory().firstEmpty() == -1) {
				Location playerLocation = randomPlayer.getLocation();
				playerLocation.getWorld().dropItem(playerLocation, giftItem);
				randomPlayer.sendMessage(plugin.playerBroadcastTag + "Your RandomGift has been dropped near you because your inventory is full.");				
			} else {
				randomPlayer.getInventory().addItem(giftItem);
			}
			
			if (plugin.deathMode == true) {
				Random deathSelect = new Random();
				if ((deathSelect.nextInt((100 - 1) + 1) + 1) > (100 - plugin.deathModeChance)) {
					randomPlayer.sendMessage(plugin.broadcastTag + "Unfortunately as part of your gift, you will explode in 5 seconds...");
					
					plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
							public void run() {
								// Check player still exists
								
								if (randomPlayer.isOnline()) {
									randomPlayer.setHealth(0);
									// Explosion
								} else {
									// Player quit
								}
							}
					}, 100L);
					return;
				}
			}
			
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
