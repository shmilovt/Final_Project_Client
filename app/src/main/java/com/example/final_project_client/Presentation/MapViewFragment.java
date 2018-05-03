package com.example.final_project_client.Presentation;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapViewFragment extends Fragment implements OnMapReadyCallback, ResultsView {

    private MapView gMapView;
    private GoogleMap mMap;
    private View mView;
    private Marker[] markers;


    public MapViewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.map_view, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gMapView = (MapView) mView.findViewById(R.id.map);
        if (gMapView != null) {
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
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //mMap.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
                int i=0;
                for(Marker mark : markers ){
                    if(mark.equals(marker)){
                        break;
                    }
                    i++;
                }
                ((ResultsActivity)getActivity()).openApartmentFullDescription(i);
                return true;
            }
        });

        ((ResultsActivity)getActivity()).getData(this);
        LatLng BeershebaCenter = new LatLng(31.25181, 34.7913);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(BeershebaCenter));
        mMap.setMinZoomPreference(12.9f);
    }


    @Override
    public void updateData(ResultsActivity resultsActivity) {
        List<Coordinate> coordinateList = resultsActivity.getCoordinates();
        if(markers!=null){
            for(Marker marker : markers){
                if(marker!=null)
                    marker.remove();
            }
        }
        markers = new Marker[coordinateList.size()];
        for (int i = 0; i < markers.length; i++){
            Coordinate coordinate = coordinateList.get(i);
            LatLng  latLng = new LatLng(coordinate.getLat(), coordinate.getLon());
            MarkerOptions markerOption = new MarkerOptions();
            markerOption.position(latLng);
            Marker marker =mMap.addMarker(markerOption);
            markers[i] = marker;
        }



    }
}
