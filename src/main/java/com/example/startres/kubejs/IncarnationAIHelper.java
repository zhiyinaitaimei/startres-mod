package com.example.startres.kubejs;

import com.example.startres.incarnation.*;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

public class IncarnationAIHelper {

    public static void injectIncarnationAI(Mob mob, String mode) {
        if (mob == null)
            return;

        // 确保实体有化身标签
        if (!mob.getTags().contains("incarnation")) {
            mob.addTag("incarnation");
        }

        // 标记为已通过KubeJS正确设置，避免备用tick检查
        mob.addTag("incarnation_ai_injected");

        // 设置模式标签
        setIncarnationMode(mob, mode);

        // 清除现有的攻击玩家AI
        removeHostilePlayerAI(mob);

        // 注入化身专用AI
        injectCustomAI(mob);

        // 根据模式设置初始AI状态
        setupModeSpecificAI(mob, mode);
    }

    public static void setIncarnationMode(Mob mob, String mode) {
        // 移除旧的模式标签
        mob.getTags().removeIf(tag -> tag.startsWith("incarnation_mode_"));

        // 添加新的模式标签
        mob.addTag("incarnation_mode_" + mode);
    }

    private static void removeHostilePlayerAI(Mob mob) {
        mob.targetSelector.getAvailableGoals().removeIf(
                g -> g.getGoal() instanceof NearestAttackableTargetGoal
                        && !(g.getGoal() instanceof IncarnationTargetGoal));
    }

    private static void injectCustomAI(Mob mob) {
        // 添加化身专用目标选择AI（最高优先级）
        if (!hasGoalOfType(mob.targetSelector.getAvailableGoals(), IncarnationTargetGoal.class)) {
            mob.targetSelector.addGoal(1, new IncarnationTargetGoal(mob));
        }

        // 添加跟随AI
        if (!hasGoalOfType(mob.goalSelector.getAvailableGoals(), IncarnationFollowOwnerGoal.class)) {
            mob.goalSelector.addGoal(2, new IncarnationFollowOwnerGoal(mob));
        }

        // 添加游荡AI
        if (!hasGoalOfType(mob.goalSelector.getAvailableGoals(), IncarnationWanderGoal.class)) {
            mob.goalSelector.addGoal(3, new IncarnationWanderGoal(mob));
        }

        // 添加待机AI
        if (!hasGoalOfType(mob.goalSelector.getAvailableGoals(), IncarnationIdleGoal.class)) {
            mob.goalSelector.addGoal(0, new IncarnationIdleGoal(mob));
        }
    }

    private static void setupModeSpecificAI(Mob mob, String mode) {
        switch (mode.toLowerCase()) {
            case "idle":
                // 待机模式：停止移动
                mob.getNavigation().stop();
                break;
            case "wander":
                // 游荡模式：设置当前位置为中心点
                // 这里可以添加特定的游荡设置
                break;
            case "follow":
            default:
                // 跟随模式：默认行为，无需特殊设置
                break;
        }
    }

    private static boolean hasGoalOfType(java.util.Set<?> goals, Class<?> goalType) {
        return goals.stream().anyMatch(g -> {
            try {
                Object goal = g.getClass().getMethod("getGoal").invoke(g);
                return goalType.isInstance(goal);
            } catch (Exception e) {
                return false;
            }
        });
    }

    public static void setupFollowIncarnation(Mob mob) {
        injectIncarnationAI(mob, "follow");
    }

    public static void setupIdleIncarnation(Mob mob) {
        injectIncarnationAI(mob, "idle");
    }

    public static void setupWanderIncarnation(Mob mob) {
        injectIncarnationAI(mob, "wander");
    }

    public static void switchIncarnationMode(Mob mob, String newMode) {
        if (!mob.getTags().contains("incarnation"))
            return;

        setIncarnationMode(mob, newMode);
        setupModeSpecificAI(mob, newMode);
    }
}