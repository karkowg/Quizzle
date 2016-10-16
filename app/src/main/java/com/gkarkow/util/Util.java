package com.gkarkow.util;

import android.widget.EditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gkarkow.model.Level;
import com.gkarkow.model.Round;

public class Util {

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static boolean isEditTextInputChecked(EditText et, String message) {
        if (et.getText().toString().length() == 0) {
            et.setError(message);
            et.requestFocus();
            return false;
        }
        return true;
    }

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static ArrayList<String> quotaDifficulties(Level level, Round round) {
        ArrayList<String> quota = new ArrayList<String>();
        Integer numQuestions = round.getNumQuestions();
        Integer quotaEasy, quotaMedium, quotaHard;
        quotaEasy = Math.round(level.getQuotaEasy() * numQuestions);
        quotaMedium = Math.round(level.getQuotaMedium()*numQuestions);
        quotaHard = Math.round(level.getQuotaHard()*numQuestions);
        /**
         * TODO:
         * Verificar se a soma das quotas é igual a numQuestions
         */
        for (int i=0; i<quotaEasy; i++) quota.add("Easy");
        for (int i=0; i<quotaMedium; i++) quota.add("Medium");
        for (int i=0; i<quotaHard; i++) quota.add("Hard");
        Collections.shuffle(quota);
        return quota;
    }
}
