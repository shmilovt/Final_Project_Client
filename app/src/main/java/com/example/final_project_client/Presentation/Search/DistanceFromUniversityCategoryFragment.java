package com.example.final_project_client.Presentation.Search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.example.final_project_client.R;
import com.example.final_project_client.UserSearchingUtils.UserSearch;

/**
 * Created by TAMIR on 4/28/2018.
 */

public class DistanceFromUniversityCategoryFragment extends CategoryFragment {
    private View mView;
    private Spinner spinner;
    private String[] options;


    public DistanceFromUniversityCategoryFragment() {
        super("מרחק מאוניברסיטה (דקות הליכה)");
    }

    @Override
    public void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_distance_category, container, false);
        spinner = (Spinner) mView.findViewById(R.id.spinnerDistanceFromUniversity);
        options = getResources().getStringArray(R.array.distanceFromUniversity);

        return mView;
    }


    public String getSelectedItem() {
        return spinner.getSelectedItem().toString();
    }

    @Override
    public void addToUserSearch(UserSearch userSearch) {
        userSearch.addCategory(this);
    }

    @Override
    public void clearData() {
        spinner.setSelection(0);

    }
}
