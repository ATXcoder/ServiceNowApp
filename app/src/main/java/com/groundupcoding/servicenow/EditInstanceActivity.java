package com.groundupcoding.servicenow;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Thomas on 2/12/2015.
 */
public class EditInstanceActivity extends ActionBarActivity {
    EditText instanceName;
    EditText instanceURL;
    Button saveButton;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_instance);

        instanceName = (EditText)findViewById(R.id.instanceName);
        instanceURL = (EditText)findViewById(R.id.instanceURL);
        saveButton = (Button)findViewById(R.id.saveButton);

        Bundle bundle = getIntent().getExtras();

        id = Long.valueOf(bundle.getString("id"));
        DataBaseHelper db = new DataBaseHelper(getApplicationContext());
        Cursor cursor = db.getInstance(id);
        cursor.moveToFirst();
        instanceName.setText(cursor.getString(1));
        instanceURL.setText(cursor.getString(2));

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelper db = new DataBaseHelper(getApplicationContext());
                db.updateInstance(instanceName.getText().toString(),instanceURL.getText().toString(),id);
            }
        });

    }
}
