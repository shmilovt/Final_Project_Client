package com.example.final_project_client.Presentation.Search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.final_project_client.R;
import com.example.final_project_client.UserSearchingUtils.UserSearch;

/**
 * Created by TAMIR on 4/29/2018.
 */

public class WarehouseCategoryFragment extends CategoryFragment {
    private View mView;

    public WarehouseCategoryFragment() {
        super("מחסן");
    }

    @Override
    public void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_warehouse_category, container, false);
        return mView;
    }

    @Override
    public void addToUserSearch(UserSearch userSearch) {
        userSearch.addCategory(this);
    }

    @Override
    public void clearData() {

    }
}
