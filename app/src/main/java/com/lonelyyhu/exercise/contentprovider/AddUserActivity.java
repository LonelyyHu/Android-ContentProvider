package com.lonelyyhu.exercise.contentprovider;

import android.content.ContentValues;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.lonelyyhu.exercise.contentprovider.MyContract.UserColumns;

public class AddUserActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etAge;
    private EditText etPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        etName = findViewById(R.id.et_name);
        etAge = findViewById(R.id.et_age);
        etPhone = findViewById(R.id.et_phone);



    }

    public void onSaveClick(View view) {

        ContentValues values = new ContentValues();
        values.put(UserColumns.NAME, etName.getText().toString());
        values.put(UserColumns.AGE, etAge.getText().toString());
        values.put(UserColumns.PHONE, etPhone.getText().toString());

        Uri uri = getContentResolver().insert(MyContract.USER_CONTENT_URI, values);

        if (uri != null) {
//            Toast.makeText(this, "Save Success!!", Toast.LENGTH_SHORT).show();
            Snackbar.make(findViewById(android.R.id.content), "Save Success",Snackbar.LENGTH_SHORT).show();
        }

        etName.getText().clear();
        etAge.getText().clear();
        etPhone.getText().clear();
    }
}
