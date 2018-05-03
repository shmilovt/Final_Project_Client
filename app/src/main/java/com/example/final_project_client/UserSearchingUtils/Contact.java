package com.example.final_project_client.UserSearchingUtils;

import com.example.final_project_client.DTOs.ContactDTO;

/**
 * Created by TAMIR on 4/29/2018.
 */

public class Contact {

    private String name;
    private String phone;

    public Contact(ContactDTO contactDTO) {
        name = contactDTO.getName();
        phone = contactDTO.getPhone();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
