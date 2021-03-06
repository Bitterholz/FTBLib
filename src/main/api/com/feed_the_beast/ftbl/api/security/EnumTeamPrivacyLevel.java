package com.feed_the_beast.ftbl.api.security;

import com.feed_the_beast.ftbl.lib.EnumNameMap;

/**
 * Created by LatvianModder on 02.06.2016.
 */
public enum EnumTeamPrivacyLevel
{
    EVERYONE,
    ALLIES,
    MEMBERS;

    public static final EnumTeamPrivacyLevel[] VALUES = values();
    public static final EnumNameMap<EnumTeamPrivacyLevel> NAME_MAP = new EnumNameMap<>(VALUES, false);
}