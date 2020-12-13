package com.portalthree;

import com.portalthree.jed.DiscordRPC;
import com.portalthree.jed.commands.*;
import com.portalthree.jed.handlers.ConfigHandler;
import com.portalthree.jed.handlers.PacketHandler;
import com.portalthree.jed.handlers.ScoreboardHandler;
import com.portalthree.jed.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.event.ClickEvent;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Mod(modid = Main.MODID, version = Main.VERSION, clientSideOnly = true)
public class Main {
    public static final String MODID = "jed";
    public static final String VERSION = "1.0";
    private static final Pattern partyFinderPlayerRegex = Pattern.compile("^Dungeon Finder > ?(?<player>\\w{1,16}) joined the dungeon group! \\(.+\\)$");
    private static final Pattern amongUsChestRegex = Pattern.compile("^Select all the ?(?<containerNameFound>\\w{1,16}) items!");
    private static final Pattern amongUsChestRegex2 = Pattern.compile("^What starts with: '?(?<itemFound>\\w)'\\?");
    public static Main INSTANCE;
    public static int titleTimer = -1;
    public static String titleText = "";
    public static boolean showTitle = false;
    public static int skillTimer = -1;
    public static boolean showSkill = false;
    public static String guiToOpen = null;
    static int tickAmount = 1;
    static String[] riddleSolutions = {
            "The reward is not in my chest!",
            "At least one of them is lying, and the reward is not in",
            "My chest doesn't have the reward. We are all telling the truth",
            "My chest has the reward and I'm telling the truth",
            "The reward isn't in any of our chests",
            "Both of them are telling the truth."
    };
    static Map<String,
            String> triviaSolutions = new HashMap<String, String>();
    static Entity highestBlaze = null;
    static Entity lowestBlaze = null;
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

    @EventHandler
    public void init(FMLInitializationEvent event) {
        FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new PacketHandler());

        ConfigHandler.reloadConfig();

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

    @EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        INSTANCE = this;
        ClientCommandHandler.instance.registerCommand(new ToggleCommand());
        ClientCommandHandler.instance.registerCommand(new ReloadConfigCommand());
        ClientCommandHandler.instance.registerCommand(new DungeonsCommand());
        ClientCommandHandler.instance.registerCommand(new MainGuiCommand());
        ClientCommandHandler.instance.registerCommand(new SetkeyCommand());
        ClientCommandHandler.instance.registerCommand(new HelpCommand());
    }

    @EventHandler
    public void postInit(final FMLPostInitializationEvent event) {
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChat(ClientChatReceivedEvent event){
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
                    playerEntity.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA + "" + EnumChatFormatting.STRIKETHROUGH + "│───── " + EnumChatFormatting.DARK_AQUA + "" + EnumChatFormatting.WHITE + player + EnumChatFormatting.DARK_AQUA + "" + EnumChatFormatting.STRIKETHROUGH + "─────│"));

                    ChatComponentText statsClickText = new ChatComponentText(EnumChatFormatting.YELLOW + "" + EnumChatFormatting.BOLD + "[STATS]");
                    statsClickText.setChatStyle(Utils.createClickStyle(ClickEvent.Action.RUN_COMMAND, "/jeddungeons " + player));
                    playerEntity.addChatMessage(statsClickText);

                    ChatComponentText kickClickText = new ChatComponentText(EnumChatFormatting.RED + "" + EnumChatFormatting.BOLD + "[KICK]");
                    kickClickText.setChatStyle(Utils.createClickStyle(ClickEvent.Action.RUN_COMMAND, "/party kick " + player));
                    playerEntity.addChatMessage(kickClickText);

                    playerEntity.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA + "" + EnumChatFormatting.STRIKETHROUGH + "│───── " + EnumChatFormatting.DARK_AQUA + "" + EnumChatFormatting.WHITE + "" + EnumChatFormatting.BOLD "[JED]" + EnumChatFormatting.DARK_AQUA + "" + EnumChatFormatting.STRIKETHROUGH + "─────│\n"));
                }
            }
        }

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

    // Delay GUI by 1 tick
    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        if (guiToOpen != null) {
            Minecraft mc = Minecraft.getMinecraft();
            if (guiToOpen.startsWith("jedGui")) {
                int page = Character.getNumericValue(guiToOpen.charAt(guiToOpen.length() - 1));
                mc.displayGuiScreen(new com.portalthree.jed.gui.MainGui(page));
            }
            guiToOpen = null;
        }
    }

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

            if (ToggleCommand.discordRpcToggled && Minecraft.getMinecraft() != null && mc.theWorld != null && player != null && Utils.inDungeons) {
                DiscordRPC.start();
                List<String> scoreboard = ScoreboardHandler.getSidebarLines();
                for (String s : scoreboard) {
                    String sCleaned = ScoreboardHandler.cleanSB(s);
                    if (sCleaned.contains("The Catacombs (")) {
                        if (sCleaned.contains("F1")) {
                            if (player.getHeldItem() != null) {
                                DiscordRPC.update(player.getName() + " is in a F1", "Holding: " + player.getHeldItem().getDisplayName().trim(), "floor_1");
                            } else {
                                DiscordRPC.update(player.getName() + " is in a F1", "https://discord.gg/Cx56wfP8dY", "floor_1");
                            }
                        } else if (sCleaned.contains("F2")) {
                            if (player.getHeldItem() != null) {
                                DiscordRPC.update(player.getName() + " is in a F2", "Holding: " + player.getHeldItem().getDisplayName().trim(), "floor_2");
                            } else {
                                DiscordRPC.update(player.getName() + " is in a F2", "https://discord.gg/Cx56wfP8dY", "floor_2");
                            }
                        } else if (sCleaned.contains("F3")) {
                            if (player.getHeldItem() != null) {
                                DiscordRPC.update(player.getName() + " is in a F3", "Holding: " + player.getHeldItem().getDisplayName().trim(), "floor_3");
                            } else {
                                DiscordRPC.update(player.getName() + " is in a F3", "https://discord.gg/Cx56wfP8dY", "floor_3");
                            }
                        } else if (sCleaned.contains("F4")) {
                            if (player.getHeldItem() != null) {
                                DiscordRPC.update(player.getName() + " is in a F4", "Holding: " + player.getHeldItem().getDisplayName().trim(), "floor_4");
                            } else {
                                DiscordRPC.update(player.getName() + " is in a F4", "https://discord.gg/Cx56wfP8dY", "floor_4");
                            }
                        } else if (sCleaned.contains("F5")) {
                            if (player.getHeldItem() != null) {
                                DiscordRPC.update(player.getName() + " is in a F5", "Holding: " + player.getHeldItem().getDisplayName().trim(), "floor_5");
                            } else {
                                DiscordRPC.update(player.getName() + " is in a F5", "https://discord.gg/Cx56wfP8dY", "floor_5");
                            }
                        } else if (sCleaned.contains("F6")) {
                            if (player.getHeldItem() != null) {
                                DiscordRPC.update(player.getName() + " is in a F6", "Holding: " + player.getHeldItem().getDisplayName().trim(), "floor_6");
                            } else {
                                DiscordRPC.update(player.getName() + " is in a F6", "https://discord.gg/Cx56wfP8dY", "floor_6");
                            }
                        } else if (sCleaned.contains("F7")) {
                            if (player.getHeldItem() != null) {
                                DiscordRPC.update(player.getName() + " is in a F7", "Holding: " + player.getHeldItem().getDisplayName().trim(), "floor_7");
                            } else {
                                DiscordRPC.update(player.getName() + " is in a F7", "https://discord.gg/Cx56wfP8dY", "floor_7");
                            }
                        }
                    }
                }
            } else if (!ToggleCommand.discordRpcToggled || !Utils.inDungeons){
                DiscordRPC.shutdown();
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
        Minecraft mc = Minecraft.getMinecraft();
        float partialTicks = event.partialTicks;

        if (ToggleCommand.mobClearToggled && Utils.inDungeons) {
            List<Entity> entities = Minecraft.getMinecraft().theWorld.getLoadedEntityList();
            for (Entity entity : entities) {
                if (entity.getName().contains("✯")) {
                    Utils.drawNametag(event);
                }
            }
        }

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
        if (ToggleCommand.creeperToggled && drawCreeperLines && !creeperLines.isEmpty()) {
            for (int i = 0; i < creeperLines.size(); i++) {
                Utils.draw3DLine(creeperLines.get(i)[0], creeperLines.get(i)[1], creeperLineColours[i % 10], event.partialTicks);
            }
        }
    }

    @SubscribeEvent
    public void onGuiRender(GuiScreenEvent.BackgroundDrawnEvent event) {
        if (event.gui instanceof GuiChest) {
            GuiChest eventGui = (GuiChest) event.gui;
            ContainerChest cc = (ContainerChest) eventGui.inventorySlots;
            String containerName = cc.getLowerChestInventory().getDisplayName().getUnformattedText();
            List<Slot> invSlots = cc.inventorySlots;
            int chestSize = eventGui.inventorySlots.inventorySlots.size();

            if (ToggleCommand.amongUsSolverToggled && containerName.trim().startsWith("Select all the")) {
                for (Slot slot : invSlots) {
                    ItemStack item = slot.getStack();
                    if (item == null) continue;
                    String name = item.getDisplayName();
                    Matcher matcher = amongUsChestRegex.matcher(containerName);
                    if (matcher.matches()) {
                        String containerNameFound = matcher.group("containerNameFound");
                        if (name.toUpperCase().contains(containerNameFound)) {
                            Utils.drawOnSlot(chestSize, slot.xDisplayPosition, slot.yDisplayPosition, 0xBFF2D249);
                        }
                    }
                }
            } else if (containerName.trim().startsWith("What starts with:'")) {
                for (Slot slot : invSlots) {
                    ItemStack item = slot.getStack();
                    if (item == null) continue;
                    String name = item.getDisplayName();
                    Matcher matcher = amongUsChestRegex2.matcher(containerName);
                    if (matcher.matches()) {
                        String itemFound = matcher.group("itemFound");
                        if (name.toUpperCase().startsWith(itemFound)) {
                            Utils.drawOnSlot(chestSize, slot.xDisplayPosition, slot.yDisplayPosition, 0xBFF2D249);
                        }
                    }
                }
            }
        }
    }
}