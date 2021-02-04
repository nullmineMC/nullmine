package com.nullmine.core.Guilds;

import com.nullmine.core.NullMine;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class GuildManager {
    private static GuildManager instance;

    public GuildManager () {
        instance = this;
    }

    public static GuildManager getInstance() {
        return instance;
    }

    public void createGuild(String name, Player owner) {

        File file = new File("./data/guilds/" + name + "/guild.yml");

        if (file.getParentFile().exists()) {
            owner.sendMessage("§4A guild with the same name already exists, please choose another name.");
            return;
        }

        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        config.set("owner", owner.getUniqueId());
        config.set("creation_date", Calendar.getInstance().getTime());

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        file = new File("./data/players/" + owner.getUniqueId() + ".yml");

        config = YamlConfiguration.loadConfiguration(file);

        config.set("guild", name);

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        NullMine.getInstance().getPlayer(owner.getUniqueId()).guild = name;

        return;
    }
}
