package com.gkarkow.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

@Table(name = "Fields")
public class Field extends Model {

    @Column(index = true, unique = true)
    private String name;

    public Field() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Game> games() {
        return getMany(Game.class, "Field");
    }

    public List<Question> questions() {
        return getMany(Question.class, "Field");
    }
}
