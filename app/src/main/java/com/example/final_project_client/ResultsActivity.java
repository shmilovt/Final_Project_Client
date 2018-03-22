package com.example.final_project_client;



import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.FragmentTransaction;


public class ResultsActivity extends AppCompatActivity {

    private MapViewFragment mapViewFragment;
    private ListViewFragment listViewFragment;
    private Button btnMode;
    private Button btnRecentlyResults;
    private Button btnNextResults;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchresults);



            if (savedInstanceState != null) {
                return;
            }
            mapViewFragment = new MapViewFragment();
            listViewFragment = new ListViewFragment();
            btnMode = findViewById(R.id.BtnMode);
            btnRecentlyResults = findViewById(R.id.BtnRecentlyResults);
            btnNextResults = findViewById(R.id.BtnNextResults);

        mapViewFragment.setArguments(getIntent().getExtras());
        getFragmentManager().beginTransaction()
                .add(R.id.fragment_container,  listViewFragment).commit();

            btnMode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(btnMode.getText().equals(getString(R.string.map_mode)))
                    {
                        btnMode.setText(getString(R.string.record_mode));
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, mapViewFragment);
                        transaction.commit();
                    }
                    else{
                        btnMode.setText(getString(R.string.map_mode));
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, listViewFragment);
                        transaction.commit();
                    }
                }
            });


    }



}
