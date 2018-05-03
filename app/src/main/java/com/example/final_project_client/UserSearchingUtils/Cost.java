package com.example.final_project_client.UserSearchingUtils;

/**
 * Created by TAMIR on 4/29/2018.
 */

public class Cost {
    private int minCost;
    private int maxCost;

    public Cost(){}
    public Cost(int minCost, int maxCost){
        this.minCost = minCost;
        this.maxCost = maxCost;
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
