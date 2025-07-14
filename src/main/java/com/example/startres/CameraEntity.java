package com.example.startres;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.Level;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;

public class CameraEntity extends Entity {

    private static final EntityDataAccessor<Float> CAMERA_YAW = SynchedEntityData.defineId(CameraEntity.class,
            EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> CAMERA_PITCH = SynchedEntityData.defineId(CameraEntity.class,
            EntityDataSerializers.FLOAT);

    public CameraEntity(EntityType<?> type, Level level) {
        super(type, level);
        this.noPhysics = true;
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(CAMERA_YAW, 0f);
        this.entityData.define(CAMERA_PITCH, 0f);
    }


    public void setCameraYaw(float yaw) {
        this.entityData.set(CAMERA_YAW, yaw);
    }

    public float getCameraYaw() {
        return this.entityData.get(CAMERA_YAW);
    }


    public void setCameraPitch(float pitch) {
        this.entityData.set(CAMERA_PITCH, pitch);
    }

    public float getCameraPitch() {
        return this.entityData.get(CAMERA_PITCH);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
    }

    @Override
    public boolean isInvulnerable() {
        return true;
    }

    @Override
    public boolean isInvisible() {
        return true;
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return EntityDimensions.fixed(0.01f, 0.01f);
    }
} //摄像机