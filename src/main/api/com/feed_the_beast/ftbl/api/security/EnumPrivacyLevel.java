package com.feed_the_beast.ftbl.api.security;

import com.feed_the_beast.ftbl.lib.EnumNameMap;
import com.feed_the_beast.ftbl.lib.ILangKeyContainer;
import com.feed_the_beast.ftbl.lib.LangKey;
import com.feed_the_beast.ftbl.lib.client.ITextureCoords;
import com.feed_the_beast.ftbl.lib.gui.GuiIcons;
import net.minecraft.util.IStringSerializable;

public enum EnumPrivacyLevel implements IStringSerializable, ILangKeyContainer
{
    PUBLIC,
    PRIVATE,
    TEAM;

    public static final EnumPrivacyLevel[] VALUES = values();
    public static final LangKey ENUM_LANG_KEY = new LangKey("ftbl.privacy");
    public static final EnumNameMap<EnumPrivacyLevel> NAME_MAP = new EnumNameMap<>(EnumPrivacyLevel.VALUES, false);

    private final String name;
    private final LangKey langKey;

    EnumPrivacyLevel()
    {
        name = EnumNameMap.createName(this);
        langKey = new LangKey("ftbl.privacy." + name);
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

    public ITextureCoords getIcon()
    {
        switch(this)
        {
            case PRIVATE:
                return GuiIcons.SECURITY_PRIVATE;
            case TEAM:
                return GuiIcons.SECURITY_TEAM;
            default:
                return GuiIcons.SECURITY_PUBLIC;
        }
    }

    public boolean isPublic()
    {
        return this == PUBLIC;
    }
}