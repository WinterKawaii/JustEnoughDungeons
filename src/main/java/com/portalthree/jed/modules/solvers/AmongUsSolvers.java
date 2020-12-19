package com.portalthree.jed.modules.solvers;

import com.portalthree.jed.commands.ToggleCommand;
import com.portalthree.jed.utils.Utils;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AmongUsSolvers {

    private static final Pattern amongUsChestRegex = Pattern.compile("^Select all the ?(?<containerNameFound>\\w{1,16}) items!");
    private static final Pattern amongUsChestRegex2 = Pattern.compile("^What starts with: '?(?<itemFound>\\w)'\\?");

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
