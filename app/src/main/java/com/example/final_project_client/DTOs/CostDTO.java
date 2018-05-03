package com.example.final_project_client.DTOs;

import com.example.final_project_client.UserSearchingUtils.Cost;

public class CostDTO { // -1 indicate no limit
    private int minCost;
    private int maxCost;

    public CostDTO(){}
    public CostDTO(int minCost, int maxCost){
        this.minCost = minCost;
        this.maxCost = maxCost;
    }

    public CostDTO(Cost cost) {
        minCost = cost.getMinCost();
        maxCost = cost.getMaxCost();
    }


    public int getMinCost() {
        return minCost;
    }

    public void setMinCost(int minCost) {
        this.minCost = minCost;
    }

    public int getMaxCost() {
        return maxCost;
    }

    public void setMaxCost(int maxCost) {
        this.maxCost = maxCost;
    }
}
