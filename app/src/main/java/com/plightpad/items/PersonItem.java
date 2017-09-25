package com.plightpad.items;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class PersonItem {
    String name;
    int value;

    public PersonItem(String s) {
        this.name = s;
    }

    static public List<PersonItem> parseStringList(List<String> list) {
        List<PersonItem> pItems = new ArrayList<>();

        for (String s : list) {
            pItems.add(new PersonItem(s));
        }
        return pItems;
    }
    public  PersonItem(){

    }
}
