package com.masivian.test.controller;

import com.masivian.test.db.MysqlBet;
import com.masivian.test.db.MysqlRoulette;
import com.masivian.test.dto.Bet;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class RouletteController {
    private final MysqlRoulette mysqlRoulette;
    private final MysqlBet mysqlBet;

    public RouletteController(MysqlRoulette mysqlRoulette, MysqlBet mysqlBet) {
        this.mysqlRoulette = mysqlRoulette;
        this.mysqlBet = mysqlBet;
    }

    @PostMapping("roulettes")
    public int createRoulette() {
        return mysqlRoulette.add();
    }

    @PutMapping("roulettes/{id}")
    public String openRoulette(@PathVariable int id) {
        return mysqlRoulette.open(id);
    }

    @PostMapping("roulettes/bets")
    public String createBet(@RequestBody Bet bet, @RequestHeader("user-id") int idUser) {
        return mysqlBet.add(bet, idUser);
    }

    @PutMapping("roulettes/{id}/bets")
    public List<Map<String, Object>> closeBets(@PathVariable int id) {
        return mysqlBet.close(id);
    }

    @GetMapping("roulettes")
    public List<Map<String, Object>> getRoulette() {
        return mysqlRoulette.listOfRoulette();
    }
}
