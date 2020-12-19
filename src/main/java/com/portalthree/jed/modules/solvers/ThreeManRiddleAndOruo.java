package com.portalthree.jed.modules.solvers;

import com.portalthree.jed.commands.ToggleCommand;
import com.portalthree.jed.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;

public class ThreeManRiddleAndOruo {
    static Map<String, String> triviaSolutions = new HashMap<String, String>();

    static String[] riddleSolutions = {
            "The reward is not in my chest!",
            "At least one of them is lying, and the reward is not in",
            "My chest doesn't have the reward. We are all telling the truth",
            "My chest has the reward and I'm telling the truth",
            "The reward isn't in any of our chests",
            "Both of them are telling the truth."
    };


    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChat(ClientChatReceivedEvent event) {
        String message = event.message.getUnformattedText();

        if (!Utils.inSkyblock) return;

        // Dungeon chat spoken by an NPC, containing :
        if (ToggleCommand.threeManToggled && Utils.inDungeons && message.contains("[NPC]")) {
            for (String solution : riddleSolutions) {
                if (message.contains(solution)) {
                    String npcName = message.substring(message.indexOf("]") + 2, message.indexOf(":"));
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_GREEN + "" + EnumChatFormatting.BOLD + npcName + EnumChatFormatting.GREEN + " has the blessing."));
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_GREEN + "" + EnumChatFormatting.BOLD + npcName + EnumChatFormatting.GREEN + " has the blessing."));
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_GREEN + "" + EnumChatFormatting.BOLD + npcName + EnumChatFormatting.GREEN + " has the blessing."));
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_GREEN + "" + EnumChatFormatting.BOLD + npcName + EnumChatFormatting.GREEN + " has the blessing."));
                }
            }
        }

        if (message.contains(":")) return;

        if (ToggleCommand.oruoToggled && Utils.inDungeons) {
            for (String question : triviaSolutions.keySet()) {
                if (message.contains(question)) {
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Answer: " + EnumChatFormatting.DARK_GREEN + EnumChatFormatting.BOLD + triviaSolutions.get(question)));
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Answer: " + EnumChatFormatting.DARK_GREEN + EnumChatFormatting.BOLD + triviaSolutions.get(question)));
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Answer: " + EnumChatFormatting.DARK_GREEN + EnumChatFormatting.BOLD + triviaSolutions.get(question)));
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Answer: " + EnumChatFormatting.DARK_GREEN + EnumChatFormatting.BOLD + triviaSolutions.get(question)));
                    break;
                }
            }
        }
    }
}
