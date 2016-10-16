package com.gkarkow.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

@Table(name = "Levels")
public class Level extends Model {

    @Column
    private String name;

    @Column
    private Boolean finished;

    @Column
    private Integer numRounds;

    @Column
    private Integer currentRound;

    @Column
    private Float average;

    @Column
    private Float quotaEasy;

    @Column
    private Float quotaMedium;

    @Column
    private Float quotaHard;

    @Column
    private Game game;

    public Level() {
        finished = false;
        average = null;
        quotaEasy = 0f;
        quotaMedium = 0f;
        quotaHard = 0f;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Integer getNumRounds() {
        return numRounds;
    }

    public void setNumRounds(Integer numRounds) {
        this.numRounds = numRounds;
    }

    public Integer getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(Integer currentRound) {
        this.currentRound = currentRound;
    }

    public Float getAverage() {
        return calcWeightedAverage();//average;
    }

    public void setAverage(Float average) {
        this.average = average;
    }

    public Float getQuotaEasy() {
        return quotaEasy;
    }

    public void setQuotaEasy(Float quotaEasy) {
        this.quotaEasy = quotaEasy;
    }

    public Float getQuotaMedium() {
        return quotaMedium;
    }

    public void setQuotaMedium(Float quotaMedium) {
        this.quotaMedium = quotaMedium;
    }

    public Float getQuotaHard() {
        return quotaHard;
    }

    public void setQuotaHard(Float quotaHard) {
        this.quotaHard = quotaHard;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<Round> rounds() {
        return getMany(Round.class, "Level");
    }

    public Float calcAverage() {
        Float average = 0f;
        for (int i=0; i<currentRound; i++) {
            average += rounds().get(i).calcAverage();
        }
        return average/currentRound.floatValue();
    }

    public Float calcPercentEasy() {
        Float percent = 0f;
        for (int i=0; i<currentRound; i++) {
            Round r = rounds().get(i);
            Float quota = quotaEasy;
            Float hits = r.getHitsEasy().floatValue();
            Float numQuestions = r.getNumQuestions().floatValue();
            percent += calcPercent(quota, hits, numQuestions);
        }
        return percent/currentRound.floatValue();
    }

    public Float calcPercentMedium() {
        Float percent = 0f;
        for (int i=0; i<currentRound; i++) {
            Round r = rounds().get(i);
            Float quota = quotaMedium;
            Float hits = r.getHitsMedium().floatValue();
            Float numQuestions = r.getNumQuestions().floatValue();
            percent += calcPercent(quota, hits, numQuestions);
        }
        return percent/currentRound.floatValue();
    }

    public Float calcPercentHard() {
        Float percent = 0f;
        for (int i=0; i<currentRound; i++) {
            Round r = rounds().get(i);
            Float quota = quotaHard;
            Float hits = r.getHitsHard().floatValue();
            Float numQuestions = r.getNumQuestions().floatValue();
            percent += calcPercent(quota, hits, numQuestions);
        }
        return percent/currentRound.floatValue();
    }

    private Float calcPercent(Float quota, Float hits, Float numQuestions) {
        return quota == 0f? 0f : hits / (numQuestions*quota);
    }

    /**
     * Média ponderada
     */
    private Float easyWeight() {
        return quotaEasy == 0f? 0f : 1f;
    }

    private Float mediumWeight() {
        return quotaMedium == 0f? 0f : 2f;
    }

    private Float hardWeight() {
        return quotaHard == 0f? 0f : 4f;
    }

    public Float calcWeightedAverage() {
        Float average = 0f;
        for (int i=0; i<currentRound; i++) {
            Round r = rounds().get(i);
            Float weighted = (r.easyAverage()*easyWeight() + r.mediumAverage()*mediumWeight() + r.hardAverage()*hardWeight())/
                    (easyWeight() + mediumWeight() + hardWeight());
            average += weighted;
        }
        return average/currentRound.floatValue();
    }
}
