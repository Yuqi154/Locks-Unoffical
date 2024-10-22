package melonslise.locks.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import melonslise.locks.Locks;
import melonslise.locks.common.container.LockPickingContainer;
import net.minecraft.client.Options;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class LockPickingScreen extends AbstractContainerScreen<LockPickingContainer> {
    public static final Component HINT = Component.literal(Locks.ID + ".gui.lockpicking.open");
    private final int length;
    public ResourceLocation lockText;
    public ResourceLocation pickText;
    public int pickX, pickY;

    public LockPickingScreen(LockPickingContainer cont, Inventory inv, Component title) {
        super(cont, inv, title);
        this.pickY = 80;
        this.length = cont.lockable.lock.getLength();
        this.reset(cont.lockable.stack, cont.player.getMainHandItem());
    }

    public static ResourceLocation getTextureFor(ItemStack stack) {
        return new ResourceLocation(Locks.ID, "textures/gui/" + ForgeRegistries.ITEMS.getKey(stack.getItem()).getPath() + ".png");
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int cornerX = (this.width - this.imageWidth) / 4;
        int cornerY = (this.height - this.imageHeight) / 3;
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        poseStack.scale(2f, 2f, 2f);
        guiGraphics.blit(this.lockText, cornerX, cornerY, 6, 0, 3, 42, 48, 80);
        for (int i = 0; i < this.length; i++){
            //guiGraphics.blit(this.lockText, cornerX, cornerY, 0, 0, 160, 16, 48, 80);
        }
        guiGraphics.blit(this.pickText, pickX, pickY, 0, 0, 160, 16, 160, 16);
        poseStack.popPose();
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int pMouseX, int pMouseY) {
        guiGraphics.drawString(this.font, this.title, 0, -this.font.lineHeight, 0xffffff);
        if (this.getMenu().isOpen())
            guiGraphics.drawString(this.font, HINT, (this.imageWidth - this.font.width(HINT)) / 2, this.imageHeight + 10, 0xffffff);
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean keyPressed(int key, int scan, int modifier) {
        Options options = this.minecraft.options;
        if (options.keyLeft.getKey().getValue() == key) this.pickX -= 3;
        if (options.keyRight.getKey().getValue() == key) this.pickX += 3;
        return super.keyPressed(key, scan, modifier);
    }

    public void reset(ItemStack lockStack, ItemStack pickStack) {
        this.lockText = getTextureFor(lockStack);
        this.pickText = getTextureFor(pickStack);
    }
}