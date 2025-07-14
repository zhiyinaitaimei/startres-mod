package com.example.startres;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;


import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;

@Mod.EventBusSubscriber(modid = "startres", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(ModEntities.CAMERA.get(), CameraEntityRenderer::new);
    }
}