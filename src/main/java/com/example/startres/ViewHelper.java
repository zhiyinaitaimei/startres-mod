package com.example.startres;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.network.protocol.game.ClientboundSetCameraPacket;

public class ViewHelper {

    public static void setCamera(ServerPlayer player, Entity entity) {
        if (player != null && entity != null && !player.level().isClientSide) {
            player.connection.send(new ClientboundSetCameraPacket(entity));
        }
    }


    public static void resetCamera(ServerPlayer player) {
        if (player != null && !player.level().isClientSide) {
            player.connection.send(new ClientboundSetCameraPacket(player));
        }
    }
}