package com.portalthree.jed.gui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class DankerGui extends GuiScreen {

	private int page;
	
	private GuiButton closeGUI;
	private GuiButton backPage;
	private GuiButton nextPage;
	private GuiButton githubLink;
	private GuiButton discordLink;
	private GuiButton changeDisplay;
	private GuiButton onlySlayer;
	private GuiButton puzzleSolvers;
	// Toggles

	public DankerGui(int page) {
		this.page = page;
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		int height = sr.getScaledHeight();
		int width = sr.getScaledWidth();
		
		// Default button size is 200, 20
		closeGUI = new GuiButton(0, width / 2 - 100, (int) (height * 0.9), "Close");
		backPage = new GuiButton(0, width / 2 - 100, (int) (height * 0.8), 80, 20, "< Back");
		nextPage = new GuiButton(0, width / 2 + 20, (int) (height * 0.8), 80, 20, "Next >");
		githubLink = new GuiButton(0, 2, height - 50, 80, 20, "GitHub");
		discordLink = new GuiButton(0, 2, height - 30, 80, 20, "Discord");
		
		// Page 1
		puzzleSolvers = new GuiButton(0, width / 2 - 100, (int) (height * 0.3), "Toggle Dungeons Puzzle Solvers");
		
		if (page == 1) {
			this.buttonList.add(puzzleSolvers);
			this.buttonList.add(nextPage);
		} else if (page == 2) {
			this.buttonList.add(nextPage);
			this.buttonList.add(backPage);
		} else if (page == 3) {
			this.buttonList.add(backPage);
		}
		this.buttonList.add(githubLink);
		this.buttonList.add(discordLink);
		this.buttonList.add(closeGUI);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void actionPerformed(GuiButton button) {
		if (button == closeGUI) {
			Minecraft.getMinecraft().thePlayer.closeScreen();
		} else if (button == nextPage) {
			me.Danker.Main.guiToOpen = "dankergui" + (page + 1);
		} else if (button == backPage) {
			me.Danker.Main.guiToOpen = "dankergui" + (page - 1);
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
		} else if (button == changeDisplay) {
			me.Danker.Main.guiToOpen = "displaygui";
		} else if (button == onlySlayer) {
			me.Danker.Main.guiToOpen = "onlyslayergui";
		} else if (button == puzzleSolvers) {
			me.Danker.Main.guiToOpen = "puzzlesolvers";
		}
	}

}
