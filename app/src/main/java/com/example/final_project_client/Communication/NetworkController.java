package com.example.final_project_client.Communication;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.final_project_client.DTOs.SearchResultsDTO;
import com.example.final_project_client.DTOs.UserSearchDTO;
import com.example.final_project_client.UserSearchingUtils.SearchResults;
import com.example.final_project_client.UserSearchingUtils.UserSearch;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Created by TAMIR on 4/19/2018.
 */

public class NetworkController implements CommunicationInterface {
    private static final String URL = "https://final-project-server.azurewebsites.net";
    private static NetworkController INSTANCE = null;
    private RequestQueue queue = null;
    private static final int TimeBetweenRequestsMS = 2000;
    private long lastTimeRequestSend;

    private NetworkController(Context context) {
        queue = Volley.newRequestQueue(context);
        lastTimeRequestSend = System.nanoTime();
    }

    public static synchronized NetworkController getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new NetworkController(context);
        }
        return INSTANCE;

    }

    @Override
    public void hello(final String name, final NetworkListener<String> listener) {
        String fullURL = URL + "/hello";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, fullURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        listener.getResult(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("no network");

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                return params;
            }
        };

        queue.add(stringRequest);
    }

    @Override
    public synchronized void searchApartments(final UserSearch userSearch, final NetworkListener<SearchResults> listener, final NetworkListener<String> errorListener) {
        long time = System.nanoTime();
        if ((time - lastTimeRequestSend)/1000000 >= TimeBetweenRequestsMS) {
            String fullURL = URL + "/searchApartments";
            UserSearchDTO userSearchDTO = new UserSearchDTO(userSearch);
            final String jsonString = userSearchDTO.toJson();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, fullURL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                                SearchResultsDTO searchResultsDTO = new SearchResultsDTO(response);
                                SearchResults searchResults = new SearchResults(searchResultsDTO);
                                listener.getResult(searchResults);
                            }


                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {


                                errorListener.getResult(error.toString());
                            }



                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("userSearchDTOString", jsonString);
                    return params;
                }
            };


            stringRequest.setShouldCache(false);
            queue.add(stringRequest);
            lastTimeRequestSend = System.nanoTime();
            }


        }
    }



