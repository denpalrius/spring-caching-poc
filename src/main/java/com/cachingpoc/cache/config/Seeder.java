package com.cachingpoc.cache.config;

import com.cachingpoc.cache.model.Player;
import com.cachingpoc.cache.repositories.PlayersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Component
public class Seeder {

    @Autowired
    PlayersRepository playersRepository;

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void seed() {
        List<Player> players = getPlayers();
        playersRepository.saveAll(players);
    }

    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<>();

        players.add(new Player("Dennis", "Tech", 11));
        players.add(new Player("Vasu", "Tech", 10));
        players.add(new Player("Emilio", "Tech", 7));

        players.add(new Player("Lionel Messi", "Red team", 10));
        players.add(new Player("Emiliano Martínez", "Argentina", 1));
        players.add(new Player("Julián Álvarez", "Argentina", 9));

        return players;
    }
}
