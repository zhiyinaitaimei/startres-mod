package com.example.startres.incarnation;

import com.example.startres.IncarnationConfig;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import java.util.EnumSet;
import java.util.UUID;

public class IncarnationFollowOwnerGoal extends Goal {
    private final Mob mob;
    private Player owner;

    public IncarnationFollowOwnerGoal(Mob mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        // 检查是否处于跟随模式
        if (!isFollowMode()) {
            return false;
        }

        UUID ownerUUID = IncarnationUtil.getOwnerUUID(mob);
        if (ownerUUID == null)
            return false;
        Player player = mob.level().getPlayerByUUID(ownerUUID);
        if (player == null)
            return false;
        this.owner = player;
        // 跟随距离
        return mob.distanceTo(owner) > IncarnationConfig.FOLLOW_DISTANCE.get() && mob.getTarget() == null;
    }

    @Override
    public void start() {
        if (owner != null) {
            mob.getNavigation().moveTo(owner, IncarnationConfig.FOLLOW_SPEED.get());
        }
    }

    @Override
    public void tick() {
        if (owner != null && mob.getTarget() == null) {
            mob.getNavigation().moveTo(owner, IncarnationConfig.FOLLOW_SPEED.get());
        }
        super.tick();
        UUID ownerUUID = IncarnationUtil.getOwnerUUID(mob);
        if (ownerUUID != null) {
            Player owner = mob.level().getPlayerByUUID(ownerUUID);
            if (owner != null) {
                double dist = mob.distanceTo(owner);
                // 传送距离和开关
                if (IncarnationConfig.ENABLE_TELEPORT.get() && dist > IncarnationConfig.TELEPORT_DISTANCE.get()) {
                    mob.teleportTo(owner.getX(), owner.getY(), owner.getZ());
                }
            }
        }
    }

    @Override
    public boolean canContinueToUse() {
        return owner != null && mob.distanceTo(owner) > IncarnationConfig.STOP_FOLLOW_DISTANCE.get()
                && mob.getTarget() == null;
    }

    private boolean isFollowMode() {
        String modeTag = getModeTag();
        return "follow".equals(modeTag);
    }

    private String getModeTag() {
        for (String tag : mob.getTags()) {
            if (tag.startsWith("incarnation_mode_")) {
                return tag.substring("incarnation_mode_".length());
            }
        }
        return "follow"; // 默认跟随模式
    }

}