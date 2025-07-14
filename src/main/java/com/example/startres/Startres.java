package com.example.startres;

import com.example.startres.ModEntities;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.ModLoadingContext;

@Mod("startres")
public class Startres {

    public Startres() {
        ModEffects.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModEntities.register(FMLJavaModLoadingContext.get().getModEventBus());


        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, IncarnationConfig.COMMON_CONFIG);
    }
}
