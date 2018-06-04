package com.example.final_project_client.Presentation.Results;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.final_project_client.R;

/**
 * Created by TAMIR on 3/22/2018.
 */

public class BriefDescriptionListAdapter extends ArrayAdapter<String> {
    private ApartmentBriefDescription[] records;
    private Context context;

    public BriefDescriptionListAdapter(Context context, ApartmentBriefDescription[] records) {
        super(context, R.layout.listview_item);
        this.records = records;
        this.context = context;
    }

    @Override
    public int getCount() {
        return records.length;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.listview_item, parent, false);
            viewHolder.costTxt = (TextView) convertView.findViewById(R.id.briefDescriptionCost);
            viewHolder.sheqelTxt = (TextView) convertView.findViewById(R.id.briefDescriptionSheqel);
            viewHolder.addressTxt = (TextView) convertView.findViewById(R.id.briefDescriptionAddress);
            viewHolder.numberOfRoomatesTxt = (TextView) convertView.findViewById(R.id.briefDescriptionRoomates);
            viewHolder.numberOfRoomsTxt = (TextView) convertView.findViewById(R.id.briefDescriptionRooms);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String address = records[position].getStreet() + " " + records[position].getBuildingNumber() + ", " + records[position].getNeighborhood();
        int cost = records[position].getCost();
        String costString;
        if (cost >= 0) {
            costString = "" + cost;
        } else {
            costString = "-";
            viewHolder.sheqelTxt.setText("");
        }

        double numberOfRooms = records[position].getNumberOfRooms();
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


        int numberOfRoomates = records[position].getNumberOfRoomates();
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

        viewHolder.addressTxt.setText(address);
        viewHolder.costTxt.setText(costString);
        viewHolder.numberOfRoomsTxt.setText(numberOfRoomsString);
        viewHolder.numberOfRoomatesTxt.setText(numberOfRoomatesString);

        return convertView;
    }

    static class ViewHolder {
        TextView costTxt;
        TextView sheqelTxt;
        TextView numberOfRoomatesTxt;
        TextView numberOfRoomsTxt;
        TextView addressTxt;

    }
}
