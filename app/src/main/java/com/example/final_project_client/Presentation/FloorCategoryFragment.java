package com.example.final_project_client.Presentation;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.final_project_client.R;

/**
 * Created by TAMIR on 3/22/2018.
 */

public class FloorCategoryFragment extends Fragment {
    private View mView;
    public FloorCategoryFragment(){
    }
    @Override
    public void onCreate(Bundle savedInstaceState){super.onCreate(savedInstaceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_floor_category, container, false);
        return mView;
    }

}