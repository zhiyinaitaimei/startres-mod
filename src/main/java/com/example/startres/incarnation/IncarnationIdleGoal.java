package com.example.startres.incarnation;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import java.util.EnumSet;

public class IncarnationIdleGoal extends Goal {
    private final Mob mob;

    public IncarnationIdleGoal(Mob mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return isIdleMode();
    }

    @Override
    public boolean canContinueToUse() {
        return isIdleMode();
    }

    @Override
    public void start() {
        mob.getNavigation().stop();
        mob.setDeltaMovement(0, mob.getDeltaMovement().y, 0);
        mob.setYRot(mob.getYRot());
        mob.setXRot(mob.getXRot());
    }

    @Override
    public void tick() {
        mob.getNavigation().stop();
        mob.setDeltaMovement(0, mob.getDeltaMovement().y, 0);
    }

    private boolean isIdleMode() {
        for (String tag : mob.getTags()) {
            if (tag.startsWith("incarnation_mode_")) {
                return "idle".equals(tag.substring("incarnation_mode_".length()));
            }
        }
        return false;
    }
}
