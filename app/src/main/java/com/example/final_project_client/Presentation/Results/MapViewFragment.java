package com.example.final_project_client.Presentation.Results;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.final_project_client.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapViewFragment extends Fragment implements OnMapReadyCallback, ResultsView {

    private View mView;
    private MapView gMapView;
    private GoogleMap mMap;
    private Map<Marker, List<Integer>> map;
    private Marker chosenMarker;
    private int indexOfDisplayedApartment;
    private List<Integer> apartmentIndexesOfMarker;
    private BriefDescriptionFragment briefDescriptionFragment;
    private boolean firstLaunch;


    public MapViewFragment() {
        indexOfDisplayedApartment = -1;
    }

    @Override
    public void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        firstLaunch = true;
        chosenMarker = null;

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
        FrameLayout briefDescriptionsFrame = (FrameLayout) mView.findViewById(R.id.briefDescriptionsFrame);
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
                if (indexOfDisplayedApartment >= 0 && apartmentIndexesOfMarker != null) {
                    ((ResultsActivity) getActivity()).openApartmentFullDescription(apartmentIndexesOfMarker.get(indexOfDisplayedApartment));

                }
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
                if (chosenMarker == null || !chosenMarker.equals(marker)) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
                    apartmentIndexesOfMarker = map.get(marker);
                    map.remove(marker);
                    if (chosenMarker != null && !chosenMarker.equals(marker)) {
                        chosenMarker.remove();
                        List<Integer> indexes = map.get(chosenMarker);
                        map.remove(chosenMarker);
                        Marker lastChoosenMarker = mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(chosenMarker.getPosition().latitude, chosenMarker.getPosition().longitude)));
                        map.put(lastChoosenMarker, indexes);

                    }
                    marker.remove();
                    chosenMarker = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude))
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                    map.put(chosenMarker, apartmentIndexesOfMarker);
                    indexOfDisplayedApartment = 0;
                    if (apartmentIndexesOfMarker != null && apartmentIndexesOfMarker.size() >= 1) {
                        System.out.println("item = " + apartmentIndexesOfMarker.get(indexOfDisplayedApartment));
                        int index = apartmentIndexesOfMarker.get(indexOfDisplayedApartment);
                        ApartmentBriefDescription apartmentBriefDescription1 = ((ResultsActivity) getActivity()).getApartmentBriefDescription(index);
                        BriefDescriptionFragment newBriefDescriptionFragment = BriefDescriptionFragment.getInstance(apartmentBriefDescription1, indexOfDisplayedApartment, apartmentIndexesOfMarker.size());
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
                    apartmentIndexesOfMarker = null;
                    setMarkerHasChoosen(chosenMarker, false);
                }


            }
        });


        ((ResultsActivity) getActivity()).getData(this);


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

        if (firstLaunch) {
            if (map.keySet().size() >= 3) {
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for (Marker marker : map.keySet()) {
                    builder.include(marker.getPosition());
                }
                LatLngBounds bounds = builder.build();
                int padding = 150; // offset from edges of the map in pixels
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                mMap.moveCamera(cu);

            } else {
                LatLng BeershebaCenter = new LatLng(31.25181, 34.7913);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(BeershebaCenter, 12.9f));
            }
            firstLaunch = false;
        } else {
            if (map.keySet().size() >= 3) {
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for (Marker marker : map.keySet()) {
                    builder.include(marker.getPosition());
                }
                LatLngBounds bounds = builder.build();
                int padding = 150; // offset from edges of the map in pixels
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                mMap.animateCamera(cu);

            } else {
                LatLng BeershebaCenter = new LatLng(31.25181, 34.7913);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(BeershebaCenter, 12.9f));

            }

        }
    }


    public Marker setMarkerHasChoosen(Marker marker, boolean isChoosen) {
        if (marker != null) {
            Marker newMarker;
            List<Integer> indexes;
            if (isChoosen) {
                chosenMarker = marker;
                marker.remove();
                indexes = map.get(marker);
                map.remove(marker);
                newMarker = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude))
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                map.put(newMarker, indexes);
                return newMarker;

            } else {
                chosenMarker = null;
                marker.remove();
                indexes = map.get(marker);
                map.remove(marker);
                newMarker = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude)));
                map.put(newMarker, indexes);
            }

        }
        return null;
    }

    public void nextApartmentBrief() {
        if (indexOfDisplayedApartment < apartmentIndexesOfMarker.size() - 1) {
            indexOfDisplayedApartment++;
            int index = apartmentIndexesOfMarker.get(indexOfDisplayedApartment);
            ApartmentBriefDescription apartmentBriefDescription = ((ResultsActivity) getActivity()).getApartmentBriefDescription(index);
            BriefDescriptionFragment newBriefDescriptionFragment = BriefDescriptionFragment.getInstance(apartmentBriefDescription, indexOfDisplayedApartment, apartmentIndexesOfMarker.size());
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            briefDescriptionFragment = newBriefDescriptionFragment;
            transaction.replace(R.id.briefDescriptionsFrame, newBriefDescriptionFragment);
            transaction.commit();
        }

    }


    public void previousApartmentBrief() {
        if (indexOfDisplayedApartment > 0) {
            indexOfDisplayedApartment--;
            int index = apartmentIndexesOfMarker.get(indexOfDisplayedApartment);
            ApartmentBriefDescription apartmentBriefDescription = ((ResultsActivity) getActivity()).getApartmentBriefDescription(index);
            BriefDescriptionFragment newBriefDescriptionFragment = BriefDescriptionFragment.getInstance(apartmentBriefDescription, indexOfDisplayedApartment, apartmentIndexesOfMarker.size());
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            briefDescriptionFragment = newBriefDescriptionFragment;
            transaction.replace(R.id.briefDescriptionsFrame, newBriefDescriptionFragment);
            transaction.commit();
        }

    }


    public void removeBriefDescription() {
        if (briefDescriptionFragment != null && getFragmentManager() != null) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.remove(briefDescriptionFragment);
            transaction.commit();
            briefDescriptionFragment = null;
            indexOfDisplayedApartment = -1;
            apartmentIndexesOfMarker = null;
            setMarkerHasChoosen(chosenMarker, false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        gMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        gMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        gMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        gMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        gMapView.onLowMemory();
    }


}
