package com.cachingpoc.redis.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

//@RedisHash("planets")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Planet implements Serializable {
    private static final long serialVersionUID = 7156526077883281623L;

    private String name;
    private Integer distanceFromSun;
}
