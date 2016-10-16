package com.gkarkow.quizzle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.gkarkow.session.Session;

public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initGUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initGUI() {
        TextView tvWelcome = (TextView) findViewById(R.id.tvWelcome);
        tvWelcome.setText(getResources().getString(R.string.hello) + ", " + Session.user.getName());
    }

    public void singlePlayer(View view) {
        Intent intent = new Intent(this, FieldActivity.class);
        startActivity(intent);
    }

    private void logout() {
        Session.game = null;
        Session.user = null;
        Session.field = null;
        Session.level = null;
        Session.round = null;
        Session.question = null;
        Session.currentQuestion = 1;
        Session.difficulties = null;

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

        finish();
    }
}
