package com.isitgeo.randomgift;

import java.io.IOException;
import java.util.Random;

import org.bukkit.ChatColor;
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

	private Random rand = new Random();

        if (plugin.getServer().getOnlinePlayers().size() > plugin.getConfig().getInt("minimum-players")){
            List<String> playersWithPerm = new ArrayList<>();

            for (Player player : plugin.getServer().getOnlinePlayers(){
                if (player.hasPermission("randomgift.receive"){
                    playersWithPerm.add(player.getName());
    			}
            }    
        Player rPlayer = plugin.getServer.getOnlinePlayer(playersWithPerm.get(rand.nextInt(playersWithPerm().size()));
		    plugin.getServer().broadcastMessage(
		    ChatColor.GOLD + "[RandomGift] " + ChatColor.WHITE
				+ rPlayer.getName() + " has been given a random gift!");

		    rPlayer.sendMessage(ChatColor.GOLD + "[RandomGift] " + ChatColor.WHITE
			     + "Be sure to thank " + player.getName()
			     + " for your random gift!");
		    generateGift(rPlayer);
	        }
	    }
	}

	public void generateGift(Player rPlayer) {

		Random gSelect = new Random();
		int gRand = gSelect.nextInt(plugin.gList.length);

		String[] itemQuant = plugin.gList[gRand].split(" ");

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
