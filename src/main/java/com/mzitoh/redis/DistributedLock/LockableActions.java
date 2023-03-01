package com.mzitoh.redis.DistributedLock;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.function.Function;
import java.util.function.Supplier;

@Builder
@Data
@AllArgsConstructor
public class LockableActions<T> {
    private Supplier<T> onSuccess;
    private Supplier<T> onFailure;
    private Function<Exception, T> onError;
}