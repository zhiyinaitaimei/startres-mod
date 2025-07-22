package com.example.startres.incarnation;

import com.example.startres.IncarnationConfig;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;

public class IncarnationAIManager {

    public static void setupEnhancedAI(Mob mob) {
        if (!mob.getTags().contains("incarnation")) {
            return;
        }

        // 移除现有的IncarnationTargetGoal
        removeOldTargetGoals(mob);

        // 添加新的增强目标选择AI
        mob.targetSelector.addGoal(1, new EnhancedIncarnationTargetGoal(mob));
    }

    private static void removeOldTargetGoals(Mob mob) {
        // 创建一个临时列表来存储要移除的目标
        var goalsToRemove = mob.targetSelector.getAvailableGoals().stream()
                .filter(goal -> goal.getGoal() instanceof IncarnationTargetGoal)
                .toList();

        // 移除旧的目标选择AI
        for (var goalWrapper : goalsToRemove) {
            mob.targetSelector.removeGoal(goalWrapper.getGoal());
        }
    }

    public static void updateAIConfiguration(Mob mob) {
        if (!mob.getTags().contains("incarnation")) {
            return;
        }

        // 如果当前目标是玩家且PVP被禁用，清除目标
        if (mob.getTarget() instanceof net.minecraft.world.entity.player.Player) {
            if (!IncarnationConfig.PVP_ENABLED.get() || !IncarnationConfig.INCARNATION_ATTACK_PLAYERS.get()) {
                mob.setTarget(null);
            }
        }
    }
}