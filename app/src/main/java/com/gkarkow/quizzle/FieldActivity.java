package com.gkarkow.quizzle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gkarkow.model.Field;
import com.gkarkow.repository.FieldDAO;
import com.gkarkow.repository.GameDAO;
import com.gkarkow.session.Session;

public class FieldActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field);

        initGUI();
    }

    private void initGUI() {
        ListView lvFields = (ListView) findViewById(R.id.lvFields);
        ArrayList<Field> fields = new ArrayList<Field>(FieldDAO.getAll());
        final ArrayList<String> items = new ArrayList<String>();
        for (Field f : fields) {
            items.add(f.getName());
        }
        final StableArrayAdapter adapter =
                new StableArrayAdapter(this, android.R.layout.simple_list_item_1, items);
        lvFields.setAdapter(adapter);

        lvFields.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                chosenField(item);
            }
        });
    }

    public void chosenField(String field) {
        Session.field = FieldDAO.getFieldByName(field);

        initGame();

        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    private void initGame() {
        Session.game = GameDAO.getGame(Session.field, Session.user);

        if (Session.game == null) {
            Session.game = GameDAO.create(Session.field, Session.user);
        }
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {
        Map<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId, List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
