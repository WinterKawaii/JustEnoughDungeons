package com.portalthree.jed.commands;

import com.portalthree.jed.handlers.ConfigHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class ToggleCommand extends CommandBase implements ICommand {
    public static boolean coordsToggled;
    public static boolean outlineTextToggled;
    public static boolean threeManToggled;
    public static boolean oruoToggled;
    public static boolean blazeToggled;
    public static boolean creeperToggled;
    public static boolean joinInformationToggled;
    public static boolean mobClearToggled;
    public static boolean amongUsSolverToggled;
    public static boolean discordRpcToggled;
    public static boolean witherKeyToggled;

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
    public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
        final EntityPlayer player = (EntityPlayer) arg0;
        final ConfigHandler cf = new ConfigHandler();

        if (arg1.length == 0) {
            player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Usage: " + getCommandUsage(arg0)));
            return;
        }

        if (arg1[0].equalsIgnoreCase("coords")) {
            coordsToggled = !coordsToggled;
            cf.writeBooleanConfig("toggles", "Coords", coordsToggled);
        } else if (arg1[0].equalsIgnoreCase("threemanpuzzle")) {
            threeManToggled = !threeManToggled;
            cf.writeBooleanConfig("toggles", "ThreeManPuzzle", threeManToggled);
        } else if (arg1[0].equalsIgnoreCase("oruopuzzle")) {
            oruoToggled = !oruoToggled;
            cf.writeBooleanConfig("toggles", "OruoPuzzle", oruoToggled);
        } else if (arg1[0].equalsIgnoreCase("blazepuzzle")) {
            blazeToggled = !blazeToggled;
            cf.writeBooleanConfig("toggles", "BlazePuzzle", blazeToggled);
        } else if (arg1[0].equalsIgnoreCase("creeperpuzzle")) {
            creeperToggled = !creeperToggled;
            cf.writeBooleanConfig("toggles", "joinInformation", creeperToggled);
        } else if (arg1[0].equalsIgnoreCase("joinInformation")) {
            joinInformationToggled = !joinInformationToggled;
            cf.writeBooleanConfig("toggles", "joinInformation", joinInformationToggled);
        } else if (arg1[0].equalsIgnoreCase("mobClear")) {
            mobClearToggled = !mobClearToggled;
            cf.writeBooleanConfig("toggles", "mobClear", mobClearToggled);
        }  else if (arg1[0].equalsIgnoreCase("amongUsSolver")) {
            amongUsSolverToggled = !amongUsSolverToggled;
            cf.writeBooleanConfig("toggles", "amongUsSolver", amongUsSolverToggled);
        }  else if (arg1[0].equalsIgnoreCase("witherKey")) {
        witherKeyToggled = !witherKeyToggled;
        cf.writeBooleanConfig("toggles", "witherKey", witherKeyToggled);
        } else if (arg1[0].equalsIgnoreCase("discordRpc")) {
            discordRpcToggled = !discordRpcToggled;
            cf.writeBooleanConfig("discordRpc", "discordRpc", discordRpcToggled);
        }
    }
}