package com.example.startres;

import net.minecraftforge.common.ForgeConfigSpec;

public class IncarnationConfig {
        public static final ForgeConfigSpec COMMON_CONFIG;

        // 跟随相关配置
        public static final ForgeConfigSpec.DoubleValue FOLLOW_DISTANCE;
        public static final ForgeConfigSpec.DoubleValue STOP_FOLLOW_DISTANCE;

        // 传送相关配置
        public static final ForgeConfigSpec.DoubleValue TELEPORT_DISTANCE;
        public static final ForgeConfigSpec.BooleanValue ENABLE_TELEPORT;

        // 移动速度配置
        public static final ForgeConfigSpec.DoubleValue FOLLOW_SPEED;

        // 游荡配置
        public static final ForgeConfigSpec.DoubleValue WANDER_RANGE;

        public static final ForgeConfigSpec.BooleanValue MODE_SWITCH_ENABLED;
        // bossbar配置
        public static final ForgeConfigSpec.ConfigValue<String> BOSSBAR_HIDE_PREFIX;

        public static final ForgeConfigSpec.BooleanValue HIDE_INCARNATION_BOSSBAR;

        static {
                ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

                FOLLOW_DISTANCE = builder.comment("化身开始跟随玩家的距离（格）")
                                .translation("config.startres.incarnation.follow_distance")
                                .defineInRange("follow_distance", 12.0, 1.0, 100.0);

                STOP_FOLLOW_DISTANCE = builder.comment("化身停止跟随玩家的距离（格）")
                                .translation("config.startres.incarnation.stop_follow_distance")
                                .defineInRange("stop_follow_distance", 4.0, 1.0, 50.0);

                FOLLOW_SPEED = builder.comment("化身跟随玩家的移动速度")
                                .translation("config.startres.incarnation.follow_speed")
                                .defineInRange("follow_speed", 1.2, 0.1, 5.0);

                ENABLE_TELEPORT = builder.comment("是否启用化身传送功能")
                                .translation("config.startres.incarnation.enable_teleport")
                                .define("enable_teleport", true);

                TELEPORT_DISTANCE = builder.comment("化身传送到玩家身边的距离（格）")
                                .translation("config.startres.incarnation.teleport_distance")
                                .defineInRange("teleport_distance", 64.0, 10.0, 1000.0);

                WANDER_RANGE = builder.comment("化身游荡范围（格）")
                                .translation("config.startres.incarnation.wander_range")
                                .defineInRange("wander_range", 8.0, 1.0, 50.0);

                MODE_SWITCH_ENABLED = builder.comment("是否允许化身切换模式（Shift+右键）实验功能!可能导致坏档！")
                                .translation("config.startres.incarnation.mode_switch_enabled")
                                .define("mode_switch_enabled", false);

                HIDE_INCARNATION_BOSSBAR = builder
                                .comment("是否隐藏名字以指定前缀开头的 Boss 血条")
                                .translation("config.startres.incarnation.hide_bossbar")
                                .define("hide_incarnation_bossbar", true);

                BOSSBAR_HIDE_PREFIX = builder.comment("隐藏Boss血条的前缀")
                                .translation("config.startres.incarnation.bossbar_hide_prefix")
                                .define("bossbar_hide_prefix", "化身:");

                COMMON_CONFIG = builder.build();
        }
}
