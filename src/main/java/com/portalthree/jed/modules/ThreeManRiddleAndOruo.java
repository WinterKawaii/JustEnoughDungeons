package com.portalthree.jed.modules;

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

    public void triviaSolutions() {
        triviaSolutions.put("What is the status of The Watcher?", "Stalker");
        triviaSolutions.put("What is the status of Bonzo?", "New Necromancer");
        triviaSolutions.put("What is the status of Scarf?", "Apprentice Necromancer");
        triviaSolutions.put("What is the status of The Professor?", "Professor");
        triviaSolutions.put("What is the status of Thorn?", "Shaman Necromancer");
        triviaSolutions.put("What is the status of Livid?", "Master Necromancer");
        triviaSolutions.put("What is the status of Sadan?", "Necromancer Lord");
        triviaSolutions.put("What is the status of Maxor?", "Young Wither");
        triviaSolutions.put("What is the status of Goldor?", "Wither Soldier");
        triviaSolutions.put("What is the status of Storm?", "Elementalist");
        triviaSolutions.put("What is the status of Necron?", "Wither Lord");
        triviaSolutions.put("year?", "Check your calendar, the nearest new year celebration's year is the answer");
        triviaSolutions.put("How many total Fairy Souls are there?", "209 Fairy Souls");
        triviaSolutions.put("How many Fairy Souls are there in Spider's Den?", "17 Fairy Souls");
        triviaSolutions.put("How many Fairy Souls are there in The End?", "12 Fairy Souls");
        triviaSolutions.put("How many Fairy Souls are there in The Barn?", "7 Fairy Souls");
        triviaSolutions.put("How many Fairy Souls are there in Mushroom Desert?", "8 Fairy Souls");
        triviaSolutions.put("How many Fairy Souls are there in Blazing Fortress?", "19 Fairy Souls");
        triviaSolutions.put("How many Fairy Souls are there in The Park?", "11 Fairy Souls");
        triviaSolutions.put("How many Fairy Souls are there in Jerry's Workshop?", "5 Fairy Souls");
        triviaSolutions.put("How many Fairy Souls are there in Hub?", "79 Fairy Souls");
        triviaSolutions.put("How many Fairy Souls are there in The Hub?", "79 Fairy Souls");
        triviaSolutions.put("How many Fairy Souls are there in Deep Caverns?", "21 Fairy Souls");
        triviaSolutions.put("How many Fairy Souls are there in Gold Mine?", "12 Fairy Souls");
        triviaSolutions.put("How many Fairy Souls are there in Dungeon Hub?", "7 Fairy Souls");
        triviaSolutions.put("Which brother is on the Spider's Den?", "Rick");
        triviaSolutions.put("What is the name of Rick's brother?", "Pat");
        triviaSolutions.put("What is the name of the Painter in the Hub?", "Marco");
        triviaSolutions.put("What is the name of the person that upgrades pets?", "Kat");
        triviaSolutions.put("What is the name of the lady of the Nether?", "Elle");
        triviaSolutions.put("Which villager in the Village gives you a Rogue Sword?", "Jamie");
        triviaSolutions.put("How many unique minions are there?", "52 Minions");
        triviaSolutions.put("Which of these enemies does not spawn in the Spider's Den?", "Zombie Spider OR Cave Spider OR Broodfather OR Wither Skeleton");
        triviaSolutions.put("Which of these monsters only spawns at night?", "Zombie Villager OR Ghast");
        triviaSolutions.put("Which of these is not a dragon in The End?", "Zoomer Dragon OR Weak Dragon OR Stonk Dragon OR Holy Dragon OR Boomer Dragon");
    }

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
