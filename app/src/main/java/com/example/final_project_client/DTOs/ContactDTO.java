package com.example.final_project_client.DTOs;

/**
 * Created by TAMIR on 4/27/2018.
 */

public class ContactDTO {
    private String name;
    private String phone;

    public ContactDTO(){ }
    public ContactDTO(String name, String phone) {
        this.name = name;
        this.phone = phone;
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
