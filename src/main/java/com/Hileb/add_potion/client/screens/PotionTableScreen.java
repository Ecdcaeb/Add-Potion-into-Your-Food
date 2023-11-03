package com.Hileb.add_potion.client.screens;

import com.Hileb.add_potion.common.gui.PotionTableMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;

import static com.Hileb.add_potion.AddPotion.MODID;

public class PotionTableScreen extends AbstractContainerScreen<PotionTableMenu> {
	private static final int BUTTON_WIDTH = 24;
	private static final int BUTTON_HEIGHT = 14;
	private static final int BUTTON_X = 72;
	private static final int BUTTON_Y = 30;

	private static final ResourceLocation BG_LOCATION = new ResourceLocation(MODID, "textures/gui/gui_potion.png");

	private boolean clicked = false;

	public PotionTableScreen(PotionTableMenu menu, Inventory inventory, Component component) {
		super(menu, inventory, component);
		--this.titleLabelY;
	}

	@Override
	public void render(PoseStack transform, int x, int y, float partialTicks) {
		super.render(transform, x, y, partialTicks);
		this.renderTooltip(transform, x, y);
	}

	@Override
	protected void renderBg(PoseStack transform, float partialTicks, int x, int y) {
		this.renderBackground(transform);
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		RenderSystem.setShaderTexture(0, BG_LOCATION);
		this.blit(transform, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
		int buttonX = this.leftPos + BUTTON_X;
		int buttonY = this.topPos + BUTTON_Y;
		this.renderButtons(transform, x, y, buttonX, buttonY);
	}

	@Override
	public boolean mouseClicked(double x, double y, int button) {
		int buttonX = this.leftPos + BUTTON_X;
		int buttonY = this.topPos + BUTTON_Y;
		double diffX = x - buttonX;
		double diffY = y - buttonY;
		if(diffX >= 0 && diffY >= 0 && diffX < BUTTON_WIDTH && diffY < BUTTON_HEIGHT) {
			this.clicked = true;
			return true;
		}

		return super.mouseClicked(x, y, button);
	}

	@SuppressWarnings("ConstantConditions")
	@Override
	public boolean mouseReleased(double x, double y, int button) {
		int buttonX = this.leftPos + BUTTON_X;
		int buttonY = this.topPos + BUTTON_Y;
		double diffX = x - buttonX;
		double diffY = y - buttonY;
		if(diffX >= 0 && diffY >= 0 && diffX < BUTTON_WIDTH && diffY < BUTTON_HEIGHT && this.clicked && this.menu.clickMenuButton(this.minecraft.player, 0)) {
			this.clicked = false;
			Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0F));
			this.minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, 0);
			return true;
		}

		this.clicked = false;
		return super.mouseReleased(x, y, button);
	}

	private void renderButtons(PoseStack transform, int x, int y, int buttonX, int buttonY) {
		int i = 0;
		if(this.clicked) {
			i = 2;
		} else {
			int diffX = x - buttonX;
			int diffY = y - buttonY;
			if (diffX >= 0 && diffY >= 0 && diffX < BUTTON_WIDTH && diffY < BUTTON_HEIGHT) {
				i = 1;
			}
		}
		this.blit(transform, buttonX, buttonY, 0, this.imageHeight + i * BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);
	}
}
