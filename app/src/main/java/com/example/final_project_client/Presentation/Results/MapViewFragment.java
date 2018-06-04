package com.example.final_project_client.Presentation.Results;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.final_project_client.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapViewFragment extends Fragment implements OnMapReadyCallback, ResultsView {


    private MapView gMapView;
    private Marker choosenMarker;
    private GoogleMap mMap;
    private View mView;
    private int indexOfDisplayedApartment;
    private List<Integer> apartmentIndexesOfMarker;
    private BriefDescriptionFragment briefDescriptionFragment;
    private FrameLayout briefDescriptionsFrame;
    private Map<Marker, List<Integer>> map;


    public MapViewFragment() {
        indexOfDisplayedApartment = -1;
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
        briefDescriptionsFrame = (FrameLayout) mView.findViewById(R.id.briefDescriptionsFrame);
        briefDescriptionsFrame.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
            public void onSwipeTop() {

            }

            public void onSwipeRight() {
                nextApartmentBrief();
            }

            public void onSwipeLeft() {
                previousApartmentBrief();


            }

            public void onSwipeBottom() {

            }

            @Override
            public void onClick() {
                super.onClick();
                if (indexOfDisplayedApartment >= 0)
                    ((ResultsActivity) getActivity()).openApartmentFullDescription(apartmentIndexesOfMarker.get(indexOfDisplayedApartment));
            }



        });


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
        System.out.println("mark index: " + indexOfDisplayedApartment);
        mMap = googleMap;
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
                apartmentIndexesOfMarker = map.get(marker);
                map.remove(marker);
                if (choosenMarker != null) {
                    choosenMarker.remove();
                    List<Integer> indexes = map.get(choosenMarker);
                    map.remove(choosenMarker);
                    Marker lastChoosenMarker = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(choosenMarker.getPosition().latitude, choosenMarker.getPosition().longitude)));
                    map.put(lastChoosenMarker, indexes);

                }
                marker.remove();
                choosenMarker = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                map.put(choosenMarker, apartmentIndexesOfMarker);
                indexOfDisplayedApartment = 0;
                if (apartmentIndexesOfMarker != null && apartmentIndexesOfMarker.size() >= 1) {
                    System.out.println("item = " + apartmentIndexesOfMarker.get(indexOfDisplayedApartment));
                    int index = apartmentIndexesOfMarker.get(indexOfDisplayedApartment);
                    ApartmentBriefDescription apartmentBriefDescription = ((ResultsActivity) getActivity()).getApartmentBriefDescription(index);
                    BriefDescriptionFragment newBriefDescriptionFragment = BriefDescriptionFragment.getInstance(apartmentBriefDescription, indexOfDisplayedApartment , apartmentIndexesOfMarker.size());
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    if (briefDescriptionFragment == null) {
                        briefDescriptionFragment = newBriefDescriptionFragment;
                        transaction.add(R.id.briefDescriptionsFrame, newBriefDescriptionFragment);
                        transaction.commit();
                    } else {
                        briefDescriptionFragment = newBriefDescriptionFragment;
                        transaction.replace(R.id.briefDescriptionsFrame, newBriefDescriptionFragment);
                        transaction.commit();
                    }

                }

                return true;
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                if (briefDescriptionFragment != null) {
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.remove(briefDescriptionFragment);
                    transaction.commit();
                    briefDescriptionFragment = null;
                    indexOfDisplayedApartment = -1;
                }

                setMarkerHasChoosen(choosenMarker, false);
            }
        });

        ((ResultsActivity) getActivity()).getData(this);

   /*     if (briefDescriptionFragment != null && indexOfDisplayedApartment >= 0 && apartmentIndexesOfMarker != null) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.remove(briefDescriptionFragment);
            transaction.add(R.id.briefDescriptionsFrame, briefDescriptionFragment);
            transaction.commit();
            setMarkerHasChoosen(choosenMarker, true);
        }*/

        LatLng BeershebaCenter = new LatLng(31.25181, 34.7913);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(BeershebaCenter));
        mMap.setMinZoomPreference(12.9f);
    }


    @Override
    public void updateData(ResultsActivity resultsActivity) {

        List<Coordinate> coordinateList = resultsActivity.getCoordinates();
        if (map != null) {
            for (Marker marker : map.keySet()) {
                if (marker != null)
                    marker.remove();
            }
        }
        map = new HashMap<>();
        for (int i = 0; i < coordinateList.size(); i++) {
            Coordinate coordinate = coordinateList.get(i);
            boolean exist = false;
            for (Marker marker : map.keySet()) {
                if (marker.getPosition().latitude == coordinate.getLat() && marker.getPosition().longitude == coordinate.getLon()) {
                    List<Integer> indexes = map.get(marker);
                    indexes.add(i);
                    map.put(marker, indexes);
                    exist = true;
                    break;
                }
            }

            if (!exist) {
                LatLng latLng = new LatLng(coordinate.getLat(), coordinate.getLon());
                MarkerOptions markerOption = new MarkerOptions();
                markerOption.position(latLng);
                Marker marker = mMap.addMarker(markerOption);
                List<Integer> indexes = new ArrayList<>();
                indexes.add(i);
                map.put(marker, indexes);
            }
        }


    }

    public void returnFromListMode() {
        setMarkerHasChoosen(choosenMarker, false);

    }

    public void setMarkerHasChoosen(Marker marker, boolean isChoosen) {
        if (marker != null) {
            Marker newMarker;
            List<Integer> indexes;
            if (isChoosen) {
                marker.remove();
                indexes = map.get(marker);
                map.remove(marker);
                newMarker = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                map.put(newMarker, indexes);

            } else {
                marker.remove();
                indexes = map.get(marker);
                map.remove(marker);
                newMarker = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude)));
                map.put(newMarker, indexes);
            }
        }
    }

    public void nextApartmentBrief(){
        if (indexOfDisplayedApartment < apartmentIndexesOfMarker.size() - 1) {
            indexOfDisplayedApartment++;
            int index = apartmentIndexesOfMarker.get(indexOfDisplayedApartment);
            ApartmentBriefDescription apartmentBriefDescription = ((ResultsActivity) getActivity()).getApartmentBriefDescription(index);
            BriefDescriptionFragment newBriefDescriptionFragment = BriefDescriptionFragment.getInstance(apartmentBriefDescription, indexOfDisplayedApartment , apartmentIndexesOfMarker.size());
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            briefDescriptionFragment = newBriefDescriptionFragment;
            transaction.replace(R.id.briefDescriptionsFrame, newBriefDescriptionFragment);
            transaction.commit();
        }

    }


    public void previousApartmentBrief(){
        if (indexOfDisplayedApartment > 0) {
            indexOfDisplayedApartment--;
            int index = apartmentIndexesOfMarker.get(indexOfDisplayedApartment);
            ApartmentBriefDescription apartmentBriefDescription = ((ResultsActivity) getActivity()).getApartmentBriefDescription(index);
            BriefDescriptionFragment newBriefDescriptionFragment = BriefDescriptionFragment.getInstance(apartmentBriefDescription, indexOfDisplayedApartment , apartmentIndexesOfMarker.size());
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            briefDescriptionFragment = newBriefDescriptionFragment;
            transaction.replace(R.id.briefDescriptionsFrame, newBriefDescriptionFragment);
            transaction.commit();
        }

    }


}
