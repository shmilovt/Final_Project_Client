package com.example.final_project_client.Presentation.Results;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.FragmentTransaction;

import com.example.final_project_client.Communication.NetworkController;
import com.example.final_project_client.Communication.NetworkListener;
import com.example.final_project_client.Presentation.FullDescription.ApartmentFullDescriptionActivity;
import com.example.final_project_client.Presentation.FullDescription.ApartmentFullDescriptionActivityDataHolder;
import com.example.final_project_client.Presentation.Search.CategoriesManager;
import com.example.final_project_client.R;
import com.example.final_project_client.UserSearchingUtils.ResultRecord;
import com.example.final_project_client.UserSearchingUtils.SearchResults;
import com.example.final_project_client.UserSearchingUtils.UserSearch;

import java.util.ArrayList;
import java.util.List;


public class ResultsActivity extends AppCompatActivity {

    public static final String APARTMENT_FULL_DESCRIPTION = "APARTMENT_FULL_DESCRIPTION";
    private MapViewFragment mapViewFragment;
    private ListViewFragment listViewFragment;
    public static final int MaxDisplay = 15;
    private Button btnMode;
    private Button btnRecentlyResults;
    private Button btnNextResults;
    private ResultRecord[] resultRecords;
    private ResultRecord[] alternativeResultRecords;
    private int index;
    private int resultRecordsMode;
    private int viewMode;


    public ResultsActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchresults);

        index = DataHolder.getInstance().getIndex();
        resultRecords = DataHolder.getInstance().getResultRecords();

        if (DataHolder.getInstance().isOnFirstLaunch()) {
            viewMode = 0;
            mapViewFragment = new MapViewFragment();
            listViewFragment = new ListViewFragment();

            DataHolder.getInstance().setMapViewFragment(mapViewFragment);
            DataHolder.getInstance().setListViewFragment(listViewFragment);
            DataHolder.getInstance().setMode(viewMode);
            DataHolder.getInstance().setOnFirstLaunch(false);
            System.out.println("first launch");
        } else {
            viewMode = DataHolder.getInstance().getMode();
            mapViewFragment = DataHolder.getInstance().getMapViewFragment();
            listViewFragment = DataHolder.getInstance().getListViewFragment();
            mapViewFragment.updateData(this);
            System.out.println("not first launch");
        }
        btnMode = findViewById(R.id.BtnMode);
        btnRecentlyResults = findViewById(R.id.BtnRecentlyResults);
        btnNextResults = findViewById(R.id.BtnNextResults);


        if (index == 0)
            btnRecentlyResults.setEnabled(false);
        if (index + MaxDisplay >= resultRecords.length) {
            btnNextResults.setEnabled(false);
        }

        if (viewMode == 0) {
            getFragmentManager().beginTransaction().add(R.id.fragment_container, mapViewFragment).commit();
            btnMode.setText(getString(R.string.record_mode));
        } else {
            getFragmentManager().beginTransaction().add(R.id.fragment_container, listViewFragment).commit();
            btnMode.setText(getString(R.string.map_mode));
        }


        btnMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnMode.getText().equals(getString(R.string.map_mode))) {
                    btnMode.setText(getString(R.string.record_mode));
                    viewMode = 0;
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, mapViewFragment);
                    transaction.commit();

                } else {
                    btnMode.setText(getString(R.string.map_mode));
                    viewMode = 1;
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, listViewFragment);
                    transaction.commit();

                }
            }
        });
    }


    public synchronized void nextResults(View view) {
        if (resultRecordsMode == 0) {
            if (index + MaxDisplay < resultRecords.length) {
                index = index + MaxDisplay;
                mapViewFragment.updateData(this);
                listViewFragment.updateData(this);
                if (index + MaxDisplay >= resultRecords.length) {
                    btnNextResults.setEnabled(false);
                }
                if (index - MaxDisplay >= 0) {
                    btnRecentlyResults.setEnabled(true);
                }
            } else {

                if (alternativeResultRecords == null) {
                    downloadAlternativeResults();
                }

                if (alternativeResultRecords != null) {
                    if (alternativeResultRecords.length == 0) {
                        // if empty show dialog
                    } else {
                        // else switch to alternative;
                        index = 0;
                        resultRecordsMode = 1 - resultRecordsMode;
                    }

                }
            }

        } else if (resultRecordsMode == 1)

        {
            if (index + MaxDisplay < alternativeResultRecords.length) {
                index = index + MaxDisplay;
                mapViewFragment.updateData(this);
                listViewFragment.updateData(this);

                if (index + MaxDisplay >= alternativeResultRecords.length) {
                    btnNextResults.setEnabled(false);
                }
                if (index - MaxDisplay >= 0) {
                    btnRecentlyResults.setEnabled(true);
                }
            }
        } else

        {

        }

    }

    public synchronized void previousResults(View view) {
        if (resultRecordsMode == 0) {
            if (index - MaxDisplay >= 0) {
                index = index - MaxDisplay;
                mapViewFragment.updateData(this);
                listViewFragment.updateData(this);

                if (index - MaxDisplay < 0) {
                    btnRecentlyResults.setEnabled(false);
                }

                if (index + MaxDisplay <= resultRecords.length) {
                    btnNextResults.setEnabled(true);
                }
            }

        } else if (resultRecordsMode == 1) {
            if (index - MaxDisplay >= 0) {
                index = index - MaxDisplay;
                mapViewFragment.updateData(this);
                listViewFragment.updateData(this);

                if (index - MaxDisplay < 0) {
                    btnRecentlyResults.setEnabled(false);
                }

                if (index + MaxDisplay <= resultRecords.length) {
                    btnNextResults.setEnabled(true);
                }
            } else {

                index = resultRecords.length - MaxDisplay;
                if (index < 0)
                    index = 0;
                resultRecordsMode = 1 - resultRecordsMode;

                btnNextResults.setBackgroundResource(R.drawable.choosen_button);
                btnRecentlyResults.setBackgroundResource(R.drawable.choosen_button);
            }


        } else {

        }
    }

    public List<Coordinate> getCoordinates() {
        ResultRecord[] selectedResultRecords;
        if (resultRecordsMode == 0)
            selectedResultRecords = resultRecords;
        else {
            selectedResultRecords = alternativeResultRecords;
        }

        List<Coordinate> coordinates = new ArrayList<>();
        for (int i = index; i < index + MaxDisplay && i < selectedResultRecords.length; i++) {
            Coordinate coordinate = new Coordinate(selectedResultRecords[i].getLat(), selectedResultRecords[i].getLon());
            coordinates.add(coordinate);
        }

        return coordinates;
    }

    public ApartmentBriefDescription getApartmentBriefDescription(int position) {
        ResultRecord[] selectedResultRecords;
        if (resultRecordsMode == 0)
            selectedResultRecords = resultRecords;
        else {
            selectedResultRecords = alternativeResultRecords;
        }

        ResultRecord resultRecord = selectedResultRecords[index + position];
        ApartmentBriefDescription apartmentBriefDescription = new ApartmentBriefDescription(resultRecord.getCost(), resultRecord.getNeighborhood(), resultRecord.getStreet(), resultRecord.getNumber(), resultRecord.getNumberOfRooms(), resultRecord.getNumberOfRoomates());
        return apartmentBriefDescription;
    }


    public List<ApartmentBriefDescription> getApartmentBriefDescriptions() {
        ResultRecord[] selectedResultRecords;
        if (resultRecordsMode == 0)
            selectedResultRecords = resultRecords;
        else {
            selectedResultRecords = alternativeResultRecords;
        }

        List<ApartmentBriefDescription> apartmentBriefDescriptions = new ArrayList<>();
        for (int i = index; i < index + MaxDisplay && i < selectedResultRecords.length; i++) {
            ResultRecord resultRecord = selectedResultRecords[i];
            ApartmentBriefDescription apartmentBriefDescription = new ApartmentBriefDescription(resultRecord.getCost(), resultRecord.getNeighborhood(), resultRecord.getStreet(), resultRecord.getNumber(), resultRecord.getNumberOfRooms(), resultRecord.getNumberOfRoomates());
            apartmentBriefDescriptions.add(apartmentBriefDescription);
        }

        return apartmentBriefDescriptions;
    }

    public void getData(ResultsView mapViewFragment) {
        mapViewFragment.updateData(this);
    }

    public void openApartmentFullDescription(int index) {

        ResultRecord[] selectedResultRecords;
        if (resultRecordsMode == 0)
            selectedResultRecords = resultRecords;
        else {
            selectedResultRecords = alternativeResultRecords;
        }

        DataHolder.getInstance().setIndex(this.index);
        DataHolder.getInstance().setResultRecords(resultRecords);
        DataHolder.getInstance().setMode(viewMode);

        int numberOfRecord = this.index + index;
        ResultRecord resultRecord = selectedResultRecords[numberOfRecord];
        ApartmentFullDescriptionActivityDataHolder.getInstance().setResultRecord(resultRecord);
        Intent intent = new Intent(this, ApartmentFullDescriptionActivity.class);
        startActivity(intent);
    }

    public void ToastSwipeRight(View view) {
        mapViewFragment.previousApartmentBrief();

    }

    public void ToastSwipeLeft(View view) {
        mapViewFragment.nextApartmentBrief();

    }

    public synchronized void downloadAlternativeResults() {
        if (!hasNetwork()) {
            buildDialogNotNetwork(ResultsActivity.this).show();
        } else {

            UserSearch userSearch = CategoriesManager.getInstance(this).convertToUserSearch();
            NetworkController.getInstance(this).searchApartments(userSearch, new NetworkListener<SearchResults>() {
                @Override
                public void getResult(SearchResults searchResults) {
                    alternativeResultRecords = searchResults.getResultRecords();
                }
            }, new NetworkListener<String>() {
                @Override
                public void getResult(String errorString) {
                    buildDialogProblemConnectingToServer(ResultsActivity.this, errorString).show();
                }
            });



        /*    String jsonString =
                    "{\"resultRecordDTOS\":[{\"apartmentID\":\"13\",\"street\":\"אלעזר בן יאיר\",\"number\":16,\"neighborhood\":\"שכונה ד\u0027\",\"floor\":-2,\"distanceFromUniversity\":18.0,\"cost\":1150,\"size\":-1,\"balcony\":false,\"yard\":true,\"animals\":false,\"warehouse\":false,\"protectedSpace\":false,\"furniture\":2,\"numberOfRooms\":4.0,\"numberOfRoomates\":3,\"dateOfPublish\":\"Tue May 01 05:38:46 IDT 2018\",\"text\":\"השותפה המקסימה שלנו Yarden Peretz עוזבת את הדירה. אני וGal Ben Maman מחפשים מישהי שתחליף את מקומה בחדר. דירת 4 חדרים חדשה משופצת לחלוטין עם חצר ענקית. הדירה באלעזר בן יאיר 16 כרבע שעה מאוניברסיטה. הדירה מרוהטת ויש בה הכל, רק להביא בגדים ולהכנס. עלות 1150 ש\\\"ח. כניסה מיידית :)\\nלפרטים:\\n0526516656\\n\",\"contacts\":[{\"name\":\"\",\"phone\":\"0526516656\"}],\"lat\":31.2628471,\"lon\":34.7904706}, "+
                            "{\"apartmentID\":\"14\",\"street\":\"בצלאל\",\"number\":20,\"neighborhood\":\"שכונה ב\u0027\",\"floor\":-2,\"distanceFromUniversity\":26.0,\"cost\":2150,\"size\":-1,\"balcony\":false,\"yard\":true,\"animals\":false,\"warehouse\":false,\"protectedSpace\":false,\"furniture\":2,\"numberOfRooms\":2.0,\"numberOfRoomates\":0,\"dateOfPublish\":\"Tue May 01 05:38:49 IDT 2018\",\"text\":\"יחידת דיור 2 חדרים להשכרה בשכונה ב\u0027 רחוב בצלאל 20 חמש דקות הליכה למכללת סמי שמעון וקרובה מאוד למרכז חן. היחידה מרוהטת. 2150 כולל מים וארנונה וחשמל עד 150 שח לחודש. לכניסה מיידית .\n\",\"contacts\":[],\"lat\":31.2628471,\"lon\":34.7904706}]}";
            System.out.println(jsonString);
            SearchResultsDTO searchResultsDTO = SearchResultsDTO.fromJSON(jsonString);
            SearchResults searchResults = new SearchResults(searchResultsDTO);
            Intent intent = new Intent(resultsActivity, ResultsActivity.class);
            DataHolder.getInstance().setIndex(0);
            DataHolder.getInstance().setResultRecords(searchResults.getResultRecords());
            DataHolder.getInstance().setOnFirstLaunch(true);
            startActivity(intent);
      */
        }
    }

    public AlertDialog.Builder buildDialogNotNetwork(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("אין חיבור אינטרנט");
        builder.setMessage("אינטרנט אינו פועל. על מנת לחפש תדאג\\י לחיבור אינטרנט.");
        builder.setPositiveButton("סגור", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return builder;
    }


    public AlertDialog.Builder buildDialogProblemConnectingToServer(Context context, String errorString) {
        String message = "היישום אינו מצליח לתקשר עם השרת.";
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("בעיית חיבור לשרת");
        builder.setMessage(message + "\n" + errorString);
        builder.setPositiveButton("סגור", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return builder;
    }


    public boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
            else
                return false;
        } else
            return false;

    }

    private boolean hasNetwork() {
        return isConnected(ResultsActivity.this);
    }
}
