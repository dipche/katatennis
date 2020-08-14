package fr.gso.katatennis.domain.service;

import fr.gso.katatennis.domain.model.Player;
import fr.gso.katatennis.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public Player findPlayerByName(String name){
        Optional<Player> player = playerRepository.findByName(name);
        return player.orElseGet(Player::new);
    }

    public Player findPlayerById(Integer plyId){
        Optional<Player> player = playerRepository.findById(plyId);
        return player.orElseGet(Player::new);
    }

    public String registerPlayer(Player player){
        Player savedPlayer = playerRepository.save(player);
        return "Player " + savedPlayer.getName() + " saved";
    }

    public Iterable<Player> findAllPlayers(){
        return playerRepository.findAll();
    }

    public Set<Player> findMatchPlayers(Integer matchId){
        return playerRepository.findAllByMatchId(matchId);
    }

    //todo avant de jouer voir le match du status pour gérer si possible de jouer,
    //todo match status must be startssoon or in progress
}
