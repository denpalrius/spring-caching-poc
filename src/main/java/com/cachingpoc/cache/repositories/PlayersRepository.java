package com.cachingpoc.cache.repositories;

import com.cachingpoc.cache.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayersRepository extends JpaRepository<Player, Long> {
    Player findByName(String name);
}
