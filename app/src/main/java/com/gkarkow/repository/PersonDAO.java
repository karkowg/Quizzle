package com.gkarkow.repository;

import com.activeandroid.query.Select;

import com.gkarkow.model.Person;

public class PersonDAO {

    public static Person getPersonByEmail(String email) {
        try {
            return new Select().from(Person.class).where("Email = ?", email).executeSingle();
        } catch (Exception ignored) {

        }
        return null;
    }
}
