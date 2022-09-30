package com.cachingpoc.terracota.config;

import com.cachingpoc.terracota.models.Player;
import com.cachingpoc.terracota.repositories.PlayersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class Seeder {

    private final PlayersRepository playersRepository;

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void seed() {
        List<Player> players = getPlayers();
        playersRepository.saveAll(players);
    }

    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<>();

        players.add(new Player("Dennis", "Street Bulls", 11));
        players.add(new Player("Vasu", "Street Bulls", 10));
        players.add(new Player("Emilio", "Street Bulls", 7));

        players.add(new Player("Lionel Messi", "La Albiceleste", 10));
        players.add(new Player("Emiliano Martínez", "La Albiceleste", 1));
        players.add(new Player("Julián Álvarez", "La Albiceleste", 9));

        return players;
    }
}
