package com.gkarkow.quizzle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gkarkow.repository.PersonDAO;
import com.gkarkow.repository.Repository;
import com.gkarkow.session.Session;
import com.gkarkow.util.Util;
import com.gkarkow.validation.PersonValidation;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initDB();
    }

    private void initDB() {
        if (Session.first) {
            Repository.populateDB();
            Session.first = false;
        }
    }

    public void doLogin(View view) {
        EditText etE = (EditText) findViewById(R.id.etEmail);
        EditText etP = (EditText) findViewById(R.id.etPassword);

        if (Util.isEditTextInputChecked(etE, getResources().getString(R.string.msg_user)) &&
                Util.isEditTextInputChecked(etP, getResources().getString(R.string.msg_pass))) {
            String email = etE.getText().toString();
            String password = etP.getText().toString();

            if (PersonValidation.validate(email, password)) {
                Session.user = PersonDAO.getPersonByEmail(email);
                welcome();
            } else {
                Toast.makeText(this, getResources().getString(R.string.msg_error_sign_in), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void welcome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
