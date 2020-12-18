package com.portalthree.jed.modules;

import com.portalthree.jed.commands.ToggleCommand;
import com.portalthree.jed.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PartyFinderInformation {
    private static final Pattern partyFinderPlayerRegex = Pattern.compile("^Dungeon Finder > ?(?<player>\\w{1,16}) joined the dungeon group! \\(.+\\)$");

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChat(ClientChatReceivedEvent event) {
        String message = event.message.getUnformattedText();

        if (!Utils.inSkyblock) return;

        if (ToggleCommand.joinInformationToggled) {
            if (message.contains("Dungeon Finder >")) {
                Minecraft mc = Minecraft.getMinecraft();
                EntityPlayerSP playerEntity = mc.thePlayer;
                String text = event.message.getUnformattedText();

                Matcher matcher = partyFinderPlayerRegex.matcher(text);
                if (matcher.matches()) {
                    String player = matcher.group("player");
                    playerEntity.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA + "\n" + EnumChatFormatting.STRIKETHROUGH + "│───── " + EnumChatFormatting.DARK_AQUA + "" + EnumChatFormatting.WHITE + player + EnumChatFormatting.DARK_AQUA + "" + EnumChatFormatting.STRIKETHROUGH + "─────│"));

                    ChatComponentText statsClickText = new ChatComponentText(EnumChatFormatting.YELLOW + "" + EnumChatFormatting.BOLD + "[STATS]");
                    statsClickText.setChatStyle(Utils.createClickStyle(ClickEvent.Action.RUN_COMMAND, "/jeddungeons " + player));
                    playerEntity.addChatMessage(statsClickText);

                    ChatComponentText kickClickText = new ChatComponentText(EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + "[KICK]");
                    kickClickText.setChatStyle(Utils.createClickStyle(ClickEvent.Action.RUN_COMMAND, "/party kick " + player));
                    playerEntity.addChatMessage(kickClickText);

                    playerEntity.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA + "" + EnumChatFormatting.STRIKETHROUGH + "│───── " + EnumChatFormatting.DARK_AQUA + "" + EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD + "[JED]" + EnumChatFormatting.DARK_AQUA + "" + EnumChatFormatting.STRIKETHROUGH + "─────│\n"));
                }
            }
        }
    }
}
