package com.example.final_project_client.Presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.final_project_client.Presentation.Report.Field;
import com.example.final_project_client.Presentation.Report.ReportActivity;
import com.example.final_project_client.R;
import com.example.final_project_client.UserSearchingUtils.ResultRecord;
import com.google.gson.Gson;


/**
 * Created by TAMIR on 5/1/2018.
 */



public class ApartmentFullDescriptionActivity extends AppCompatActivity {
    public static final String CATEGORY = "com.example.final_project_client.CATEGORY";
    public static final String  SOURCE_TEXT = "com.example.final_project_client.SOURCE_TEXT";
    public static final String  APARTMENT_ID = "com.example.final_project_client.APARTMENT_ID";
    private ResultRecord resultRecord;
    private ListView contactsListView;
    private TextView sourceText;
    private TextView addressTxt;
    private TextView floorTxt;
    private TextView costTxt;
    private TextView sizeTxt;
    private TextView roomsTxt;
    private TextView roomatesTxt;
    private TextView distanceFromUniversityTxt;
    private TextView publishDateTxt;
    private TextView furnitureTxt;
    private TextView protectedSpaceTxt;
    private TextView animalsTxt;
    private TextView warehouseTxt;
    private TextView balconyTxt;
    private TextView gardenTxt;
    private Button editAddressBtn;
    private Button editCostBtn;
    private Button editFloorBtn;
    private Button editSizeBtn;
    private Button editRoomsBtn;
    private Button editRoomatesBtn;
    private Button editFurnitureBtn;
    private Button editGardenBtn;
    private Button editProtectedSpaceBtn;
    private Button editBalconyBtn;
    private Button editWarehouseBtn;
    private Button editAnimalsBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actiivity_apartment_full_description);
        resultRecord = ApartmentFullDescriptionActivityDataHolder.getInstance().getResultRecord();

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

        sizeTxt = (TextView) findViewById(R.id.sizeTxt);
        int size = resultRecord.getSize();
        String sizeString;
        if (size >= 0) {
            sizeString = "" + size;
        } else {
            sizeString = "---";
        }
        sizeTxt.setText(sizeString);

        roomsTxt = (TextView) findViewById(R.id.roomsTxt);
        double rooms = resultRecord.getNumberOfRooms();

        String roomsString;
        if (rooms <= 0) {
            roomsString = "---";
        } else if (rooms == 1) {
            roomsString = "דירת יחיד";
        } else {
            if (rooms % 1 == 0)
                roomsString = "" + (int) rooms;
            else {
                roomsString = "" + rooms;
            }
        }

        roomsTxt.setText(roomsString);

        roomatesTxt = (TextView) findViewById(R.id.roomatesTxt);
        int roomates = resultRecord.getNumberOfRoomates();
        String roomatesString;
        if (roomates < 0) {
            roomatesString = "---";
        } else if (roomates == 0) {
            roomatesString = "יחיד/זוג";
        } else if (roomates == 1) {
            roomatesString = "יחיד";
        } else {
            roomatesString = "" + roomates;
        }

        roomatesTxt.setText(roomatesString);

        distanceFromUniversityTxt = (TextView) findViewById(R.id.distanceFromUniversityTxt);
        int distanceFromUniversity = (int) resultRecord.getDistanceFromUniversity();
        String distanceFromUniversityString;
        if (distanceFromUniversity < 0) {
            distanceFromUniversityString = "---";
        } else {
            distanceFromUniversityString = "" + distanceFromUniversity;
        }
        distanceFromUniversityTxt.setText(distanceFromUniversityString);

        publishDateTxt = (TextView) findViewById(R.id.publishDateTxt);
        publishDateTxt.setText(resultRecord.getDateOfPublish());

        furnitureTxt = (TextView) findViewById(R.id.furnitureTxt);
        String furnitureString;

        switch (resultRecord.getFurniture()) {
            case 0:
                furnitureString = "אין";
                break;
            case 1:
                furnitureString = "ריהוט חלקי";
                break;
            case 2:
                furnitureString = "ריהוט מלא";
                break;
            default:
                furnitureString = "";
                break;
        }

        furnitureTxt.setText(furnitureString);

        protectedSpaceTxt = (TextView) findViewById(R.id.protectedSpaceTxt);
        warehouseTxt = (TextView) findViewById(R.id.warehouseTxt);
        animalsTxt = (TextView) findViewById(R.id.animalsTxt);
        balconyTxt = (TextView) findViewById(R.id.balconyTxt);
        gardenTxt = (TextView) findViewById(R.id.gardenTxt);

        String checkMark = "\u2713";
        String crossMark = "\u2718";
        String animalsString, balconyString, protectedSpaceString, warehouseString, yardString;
        if (resultRecord.isAnimals()) {
            animalsString = checkMark;
        } else {
            animalsString = crossMark;
        }
        if (resultRecord.isBalcony()) {
            balconyString = checkMark;
        } else {
            balconyString = crossMark;
        }
        if (resultRecord.isProtectedSpace()) {
            protectedSpaceString = checkMark;
        } else {
            protectedSpaceString = crossMark;
        }
        if (resultRecord.isWarehouse()) {
            warehouseString = checkMark;
        } else {
            warehouseString = crossMark;
        }
        if (resultRecord.isYard()) {
            yardString = checkMark;
        } else {
            yardString = crossMark;
        }

        protectedSpaceTxt.setText(protectedSpaceString);
        animalsTxt.setText(animalsString);
        gardenTxt.setText(yardString);
        warehouseTxt.setText(warehouseString);
        balconyTxt.setText(balconyString);

        setClickListenersToButtons();

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

    public void startReportActivity(Field field) {
        Intent intent = new Intent(this, ReportActivity.class);
        intent.putExtra(CATEGORY, new Gson().toJson(field));
        intent.putExtra(APARTMENT_ID, resultRecord.getApartmentID());
        intent.putExtra(SOURCE_TEXT , resultRecord.getText());
        startActivity(intent);

    }


    private void setClickListenersToButtons() {

        editAddressBtn = (Button) findViewById(R.id.editAddressBtn);
        editAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startReportActivity(Field.address);
            }
        });

        editCostBtn = (Button) findViewById(R.id.editCostBtn);
        editCostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startReportActivity(Field.cost);
            }
        });

        editFloorBtn = (Button) findViewById(R.id.editFloorBtn);
        editFloorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startReportActivity(Field.floor);
            }
        });

        editRoomsBtn = (Button) findViewById(R.id.editRoomsBtn);
        editRoomsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startReportActivity(Field.numRooms);
            }
        });

        editSizeBtn = (Button) findViewById(R.id.editSizeBtn);
        editSizeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startReportActivity(Field.apartmentSize);
            }
        });

        editRoomatesBtn = (Button) findViewById(R.id.editRoomatesBtn);
        editRoomatesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startReportActivity(Field.numRoomates);
            }
        });

        editAnimalsBtn = (Button) findViewById(R.id.editAnimalsBtn);
        editAnimalsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startReportActivity(Field.animals);
            }
        });

        editBalconyBtn = (Button) findViewById(R.id.editBalconyBtn);
        editBalconyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startReportActivity(Field.balcony);
            }
        });

        editFurnitureBtn = (Button) findViewById(R.id.editFurnitureBtn);
        editFurnitureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startReportActivity(Field.furniture);
            }
        });


        editGardenBtn = (Button) findViewById(R.id.editGardenBtn);
        editGardenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startReportActivity(Field.yard);
            }
        });

        editWarehouseBtn = (Button) findViewById(R.id.editWarehouseBtn);
        editWarehouseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startReportActivity(Field.warehouse);
            }
        });

        editProtectedSpaceBtn = (Button) findViewById(R.id.editProtectedSpaceBtn);
        editProtectedSpaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startReportActivity(Field.protectedSpace);
            }
        });


    }

}

