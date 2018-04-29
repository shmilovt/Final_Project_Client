package com.example.final_project_client.Presentation;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.final_project_client.R;

/**
 * Created by TAMIR on 4/28/2018.
 */

public class ProtectedSpaceCategoryFragment extends CategoryFragment {
    private View mView;

    public ProtectedSpaceCategoryFragment() {
        super("מרחב מוגן");
    }

    @Override
    public void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_protectedspace_category, container, false);
        return mView;
    }
}
