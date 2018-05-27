package com.example.final_project_client.Presentation;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.final_project_client.R;
import com.google.gson.Gson;

/**
 * Created by TAMIR on 5/24/2018.
 */

public class BriefDescriptionFragment extends Fragment {
    private View mView;
    private ApartmentBriefDescription apartmentBriefDescription;
    public static final String BRIEF_DESCRIPTION = "com.example.final_project_client.BRIEF_DESCRIPTION";

    public BriefDescriptionFragment(){}

    public static final BriefDescriptionFragment getInstance(ApartmentBriefDescription apartmentBriefDescription) {
        BriefDescriptionFragment newBriefDescriptionFragment = new BriefDescriptionFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BRIEF_DESCRIPTION, new Gson().toJson(apartmentBriefDescription));
        newBriefDescriptionFragment.setArguments(bundle);
        return newBriefDescriptionFragment;
    }
    @Override
    public void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        apartmentBriefDescription = (new Gson()).fromJson(getArguments().getString(BRIEF_DESCRIPTION), ApartmentBriefDescription.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.listview_item, container, false);
        TextView costTxt = (TextView) mView.findViewById(R.id.briefDescriptionCost);
        TextView sheqelTxt = (TextView) mView.findViewById(R.id.briefDescriptionSheqel);
        TextView addressTxt = (TextView) mView.findViewById(R.id.briefDescriptionAddress);
        TextView numberOfRoomatesTxt = (TextView) mView.findViewById(R.id.briefDescriptionRoomates);
        TextView numberOfRoomsTxt = (TextView) mView.findViewById(R.id.briefDescriptionRooms);


        String address = apartmentBriefDescription.getStreet()+" "+apartmentBriefDescription.getBuildingNumber()+", "+apartmentBriefDescription.getNeighborhood();
        int cost =  apartmentBriefDescription.getCost();
        String costString ;
        if(cost>=0){
            costString  = ""+cost;
        }
        else{
            costString = "-";
            sheqelTxt.setText("");
        }

        double numberOfRooms = apartmentBriefDescription.getNumberOfRooms();
        String numberOfRoomsString ;
        if(numberOfRooms%1==0)
            numberOfRoomsString = ""+(int)numberOfRooms;
        else
            numberOfRoomsString = ""+numberOfRooms;

        int numberOfRoomates = apartmentBriefDescription.getNumberOfRoomates();
        String numberOfRoomatesString = ""+numberOfRoomates;

        addressTxt.setText(address);
        costTxt.setText(costString);
        numberOfRoomsTxt.setText(numberOfRoomsString);
        numberOfRoomatesTxt.setText(numberOfRoomatesString);
        return mView;
    }



}
