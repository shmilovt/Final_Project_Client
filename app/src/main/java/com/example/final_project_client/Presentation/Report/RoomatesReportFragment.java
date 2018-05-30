package com.example.final_project_client.Presentation.Report;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.example.final_project_client.R;
import com.google.gson.Gson;


public class RoomatesReportFragment extends Fragment implements ReportFragment {
    private View mView;
    private Spinner spinner;

    public RoomatesReportFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_roomates_report, container, false);
        spinner = mView.findViewById(R.id.spinnerRoomatesReport);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


    @Override
    public String getContent() {
        String selectedItemString = spinner.getSelectedItem().toString();
        Integer selectedItem;
        switch (selectedItemString) {
            case "":
                selectedItem = null;
                break;
            case "דירת יחיד":
                selectedItem = 1;
                break;
            case "זוג":
                selectedItem = 0;
                break;
            case "2 שותפים":
                selectedItem = 2;
                break;
            case "3 שותפים":
                selectedItem = 3;
                break;
            case "4 שותפים":
                selectedItem = 4;
                break;
            case "5 שותפים":
                selectedItem = 5;
                break;
            default:
                selectedItem = null;
                break;

        }

        if(selectedItem == null)
            return null;
        else{
            Gson gson = new Gson();
            return gson.toJson(selectedItem);
        }
    }
}
