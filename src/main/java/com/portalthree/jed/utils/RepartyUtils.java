package com.portalthree.jed.utils;

import com.portalthree.jed.handlers.events.ChatReceivedHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;

public class RepartyUtils extends Thread {
    public static ArrayList<String> nameList;

    static {
        RepartyUtils.nameList = new ArrayList<String>();
    }

    @Override
    public void run() {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;

        player.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA + "Just Enough Dungeons ➜" + EnumChatFormatting.YELLOW + " Dumping the players in your party..."));
        RepartyUtils.nameList.clear();
        ChatReceivedHandler.currentPartyUsing = "re";
        ChatReceivedHandler.togglePartyMessage = true;
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Minecraft.getMinecraft().thePlayer.sendChatMessage("/pl");
        try {
            Thread.sleep(250L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Minecraft.getMinecraft().thePlayer.sendChatMessage("/p disband");
        ChatReceivedHandler.togglePartyMessage = false;
        try {
            Thread.sleep(250L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (RepartyUtils.nameList.size() == 0) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Couldn't find anybody in your party!"));
        } else {
            for (int i = 0; i < RepartyUtils.nameList.size(); ++i) {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/p invite " + RepartyUtils.nameList.get(i));
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }
}