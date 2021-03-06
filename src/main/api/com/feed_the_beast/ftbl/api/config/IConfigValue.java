package com.feed_the_beast.ftbl.api.config;

import com.feed_the_beast.ftbl.api.gui.IMouseButton;
import com.feed_the_beast.ftbl.lib.io.IExtendedIOObject;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.IJsonSerializable;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

/**
 * Created by LatvianModder on 11.09.2016.
 */
public interface IConfigValue extends IExtendedIOObject, INBTSerializable<NBTBase>, IJsonSerializable
{
    String getID();

    @Nullable
    Object getValue();

    String getString();

    boolean getBoolean();

    int getInt();

    default double getDouble()
    {
        return getInt();
    }

    default boolean containsInt(int val)
    {
        return getInt() == val;
    }

    IConfigValue copy();

    default boolean equalsValue(IConfigValue value)
    {
        return Objects.equals(getValue(), value.getValue());
    }

    default int getColor()
    {
        return 0x999999;
    }

    @Nullable
    default String getMinValueString()
    {
        return null;
    }

    @Nullable
    default String getMaxValueString()
    {
        return null;
    }

    @Nullable
    default List<String> getVariants()
    {
        return null;
    }

    void onClicked(IGuiEditConfig gui, IConfigKey key, IMouseButton button);

    default boolean canParse(String text)
    {
        return true;
    }

    default boolean isNull()
    {
        return false;
    }
}