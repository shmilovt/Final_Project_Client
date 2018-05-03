package com.example.final_project_client.Presentation;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.final_project_client.R;
import com.example.final_project_client.UserSearchingUtils.UserSearch;

/**
 * Created by TAMIR on 4/28/2018.
 */

public class AnimalsCategoryFragment extends CategoryFragment {
    private View mView;

    public AnimalsCategoryFragment() {
        super("חיות");
    }

    @Override
    public void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_animals_category, container, false);
        return mView;
    }

    @Override
    public void addToUserSearch(UserSearch userSearch) {
        userSearch.addCategory(this);
    }
}
