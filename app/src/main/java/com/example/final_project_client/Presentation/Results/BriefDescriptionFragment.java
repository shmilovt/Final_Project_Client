package com.example.final_project_client.Presentation.Results;

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
    private int position;
    private int totalApartmentsInBuilding;
    public static final String BRIEF_DESCRIPTION = "com.example.final_project_client.BRIEF_DESCRIPTION";
    public static final String POSITION = "com.example.final_project_client.POSITION";
    public static final String TOTAL_APARTMENTS = "com.example.final_project_client.TOTAL_APARTMENTS";

    public BriefDescriptionFragment(){}

    public static final BriefDescriptionFragment getInstance(ApartmentBriefDescription apartmentBriefDescription , int position, int totalApartmentsInBuilding) {
        BriefDescriptionFragment newBriefDescriptionFragment = new BriefDescriptionFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BRIEF_DESCRIPTION, new Gson().toJson(apartmentBriefDescription));
        bundle.putInt(POSITION, position);
        bundle.putInt(TOTAL_APARTMENTS , totalApartmentsInBuilding);
        newBriefDescriptionFragment.setArguments(bundle);
        return newBriefDescriptionFragment;
    }
    @Override
    public void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        apartmentBriefDescription = (new Gson()).fromJson(getArguments().getString(BRIEF_DESCRIPTION), ApartmentBriefDescription.class);
        position = getArguments().getInt(POSITION);
        totalApartmentsInBuilding = getArguments().getInt(TOTAL_APARTMENTS);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_map_brief_description, container, false);
        TextView costTxt = (TextView) mView.findViewById(R.id.mapBriefDescriptionCost);
        TextView sheqelTxt = (TextView) mView.findViewById(R.id.mapBriefDescriptionSheqel);
        TextView addressTxt = (TextView) mView.findViewById(R.id.mapBriefDescriptionAddress);
        TextView numberOfRoomatesTxt = (TextView) mView.findViewById(R.id.mapBriefDescriptionRoomates);
        TextView numberOfRoomsTxt = (TextView) mView.findViewById(R.id.mapBriefDescriptionRooms);
        TextView paging = (TextView) mView.findViewById(R.id.paging);
        TextView swipeRight = (TextView) mView.findViewById(R.id.sweptRightSign);
        TextView swipeLeft = (TextView) mView.findViewById(R.id.sweptLeftSign);

        if(totalApartmentsInBuilding == 1){
            swipeLeft.setEnabled(false);
            swipeRight.setEnabled(false);
            swipeLeft.setText("");
            swipeRight.setText("");
            paging.setText("");
        }
        else{
            String pagingString = (position + 1) + "/" + totalApartmentsInBuilding;
            paging.setText(pagingString);

            if(position != 0)
                swipeRight.setTextColor(getResources().getColor(R.color.black));

            if(position != totalApartmentsInBuilding - 1)
                swipeLeft.setTextColor(getResources().getColor(R.color.black));
        }

        String address;
        if(apartmentBriefDescription.getNeighborhood().equals("")){
            address = apartmentBriefDescription.getStreet()+" "+apartmentBriefDescription.getBuildingNumber();
        }
        else{
            address = apartmentBriefDescription.getStreet()+" "+apartmentBriefDescription.getBuildingNumber()+", "+apartmentBriefDescription.getNeighborhood();
        }

        int cost = apartmentBriefDescription.getCost();
        String costString;
        if (cost >= 0) {
            costString = "" + cost;
        } else {
            costString = "-";
            sheqelTxt.setText("");
        }



        double numberOfRooms = apartmentBriefDescription.getNumberOfRooms();
        String numberOfRoomsString;

        if (numberOfRooms <= 0) {
            numberOfRoomsString = "-";
        } else if (numberOfRooms == 1) {
            numberOfRoomsString = "דירת יחיד";
        } else {
            if (numberOfRooms % 1 == 0)
                numberOfRoomsString = "" + (int) numberOfRooms;
            else {
                numberOfRoomsString = "" + numberOfRooms;
            }
        }


        int numberOfRoomates = apartmentBriefDescription.getNumberOfRoomates();
        String numberOfRoomatesString;

        if (numberOfRoomates < 0) {
            numberOfRoomatesString = "-";
        } else if (numberOfRoomates == 0) {
            numberOfRoomatesString = "יחיד/זוג";
        } else if (numberOfRoomates == 1) {
            numberOfRoomatesString = "יחיד";
        } else {
            numberOfRoomatesString = "" + numberOfRoomates;
        }

        addressTxt.setText(address);
        costTxt.setText(costString);
        numberOfRoomsTxt.setText(numberOfRoomsString);
        numberOfRoomatesTxt.setText(numberOfRoomatesString);
        return mView;
    }



}
