package com.portalthree.jed.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class SetPartyCommand extends CommandBase implements ICommand {

    public static boolean set = false;

    @Override
    public String getCommandName() { return "jedsetmembers"; }
    public static String getName() { return "jedsetmembers"; }

    @Override
    public String getCommandUsage(ICommandSender sender) { return "/" + getCommandName() + " <Party members>"; }
    public static String getUsage(){ return "/" + getName() + " <Party members>"; }

    @Override
    public int getRequiredPermissionLevel() { return 0; }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        final EntityPlayer player = (EntityPlayer) sender;

        if(args.length == 0) {
            player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Usage: " + getCommandUsage(sender)));
            return;
        }

        RepartyCommand.players = args;

        String members = String.join("\n" + "➜ " + EnumChatFormatting.WHITE,RepartyCommand.players);
        player.addChatMessage(new ChatComponentText( EnumChatFormatting.DARK_AQUA + "" + EnumChatFormatting.STRIKETHROUGH + "│─────── " + EnumChatFormatting.WHITE + "[JED]" + EnumChatFormatting.DARK_AQUA + "" + EnumChatFormatting.STRIKETHROUGH + "───────│\n" +
                EnumChatFormatting.WHITE + members + "\n" +
                EnumChatFormatting.DARK_AQUA + "" + EnumChatFormatting.STRIKETHROUGH + "│─────── " + EnumChatFormatting.WHITE + "[JED]" + EnumChatFormatting.DARK_AQUA + "" + EnumChatFormatting.STRIKETHROUGH + "───────│\n"));
        set = true;
    }
}