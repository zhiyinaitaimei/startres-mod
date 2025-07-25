package com.example.startres.incarnation;

import com.example.startres.IncarnationConfig;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import java.util.UUID;

public class IncarnationTargetGoal extends NearestAttackableTargetGoal<LivingEntity> {
    private final Mob mob;

    public IncarnationTargetGoal(Mob mob) {
        super(mob, LivingEntity.class, 10, true, false, target -> true);
        this.mob = mob;
    }

    @Override
    public boolean canUse() {
        UUID ownerUUID = IncarnationUtil.getOwnerUUID(mob);
        if (ownerUUID == null)
            return false;
        Player owner = mob.level().getPlayerByUUID(ownerUUID);
        if (owner == null)
            return false;

        // 1.优先攻击玩家当前攻击的目标
        LivingEntity playerTarget = owner.getLastHurtMob();
        if (playerTarget != null && playerTarget != mob) {
            this.target = playerTarget;
            return true;
        }

        // 2.其次攻击最近攻击玩家的目标
        for (LivingEntity target : mob.level().getEntitiesOfClass(LivingEntity.class,
                mob.getBoundingBox().inflate(16))) {
            if (target == mob)
                continue;
            if (target.getTags().contains("incarnation"))
                continue;
            if (owner.getLastHurtByMob() == target) { // 玩家最近被该目标攻击
                this.target = target;
                return true;
            }
        }

        // 3. 主动攻击所有“与玩家有仇恨”的目标（如目标的攻击目标是玩家），优先距离玩家最近
        LivingEntity nearestHostile = null;
        double minDist = Double.MAX_VALUE;
        for (LivingEntity target : mob.level().getEntitiesOfClass(LivingEntity.class,
                mob.getBoundingBox().inflate(16))) {
            if (target == mob)
                continue;
            if (target.getTags().contains("incarnation"))
                continue;
            if (target instanceof Mob hostileMob) {
                if (hostileMob.getTarget() == owner) {
                    double dist = owner.distanceTo(target);
                    if (dist < minDist) {
                        minDist = dist;
                        nearestHostile = target;
                    }
                }
            }
        }
        if (nearestHostile != null) {
            this.target = nearestHostile;
            return true;
        }

        // 4.再其次攻击与玩家有仇恨的目标
        for (LivingEntity target : mob.level().getEntitiesOfClass(LivingEntity.class,
                mob.getBoundingBox().inflate(16))) {
            if (target == mob)
                continue;
            if (target.getTags().contains("incarnation"))
                continue;
            if (target.getLastHurtByMob() == owner || owner.getLastHurtMob() == target) {
                this.target = target;
                return true;
            }
        }

        // 5. 主动攻击其他化身正在攻击的实体（最低优先级）
        for (LivingEntity other : mob.level().getEntitiesOfClass(LivingEntity.class,
                mob.getBoundingBox().inflate(16))) {
            if (other == mob)
                continue;
            if (!other.getTags().contains("incarnation") && !other.getTags().contains("ally"))
                continue;
            if (other instanceof Mob otherMob) {
                LivingEntity theirTarget = otherMob.getTarget();
                if (theirTarget != null && theirTarget != mob && !theirTarget.getTags().contains("incarnation")) {
                    this.target = theirTarget;
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean isValidTarget(LivingEntity target) {
        // 不攻击友军/化身
        if (target.getTags().contains("incarnation") || target.getTags().contains("ally")) {
            return false;
        }
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = this.mob.getTarget();
        if (target == null)
            return false;

        // 如果玩家当前攻击的目标不是当前目标，则切换
        UUID ownerUUID = IncarnationUtil.getOwnerUUID(mob);
        if (ownerUUID != null) {
            Player owner = mob.level().getPlayerByUUID(ownerUUID);
            if (owner != null) {
                LivingEntity playerTarget = owner.getLastHurtMob();
                if (playerTarget != null && playerTarget != mob && playerTarget != target) {
                    return false; // 让AI重新选择目标
                }
            }
        }
        return super.canContinueToUse();
    }
}