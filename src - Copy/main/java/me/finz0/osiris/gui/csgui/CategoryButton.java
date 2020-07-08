package me.finz0.osiris.gui.csgui;

import me.finz0.osiris.command.Command;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.util.FontUtils;
import me.finz0.osiris.util.Rainbow;
import net.minecraft.client.gui.Gui;

public class CategoryButton {
    int x;
    int y;
    int width;
    int height;
    CsClickGUI gui;
    Module.Category category;

    int newX;
    int newY;

    public CategoryButton(Module.Category c, int iX, int iY, CsClickGUI csClickGUI){
        x= iX;
        y = iY;
        gui = csClickGUI;
        category = c;
        width = FontUtils.getStringWidth(gui.customFont, category.name()) + 4;
        height = FontUtils.getFontHeight(gui.customFont) + 6;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        if(gui.dragging){
            x = newX + mouseX;
            y = newY + mouseY;
        }
        width = FontUtils.getStringWidth(gui.customFont, category.name()) + 2;
        height = FontUtils.getFontHeight(gui.customFont) + 2;
        Gui.drawRect(x, y, x + width + 2, y + height + 2, 0xff444444);
        Gui.drawRect(x + 1, y + 1, (x + 1) + (width - 1), (y + 1) + (height - 1), 0xff222222);
        FontUtils.drawStringWithShadow(gui.customFont, category.name(), x + 2, y + 2, Rainbow.getInt());
    }

    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton){
        if(mouseButton == 0 && mouseX > x && mouseX < width && mouseY > y && mouseY < height){
            gui.updateCategory(category);
            Command.sendClientMessage("Clicked category");
            return true;
        }

        if(gui.drag(mouseX, mouseY, mouseButton)){
            newX = x - mouseX;
            newY = y - mouseY;
        }

        return false;
    }
}
