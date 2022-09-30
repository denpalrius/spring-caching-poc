package com.cachingpoc.redis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.io.Serializable;

//@RedisHash("cars")
@Entity(name = "cars")
@DynamicUpdate
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Car implements Serializable {
    private static final long serialVersionUID = 7156526077883281623L;
    public enum CarMake{
        Mercedes,
        Audi,
        Peugeot
    }

    @Id
    @SequenceGenerator(name = "SEQ_GEN", sequenceName = "SEQ_CAR", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
    private Long id;

    private String registration;
    private Integer yom;

    @Enumerated(EnumType.STRING)
    private CarMake make;
}
