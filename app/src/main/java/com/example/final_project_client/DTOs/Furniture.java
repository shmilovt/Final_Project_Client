package com.example.final_project_client.DTOs;

public enum Furniture {
    unknown(-1),
    without(0),
    partial(1),
    full(2);



    private final int value;
    private Furniture(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
