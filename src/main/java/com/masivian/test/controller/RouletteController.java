package com.masivian.test.controller;

import com.masivian.test.dto.Bet;
import com.masivian.test.dto.Roulette;
import org.springframework.web.bind.annotation.*;

@RestController
public class RouletteController {
    @PostMapping("roulettes")
    public int createRoulette(){
        return 0;
    }

    @PutMapping("roulettes/:id")
    public boolean openRoulette(@PathVariable int id){
        return true;
    }

    @PostMapping("roulettes/bets")
    public boolean createBet(@RequestBody Bet bet,@RequestHeader int idUser){
        return false;
    }

    @PutMapping("roulettes/:id/bets")
    public  String[] closeBets(@PathVariable int id){
        return null;
    }

    @GetMapping("roulettes")
    public Roulette[] getRoulette(){
        return null;
    }

}
