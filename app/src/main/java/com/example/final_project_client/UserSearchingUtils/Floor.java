package com.example.final_project_client.UserSearchingUtils;

/**
 * Created by TAMIR on 4/29/2018.
 */

public class Floor {
    private int minFloor;
    private int maxFloor;

    public Floor(){}
    public Floor(int minFloor, int maxFloor){
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
    }

    public int getMinFloor() {
        return minFloor;
    }

    public void setMinFloor(int minFloor) {
        this.minFloor = minFloor;
    }

    public int getMaxFloor() {
        return maxFloor;
    }

    public void setMaxFloor(int maxFloor) {
        this.maxFloor = maxFloor;
    }
}
