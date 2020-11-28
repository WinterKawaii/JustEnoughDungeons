package com.portalthree.jed.handlers;

import com.portalthree.jed.commands.ToggleCommand;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigHandler {
	public static Configuration config;
	private static final String file = "config/JustEnoughDungeons.cfg";
	
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
		// Puzzle Solvers
		if (!hasKey("toggles", "ThreeManPuzzle")) writeBooleanConfig("toggles", "ThreeManPuzzle", false);
		if (!hasKey("toggles", "OruoPuzzle")) writeBooleanConfig("toggles", "OruoPuzzle", false);
		if (!hasKey("toggles", "BlazePuzzle")) writeBooleanConfig("toggles", "BlazePuzzle", false);
		if (!hasKey("toggles", "CreeperPuzzle")) writeBooleanConfig("toggles", "CreeperPuzzle", false);
		if (!hasKey("toggles", "joinInformation")) writeBooleanConfig("toggles", "joinInformation", false);
		if (!hasKey("toggles", "mobClear")) writeBooleanConfig("toggles", "mobClear", false);
		if (!hasKey("toggles", "necronGlow")) writeBooleanConfig("toggles", "necronGlow", false);
		if (!hasKey("toggles", "amongUsSolver")) writeBooleanConfig("toggles", "amongUsSolver", false);

		//API
		if (!hasKey("api", "APIKey")) writeStringConfig("api", "APIKey", "");

		// Puzzle Solvers
		ToggleCommand.threeManToggled = getBoolean("toggles", "ThreeManPuzzle");
		ToggleCommand.oruoToggled = getBoolean("toggles", "OruoPuzzle");
		ToggleCommand.blazeToggled = getBoolean("toggles", "BlazePuzzle");
		ToggleCommand.creeperToggled = getBoolean("toggles", "CreeperPuzzle");
		ToggleCommand.joinInformationToggled = getBoolean("toggles", "joinInformation");
		ToggleCommand.mobClearToggled = getBoolean("toggles", "mobClear");
		ToggleCommand.necronGlowToggled = getBoolean("toggles", "necronGlow");
		ToggleCommand.amongUsSolverToggled = getBoolean("toggles", "amongUsSolver");

	}
	
}
