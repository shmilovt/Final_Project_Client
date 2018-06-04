package com.example.final_project_client.Presentation.Search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.final_project_client.R;
import com.example.final_project_client.UserSearchingUtils.UserSearch;

/**
 * Created by TAMIR on 3/22/2018.
 */

public class FloorCategoryFragment extends CategoryFragment {
    private View mView;
    private EditText minFloor;
    private EditText maxFloor;
    public FloorCategoryFragment(){
        super("קומה");
    }
    @Override
    public void onCreate(Bundle savedInstaceState){super.onCreate(savedInstaceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_floor_category, container, false);
        minFloor = mView.findViewById(R.id.editTextMinFloor);
        maxFloor = mView.findViewById(R.id.editTextMaxFloor);
        return mView;
    }


    public String getMinFloor() {
        return minFloor.getText().toString();
    }

    public String getMaxFloor() {
        return maxFloor.getText().toString();
    }
    @Override
    public void addToUserSearch(UserSearch userSearch) {
        userSearch.addCategory(this);
    }

    @Override
    public void clearData() {

    }

}