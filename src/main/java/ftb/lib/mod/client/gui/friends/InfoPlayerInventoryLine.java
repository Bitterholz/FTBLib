package ftb.lib.mod.client.gui.friends;

import ftb.lib.api.ForgePlayerSP;
import ftb.lib.api.gui.GuiLM;
import ftb.lib.api.info.*;
import ftb.lib.mod.client.gui.info.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.*;

import java.util.List;

/**
 * Created by LatvianModder on 23.03.2016.
 */
@SideOnly(Side.CLIENT)
public class InfoPlayerInventoryLine extends InfoTextLine
{
	public final ForgePlayerSP playerLM;
	
	public InfoPlayerInventoryLine(InfoPage c, ForgePlayerSP p)
	{
		super(c, null);
		playerLM = p;
	}
	
	@SideOnly(Side.CLIENT)
	public ButtonInfoTextLine createWidget(GuiInfo gui)
	{ return new ButtonInfoPlayerInventory(gui, this); }
	
	public class ButtonInfoPlayerInventory extends ButtonInfoTextLine
	{
		public ButtonInfoPlayerInventory(GuiInfo g, InfoPlayerInventoryLine w)
		{
			super(g, null);
			width = 18 * 9;
			height = 18 * 4 + 4;
		}
		
		public void addMouseOverText(List<String> l)
		{
		}
		
		public void onButtonPressed(int b)
		{
		}
		
		public void renderWidget()
		{
			int ay = getAY();
			if(ay < -height || ay > guiInfo.mainPanel.height) return;
			int ax = getAX();
			float z = gui.getZLevel();
			
			GlStateManager.color(1F, 1F, 1F, 0.2F);
			GuiLM.drawBlankRect(ax, ay, z, width, height);
			
			for(int i = 0; i < 9 * 3; i++)
			{
				GuiLM.drawBlankRect(ax + (i % 9) * 18 + 1, ay + (i / 9) * 18 + 1, z, 16, 16);
			}
			
			for(int i = 0; i < 9; i++)
			{
				GuiLM.drawBlankRect(ax + i * 18 + 1, ay + 18 * 3 + 5, z, 16, 16);
			}
			
			GlStateManager.color(1F, 1F, 1F, 1F);
			
			EntityPlayer ep = playerLM.getPlayer();
			
			if(ep != null)
			{
				for(int i = 0; i < ep.inventory.mainInventory.length - 9; i++)
				{
					GuiLM.drawItem(gui, ep.inventory.mainInventory[i + 9], ax + (i % 9) * 18 + 1, ay + (i / 9) * 18 + 1);
				}
				
				for(int i = 0; i < 9; i++)
				{
					GuiLM.drawItem(gui, ep.inventory.mainInventory[i], ax + i * 18 + 1, ay + 18 * 3 + 5);
				}
			}
		}
	}
}
