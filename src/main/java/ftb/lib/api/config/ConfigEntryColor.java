package ftb.lib.api.config;

import com.google.gson.*;
import latmod.lib.LMColor;
import net.minecraft.nbt.NBTTagCompound;

public class ConfigEntryColor extends ConfigEntry
{
	public final LMColor.RGB value;
	public final LMColor.RGB defValue;
	
	public ConfigEntryColor(String id, LMColor def)
	{
		super(id);
		value = new LMColor.RGB();
		value.set(def);
		
		defValue = new LMColor.RGB();
		defValue.set(def);
	}
	
	public ConfigEntryType getConfigType()
	{ return ConfigEntryType.COLOR; }
	
	public int getColor()
	{ return value.color(); }
	
	public void fromJson(JsonElement o)
	{ value.setRGBA(o.getAsInt()); }
	
	public JsonElement getSerializableElement()
	{ return new JsonPrimitive(value.color()); }
	
	public String getAsString()
	{ return value.toString(); }
	
	public int getAsInt()
	{ return value.color(); }
	
	public String getDefValueString()
	{ return defValue.toString(); }
	
	public void writeToNBT(NBTTagCompound tag, boolean extended)
	{
		super.writeToNBT(tag, extended);
		tag.setInteger("V", value.color());
		
		if(extended)
		{
			tag.setInteger("D", defValue.color());
		}
	}
	
	public void readFromNBT(NBTTagCompound tag, boolean extended)
	{
		super.readFromNBT(tag, extended);
		value.setRGBA(tag.getInteger("V"));
		
		if(extended)
		{
			defValue.setRGBA(tag.getInteger("D"));
		}
	}
}