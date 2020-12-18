package com.portalthree.jed.modules;

import com.portalthree.jed.commands.ToggleCommand;
import com.portalthree.jed.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

public class BlazeSolver {
    public static int titleTimer = -1;
    public static boolean showTitle = false;
    public static int skillTimer = -1;
    public static boolean showSkill = false;
    static int tickAmount = 1;
    static Entity highestBlaze = null;
    static Entity lowestBlaze = null;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;

        // Checks every second
        tickAmount++;
        if (tickAmount % 20 == 0) {
            if (player != null) {
                Utils.checkForSkyblock();
                Utils.checkForDungeons();
            }

            tickAmount = 0;
        }

        // Checks 5 times per second
        if (tickAmount % 4 == 0) {
            if (ToggleCommand.blazeToggled && Utils.inDungeons && mc.theWorld != null) {
                List<Entity> entities = mc.theWorld.getLoadedEntityList();
                int highestHealth = 0;
                highestBlaze = null;
                int lowestHealth = 99999999;
                lowestBlaze = null;

                for (Entity entity : entities) {
                    if (entity.getName().contains("Blaze") && entity.getName().contains("/")) {
                        String blazeName = StringUtils.stripControlCodes(entity.getName());
                        try {
                            int health = Integer.parseInt(blazeName.substring(blazeName.indexOf("/") + 1, blazeName.length() - 2));
                            if (health > highestHealth) {
                                highestHealth = health;
                                highestBlaze = entity;
                            }
                            if (health < lowestHealth) {
                                lowestHealth = health;
                                lowestBlaze = entity;
                            }
                        } catch (NumberFormatException ex) {
                            System.err.println(ex);
                        }
                    }
                }
            }
        }

        if (titleTimer >= 0) {
            if (titleTimer == 0) {
                showTitle = false;
            }
            titleTimer--;
        }
        if (skillTimer >= 0) {
            if (skillTimer == 0) {
                showSkill = false;
            }
            skillTimer--;
        }
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        if (ToggleCommand.blazeToggled) {
            if (lowestBlaze != null) {
                BlockPos stringPos = new BlockPos(lowestBlaze.posX, lowestBlaze.posY + 1, lowestBlaze.posZ);
                Utils.draw3DString(stringPos, EnumChatFormatting.BOLD + "Smallest!", 0xFF0000, event.partialTicks);
                AxisAlignedBB aabb = new AxisAlignedBB(lowestBlaze.posX - 0.5, lowestBlaze.posY - 2, lowestBlaze.posZ - 0.5, lowestBlaze.posX + 0.5, lowestBlaze.posY, lowestBlaze.posZ + 0.5);
                Utils.draw3DBox(aabb, 0xFF, 0x00, 0x00, 0xFF, event.partialTicks);
            }
            if (highestBlaze != null) {
                BlockPos stringPos = new BlockPos(highestBlaze.posX, highestBlaze.posY + 1, highestBlaze.posZ);
                Utils.draw3DString(stringPos, EnumChatFormatting.BOLD + "Biggest!", 0x40FF40, event.partialTicks);
                AxisAlignedBB aabb = new AxisAlignedBB(highestBlaze.posX - 0.5, highestBlaze.posY - 2, highestBlaze.posZ - 0.5, highestBlaze.posX + 0.5, highestBlaze.posY, highestBlaze.posZ + 0.5);
                Utils.draw3DBox(aabb, 0x00, 0xFF, 0x00, 0xFF, event.partialTicks);
            }
        }
    }
}
