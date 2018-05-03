package com.example.final_project_client.Presentation;

import com.example.final_project_client.UserSearchingUtils.Contact;
import com.example.final_project_client.UserSearchingUtils.ResultRecord;

/**
 * Created by TAMIR on 5/1/2018.
 */

public class ApartmentFullDescription {
    private String address;
    private String neighborhood;
    private String cost;
    private String floor;
    private String date;
    private String numOfRooms;
    private String numOfRoomates;
    private String apartmentSize;
    private String distaceFromUniversity;
    private String contacts;
    private String txt;

    public ApartmentFullDescription(ResultRecord resultRecord) {
        address = resultRecord.getStreet()+" "+resultRecord.getNumber();
        neighborhood = resultRecord.getNeighborhood();
        cost = ""+resultRecord.getCost();
        floor = ""+resultRecord.getFloor();
        date = resultRecord.getDateOfPublish();
        numOfRooms = ""+resultRecord.getNumberOfRooms();
        numOfRoomates = ""+resultRecord.getNumberOfRoomates();
        apartmentSize = ""+resultRecord.getSize();
        distaceFromUniversity = ""+(int)resultRecord.getDistanceFromUniversity();
        txt = resultRecord.getText();
        contacts = "";
        for(Contact contact : resultRecord.getContacts()){
           contacts  = contact + contact.getName()+"\n";
        }

    }
    public String toString(){
        String output ;
        date = "תאריך פרסום: " + date;
        address = "כתובת: " + address;
        neighborhood = "שכונה: " + neighborhood;
        cost = "מחיר: " + cost;
        floor = "קומה: " + floor;
        numOfRoomates = "מספר שותפים: " + numOfRoomates;
        numOfRooms = "מספר חדרים: " + numOfRooms;
        apartmentSize = "גודל דירה: "+ apartmentSize +" מ\"ר";
        distaceFromUniversity = "מרחק הליכה מאוניברסיטה: "+ distaceFromUniversity + " דקות";
        contacts = "אנשי קשר: "+ "\n" + contacts;
        txt = "מקור: "+"\n"+txt;
        output = date+"\n\n"+ address + "\n\n" + neighborhood + "\n\n" +floor+ "\n\n" + cost +"\n\n"+ numOfRoomates +"\n\n"+ numOfRooms +"\n\n"+ apartmentSize+"\n\n"+ distaceFromUniversity;
        return output;
    }
}
