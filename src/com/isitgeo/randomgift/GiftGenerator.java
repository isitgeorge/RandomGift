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

	private ItemStack items;
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
					plugin.cooldown = System.currentTimeMillis() - plugin.cooldownTime;
					return;
				}
			} else {
				if (pListArray.length < plugin.minimumPlayers) {
					debug.log("Not enough players currently online");
					plugin.cooldown = System.currentTimeMillis() - plugin.cooldownTime;
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

			    if (enchant.length < 2){
				plugin.getLogger().log(Level.WARNING, "Item - {0} improperly defined, no gift given.", plugin.itemList[gRand]);
				return;
			    }

			    String enchantName = enchant[0];
			    enchantName = enchantName.toUpperCase();

			    switch (enchantName) {
			    	case "NAME":
				    {
					String name = enchant[1].replace("_", " ");
					ItemMeta itemMeta = items.getItemMeta();
					itemMeta.setDisplayName(name);
					items.setItemMeta(itemMeta);
					break;
				    }
			    	case "LORE":
				    {
					String loreRaw = enchant[1].replace("_", " ");
					String[] lore = loreRaw.split("\\|");
					ItemMeta itemMeta = items.getItemMeta();
					itemMeta.setLore(Arrays.asList(lore));
					items.setItemMeta(itemMeta);
					break;
				    }
			    	default:
				    {
					int enchantPower = Integer.parseInt(enchant[1]);

					Enchantment enchantment = getEnchantment(enchantName);
					if (enchantment == null){
					    plugin.getLogger().log(Level.WARNING, "Enchantment {0} not valid, no gift given.", enchantName);
					    return;
					}

					ItemMeta itemMeta = items.getItemMeta();
					itemMeta.addEnchant(enchantment, enchantPower, true);
					items.setItemMeta(itemMeta);
					break;
				    }
			    }
			}

	        }
		rPlayer.getInventory().addItem(items);
		if (plugin.enableBroadcastMessage) {	
			plugin.getServer().broadcastMessage(plugin.broadcastTag + rPlayer.getName() + " has been given a random gift!");
		}
		
		plugin.historicPlayer = rPlayer.getName();

		rPlayer.sendMessage(plugin.broadcastTag + "Be sure to thank " + playerGifter + " for your random gift!");
	    }
	}
}
