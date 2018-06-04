package com.example.final_project_client.Presentation.Report;

/**
 * Created by TAMIR on 5/28/2018.
 */

public enum ReportCategory {

    address(1),
    floor(2),
    cost(3),
    size(4),
    rooms(5),
    roomates(6),
    furniture(7),
    garden(8),
    protectedSpace(9),
    warehouse(10),
    balcony(11),
    animals(12);

    private final int value;
    private ReportCategory(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
