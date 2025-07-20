package com.example.startres.incarnation;

import com.example.startres.IncarnationConfig;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "startres")
public class IncarnationInteractionEvents {

    @SubscribeEvent
    public static void onPlayerInteractEntity(PlayerInteractEvent.EntityInteract event) {
        if (!IncarnationConfig.MODE_SWITCH_ENABLED.get()) return;
        if (event.getEntity().level().isClientSide()) {
            return;
        }

        if (!(event.getTarget() instanceof Mob mob)) {
            return;
        }

        // 检查是否是化身
        if (!mob.getTags().contains("incarnation")) {
            return;
        }

        // 检查是否是化身的主人
        if (!isOwner(mob, event.getEntity())) {
            return;
        }

        // 检查是否按下了Shift+右键
        if (event.getEntity().isShiftKeyDown()) {
            event.setCanceled(true); // 取消默认交互

            // 切换模式
            IncarnationMode currentMode = getCurrentMode(mob);
            IncarnationMode newMode = currentMode.next();
            setMode(mob, newMode);

            // 发送消息给玩家
            String message = net.minecraft.network.chat.Component.translatable("incarnation.mode.switch",
                    net.minecraft.network.chat.Component.translatable(newMode.getDisplayName())).getString();
            event.getEntity().displayClientMessage(net.minecraft.network.chat.Component.literal(message), true);

            // 如果是游荡模式，设置中心位置
            if (newMode == IncarnationMode.WANDER) {
                setWanderCenter(mob, mob.position());
            }
        }
    }

    private static boolean isOwner(Mob mob, Player player) {
        String ownerUUID = IncarnationUtil.getOwnerUUID(mob).toString();
        return player.getUUID().toString().equals(ownerUUID);
    }

    private static IncarnationMode getCurrentMode(Mob mob) {
        for (String tag : mob.getTags()) {
            if (tag.startsWith("incarnation_mode_")) {
                String modeId = tag.substring("incarnation_mode_".length());
                return IncarnationMode.fromId(modeId);
            }
        }
        return IncarnationMode.FOLLOW; // 默认跟随模式
    }

    private static void setMode(Mob mob, IncarnationMode mode) {
        // 移除旧的模式标签
        mob.getTags().removeIf(tag -> tag.startsWith("incarnation_mode_"));

        // 添加新的模式标签
        mob.addTag("incarnation_mode_" + mode.getId());
    }

    private static void setWanderCenter(Mob mob, Vec3 centerPos) {
        // 找到游荡AI并设置中心位置
        for (var goal : mob.goalSelector.getAvailableGoals()) {
            if (goal.getGoal() instanceof IncarnationWanderGoal wanderGoal) {
                wanderGoal.setCenterPos(centerPos);
                break;
            }
        }
    }
}