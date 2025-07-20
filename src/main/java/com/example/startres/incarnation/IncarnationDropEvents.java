package com.example.startres.incarnation;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "startres")
public class IncarnationDropEvents {
    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.getTags().contains("incarnation")) {
            event.getDrops().clear(); // 清空所有掉落
        }
    }
}
