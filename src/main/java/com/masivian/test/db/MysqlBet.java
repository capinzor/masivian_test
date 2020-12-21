package com.masivian.test.db;

import com.masivian.test.dto.Bet;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class MysqlBet {
    private final JdbcTemplate jdbcTemplate;

    public MysqlBet(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private int rouletteIsValid(int rouletteId) {
        Integer stateRoulette;
        try {
            stateRoulette = jdbcTemplate.queryForObject("SELECT state FROM roulette WHERE id = ?", Integer.class, rouletteId);
        } catch (EmptyResultDataAccessException e) {
            return -1;
        }
        return stateRoulette != null ? stateRoulette : -1;
    }

    private String validation(int stateRoulette, Bet bet) {
        if (stateRoulette == -1) {
            return "La Ruleta aun no ha sido creada";
        } else if (stateRoulette != 1) {
            return "La ruleta aún no ha sido abierta";
        } else if (bet.getKind().equals("number")) {
            int betValue = Integer.parseInt(bet.getValue());
            if (betValue < 0 || betValue > 36) {
                return "Número inválido, su apuesta debe estar entre 0 y 36";
            }
        } else if (bet.getKind().equals("color")) {
            if (!bet.getValue().equals("black") && !bet.getValue().equals("red")) {
                return "Color inválido, su apuesta debe ser rojo o negro";
            }
        } else if (!bet.getKind().equals("color") && !bet.getKind().equals("number")) {
            return "La seleccion de tipo de apuesta debe ser  color o número";
        } else if (bet.getAmount() < 0 || bet.getAmount() > 10000) {
            return "La apuesta no puede ser inferior a 0 ni superior a 10000";
        }
        return "";
    }

    public String add(Bet bet, int userId) {
        int stateRoulette = rouletteIsValid(bet.getRouletteId());
        String message = validation(stateRoulette, bet);
        if (message.length() != 0) {
            return message;
        }
        int rows = jdbcTemplate.update("INSERT INTO bet (kind,value,amount,gain,id_roulette, id_user) values(?,?,?,?,?,?) ",
                bet.getKind(), bet.getValue(), bet.getAmount(), 0, bet.getRouletteId(), userId);
        return rows == 1 ? "Operación exitosa" : "La operación no se pudo realizar";
    }

    private String[] winnerNumber() {
        int number = (int) Math.floor(Math.random() * 37);
        String winnerColor = number % 2 == 0 ? "red" : "black";
        return new String[]{Integer.toString(number), winnerColor};
    }

    public List<Map<String, Object>> close(int rouletteId) {
        int stateRoulette = rouletteIsValid(rouletteId);
        String[] winnerArray = winnerNumber();
        if (stateRoulette == 1) {
            jdbcTemplate.update("UPDATE bet SET gain=amount*5 WHERE id_roulette=? AND value = ?", rouletteId, winnerArray[0]);
            jdbcTemplate.update("UPDATE bet SET gain=amount*1.8 WHERE id_roulette=? AND value = ?", rouletteId, winnerArray[1]);
            jdbcTemplate.update("UPDATE roulette SET state=false WHERE id=?", rouletteId);
        }
        return jdbcTemplate.queryForList("SELECT * FROM bet WHERE id_roulette=?", rouletteId);
    }
}
