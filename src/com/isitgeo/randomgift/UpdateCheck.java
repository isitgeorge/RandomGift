package com.isitgeo.randomgift;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class UpdateCheck {

	private RandomGift plugin;
	private Notifications notify;

	public UpdateCheck(RandomGift plugin, Notifications notify) {
		this.plugin = plugin;
		this.notify = notify;
	}
	
	URL updateCheck = null;
	URLConnection connection = null;
	BufferedReader reader = null;
	String response = null;

	void check() {

		if (plugin.versionCheck == true) {

			try {
				updateCheck = new URL("https://api.curseforge.com/servermods/files?projectids=67733");
			} catch (MalformedURLException e) {
				return;
			}

			try {
				connection = updateCheck.openConnection();
				connection.setReadTimeout(5000);
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				response = reader.readLine();
			} catch (IOException e) {
				plugin.getLogger().warning("There was a problem checking for updates! Are you connected to the internet?");
				return;
			}

			JSONArray array = (JSONArray) JSONValue.parse(response);
			JSONObject latest = (JSONObject) array.get(array.size() - 1);
			String version = (String) latest.get("fileName");

			int versionNum = Integer.parseInt(version.replaceAll("[^0-9]", ""));
			int currentVersion = Integer.parseInt(plugin.getDescription().getVersion().replaceAll("[^0-9]", ""));

			if (currentVersion < versionNum) {
				notify.consoleUpdateAvailable();
				plugin.updateAvailable = true;
			}
		}
	}
}
