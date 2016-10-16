package com.gkarkow.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Questions")
public class Question extends Model {

    @Column
    private String description;

    @Column
    private Field field;

    @Column
    private Alternative a;

    @Column
    private Alternative b;

    @Column
    private Alternative c;

    @Column
    private Alternative d;

    @Column
    private Difficulty difficulty;

    public Question() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Alternative getA() {
        return a;
    }

    public void setA(Alternative a) {
        this.a = a;
    }

    public Alternative getB() {
        return b;
    }

    public void setB(Alternative b) {
        this.b = b;
    }

    public Alternative getC() {
        return c;
    }

    public void setC(Alternative c) {
        this.c = c;
    }

    public Alternative getD() {
        return d;
    }

    public void setD(Alternative d) {
        this.d = d;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
}
