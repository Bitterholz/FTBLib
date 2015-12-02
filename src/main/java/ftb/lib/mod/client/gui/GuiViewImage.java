package ftb.lib.mod.client.gui;

import cpw.mods.fml.relauncher.*;
import ftb.lib.client.TextureCoords;
import ftb.lib.gui.GuiLM;
import ftb.lib.gui.widgets.ButtonLM;
import net.minecraft.client.gui.GuiScreen;

@SideOnly(Side.CLIENT)
public class GuiViewImage extends GuiLM
{
	public final GuiScreen parent;
	public final TextureCoords texCoords;
	public final ButtonLM buttonClose;
	
	public GuiViewImage(GuiScreen p, TextureCoords t)
	{
		super(null, null);
		parent = p;
		texCoords = t;
		hideNEI = true;
		
		buttonClose = new ButtonLM(this, 0, 0, 0, 0)
		{
			public void onButtonPressed(int b)
			{ mc.displayGuiScreen(parent); }
		};
	}
	
	public void initLMGui()
	{
		xSize = width;
		ySize = height;
		buttonClose.width = width;
		buttonClose.height = height;
	}
	
	public void addWidgets()
	{
		mainPanel.add(buttonClose);
	}
	
	public void drawBackground()
	{
		super.drawBackground();
		
		if(texCoords != null && texCoords.isValid())
		{
			setTexture(texCoords.texture);
			
			double w = texCoords.width;
			double h = texCoords.height;
			
			if(w > width)
			{
				w = width;
				h = texCoords.getHeight(w);
			}
			
			if(h > height)
			{
				h = height;
				w = texCoords.getWidth(h);
			}
			
			render(texCoords, (width - w) / 2, (height - h) / 2, w, h);
		}
	}
}