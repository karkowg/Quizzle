package com.gkarkow.validation;

import com.gkarkow.model.Person;
import com.gkarkow.repository.PersonDAO;

public class PersonValidation {

    public static boolean validate(String email, String password) {
        Person person = PersonDAO.getPersonByEmail(email);
        return person == null? false : password.equals(person.getPassword());
    }
}
