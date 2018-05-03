package com.example.final_project_client.UserSearchingUtils;

/**
 * Created by TAMIR on 4/29/2018.
 */

public class Size {
    private int minSize;
    private int maxSize;

    public Size(){};

    public Size(int minSize, int maxSize) {
        this.minSize = minSize;
        this.maxSize = maxSize;
    }


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
}
