package fr.gso.katatennis.domain.service.impl;

import fr.gso.katatennis.domain.model.Game;
import fr.gso.katatennis.domain.service.GameService;
import fr.gso.katatennis.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    public List<Game> findGameSets(Integer setId){
        return this.gameRepository.findByTennisSetNumberOrderByIdDesc(setId);
    }

   public Game saveAGame(Game gameToUpdate){
        return this.gameRepository.save(gameToUpdate);
   }
}
