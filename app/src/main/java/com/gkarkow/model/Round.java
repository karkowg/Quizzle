package com.gkarkow.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "Rounds")
public class Round extends Model {

    @Column
    private Integer number;

    @Column
    private Integer numQuestions;

    @Column
    private Integer hitsEasy;

    @Column
    private Integer hitsMedium;

    @Column
    private Integer hitsHard;

    @Column
    private Level level;

    public Round() {
        hitsEasy = 0;
        hitsMedium = 0;
        hitsHard = 0;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getNumQuestions() {
        return numQuestions;
    }

    public void setNumQuestions(Integer numQuestions) {
        this.numQuestions = numQuestions;
    }

    public Integer getHitsEasy() {
        return hitsEasy;
    }

    public void setHitsEasy(Integer hitsEasy) {
        this.hitsEasy = hitsEasy;
    }

    public Integer getHitsMedium() {
        return hitsMedium;
    }

    public void setHitsMedium(Integer hitsMedium) {
        this.hitsMedium = hitsMedium;
    }

    public Integer getHitsHard() {
        return hitsHard;
    }

    public void setHitsHard(Integer hitsHard) {
        this.hitsHard = hitsHard;
    }

    public Integer getTotalHits() {
        return hitsEasy + hitsMedium + hitsHard;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Float calcAverage() {
        return getTotalHits() / numQuestions.floatValue() * 10;
    }

    /**
     * Média ponderada
     */
    public Float easyAverage() {
        if (level.getQuotaEasy() == 0f) return 0f;
        return hitsEasy / (numQuestions.floatValue() * level.getQuotaEasy()) *10;
    }

    public Float mediumAverage() {
        if (level.getQuotaMedium() == 0f) return 0f;
        return hitsMedium / (numQuestions.floatValue() * level.getQuotaMedium()) *10;
    }

    public Float hardAverage() {
        if (level.getQuotaHard() == 0f) return 0f;
        return hitsHard / (numQuestions.floatValue() * level.getQuotaHard()) *10;
    }
}
