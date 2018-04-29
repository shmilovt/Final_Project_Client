package com.example.final_project_client.Presentation;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.final_project_client.R;

/**
 * Created by TAMIR on 4/28/2018.
 */

public class SizeCategoryFragment extends CategoryFragment {
    private View mView;
    private EditText minSize;
    private EditText maxSize;
    public SizeCategoryFragment(){
        super("גודל דירה (מ\"ר)");
    }
    @Override
    public void onCreate(Bundle savedInstaceState){super.onCreate(savedInstaceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_size_category, container, false);
        minSize = (EditText) mView.findViewById(R.id.editTextMinSize);
        maxSize = (EditText) mView.findViewById(R.id.editTextMaxSize);
        return mView;
    }


    public String getMinCost() {
        return minSize.getText().toString();
    }

    public String getMaxCost() {
        return maxSize.getText().toString();
    }
}
