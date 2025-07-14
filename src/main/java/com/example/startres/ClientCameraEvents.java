package com.example.startres;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.client.Minecraft;

@Mod.EventBusSubscriber(modid = "startres", value = Dist.CLIENT)
public class ClientCameraEvents {
    @SubscribeEvent
    public static void onCameraAngles(ViewportEvent.ComputeCameraAngles event) {
        if (Minecraft.getInstance().getCameraEntity() instanceof CameraEntity camera) {
            event.setYaw(camera.getCameraYaw());
            event.setPitch(camera.getCameraPitch());
        }
    }
}