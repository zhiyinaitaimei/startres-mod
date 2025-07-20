package com.example.startres.incarnation;

import net.minecraft.world.entity.Mob;
import java.util.UUID;

public class IncarnationUtil {
    public static UUID getOwnerUUID(Mob mob) {
        for (String tag : mob.getTags()) {
            if (tag.startsWith("incarnation_owner_")) {
                try {
                    return UUID.fromString(tag.substring("incarnation_owner_".length()));
                } catch (Exception ignored) {
                }
            }
        }
        return null;
    }
}