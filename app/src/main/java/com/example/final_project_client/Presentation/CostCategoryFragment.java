package com.example.final_project_client.Presentation;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.final_project_client.R;
import com.example.final_project_client.UserSearchingUtils.UserSearch;

/**
 * Created by TAMIR on 3/22/2018.
 */

public class CostCategoryFragment extends CategoryFragment {
    private View mView;
    private EditText minCost;
    private EditText maxCost;

    public CostCategoryFragment() {
        super("מחיר");
    }

    @Override
    public void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_cost_category, container, false);
        minCost = (EditText) mView.findViewById(R.id.editTextMinCost);
        maxCost = (EditText) mView.findViewById(R.id.editTextMaxCost);
        return mView;
    }


    public String getMinCost() {
        return minCost.getText().toString();
    }

    public String getMaxCost() {
        return maxCost.getText().toString();
    }
    @Override
    public void addToUserSearch(UserSearch userSearch) {
        userSearch.addCategory(this);
    }

}
