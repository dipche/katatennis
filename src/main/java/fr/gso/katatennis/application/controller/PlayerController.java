package fr.gso.katatennis.application.controller;

import fr.gso.katatennis.domain.model.Player;
import fr.gso.katatennis.domain.service.impl.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public @ResponseBody String addNewPlayer(@RequestParam String name) {
        Player player = new Player(name);
        return playerService.registerPlayer(player);
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Player> getAllPlayers() {
        return playerService.findAllPlayers();
    }
}
