package com.example.final_project_client.DTOs;

import com.example.final_project_client.UserSearchingUtils.Floor;

public class FloorDTO {
    private int minFloor;
    private int maxFloor;

    public FloorDTO(){}
    public FloorDTO(int minFloor, int maxFloor){
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
    }

    public FloorDTO(Floor floor) {
        if(floor!=null){
        minFloor = floor.getMinFloor();
        maxFloor = floor.getMaxFloor();
        }
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
