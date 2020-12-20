package com.portalthree.jed.modules;

import com.portalthree.jed.commands.ToggleCommand;
import com.portalthree.jed.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayList;
import java.util.List;

public class CreeperSolver {
    public static int titleTimer = -1;
    public static boolean showTitle = false;
    public static int skillTimer = -1;
    public static boolean showSkill = false;
    static int tickAmount = 1;
    static int[] creeperLineColours = {
            0x50EF39,
            0xC51111,
            0x132ED1,
            0x117F2D,
            0xED54BA,
            0xEF7D0D,
            0xF5F557,
            0xD6E0F0,
            0x6B2FBB,
            0x39FEDC
    };
    static boolean drawCreeperLines = false;
    static Vec3 creeperLocation = new Vec3(0, 0, 0);
    static List<Vec3[]> creeperLines = new ArrayList<Vec3[]>();

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


            if (ToggleCommand.creeperToggled && Utils.inDungeons && mc.theWorld != null) {
                double x = player.posX;
                double y = player.posY;
                double z = player.posZ;
                // Find creepers nearby
                AxisAlignedBB creeperScan = new AxisAlignedBB(x - 14, y - 8, z - 13, x + 14, y + 8, z + 13); // 28x16x26 cube
                List<EntityCreeper> creepers = mc.theWorld.getEntitiesWithinAABB(EntityCreeper.class, creeperScan);
                // Check if creeper is nearby
                if (creepers.size() > 0) {
                    EntityCreeper creeper = creepers.get(0);
                    // Start creeper line drawings
                    creeperLines.clear();
                    if (!drawCreeperLines) creeperLocation = new Vec3(creeper.posX, creeper.posY + 1, creeper.posZ);
                    drawCreeperLines = true;
                    // Search for nearby sea lanterns and   blocks
                    BlockPos point1 = new BlockPos(creeper.posX - 14, creeper.posY - 7, creeper.posZ - 13);
                    BlockPos point2 = new BlockPos(creeper.posX + 14, creeper.posY + 10, creeper.posZ + 13);
                    Iterable<BlockPos> blocks = BlockPos.getAllInBox(point1, point2);
                    for (BlockPos blockPos : blocks) {
                        Block block = mc.theWorld.getBlockState(blockPos).getBlock();
                        if (block == Blocks.sea_lantern || block == Blocks.prismarine) {
                            // Connect block to nearest block on opposite side
                            Vec3 startBlock = new Vec3(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);
                            BlockPos oppositeBlock = Utils.getFirstBlockPosAfterVectors(mc, startBlock, creeperLocation, 10, 20);
                            BlockPos endBlock = Utils.getNearbyBlock(mc, oppositeBlock, Blocks.sea_lantern, Blocks.prismarine);
                            if (endBlock != null && startBlock.yCoord > 68 && endBlock.getY() > 68) { // Don't create line underground
                                // Add to list for drawing
                                Vec3[] insertArray = {
                                        startBlock,
                                        new Vec3(endBlock.getX() + 0.5, endBlock.getY() + 0.5, endBlock.getZ() + 0.5)
                                };
                                creeperLines.add(insertArray);
                            }
                        }
                    }
                } else {
                    drawCreeperLines = false;
                }
            }
            tickAmount = 0;
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
        if (ToggleCommand.creeperToggled && drawCreeperLines && !creeperLines.isEmpty()) {
            for (int i = 0; i < creeperLines.size(); i++) {
                Utils.draw3DLine(creeperLines.get(i)[0], creeperLines.get(i)[1], creeperLineColours[i % 10], event.partialTicks);
            }
        }
    }
}
