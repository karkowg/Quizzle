package com.gkarkow.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

@Table(name = "Games")
public class Game extends Model {

    @Column
    private Integer numLevels;

    @Column
    private Integer currentLevel;

    @Column
    private Float percentHitEasy;

    @Column
    private Float percentHitMedium;

    @Column
    private Float percentHitHard;

    @Column
    private Float cumulative;

    @Column
    private Boolean finished;

    @Column(onDelete = Column.ForeignKeyAction.SET_NULL, onUpdate = Column.ForeignKeyAction.SET_NULL)
    private Field field;

    @Column(onDelete = Column.ForeignKeyAction.SET_NULL, onUpdate = Column.ForeignKeyAction.SET_NULL)
    private Person person;

    public Game() {
        person = null;
        percentHitEasy = null;
        percentHitMedium = null;
        percentHitHard = null;
        cumulative = null;
        finished = false;
    }

    public Integer getNumLevels() {
        return numLevels;
    }

    public void setNumLevels(Integer numLevels) {
        this.numLevels = numLevels;
    }

    public Integer getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Integer currentLevel) {
        this.currentLevel = currentLevel;
    }

    public Float getPercentHitEasy() {
        return calcPercentEasy();//percentHitEasy;
    }

    public void setPercentHitEasy(Float percentHitEasy) {
        this.percentHitEasy = percentHitEasy;
    }

    public Float getPercentHitMedium() {
        return calcPercentMedium();//percentHitMedium;
    }

    public void setPercentHitMedium(Float percentHitMedium) {
        this.percentHitMedium = percentHitMedium;
    }

    public Float getPercentHitHard() {
        return calcPercentHard();//percentHitHard;
    }

    public void setPercentHitHard(Float percentHitHard) {
        this.percentHitHard = percentHitHard;
    }

    public Float getCumulative() {
        return calcCumulative();//cumulative;
    }

    public void setCumulative(Float cumulative) {
        this.cumulative = cumulative;
    }

    public Boolean isFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<Level> levels() {
        return getMany(Level.class, "Game");
    }

    public Float calcCumulative() {
        Float cumulative = 0f;
        Float div = 0f;
        for (int i=0; i<currentLevel; i++) {
            Level level = levels().get(i);
            if (level.isFinished()) {
                div++;
                cumulative += level.getAverage();
            }
        }
        return div == 0f? 0f : cumulative/div;
    }

    public Float calcPercentEasy() {
        Float percent = 0f;
        Float div = 0f;
        for (int i=0; i<currentLevel; i++) {
            Level level = levels().get(i);
            if (level.isFinished() && level.getQuotaEasy()>0) {
                div++;
                percent += level.calcPercentEasy();
            }
        }
        return div == 0f? 0f : percent/div;
    }

    public Float calcPercentMedium() {
        Float percent = 0f;
        Float div = 0f;
        for (int i=0; i<currentLevel; i++) {
            Level level = levels().get(i);
            if (level.isFinished() && level.getQuotaMedium()>0) {
                div++;
                percent += level.calcPercentMedium();
            }
        }
        return div == 0f? 0f : percent/div;
    }

    public Float calcPercentHard() {
        Float percent = 0f;
        Float div = 0f;
        for (int i=0; i<currentLevel; i++) {
            Level level = levels().get(i);
            if (level.isFinished() && level.getQuotaHard()>0) {
                div++;
                percent += level.calcPercentHard();
            }
        }
        return div == 0f? 0f : percent/div;
    }

    public String lastFinishedLevelName() {
        String name = null;
        for (Level l : levels()) {
            if (l.isFinished()) name = l.getName();
        }
        return name == null? "-" : name;
    }


}
