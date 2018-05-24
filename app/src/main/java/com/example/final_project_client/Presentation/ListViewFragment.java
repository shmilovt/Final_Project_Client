package com.example.final_project_client.Presentation;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.final_project_client.R;

import java.util.List;

/**
 * Created by TAMIR on 3/22/2018.
 */

public class ListViewFragment extends Fragment implements ResultsView {

    private ListView listview;
    private View view;
    private ApartmentBriefDescription[] apartmentBriefDescriptions;

    public ListViewFragment(){
        System.out.println("hello");
    }

    @Override
    public void onCreate(Bundle savedInstaceState){
        super.onCreate(savedInstaceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.records_view, container, false);
       return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        listview = (ListView) view.findViewById(R.id.listView);
        ResultsActivity resultsActivity = ((ResultsActivity)getActivity());
        resultsActivity.getData(this);
    }

    @Override
    public void updateData(ResultsActivity resultsActivity) {

        List<ApartmentBriefDescription> apartmentBriefDescriptionList =  resultsActivity.getApartmentBriefDescriptions();
        ApartmentBriefDescription [] apartmentBriefDescriptions = new ApartmentBriefDescription[apartmentBriefDescriptionList.size()];
        for(int i=0; i<apartmentBriefDescriptions.length; i++){
            apartmentBriefDescriptions[i] = apartmentBriefDescriptionList.get(i);
        }
        MyAdapter myAdapter = new MyAdapter(resultsActivity, apartmentBriefDescriptions);
        listview.setAdapter(myAdapter);

    }
}
