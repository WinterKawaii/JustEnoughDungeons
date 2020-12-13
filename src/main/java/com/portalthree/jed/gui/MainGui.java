package com.portalthree.jed.gui;

import com.portalthree.jed.commands.ToggleCommand;
import com.portalthree.jed.handlers.ConfigHandler;
import com.portalthree.jed.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MainGui extends GuiScreen {

    ResourceLocation texture = new ResourceLocation("jed:abackground.png");
    private int page;
    private GuiButton closeGUI;
    private GuiButton githubLink;
    private GuiButton discordLink;
    private GuiButton riddle;
    private GuiButton trivia;
    private GuiButton blaze;
    private GuiButton creeper;
    private GuiButton joinInformation;
    private GuiButton mobClear;
    private GuiButton amongUsSolver;
    // Toggles
    private GuiButton discordRpc;

    public MainGui(int page) {
        this.page = page;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        zLevel = -5000;
        //Main Frame
        GlStateManager.pushMatrix();
        float x = (float) width / 1920;
        float y = (float) height / 1080;
        GlStateManager.scale(x * 7.5, y * 4, 0);
        mc.getTextureManager().bindTexture(texture);
        drawTexturedModalRect(0, 0, 0, 0, 1920, 1080);
        GlStateManager.popMatrix();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui() {
        zLevel = 5000;
        super.initGui();

        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        int height = sr.getScaledHeight();
        int width = sr.getScaledWidth();

        // Default button size is 200, 20
        closeGUI = new GuiButton(0, width / 2 - 100, (int) (height * 0.9), "Close");
        githubLink = new GuiButton(0, 2, height - 50, 80, 20, "GitHub");
        discordLink = new GuiButton(0, 2, height - 30, 80, 20, "Discord");


        // Page 1
        GlStateManager.pushMatrix();
        riddle = new GuiButton(0, width / 2 - 100, (int) (height * 0.25), "Riddle Solver: " + Utils.getColouredBoolean(ToggleCommand.threeManToggled));
        trivia = new GuiButton(0, width / 2 - 100, (int) (height * 0.3), "Trivia Solver: " + Utils.getColouredBoolean(ToggleCommand.oruoToggled));
        blaze = new GuiButton(0, width / 2 - 100, (int) (height * 0.35), "Blaze Solver: " + Utils.getColouredBoolean(ToggleCommand.blazeToggled));
        creeper = new GuiButton(0, width / 2 - 100, (int) (height * 0.4), "Creeper Solver : " + Utils.getColouredBoolean(ToggleCommand.creeperToggled));
        joinInformation = new GuiButton(0, width / 2 - 100, (int) (height * 0.45), "Party Finder Join Information : " + Utils.getColouredBoolean(ToggleCommand.joinInformationToggled));
        mobClear = new GuiButton(0, width / 2 - 100, (int) (height * 0.50), "Highlight Starred Mobs : " + Utils.getColouredBoolean(ToggleCommand.mobClearToggled));
        amongUsSolver = new GuiButton(0, width / 2 - 100, (int) (height * 0.60), "Among Us Tasks Solver : " + Utils.getColouredBoolean(ToggleCommand.amongUsSolverToggled));
        discordRpc = new GuiButton(0, width / 2 - 100, (int) (height * 0.65), "Discord RPC (in dungeons) : " + Utils.getColouredBoolean(ToggleCommand.discordRpcToggled));
        GlStateManager.popMatrix();

        this.buttonList.add(riddle);
        this.buttonList.add(trivia);
        this.buttonList.add(blaze);
        this.buttonList.add(creeper);
        this.buttonList.add(joinInformation);
        this.buttonList.add(githubLink);
        this.buttonList.add(discordLink);
        this.buttonList.add(closeGUI);
        this.buttonList.add(mobClear);
        this.buttonList.add(amongUsSolver);
        this.buttonList.add(discordRpc);

    }


    @Override
    public void actionPerformed(GuiButton button) {
        if (button == closeGUI) {
            Minecraft.getMinecraft().thePlayer.closeScreen();
        } else if (button == riddle) {
            ToggleCommand.threeManToggled = !ToggleCommand.threeManToggled;
            ConfigHandler.writeBooleanConfig("toggles", "ThreeManPuzzle", ToggleCommand.threeManToggled);
            riddle.displayString = "Riddle Solver: " + Utils.getColouredBoolean(ToggleCommand.threeManToggled);
        } else if (button == trivia) {
            ToggleCommand.oruoToggled = !ToggleCommand.oruoToggled;
            ConfigHandler.writeBooleanConfig("toggles", "OruoPuzzle", ToggleCommand.oruoToggled);
            trivia.displayString = "Trivia Solver: " + Utils.getColouredBoolean(ToggleCommand.oruoToggled);
        } else if (button == blaze) {
            ToggleCommand.blazeToggled = !ToggleCommand.blazeToggled;
            ConfigHandler.writeBooleanConfig("toggles", "BlazePuzzle", ToggleCommand.blazeToggled);
            blaze.displayString = "Blaze Solver: " + Utils.getColouredBoolean(ToggleCommand.blazeToggled);
        } else if (button == creeper) {
            ToggleCommand.creeperToggled = !ToggleCommand.creeperToggled;
            ConfigHandler.writeBooleanConfig("toggles", "CreeperPuzzle", ToggleCommand.creeperToggled);
            creeper.displayString = "Creeper Solver: " + Utils.getColouredBoolean(ToggleCommand.creeperToggled);
        } else if (button == joinInformation) {
            ToggleCommand.joinInformationToggled = !ToggleCommand.joinInformationToggled;
            ConfigHandler.writeBooleanConfig("toggles", "joinInformation", ToggleCommand.joinInformationToggled);
            joinInformation.displayString = "Party Finder Join Information: " + Utils.getColouredBoolean(ToggleCommand.joinInformationToggled);
        } else if (button == mobClear) {
            ToggleCommand.mobClearToggled = !ToggleCommand.mobClearToggled;
            ConfigHandler.writeBooleanConfig("toggles", "mobClear", ToggleCommand.mobClearToggled);
            mobClear.displayString = "Highlight Starred Mobs : " + Utils.getColouredBoolean(ToggleCommand.mobClearToggled);
        } else if (button == amongUsSolver) {
            ToggleCommand.amongUsSolverToggled = !ToggleCommand.amongUsSolverToggled;
            ConfigHandler.writeBooleanConfig("toggles", "amongUsSolver", ToggleCommand.amongUsSolverToggled);
            amongUsSolver.displayString = "Among Us Tasks Solver : " + Utils.getColouredBoolean(ToggleCommand.amongUsSolverToggled);
        } else if (button == discordRpc) {
            ToggleCommand.discordRpcToggled = !ToggleCommand.discordRpcToggled;
            ConfigHandler.writeBooleanConfig("toggles", "discordRpc", ToggleCommand.discordRpcToggled);
            discordRpc.displayString = "Discord RPC (in dungeons) : " + Utils.getColouredBoolean(ToggleCommand.discordRpcToggled);
        } else if (button == githubLink) {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/portalthree"));
            } catch (IOException | URISyntaxException ex) {
                System.err.println(ex);
            }
        } else if (button == discordLink) {
            try {
                Desktop.getDesktop().browse(new URI("https://discord.gg/Cx56wfP8dY"));
            } catch (IOException | URISyntaxException ex) {
                System.err.println(ex);
            }
        }
    }

}
