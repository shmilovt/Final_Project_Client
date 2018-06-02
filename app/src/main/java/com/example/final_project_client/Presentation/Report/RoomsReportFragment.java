package com.example.final_project_client.Presentation.Report;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.example.final_project_client.R;
import com.google.gson.Gson;


public class RoomsReportFragment extends Fragment implements ReportFragment {

    private View mView;
    private Spinner spinnerNumberOfRooms;

    public RoomsReportFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_rooms_report, container, false);
        spinnerNumberOfRooms = (Spinner) mView.findViewById(R.id.spinnerNumberOfRoomsReport);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public String getContent() {
        Gson gson = new Gson();
        int selectedItemPosition = spinnerNumberOfRooms.getSelectedItemPosition();
        Double numberOfRooms;
        switch (selectedItemPosition) {
            case 0:
                return null;
            case 1:
                numberOfRooms =  1.0;
                return gson.toJson(numberOfRooms) ;
            default:
                numberOfRooms = 1 + selectedItemPosition*0.5;
                return gson.toJson(numberOfRooms) ;

        }

    }
}
