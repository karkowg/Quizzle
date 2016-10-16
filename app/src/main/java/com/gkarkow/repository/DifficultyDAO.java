package com.gkarkow.repository;

import com.activeandroid.query.Select;

import com.gkarkow.model.Difficulty;

public class DifficultyDAO {

    public static Difficulty getDifficultyByName(String name) {
        try {
            return new Select().from(Difficulty.class).where("Name = ?", name).executeSingle();
        } catch (Exception ignored) {

        }
        return null;
    }
}
