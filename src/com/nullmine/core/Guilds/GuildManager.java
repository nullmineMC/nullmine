package com.nullmine.core.Guilds;

import org.bukkit.entity.Player;

public class GuildManager {
    private static GuildManager instance;

    public GuildManager () {
        instance = this;
    }

    public static GuildManager getInstance() {
        return instance;
    }

    public boolean createGuild(String name, Player owner) {



        return true;
    }

    public String getPlayersGuild(Player p) {
        return null;
    }


}
