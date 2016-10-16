package com.gkarkow.session;

import java.util.ArrayList;

import com.gkarkow.model.Field;
import com.gkarkow.model.Game;
import com.gkarkow.model.Level;
import com.gkarkow.model.Person;
import com.gkarkow.model.Question;
import com.gkarkow.model.Round;

public class Session {

    public static Game game = null;
    public static Person user = null;
    public static Field field = null;
    public static Level level = null;
    public static Round round = null;
    public static Question question = null;
    public static Integer currentQuestion = 1;
    public static ArrayList<String> difficulties = null;
    public static Boolean first = true;

}
