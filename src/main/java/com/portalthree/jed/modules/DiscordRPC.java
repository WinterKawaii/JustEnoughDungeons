package com.portalthree.jed.modules;

import com.portalthree.jed.utils.DiscordRPCUtils;
import com.portalthree.jed.commands.ToggleCommand;
import com.portalthree.jed.handlers.ScoreboardHandler;
import com.portalthree.jed.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

public class DiscordRPC {
    static int tickAmount = 1;

    @SubscribeEvent
    public void onTick(TickEvent.RenderTickEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;

        // Checks every second
        tickAmount++;
        if (tickAmount % 20 == 0) {
            if (player != null) {
                Utils.checkForSkyblock();
                Utils.checkForDungeons();
            }

            if (ToggleCommand.discordRpcToggled && Minecraft.getMinecraft() != null && mc.theWorld != null && player != null && Utils.inDungeons) {
                DiscordRPCUtils.start();
                List<String> scoreboard = ScoreboardHandler.getSidebarLines();
                for (String s : scoreboard) {
                    String sCleaned = ScoreboardHandler.cleanSB(s);
                    if (sCleaned.contains("The Catacombs (")) {
                        if (sCleaned.contains("F1")) {
                            if (player.getHeldItem() != null) {
                                DiscordRPCUtils.update(player.getName() + " is in a F1", "Holding: " + player.getHeldItem().getDisplayName().trim(), "floor_1");
                            } else {
                                DiscordRPCUtils.update(player.getName() + " is in a F1", "https://discord.gg/Cx56wfP8dY", "floor_1");
                            }
                        } else if (sCleaned.contains("F2")) {
                            if (player.getHeldItem() != null) {
                                DiscordRPCUtils.update(player.getName() + " is in a F2", "Holding: " + player.getHeldItem().getDisplayName().trim(), "floor_2");
                            } else {
                                DiscordRPCUtils.update(player.getName() + " is in a F2", "https://discord.gg/Cx56wfP8dY", "floor_2");
                            }
                        } else if (sCleaned.contains("F3")) {
                            if (player.getHeldItem() != null) {
                                DiscordRPCUtils.update(player.getName() + " is in a F3", "Holding: " + player.getHeldItem().getDisplayName().trim(), "floor_3");
                            } else {
                                DiscordRPCUtils.update(player.getName() + " is in a F3", "https://discord.gg/Cx56wfP8dY", "floor_3");
                            }
                        } else if (sCleaned.contains("F4")) {
                            if (player.getHeldItem() != null) {
                                DiscordRPCUtils.update(player.getName() + " is in a F4", "Holding: " + player.getHeldItem().getDisplayName().trim(), "floor_4");
                            } else {
                                DiscordRPCUtils.update(player.getName() + " is in a F4", "https://discord.gg/Cx56wfP8dY", "floor_4");
                            }
                        } else if (sCleaned.contains("F5")) {
                            if (player.getHeldItem() != null) {
                                DiscordRPCUtils.update(player.getName() + " is in a F5", "Holding: " + player.getHeldItem().getDisplayName().trim(), "floor_5");
                            } else {
                                DiscordRPCUtils.update(player.getName() + " is in a F5", "https://discord.gg/Cx56wfP8dY", "floor_5");
                            }
                        } else if (sCleaned.contains("F6")) {
                            if (player.getHeldItem() != null) {
                                DiscordRPCUtils.update(player.getName() + " is in a F6", "Holding: " + player.getHeldItem().getDisplayName().trim(), "floor_6");
                            } else {
                                DiscordRPCUtils.update(player.getName() + " is in a F6", "https://discord.gg/Cx56wfP8dY", "floor_6");
                            }
                        } else if (sCleaned.contains("F7")) {
                            if (player.getHeldItem() != null) {
                                DiscordRPCUtils.update(player.getName() + " is in a F7", "Holding: " + player.getHeldItem().getDisplayName().trim(), "floor_7");
                            } else {
                                DiscordRPCUtils.update(player.getName() + " is in a F7", "https://discord.gg/Cx56wfP8dY", "floor_7");
                            }
                        }
                    }
                }
            } else if (!ToggleCommand.discordRpcToggled || !Utils.inDungeons) {
                DiscordRPCUtils.stop();
            }
        }
    }
}
