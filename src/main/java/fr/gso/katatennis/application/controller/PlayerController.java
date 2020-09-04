package fr.gso.katatennis.application.controller;

import fr.gso.katatennis.application.request.CreatePlayerJsonCommand;
import fr.gso.katatennis.application.response.CreatePlayerJsonResponse;
import fr.gso.katatennis.domain.model.Player;
import fr.gso.katatennis.domain.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @GetMapping(path="/view/{name}")
    public Player viewPlayer(@PathVariable(value = "name") String name) {
        return playerService.findPlayerByName(name);
    }


    @PostMapping(path="/add")
    public @ResponseBody CreatePlayerJsonResponse addNewPlayer(@RequestBody CreatePlayerJsonCommand createPlayerJsonCommand) {
        Player player = new Player(createPlayerJsonCommand.getPlayerName());
        Player playerCreated = playerService.registerPlayer(player);
        return new CreatePlayerJsonResponse(playerCreated.getId(), playerCreated.getName());
    }

    @GetMapping(path="/all")
    public @ResponseBody
    List<Player> getAllPlayers() {
        return (List<Player>)playerService.findAllPlayers();
    }
}
