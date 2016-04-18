package ftb.lib.api.info;

import com.google.gson.*;
import ftb.lib.mod.client.gui.info.*;
import net.minecraft.util.IJsonSerializable;
import net.minecraft.util.text.*;
import net.minecraftforge.fml.relauncher.*;

/**
 * Created by LatvianModder on 20.03.2016.
 */
public class InfoTextLine implements IJsonSerializable
{
	public static InfoTextLine get(InfoPage c, JsonElement e)
	{
		if(e == null || e.isJsonNull()) return null;
		else if(e.isJsonPrimitive())
		{
			String s = e.getAsString();
			return s.trim().isEmpty() ? null : new InfoTextLine(c, s);
		}
		else
		{
			JsonObject o = e.getAsJsonObject();
			
			InfoExtendedTextLine l;
			
			if(o.has("image"))
			{
				l = new InfoImageLine(c);
			}
			else
			{
				l = new InfoExtendedTextLine(c, null);
			}
			
			l.fromJson(o);
			return l;
		}
	}
	
	public final InfoPage page;
	private String text;
	
	public InfoTextLine(InfoPage c, String s)
	{
		page = c;
		text = s;
	}
	
	public ITextComponent getText()
	{ return new TextComponentString(text); }
	
	@SideOnly(Side.CLIENT)
	public ButtonInfoTextLine createWidget(GuiInfo gui)
	{ return new ButtonInfoTextLine(gui, this); }
	
	public void fromJson(JsonElement e)
	{ text = e.getAsString(); }
	
	public JsonElement getSerializableElement()
	{ return new JsonPrimitive(text); }
}