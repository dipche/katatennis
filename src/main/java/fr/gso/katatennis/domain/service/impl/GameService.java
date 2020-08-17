package fr.gso.katatennis.domain.service.impl;

import fr.gso.katatennis.domain.model.Game;
import fr.gso.katatennis.domain.service.IGameService;
import fr.gso.katatennis.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService implements IGameService {

    @Autowired
    private GameRepository gameRepository;

    public List<Game> findSetGames(Integer setId){
        return gameRepository.findByTennisSetIdOrderByIdIdDesc(setId);
    }
}
