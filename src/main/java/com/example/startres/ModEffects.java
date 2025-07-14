package com.example.startres;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.ArrayList;
import java.util.UUID;

public class ModEffects {
    //
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS,
            "startres");

    public static final RegistryObject<MobEffect> IMMUNE_ALL_NEG = EFFECTS.register("immune_all_neg",
            () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0x00FFFF) {
                @Override
                public void applyEffectTick(LivingEntity entity, int amplifier) {
                    for (MobEffectInstance eff : new ArrayList<>(entity.getActiveEffects())) {
                        if (eff.getEffect().getCategory() == MobEffectCategory.HARMFUL) {
                            entity.removeEffect(eff.getEffect());
                        }
                    }
                }

                @Override
                public boolean isDurationEffectTick(int duration, int amplifier) {
                    return true;
                }
            });
    public static final RegistryObject<MobEffect> INCARNATION_CD = EFFECTS.register("incarnation_cd", () -> {
        MobEffect effect = new StackingMobEffect(MobEffectCategory.BENEFICIAL, 0xFF00FF);
        return effect;
    });

    public static final RegistryObject<MobEffect> INCARNATION_LIFE = EFFECTS.register("incarnation_life",

            IncarnationLifeEffect::new);

    public static final RegistryObject<MobEffect> INCARNATION_NO_FALL = EFFECTS.register("incarnation_no_fall",
            () -> new MobEffect(MobEffectCategory.BENEFICIAL, 0x00FFFF) {
            });

    public static void register(IEventBus bus) {
        EFFECTS.register(bus);
    }
}

class IncarnationLifeEffect extends MobEffect {
    public IncarnationLifeEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x00FFAA);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return false;
    }
}
