package com.example.startres.kubejs;

import com.example.startres.IncarnationConfig;
import com.example.startres.incarnation.IncarnationAIManager;
import com.example.startres.incarnation.IncarnationUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class IncarnationKubeJSHelper {

    public static boolean setupIncarnation(Entity entity, String mode) {
        if (!(entity instanceof Mob mob)) {
            return false;
        }

        // 检查是否为Boss类型实体，给出警告
        if (isBossEntity(entity)) {
            System.out.println("[Startres警告] 检测到Boss类型化身: " + entity.getType() +
                    " - 切换模式可能导致存档崩溃，请谨慎使用！");
        }

        // 注入AI系统
        IncarnationAIHelper.injectIncarnationAI(mob, mode);

        // 设置增强AI
        IncarnationAIManager.setupEnhancedAI(mob);

        // 隐藏boss血条
        IncarnationBossBarHelper.hideIncarnationBossBar(entity);

        return true;
    }

    private static boolean isBossEntity(Entity entity) {
        String entityType = entity.getType().toString();
        return entityType.contains("wither") ||
                entityType.contains("dragon") ||
                entityType.contains("boss") ||
                entity.getTags().contains("boss") ||
                IncarnationBossBarHelper.hasBossBar(entity);
    }

    public static boolean setupIncarnation(Entity entity) {
        return setupIncarnation(entity, "follow");
    }

    public static boolean switchMode(Entity entity, String newMode) {
        if (!(entity instanceof Mob mob)) {
            return false;
        }

        if (!mob.getTags().contains("incarnation")) {
            return false;
        }

        // Boss类型实体切换模式警告
        if (isBossEntity(entity)) {
            System.err.println("[Startres严重警告] 尝试切换Boss类型化身模式: " + entity.getType() +
                    " - 这可能导致存档崩溃！建议避免对Boss类型化身切换模式。");
        }

        IncarnationAIHelper.switchIncarnationMode(mob, newMode);
        return true;
    }

    public static boolean isIncarnation(Entity entity) {
        return entity != null && entity.getTags().contains("incarnation");
    }

    public static String getIncarnationMode(Entity entity) {
        if (entity == null)
            return null;

        for (String tag : entity.getTags()) {
            if (tag.startsWith("incarnation_mode_")) {
                return tag.substring("incarnation_mode_".length());
            }
        }
        return "follow"; // 默认模式
    }

    public static boolean isPVPEnabled() {
        return IncarnationConfig.PVP_ENABLED.get();
    }

    public static boolean canIncarnationAttackPlayers() {
        return IncarnationConfig.INCARNATION_ATTACK_PLAYERS.get();
    }

    public static void forceStopAttackingPlayers(Entity entity) {
        if (!(entity instanceof Mob mob)) {
            return;
        }

        if (!mob.getTags().contains("incarnation")) {
            return;
        }

        if (mob.getTarget() instanceof Player) {
            mob.setTarget(null);
        }
    }

    public static boolean isAttackingOwner(Entity entity) {
        if (!(entity instanceof Mob mob)) {
            return false;
        }

        if (!mob.getTags().contains("incarnation")) {
            return false;
        }

        if (!(mob.getTarget() instanceof Player targetPlayer)) {
            return false;
        }

        try {
            return IncarnationUtil.getOwnerUUID(mob) != null &&
                    IncarnationUtil.getOwnerUUID(mob).equals(targetPlayer.getUUID());
        } catch (Exception e) {
            return false;
        }
    }

    public static void fixIncarnationAttackingOwner(Entity entity) {
        if (isAttackingOwner(entity)) {
            ((Mob) entity).setTarget(null);
        }
    }

    public static void setupEnhancedAI(Entity entity) {
        if (!(entity instanceof Mob mob)) {
            return;
        }
        IncarnationAIManager.setupEnhancedAI(mob);
    }

    public static void updateAIConfiguration(Entity entity) {
        if (!(entity instanceof Mob mob)) {
            return;
        }
        IncarnationAIManager.updateAIConfiguration(mob);
    }

}