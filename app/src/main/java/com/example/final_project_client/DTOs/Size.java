package com.example.final_project_client.DTOs;

/**
 * Created by TAMIR on 4/28/2018.
 */

public class Size {
    public int getMinSize() {
        return minSize;
    }

    public void setMinSize(int minSize) {
        this.minSize = minSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    private int minSize;
    private int maxSize;

    public Size(int minSize, int maxSize) {
        this.minSize = minSize;
        this.maxSize = maxSize;
    }
}
