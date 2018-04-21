package com.example.final_project_client.Presentation;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.final_project_client.R;

/**
 * Created by TAMIR on 3/22/2018.
 */

public class CostCategoryFragment extends Fragment {
    private View mView;
    private EditText minCost ;
    private EditText maxCost ;
    public CostCategoryFragment(){
    }
    @Override
    public void onCreate(Bundle savedInstaceState){super.onCreate(savedInstaceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_cost_category, container, false);
        minCost = (EditText) mView.findViewById(R.id.editTextMinCost);
        maxCost = (EditText) mView.findViewById(R.id.editTextMaxCost);
        return mView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(!minCost.getText().toString().equals(""))
            outState.putInt("minCost", Integer.parseInt(minCost.getText().toString()));

        if(!maxCost.getText().toString().equals(""))
        outState.putInt("maxCost" , Integer.parseInt(maxCost.getText().toString()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState!=null){
            Integer minimalCost = savedInstanceState.getInt("minCost");
            Integer maximalCost = savedInstanceState.getInt("maxCost");
            if(minimalCost!=null)
                ( (EditText) mView.findViewById(R.id.editTextMinCost)).setText(minimalCost);
            if(maximalCost!=null)
                ( (EditText) mView.findViewById(R.id.editTextMaxCost)).setText(maximalCost);
        }
    }
}
