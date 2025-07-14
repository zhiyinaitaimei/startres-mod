package com.example.startres.incarnation;

public enum IncarnationMode {
    FOLLOW("follow", "incarnation.mode.follow"),
    IDLE("idle", "incarnation.mode.idle"),
    WANDER("wander", "incarnation.mode.wander");

    private final String id;
    private final String displayName;

    IncarnationMode(String id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static IncarnationMode fromId(String id) {
        for (IncarnationMode mode : values()) {
            if (mode.id.equals(id)) {
                return mode;
            }
        }
        return FOLLOW; // 默认跟随模式
    }

    public IncarnationMode next() {
        IncarnationMode[] modes = values();
        int currentIndex = this.ordinal();
        int nextIndex = (currentIndex + 1) % modes.length;
        return modes[nextIndex];
    }
}