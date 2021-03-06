package com.feed_the_beast.ftbl.lib.tile;

import com.feed_the_beast.ftbl.api.tile.IInvMode;
import com.feed_the_beast.ftbl.lib.EnumNameMap;
import com.feed_the_beast.ftbl.lib.LangKey;
import com.feed_the_beast.ftbl.lib.client.ITextureCoordsProvider;
import com.feed_the_beast.ftbl.lib.gui.GuiIcons;

public enum EnumInvMode implements IInvMode
{
    IO,
    IN,
    OUT,
    NONE;

    public static final EnumInvMode[] VALUES = values();
    public static final LangKey ENUM_LANG_KEY = new LangKey("ftbl.invmode");

    private final String name;
    private final LangKey langKey;

    EnumInvMode()
    {
        name = EnumNameMap.createName(this);
        langKey = new LangKey("ftbl.invmode." + name);
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public LangKey getLangKey()
    {
        return langKey;
    }

    @Override
    public ITextureCoordsProvider getIcon()
    {
        switch(this)
        {
            case IO:
                return GuiIcons.INV_IO;
            case IN:
                return GuiIcons.INV_IN;
            case OUT:
                return GuiIcons.INV_OUT;
            default:
                return GuiIcons.INV_NONE;
        }
    }

    @Override
    public boolean canInsert()
    {
        return this == IO || this == IN;
    }

    @Override
    public boolean canExtract()
    {
        return this == IO || this == OUT;
    }
}