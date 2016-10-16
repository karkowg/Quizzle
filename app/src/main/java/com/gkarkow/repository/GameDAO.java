package com.gkarkow.repository;

import com.activeandroid.query.Select;

import com.gkarkow.model.Field;
import com.gkarkow.model.Game;
import com.gkarkow.model.Level;
import com.gkarkow.model.Person;
import com.gkarkow.model.Round;

public class GameDAO {

    public static Game getGame(Field field) {
        try {
            return new Select()
                    .from(Game.class)
                    .where("Field = ?", field.getId())
                    .executeSingle();
        } catch (Exception ignored) {

        }
        return null;
    }

    public static Game getGame(Field field, Person person) {
        try {
            return new Select()
                    .from(Game.class)
                    .where("Field = ? AND Person = ?", field.getId(), person.getId())
                    .executeSingle();
        } catch (Exception ignored) {

        }
        return null;
    }

    public static Game create(Field field, Person user) {
        Game game, model;
        model = getGame(field);
        game = new Game();
        game.setPerson(user);
        game.setField(field);
        game.setNumLevels(model.getNumLevels());
        game.setCurrentLevel(model.getCurrentLevel());
        game.save();
        for (Level l : model.levels()) {
            Level level = new Level();
            level.setName(l.getName());
            level.setNumRounds(l.getNumRounds());
            level.setCurrentRound(l.getCurrentRound());
            level.setQuotaEasy(l.getQuotaEasy());
            level.setQuotaMedium(l.getQuotaMedium());
            level.setQuotaHard(l.getQuotaHard());
            level.setGame(game);
            level.save();
            for (Round r : l.rounds()) {
                Round round = new Round();
                round.setNumber(r.getNumber());
                round.setNumQuestions(r.getNumQuestions());
                round.setLevel(level);
                round.save();
            }
        }
        return game;
    }
}
