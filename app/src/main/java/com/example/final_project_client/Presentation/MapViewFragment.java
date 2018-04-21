package com.example.final_project_client.Presentation;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.final_project_client.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapViewFragment extends Fragment implements OnMapReadyCallback {

    private MapView gMapView;
    private GoogleMap mMap;
    private View mView;


    public MapViewFragment(){

    }
    @Override
    public void onCreate(Bundle savedInstaceState){super.onCreate(savedInstaceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.map_view, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        gMapView = (MapView)mView.findViewById(R.id.map);
        if(gMapView != null){
            gMapView.onCreate(null);
            gMapView.onResume();
            gMapView.getMapAsync(this);
        }
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng BeerSheva1 = new LatLng(31.261129, 34.798384);
        LatLng BeerSheva2 = new LatLng(31.263432, 34.794884);
        LatLng BeerSheva3 = new LatLng(31.262129, 34.797384);
        LatLng BeerSheva4 = new LatLng(31.256561, 34.794576);
        LatLng BeerSheva5 = new LatLng(31.270088, 34.779584);
        LatLng BeerSheva6 = new LatLng(31.280991, 34.799228);
        LatLng BeerSheva7 = new LatLng(31.263732, 34.794841);

        LatLng BeershebaCenter = new LatLng(31.25181, 34.7913);


        mMap.addMarker(new MarkerOptions().position(BeerSheva1));
        mMap.addMarker(new MarkerOptions().position(BeerSheva2));
        mMap.addMarker(new MarkerOptions().position(BeerSheva3));
        mMap.addMarker(new MarkerOptions().position(BeerSheva4));
        mMap.addMarker(new MarkerOptions().position(BeerSheva5));
        mMap.addMarker(new MarkerOptions().position(BeerSheva6));
        mMap.addMarker(new MarkerOptions().position(BeerSheva7).title("אלכסנדר ינאי 17").snippet("קומה: 5" + "\n" +
                "מספר שותפים: 2" +"\n"+
                "מחיר: 2900"));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(BeershebaCenter));
        mMap.setMinZoomPreference(12.9f);
    }



}
