package com.example.final_project_client.UserSearchingUtils;

/**
 * Created by TAMIR on 4/29/2018.
 */

public enum Neighborhood {
    unknown("", "לא נבחר"),
    ramot("רמות","רמות"),
    alef("שכונה א'", "שכונה א"),
    bet("שכונה ב'", "שכונה ב"),
    gimel("שכונה ג'", "שכונה ג"),
    dalet("שכונה ד'", "שכונה ד"),
    hi("שכונה ה'", "שכונה ה"),
    vav("שכונה ו'", "שכונה ו"),
    tet("שכונה ט'", "שכונה ט"),
    yudalef("שכונה יא","שכונה יא");



    private final String value;
    private final String name;
    private Neighborhood(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static Neighborhood findByName(String name) {
        for(Neighborhood neighborhood: Neighborhood.values()){
            if(neighborhood.name.equals(name))
                return neighborhood;
        }
        return null;
    }
}
