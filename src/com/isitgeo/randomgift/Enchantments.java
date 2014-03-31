package com.isitgeo.randomgift;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.enchantments.Enchantment;

public class Enchantments {

	public static Enchantment getEnchantment(String enchString) {

		enchString = enchString.toLowerCase().replaceAll("[ _-]", "");
		Map<String, String> aliases = new HashMap<>();
		
		// PROTECTION_ENVIRONMENTAL
		aliases.put("protectionenvironmental", "protectionenvironmental");
		aliases.put("protection", "protectionenvironmental");
		
		// PROTECTION_FIRE
		aliases.put("protectionfire", "protectionfire");
		aliases.put("fireprotection", "protectionfire");
		
		// PROTECTION_FALL
		aliases.put("protectionfall", "protectionfall");
		aliases.put("fallprotection", "protectionfall");
		aliases.put("featherfalling", "protectionfall");
		
		// PROTECTION_EXPLOSIONS
		aliases.put("protectionexplosions", "protectionexplosions");
		aliases.put("blastprotection", "protectionexplosions");
		
		// PROTECTION_PROJECTILE
		aliases.put("protectionprojectile", "protectionprojectile");
		aliases.put("projectileprotection", "protectionprojectile");
		
		// OXYGEN
		aliases.put("oxygen", "oxygen");
		aliases.put("respiration", "oxygen");
		aliases.put("breathing", "oxygen");
		
		// WATER_WORKER
		aliases.put("waterworker", "waterworker");
		aliases.put("watermine", "waterworker");
		aliases.put("aquaaffinity", "waterworker");
		
		//THORNS
		aliases.put("thorns", "thorns");
		
		// DAMAGE_ALL
		aliases.put("damageall", "damageall");
		aliases.put("sharpness", "damageall");
		
		// DAMAGE_UNDEAD
		aliases.put("damageundead", "damageundead");
		aliases.put("smite", "damageundead");
		
		// DAMAGE_ARTHROPODS
		aliases.put("damagearthropods", "damagearthropods");
		aliases.put("baneofarthropods", "damagearthropods");
		aliases.put("arthropod", "damagearthropods");

		// KNOCKBACK
		aliases.put("knockback", "knockback");
		
		// FIRE_ASPECT
		aliases.put("fireaspect", "fireaspect");
		aliases.put("fire", "fireaspect");
		
		// LOOT_BONUS_MOBS
		aliases.put("lootbonusmobs", "lootbonusmobs");
		aliases.put("mobslootbonus", "lootbonusmobs");
		aliases.put("mobloot", "lootbonusmobs");
		aliases.put("looting", "loobbonusmobs");
		
		// DIG_SPEED
		aliases.put("digspeed", "digspeed");
		aliases.put("efficiency", "digspeed");
		
		// SILK_TOUCH
		aliases.put("silktouch", "silktouch");
		
		// DURABILITY
		aliases.put("durability", "durability");
		aliases.put("unbreaking", "durability");
		
		// LOOT_BONUS_BLOCKS
		aliases.put("lootbonusblocks", "lootbonusblocks");
		aliases.put("fortune", "lootbonusblocks");
		
		// ARROW_DAMAGE
		aliases.put("arrowdamage", "arrowdamage");
		aliases.put("arrowpower", "arrowdamage");
		aliases.put("power", "arrowdamage");
		
		// ARROW_KNOCKBACK
		aliases.put("arrowknockback", "arrowknockback");
		aliases.put("punch", "arrowknockback");
		aliases.put("arrowpunch", "arrowknockback");
		
		// ARROW_FIRE
		aliases.put("arrowfire", "arrowfire");
		aliases.put("firearrow", "arrowfire");
		aliases.put("flame", "arrowfire");
		
		// ARROW_INFINITE
		aliases.put("arrowinfinite", "arrowinfinite");
		aliases.put("infinitearrow", "arrowinfinite");
		aliases.put("infinity", "arrowinfinite");
		
		// LUCK
		aliases.put("luck", "luck");
		
		// LURE
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
