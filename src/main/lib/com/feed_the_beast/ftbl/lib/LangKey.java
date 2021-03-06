package com.feed_the_beast.ftbl.lib;

import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by LatvianModder on 17.04.2016.
 */
public final class LangKey implements ILangKeyContainer
{
    private final String key;

    public LangKey(String s)
    {
        key = s;
    }

    @SideOnly(Side.CLIENT)
    public String translate(Object... o)
    {
        return I18n.format(key, o);
    }

    public ITextComponent textComponent(Object... o)
    {
        return new TextComponentTranslation(key, o);
    }

    public void printChat(ICommandSender ics, Object... o)
    {
        ics.addChatMessage(textComponent(o));
    }

    public CommandException commandError(Object... o)
    {
        return new CommandException(key, o);
    }

    @Override
    public LangKey getLangKey()
    {
        return this;
    }
}
