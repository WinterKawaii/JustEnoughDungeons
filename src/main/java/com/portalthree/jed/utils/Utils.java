package com.portalthree.jed.utils;

import com.portalthree.Main;
import com.portalthree.jed.handlers.ScoreboardHandler;
import com.portalthree.jed.handlers.TextRenderer;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.init.Blocks;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.util.*;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Utils {

	public static boolean inSkyblock = false;
	public static boolean inDungeons = false;
	static int[] skillXPPerLevel = {
			0,
			50,
			125,
			200,
			300,
			500,
			750,
			1000,
			1500,
			2000,
			3500,
			5000,
			7500,
			10000,
			15000,
			20000,
			30000,
			50000,
			75000,
			100000,
			200000,
			300000,
			400000,
			500000,
			600000,
			700000,
			800000,
			900000,
			1000000,
			1100000,
			1200000,
			1300000,
			1400000,
			1500000,
			1600000,
			1700000,
			1800000,
			1900000,
			2000000,
			2100000,
			2200000,
			2300000,
			2400000,
			2500000,
			2600000,
			2750000,
			2900000,
			3100000,
			3400000,
			3700000,
			4000000
	};
	static int[] dungeonsXPPerLevel = {
			0,
			50,
			75,
			110,
			160,
			230,
			330,
			470,
			670,
			950,
			1340,
			1890,
			2665,
			3760,
			5260,
			7380,
			10300,
			14400,
			20000,
			27600,
			38000,
			52500,
			71500,
			97000,
			132000,
			180000,
			243000,
			328000,
			445000,
			600000,
			800000,
			1065000,
			1410000,
			1900000,
			2500000,
			3300000,
			4300000,
			5600000,
			7200000,
			9200000,
			12000000,
			15000000,
			19000000,
			24000000,
			30000000,
			38000000,
			48000000,
			60000000,
			75000000,
			93000000,
			116250000
	};
	static int[] expertiseKills = {
			50,
			100,
			250,
			500,
			1000,
			2500,
			5500,
			10000,
			15000
	};

	public static int getItems(String item) {
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.thePlayer;

		double x = player.posX;
		double y = player.posY;
		double z = player.posZ;
		AxisAlignedBB scan = new AxisAlignedBB(x - 6, y - 6, z - 6, x + 6, y + 6, z + 6);
		List < EntityItem > items = mc.theWorld.getEntitiesWithinAABB(EntityItem.class, scan);

		for (EntityItem i: items) {
			String itemName = StringUtils.stripControlCodes(i.getEntityItem().getDisplayName());
			if (itemName.equals(item)) return i.getEntityItem().stackSize;
		}
		// No items found
		return 0;
	}

	public static Object getField(Class < ?>clazz, Object o, String...fieldNames) {
		Field field = null;
		for (String fieldName: fieldNames) {
			try {
				field = clazz.getDeclaredField(fieldName);
				break;
			} catch(Exception e) {}
		}
		if (field != null) {
			field.setAccessible(true);
			try {
				return field.get(o);
			} catch(IllegalAccessException e) {}
		}
		return null;
	}

	public static List < String > getMatchingPlayers(String arg) {
		List < String > matchingPlayers = new ArrayList < >();
		Collection < NetworkPlayerInfo > players = Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap();

		for (NetworkPlayerInfo player: players) {
			String playerName = player.getGameProfile().getName();
			if (playerName.startsWith("!")) continue; // New tablist
			if (playerName.toLowerCase().startsWith(arg.toLowerCase())) {
				matchingPlayers.add(playerName);
			}
		}

		return matchingPlayers;
	}

	public static ChatStyle createClickStyle(ClickEvent.Action action, String value) {
		ChatStyle style = new ChatStyle();
		style.setChatClickEvent(new ClickEvent(action, value));
		style.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(EnumChatFormatting.YELLOW + value)));
		return style;
	}

	public static void createTitle(String text, int seconds) {
		Minecraft.getMinecraft().thePlayer.playSound("random.orb", 1, (float) 0.5);
		Main.titleTimer = seconds * 20;
		Main.showTitle = true;
		Main.titleText = text;
	}

	public static void drawTitle(String text) {
		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution scaledResolution = new ScaledResolution(mc);

		int height = scaledResolution.getScaledHeight();
		int width = scaledResolution.getScaledWidth();
		int textLength = mc.fontRendererObj.getStringWidth(text);

		double scale = 4;
		if (textLength * scale > (width * 0.9F)) {
			scale = (width * 0.9F) / (float) textLength;
		}

		int titleX = (int)((width / 2) - (textLength * scale / 2));
		int titleY = (int)((height * 0.45) / scale);
		new TextRenderer(mc, text, titleX, titleY, scale);
	}

	public static void checkForSkyblock() {
		Minecraft mc = Minecraft.getMinecraft();
		if (mc != null && mc.theWorld != null && !mc.isSingleplayer()) {
			ScoreObjective scoreboardObj = mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(1);
			if (scoreboardObj != null) {
				String scObjName = ScoreboardHandler.cleanSB(scoreboardObj.getDisplayName());
				if (scObjName.contains("SKYBLOCK")) {
					inSkyblock = true;
					return;
				}
			}
		}
		inSkyblock = false;
	}

	public static void checkForDungeons() {
		Minecraft mc = Minecraft.getMinecraft();
		if (inSkyblock) {
			List < String > scoreboard = ScoreboardHandler.getSidebarLines();
			for (String s: scoreboard) {
				String sCleaned = ScoreboardHandler.cleanSB(s);
				if (sCleaned.contains("The Catacombs")) {
					inDungeons = true;
					return;
				}
			}
		}
		inDungeons = false;
	}

	public static String capitalizeString(String string) {
		String[] words = string.split("_");

		for (int i = 0; i < words.length; i++) {
			words[i] = words[i].substring(0, 1).toUpperCase() + words[i].substring(1).toLowerCase();
		}

		return String.join(" ", words);
	}

	public static double getPercentage(int num1, int num2) {
		if (num2 == 0) return 0D;
		double result = ((double) num1 * 100D) / (double) num2;
		result = Math.round(result * 100D) / 100D;
		return result;
	}

	public static void drawOnSlot(int size, int xSlotPos, int ySlotPos, int color) {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		int guiLeft = (sr.getScaledWidth() - 176) / 2;
		int guiTop = (sr.getScaledHeight() - 222) / 2;
		int x = guiLeft + xSlotPos;
		int y = guiTop + ySlotPos;
		// Move down when chest isn't 6 rows
		if (size != 90) y += (6 - (size - 36) / 9) * 9;

		GL11.glTranslated(0, 0, 1);
		Gui.drawRect(x, y, x + 16, y + 16, color);
		GL11.glTranslated(0, 0, -1);
	}

	public static void bindTexture(ResourceLocation resource)
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(resource);
	}

	public static void bindTexture(String resource)
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(resource));
	}

	public static String getTimeBetween(double timeOne, double timeTwo) {
		double secondsBetween = Math.floor(timeTwo - timeOne);

		String timeFormatted = "";
		int days;
		int hours;
		int minutes;
		int seconds;

		if (secondsBetween > 86400) {
			// More than 1d, display #d#h
			days = (int)(secondsBetween / 86400);
			hours = (int)(secondsBetween % 86400 / 3600);
			timeFormatted = days + "d" + hours + "h";
		} else if (secondsBetween > 3600) {
			// More than 1h, display #h#m
			hours = (int)(secondsBetween / 3600);
			minutes = (int)(secondsBetween % 3600 / 60);
			timeFormatted = hours + "h" + minutes + "m";
		} else {
			// Display #m#s
			minutes = (int)(secondsBetween / 60);
			seconds = (int)(secondsBetween % 60);
			timeFormatted = minutes + "m" + seconds + "s";
		}

		return timeFormatted;
	}

	public static double xpToDungeonsLevel(double xp) {
		for (int i = 0, xpAdded = 0; i < dungeonsXPPerLevel.length; i++) {
			xpAdded += dungeonsXPPerLevel[i];
			if (xp < xpAdded) {
				double level = (i - 1) + (xp - (xpAdded - dungeonsXPPerLevel[i])) / dungeonsXPPerLevel[i];
				return (double) Math.round(level * 100) / 100;
			}
		}
		return 50D;
	}

	public static String getColouredBoolean(boolean bool) {
		return bool ? EnumChatFormatting.GREEN + "On": EnumChatFormatting.RED + "Off";
	}

	public static void draw3DLine(Vec3 pos1, Vec3 pos2, int colourInt, float partialTicks) {
		Entity render = Minecraft.getMinecraft().getRenderViewEntity();
		WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
		Color colour = new Color(colourInt);

		double realX = render.lastTickPosX + (render.posX - render.lastTickPosX) * partialTicks;
		double realY = render.lastTickPosY + (render.posY - render.lastTickPosY) * partialTicks;
		double realZ = render.lastTickPosZ + (render.posZ - render.lastTickPosZ) * partialTicks;

		GlStateManager.pushMatrix();
		GlStateManager.translate( - realX, -realY, -realZ);
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GL11.glLineWidth(2);
		GlStateManager.color(colour.getRed() / 255f, colour.getGreen() / 255f, colour.getBlue() / 255f, colour.getAlpha() / 255f);
		worldRenderer.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION);

		worldRenderer.pos(pos1.xCoord, pos1.yCoord, pos1.zCoord).endVertex();
		worldRenderer.pos(pos2.xCoord, pos2.yCoord, pos2.zCoord).endVertex();
		Tessellator.getInstance().draw();

		GlStateManager.translate(realX, realY, realZ);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.popMatrix();
	}

	public static void draw3DString(BlockPos pos, String text, int colour, float partialTicks) {
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.thePlayer;
		double x = (pos.getX() - player.lastTickPosX) + ((pos.getX() - player.posX) - (pos.getX() - player.lastTickPosX)) * partialTicks;
		double y = (pos.getY() - player.lastTickPosY) + ((pos.getY() - player.posY) - (pos.getY() - player.lastTickPosY)) * partialTicks;
		double z = (pos.getZ() - player.lastTickPosZ) + ((pos.getZ() - player.posZ) - (pos.getZ() - player.lastTickPosZ)) * partialTicks;
		RenderManager renderManager = mc.getRenderManager();

		float f = 1.6F;
		float f1 = 0.016666668F * f;
		int width = mc.fontRendererObj.getStringWidth(text) / 2;
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		GL11.glNormal3f(0f, 1f, 0f);
		GlStateManager.rotate( - renderManager.playerViewY, 0f, 1f, 0f);
		GlStateManager.rotate(renderManager.playerViewX, 1f, 0f, 0f);
		GlStateManager.scale( - f1, -f1, -f1);
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		mc.fontRendererObj.drawString(text, -width, 0, colour);
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
	}

	// Yoinked from ForgeHax
	public static void draw3DBox(AxisAlignedBB aabb, int r, int g, int b, int a, float partialTicks) {
		Entity render = Minecraft.getMinecraft().getRenderViewEntity();
		WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();

		double realX = render.lastTickPosX + (render.posX - render.lastTickPosX) * partialTicks;
		double realY = render.lastTickPosY + (render.posY - render.lastTickPosY) * partialTicks;
		double realZ = render.lastTickPosZ + (render.posZ - render.lastTickPosZ) * partialTicks;

		GlStateManager.pushMatrix();
		GlStateManager.translate( - realX, -realY, -realZ);
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GL11.glLineWidth(2);
		GlStateManager.color(r / 255f, g / 255f, b / 255f, a / 255f);
		worldRenderer.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION);

		worldRenderer.pos(aabb.minX, aabb.minY, aabb.minZ).endVertex();
		worldRenderer.pos(aabb.maxX, aabb.minY, aabb.minZ).endVertex();
		worldRenderer.pos(aabb.maxX, aabb.minY, aabb.maxZ).endVertex();
		worldRenderer.pos(aabb.minX, aabb.minY, aabb.maxZ).endVertex();
		worldRenderer.pos(aabb.minX, aabb.minY, aabb.minZ).endVertex();
		Tessellator.getInstance().draw();
		worldRenderer.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION);
		worldRenderer.pos(aabb.minX, aabb.maxY, aabb.minZ).endVertex();
		worldRenderer.pos(aabb.maxX, aabb.maxY, aabb.minZ).endVertex();
		worldRenderer.pos(aabb.maxX, aabb.maxY, aabb.maxZ).endVertex();
		worldRenderer.pos(aabb.minX, aabb.maxY, aabb.maxZ).endVertex();
		worldRenderer.pos(aabb.minX, aabb.maxY, aabb.minZ).endVertex();
		Tessellator.getInstance().draw();
		worldRenderer.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION);
		worldRenderer.pos(aabb.minX, aabb.minY, aabb.minZ).endVertex();
		worldRenderer.pos(aabb.minX, aabb.maxY, aabb.minZ).endVertex();
		worldRenderer.pos(aabb.maxX, aabb.minY, aabb.minZ).endVertex();
		worldRenderer.pos(aabb.maxX, aabb.maxY, aabb.minZ).endVertex();
		worldRenderer.pos(aabb.maxX, aabb.minY, aabb.maxZ).endVertex();
		worldRenderer.pos(aabb.maxX, aabb.maxY, aabb.maxZ).endVertex();
		worldRenderer.pos(aabb.minX, aabb.minY, aabb.maxZ).endVertex();
		worldRenderer.pos(aabb.minX, aabb.maxY, aabb.maxZ).endVertex();
		Tessellator.getInstance().draw();

		GlStateManager.translate(realX, realY, realZ);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.popMatrix();
	}

	public static BlockPos getFirstBlockPosAfterVectors(Minecraft mc, Vec3 pos1, Vec3 pos2, int strength, int distance) {
		double x = pos2.xCoord - pos1.xCoord;
		double y = pos2.yCoord - pos1.yCoord;
		double z = pos2.zCoord - pos1.zCoord;

		for (int i = strength; i < distance * strength; i++) { // Start at least 1 strength away
			double newX = pos1.xCoord + ((x / strength) * i);
			double newY = pos1.yCoord + ((y / strength) * i);
			double newZ = pos1.zCoord + ((z / strength) * i);

			BlockPos newBlock = new BlockPos(newX, newY, newZ);
			if (mc.theWorld.getBlockState(newBlock).getBlock() != Blocks.air) {
				return newBlock;
			}
		}

		return null;
	}

	public static BlockPos getNearbyBlock(Minecraft mc, BlockPos pos, Block...blockTypes) {
		if (pos == null) return null;
		BlockPos pos1 = new BlockPos(pos.getX() - 2, pos.getY() - 3, pos.getZ() - 2);
		BlockPos pos2 = new BlockPos(pos.getX() + 2, pos.getY() + 3, pos.getZ() + 2);

		BlockPos closestBlock = null;
		double closestBlockDistance = 99;
		Iterable < BlockPos > blocks = BlockPos.getAllInBox(pos1, pos2);

		for (BlockPos block: blocks) {
			for (Block blockType: blockTypes) {
				if (mc.theWorld.getBlockState(block).getBlock() == blockType && block.distanceSq(pos) < closestBlockDistance) {
					closestBlock = block;
					closestBlockDistance = block.distanceSq(pos);
				}
			}
		}

		return closestBlock;
	}

	public static boolean isNPC(Entity entity) {
		if (! (entity instanceof EntityOtherPlayerMP)) {
			return false;
		}

		EntityLivingBase entityLivingBase = (EntityLivingBase) entity;

		return entity.getUniqueID().version() == 4 && entityLivingBase.getHealth() == 20.0F && !entityLivingBase.isPlayerSleeping();
	}

}