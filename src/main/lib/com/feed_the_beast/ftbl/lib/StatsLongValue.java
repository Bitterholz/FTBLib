package com.feed_the_beast.ftbl.lib;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import net.minecraft.util.IJsonSerializable;

/**
 * Created by LatvianModder on 04.07.2016.
 */
public class StatsLongValue implements IJsonSerializable
{
    private long value;

    public StatsLongValue(long l)
    {
        set(l);
    }

    public void set(long v)
    {
        value = v;
    }

    public long get()
    {
        return value;
    }

    @Override
    public void fromJson(JsonElement json)
    {
        set(json.getAsLong());
    }

    @Override
    public JsonElement getSerializableElement()
    {
        return new JsonPrimitive(get());
    }
}