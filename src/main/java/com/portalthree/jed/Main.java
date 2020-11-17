package me.Danker;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import com.portalthree.jed.Utils;
import com.portalthree.jed.commands.*;
import com.portalthree.jed.handlers.APIHandler;
import com.portalthree.jed.handlers.ConfigHandler;
import com.portalthree.jed.handlers.ScoreboardHandler;
import com.portalthree.jed.handlers.PacketHandler;

import com.google.gson.JsonObject;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEvent.Action;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.versioning.DefaultArtifactVersion;


@Mod(modid = me.Danker.Main.MODID, version = me.Danker.Main.VERSION, clientSideOnly = true)
public class Main
{
    public static final String MODID = "jed";
    public static final String VERSION = "1.0";

    static double checkItemsNow = 0;
    static double itemsChecked = 0;
    public static Map<String, String> t6Enchants = new HashMap<String, String>();
    public static Pattern pattern = Pattern.compile("");
    static boolean updateChecked = false;
    public static int titleTimer = -1;
    public static boolean showTitle = false;
    public static String titleText = "";
    public static int SKILL_TIME;
    public static int skillTimer = -1;
    public static boolean showSkill = false;
    public static String skillText = "";
    static int tickAmount = 1;
    static KeyBinding[] keyBindings = new KeyBinding[1];
    static int lastMouse = -1;
    static boolean usingLabymod = false;
    public static String guiToOpen = null;
    static String[] riddleSolutions = {"The reward is not in my chest!", "At least one of them is lying, and the reward is not in",
            "My chest doesn't have the reward. We are all telling the truth", "My chest has the reward and I'm telling the truth",
            "The reward isn't in any of our chests", "Both of them are telling the truth."};
    static Map<String, String> triviaSolutions = new HashMap<String, String>();
    static Entity highestBlaze = null;
    static Entity lowestBlaze = null;
    // Among Us colours
    static int[] creeperLineColours = {0x50EF39, 0xC51111, 0x132ED1, 0x117F2D, 0xED54BA, 0xEF7D0D, 0xF5F557, 0xD6E0F0, 0x6B2FBB, 0x39FEDC};
    static boolean drawCreeperLines = false;
    static Vec3 creeperLocation = new Vec3(0, 0, 0);
    static List<Vec3[]> creeperLines = new ArrayList<Vec3[]>();
    static boolean foundLivid = false;
    static Entity livid = null;


    @EventHandler
    public void init(FMLInitializationEvent event) {
        FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new PacketHandler());

        final ConfigHandler cf = new ConfigHandler();
        cf.reloadConfig();


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
        ClientCommandHandler.instance.registerCommand(new ToggleCommand());
        ClientCommandHandler.instance.registerCommand(new ReloadConfigCommand());
        ClientCommandHandler.instance.registerCommand(new DungeonsCommand());
        ClientCommandHandler.instance.registerCommand(new MainGuiCommand());
    }

    @EventHandler
    public void postInit(final FMLPostInitializationEvent event) {
        usingLabymod = Loader.isModLoaded("labymod");
        System.out.println("LabyMod detection: " + usingLabymod);
    }

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        foundLivid = false;
        livid = null;
    }

    // It randomly broke, so I had to make it the highest priority
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChat(ClientChatReceivedEvent event) {
        final ToggleCommand tc = new ToggleCommand();
        String message = event.message.getUnformattedText();

        if (!Utils.inSkyblock) return;

        // Action Bar
        if (event.type == 2) {
            String[] actionBarSections = event.message.getUnformattedText().split(" {3,}");
            for (String section : actionBarSections) {
                if (tc.skill50DisplayToggled) {
                    if (section.contains("+") && section.contains("/") && section.contains("(")) {
                        if (section.contains("Runecrafting")) return;

                        String xpGained = section.substring(section.indexOf("+"), section.indexOf("(") - 1);
                        double currentXp = Double.parseDouble(section.substring(section.indexOf("(") + 1, section.indexOf("/")).replaceAll(",", ""));
                        int previousXp = Utils.getPastXpEarned(Integer.parseInt(section.substring(section.indexOf("/") + 1, section.indexOf(")")).replaceAll(",", "")));
                        double percentage = (double) Math.floor(((currentXp + previousXp) / 55172425) * 10000D) / 100D;

                        skillTimer = SKILL_TIME;
                        showSkill = true;
                        skillText = EnumChatFormatting.AQUA + xpGained + " (" + NumberFormat.getNumberInstance(Locale.US).format(currentXp + previousXp) + "/55,172,425) " + percentage + "%";
                    }
                }
            }
            return;
        }


        // Dungeon chat spoken by an NPC, containing :
        if (ToggleCommand.threeManToggled && Utils.inDungeons && message.contains("[NPC]")) {
            for (String solution : riddleSolutions) {
                if (message.contains(solution)) {
                    String npcName = message.substring(message.indexOf("]") + 2, message.indexOf(":"));
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_GREEN + "" + EnumChatFormatting.BOLD + npcName + EnumChatFormatting.GREEN + " has the blessing."));
                    break;
                }
            }
        }


        if (message.contains(":")) return;

        if (ToggleCommand.oruoToggled && Utils.inDungeons) {
            for (String question : triviaSolutions.keySet()) {
                if (message.contains(question)) {
                    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Answer: " + EnumChatFormatting.DARK_GREEN + EnumChatFormatting.BOLD + triviaSolutions.get(question)));
                    break;
                }
            }
        }


        // Spirit Bear alerts
        if (tc.spiritBearAlerts && message.contains("The Spirit Bear has appeared!")) {
            Utils.createTitle(EnumChatFormatting.DARK_PURPLE + "SPIRIT BEAR", 2);
        }

        // Spirit Sceptre
        if (!tc.sceptreMessages && message.contains("Your Spirit Sceptre hit ")) {
            event.setCanceled(true);
        }
        // Midas Staff
        if (!tc.midasStaffMessages && message.contains("Your Molten Wave hit ")) {
            event.setCanceled(true);
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

            if (DisplayCommand.auto && mc != null && mc.theWorld != null) {
                List<String> scoreboard = ScoreboardHandler.getSidebarLines();
                boolean found = false;
                for (String s : scoreboard) {
                    String sCleaned = ScoreboardHandler.cleanSB(s);
                    if (sCleaned.contains("Sven Packmaster")) {
                        DisplayCommand.display = "wolf";
                        found = true;
                    } else if (sCleaned.contains("Tarantula Broodfather")) {
                        DisplayCommand.display = "spider";
                        found = true;
                    } else if (sCleaned.contains("Revenant Horror")) {
                        DisplayCommand.display = "zombie";
                        found = true;
                    } else if (sCleaned.contains("The Catacombs (")) {
                        if (sCleaned.contains("F1")) {
                            DisplayCommand.display = "catacombs_floor_one";
                        } else if (sCleaned.contains("F2")) {
                            DisplayCommand.display = "catacombs_floor_two";
                        } else if (sCleaned.contains("F3")) {
                            DisplayCommand.display = "catacombs_floor_three";
                        } else if (sCleaned.contains("F4")) {
                            DisplayCommand.display = "catacombs_floor_four";
                        } else if (sCleaned.contains("F5")) {
                            DisplayCommand.display = "catacombs_floor_five";
                        } else if (sCleaned.contains("F6")) {
                            DisplayCommand.display = "catacombs_floor_six";
                        }
                        found = true;
                    }
                }
                if (!found) DisplayCommand.display = "off";
                ConfigHandler.writeStringConfig("misc", "display", DisplayCommand.display);
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
                    // Search for nearby sea lanterns and prismarine blocks
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
                                Vec3[] insertArray = {startBlock, new Vec3(endBlock.getX() + 0.5, endBlock.getY() + 0.5, endBlock.getZ() + 0.5)};
                                creeperLines.add(insertArray);
                            }
                        }
                    }
                } else {
                    drawCreeperLines = false;
                }
            }

            if (ToggleCommand.lividSolverToggled && Utils.inDungeons && !foundLivid && mc.theWorld != null) {
                boolean inF5 = false;

                List<String> scoreboard = ScoreboardHandler.getSidebarLines();
                for (String s : scoreboard) {
                    String sCleaned = ScoreboardHandler.cleanSB(s);
                    if (sCleaned.contains("The Catacombs (F5)")) {
                        inF5 = true;
                        break;
                    }
                }

                if (inF5) {
                    List<Entity> loadedLivids = new ArrayList<Entity>();
                    List<Entity> entities = mc.theWorld.getLoadedEntityList();
                    for (Entity entity : entities) {
                        String name = entity.getName();
                        if (name.contains("Livid") && name.length() > 5 && name.charAt(1) == name.charAt(5) && !loadedLivids.contains(entity)) {
                            loadedLivids.add(entity);
                        }
                    }
                    if (loadedLivids.size() > 8) {
                        livid = loadedLivids.get(0);
                        foundLivid = true;
                    }
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

    // Delay GUI by 1 tick
    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        if (guiToOpen != null) {
            Minecraft mc = Minecraft.getMinecraft();
            if (guiToOpen.startsWith("dankergui")) {
                int page = Character.getNumericValue(guiToOpen.charAt(guiToOpen.length() - 1));
                mc.displayGuiScreen(new com.portalthree.jed.gui.MainGui(page));
            } else if (guiToOpen.equals("displaygui")) {
                mc.displayGuiScreen(new me.Danker.gui.DisplayGui());
            } else if (guiToOpen.equals("puzzlesolvers")) {
                mc.displayGuiScreen(new me.Danker.gui.PuzzleSolversGui());
            }
            guiToOpen = null;
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
        if (ToggleCommand.creeperToggled && drawCreeperLines && !creeperLines.isEmpty()) {
            for (int i = 0; i < creeperLines.size(); i++) {
                Utils.draw3DLine(creeperLines.get(i)[0], creeperLines.get(i)[1], creeperLineColours[i % 10], event.partialTicks);
            }
        }
    }

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent event) {
        if (!Utils.inSkyblock || Minecraft.getMinecraft().thePlayer != event.entityPlayer) return;
        ItemStack item = event.entityPlayer.getHeldItem();
        if (item == null) return;

        if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR) {
            if (ToggleCommand.aotdToggled && item.getDisplayName().contains("Aspect of the Dragons")) {
                event.setCanceled(true);
            }
            if (ToggleCommand.lividDaggerToggled && item.getDisplayName().contains("Livid Dagger")) {
                event.setCanceled(true);
            }
        }
    }

}
