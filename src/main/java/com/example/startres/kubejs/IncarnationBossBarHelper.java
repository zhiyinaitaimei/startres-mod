package com.example.startres.kubejs;

import net.minecraft.world.entity.Entity;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class IncarnationBossBarHelper {

    private static final Map<Class<?>, Field> BOSS_EVENT_FIELD_CACHE = new ConcurrentHashMap<>();
    private static final Map<Class<?>, Method> SET_VISIBLE_METHOD_CACHE = new ConcurrentHashMap<>();

    /**
     * 在KubeJS召唤化身后立即调用
     * 
     * @param entity 要隐藏boss血条的实体
     * @return 是否成功隐藏了boss血条
     */
    public static boolean hideIncarnationBossBar(Entity entity) {
        if (entity == null)
            return false;

        // 确保实体有化身标签
        if (!entity.getTags().contains("incarnation")) {
            entity.addTag("incarnation");
        }

        entity.addTag("incarnation_bossbar_handled");

        try {
            Class<?> entityClass = entity.getClass();

            Field bossEventField = BOSS_EVENT_FIELD_CACHE.computeIfAbsent(entityClass, clazz -> {
                Class<?> currentClass = clazz;
                while (currentClass != null) {
                    for (Field f : currentClass.getDeclaredFields()) {
                        if (f.getType().getSimpleName().toLowerCase().contains("bossevent")) {
                            f.setAccessible(true);
                            return f;
                        }
                    }
                    currentClass = currentClass.getSuperclass();
                }
                return null;
            });

            if (bossEventField != null) {
                Object bossEvent = bossEventField.get(entity);
                if (bossEvent != null) {
                    Method setVisible = SET_VISIBLE_METHOD_CACHE.computeIfAbsent(bossEvent.getClass(), clazz -> {
                        try {
                            return clazz.getMethod("setVisible", boolean.class);
                        } catch (NoSuchMethodException e) {
                            return null;
                        }
                    });

                    if (setVisible != null) {
                        setVisible.invoke(bossEvent, false);
                        return true; // 成功隐藏boss血条
                    }
                }
            }
        } catch (Exception e) {
        }

        return false; // 没有boss血条或隐藏失败
    }

    public static boolean showIncarnationBossBar(Entity entity) {
        if (entity == null)
            return false;

        try {
            Class<?> entityClass = entity.getClass();
            Field bossEventField = BOSS_EVENT_FIELD_CACHE.get(entityClass);

            if (bossEventField != null) {
                Object bossEvent = bossEventField.get(entity);
                if (bossEvent != null) {
                    Method setVisible = SET_VISIBLE_METHOD_CACHE.get(bossEvent.getClass());
                    if (setVisible != null) {
                        setVisible.invoke(bossEvent, true);
                        return true;
                    }
                }
            }
        } catch (Exception e) {
        }

        return false;
    }

    public static boolean hasBossBar(Entity entity) {
        if (entity == null)
            return false;

        try {
            Class<?> entityClass = entity.getClass();
            Field bossEventField = BOSS_EVENT_FIELD_CACHE.get(entityClass);

            if (bossEventField == null) {
                // 尝试查找boss血条字段
                bossEventField = BOSS_EVENT_FIELD_CACHE.computeIfAbsent(entityClass, clazz -> {
                    Class<?> currentClass = clazz;
                    while (currentClass != null) {
                        for (Field f : currentClass.getDeclaredFields()) {
                            if (f.getType().getSimpleName().toLowerCase().contains("bossevent")) {
                                f.setAccessible(true);
                                return f;
                            }
                        }
                        currentClass = currentClass.getSuperclass();
                    }
                    return null;
                });
            }

            if (bossEventField != null) {
                Object bossEvent = bossEventField.get(entity);
                return bossEvent != null;
            }
        } catch (Exception e) {
            // 静默处理异常
        }

        return false;
    }

    public static void clearCache() {
        BOSS_EVENT_FIELD_CACHE.clear();
        SET_VISIBLE_METHOD_CACHE.clear();
    }
} // 清理缓存