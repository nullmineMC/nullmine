package com.nullmine.core.Guilds;

import com.mojang.authlib.GameProfile;
import com.nullmine.core.NullMine;
import net.minecraft.server.v1_8_R3.*;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GuildNPC implements Listener {
    private EntityPlayer npc;
    private List<UUID> players;
    private static GuildNPC instance;
    private Location loc;

    public static GuildNPC getInstance() {
        return instance;
    }

    public GuildNPC() {
        instance = this;
        players = new ArrayList<>();

        World wrld = Bukkit.getWorld("spawn");
        loc = new Location(Bukkit.getWorld("spawn"), 0, 50, 0);

        npc = new EntityPlayer(((CraftServer) Bukkit.getServer()).getServer(), ((CraftWorld) Bukkit.getWorld("spawn")).getHandle(), new GameProfile(UUID.randomUUID(), "§bGuild manager"), new PlayerInteractManager(((CraftWorld) Bukkit.getWorld("spawn")).getHandle()));
        npc.getBukkitEntity().getPlayer().setPlayerListName("");
        npc.getProfile().getProperties().put("textures", new Property("textures","ewogICJ0aW1lc3RhbXAiIDogMTYxMjAwNDY1ODMzMCwKICAicHJvZmlsZUlkIiA6ICIwODFhMzA1ZWExM2E0MGU1YThmNWM2NjIxYTEyOTkwOSIsCiAgInByb2ZpbGVOYW1lIiA6ICJudWxsQmxhZGUiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTUxOWFlZjM2YzcyZTkxMTUzNDg1YzA2MGE3MmFlMWQ1MzAyMDI3MmM4OWEyODY2NzhlYmExNjlmOTM0NjBlYyIsCiAgICAgICJtZXRhZGF0YSIgOiB7CiAgICAgICAgIm1vZGVsIiA6ICJzbGltIgogICAgICB9CiAgICB9CiAgfQp9","PGH7NSd8ebn4XMmHOZqN7q4zttlP0GnmmHJcwHhnt4tvvE8IwpTETdDWmNdKzcKwz00u+owd+RZhUkhwBcRhDboNxZS7RvAx6yxEQsJPi+stp7hFefKOgCBbaxRH0JcB5Ya8IIVOGN2ZTEPXfYM1agdQDz9sGMae3OhhmzVAQvcaDs1fbQcgYD4FT1V3+WmLTPNytRRnYFU/+yhh6HRbHqRPgdWsIiQw7w/sENbGVu/mZmTWYC+SXpw0GIjvNYMt1UEbbgTk0fCJRFpU5gEwxQKeATpihuj2zgaAFaTrNMkuXmvvTgwGCw5zA48/BmLhl94nTvu31PGs34c0FadXOlrujkJaYYysilqegkkEE5DRU4AM1CI8CFfXyoTIb70Qh70oRTVG650qn2G3EoLFr7OMaMK/Cdj1929fPACApOCMkUBOHTQFYY3nUdxIu0C0RgD4z5ulVVOz/HOLuLMHorEaLnYeF+s8Z8WUDLeCNxraKSwinBoKqzjgDU5L+gps1kAEa4ykpmWWiYjdGA1iE9Z/ZvlPOy9C0N5IUg8zQ270Egyv4WnYZauPDDfCHNBAJZUZvgZTDuCrCyWdn5xeKyYJKlH4Z4UQIpohUKMpILncrsB0MDzIbRCeDJYEAKHoBV2g09RZlxHbH72oOZ+Xt/Cxvblo/q+veR2/j5MfHaU="));

        npc.setLocation(0.5, 50, 0.5, 0, 0);

        Bukkit.getPluginManager().registerEvents(this, NullMine.getInstance());

        Bukkit.getScheduler().runTaskTimer(NullMine.getInstance(), () -> {
            for (Entity e : wrld.getNearbyEntities(loc, 64, 32, 64)) {
                if (e instanceof Player && !players.contains(e.getUniqueId())) {
                    ((CraftPlayer) e).getHandle().playerConnection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
                    players.add(e.getUniqueId());
                }
            }

        }, 40, 40);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        PlayerConnection connection = ((CraftPlayer) e.getPlayer()).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
    }

    @EventHandler
    public void playerLeaveWorld(PlayerChangedWorldEvent e) {
        if (e.getFrom().getName() == "spawn") {
            players.remove(e.getPlayer().getUniqueId());
        }
    }

    @EventHandler
    public void PlayerLeave(PlayerQuitEvent e) {
        players.remove(e.getPlayer().getUniqueId());
    }

    public void usePacket(PacketPlayInUseEntity p, Player pl) {
        Bukkit.getScheduler().runTaskLater(NullMine.getInstance(), () -> {
            if (!p.a().equals(PacketPlayInUseEntity.EnumEntityUseAction.INTERACT)) return;
            if (pl.getWorld().getName().equals("spawn") && pl.getLocation().distance(loc) < 6 && p.a(((CraftPlayer) pl).getHandle().getWorld()) == null) {
                GuildMenu.getInstance().open(pl);
            }
        }, 1);
    }

}
