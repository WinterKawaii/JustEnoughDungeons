package com.portalthree.jed.commands;

import com.portalthree.jed.handlers.ConfigHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public class ToggleCommand extends CommandBase implements ICommand {
	public static boolean gpartyToggled;
	public static boolean coordsToggled;
	public static boolean goldenToggled;
	public static boolean slayerCountTotal;
	public static boolean rngesusAlerts;
	public static boolean splitFishing;
	public static boolean chatMaddoxToggled;
	public static boolean spiritBearAlerts;
	public static boolean aotdToggled;
	public static boolean lividDaggerToggled;
	public static boolean sceptreMessages;
	public static boolean petColoursToggled;
	public static boolean dungeonTimerToggled;
	public static boolean golemAlertToggled;
	public static boolean expertiseLoreToggled;
	public static boolean skill50DisplayToggled;
	public static boolean outlineTextToggled;
	public static boolean midasStaffMessages;
	public static boolean lividSolverToggled;
	// Puzzle Solvers
	public static boolean threeManToggled;
	public static boolean oruoToggled;
	public static boolean blazeToggled;
	public static boolean creeperToggled;
	
	@Override
	public String getCommandName() {
		return "aaaaaaaaaaaaaaaaaa";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return "/" + getCommandName() + " ";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}
	
	@Override
	public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
		if (args.length == 1) {
			return getListOfStringsMatchingLastWord(args, "gparty", "coords", "golden", "slayercount", "rngesusalerts",
														  "splitfishing", "chatmaddox", "spiritbearalerts", "aotd", "lividdagger",
														  "sceptremessages", "petcolors", "dungeontimer", "golemalerts",
														  "expertiselore", "skill50display", "outlinetext", "midasstaffmessages",
														  "lividsolver",  "threemanpuzzle", "oruopuzzle", "blazepuzzle", "creeperpuzzle", 
														  "list");
		}
		return null;
	}
	
	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
		final EntityPlayer player = (EntityPlayer)arg0;
		final ConfigHandler cf = new ConfigHandler();
		
		if (arg1.length == 0) {
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Usage: " + getCommandUsage(arg0)));
			return;
		}
		
		if (arg1[0].equalsIgnoreCase("gparty")) {
			gpartyToggled = !gpartyToggled;
			cf.writeBooleanConfig("toggles", "GParty", gpartyToggled);
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Guild party notifications has been set to " + EnumChatFormatting.DARK_GREEN + gpartyToggled + EnumChatFormatting.GREEN + "."));
		} else if (arg1[0].equalsIgnoreCase("coords")) {
			coordsToggled = !coordsToggled;
			cf.writeBooleanConfig("toggles", "Coords", coordsToggled);
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Coord/Angle display has been set to " + EnumChatFormatting.DARK_GREEN + coordsToggled + EnumChatFormatting.GREEN + "."));
		} else if (arg1[0].equalsIgnoreCase("golden")) { 
			goldenToggled = !goldenToggled;
			cf.writeBooleanConfig("toggles", "Golden", goldenToggled);
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Golden T6 enchants has been set to " + EnumChatFormatting.DARK_GREEN + goldenToggled + EnumChatFormatting.GREEN + "."));
		} else if (arg1[0].equalsIgnoreCase("slayercount")) {
			slayerCountTotal = !slayerCountTotal;
			cf.writeBooleanConfig("toggles", "SlayerCount", slayerCountTotal);
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Counting total 20% slayer drops has been set to " + EnumChatFormatting.DARK_GREEN + slayerCountTotal + EnumChatFormatting.GREEN + "."));
		} else if (arg1[0].equalsIgnoreCase("rngesusalerts")) {
			rngesusAlerts = !rngesusAlerts;
			cf.writeBooleanConfig("toggles", "RNGesusAlerts", rngesusAlerts);
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Slayer RNGesus alerts has been set to " + EnumChatFormatting.DARK_GREEN + rngesusAlerts + EnumChatFormatting.GREEN + "."));
		} else if (arg1[0].equalsIgnoreCase("splitfishing")) {
			splitFishing = !splitFishing;
			cf.writeBooleanConfig("toggles", "SplitFishing", splitFishing);
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Split fishing display has been set to " + EnumChatFormatting.DARK_GREEN + splitFishing + EnumChatFormatting.GREEN + "."));
		} else if (arg1[0].equalsIgnoreCase("chatmaddox")) {
			chatMaddoxToggled = !chatMaddoxToggled;
			cf.writeBooleanConfig("toggles", "ChatMaddox", chatMaddoxToggled);
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Chat Maddox menu has been set to " + EnumChatFormatting.DARK_GREEN + chatMaddoxToggled + EnumChatFormatting.GREEN + "."));
		} else if (arg1[0].equalsIgnoreCase("spiritbearalerts")) { 
			spiritBearAlerts = !spiritBearAlerts;
			cf.writeBooleanConfig("toggles", "SpiritBearAlerts", spiritBearAlerts);
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Spirit Bear alerts have been set to " + EnumChatFormatting.DARK_GREEN + spiritBearAlerts + EnumChatFormatting.GREEN + "."));
		} else if (arg1[0].equalsIgnoreCase("aotd")) {
			aotdToggled = !aotdToggled;
			cf.writeBooleanConfig("toggles", "AOTD", aotdToggled);
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Block AOTD ability been set to " + EnumChatFormatting.DARK_GREEN + aotdToggled + EnumChatFormatting.GREEN + "."));
		} else if (arg1[0].equalsIgnoreCase("lividdagger")) {
			lividDaggerToggled = !lividDaggerToggled;
			cf.writeBooleanConfig("toggles", "LividDagger", lividDaggerToggled);
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Block Livid Dagger ability been set to " + EnumChatFormatting.DARK_GREEN + lividDaggerToggled + EnumChatFormatting.GREEN + "."));
		} else if (arg1[0].equalsIgnoreCase("sceptremessages")) {
			sceptreMessages = !sceptreMessages;
			cf.writeBooleanConfig("toggles", "SceptreMessages", sceptreMessages);
		} else if (arg1[0].equalsIgnoreCase("skill50display")) {
			skill50DisplayToggled = !skill50DisplayToggled;
			cf.writeBooleanConfig("toggles", "Skill50Display", skill50DisplayToggled);
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Skill 50 display has been set to " + EnumChatFormatting.DARK_GREEN + skill50DisplayToggled + EnumChatFormatting.GREEN + "."));
		} else if (arg1[0].equalsIgnoreCase("outlinetext")) {
			outlineTextToggled = !outlineTextToggled;
			cf.writeBooleanConfig("toggles", "OutlineText", outlineTextToggled);
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Outline displayed text has been set to " + EnumChatFormatting.DARK_GREEN + outlineTextToggled + EnumChatFormatting.GREEN + "."));
		} else if (arg1[0].equalsIgnoreCase("midasstaffmessages")) {
			midasStaffMessages = !midasStaffMessages;
			cf.writeBooleanConfig("toggles", "MidasStaffMessages", midasStaffMessages);
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Midas Staff messages have been set to " + EnumChatFormatting.DARK_GREEN + midasStaffMessages + EnumChatFormatting.GREEN + "."));
		} else if (arg1[0].equalsIgnoreCase("lividsolver")) { 
			lividSolverToggled = !lividSolverToggled;
			cf.writeBooleanConfig("toggles", "LividSolver", lividSolverToggled);
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Livid solver has been set to " + EnumChatFormatting.DARK_GREEN + lividSolverToggled + EnumChatFormatting.GREEN + "."));
		} else if (arg1[0].equalsIgnoreCase("threemanpuzzle")) { 
			threeManToggled = !threeManToggled;
			cf.writeBooleanConfig("toggles", "ThreeManPuzzle", threeManToggled);
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Three man puzzle solver has been set to " + EnumChatFormatting.DARK_GREEN + threeManToggled + EnumChatFormatting.GREEN + "."));
		} else if (arg1[0].equalsIgnoreCase("oruopuzzle")) { 
			oruoToggled = !oruoToggled;
			cf.writeBooleanConfig("toggles", "OruoPuzzle", oruoToggled);
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Oruo trivia solver has been set to " + EnumChatFormatting.DARK_GREEN + oruoToggled + EnumChatFormatting.GREEN + "."));
		} else if (arg1[0].equalsIgnoreCase("blazepuzzle")) { 
			blazeToggled = !blazeToggled;
			cf.writeBooleanConfig("toggles", "BlazePuzzle", blazeToggled);
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Blaze puzzle solver has been set to " + EnumChatFormatting.DARK_GREEN + blazeToggled + EnumChatFormatting.GREEN + "."));
		} else if (arg1[0].equalsIgnoreCase("creeperpuzzle")) {
			creeperToggled = !creeperToggled;
			cf.writeBooleanConfig("creeperpuzzle", "CreeperPuzzle", creeperToggled);
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Creeper puzzle solver has been set to " + EnumChatFormatting.DARK_GREEN + creeperToggled + EnumChatFormatting.GREEN + "."));
		} else if (arg1[0].equalsIgnoreCase("list")) {
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Guild party notifications: " + EnumChatFormatting.DARK_GREEN + gpartyToggled + "\n" +
														EnumChatFormatting.GREEN + " Coord/Angle display: " + EnumChatFormatting.DARK_GREEN + coordsToggled + "\n" +
														EnumChatFormatting.GREEN + " Golden T6 enchants: " + EnumChatFormatting.DARK_GREEN + goldenToggled + "\n" +
														EnumChatFormatting.GREEN + " Counting total 20% slayer drops: " + EnumChatFormatting.DARK_GREEN + slayerCountTotal + "\n" +
														EnumChatFormatting.GREEN + " Slayer RNGesus alerts: " + EnumChatFormatting.DARK_GREEN + rngesusAlerts + "\n" +
														EnumChatFormatting.GREEN + " Split fishing display: " + EnumChatFormatting.DARK_GREEN + splitFishing + "\n" +
														EnumChatFormatting.GREEN + " Chat Maddox menu: " + EnumChatFormatting.DARK_GREEN + chatMaddoxToggled + "\n" +
														EnumChatFormatting.GREEN + " Spirit Bear alerts: " + EnumChatFormatting.DARK_GREEN + spiritBearAlerts + "\n" +
														EnumChatFormatting.GREEN + " Block AOTD ability: " + EnumChatFormatting.DARK_GREEN + aotdToggled + "\n" +
														EnumChatFormatting.GREEN + " Block Livid Dagger ability: " + EnumChatFormatting.DARK_GREEN + lividDaggerToggled + "\n" +
														EnumChatFormatting.GREEN + " Spirit Sceptre messages: " + EnumChatFormatting.DARK_GREEN + sceptreMessages + "\n" +
														EnumChatFormatting.GREEN + " Pet colours: " + EnumChatFormatting.DARK_GREEN + petColoursToggled + "\n" +
														EnumChatFormatting.GREEN + " Dungeon timer: " + EnumChatFormatting.DARK_GREEN + dungeonTimerToggled + "\n" +
														EnumChatFormatting.GREEN + " Golem spawn alerts: " + EnumChatFormatting.DARK_GREEN + golemAlertToggled + "\n" +
														EnumChatFormatting.GREEN + " Expertise in lore: " + EnumChatFormatting.DARK_GREEN + expertiseLoreToggled + "\n" +
														EnumChatFormatting.GREEN + " Skill 50 display: " + EnumChatFormatting.DARK_GREEN + skill50DisplayToggled + "\n" +
														EnumChatFormatting.GREEN + " Outline displayed text: " + EnumChatFormatting.DARK_GREEN + outlineTextToggled + "\n" +
														EnumChatFormatting.GREEN + " Midas Staff messages: " + EnumChatFormatting.DARK_GREEN + midasStaffMessages + "\n" +
														EnumChatFormatting.GREEN + " Livid solver: " + EnumChatFormatting.DARK_GREEN + lividSolverToggled + "\n" +
														EnumChatFormatting.GREEN + " Three man puzzle solver: " + EnumChatFormatting.DARK_GREEN + threeManToggled + "\n" +
														EnumChatFormatting.GREEN + " Oruo trivia solver: " + EnumChatFormatting.DARK_GREEN + oruoToggled + "\n" +
														EnumChatFormatting.GREEN + " Blaze puzzle solver: " + EnumChatFormatting.DARK_GREEN + blazeToggled + "\n" +
														EnumChatFormatting.GREEN + " Creeper puzzle solver: " + EnumChatFormatting.DARK_GREEN + creeperToggled));
		} else {
			player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Usage: " + getCommandUsage(arg0)));
		}
	}
}
