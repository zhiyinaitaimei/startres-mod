package com.example.startres.kubejs;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;

public class IncarnationKubeJSHelper {
    public static boolean setupIncarnation(Entity entity, String mode) {
        if (!(entity instanceof Mob mob)) {
            return false;
        }

        if (isBossEntity(entity)) {
            System.out.println("[Startres警告] 检测到Boss类型化身: " + entity.getType() +
                    " - 切换模式可能导致存档崩溃，请谨慎使用！");
        }

        IncarnationAIHelper.injectIncarnationAI(mob, mode);

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
}