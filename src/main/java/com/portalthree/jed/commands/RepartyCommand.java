package com.portalthree.jed.commands;

import com.portalthree.jed.utils.RepartyUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;


public class RepartyCommand extends CommandBase implements ICommand {

    @Override
    public String getCommandName() {
        return "jedreparty";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/" + getCommandName();
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        final Thread runCmd = new RepartyUtils();
        runCmd.start();
    }
}