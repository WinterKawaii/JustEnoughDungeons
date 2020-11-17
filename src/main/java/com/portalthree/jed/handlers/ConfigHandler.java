package com.portalthree.jed.handlers;

import java.io.File;


import com.portalthree.jed.commands.ToggleCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {
	public static Configuration config;
	private static String file = "config/Danker's Skyblock Mod.cfg";
	
	public static void init() {
		config = new Configuration(new File(file));
		try {
			config.load();
		} catch (Exception ex) {
			System.err.println(ex);
		} finally {
			config.save();
		}
	}
	
	public static int getInt(String category, String key) {
		config = new Configuration(new File(file));
		try {
			config.load();
			if (config.getCategory(category).containsKey(key)) {
				return config.get(category, key, 0).getInt();
			}
		} catch (Exception ex) {
			System.err.println(ex);
		} finally {
			config.save();
		}
		return 0;
	}
	
	public static double getDouble(String category, String key) {
		config = new Configuration(new File(file));
		try {
			config.load();
			if (config.getCategory(category).containsKey(key)) {
				return config.get(category, key, 0D).getDouble();
			}
		} catch (Exception ex) {
			System.err.println(ex);
		} finally {
			config.save();
		}
		return 0D;
	}
	
	public static String getString(String category, String key) {
		config = new Configuration(new File(file));
		try {
			config.load();
			if (config.getCategory(category).containsKey(key)) {
				return config.get(category, key, "").getString();
			}
		} catch (Exception ex) {
			System.err.println(ex);
		} finally {
			config.save();
		}
		return "";
	}
	
	public static boolean getBoolean(String category, String key) {
		config = new Configuration(new File(file));
		try {
			config.load();
			if (config.getCategory(category).containsKey(key)) {
				return config.get(category, key, false).getBoolean();
			}
		} catch (Exception ex) {
			System.err.println(ex);
		} finally {
			config.save();
		}
		return true;
	}
	
	public static void writeIntConfig(String category, String key, int value) {
		config = new Configuration(new File(file));
		try {
			config.load();
			int set = config.get(category, key, value).getInt();
			config.getCategory(category).get(key).set(value);
		} catch (Exception ex) {
			System.err.println(ex);
		} finally {
			config.save();
		}
	}
	
	public static void writeDoubleConfig(String category, String key, double value) {
		config = new Configuration(new File(file));
		try {
			config.load();
			double set = config.get(category, key, value).getDouble();
			config.getCategory(category).get(key).set(value);
		} catch (Exception ex) {
			System.err.println(ex);
		} finally {
			config.save();
		}
	}
	
	public static void writeStringConfig(String category, String key, String value) {
		config = new Configuration(new File(file));
		try {
			config.load();
			String set = config.get(category, key, value).getString();
			config.getCategory(category).get(key).set(value);
		} catch (Exception ex) {
			System.err.println(ex);
		} finally {
			config.save();
		}
	}
	
	public static void writeBooleanConfig(String category, String key, boolean value) {
		config = new Configuration(new File(file));
		try {
			config.load();
			boolean set = config.get(category, key, value).getBoolean();
			config.getCategory(category).get(key).set(value);
		} catch (Exception ex) {
			System.err.println(ex);
		} finally {
			config.save();
		}
	}
	
	public static boolean hasKey(String category, String key) {
		config = new Configuration(new File(file));
		try {
			config.load();
			if (!config.hasCategory(category)) return false;
			return config.getCategory(category).containsKey(key);
		} catch (Exception ex) {
			System.err.println(ex);
		} finally {
			config.save();
		}
		return false;
	}
	
	public static void deleteCategory(String category) {
		config = new Configuration(new File(file));
		try {
			config.load();
			if (config.hasCategory(category)) {
				config.removeCategory(new ConfigCategory(category));
			}
		} catch (Exception ex) {
			System.err.println(ex);
		} finally {
			config.save();
		}
	}
	
	public static void reloadConfig() {
		// Config init
		if (!hasKey("toggles", "GParty")) writeBooleanConfig("toggles", "GParty", false);
		if (!hasKey("toggles", "Coords")) writeBooleanConfig("toggles", "Coords", false);
		if (!hasKey("toggles", "Golden")) writeBooleanConfig("toggles", "Golden", false);
		if (!hasKey("toggles", "SlayerCount")) writeBooleanConfig("toggles", "SlayerCount", true);
		if (!hasKey("toggles", "RNGesusAlerts")) writeBooleanConfig("toggles", "RNGesusAlerts", true);
		if (!hasKey("toggles", "SplitFishing")) writeBooleanConfig("toggles", "SplitFishing", true);
		if (!hasKey("toggles", "ChatMaddox")) writeBooleanConfig("toggles", "ChatMaddox", true);
		if (!hasKey("toggles", "SpiritBearAlerts")) writeBooleanConfig("toggles", "SpiritBearAlerts", true);
		if (!hasKey("toggles", "AOTD")) writeBooleanConfig("toggles", "AOTD", false);
		if (!hasKey("toggles", "LividDagger")) writeBooleanConfig("toggles", "LividDagger", false);
		if (!hasKey("toggles", "SceptreMessages")) writeBooleanConfig("toggles", "SceptreMessages", true);
		if (!hasKey("toggles", "PetColors")) writeBooleanConfig("toggles", "PetColors", false);
		if (!hasKey("toggles", "DungeonTimer")) writeBooleanConfig("toggles", "DungeonTimer", false);
		if (!hasKey("toggles", "BlockSlayer")) writeStringConfig("toggles", "BlockSlayer", "");
		if (!hasKey("toggles", "GolemAlerts")) writeBooleanConfig("toggles", "GolemAlerts", false);
		if (!hasKey("toggles", "ExpertiseLore")) writeBooleanConfig("toggles", "ExpertiseLore", true);
		if (!hasKey("toggles", "Skill50Display")) writeBooleanConfig("toggles", "Skill50Display", false);
		if (!hasKey("toggles", "OutlineText")) writeBooleanConfig("toggles", "OutlineText", false);
		if (!hasKey("toggles", "MidasStaffMessages")) writeBooleanConfig("toggles", "MidasStaffMessages", true);
		if (!hasKey("toggles", "LividSolver")) writeBooleanConfig("toggles", "LividSolver", false);
		// Puzzle Solvers
		if (!hasKey("toggles", "ThreeManPuzzle")) writeBooleanConfig("toggles", "ThreeManPuzzle", false);
		if (!hasKey("toggles", "OruoPuzzle")) writeBooleanConfig("toggles", "OruoPuzzle", false);
		if (!hasKey("toggles", "BlazePuzzle")) writeBooleanConfig("toggles", "BlazePuzzle", false);
		if (!hasKey("toggles", "CreeperPuzzle")) writeBooleanConfig("toggles", "CreeperPuzzle", false);

		if (!hasKey("api", "APIKey")) writeStringConfig("api", "APIKey", "");

		// Wolf Loot
		if (!hasKey("wolf", "svens")) writeIntConfig("wolf", "svens", 0);
		if (!hasKey("wolf", "teeth")) writeIntConfig("wolf", "teeth", 0);
		if (!hasKey("wolf", "wheel")) writeIntConfig("wolf", "wheel", 0);
		if (!hasKey("wolf", "wheelDrops")) writeIntConfig("wolf", "wheelDrops", 0);
		if (!hasKey("wolf", "spirit")) writeIntConfig("wolf", "spirit", 0);
		if (!hasKey("wolf", "book")) writeIntConfig("wolf", "book", 0);
		if (!hasKey("wolf", "egg")) writeIntConfig("wolf", "egg", 0);
		if (!hasKey("wolf", "couture")) writeIntConfig("wolf", "couture", 0);
		if (!hasKey("wolf", "bait")) writeIntConfig("wolf", "bait", 0);
		if (!hasKey("wolf", "flux")) writeIntConfig("wolf", "flux", 0);
		if (!hasKey("wolf", "timeRNG")) writeDoubleConfig("wolf", "timeRNG", -1);
		if (!hasKey("wolf", "bossRNG")) writeIntConfig("wolf", "bossRNG", -1);
		// Spider Loot
		if (!hasKey("spider", "tarantulas")) writeIntConfig("spider", "tarantulas", 0);
		if (!hasKey("spider", "web")) writeIntConfig("spider", "web", 0);
		if (!hasKey("spider", "tap")) writeIntConfig("spider", "tap", 0);
		if (!hasKey("spider", "tapDrops")) writeIntConfig("spider", "tapDrops", 0);
		if (!hasKey("spider", "bite")) writeIntConfig("spider", "bite", 0);
		if (!hasKey("spider", "catalyst")) writeIntConfig("spider", "catalyst", 0);
		if (!hasKey("spider", "book")) writeIntConfig("spider", "book", 0);
		if (!hasKey("spider", "swatter")) writeIntConfig("spider", "swatter", 0);
		if (!hasKey("spider", "talisman")) writeIntConfig("spider", "talisman", 0);
		if (!hasKey("spider", "mosquito")) writeIntConfig("spider", "mosquito", 0);
		if (!hasKey("spider", "timeRNG")) writeDoubleConfig("spider", "timeRNG", -1);
		if (!hasKey("spider", "bossRNG")) writeIntConfig("spider", "bossRNG", -1);
		// Zombie Loot
		if (!hasKey("zombie", "revs")) writeIntConfig("zombie", "revs", 0);
		if (!hasKey("zombie", "revFlesh")) writeIntConfig("zombie", "revFlesh", 0);
		if (!hasKey("zombie", "foulFlesh")) writeIntConfig("zombie", "foulFlesh", 0);
		if (!hasKey("zombie", "foulFleshDrops")) writeIntConfig("zombie", "foulFleshDrops", 0);
		if (!hasKey("zombie", "pestilence")) writeIntConfig("zombie", "pestilence", 0);
		if (!hasKey("zombie", "undeadCatalyst")) writeIntConfig("zombie", "undeadCatalyst", 0);
		if (!hasKey("zombie", "book")) writeIntConfig("zombie", "book", 0);
		if (!hasKey("zombie", "beheaded")) writeIntConfig("zombie", "beheaded", 0);
		if (!hasKey("zombie", "revCatalyst")) writeIntConfig("zombie", "revCatalyst", 0);
		if (!hasKey("zombie", "snake")) writeIntConfig("zombie", "snake", 0);
		if (!hasKey("zombie", "scythe")) writeIntConfig("zombie", "scythe", 0);
		if (!hasKey("zombie", "timeRNG")) writeDoubleConfig("zombie", "timeRNG", -1);
		if (!hasKey("zombie", "bossRNG")) writeIntConfig("zombie", "bossRNG", -1);

		// Fishing
		if (!hasKey("fishing", "seaCreature")) writeIntConfig("fishing", "seaCreature", 0);
		if (!hasKey("fishing", "goodCatch")) writeIntConfig("fishing", "goodCatch", 0);
		if (!hasKey("fishing", "greatCatch")) writeIntConfig("fishing", "greatCatch", 0);
		if (!hasKey("fishing", "squid")) writeIntConfig("fishing", "squid", 0);
		if (!hasKey("fishing", "seaWalker")) writeIntConfig("fishing", "seaWalker", 0);
		if (!hasKey("fishing", "nightSquid")) writeIntConfig("fishing", "nightSquid", 0);
		if (!hasKey("fishing", "seaGuardian")) writeIntConfig("fishing", "seaGuardian", 0);
		if (!hasKey("fishing", "seaWitch")) writeIntConfig("fishing", "seaWitch", 0);
		if (!hasKey("fishing", "seaArcher")) writeIntConfig("fishing", "seaArcher", 0);
		if (!hasKey("fishing", "monsterOfDeep")) writeIntConfig("fishing", "monsterOfDeep", 0);
		if (!hasKey("fishing", "catfish")) writeIntConfig("fishing", "catfish", 0);
		if (!hasKey("fishing", "carrotKing")) writeIntConfig("fishing", "carrotKing", 0);
		if (!hasKey("fishing", "seaLeech")) writeIntConfig("fishing", "seaLeech", 0);
		if (!hasKey("fishing", "guardianDefender")) writeIntConfig("fishing", "guardianDefender", 0);
		if (!hasKey("fishing", "deepSeaProtector")) writeIntConfig("fishing", "deepSeaProtector", 0);
		if (!hasKey("fishing", "hydra")) writeIntConfig("fishing", "hydra", 0);
		if (!hasKey("fishing", "seaEmperor")) writeIntConfig("fishing", "seaEmperor", 0);
		if (!hasKey("fishing", "empTime")) writeDoubleConfig("fishing", "empTime", -1);
		if (!hasKey("fishing", "empSC")) writeIntConfig("fishing", "empSC", -1);
		if (!hasKey("fishing", "milestone")) writeIntConfig("fishing", "milestone", 0);
		// Fishing Winter
		if (!hasKey("fishing", "frozenSteve")) writeIntConfig("fishing", "frozenSteve", 0);
		if (!hasKey("fishing", "snowman")) writeIntConfig("fishing", "snowman", 0);
		if (!hasKey("fishing", "grinch")) writeIntConfig("fishing", "grinch", 0);
		if (!hasKey("fishing", "yeti")) writeIntConfig("fishing", "yeti", 0);
		if (!hasKey("fishing", "yetiTime")) writeDoubleConfig("fishing", "yetiTime", -1);
		if (!hasKey("fishing", "yetiSC")) writeIntConfig("fishing", "yetiSC", -1);
		// Fishing Festival
		if (!hasKey("fishing", "nurseShark")) writeIntConfig("fishing", "nurseShark", 0);
		if (!hasKey("fishing", "blueShark")) writeIntConfig("fishing", "blueShark", 0);
		if (!hasKey("fishing", "tigerShark")) writeIntConfig("fishing", "tigerShark", 0);
		if (!hasKey("fishing", "greatWhiteShark")) writeIntConfig("fishing", "greatWhiteShark", 0);
		// Spooky Fishing
		if (!hasKey("fishing", "scarecrow")) writeIntConfig("fishing", "scarecrow", 0);
		if (!hasKey("fishing", "nightmare")) writeIntConfig("fishing", "nightmare", 0);
		if (!hasKey("fishing", "werewolf")) writeIntConfig("fishing", "werewolf", 0);
		if (!hasKey("fishing", "phantomFisher")) writeIntConfig("fishing", "phantomFisher", 0);
		if (!hasKey("fishing", "grimReaper")) writeIntConfig("fishing", "grimReaper", 0);

		// Dungeons
		if (!hasKey("catacombs", "recombobulator")) writeIntConfig("catacombs", "recombobulator", 0);
		if (!hasKey("catacombs", "fumingBooks")) writeIntConfig("catacombs", "fumingBooks", 0);
		// F1
		if (!hasKey("catacombs", "bonzoStaff")) writeIntConfig("catacombs", "bonzoStaff", 0);
		if (!hasKey("catacombs", "floorOneCoins")) writeDoubleConfig("catacombs", "floorOneCoins", 0);
		if (!hasKey("catacombs", "floorOneTime")) writeDoubleConfig("catacombs", "floorOneTime", 0);
		// F2
		if (!hasKey("catacombs", "scarfStudies")) writeIntConfig("catacombs", "scarfStudies", 0);
		if (!hasKey("catacombs", "floorTwoCoins")) writeDoubleConfig("catacombs", "floorTwoCoins", 0);
		if (!hasKey("catacombs", "floorTwoTime")) writeDoubleConfig("catacombs", "floorTwoTime", 0);
		// F3
		if (!hasKey("catacombs", "adaptiveHelm")) writeIntConfig("catacombs", "adaptiveHelm", 0);
		if (!hasKey("catacombs", "adaptiveChest")) writeIntConfig("catacombs", "adaptiveChest", 0);
		if (!hasKey("catacombs", "adaptiveLegging")) writeIntConfig("catacombs", "adaptiveLegging", 0);
		if (!hasKey("catacombs", "adaptiveBoot")) writeIntConfig("catacombs", "adaptiveBoot", 0);
		if (!hasKey("catacombs", "adaptiveSword")) writeIntConfig("catacombs", "adaptiveSword", 0);
		if (!hasKey("catacombs", "floorThreeCoins")) writeDoubleConfig("catacombs", "floorThreeCoins", 0);
		if (!hasKey("catacombs", "floorThreeTime")) writeDoubleConfig("catacombs", "floorThreeTime", 0);
		// F4
		if (!hasKey("catacombs", "spiritWing")) writeIntConfig("catacombs", "spiritWing", 0);
		if (!hasKey("catacombs", "spiritBone")) writeIntConfig("catacombs", "spiritBone", 0);
		if (!hasKey("catacombs", "spiritBoot")) writeIntConfig("catacombs", "spiritBoot", 0);
		if (!hasKey("catacombs", "spiritSword")) writeIntConfig("catacombs", "spiritSword", 0);
		if (!hasKey("catacombs", "spiritBow")) writeIntConfig("catacombs", "spiritBow", 0);
		if (!hasKey("catacombs", "spiritPetEpic")) writeIntConfig("catacombs", "spiritPetEpic", 0);
		if (!hasKey("catacombs", "spiritPetLeg")) writeIntConfig("catacombs", "spiritPetLeg", 0);
		if (!hasKey("catacombs", "floorFourCoins")) writeDoubleConfig("catacombs", "floorFourCoins", 0);
		if (!hasKey("catacombs", "floorFourTime")) writeDoubleConfig("catacombs", "floorFourTime", 0);
		// F5
		if (!hasKey("catacombs", "warpedStone")) writeIntConfig("catacombs", "warpedStone", 0);
		if (!hasKey("catacombs", "shadowAssassinHelm")) writeIntConfig("catacombs", "shadowAssassinHelm", 0);
		if (!hasKey("catacombs", "shadowAssassinChest")) writeIntConfig("catacombs", "shadowAssassinChest", 0);
		if (!hasKey("catacombs", "shadowAssassinLegging")) writeIntConfig("catacombs", "shadowAssassinLegging", 0);
		if (!hasKey("catacombs", "shadowAssassinBoot")) writeIntConfig("catacombs", "shadowAssassinBoot", 0);
		if (!hasKey("catacombs", "lividDagger")) writeIntConfig("catacombs", "lividDagger", 0);
		if (!hasKey("catacombs", "shadowFury")) writeIntConfig("catacombs", "shadowFury", 0);
		if (!hasKey("catacombs", "floorFiveCoins")) writeDoubleConfig("catacombs", "floorFiveCoins", 0);
		if (!hasKey("catacombs", "floorFiveTime")) writeDoubleConfig("catacombs", "floorFiveTime", 0);
		// F6
		if (!hasKey("catacombs", "ancientRose")) writeIntConfig("catacombs", "ancientRose", 0);
		if (!hasKey("catacombs", "precursorEye")) writeIntConfig("catacombs", "precursorEye", 0);
		if (!hasKey("catacombs", "giantsSword")) writeIntConfig("catacombs", "giantsSword", 0);
		if (!hasKey("catacombs", "necroLordHelm")) writeIntConfig("catacombs", "necroHelm", 0);
		if (!hasKey("catacombs", "necroLordChest")) writeIntConfig("catacombs", "necroChest", 0);
		if (!hasKey("catacombs", "necroLordLegging")) writeIntConfig("catacombs", "necroLegging", 0);
		if (!hasKey("catacombs", "necroLordBoot")) writeIntConfig("catacombs", "necroBoot", 0);
		if (!hasKey("catacombs", "necroSword")) writeIntConfig("catacombs", "necroSword", 0);
		if (!hasKey("catacombs", "floorSixCoins")) writeDoubleConfig("catacombs", "floorSixCoins", 0);
		if (!hasKey("catacombs", "floorSixTime")) writeDoubleConfig("catacombs", "floorSixTime", 0);

		if (!hasKey("misc", "display")) writeStringConfig("misc", "display", "off");
		if (!hasKey("misc", "autoDisplay")) writeBooleanConfig("misc", "autoDisplay", false);
		if (!hasKey("misc", "skill50Time")) writeIntConfig("misc", "skill50Time", 3);

		ScaledResolution scaled = new ScaledResolution(Minecraft.getMinecraft());
		int height = scaled.getScaledHeight();
		if (!hasKey("locations", "coordsX")) writeIntConfig("locations", "coordsX", 5);
		if (!hasKey("locations", "coordsY")) writeIntConfig("locations", "coordsY", height - 25);
		if (!hasKey("locations", "displayX")) writeIntConfig("locations", "displayX", 80);
		if (!hasKey("locations", "displayY")) writeIntConfig("locations", "displayY", 5);
		if (!hasKey("locations", "dungeonTimerX")) writeIntConfig("locations", "dungeonTimerX", 5);
		if (!hasKey("locations", "dungeonTimerY")) writeIntConfig("locations", "dungeonTimerY", 5);
		if (!hasKey("locations", "skill50X")) writeIntConfig("locations", "skill50X", 40);
		if (!hasKey("locations", "skill50Y")) writeIntConfig("locations", "skill50Y", 10);
		if (!hasKey("locations", "lividHpX")) writeIntConfig("locations", "lividHpX", 40);
		if (!hasKey("locations", "lividHpY")) writeIntConfig("locations", "lividHpY", 20);
		if (!hasKey("scales", "coordsScale")) writeDoubleConfig("scales", "coordsScale", 1);
		if (!hasKey("scales", "displayScale")) writeDoubleConfig("scales", "displayScale", 1);
		if (!hasKey("scales", "dungeonTimerScale")) writeDoubleConfig("scales", "dungeonTimerScale", 1);
		if (!hasKey("scales", "skill50Scale")) writeDoubleConfig("scales", "skill50Scale", 1);
		if (!hasKey("scales", "lividHpScale")) writeDoubleConfig("scales", "lividHpScale", 1);

		final ToggleCommand tf = new ToggleCommand();
		tf.gpartyToggled = getBoolean("toggles", "GParty");
		tf.coordsToggled = getBoolean("toggles", "Coords");
		tf.goldenToggled = getBoolean("toggles", "Golden");
		tf.slayerCountTotal = getBoolean("toggles", "SlayerCount");
		tf.rngesusAlerts = getBoolean("toggles", "RNGesusAlerts");
		tf.splitFishing = getBoolean("toggles", "SplitFishing");
		tf.chatMaddoxToggled = getBoolean("toggles", "ChatMaddox");
		tf.spiritBearAlerts = getBoolean("toggles", "SpiritBearAlerts");
		tf.aotdToggled = getBoolean("toggles", "AOTD");
		tf.lividDaggerToggled = getBoolean("toggles", "LividDagger");
		tf.sceptreMessages = getBoolean("toggles", "SceptreMessages");
		tf.petColoursToggled = getBoolean("toggles", "PetColors");
		tf.dungeonTimerToggled = getBoolean("toggles", "DungeonTimer");
		tf.golemAlertToggled = getBoolean("toggles", "GolemAlerts");
		tf.expertiseLoreToggled = getBoolean("toggles", "ExpertiseLore");
		tf.skill50DisplayToggled = getBoolean("toggles", "Skill50Display");
		tf.outlineTextToggled = getBoolean("toggles", "OutlineText");
		tf.midasStaffMessages = getBoolean("toggles", "MidasStaffMessages");
		tf.lividSolverToggled = getBoolean("toggles", "LividSolver");
		// Puzzle Solvers
		tf.threeManToggled = getBoolean("toggles", "ThreeManPuzzle");
		tf.oruoToggled = getBoolean("toggles", "OruoPuzzle");
		tf.blazeToggled = getBoolean("toggles", "BlazePuzzle");
		tf.creeperToggled = getBoolean("toggles", "CreeperPuzzle");


	}
	
}
