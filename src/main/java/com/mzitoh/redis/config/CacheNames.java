package com.mzitoh.redis.config;

public enum CacheNames {
    CARS("car"),
    PLANETS("planets");

    private final String name;

    private CacheNames(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
