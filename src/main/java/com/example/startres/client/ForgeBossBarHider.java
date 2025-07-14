package com.example.startres.client;

import com.example.startres.IncarnationConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class ForgeBossBarHider {

    @SubscribeEvent
    public static void onBossBarRender(CustomizeGuiOverlayEvent.BossEventProgress event) {
        if (!IncarnationConfig.HIDE_INCARNATION_BOSSBAR.get()) return;

        String prefix = IncarnationConfig.BOSSBAR_HIDE_PREFIX.get();
        if (event.getBossEvent().getName().getString().startsWith(prefix)) {
            event.setCanceled(true);
        }
    }
}