package com.mzitoh.redis.DistributedLock;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.concurrent.TimeUnit;

@Builder
@Data
@AllArgsConstructor
public class Time {
    private long duration;
    private TimeUnit timeUnit;
}