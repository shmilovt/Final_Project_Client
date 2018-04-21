package com.example.final_project_client.Communication;
import com.example.final_project_client.UserSearchingUtils.SearchResults;
import com.example.final_project_client.UserSearchingUtils.UserSearch;

/**
 * Created by TAMIR on 4/19/2018.
 */

interface CommunicationInterface {
    void hello(String name,  NetworkListener<String> listener);
    void searchApartments(UserSearch userSearch, NetworkListener<SearchResults> listener);
}
