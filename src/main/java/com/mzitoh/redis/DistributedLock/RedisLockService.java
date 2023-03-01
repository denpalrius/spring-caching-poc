package com.mzitoh.redis.DistributedLock;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.support.locks.ExpirableLockRegistry;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

@RequiredArgsConstructor
//@Profile(Constants.REDIS_PROFILE)
@Service
public class RedisLockService {
    private final LockRegistry lockRegistry1;
    private final ExpirableLockRegistry lockRegistry;

    private static final Time DEFAULT_WAIT = new Time(0, TimeUnit.NANOSECONDS);
    private static final String REDIS_LOCK_KEY = "redis_cache_lock_key";

    public String update(Object param) {
        String lockKey = UUID.randomUUID().toString();

        Lock lock = lockRegistry1.obtain(lockKey);

//        Lock lock = lockRegistry.obtain(lockKey);
        boolean success = lock.tryLock();
        String returnVal;

        if (success) {
            returnVal = "redis lock successful";
        } else {
            returnVal = "redis lock unsuccessful";
        }

        // ...
        // update a shared resource
        // ...

        lock.unlock();
        return returnVal;
    }

    public String lock() {
        Lock lock = lockRegistry.obtain(REDIS_LOCK_KEY);
        String returnVal = null;
        if (lock.tryLock()) {
            returnVal = "redis lock successful";
        } else {
            returnVal = "redis lock unsuccessful";
        }
        lock.unlock();

        return returnVal;
    }

    public void failLock() {
        //
    }

    public String properLock() {
        return null;
    }

    public String testWaitLock(long lockWaitSeconds, long threadSleepSeconds) {
        return null;
    }

}
