package com.example.final_project_client.DTOs;

/**
 * Created by TAMIR on 4/21/2018.
 */

public enum NumberOfRoomates {
    unknown(-1),
    couple(0),
    one(1),
    two(2),
    three(3),
    four(4),
    fiveOrMore(5);


    private final int value;
    private NumberOfRoomates(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}

