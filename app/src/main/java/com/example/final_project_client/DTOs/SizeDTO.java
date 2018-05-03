package com.example.final_project_client.DTOs;

import com.example.final_project_client.UserSearchingUtils.Size;

/**
 * Created by TAMIR on 4/28/2018.
 */

public class SizeDTO {

    private int minSize;
    private int maxSize;

    public SizeDTO(){}

    public SizeDTO(int minSize, int maxSize) {
        this.minSize = minSize;
        this.maxSize = maxSize;
    }

    public SizeDTO(Size size) {
        minSize = size.getMinSize();
        maxSize = size.getMaxSize();
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
