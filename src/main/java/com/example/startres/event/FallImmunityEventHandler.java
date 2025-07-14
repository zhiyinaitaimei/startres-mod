package com.example.startres.event;

import com.example.startres.ModEffects;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class FallImmunityEventHandler {

    @SubscribeEvent
    public static void onFall(LivingFallEvent event) {
        if (event.getEntity().hasEffect(ModEffects.INCARNATION_NO_FALL.get())) {
            event.setCanceled(true); // 取消掉落伤害
        }
    }
}