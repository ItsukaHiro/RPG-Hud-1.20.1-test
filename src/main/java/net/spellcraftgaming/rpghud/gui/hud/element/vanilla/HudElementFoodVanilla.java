package net.spellcraftgaming.rpghud.gui.hud.element.vanilla;

import java.util.Random;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.util.FoodStats;
import net.minecraftforge.client.GuiIngameForge;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElement;
import net.spellcraftgaming.rpghud.gui.hud.element.HudElementType;
import net.spellcraftgaming.rpghud.gui.override.GuiIngameRPGHud;

public class HudElementFoodVanilla extends HudElement {

    protected Random rand = new Random();

    public HudElementFoodVanilla() {
        super(HudElementType.FOOD, 0, 0, 0, 0, true);
    }

    @Override
    public boolean checkConditions() {
        return !this.mc.gameSettings.hideGUI && !(this.mc.player.getRidingEntity() instanceof EntityLiving) && this.mc.playerController.shouldDrawHUD();
    }

    @Override
    public void drawElement(Gui gui, float zLevel, float partialTicks, int scaledWidth, int scaledHeight) {
        GuiIngameRPGHud theGui = ((GuiIngameRPGHud) gui);
        EntityPlayer player = (EntityPlayer) this.mc.getRenderViewEntity();
        GlStateManager.enableBlend();
        int left = scaledWidth / 2 + 91;
        int top = scaledHeight - GuiIngameForge.right_height;
        GuiIngameForge.right_height += 10;
        boolean unused = false;// Unused flag in vanilla, seems to be part of a 'fade out' mechanic

        FoodStats stats = this.mc.player.getFoodStats();
        int level = stats.getFoodLevel();

        for(int i = 0; i < 10; ++i) {
            int idx = i * 2 + 1;
            int x = left - i * 8 - 9;
            int y = top;
            int icon = 16;
            byte background = 0;

            if(this.mc.player.isPotionActive(MobEffects.HUNGER)) {
                icon += 36;
                background = 13;
            }
            if(unused)
                background = 1; // Probably should be a += 1 but vanilla never uses this

            if(player.getFoodStats().getSaturationLevel() <= 0.0F && theGui.getTicks() % (level * 3 + 1) == 0)
                y = top + (this.rand.nextInt(3) - 1);

            gui.drawTexturedModalRect(x, y, 16 + background * 9, 27, 9, 9);

            if(idx < level)
                gui.drawTexturedModalRect(x, y, icon + 36, 27, 9, 9);
            else if(idx == level)
                gui.drawTexturedModalRect(x, y, icon + 45, 27, 9, 9);
        }
    }

}
