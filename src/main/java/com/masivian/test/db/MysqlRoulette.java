package com.masivian.test.db;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MysqlRoulette {
    private final JdbcTemplate jdbcTemplate;

    public MysqlRoulette(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int add() {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("roulette")
                .usingGeneratedKeyColumns("id");
        Map<String, Boolean> parameters = new HashMap<>();
        parameters.put("state", false);
        Number id = simpleJdbcInsert.executeAndReturnKey(parameters);
        return id.intValue();
    }

    public String open(int id) {
        int rows = jdbcTemplate.update("UPDATE roulette SET state=1 where id=?", id);
        return rows == 1 ? "Operación exitosa" : "La operación no se pudo realizar";
    }

    public List<Map<String, Object>> listOfRoulette() {
        return jdbcTemplate.queryForList("SELECT * FROM roulette");
    }

}