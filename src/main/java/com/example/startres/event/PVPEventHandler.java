package com.example.startres.event;

import com.example.startres.IncarnationConfig;
import com.example.startres.incarnation.IncarnationUtil;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "startres")
public class PVPEventHandler {

    @SubscribeEvent
    public static void onLivingChangeTarget(LivingChangeTargetEvent event) {
        // 只处理化身实体
        if (!(event.getEntity() instanceof Mob mob)) {
            return;
        }

        // 检查是否是化身
        if (!mob.getTags().contains("incarnation")) {
            return;
        }

        // 检查新目标是否是玩家
        if (!(event.getNewTarget() instanceof Player targetPlayer)) {
            return;
        }

        // 如果PVP未启用，取消目标设定
        if (!IncarnationConfig.PVP_ENABLED.get()) {
            event.setCanceled(true);
            return;
        }

        // 如果化身攻击玩家功能被禁用，取消目标设定
        if (!IncarnationConfig.INCARNATION_ATTACK_PLAYERS.get()) {
            event.setCanceled(true);
            return;
        }

        // 检查是否是化身的主人
        if (isOwner(mob, targetPlayer)) {
            event.setCanceled(true);
        }
    }

    /**
     * 检查玩家是否是化身的主人
     */
    private static boolean isOwner(Mob mob, Player player) {
        try {
            return IncarnationUtil.getOwnerUUID(mob) != null &&
                    IncarnationUtil.getOwnerUUID(mob).equals(player.getUUID());
        } catch (Exception e) {
            return false;
        }
    }
}