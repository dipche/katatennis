package fr.gso.katatennis.domain.service.impl;

import fr.gso.katatennis.domain.model.Game;
import fr.gso.katatennis.domain.service.GameService;
import fr.gso.katatennis.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    public List<Game> findGameSets(Integer setId){
        return gameRepository.findByTennisSetIdOrderByIdDesc(setId);
    }
}