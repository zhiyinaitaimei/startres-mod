package com.example.startres;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.eventbus.api.IEventBus;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES,
            "startres");

    public static final RegistryObject<EntityType<CameraEntity>> CAMERA = ENTITIES.register("camera_entity",
            () -> EntityType.Builder.<CameraEntity>of(CameraEntity::new, MobCategory.MISC)
                    .sized(0.01f, 0.01f)
                    .clientTrackingRange(256)
                    .updateInterval(1)
                    .build("camera_entity"));

    public static void register(IEventBus bus) {
        ENTITIES.register(bus);
    }
}