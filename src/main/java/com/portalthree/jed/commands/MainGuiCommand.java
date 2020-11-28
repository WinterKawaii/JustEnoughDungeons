package com.portalthree.jed.commands;


import com.portalthree.Main;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class MainGuiCommand extends CommandBase {

	@Override
	public String getCommandName() {
		return "jed";
	}

	@Override
	public String getCommandUsage(ICommandSender arg0) {
		return null;
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}
	
	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
		Main.guiToOpen = "jedGui";
	}

}
