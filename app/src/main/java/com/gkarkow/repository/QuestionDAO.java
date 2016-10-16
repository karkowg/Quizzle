package com.gkarkow.repository;

import com.activeandroid.query.Select;

import com.gkarkow.model.Difficulty;
import com.gkarkow.model.Field;
import com.gkarkow.model.Question;

public class QuestionDAO {

    public static Question getRandom(Field field) {
        try {
            return new Select()
                    .from(Question.class)
                    .where("Field = ?", field.getId())
                    .orderBy("RANDOM()")
                    .executeSingle();
        } catch (Exception ignored) {

        }
        return null;
    }

    public static Question getRandom(Field field, Difficulty difficulty) {
        try {
            return new Select()
                    .from(Question.class)
                    .where("Field = ? AND Difficulty = ?", field.getId(), difficulty.getId())
                    .orderBy("RANDOM()")
                    .executeSingle();
        } catch (Exception ignored) {

        }
        return null;
    }
}
