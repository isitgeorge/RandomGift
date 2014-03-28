package com.isitgeo.randomgift;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Statistics {
	
	private RandomGift plugin;

	public Statistics(RandomGift plugin) {
		this.plugin = plugin;
	}

	public void sendStats() {
		if (plugin.collectStats == true) {
			URL updateSend = null;
			try {
				updateSend = new URL("http://plugin-stats.isitgeo.com");
			} catch (MalformedURLException e) {
			
			}
		
			URLConnection updateSender = null;
			try {
				updateSender = updateSend.openConnection();
				updateSender.setRequestProperty("plugin-name", "RandomGift");
				updateSender.setRequestProperty("plugin-version", plugin.getDescription().getVersion().toString());
				updateSender.setRequestProperty("plugin-conf-vers", plugin.getConfig().getString("config-version"));
				updateSender.setReadTimeout(5000);
				updateSender.getInputStream();
			}  catch (IOException e) {
				// Failed to contact statistic server
			}
		
			try {
				MetricsLite metrics = new MetricsLite(plugin);
				metrics.start();
			} catch (IOException e) {
				// Failed to submit the stats :-(
			}
		}	
	}
}
