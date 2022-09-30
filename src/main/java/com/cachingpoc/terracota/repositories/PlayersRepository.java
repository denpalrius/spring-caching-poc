package com.cachingpoc.terracota.repositories;

import com.cachingpoc.terracota.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayersRepository extends JpaRepository<Player, Long> {
    Player findByName(String name);
}
