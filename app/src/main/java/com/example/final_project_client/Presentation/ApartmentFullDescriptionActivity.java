package com.example.final_project_client.Presentation;


import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.example.final_project_client.R;
import com.example.final_project_client.UserSearchingUtils.ResultRecord;
import com.example.final_project_client.UserSearchingUtils.SearchResults;
import com.google.gson.Gson;

import org.w3c.dom.Text;

/**
 * Created by TAMIR on 5/1/2018.
 */

public class ApartmentFullDescriptionActivity extends AppCompatActivity {
    private ResultRecord resultRecord;
    private ListView contactsListView;
    private TextView sourceText;
    private TextView addressTxt;
    private TextView floorTxt;
    private TextView costTxt;
    private TextView sizeTxt;
    private TextView roomsTxt;
    private TextView roomatesTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actiivity_apartment_full_description);
        Intent intent = getIntent();
        String resultRecordString = intent.getStringExtra(ResultsActivity.APARTMENT_FULL_DESCRIPTION);
        resultRecord = new Gson().fromJson(resultRecordString, ResultRecord.class);

        contactsListView = (ListView) findViewById(R.id.listViewContacts);
        ContactListAdapter contactListAdapter = new ContactListAdapter(ApartmentFullDescriptionActivity.this, resultRecord.getContacts());
        contactsListView.setAdapter(contactListAdapter);
        setListViewHeightBasedOnChildren(contactsListView);

        sourceText = (TextView) findViewById(R.id.sourceText);
        sourceText.setText(resultRecord.getText());

        addressTxt = (TextView) findViewById(R.id.addressTxt);
        String address = resultRecord.getStreet() + " " + resultRecord.getNumber() + ", " + resultRecord.getNeighborhood();
        addressTxt.setText(address);

        floorTxt = (TextView) findViewById(R.id.floorTxt);
        String floorString;
        int floor = resultRecord.getFloor();
        if (floor < -1) {
            floorString = "---";
        } else {
            floorString = "" + floor;
        }
        floorTxt.setText(floorString);

        costTxt = (TextView) findViewById(R.id.costTxt);
        String costString;
        int cost = resultRecord.getCost();
        if (cost < 0) {
            costString = "---";
        } else {
            costString = "" + cost;
        }

        costTxt.setText(costString);

        sizeTxt =  (TextView) findViewById(R.id.sizeTxt);
        int size = resultRecord.getSize();
        String sizeString;
        if(size >= 0){
            sizeString = ""+size;
        }
        else{
            sizeString = "---";
        }
        sizeTxt.setText(sizeString);

       roomsTxt = (TextView) findViewById(R.id.roomsTxt);
       double rooms = resultRecord.getNumberOfRooms();

       String roomsString;
       if(rooms<=0){
           roomsString = "---";
       }
       else if (rooms == 1){
           roomsString = "דירת יחיד";
       }
       else {
           if(rooms%1 == 0)
            roomsString = ""+(int)rooms;
           else{
               roomsString = ""+rooms;
           }
       }

       roomsTxt.setText(roomsString);

       roomatesTxt = (TextView) findViewById(R.id.roomatesTxt);
       int roomates = resultRecord.getNumberOfRoomates();
       String roomatesString;
       if(roomates<0){
           roomatesString = "---";
       }
       else if(roomates == 0){
           roomatesString = "יחיד/זוג";
       }
       else if(roomates == 1){
           roomatesString = "יחיד";
       }
       else{
           roomatesString = ""+roomates;
       }

       roomatesTxt.setText(roomatesString);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}

