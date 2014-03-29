package com.isitgeo.randomgift;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.enchantments.Enchantment;

public class Enchantments {

	public static Enchantment getEnchantment(String enchString) {

		enchString = enchString.toLowerCase().replaceAll("[ _-]", "");
		Map<String, String> aliases = new HashMap<>();

		aliases.put("damageall", "damageall");
		aliases.put("sharpness", "damageall");

		aliases.put("damagearthropods", "damagearthropods");
		aliases.put("baneofarthropods", "damagearthropods");
		aliases.put("artropod", "damagearthropods");

		aliases.put("damageundead", "damageundead");
		aliases.put("smite", "damageundead");

		aliases.put("digspeed", "digspeed");
		aliases.put("efficiency", "digspeed");

		aliases.put("durability", "durability");
		aliases.put("unbreaking", "durability");

		aliases.put("thorns", "thorns");

		aliases.put("fireaspect", "fireaspect");
		aliases.put("fire", "fireaspect");

		aliases.put("knockback", "knockback");

		aliases.put("lootbonusblocks", "lootbonusblocks");
		aliases.put("fortune", "lootbonusblocks");

		aliases.put("lootbonusmobs", "lootbonusmobs");
		aliases.put("mobslootbonus", "lootbonusmobs");
		aliases.put("mobloot", "lootbonusmobs");

		aliases.put("oxygen", "oxygen");
		aliases.put("respiration", "oxygen");
		aliases.put("breathing", "oxygen");

		aliases.put("protectionenvironmental", "protectionenvironmental");
		aliases.put("protection", "protectionenvironmental");
		aliases.put("protect", "protectionenvironmental");

		aliases.put("protectionexplosions", "protectionexplosions");
		aliases.put("blastprotection", "protectionexplosions");

		aliases.put("protectionfall", "protectionfall");
		aliases.put("fallprotection", "protectionfall");

		aliases.put("protectionfire", "protectionfire");
		aliases.put("fireprotection", "protectionfire");

		aliases.put("protectionprojectile", "protectionprojectile");
		aliases.put("projectileprotection", "protectionprojectile");

		aliases.put("silktouch", "silktouch");

		aliases.put("waterworker", "waterworker");
		aliases.put("watermine", "waterworker");

		aliases.put("arrowfire", "arrowfire");
		aliases.put("firearrow", "arrowfire");
		aliases.put("arrowfire", "arrowfire");
		aliases.put("flamearrow", "arrowfire");

		aliases.put("arrowdamage", "arrowdamage");
		aliases.put("arrowpower", "arrowdamage");

		aliases.put("arrowknockback", "arrowknockback");
		aliases.put("arrowkb", "arrowknockback");
		aliases.put("punch", "arrowknockback");
		aliases.put("arrowpunch", "arrowknockback");

		aliases.put("arrowinfinite", "arrowinfinite");
		aliases.put("infinitearrow", "arrowinfinite");
		aliases.put("infinity", "arrowinfinite");

		aliases.put("luck", "luck");

		aliases.put("lure", "lure");

		String alias = aliases.get(enchString);

		if (alias != null)
			enchString = alias;

		for (Enchantment value : Enchantment.values()) {
			if (enchString.equalsIgnoreCase(value.getName().replaceAll("[ _-]",	""))) {
				return value;
			}
		}

		return null;
	}
}
