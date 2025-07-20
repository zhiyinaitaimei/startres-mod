package com.example.startres.bossbar;

import com.example.startres.IncarnationConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.BossEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = "startres", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class EnhancedBossBarHider {

    @SubscribeEvent
    public static void onBossBarRender(CustomizeGuiOverlayEvent.BossEventProgress event) {

        if (!IncarnationConfig.HIDE_INCARNATION_BOSSBAR.get()) {
            return;
        }

        BossEvent bossEvent = event.getBossEvent();
        Component displayName = bossEvent.getName();
        String bossName = displayName.getString();

        if (isIncarnationBoss(bossName)) {
            event.setCanceled(true);
        }
    }

    private static boolean isIncarnationBoss(String bossName) {
        String lowerName = bossName.toLowerCase();
        String hidePrefix = IncarnationConfig.BOSSBAR_HIDE_PREFIX.get().toLowerCase();

        if (!hidePrefix.isEmpty() && lowerName.contains(hidePrefix)) {
            return true;
        }

        if (lowerName.contains("incarnation") || lowerName.contains("化身")) {
            return true;
        }

        String[] incarnationKeywords = {
                "avatar", "summon", "召唤", "分身", "替身"
        };

        for (String keyword : incarnationKeywords) {
            if (lowerName.contains(keyword)) {
                return true;
            }
        }

        return false;
    }
}

