package com.portalthree.jed.commands;

import com.google.gson.JsonObject;
import com.portalthree.jed.handlers.APIHandler;
import com.portalthree.jed.handlers.ConfigHandler;
import com.portalthree.jed.utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public class DungeonsCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "jeddungeons";
    }

    @Override
    public String getCommandUsage(ICommandSender arg0) {
        return "/" + getCommandName() + " [name]";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        if (args.length == 1) {
            return Utils.getMatchingPlayers(args[0]);
        }
        return null;
    }

    @Override
    public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
        // MULTI THREAD DRIFTING
        new Thread(() -> {
            APIHandler ah = new APIHandler();
            ConfigHandler cf = new ConfigHandler();
            EntityPlayer player = (EntityPlayer) arg0;

            // Check key
            String key = cf.getString("api", "APIKey");
            if (key.equals("")) {
                player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + " " + EnumChatFormatting.BOLD + "WRONG API KEY! Use /jedsetkey. <api-key>"));
            }

            // Get UUID for Hypixel API requests
            String username;
            String uuid;
            if (arg1.length == 0) {
                username = player.getName();
                uuid = player.getUniqueID().toString().replaceAll("[\\-]", "");
                player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Checking dungeon stats of " + EnumChatFormatting.DARK_GREEN + username));
            } else {
                username = arg1[0];
                player.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Checking dungeon stats of " + EnumChatFormatting.DARK_GREEN + username));
                uuid = ah.getUUID(username);
            }

            // Find stats of latest profile
            String latestProfile = ah.getLatestProfileID(uuid, key);
            if (latestProfile == null) return;

            String profileURL = "https://api.hypixel.net/skyblock/profile?profile=" + latestProfile + "&key=" + key;
            System.out.println("Fetching profile...");
            JsonObject profileResponse = ah.getResponse(profileURL);
            if (!profileResponse.get("success").getAsBoolean()) {
                String reason = profileResponse.get("cause").getAsString();
                player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "(/jedsetkey will probably resolve this problem) Failed with reason: " + reason));
                return;
            }

            System.out.println("Fetching dungeon stats...");
            JsonObject dungeonsObject = profileResponse.get("profile").getAsJsonObject().get("members").getAsJsonObject().get(uuid).getAsJsonObject().get("dungeons").getAsJsonObject();
            if (!dungeonsObject.get("dungeon_types").getAsJsonObject().get("catacombs").getAsJsonObject().has("experience")) {
                player.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "This player has not played dungeons."));
                return;
            }

            double catacombs = Utils.xpToDungeonsLevel(dungeonsObject.get("dungeon_types").getAsJsonObject().get("catacombs").getAsJsonObject().get("experience").getAsDouble());
            double healer = Utils.xpToDungeonsLevel(dungeonsObject.get("player_classes").getAsJsonObject().get("healer").getAsJsonObject().get("experience").getAsDouble());
            double mage = Utils.xpToDungeonsLevel(dungeonsObject.get("player_classes").getAsJsonObject().get("mage").getAsJsonObject().get("experience").getAsDouble());
            double berserk = Utils.xpToDungeonsLevel(dungeonsObject.get("player_classes").getAsJsonObject().get("berserk").getAsJsonObject().get("experience").getAsDouble());
            double archer = Utils.xpToDungeonsLevel(dungeonsObject.get("player_classes").getAsJsonObject().get("archer").getAsJsonObject().get("experience").getAsDouble());
            double tank = Utils.xpToDungeonsLevel(dungeonsObject.get("player_classes").getAsJsonObject().get("tank").getAsJsonObject().get("experience").getAsDouble());
            String selectedClass = Utils.capitalizeString(dungeonsObject.get("selected_dungeon_class").getAsString());

            player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA + "" + EnumChatFormatting.STRIKETHROUGH + "│─────────── " + EnumChatFormatting.DARK_AQUA + "" + EnumChatFormatting.WHITE + "[JED]" + EnumChatFormatting.DARK_AQUA + "" + EnumChatFormatting.STRIKETHROUGH + "───────────│\n" +
                    EnumChatFormatting.YELLOW + "  Currently Checking: " + EnumChatFormatting.WHITE + username + EnumChatFormatting.YELLOW + "'s stats.\n" +
                    EnumChatFormatting.GRAY + "  Catacombs Level: " + EnumChatFormatting.LIGHT_PURPLE + catacombs + "\n" +
                    EnumChatFormatting.GRAY + "  Current Selected Class: " + EnumChatFormatting.LIGHT_PURPLE + selectedClass + "\n\n" +
                    EnumChatFormatting.GRAY + "  Average Class Level: " + EnumChatFormatting.LIGHT_PURPLE + (tank + archer + berserk + mage + healer) / 5 + "\n" +
                    EnumChatFormatting.DARK_AQUA + "" + EnumChatFormatting.STRIKETHROUGH + "│─────────── " + EnumChatFormatting.DARK_AQUA + "" + EnumChatFormatting.WHITE + "[JED]" + EnumChatFormatting.DARK_AQUA + "" + EnumChatFormatting.STRIKETHROUGH + "───────────│\n"));
        }).start();
    }

}
