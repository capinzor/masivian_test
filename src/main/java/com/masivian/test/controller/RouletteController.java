package com.masivian.test.controller;

import com.masivian.test.db.MysqlRoulette;
import com.masivian.test.dto.Bet;
import com.masivian.test.dto.Roulette;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class RouletteController {

    private final MysqlRoulette mysqlRoulette;

    public RouletteController(MysqlRoulette mysqlRoulette) {
        this.mysqlRoulette = mysqlRoulette;
    }

    @PostMapping("roulettes")
    public int createRoulette() {
        return mysqlRoulette.add();
    }

    @PutMapping("roulettes/:id")
    public boolean openRoulette(@PathVariable int id) {
        return true;
    }

    @PostMapping("roulettes/bets")
    public boolean createBet(@RequestBody Bet bet, @RequestHeader int idUser) {
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
