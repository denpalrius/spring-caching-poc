package com.mzitoh.ehcache.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString()
@AllArgsConstructor
public class Player implements Serializable {
    @Id
    @GeneratedValue
    public Long id;
    public String name;
    public String teamName;
    public Integer jerseyNumber;

    public Player() {
    }

    public Player(String name, String teamName, Integer jerseyNumber) {
        this.name = name;
        this.teamName = teamName;
        this.jerseyNumber = jerseyNumber;
    }
}
