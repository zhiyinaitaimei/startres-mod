package com.example.startres;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class CameraEntityRenderer extends EntityRenderer<CameraEntity> {
    public CameraEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(CameraEntity entity) {
        return null;
    }
}