package com.example.final_project_client.Presentation.Search;

import android.app.Fragment;

import com.example.final_project_client.UserSearchingUtils.UserSearch;

/**
 * Created by TAMIR on 4/29/2018.
 */

public abstract class CategoryFragment extends Fragment{
    private String name;
    private int priority;



    public CategoryFragment(String name){
        this.name = name;
        priority = -1;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public abstract void addToUserSearch(UserSearch userSearch);

    public abstract void clearData();
}
