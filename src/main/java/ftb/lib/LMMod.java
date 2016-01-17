package ftb.lib;

import ftb.lib.api.block.IBlockLM;
import ftb.lib.api.item.IItemLM;
import ftb.lib.mod.*;
import ftb.lib.recipes.LMRecipes;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.relauncher.*;
import org.apache.logging.log4j.*;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.*;

public class LMMod
{
	public static final HashMap<String, LMMod> modsMap = new HashMap<>();
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public static @interface Instance
	{
		String value();
	}
	
	private static LMMod getLMMod(Object o)
	{
		if(o == null) return null;
		
		try
		{
			Field[] fields = o.getClass().getDeclaredFields();
			
			for(Field f : fields)
			{
				if(f.isAnnotationPresent(LMMod.Instance.class))
				{
					LMMod.Instance m = f.getAnnotation(LMMod.Instance.class);
					
					if(m.value() != null)
					{
						LMMod mod = new LMMod(m.value());
						f.set(o, mod);
						return mod;
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void init(Object o)
	{
		LMMod mod = getLMMod(o);
		if(mod == null)
		{
			FTBLib.logger.warn("LMMod failed to load from " + o);
			return;
		}
		modsMap.put(mod.modID, mod);
		if(FTBLibFinals.DEV) FTBLib.logger.info("LMMod '" + mod.toString() + "' loaded");
	}
	
	// End of static //
	
	public final String modID;
	public final String lowerCaseModID;
	public final String assets;
	private ModContainer modContainer;
	public final List<IBlockLM> blocks;
	public final List<IItemLM> items;
	
	public Logger logger;
	public LMRecipes recipes;
	
	public LMMod(String id)
	{
		modID = id;
		lowerCaseModID = modID.toLowerCase();
		assets = lowerCaseModID + ":";
		blocks = new ArrayList<>();
		items = new ArrayList<>();
		
		logger = LogManager.getLogger(modID);
		recipes = LMRecipes.defaultInstance;
	}
	
	public ModContainer getModContainer()
	{
		if(modContainer == null) modContainer = Loader.instance().getModObjectList().inverse().get(modID);
		return modContainer;
	}
	
	public void setRecipes(LMRecipes r)
	{ recipes = (r == null) ? new LMRecipes() : r; }
	
	public String toFullString()
	{ return modID + '-' + Loader.MC_VERSION + '-' + modContainer.getDisplayVersion(); }
	
	public String toString()
	{ return modID; }
	
	public ResourceLocation getLocation(String s)
	{ return new ResourceLocation(lowerCaseModID, s); }
	
	public CreativeTabs createTab(final String s, final ItemStack icon)
	{
		CreativeTabs tab = new CreativeTabs(assets + s)
		{
			@SideOnly(Side.CLIENT)
			public ItemStack getIconItemStack()
			{ return icon; }
			
			@SideOnly(Side.CLIENT)
			public Item getTabIconItem()
			{ return getIconItemStack().getItem(); }
		};
		
		return tab;
	}
	
	public String getBlockName(String s)
	{ return assets + "tile." + s; }
	
	public String getItemName(String s)
	{ return assets + "item." + s; }
	
	public String translate(String s, Object... args)
	{ return FTBLibMod.proxy.translate(assets + s, args); }
	
	public void addItem(IItemLM i)
	{
		FTBLib.addItem((Item) i, i.getItemID());
		items.add(i);
	}
	
	public void addBlock(IBlockLM b)
	{
		FTBLib.addBlock((Block) b, b.getItemBlock(), b.getItemID());
		blocks.add(b);
	}
	
	//public void addTile(Class<? extends TileLM> c, String s, String... alt)
	//{ FTBLib.addTileEntity(c, modID + '.' + s, alt); }
	
	public void addEntity(Class<? extends Entity> c, String s, int id)
	{ FTBLib.addEntity(c, s, id, modID); }
	
	public void onPostLoaded()
	{
		for(int i = 0; i < items.size(); i++)
			items.get(i).onPostLoaded();
		
		for(int i = 0; i < blocks.size(); i++)
			blocks.get(i).onPostLoaded();
	}
	
	public void loadRecipes()
	{
		for(int i = 0; i < items.size(); i++)
			items.get(i).loadRecipes();
		
		for(int i = 0; i < blocks.size(); i++)
			blocks.get(i).loadRecipes();
		
		if(recipes != null) recipes.loadRecipes();
	}
}