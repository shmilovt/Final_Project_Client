package com.example.final_project_client.DTOs;

/**
 * Created by TAMIR on 4/28/2018.
 */

public enum DistanceFromUniversity {
    unknown(-1),
    until5(5),
    until10(10),
    until15(15),
    until20(20);

    private final int value;
    private DistanceFromUniversity(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
