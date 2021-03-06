package com.feed_the_beast.ftbl.api.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

/**
 * Created by LatvianModder on 09.08.2016.
 */
public interface IMaterial extends IStringSerializable
{
    Item getItem();

    default void setItem(Item item)
    {
    }

    int getMetadata();

    default boolean isAdded()
    {
        return true;
    }

    default ItemStack getStack(int size)
    {
        return new ItemStack(getItem(), size, getMetadata());
    }
}