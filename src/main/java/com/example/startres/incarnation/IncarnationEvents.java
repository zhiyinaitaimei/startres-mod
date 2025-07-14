package com.example.startres.incarnation;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "startres")
public class IncarnationEvents {
    @SubscribeEvent
    public static void onEntityTick(LivingEvent.LivingTickEvent event) {
        if (!(event.getEntity() instanceof Mob mob))
            return;
        if (!mob.getTags().contains("incarnation"))
            return;

        mob.targetSelector.getAvailableGoals().removeIf(
                g -> g.getGoal() instanceof NearestAttackableTargetGoal
                        && !(g.getGoal() instanceof IncarnationTargetGoal));

        if (!mob.goalSelector.getAvailableGoals().stream().anyMatch(g -> g.getGoal() instanceof IncarnationIdleGoal)) {
            mob.goalSelector.addGoal(0, new IncarnationIdleGoal(mob));
        } //除攻击玩家的AI

        boolean hasGoal = mob.targetSelector.getAvailableGoals().stream()
                .anyMatch(g -> g.getGoal() instanceof IncarnationTargetGoal);
        if (!hasGoal) {
            mob.targetSelector.addGoal(1, new IncarnationTargetGoal(mob));
        } // 添加自定义AI

        if (!mob.goalSelector.getAvailableGoals().stream()
                .anyMatch(g -> g.getGoal() instanceof IncarnationFollowOwnerGoal)) {
            mob.goalSelector.addGoal(2, new IncarnationFollowOwnerGoal(mob));
        } // 添加跟随AI

        if (!mob.goalSelector.getAvailableGoals().stream()
                .anyMatch(g -> g.getGoal() instanceof IncarnationWanderGoal)) {
            mob.goalSelector.addGoal(3, new IncarnationWanderGoal(mob));
        }  // 添加游荡AI
    }
}