package me.finz0.osiris.gui.csgui;

import me.finz0.osiris.command.Command;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.util.FontUtils;
import me.finz0.osiris.util.Rainbow;
import net.minecraft.client.gui.Gui;

public class ModuleButton {
    int x;
    int y;
    int width;
    int height;
    Module module;
    CsClickGUI gui;

    int newX;
    int newY;

    public ModuleButton(Module mod, int iX, int iY, CsClickGUI csClickGUI){
        gui = csClickGUI;
        module = mod;
        x = iX;
        y = iY;
        width = 100;
        height = 25;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        if(gui.dragging){
            x = newX + mouseX;
            y = newY + mouseY;
        }
        Gui.drawRect(x, y, x +  width, y +  height, Rainbow.getInt());
        Gui.drawRect(x + 2, y + 2, (x + 2) + (width - 2), (y + 2) + (height - 2), 0xff222222);
        FontUtils.drawStringWithShadow(gui.customFont, module.getName(), x + 2, y + 2, gui.currentModule.equals(module) ? Rainbow.getInt() : 0xffffffff);
    }

    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton){
        if(mouseButton == 0 && mouseX > x && mouseX < width && mouseY > y && mouseY < height){
            gui.currentModule = module;
            Command.sendClientMessage("Clicked module");
            return true;
        }

        if(gui.drag(mouseX, mouseY, mouseButton)){
            newX = x - mouseX;
            newY = y - mouseY;
        }

        return false;
    }
}
