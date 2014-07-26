package com.isitgeo.randomgift;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.plugin.RegisteredServiceProvider;

public class EconomyHandler {
	private RandomGift plugin;

	public EconomyHandler(RandomGift plugin) {
		this.plugin = plugin;
	}

	public boolean setupEconomy() {
		if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		
		RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
		
		if (rsp == null) {
			return false;
		}
		plugin.econ = rsp.getProvider();
		return plugin.econ != null;
	}
}
