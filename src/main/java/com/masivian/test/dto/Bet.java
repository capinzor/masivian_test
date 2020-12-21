package com.masivian.test.dto;

import lombok.Data;

@Data
public class Bet {
    private String kind;
    private String value;
    private int amount;
    private int rouletteId;
}
