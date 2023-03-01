package com.mzitoh.redis.DistributedLock;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.function.Function;
import java.util.function.Supplier;

@Log4j2
@RequiredArgsConstructor
@Service
public class LockService {
    private static final Time DEFAULT_WAIT = new Time(0, TimeUnit.NANOSECONDS);
    private static final String REDIS_LOCK_KEY = "redis_cache_lock_key";
    private final LockRegistry lockRegistry;
    protected String lock(Time wait, String type) {
        LockableActions<String> actions = createActions(type);
        return lock(lockRegistry, actions, wait);
    }

    protected String lock(String type) {
        LockableActions<String> actions = createActions(type);
        return lock(lockRegistry, actions, DEFAULT_WAIT);
    }

    // TODO: Remove threads
    public String testWaitLock(long lockWaitSeconds, long threadSleepSeconds) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Time waitTime = new Time(lockWaitSeconds, TimeUnit.SECONDS);
        Runnable threadOne = createTestThread(lockRegistry, waitTime, threadSleepSeconds);
        Runnable threadTwo = createTestThread(lockRegistry, waitTime, threadSleepSeconds);

        executor.submit(threadOne);
        executor.submit(threadTwo);

        executor.shutdown();

        return "did lock stuff";
    }

    private Runnable createTestThread(LockRegistry lockRegistry, Time waitTime, long threadSleepSeconds) {
        return () -> {
            long halfSleep = (threadSleepSeconds * 1000) / 2;
            UUID id = UUID.randomUUID();
            Lock lock = null;

            try {
                lock = lockRegistry.obtain(REDIS_LOCK_KEY);
            } catch (Exception e) {
                log.info(String.format("Unable to obtain lock: %s", REDIS_LOCK_KEY));
            }

            try {
                log.info("attempting to grab a lock for thread: " + id);
                if (lock.tryLock(waitTime.getDuration(), waitTime.getTimeUnit())) {
                    log.info("Locking the thread: " + id);
                    Thread.sleep(halfSleep);
                    log.info(id + " is doing some processing!!!");
                    Thread.sleep(halfSleep);
                } else {
                    log.info("Unable to lock the thread: " + id);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                log.info("Unlocking the thread: " + id);
            }
        };
    }

    private <T> T lock(LockRegistry lockRegistry, LockableActions<T> lockableActions, Time waitTime) {
        Lock lock = null;

        try {
            //even though method takes an object due to the distributed lock interface both LockRegistries force
            // you to use a string as the key
            lock = lockRegistry.obtain(REDIS_LOCK_KEY);
        } catch (Exception e) {
            log.info(String.format("Unable to obtain lock: %s", REDIS_LOCK_KEY));
            return lockableActions.getOnError().apply(e);
        }

        T successful;
        try {
            waitTime = waitTime == null ? DEFAULT_WAIT : waitTime;
            successful = lock.tryLock(waitTime.getDuration(), waitTime.getTimeUnit()) ?
                    lockableActions.getOnSuccess().get() :
                    lockableActions.getOnFailure().get();
        } catch (Exception e) {
            //this should be a log message
            e.printStackTrace();
//            log.error(e);

            successful = lockableActions.getOnError().apply(e);
        } finally {
            try {
                lock.unlock();
            } catch (Exception e) {
                //we don't care about this case as if we can't unlock it, it means this lock is owned by someone else
            }
        }

        return successful;
    }

    private LockableActions<String> createActions(String typeOfLock) {
        Supplier<String> success = () -> String.format("successfully locked with %s", typeOfLock);
        Supplier<String> failure = () -> String.format("failed to lock with %s", typeOfLock);
        Function<Exception, String> exception = (e) -> String.format("%s lock went off the rails on a crazy train: %s", typeOfLock, e.getMessage());

        return new LockableActions<>(success, failure, exception);
    }
}

