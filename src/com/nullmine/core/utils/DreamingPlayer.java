package com.nullmine.core.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class DreamingPlayer {
    // general
    public String guild;
    public Player player;

    // breaking blocks
    public int ticksNeeded;
    public int ticksGoneBy;
    public Location breakingBlockLocation;
    public boolean willDrop;
}
