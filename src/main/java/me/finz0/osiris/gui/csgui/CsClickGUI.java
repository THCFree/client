package me.finz0.osiris.gui.csgui;

import me.finz0.osiris.OsirisMod;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.module.ModuleManager;
import me.finz0.osiris.module.modules.gui.CsClickGuiModule;
import me.finz0.osiris.util.FontUtils;
import me.finz0.osiris.util.Rainbow;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//might finish this sometime idk
public class CsClickGUI extends GuiScreen {
    int x;
    int y;
    int width;
    int height;

    boolean dragging = false;
    int newX;
    int newY;

    List<CategoryButton> categoryButtons;
    List<ModuleButton> moduleButtons;

    public boolean customFont;
    public Module.Category currentCategory;
    public Module currentModule;

    private CsClickGUI instance;

    public CsClickGUI(){
        instance = this;
        categoryButtons = new ArrayList<>();
        moduleButtons = new ArrayList<>();
        updateCategory(Module.Category.COMBAT);
        currentModule = ModuleManager.getModulesInCategory(currentCategory).get(0);
        x = 100;
        y = 25;
        width = 100;
        height = 250;
        customFont = false;
        int buttonX = 0;
        for(Module.Category c : Module.Category.values()){
            CategoryButton button = new CategoryButton(c, x + buttonX, y, this);
            categoryButtons.add(button);
            buttonX += button.width;
            width = buttonX;
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        customFont = ((CsClickGuiModule) ModuleManager.getModuleByName("CsClickGUI")).cfont.getValBoolean();
        if(dragging){
            x = newX + mouseX;
            y = newY + mouseY;
        }

        drawRect(x - 2, y - 4 - FontUtils.getFontHeight(customFont), x + width + 2, y - 2, Rainbow.getInt());
        FontUtils.drawStringWithShadow(customFont, OsirisMod.MODNAME, x, y - 2 - FontUtils.getFontHeight(customFont), 0xffaaaaaa);

        drawRect(x - 2, y - 2, x + width + 2, y + height + 2, Rainbow.getInt());
        drawRect(x, y, x + width, y + height, 0xff222222);

        for(CategoryButton button : categoryButtons){
            button.drawScreen(mouseX, mouseY, partialTicks);
        }

        for(ModuleButton button : moduleButtons){
            button.drawScreen(mouseX, mouseY, partialTicks);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    boolean drag(int mouseX, int mouseY, int mouseButton){
        return mouseButton == 0 && mouseX >= (x - 2) && mouseX <= (x + width + 2) && mouseY >= (y - 4 - FontUtils.getFontHeight(customFont)) && mouseY <= (y - 2);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for(CategoryButton button : categoryButtons){
            if(button.mouseClicked(mouseX, mouseY, mouseButton)) {
                button.mouseClicked(mouseX, mouseY, mouseButton);
                return;
            }
        }
        for(ModuleButton button : moduleButtons){
            if(button.mouseClicked(mouseX, mouseY, mouseButton)){
                button.mouseClicked(mouseX, mouseY, mouseButton);
                return;
            }
        }

        if(drag(mouseX, mouseY, mouseButton)){
            newX = x - mouseX;
            newY = y - mouseY;
            dragging = true;
            return;
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void mouseReleased(int mouseX, int mouseY, int state){
        if (state == 0) dragging = false;
    }

    public boolean doesGuiPauseGame(){
        return false;
    }

    public void updateCategory(Module.Category newCategory){
        currentCategory = newCategory;
        moduleButtons.clear();
        int yy = 0;
        int xx = 0;
        for(Module m : ModuleManager.getModulesInCategory(newCategory)){
            ModuleButton button = new ModuleButton(m, x + xx, y + yy, instance);
            moduleButtons.add(button);
            if(y + yy + button.height > y + height)
                xx += button.width;
            else
                yy += button.height;
        }
}

    private CategoryButton getCButton(Module.Category c){
        return categoryButtons.stream().filter(b -> b.category.equals(c)).findFirst().orElse(null);
    }
}
