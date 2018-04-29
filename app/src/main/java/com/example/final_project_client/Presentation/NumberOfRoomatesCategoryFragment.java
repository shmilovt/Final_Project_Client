package com.example.final_project_client.Presentation;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.example.final_project_client.R;

/**
 * Created by TAMIR on 4/28/2018.
 */

public class NumberOfRoomatesCategoryFragment extends CategoryFragment {
    private View mView;
    private Spinner spinner;

    public NumberOfRoomatesCategoryFragment() {
        super("מספר שותפים");
    }

    @Override
    public void onCreate(Bundle savedInstaceState){super.onCreate(savedInstaceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_roomates_category, container, false);
        spinner = mView.findViewById(R.id.spinnerNumberOfRoomates);
        return mView;
    }

    public String getSelectedItem(){
        return spinner.getSelectedItem().toString();
    }
}
