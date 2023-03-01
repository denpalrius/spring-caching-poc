package com.mzitoh.redis.DistributedLock;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/redis_lock")
public class LockTestController {
    private final RedisLockService redisLockService;
    private final LockService lockService;

    @RequestMapping(value = {"/lock"}, method = RequestMethod.GET)
    public String lock() {
        lockService.lock("testType");

        return redisLockService.update(null);
//        return redisLockService.lock();
    }

    @RequestMapping(value = {"/properLock"}, method = RequestMethod.PUT)
    public String properLock() {
        return redisLockService.properLock();
    }

    @RequestMapping(value = {"/failLock"}, method = RequestMethod.PUT)
    public String failLock() {
        redisLockService.failLock();
        return "fail lock called, output in logs";
    }
}
