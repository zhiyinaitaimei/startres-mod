package com.example.startres.kubejs;

import com.example.startres.IncarnationConfig;
import com.example.startres.incarnation.IncarnationAIManager;
import com.example.startres.incarnation.IncarnationUtil;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class IncarnationPVPHelper {

    public static boolean isPVPEnabled() {
        return IncarnationConfig.PVP_ENABLED.get();
    }

    public static boolean canIncarnationAttackPlayers() {
        return IncarnationConfig.INCARNATION_ATTACK_PLAYERS.get();
    }

    public static void forceStopAttackingPlayers(Mob incarnation) {
        if (!incarnation.getTags().contains("incarnation")) {
            return;
        }

        if (incarnation.getTarget() instanceof Player) {
            incarnation.setTarget(null);
        }
    }

    public static boolean isAttackingOwner(Mob incarnation) {
        if (!incarnation.getTags().contains("incarnation")) {
            return false;
        }

        if (!(incarnation.getTarget() instanceof Player targetPlayer)) {
            return false;
        }

        try {
            return IncarnationUtil.getOwnerUUID(incarnation) != null &&
                    IncarnationUtil.getOwnerUUID(incarnation).equals(targetPlayer.getUUID());
        } catch (Exception e) {
            return false;
        }
    }

    public static void fixIncarnationAttackingOwner(Mob incarnation) {
        if (isAttackingOwner(incarnation)) {
            incarnation.setTarget(null);
        }
    }

    public static void setupEnhancedAI(Mob incarnation) {
        IncarnationAIManager.setupEnhancedAI(incarnation);
    }

    public static void updateAIConfiguration(Mob incarnation) {
        IncarnationAIManager.updateAIConfiguration(incarnation);
    }
}