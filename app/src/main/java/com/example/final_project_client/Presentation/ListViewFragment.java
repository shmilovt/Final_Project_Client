package com.example.final_project_client.Presentation;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.final_project_client.R;

/**
 * Created by TAMIR on 3/22/2018.
 */

public class ListViewFragment extends Fragment {

    private ListView listview;
    private View view;

    public ListViewFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstaceState){super.onCreate(savedInstaceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.records_view, container, false);
       return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        listview = (ListView) view.findViewById(R.id.listView);
    }
}
