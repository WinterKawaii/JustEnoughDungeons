package com.portalthree.jed;

import com.portalthree.jed.commands.*;
import com.portalthree.jed.handlers.ConfigHandler;
import com.portalthree.jed.handlers.PacketHandler;
import com.portalthree.jed.handlers.events.ChatReceivedHandler;
import com.portalthree.jed.modules.DiscordRPC;
import com.portalthree.jed.modules.HideImplosion;
import com.portalthree.jed.modules.NametagsRenderers;
import com.portalthree.jed.modules.PartyFinderInformation;
import com.portalthree.jed.modules.solvers.AmongUsSolvers;
import com.portalthree.jed.modules.solvers.BlazeSolver;
import com.portalthree.jed.modules.solvers.CreeperSolver;
import com.portalthree.jed.modules.solvers.ThreeManRiddleAndOruo;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod(modid = Main.MODID, version = Main.VERSION, clientSideOnly = true)
public class Main {
    public static final String MODID = "jed";
    public static final String VERSION = "alpha-build";
    public static Main INSTANCE;
    public static int titleTimer = -1;
    public static String titleText = "";
    public static boolean showTitle = false;
    public static String guiToOpen = null;


    @EventHandler
    public void init(FMLInitializationEvent event) {
        FMLCommonHandler.instance().bus().register(this);
        INSTANCE = this; 

        //TODO: To add a new feature, add something here.
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new PacketHandler());
        MinecraftForge.EVENT_BUS.register(new PartyFinderInformation());
        MinecraftForge.EVENT_BUS.register(new ThreeManRiddleAndOruo());
        MinecraftForge.EVENT_BUS.register(new AmongUsSolvers());
        MinecraftForge.EVENT_BUS.register(new NametagsRenderers());
        MinecraftForge.EVENT_BUS.register(new DiscordRPC());
        MinecraftForge.EVENT_BUS.register(new CreeperSolver());
        MinecraftForge.EVENT_BUS.register(new BlazeSolver());
        MinecraftForge.EVENT_BUS.register(new HideImplosion());

        //Event Handlers
        MinecraftForge.EVENT_BUS.register(new ChatReceivedHandler());

        // Commands
        ClientCommandHandler.instance.registerCommand(new ToggleCommand());
        ClientCommandHandler.instance.registerCommand(new ReloadConfigCommand());
        ClientCommandHandler.instance.registerCommand(new DungeonsCommand());
        ClientCommandHandler.instance.registerCommand(new MainGuiCommand());
        ClientCommandHandler.instance.registerCommand(new SetkeyCommand());
        ClientCommandHandler.instance.registerCommand(new HelpCommand());
        ClientCommandHandler.instance.registerCommand(new RepartyCommand());

        ConfigHandler.reloadConfig();
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
}