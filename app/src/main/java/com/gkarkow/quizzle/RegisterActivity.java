package com.gkarkow.quizzle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.gkarkow.model.Person;
import com.gkarkow.repository.PersonDAO;
import com.gkarkow.util.Util;

public class RegisterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void onClick(View view) {
        EditText etN = (EditText) findViewById(R.id.etNameR);
        EditText etE = (EditText) findViewById(R.id.etEmailR);
        EditText etP = (EditText) findViewById(R.id.etPasswordR);
        if (Util.isEditTextInputChecked(etN, getResources().getString(R.string.msg_error_name)) &&
                Util.isEditTextInputChecked(etE, getResources().getString(R.string.msg_error_email)) &&
                Util.isEditTextInputChecked(etP, getResources().getString(R.string.msg_error_pass))) {
            if (userExists(etE.getText().toString())) {
                etE.setError(getResources().getString(R.string.msg_error_email_exists));
                etE.requestFocus();
                return;
            }
            if (!Util.isValidEmail(etE.getText().toString())) {
                etE.setError(getResources().getString(R.string.msg_error_email_invalid));
                etE.requestFocus();
                return;
            }

            Person person = new Person();
            person.setName(etN.getText().toString());
            person.setEmail(etE.getText().toString());
            person.setPassword(etP.getText().toString());
            person.save();

            final AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage(getResources().getString(R.string.msg_success_sign_up));
            alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                    signIn();
                }
            });
            alert.show();
        }
    }

    private void signIn() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        finish();
    }

    private boolean userExists(String email) {
        return PersonDAO.getPersonByEmail(email) != null;
    }
}
