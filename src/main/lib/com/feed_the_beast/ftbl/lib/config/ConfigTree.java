package com.feed_the_beast.ftbl.lib.config;

import com.feed_the_beast.ftbl.api.config.IConfigKey;
import com.feed_the_beast.ftbl.api.config.IConfigTree;
import com.feed_the_beast.ftbl.api.config.IConfigValue;
import com.feed_the_beast.ftbl.lib.internal.FTBLibIntegrationInternal;
import com.feed_the_beast.ftbl.lib.util.LMNetUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by LatvianModder on 12.09.2016.
 */
public class ConfigTree implements IConfigTree
{
    private static final int HAS_DISPLAY_NAME = 1;
    private static final int HAS_INFO = 2;

    private final Map<IConfigKey, IConfigValue> tree;

    public ConfigTree(boolean linked)
    {
        tree = linked ? new LinkedHashMap<>() : new HashMap<>();
    }

    public ConfigTree()
    {
        this(false);
    }

    @Override
    public final Map<IConfigKey, IConfigValue> getTree()
    {
        return tree;
    }

    @Override
    public IConfigTree copy()
    {
        ConfigTree t = new ConfigTree();
        getTree().forEach((key, value) -> t.add(key, value.copy()));
        return t;
    }

    @Override
    public void writeData(ByteBuf data)
    {
        data.writeShort(tree.size());

        tree.forEach((key, value) ->
        {
            LMNetUtils.writeString(data, key.getID());
            data.writeByte(key.getFlags());

            IConfigValue defValue = key.getDefValue();
            LMNetUtils.writeString(data, defValue.getID());
            defValue.writeData(data);

            byte extraFlags = 0;

            ITextComponent rawDN = key.getRawDisplayName();

            if(rawDN != null)
            {
                extraFlags |= HAS_DISPLAY_NAME;
            }

            String info = key.getInfo();

            if(!info.isEmpty())
            {
                extraFlags |= HAS_INFO;
            }

            data.writeByte(extraFlags);

            if(rawDN != null)
            {
                LMNetUtils.writeTextComponent(data, rawDN);
            }

            if(!info.isEmpty())
            {
                LMNetUtils.writeString(data, info);
            }

            LMNetUtils.writeString(data, value.getID());
            value.writeData(data);
        });
    }

    @Override
    public void readData(ByteBuf data)
    {
        int s = data.readUnsignedShort();
        tree.clear();

        while(--s >= 0)
        {
            String id = LMNetUtils.readString(data);
            byte flags = data.readByte();

            IConfigValue value = FTBLibIntegrationInternal.API.getConfigValueFromID(LMNetUtils.readString(data));
            value.readData(data);

            ConfigKey key = new ConfigKey(id, value);
            key.setFlags(flags);

            byte extraFlags = data.readByte();

            if((extraFlags & HAS_DISPLAY_NAME) != 0)
            {
                key.setDisplayName(LMNetUtils.readTextComponent(data));
            }

            if((extraFlags & HAS_INFO) != 0)
            {
                key.setInfo(LMNetUtils.readString(data));
            }

            value = FTBLibIntegrationInternal.API.getConfigValueFromID(LMNetUtils.readString(data));
            value.readData(data);
            tree.put(key, value);
        }
    }

    @Override
    public void fromJson(JsonElement json)
    {
        JsonObject o = json.getAsJsonObject();
        getTree().forEach((key, value) ->
        {
            if(!key.getFlag(IConfigKey.EXCLUDED))
            {
                JsonElement e = o.get(key.getID());

                if(e != null)
                {
                    value.fromJson(e);
                }
            }
        });
    }

    @Override
    public JsonElement getSerializableElement()
    {
        JsonObject o = new JsonObject();
        tree.forEach((key, value) ->
        {
            if(!key.getFlag(IConfigKey.EXCLUDED))
            {
                o.add(key.getID(), value.getSerializableElement());
            }
        });

        return o;
    }

    @Override
    public NBTBase serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();
        tree.forEach((key, value) -> nbt.setTag(key.getID(), value.serializeNBT()));
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTBase nbt)
    {
        NBTTagCompound configTag = (NBTTagCompound) nbt;
        tree.forEach((key, value) ->
        {
            NBTBase base = configTag.getTag(key.getID());

            if(base != null)
            {
                value.deserializeNBT(base);
            }
        });
    }
}