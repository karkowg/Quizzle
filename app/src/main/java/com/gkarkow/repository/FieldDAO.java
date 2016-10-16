package com.gkarkow.repository;

import com.activeandroid.query.Select;

import java.util.List;

import com.gkarkow.model.Field;

public class FieldDAO {

    public static Field getFieldByName(String name) {
        try {
            return new Select().from(Field.class).where("Name = ?", name).executeSingle();
        } catch (Exception ignored) {

        }
        return null;
    }

    public static List<Field> getAll() {
        try {
            return new Select().from(Field.class).orderBy("Name").execute();
        } catch (Exception ignored) {

        }
        return null;
    }
}
