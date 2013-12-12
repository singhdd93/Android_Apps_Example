package com.debugrelease.android.listviewwithimagesandtext;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends Activity {

    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Model.LoadModel();
        listView = (ListView) findViewById(R.id.listView);
        String[] ids = new String[Model.Items.size()];
        for (int i= 0; i < ids.length; i++){

            ids[i] = Integer.toString(i+1);
        }

        ItemAdapter adapter = new ItemAdapter(this,R.layout.row, ids);
        listView.setAdapter(adapter);

    }
}
