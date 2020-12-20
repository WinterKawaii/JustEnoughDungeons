package com.portalthree.jed.handlers.events;

import com.portalthree.jed.utils.RepartyUtils;
import com.portalthree.jed.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;

public class ChatReceivedHandler {
    public static Boolean togglePartyMessage;
    public static String currentPartyUsing;

    static {
        ChatReceivedHandler.togglePartyMessage = false;
        ChatReceivedHandler.currentPartyUsing = "";
    }

    public static String removeRankFromString(final String string) {
        String temp = string;
        temp = temp.replace("[VIP] ", "");
        temp = temp.replace("[VIP+] ", "");
        temp = temp.replace("[MVP] ", "");
        temp = temp.replace("[MVP+] ", "");
        temp = temp.replace("[MVP++] ", "");
        return temp;
    }

    @SubscribeEvent
    public void chatRecieved(final ClientChatReceivedEvent event) {
        final char dot = '\u25cf';
        final String message = event.message.getUnformattedText();
        ArrayList<String> curArrList;
        Minecraft mc = Minecraft.getMinecraft();

        if (ChatReceivedHandler.togglePartyMessage) {
            if (ChatReceivedHandler.currentPartyUsing.equals("")) {
                return;
            } else {
                if (!ChatReceivedHandler.currentPartyUsing.equals("re")) {
                    return;
                }
                curArrList = RepartyUtils.nameList;
            }
            if (message.startsWith("Party Leader:")) {
                final String leaderName = removeRankFromString(message.replace(" " + dot, "").replace("Party Leader: ", ""));
                System.out.println("Party Leader Name: " + leaderName);
                if (!Utils.checkIfArrayContains(curArrList, leaderName) && !leaderName.equals(mc.thePlayer.getDisplayNameString())) {
                    curArrList.add(leaderName);
                }
                event.setCanceled(true);
            } else if (message.startsWith("Party Members:")) {
                final String[] tempArray = removeRankFromString(message.replace("Party Members: ", "")).split(" " + dot + " ");
                for (int i = 0; i < tempArray.length; ++i) {
                    System.out.println("Member " + i + " Name: " + tempArray[i]);
                    if (!Utils.checkIfArrayContains(curArrList, tempArray[i]) && !tempArray[i].equals(mc.thePlayer.getDisplayNameString())) {
                        curArrList.add(tempArray[i]);
                    }
                }
                event.setCanceled(true);
            } else if (message.startsWith("-")) {
                event.setCanceled(true);
            } else if (message.startsWith("Party Members")) {
                event.setCanceled(true);
            } else if (message.startsWith("Party Moderators")) {
                final String[] tempArray = removeRankFromString(message.replace("Party Moderators: ", "")).split(" " + dot + " ");
                for (int i = 0; i < tempArray.length; ++i) {
                    System.out.println("Moderator " + i + " Name: " + tempArray[i]);
                    if (!Utils.checkIfArrayContains(curArrList, tempArray[i]) && !tempArray[i].equals(mc.thePlayer.getDisplayNameString())) {
                        curArrList.add(tempArray[i]);
                    }
                }
                event.setCanceled(true);
            } else if (message.replace(" ", "").equals("")) {
                event.setCanceled(true);
            } else if (message.startsWith("You are not in a party right now.") || message.startsWith("You are not currently in a party.")) {
                RepartyUtils.nameList.clear();
                if (ChatReceivedHandler.togglePartyMessage) {
                    event.setCanceled(true);
                }
            }
        }

    }
}