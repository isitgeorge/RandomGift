package com.isitgeo.randomgift;

import java.io.IOException;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiftGenerator {

	private RandomGift plugin;
	private Debugger debug;

	public GiftGenerator(RandomGift plugin, Debugger debug) {
		this.plugin = plugin;
		this.debug = debug;
	}

	public void check(Player player) throws IOException {

		if (System.currentTimeMillis() - plugin.cooldown >= plugin.cooldownTime) {
			debug.log("Cooldown timer ready");
			
			if (player.hasPermission("randomgift.trigger")) {
				debug.log("Checking " + player.getName() + " has trigger permission");
				getPlayers(player);
				plugin.cooldown = System.currentTimeMillis();
			} else {
				debug.log(player.getName() + " does not have trigger permission");
			}

		} else {
			debug.log("Cooldown timer not ready");
		}
	}

	public void getPlayers(Player player) {
		Player[] pListTotal = plugin.getServer().getOnlinePlayers();
		String pList = "";
		
		for (Player p : plugin.getServer().getOnlinePlayers()){
			if (p.hasPermission("randomgift.receive")){
				debug.log(p.getName() + " has randomgift.receive, added to list");
				pList += p.getName() + " ";
			}
		}
		
		String[] pListArray = pList.split("\\s+");
		
		if (plugin.allPlayers == true){
			if (pListTotal.length < plugin.minimumPlayers) {
				debug.log("Not enough players currently online");
				return;
			}
		} else {
			if (pListArray.length < plugin.minimumPlayers){
				debug.log("Not enough players online");
				return;
			}
		}

		Random pSelect = new Random();
		int pRand = pSelect.nextInt(pListArray.length);

		Player rPlayer = plugin.getServer().getPlayer(pListArray[pRand]);
		
		debug.log(rPlayer.getName() + " has been selected for a gift");
		
		if (plugin.enableBroadcastMessage) {	
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
		
		plugin.historicPlayer = rPlayer.getName();
	}
}
