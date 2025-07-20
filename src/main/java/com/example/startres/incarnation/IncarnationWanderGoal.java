package com.example.startres.incarnation;

import com.example.startres.IncarnationConfig;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;
import java.util.EnumSet;
import net.minecraft.world.entity.PathfinderMob;

public class IncarnationWanderGoal extends Goal {
    private final Mob mob;
    private final double wanderRange;
    private Vec3 centerPos;
    private int cooldown;

    public IncarnationWanderGoal(Mob mob) {
        this.mob = mob;
        this.wanderRange = IncarnationConfig.WANDER_RANGE.get();
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        // 检查是否处于游荡模式
        if (!isWanderMode()) {
            return false;
        }

        // 设置中心位置（如果还没有设置）
        if (centerPos == null) {
            centerPos = mob.position();
        }

        // 冷却时间检查
        if (cooldown > 0) {
            cooldown--;
            return false;
        }

        return true;
    }

    @Override
    public void start() {
        // 在中心位置周围随机选择一个位置
        Vec3 randomPos = DefaultRandomPos.getPosTowards((PathfinderMob) mob, (int) wanderRange, 7, centerPos, 1.0);
        if (randomPos == null) {
            // 如果找不到合适的位置，就在当前位置附近随机
            randomPos = DefaultRandomPos.getPosTowards((PathfinderMob) mob, 5, 7, mob.position(), 1.0);
        }

        if (randomPos != null) {
            mob.getNavigation().moveTo(randomPos.x, randomPos.y, randomPos.z, 1.0);
        }

        cooldown = 60; // 1秒冷却时间
    }

    @Override
    public boolean canContinueToUse() {
        return isWanderMode() && !mob.getNavigation().isDone();
    }

    @Override
    public void stop() {
        mob.getNavigation().stop();
    }

    private boolean isWanderMode() {
        String modeTag = getModeTag();
        return "wander".equals(modeTag);
    }

    private String getModeTag() {
        for (String tag : mob.getTags()) {
            if (tag.startsWith("incarnation_mode_")) {
                return tag.substring("incarnation_mode_".length());
            }
        }
        return "follow"; // 默认跟随模式
    }

    // 设置新的中心位置（当模式切换时调用）
    public void setCenterPos(Vec3 pos) {
        this.centerPos = pos;
    }
}