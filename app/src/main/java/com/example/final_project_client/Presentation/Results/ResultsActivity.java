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
    private int numberOfNextResults;
    private int numberOfPreviousResults;
    private int numberOfCurrentResults;


    public ResultsActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchresults);

        index = DataHolder.getInstance().getIndex();
        resultRecords = DataHolder.getInstance().getResultRecords();


        if (DataHolder.getInstance().isOnFirstLaunch()) {
            setNumberOfPreviousResults(0);
            initialNumberOfNextResults();
            initialNumberOfCurrentResults();
            viewMode = 0;
            resultRecordsMode = 0;
            mapViewFragment = new MapViewFragment();
            listViewFragment = new ListViewFragment();
            DataHolder.getInstance().setAlternativeRecords(null);
            DataHolder.getInstance().setMapViewFragment(mapViewFragment);
            DataHolder.getInstance().setListViewFragment(listViewFragment);
            DataHolder.getInstance().setResultRecordMode(resultRecordsMode);
            DataHolder.getInstance().setMode(viewMode);
            DataHolder.getInstance().setOnFirstLaunch(false);
            System.out.println("first launch");
        } else {

            viewMode = DataHolder.getInstance().getMode();
            mapViewFragment = DataHolder.getInstance().getMapViewFragment();
            listViewFragment = DataHolder.getInstance().getListViewFragment();
            resultRecordsMode = DataHolder.getInstance().getRessultRecordsMode();
            alternativeResultRecords = DataHolder.getInstance().getAlternativeRecords();
            mapViewFragment.updateData(this);
        }
        btnMode = findViewById(R.id.BtnMode);
        btnRecentlyResults = findViewById(R.id.BtnRecentlyResults);
        btnNextResults = findViewById(R.id.BtnNextResults);

        configButtons();

        if (viewMode == 0) {
            getFragmentManager().beginTransaction().add(R.id.fragment_container, mapViewFragment).commit();
        } else {
            getFragmentManager().beginTransaction().add(R.id.fragment_container, listViewFragment).commit();
        }


        btnMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String btnName;
                if (viewMode == 1) {
                    btnName = getResources().getString(R.string.record_mode) + "\n(" + numberOfCurrentResults + ")";
                    btnMode.setText(btnName);
                    viewMode = 0;
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, mapViewFragment);
                    transaction.commit();

                } else {
                    btnName = getResources().getString(R.string.map_mode) + "\n(" + numberOfCurrentResults + ")";
                    btnMode.setText(btnName);
                    viewMode = 1;
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, listViewFragment);
                    transaction.commit();

                }
            }
        });
    }

    private void initialNumberOfCurrentResults() {
        if (index + MaxDisplay <= resultRecords.length)
            numberOfCurrentResults = MaxDisplay;
        else {
            numberOfCurrentResults = resultRecords.length - index;
        }
    }

    private void initialNumberOfNextResults() {
        if (resultRecords.length <= MaxDisplay)
            setNumberOfNextResults(0);
        else
            setNumberOfNextResults(resultRecords.length - MaxDisplay);
    }

    private void setNumberOfNextResults(int numberOfNextResults) {
        this.numberOfNextResults = numberOfNextResults;
    }


    public synchronized void nextResults(View view) {
        if (viewMode == 0) {
            mapViewFragment.removeBriefDescription();
        }
        if (resultRecordsMode == 0) {
            if (index + MaxDisplay < resultRecords.length) {
                index = index + MaxDisplay;
                updateViews();

            } else {

                if (alternativeResultRecords == null) {
                    buildDialogDownloadAlternativeResults(this).show();
                } else {
                    fromSearchResultsToAlternativeResults();
                }

            }

        } else if (resultRecordsMode == 1) {
            if (index + MaxDisplay < alternativeResultRecords.length) {
                index = index + MaxDisplay;
                updateViews();
            }
        }


        configButtons();


    }

    private void calcNumberOfCurrentResults() {
        if (resultRecordsMode == 0) {
            if (index + MaxDisplay <= resultRecords.length)
                numberOfCurrentResults = MaxDisplay;
            else {
                numberOfCurrentResults = resultRecords.length - index;
            }
        } else if (resultRecordsMode == 1) {
            if (index + MaxDisplay <= alternativeResultRecords.length)
                numberOfCurrentResults = MaxDisplay;
            else {
                numberOfCurrentResults = alternativeResultRecords.length - index;
            }
        }
    }

    private void updateViews() {
        mapViewFragment.updateData(this);
        listViewFragment.updateData(this);
    }

    public synchronized void previousResults(View view) {
        if (viewMode == 0) {
            mapViewFragment.removeBriefDescription();
        }
        if (resultRecordsMode == 0) {
            if (index - MaxDisplay >= 0) {
                index = index - MaxDisplay;

                updateViews();
            }

        } else if (resultRecordsMode == 1) {
            if (index - MaxDisplay >= 0) {
                index = index - MaxDisplay;
            } else {


                if (resultRecords.length <= MaxDisplay)
                    index = 0;
                else {
                    index = resultRecords.length - resultRecords.length % MaxDisplay;
                }
                resultRecordsMode = 1 - resultRecordsMode;

            }
            updateViews();
        }


        configButtons();
    }

    private void configButtons() {
        String btnName;
        calcNumberOfNextResults();
        calcNumberOfPreviousResults();
        calcNumberOfCurrentResults();

        btnName = getResources().getString(R.string.NextResults) + "\n(" + numberOfNextResults + ")";
        btnNextResults.setText(btnName);
        btnName = getResources().getString(R.string.RecentlyResults) + "\n(" + numberOfPreviousResults + ")";
        btnRecentlyResults.setText(btnName);

        if (viewMode == 0) {
            btnName = getResources().getString(R.string.record_mode) + "\n(" + numberOfCurrentResults + ")";
            btnMode.setText(btnName);
        } else if (viewMode == 1) {
            btnName = getResources().getString(R.string.map_mode) + "\n(" + numberOfCurrentResults + ")";
            btnMode.setText(btnName);
        }

        if (resultRecordsMode == 0) {

            btnMode.setBackgroundResource(R.drawable.regular_next_previous_button_background);
            btnNextResults.setEnabled(true);

            if (index == 0) {
                btnRecentlyResults.setEnabled(false);
                btnRecentlyResults.setBackgroundResource(R.drawable.enabled_false_background);
            } else {
                btnRecentlyResults.setEnabled(true);
                btnRecentlyResults.setBackgroundResource(R.drawable.regular_next_previous_button_background);
            }

            if (index + MaxDisplay < resultRecords.length) {
                btnNextResults.setBackgroundResource(R.drawable.regular_next_previous_button_background);
                btnName = getResources().getString(R.string.NextResults) + "\n(" + numberOfNextResults + ")";
                btnNextResults.setText(btnName);
            } else {

                btnNextResults.setBackgroundResource(R.drawable.button_alternatives_background);
                if (alternativeResultRecords != null)
                    btnName = getResources().getString(R.string.alternativeResults) + "\n(" + numberOfNextResults + ")";
                else{
                    btnName = getResources().getString(R.string.alternativeResults);
                }
                btnNextResults.setText(btnName);

            }


        } else if (resultRecordsMode == 1) {

            btnMode.setBackgroundResource(R.drawable.button_alternatives_background);
            btnNextResults.setBackgroundResource(R.drawable.button_alternatives_background);
            btnRecentlyResults.setBackgroundResource(R.drawable.button_alternatives_background);
            btnRecentlyResults.setEnabled(true);

            if (index + MaxDisplay < alternativeResultRecords.length) {
                btnNextResults.setEnabled(true);
                btnNextResults.setBackgroundResource(R.drawable.button_alternatives_background);
            } else {
                btnNextResults.setEnabled(false);
                btnNextResults.setBackgroundResource(R.drawable.enabled_false_background);
            }
        }
    }

    private void calcNumberOfNextResults() {
        if (resultRecordsMode == 0) {
            if (alternativeResultRecords != null) {
                if (index + MaxDisplay >= resultRecords.length) {
                    numberOfNextResults = alternativeResultRecords.length;
                } else {
                    numberOfNextResults = resultRecords.length - (index + MaxDisplay) + alternativeResultRecords.length;
                }
            } else {
                if (index + MaxDisplay >= resultRecords.length)
                    numberOfNextResults = 0;
                else {
                    numberOfNextResults = resultRecords.length - (index + MaxDisplay);
                }
            }
        } else if (resultRecordsMode == 1) {
            if (index + MaxDisplay >= alternativeResultRecords.length)
                numberOfNextResults = 0;
            else {
                numberOfNextResults = alternativeResultRecords.length - (index + MaxDisplay);
            }

        }
    }

    private void calcNumberOfPreviousResults() {
        if (resultRecordsMode == 0) {

            numberOfPreviousResults = index;
        } else if (resultRecordsMode == 1) {

            numberOfPreviousResults = index + resultRecords.length;
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
        DataHolder.getInstance().setAlternativeRecords(alternativeResultRecords);
        DataHolder.getInstance().setResultRecordMode(resultRecordsMode);

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
        if (alternativeResultRecords == null) {
            if (!hasNetwork()) {
                buildDialogNotNetwork(ResultsActivity.this).show();
            } else {

                UserSearch userSearch = CategoriesManager.getInstance().convertToUserSearch();
                NetworkController.getInstance(this).getAlternativeApartments(userSearch, new NetworkListener<SearchResults>() {
                    @Override
                    public void getResult(SearchResults searchResults) {
                        alternativeResultRecords = searchResults.getResultRecords();

                        if (alternativeResultRecords != null) {
                            fromSearchResultsToAlternativeResults();
                        }
                    }
                }, new NetworkListener<String>() {
                    @Override
                    public void getResult(String errorString) {
                        buildDialogProblemConnectingToServer(ResultsActivity.this, errorString).show();
                    }
                });
            }

         /*   if (alternativeResultRecords == null) {
                String jsonString =
                        "{\"resultRecordDTOS\":[{\"apartmentID\":\"13\",\"street\":\"אלעזר בן יאיר\",\"number\":16,\"neighborhood\":\"שכונה ד\u0027\",\"floor\":-2,\"distanceFromUniversity\":18.0,\"cost\":1150,\"size\":-1,\"balcony\":false,\"yard\":true,\"animals\":false,\"warehouse\":false,\"protectedSpace\":false,\"furniture\":2,\"numberOfRooms\":4.0,\"numberOfRoomates\":3,\"dateOfPublish\":\"Tue May 01 05:38:46 IDT 2018\",\"text\":\"השותפה המקסימה שלנו Yarden Peretz עוזבת את הדירה. אני וGal Ben Maman מחפשים מישהי שתחליף את מקומה בחדר. דירת 4 חדרים חדשה משופצת לחלוטין עם חצר ענקית. הדירה באלעזר בן יאיר 16 כרבע שעה מאוניברסיטה. הדירה מרוהטת ויש בה הכל, רק להביא בגדים ולהכנס. עלות 1150 ש\\\"ח. כניסה מיידית :)\\nלפרטים:\\n0526516656\\n\",\"contacts\":[{\"name\":\"\",\"phone\":\"0526516656\"}],\"lat\":31.2628471,\"lon\":34.7904706}, " +
                                "{\"apartmentID\":\"14\",\"street\":\"בצלאל\",\"number\":20,\"neighborhood\":\"שכונה ב\u0027\",\"floor\":-2,\"distanceFromUniversity\":26.0,\"cost\":2150,\"size\":-1,\"balcony\":false,\"yard\":true,\"animals\":false,\"warehouse\":false,\"protectedSpace\":false,\"furniture\":2,\"numberOfRooms\":2.0,\"numberOfRoomates\":0,\"dateOfPublish\":\"Tue May 01 05:38:49 IDT 2018\",\"text\":\"יחידת דיור 2 חדרים להשכרה בשכונה ב\u0027 רחוב בצלאל 20 חמש דקות הליכה למכללת סמי שמעון וקרובה מאוד למרכז חן. היחידה מרוהטת. 2150 כולל מים וארנונה וחשמל עד 150 שח לחודש. לכניסה מיידית .\n\",\"contacts\":[],\"lat\":31.2628471,\"lon\":34.7904706} ," +
                                "{\"apartmentID\":\"14\",\"street\":\"בצלאל\",\"number\":20,\"neighborhood\":\"שכונה ב\u0027\",\"floor\":-2,\"distanceFromUniversity\":26.0,\"cost\":2150,\"size\":-1,\"balcony\":false,\"yard\":true,\"animals\":false,\"warehouse\":false,\"protectedSpace\":false,\"furniture\":2,\"numberOfRooms\":2.0,\"numberOfRoomates\":0,\"dateOfPublish\":\"Tue May 01 05:38:49 IDT 2018\",\"text\":\"יחידת דיור 2 חדרים להשכרה בשכונה ב\u0027 רחוב בצלאל 20 חמש דקות הליכה למכללת סמי שמעון וקרובה מאוד למרכז חן. היחידה מרוהטת. 2150 כולל מים וארנונה וחשמל עד 150 שח לחודש. לכניסה מיידית .\n\",\"contacts\":[],\"lat\":31.2628471,\"lon\":34.7904706} ," +
                                "{\"apartmentID\":\"14\",\"street\":\"בצלאל\",\"number\":20,\"neighborhood\":\"שכונה ב\u0027\",\"floor\":-2,\"distanceFromUniversity\":26.0,\"cost\":2150,\"size\":-1,\"balcony\":false,\"yard\":true,\"animals\":false,\"warehouse\":false,\"protectedSpace\":false,\"furniture\":2,\"numberOfRooms\":2.0,\"numberOfRoomates\":0,\"dateOfPublish\":\"Tue May 01 05:38:49 IDT 2018\",\"text\":\"יחידת דיור 2 חדרים להשכרה בשכונה ב\u0027 רחוב בצלאל 20 חמש דקות הליכה למכללת סמי שמעון וקרובה מאוד למרכז חן. היחידה מרוהטת. 2150 כולל מים וארנונה וחשמל עד 150 שח לחודש. לכניסה מיידית .\n\",\"contacts\":[],\"lat\":31.2628471,\"lon\":34.7904706} ," +
                                "{\"apartmentID\":\"14\",\"street\":\"בצלאל\",\"number\":20,\"neighborhood\":\"שכונה ב\u0027\",\"floor\":-2,\"distanceFromUniversity\":26.0,\"cost\":2150,\"size\":-1,\"balcony\":false,\"yard\":true,\"animals\":false,\"warehouse\":false,\"protectedSpace\":false,\"furniture\":2,\"numberOfRooms\":2.0,\"numberOfRoomates\":0,\"dateOfPublish\":\"Tue May 01 05:38:49 IDT 2018\",\"text\":\"יחידת דיור 2 חדרים להשכרה בשכונה ב\u0027 רחוב בצלאל 20 חמש דקות הליכה למכללת סמי שמעון וקרובה מאוד למרכז חן. היחידה מרוהטת. 2150 כולל מים וארנונה וחשמל עד 150 שח לחודש. לכניסה מיידית .\n\",\"contacts\":[],\"lat\":31.2628471,\"lon\":34.7904706} ," +
                                "{\"apartmentID\":\"14\",\"street\":\"בצלאל\",\"number\":20,\"neighborhood\":\"שכונה ב\u0027\",\"floor\":-2,\"distanceFromUniversity\":26.0,\"cost\":2150,\"size\":-1,\"balcony\":false,\"yard\":true,\"animals\":false,\"warehouse\":false,\"protectedSpace\":false,\"furniture\":2,\"numberOfRooms\":2.0,\"numberOfRoomates\":0,\"dateOfPublish\":\"Tue May 01 05:38:49 IDT 2018\",\"text\":\"יחידת דיור 2 חדרים להשכרה בשכונה ב\u0027 רחוב בצלאל 20 חמש דקות הליכה למכללת סמי שמעון וקרובה מאוד למרכז חן. היחידה מרוהטת. 2150 כולל מים וארנונה וחשמל עד 150 שח לחודש. לכניסה מיידית .\n\",\"contacts\":[],\"lat\":31.2628471,\"lon\":34.7904706} ," +
                                "{\"apartmentID\":\"14\",\"street\":\"בצלאל\",\"number\":20,\"neighborhood\":\"שכונה ב\u0027\",\"floor\":-2,\"distanceFromUniversity\":26.0,\"cost\":2150,\"size\":-1,\"balcony\":false,\"yard\":true,\"animals\":false,\"warehouse\":false,\"protectedSpace\":false,\"furniture\":2,\"numberOfRooms\":2.0,\"numberOfRoomates\":0,\"dateOfPublish\":\"Tue May 01 05:38:49 IDT 2018\",\"text\":\"יחידת דיור 2 חדרים להשכרה בשכונה ב\u0027 רחוב בצלאל 20 חמש דקות הליכה למכללת סמי שמעון וקרובה מאוד למרכז חן. היחידה מרוהטת. 2150 כולל מים וארנונה וחשמל עד 150 שח לחודש. לכניסה מיידית .\n\",\"contacts\":[],\"lat\":31.2628471,\"lon\":34.7904706} ," +
                                "{\"apartmentID\":\"14\",\"street\":\"בצלאל\",\"number\":20,\"neighborhood\":\"שכונה ב\u0027\",\"floor\":-2,\"distanceFromUniversity\":26.0,\"cost\":2150,\"size\":-1,\"balcony\":false,\"yard\":true,\"animals\":false,\"warehouse\":false,\"protectedSpace\":false,\"furniture\":2,\"numberOfRooms\":2.0,\"numberOfRoomates\":0,\"dateOfPublish\":\"Tue May 01 05:38:49 IDT 2018\",\"text\":\"יחידת דיור 2 חדרים להשכרה בשכונה ב\u0027 רחוב בצלאל 20 חמש דקות הליכה למכללת סמי שמעון וקרובה מאוד למרכז חן. היחידה מרוהטת. 2150 כולל מים וארנונה וחשמל עד 150 שח לחודש. לכניסה מיידית .\n\",\"contacts\":[],\"lat\":31.2628471,\"lon\":34.7904706} ," +
                                "{\"apartmentID\":\"14\",\"street\":\"בצלאל\",\"number\":20,\"neighborhood\":\"שכונה ב\u0027\",\"floor\":-2,\"distanceFromUniversity\":26.0,\"cost\":2150,\"size\":-1,\"balcony\":false,\"yard\":true,\"animals\":false,\"warehouse\":false,\"protectedSpace\":false,\"furniture\":2,\"numberOfRooms\":2.0,\"numberOfRoomates\":0,\"dateOfPublish\":\"Tue May 01 05:38:49 IDT 2018\",\"text\":\"יחידת דיור 2 חדרים להשכרה בשכונה ב\u0027 רחוב בצלאל 20 חמש דקות הליכה למכללת סמי שמעון וקרובה מאוד למרכז חן. היחידה מרוהטת. 2150 כולל מים וארנונה וחשמל עד 150 שח לחודש. לכניסה מיידית .\n\",\"contacts\":[],\"lat\":31.2628471,\"lon\":34.7904706} ," +
                                "{\"apartmentID\":\"14\",\"street\":\"בצלאל\",\"number\":20,\"neighborhood\":\"שכונה ב\u0027\",\"floor\":-2,\"distanceFromUniversity\":26.0,\"cost\":2150,\"size\":-1,\"balcony\":false,\"yard\":true,\"animals\":false,\"warehouse\":false,\"protectedSpace\":false,\"furniture\":2,\"numberOfRooms\":2.0,\"numberOfRoomates\":0,\"dateOfPublish\":\"Tue May 01 05:38:49 IDT 2018\",\"text\":\"יחידת דיור 2 חדרים להשכרה בשכונה ב\u0027 רחוב בצלאל 20 חמש דקות הליכה למכללת סמי שמעון וקרובה מאוד למרכז חן. היחידה מרוהטת. 2150 כולל מים וארנונה וחשמל עד 150 שח לחודש. לכניסה מיידית .\n\",\"contacts\":[],\"lat\":31.2628471,\"lon\":34.7904706} ," +
                                "{\"apartmentID\":\"14\",\"street\":\"בצלאל\",\"number\":20,\"neighborhood\":\"שכונה ב\u0027\",\"floor\":-2,\"distanceFromUniversity\":26.0,\"cost\":2150,\"size\":-1,\"balcony\":false,\"yard\":true,\"animals\":false,\"warehouse\":false,\"protectedSpace\":false,\"furniture\":2,\"numberOfRooms\":2.0,\"numberOfRoomates\":0,\"dateOfPublish\":\"Tue May 01 05:38:49 IDT 2018\",\"text\":\"יחידת דיור 2 חדרים להשכרה בשכונה ב\u0027 רחוב בצלאל 20 חמש דקות הליכה למכללת סמי שמעון וקרובה מאוד למרכז חן. היחידה מרוהטת. 2150 כולל מים וארנונה וחשמל עד 150 שח לחודש. לכניסה מיידית .\n\",\"contacts\":[],\"lat\":31.2628471,\"lon\":34.7904706} ," +
                                "{\"apartmentID\":\"14\",\"street\":\"בצלאל\",\"number\":20,\"neighborhood\":\"שכונה ב\u0027\",\"floor\":-2,\"distanceFromUniversity\":26.0,\"cost\":2150,\"size\":-1,\"balcony\":false,\"yard\":true,\"animals\":false,\"warehouse\":false,\"protectedSpace\":false,\"furniture\":2,\"numberOfRooms\":2.0,\"numberOfRoomates\":0,\"dateOfPublish\":\"Tue May 01 05:38:49 IDT 2018\",\"text\":\"יחידת דיור 2 חדרים להשכרה בשכונה ב\u0027 רחוב בצלאל 20 חמש דקות הליכה למכללת סמי שמעון וקרובה מאוד למרכז חן. היחידה מרוהטת. 2150 כולל מים וארנונה וחשמל עד 150 שח לחודש. לכניסה מיידית .\n\",\"contacts\":[],\"lat\":31.2628471,\"lon\":34.7904706} ," +
                                "{\"apartmentID\":\"14\",\"street\":\"בצלאל\",\"number\":20,\"neighborhood\":\"שכונה ב\u0027\",\"floor\":-2,\"distanceFromUniversity\":26.0,\"cost\":2150,\"size\":-1,\"balcony\":false,\"yard\":true,\"animals\":false,\"warehouse\":false,\"protectedSpace\":false,\"furniture\":2,\"numberOfRooms\":2.0,\"numberOfRoomates\":0,\"dateOfPublish\":\"Tue May 01 05:38:49 IDT 2018\",\"text\":\"יחידת דיור 2 חדרים להשכרה בשכונה ב\u0027 רחוב בצלאל 20 חמש דקות הליכה למכללת סמי שמעון וקרובה מאוד למרכז חן. היחידה מרוהטת. 2150 כולל מים וארנונה וחשמל עד 150 שח לחודש. לכניסה מיידית .\n\",\"contacts\":[],\"lat\":31.2628471,\"lon\":34.7904706} ," +
                                "{\"apartmentID\":\"14\",\"street\":\"בצלאל\",\"number\":20,\"neighborhood\":\"שכונה ב\u0027\",\"floor\":-2,\"distanceFromUniversity\":26.0,\"cost\":2150,\"size\":-1,\"balcony\":false,\"yard\":true,\"animals\":false,\"warehouse\":false,\"protectedSpace\":false,\"furniture\":2,\"numberOfRooms\":2.0,\"numberOfRoomates\":0,\"dateOfPublish\":\"Tue May 01 05:38:49 IDT 2018\",\"text\":\"יחידת דיור 2 חדרים להשכרה בשכונה ב\u0027 רחוב בצלאל 20 חמש דקות הליכה למכללת סמי שמעון וקרובה מאוד למרכז חן. היחידה מרוהטת. 2150 כולל מים וארנונה וחשמל עד 150 שח לחודש. לכניסה מיידית .\n\",\"contacts\":[],\"lat\":31.2628471,\"lon\":34.7904706} ," +
                                "{\"apartmentID\":\"14\",\"street\":\"בצלאל\",\"number\":20,\"neighborhood\":\"שכונה ב\u0027\",\"floor\":-2,\"distanceFromUniversity\":26.0,\"cost\":2150,\"size\":-1,\"balcony\":false,\"yard\":true,\"animals\":false,\"warehouse\":false,\"protectedSpace\":false,\"furniture\":2,\"numberOfRooms\":2.0,\"numberOfRoomates\":0,\"dateOfPublish\":\"Tue May 01 05:38:49 IDT 2018\",\"text\":\"יחידת דיור 2 חדרים להשכרה בשכונה ב\u0027 רחוב בצלאל 20 חמש דקות הליכה למכללת סמי שמעון וקרובה מאוד למרכז חן. היחידה מרוהטת. 2150 כולל מים וארנונה וחשמל עד 150 שח לחודש. לכניסה מיידית .\n\",\"contacts\":[],\"lat\":31.2628471,\"lon\":34.7904706} ," +
                                "{\"apartmentID\":\"14\",\"street\":\"בצלאל\",\"number\":20,\"neighborhood\":\"שכונה ב\u0027\",\"floor\":-2,\"distanceFromUniversity\":26.0,\"cost\":2150,\"size\":-1,\"balcony\":false,\"yard\":true,\"animals\":false,\"warehouse\":false,\"protectedSpace\":false,\"furniture\":2,\"numberOfRooms\":2.0,\"numberOfRoomates\":0,\"dateOfPublish\":\"Tue May 01 05:38:49 IDT 2018\",\"text\":\"יחידת דיור 2 חדרים להשכרה בשכונה ב\u0027 רחוב בצלאל 20 חמש דקות הליכה למכללת סמי שמעון וקרובה מאוד למרכז חן. היחידה מרוהטת. 2150 כולל מים וארנונה וחשמל עד 150 שח לחודש. לכניסה מיידית .\n\",\"contacts\":[],\"lat\":31.2628471,\"lon\":34.7904706} ," +
                                "{\"apartmentID\":\"14\",\"street\":\"בצלאל\",\"number\":20,\"neighborhood\":\"שכונה ב\u0027\",\"floor\":-2,\"distanceFromUniversity\":26.0,\"cost\":2150,\"size\":-1,\"balcony\":false,\"yard\":true,\"animals\":false,\"warehouse\":false,\"protectedSpace\":false,\"furniture\":2,\"numberOfRooms\":2.0,\"numberOfRoomates\":0,\"dateOfPublish\":\"Tue May 01 05:38:49 IDT 2018\",\"text\":\"יחידת דיור 2 חדרים להשכרה בשכונה ב\u0027 רחוב בצלאל 20 חמש דקות הליכה למכללת סמי שמעון וקרובה מאוד למרכז חן. היחידה מרוהטת. 2150 כולל מים וארנונה וחשמל עד 150 שח לחודש. לכניסה מיידית .\n\",\"contacts\":[],\"lat\":31.2628471,\"lon\":34.7904706} ," +
                                "{\"apartmentID\":\"14\",\"street\":\"בצלאל\",\"number\":20,\"neighborhood\":\"שכונה ב\u0027\",\"floor\":-2,\"distanceFromUniversity\":26.0,\"cost\":2150,\"size\":-1,\"balcony\":false,\"yard\":true,\"animals\":false,\"warehouse\":false,\"protectedSpace\":false,\"furniture\":2,\"numberOfRooms\":2.0,\"numberOfRoomates\":0,\"dateOfPublish\":\"Tue May 01 05:38:49 IDT 2018\",\"text\":\"יחידת דיור 2 חדרים להשכרה בשכונה ב\u0027 רחוב בצלאל 20 חמש דקות הליכה למכללת סמי שמעון וקרובה מאוד למרכז חן. היחידה מרוהטת. 2150 כולל מים וארנונה וחשמל עד 150 שח לחודש. לכניסה מיידית .\n\",\"contacts\":[],\"lat\":31.2628471,\"lon\":34.7904706} ," +
                                "{\"apartmentID\":\"14\",\"street\":\"בצלאל\",\"number\":20,\"neighborhood\":\"שכונה ב\u0027\",\"floor\":-2,\"distanceFromUniversity\":26.0,\"cost\":2150,\"size\":-1,\"balcony\":false,\"yard\":true,\"animals\":false,\"warehouse\":false,\"protectedSpace\":false,\"furniture\":2,\"numberOfRooms\":2.0,\"numberOfRoomates\":0,\"dateOfPublish\":\"Tue May 01 05:38:49 IDT 2018\",\"text\":\"יחידת דיור 2 חדרים להשכרה בשכונה ב\u0027 רחוב בצלאל 20 חמש דקות הליכה למכללת סמי שמעון וקרובה מאוד למרכז חן. היחידה מרוהטת. 2150 כולל מים וארנונה וחשמל עד 150 שח לחודש. לכניסה מיידית .\n\",\"contacts\":[],\"lat\":31.2628471,\"lon\":34.7904706} ," +
                                "{\"apartmentID\":\"14\",\"street\":\"בצלאל\",\"number\":20,\"neighborhood\":\"שכונה ב\u0027\",\"floor\":-2,\"distanceFromUniversity\":26.0,\"cost\":2150,\"size\":-1,\"balcony\":false,\"yard\":true,\"animals\":false,\"warehouse\":false,\"protectedSpace\":false,\"furniture\":2,\"numberOfRooms\":2.0,\"numberOfRoomates\":0,\"dateOfPublish\":\"Tue May 01 05:38:49 IDT 2018\",\"text\":\"יחידת דיור 2 חדרים להשכרה בשכונה ב\u0027 רחוב בצלאל 20 חמש דקות הליכה למכללת סמי שמעון וקרובה מאוד למרכז חן. היחידה מרוהטת. 2150 כולל מים וארנונה וחשמל עד 150 שח לחודש. לכניסה מיידית .\n\",\"contacts\":[],\"lat\":31.2628471,\"lon\":34.7904706} ," +
                                "{\"apartmentID\":\"14\",\"street\":\"בצלאל\",\"number\":20,\"neighborhood\":\"שכונה ב\u0027\",\"floor\":-2,\"distanceFromUniversity\":26.0,\"cost\":2150,\"size\":-1,\"balcony\":false,\"yard\":true,\"animals\":false,\"warehouse\":false,\"protectedSpace\":false,\"furniture\":2,\"numberOfRooms\":2.0,\"numberOfRoomates\":0,\"dateOfPublish\":\"Tue May 01 05:38:49 IDT 2018\",\"text\":\"יחידת דיור 2 חדרים להשכרה בשכונה ב\u0027 רחוב בצלאל 20 חמש דקות הליכה למכללת סמי שמעון וקרובה מאוד למרכז חן. היחידה מרוהטת. 2150 כולל מים וארנונה וחשמל עד 150 שח לחודש. לכניסה מיידית .\n\",\"contacts\":[],\"lat\":31.2628471,\"lon\":34.7904706} ," +
                                "{\"apartmentID\":\"14\",\"street\":\"בצלאל\",\"number\":20,\"neighborhood\":\"שכונה ב\u0027\",\"floor\":-2,\"distanceFromUniversity\":26.0,\"cost\":2150,\"size\":-1,\"balcony\":false,\"yard\":true,\"animals\":false,\"warehouse\":false,\"protectedSpace\":false,\"furniture\":2,\"numberOfRooms\":2.0,\"numberOfRoomates\":0,\"dateOfPublish\":\"Tue May 01 05:38:49 IDT 2018\",\"text\":\"יחידת דיור 2 חדרים להשכרה בשכונה ב\u0027 רחוב בצלאל 20 חמש דקות הליכה למכללת סמי שמעון וקרובה מאוד למרכז חן. היחידה מרוהטת. 2150 כולל מים וארנונה וחשמל עד 150 שח לחודש. לכניסה מיידית .\n\",\"contacts\":[],\"lat\":31.2628471,\"lon\":34.7904706} ," +
                                "{\"apartmentID\":\"14\",\"street\":\"בצלאל\",\"number\":20,\"neighborhood\":\"שכונה ב\u0027\",\"floor\":-2,\"distanceFromUniversity\":26.0,\"cost\":2150,\"size\":-1,\"balcony\":false,\"yard\":true,\"animals\":false,\"warehouse\":false,\"protectedSpace\":false,\"furniture\":2,\"numberOfRooms\":2.0,\"numberOfRoomates\":0,\"dateOfPublish\":\"Tue May 01 05:38:49 IDT 2018\",\"text\":\"יחידת דיור 2 חדרים להשכרה בשכונה ב\u0027 רחוב בצלאל 20 חמש דקות הליכה למכללת סמי שמעון וקרובה מאוד למרכז חן. היחידה מרוהטת. 2150 כולל מים וארנונה וחשמל עד 150 שח לחודש. לכניסה מיידית .\n\",\"contacts\":[],\"lat\":31.2628471,\"lon\":34.7904706} ," +
                                "{\"apartmentID\":\"14\",\"street\":\"בצלאל\",\"number\":20,\"neighborhood\":\"שכונה ב\u0027\",\"floor\":-2,\"distanceFromUniversity\":26.0,\"cost\":2150,\"size\":-1,\"balcony\":false,\"yard\":true,\"animals\":false,\"warehouse\":false,\"protectedSpace\":false,\"furniture\":2,\"numberOfRooms\":2.0,\"numberOfRoomates\":0,\"dateOfPublish\":\"Tue May 01 05:38:49 IDT 2018\",\"text\":\"יחידת דיור 2 חדרים להשכרה בשכונה ב\u0027 רחוב בצלאל 20 חמש דקות הליכה למכללת סמי שמעון וקרובה מאוד למרכז חן. היחידה מרוהטת. 2150 כולל מים וארנונה וחשמל עד 150 שח לחודש. לכניסה מיידית .\n\",\"contacts\":[],\"lat\":31.2628471,\"lon\":34.7904706} ," +
                                "{\"apartmentID\":\"14\",\"street\":\"בצלאל\",\"number\":20,\"neighborhood\":\"שכונה ב\u0027\",\"floor\":-2,\"distanceFromUniversity\":26.0,\"cost\":2150,\"size\":-1,\"balcony\":false,\"yard\":true,\"animals\":false,\"warehouse\":false,\"protectedSpace\":false,\"furniture\":2,\"numberOfRooms\":2.0,\"numberOfRoomates\":0,\"dateOfPublish\":\"Tue May 01 05:38:49 IDT 2018\",\"text\":\"יחידת דיור 2 חדרים להשכרה בשכונה ב\u0027 רחוב בצלאל 20 חמש דקות הליכה למכללת סמי שמעון וקרובה מאוד למרכז חן. היחידה מרוהטת. 2150 כולל מים וארנונה וחשמל עד 150 שח לחודש. לכניסה מיידית .\n\",\"contacts\":[],\"lat\":31.2628471,\"lon\":34.7904706} ," +
                                "{\"apartmentID\":\"14\",\"street\":\"בצלאל\",\"number\":20,\"neighborhood\":\"שכונה ב\u0027\",\"floor\":-2,\"distanceFromUniversity\":26.0,\"cost\":2150,\"size\":-1,\"balcony\":false,\"yard\":true,\"animals\":false,\"warehouse\":false,\"protectedSpace\":false,\"furniture\":2,\"numberOfRooms\":2.0,\"numberOfRoomates\":0,\"dateOfPublish\":\"Tue May 01 05:38:49 IDT 2018\",\"text\":\"יחידת דיור 2 חדרים להשכרה בשכונה ב\u0027 רחוב בצלאל 20 חמש דקות הליכה למכללת סמי שמעון וקרובה מאוד למרכז חן. היחידה מרוהטת. 2150 כולל מים וארנונה וחשמל עד 150 שח לחודש. לכניסה מיידית .\n\",\"contacts\":[],\"lat\":31.2628471,\"lon\":34.7904706} ," +
                                "{\"apartmentID\":\"14\",\"street\":\"בצלאל\",\"number\":20,\"neighborhood\":\"שכונה ב\u0027\",\"floor\":-2,\"distanceFromUniversity\":26.0,\"cost\":2150,\"size\":-1,\"balcony\":false,\"yard\":true,\"animals\":false,\"warehouse\":false,\"protectedSpace\":false,\"furniture\":2,\"numberOfRooms\":2.0,\"numberOfRoomates\":0,\"dateOfPublish\":\"Tue May 01 05:38:49 IDT 2018\",\"text\":\"יחידת דיור 2 חדרים להשכרה בשכונה ב\u0027 רחוב בצלאל 20 חמש דקות הליכה למכללת סמי שמעון וקרובה מאוד למרכז חן. היחידה מרוהטת. 2150 כולל מים וארנונה וחשמל עד 150 שח לחודש. לכניסה מיידית .\n\",\"contacts\":[],\"lat\":31.2628471,\"lon\":34.7904706} ," +
                                "{\"apartmentID\":\"14\",\"street\":\"בצלאל\",\"number\":20,\"neighborhood\":\"שכונה ב\u0027\",\"floor\":-2,\"distanceFromUniversity\":26.0,\"cost\":2150,\"size\":-1,\"balcony\":false,\"yard\":true,\"animals\":false,\"warehouse\":false,\"protectedSpace\":false,\"furniture\":2,\"numberOfRooms\":2.0,\"numberOfRoomates\":0,\"dateOfPublish\":\"Tue May 01 05:38:49 IDT 2018\",\"text\":\"יחידת דיור 2 חדרים להשכרה בשכונה ב\u0027 רחוב בצלאל 20 חמש דקות הליכה למכללת סמי שמעון וקרובה מאוד למרכז חן. היחידה מרוהטת. 2150 כולל מים וארנונה וחשמל עד 150 שח לחודש. לכניסה מיידית .\n\",\"contacts\":[],\"lat\":31.2628471,\"lon\":34.7904706} ," +
                                "{\"apartmentID\":\"14\",\"street\":\"בצלאל\",\"number\":20,\"neighborhood\":\"שכונה ב\u0027\",\"floor\":-2,\"distanceFromUniversity\":26.0,\"cost\":2150,\"size\":-1,\"balcony\":false,\"yard\":true,\"animals\":false,\"warehouse\":false,\"protectedSpace\":false,\"furniture\":2,\"numberOfRooms\":2.0,\"numberOfRoomates\":0,\"dateOfPublish\":\"Tue May 01 05:38:49 IDT 2018\",\"text\":\"יחידת דיור 2 חדרים להשכרה בשכונה ב\u0027 רחוב בצלאל 20 חמש דקות הליכה למכללת סמי שמעון וקרובה מאוד למרכז חן. היחידה מרוהטת. 2150 כולל מים וארנונה וחשמל עד 150 שח לחודש. לכניסה מיידית .\n\",\"contacts\":[],\"lat\":31.2628471,\"lon\":34.7904706} ," +
                                "{\"apartmentID\":\"14\",\"street\":\"בצלאל\",\"number\":20,\"neighborhood\":\"שכונה ב\u0027\",\"floor\":-2,\"distanceFromUniversity\":26.0,\"cost\":2150,\"size\":-1,\"balcony\":false,\"yard\":true,\"animals\":false,\"warehouse\":false,\"protectedSpace\":false,\"furniture\":2,\"numberOfRooms\":2.0,\"numberOfRoomates\":0,\"dateOfPublish\":\"Tue May 01 05:38:49 IDT 2018\",\"text\":\"יחידת דיור 2 חדרים להשכרה בשכונה ב\u0027 רחוב בצלאל 20 חמש דקות הליכה למכללת סמי שמעון וקרובה מאוד למרכז חן. היחידה מרוהטת. 2150 כולל מים וארנונה וחשמל עד 150 שח לחודש. לכניסה מיידית .\n\",\"contacts\":[],\"lat\":31.2628471,\"lon\":34.7904706} ," +
                                "{\"apartmentID\":\"14\",\"street\":\"בצלאל\",\"number\":20,\"neighborhood\":\"שכונה ב\u0027\",\"floor\":-2,\"distanceFromUniversity\":26.0,\"cost\":2150,\"size\":-1,\"balcony\":false,\"yard\":true,\"animals\":false,\"warehouse\":false,\"protectedSpace\":false,\"furniture\":2,\"numberOfRooms\":2.0,\"numberOfRoomates\":0,\"dateOfPublish\":\"Tue May 01 05:38:49 IDT 2018\",\"text\":\"יחידת דיור 2 חדרים להשכרה בשכונה ב\u0027 רחוב בצלאל 20 חמש דקות הליכה למכללת סמי שמעון וקרובה מאוד למרכז חן. היחידה מרוהטת. 2150 כולל מים וארנונה וחשמל עד 150 שח לחודש. לכניסה מיידית .\n\",\"contacts\":[],\"lat\":31.2628471,\"lon\":34.7904706} ," +
                                "{\"apartmentID\":\"14\",\"street\":\"בצלאל\",\"number\":20,\"neighborhood\":\"שכונה ב\u0027\",\"floor\":-2,\"distanceFromUniversity\":26.0,\"cost\":2150,\"size\":-1,\"balcony\":false,\"yard\":true,\"animals\":false,\"warehouse\":false,\"protectedSpace\":false,\"furniture\":2,\"numberOfRooms\":2.0,\"numberOfRoomates\":0,\"dateOfPublish\":\"Tue May 01 05:38:49 IDT 2018\",\"text\":\"יחידת דיור 2 חדרים להשכרה בשכונה ב\u0027 רחוב בצלאל 20 חמש דקות הליכה למכללת סמי שמעון וקרובה מאוד למרכז חן. היחידה מרוהטת. 2150 כולל מים וארנונה וחשמל עד 150 שח לחודש. לכניסה מיידית .\n\",\"contacts\":[],\"lat\":31.2628471,\"lon\":34.7904706} ," +
                                "{\"apartmentID\":\"14\",\"street\":\"בצלאל\",\"number\":20,\"neighborhood\":\"שכונה ב\u0027\",\"floor\":-2,\"distanceFromUniversity\":26.0,\"cost\":2150,\"size\":-1,\"balcony\":false,\"yard\":true,\"animals\":false,\"warehouse\":false,\"protectedSpace\":false,\"furniture\":2,\"numberOfRooms\":2.0,\"numberOfRoomates\":0,\"dateOfPublish\":\"Tue May 01 05:38:49 IDT 2018\",\"text\":\"יחידת דיור 2 חדרים להשכרה בשכונה ב\u0027 רחוב בצלאל 20 חמש דקות הליכה למכללת סמי שמעון וקרובה מאוד למרכז חן. היחידה מרוהטת. 2150 כולל מים וארנונה וחשמל עד 150 שח לחודש. לכניסה מיידית .\n\",\"contacts\":[],\"lat\":31.2628471,\"lon\":34.7904706} ," +
                                "{\"apartmentID\":\"14\",\"street\":\"בצלאל\",\"number\":20,\"neighborhood\":\"שכונה ב\u0027\",\"floor\":-2,\"distanceFromUniversity\":26.0,\"cost\":2150,\"size\":-1,\"balcony\":false,\"yard\":true,\"animals\":false,\"warehouse\":false,\"protectedSpace\":false,\"furniture\":2,\"numberOfRooms\":2.0,\"numberOfRoomates\":0,\"dateOfPublish\":\"Tue May 01 05:38:49 IDT 2018\",\"text\":\"יחידת דיור 2 חדרים להשכרה בשכונה ב\u0027 רחוב בצלאל 20 חמש דקות הליכה למכללת סמי שמעון וקרובה מאוד למרכז חן. היחידה מרוהטת. 2150 כולל מים וארנונה וחשמל עד 150 שח לחודש. לכניסה מיידית .\n\",\"contacts\":[],\"lat\":31.2628471,\"lon\":34.7904706} ," +
                                "{\"apartmentID\":\"14\",\"street\":\"בצלאל\",\"number\":20,\"neighborhood\":\"שכונה ב\u0027\",\"floor\":-2,\"distanceFromUniversity\":26.0,\"cost\":2150,\"size\":-1,\"balcony\":false,\"yard\":true,\"animals\":false,\"warehouse\":false,\"protectedSpace\":false,\"furniture\":2,\"numberOfRooms\":2.0,\"numberOfRoomates\":0,\"dateOfPublish\":\"Tue May 01 05:38:49 IDT 2018\",\"text\":\"יחידת דיור 2 חדרים להשכרה בשכונה ב\u0027 רחוב בצלאל 20 חמש דקות הליכה למכללת סמי שמעון וקרובה מאוד למרכז חן. היחידה מרוהטת. 2150 כולל מים וארנונה וחשמל עד 150 שח לחודש. לכניסה מיידית .\n\",\"contacts\":[],\"lat\":31.2628471,\"lon\":34.7904706} ," +
                                "{\"apartmentID\":\"14\",\"street\":\"בצלאל\",\"number\":20,\"neighborhood\":\"שכונה ב\u0027\",\"floor\":-2,\"distanceFromUniversity\":26.0,\"cost\":2150,\"size\":-1,\"balcony\":false,\"yard\":true,\"animals\":false,\"warehouse\":false,\"protectedSpace\":false,\"furniture\":2,\"numberOfRooms\":2.0,\"numberOfRoomates\":0,\"dateOfPublish\":\"Tue May 01 05:38:49 IDT 2018\",\"text\":\"יחידת דיור 2 חדרים להשכרה בשכונה ב\u0027 רחוב בצלאל 20 חמש דקות הליכה למכללת סמי שמעון וקרובה מאוד למרכז חן. היחידה מרוהטת. 2150 כולל מים וארנונה וחשמל עד 150 שח לחודש. לכניסה מיידית .\n\",\"contacts\":[],\"lat\":31.2628471,\"lon\":34.7904706} " +
                                "]}";
                System.out.println(jsonString);
                SearchResultsDTO searchResultsDTO = SearchResultsDTO.fromJSON(jsonString);
                SearchResults searchResults = new SearchResults(searchResultsDTO);
                alternativeResultRecords = searchResults.getResultRecords();
                if (alternativeResultRecords != null) {
                    fromSearchResultsToAlternativeResults();
                }
            } */


        }
    }

    public AlertDialog.Builder buildDialogNoAlternativeResults(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("אין תוצאות נוספות");
        builder.setPositiveButton("סגור", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return builder;
    }


    public AlertDialog.Builder buildDialogNotNetwork(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("אין חיבור אינטרנט");
        builder.setMessage("אינטרנט אינו פועל. על מנת לטעון תוצאות נוספות יש לוודא חיבור לאינטרנט.");
        builder.setPositiveButton("סגור", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return builder;
    }

    public AlertDialog.Builder buildDialogDownloadAlternativeResults(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("טעינת תוצאות אלטרנטיביות");
        builder.setMessage("האם ברצונך לטעון תוצאות אלטרנטיביות לתוצאות החיפוש?");
        builder.setPositiveButton("כן", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                downloadAlternativeResults();
            }
        });
        builder.setNegativeButton("לא", new DialogInterface.OnClickListener() {
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

    public void fromSearchResultsToAlternativeResults() {
        if (alternativeResultRecords.length == 0) {
            buildDialogNoAlternativeResults(ResultsActivity.this).show();
        } else {
            index = 0;
            resultRecordsMode = 1 - resultRecordsMode;
            updateViews();
        }
        configButtons();
    }

    public void setNumberOfPreviousResults(int numberOfPreviousResults) {
        this.numberOfPreviousResults = numberOfPreviousResults;
    }
}

