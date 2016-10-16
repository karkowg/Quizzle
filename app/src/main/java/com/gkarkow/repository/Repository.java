package com.gkarkow.repository;

import com.activeandroid.ActiveAndroid;

import com.gkarkow.model.Alternative;
import com.gkarkow.model.Difficulty;
import com.gkarkow.model.Field;
import com.gkarkow.model.Game;
import com.gkarkow.model.Level;
import com.gkarkow.model.Person;
import com.gkarkow.model.Question;
import com.gkarkow.model.Round;

public class Repository {

    private static Alternative alternative;
    private static Difficulty difficulty;
    private static Field field;
    private static Game game;
    private static Level level;
    private static Person person;
    private static Question question;
    private static Round round;

    public static void populateDB() {
        try {
            person = new Person();
            person.setName("Admin");
            person.setEmail("admin@mail.com");
            person.setPassword("admin");
            person.save();

            //

            difficulty = new Difficulty();
            difficulty.setName("Easy");
            difficulty.setNumber(1);
            difficulty.setTimeout(20);
            difficulty.setWeight(1);
            difficulty.save();

            difficulty = new Difficulty();
            difficulty.setName("Medium");
            difficulty.setNumber(2);
            difficulty.setTimeout(25);
            difficulty.setWeight(2);
            difficulty.save();

            difficulty = new Difficulty();
            difficulty.setName("Hard");
            difficulty.setNumber(3);
            difficulty.setTimeout(30);
            difficulty.setWeight(4);
            difficulty.save();

            //

            ActiveAndroid.beginTransaction();
            try {
                /**
                 * Fields
                 * 4x
                 */
                for (int i=0; i<4; i++) {
                    field = new Field();
                    field.setName(fieldName(i));
                    field.save();

                    game = new Game();
                    game.setField(field);
                    game.setNumLevels(3);
                    game.setCurrentLevel(1);
                    game.save();

                    /**
                     * Difficulties
                     * 3x (Easy, Medium, Hard)
                     */
                    for (int j=0; j<3; j++) {
                        /**
                         * Questions
                         * 20x
                         */
                        for (int k=1; k<21; k++) {
                            question = new Question();
                            question.setDescription("Questão de " +field.getName()+ " " +k);
                            question.setDifficulty(DifficultyDAO.getDifficultyByName(difficultyName(j)));
                            question.setField(field);

                            alternative = new Alternative();
                            alternative.setDescription("Verdadeiro");
                            alternative.setCorrect(true);
                            alternative.save();
                            question.setA(alternative);

                            alternative = new Alternative();
                            alternative.setDescription("Falso");
                            alternative.save();
                            question.setB(alternative);

                            alternative = new Alternative();
                            alternative.setDescription("Falso");
                            alternative.save();
                            question.setC(alternative);

                            alternative = new Alternative();
                            alternative.setDescription("Falso");
                            alternative.save();
                            question.setD(alternative);

                            question.save();
                        }
                    }

                    level = new Level();
                    level.setName("Iniciante");
                    level.setNumRounds(2);
                    level.setCurrentRound(1);
                    level.setQuotaEasy(1f);
                    level.setGame(game);
                    level.save();

                    for (int j=1; j<=level.getNumRounds(); j++) {
                        round = new Round();
                        round.setNumber(j);
                        round.setNumQuestions(4);
                        round.setLevel(level);
                        round.save();
                    }

                    level = new Level();
                    level.setName("Intermediário");
                    level.setNumRounds(2);
                    level.setCurrentRound(1);
                    level.setQuotaEasy(0.5f);
                    level.setQuotaMedium(0.5f);
                    level.setGame(game);
                    level.save();

                    for (int j=1; j<=level.getNumRounds(); j++) {
                        round = new Round();
                        round.setNumber(j);
                        round.setNumQuestions(4);
                        round.setLevel(level);
                        round.save();
                    }

                    level = new Level();
                    level.setName("Avançado");
                    level.setNumRounds(2);
                    level.setCurrentRound(1);
                    level.setQuotaMedium(0.5f);
                    level.setQuotaHard(0.5f);
                    level.setGame(game);
                    level.save();

                    for (int j=1; j<=level.getNumRounds(); j++) {
                        round = new Round();
                        round.setNumber(j);
                        round.setNumQuestions(4);
                        round.setLevel(level);
                        round.save();
                    }
                }
                ActiveAndroid.setTransactionSuccessful();
            }
            finally {
                ActiveAndroid.endTransaction();
            }
        } catch (Exception ignored) {

        }
    }

    private static String fieldName(int i) {
        switch (i) {
            case 0: return "Biologia";
            case 1: return "Geografia";
            case 2: return "História";
            case 3: return "Português";
            default: return "Campo de estudo " +i;
        }
    }

    private static String difficultyName(int i) {
        switch (i) {
            case 0: return "Easy";
            case 1: return "Medium";
            case 2: return "Hard";
        }
        return "";
    }
}
