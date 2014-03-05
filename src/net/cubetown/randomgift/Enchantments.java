package net.cubetown.randomgift;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.enchantments.Enchantment;

public class Enchantments {
    public static Enchantment getEnchantment(String enchString) {

        enchString = enchString.toLowerCase().replaceAll("[ _-]", "");
 
        Map <String, String> aliases = new HashMap<>();
	
	aliases.put("damageall", "damageall");
	aliases.put("sharpness", "damageall");
	aliases.put("alldamage", "damageall");
	aliases.put("alldmg", "damageall");
	aliases.put("sharp", "damageall");
	
	aliases.put("damagearthropods", "damagearthropods");
	aliases.put("ardmg", "damagearthropods");
	aliases.put("baneofarthropods", "damagearthropods");
	aliases.put("baneofarthropod", "damagearthropods");
	aliases.put("artropod", "damagearthropods");
	
	aliases.put("damageundead", "damageundead");
	aliases.put("undeaddamage", "damageundead");
	aliases.put("smite", "damageundead");
	aliases.put("undead", "damageundead");
	
	aliases.put("digspeed", "digspeed");
	aliases.put("efficiency", "digspeed");
	aliases.put("minespeed", "digspeed");
	aliases.put("cutspeed", "digspeed");
	aliases.put("eff", "digspeed");
	
	aliases.put("durability", "durability");
	aliases.put("unbreaking", "durability");
	
	aliases.put("thorns", "thorns");
	aliases.put("highcrit", "thorns");
	aliases.put("thorn", "thorns");
	aliases.put("highercrit", "thorns");

	aliases.put("fireaspect", "fireaspect");
	aliases.put("fire", "fireaspect");
	aliases.put("meleefire", "fireaspect");
	aliases.put("flame", "fireaspect");
	aliases.put("meleeflame", "fireaspect");
	
	aliases.put("knockback", "knockback");
	aliases.put("kb", "knockback");
	
	aliases.put("lootbonusblocks", "lootbonusblocks");
	aliases.put("blockslootbonus", "lootbonusblocks");
	aliases.put("fortune", "lootbonusblocks");
	aliases.put("lbb", "lootbonusblocks");
	
	aliases.put("lootbonusmobs", "lootbonusmobs");
	aliases.put("mobslootbonus", "lootbonusmobs");
	aliases.put("mobloot", "lootbonusmobs");
	aliases.put("looting", "lootbonusmobs");
	aliases.put("lbm", "lootbonusmobs");
	
	aliases.put("oxygen", "oxygen");
	aliases.put("respiration", "oxygen");
	aliases.put("breathing", "oxygen");
	aliases.put("breath", "oxygen");
	
	aliases.put("protectionenvironmental", "protectionenvironmental");
	aliases.put("protection", "protectionenvironmental");
	aliases.put("prot", "protectionenvironmental");
	aliases.put("protect", "protectionenvironmental");
	
	aliases.put("protectionexplosions", "protectionexplosions");
	aliases.put("explosionprotection", "protectionexplosions");
	aliases.put("explosionsprotection", "protectionexplosions");
	aliases.put("blastprotection", "protectionexplosions");
	aliases.put("blastprotect", "protectionexplosions");
	aliases.put("explosionprotect", "protectionexplosions");
	
	aliases.put("protectionfall", "protectionfall");
	aliases.put("fallprotection", "protectionfall");
	aliases.put("featherfall", "protectionfall");
	aliases.put("featherfalling", "protectionfall");
	
	aliases.put("protectionfire", "protectionfire");
	aliases.put("fireprotection", "protectionfire");
	aliases.put("fireprotect", "protectionfire");
	aliases.put("flameprotect", "protectionfire");
	aliases.put("flameprotection", "protectionfire");
	
	aliases.put("protectionprojectile", "protectionprojectile");
	aliases.put("projectileprotection", "protectionprojectile");
	aliases.put("projprotect", "protectionprojectile");
	
	aliases.put("silktouch", "silktouch");
	aliases.put("softtouch", "silktouch");
	
	aliases.put("waterworker", "waterworker");
	aliases.put("aquaaffinity", "waterworker");
	aliases.put("watermine", "waterworker");
	
	aliases.put("arrowfire", "arrowfire");
	aliases.put("firearrow", "arrowfire");
	aliases.put("arrowfire", "arrowfire");
	aliases.put("fire", "arrowfire");
	aliases.put("flame", "arrowfire");
	aliases.put("flamearrow", "arrowfire");
	
	aliases.put("arrowdamage", "arrowdamage");
	aliases.put("power", "arrowdamage");
	aliases.put("arrowpower", "arrowdamage");
	
	aliases.put("arrowknockback", "arrowknockback");
	aliases.put("arrowkb", "arrowknockback");
	aliases.put("punch", "arrowknockback");
	aliases.put("arrowpunch", "arrowknockback");
	
	aliases.put("arrowinfinite", "arrowinfinite");
	aliases.put("infinitearrow", "arrowinfinite");
	aliases.put("infinity", "arrowinfinite");
	aliases.put("unlimited", "arrowinfinite");
	aliases.put("unlimitedarrow", "arrowinfinite");
	
	aliases.put("luck", "luck");
	aliases.put("luckofsea", "luck");
	aliases.put("luckofseas", "luck");
	aliases.put("rodluck", "luck");
	
	aliases.put("lure", "lure");
	aliases.put("rodlure", "lure");
	 
        String alias = aliases.get(enchString);
        if (alias != null)
            enchString = alias;
 
        for (Enchantment value : Enchantment.values()) {
            if (enchString.equalsIgnoreCase(value.getName().replaceAll("[ _-]", ""))) {
                return value;
            }
        }
       
        return null;
    }
}
