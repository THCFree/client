package de.Hero.clickgui.elements.menu;

import de.Hero.clickgui.elements.Element;
import de.Hero.clickgui.elements.ModuleButton;
import de.Hero.clickgui.util.FontUtil;
import me.finz0.osiris.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

//partly skidded from luchadora
public class ElementColorPicker extends Element {
    public ElementColorPicker(ModuleButton iparent, Setting iset) {
        parent = iparent;
        set = iset;
        super.setup();
        pickerResource = new ResourceLocation("osiris:textures/colorpicker.png");

        try {
            InputStream inputStream = Minecraft.getMinecraft().getResourceManager().getResource(pickerResource).getInputStream();
            image = ImageIO.read(inputStream);
        } catch(IOException e){
            e.printStackTrace();
        }

    }

    BufferedImage image;

    ResourceLocation pickerResource;

    int downScale;
    int upScale;


    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Gui.drawRect((int)x, (int)y, (int)x + (int)width, (int)y + (int)height, 0xff1a1a1a);

        FontUtil.drawTotalCenteredString(setstrg, x + width / 2, y + 15/2f, 0xffffffff);

        Gui.drawRect((int)x, (int)y + 14, (int)x + (int)width, (int)y + 15, 0x77000000);
        if (pickerExtended) {
            Gui.drawRect((int)x, (int)y + 15, (int)x + (int)width, (int)y + (int)height, 0xaa121212);
            double ay = y + 15;

            GlStateManager.disableBlend();
            GlStateManager.disableDepth();
            downScale = (int) (width / image.getWidth());
            upScale = (int) (image.getWidth() / width);
            GlStateManager.pushAttrib();
            GlStateManager.scale(downScale, downScale, 1.0f);
            Minecraft.getMinecraft().renderEngine.bindTexture(pickerResource);
            final int xx = (int) Math.round(x * upScale);
            final int yy = (int) Math.round(ay * upScale);
            final int iwidth = (int) Math.round(width * upScale);
            final int iheight = (int) Math.round(height * upScale);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(xx, yy, 0, 0, iwidth, iheight);
            GlStateManager.disableBlend();
            GlStateManager.scale(upScale, upScale, 1.0f);
            GlStateManager.popAttrib();
        }
    }

    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (isButtonHovered(mouseX, mouseY)) {
                pickerExtended = !pickerExtended;
                return true;
            }

            if (!pickerExtended)return false;
            if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
                try {
                    final int xx = (int) Math.round((mouseX - this.x) * upScale);
                    final int yy = (int) Math.round((mouseY - (this.y + 15)) * upScale);
                    set.setValColor(getColorAtPixel(xx, yy));
                } catch(ArrayIndexOutOfBoundsException e){
                    //Command.sendClientMessage(e.toString());
                    return false;
                }
                return true;
            }

        }

        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public boolean isButtonHovered(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + 15;
    }

    private Color getColorAtPixel(int x, int y) {
        return new Color(image.getRGB(x, y));
    }
}
