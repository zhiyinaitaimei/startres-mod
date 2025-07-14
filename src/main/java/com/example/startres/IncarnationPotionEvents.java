package com.example.startres;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "startres")
public class IncarnationPotionEvents {
    @SubscribeEvent
    public static void onEffectExpired(MobEffectEvent.Expired event) {
        if (event.getEffectInstance().getEffect() == ModEffects.INCARNATION_LIFE.get()) {
            LivingEntity entity = event.getEntity();
            if (entity.getTags().contains("incarnation")) {
                entity.discard(); // 让化身消失
            }
        }
    }
}