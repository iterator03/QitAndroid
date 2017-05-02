package com.androidproject.queuehint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;

public class Scan extends AppCompatActivity {
    private ImageView mImageView;
    Button login;
    EditText e1, e2, e3;
    String username, number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_scan);

        Button login = (Button) findViewById(R.id.login);
        e1 = (EditText) findViewById(R.id.name);
        e3 = (EditText) findViewById(R.id.number);



        /*GETTING VALUES FROM ALL THE FIELDS*/


       login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               username = e1.getText().toString();
               number = e3.getText().toString();
               /*INPUT VALIDATION*/
               if (username.equals("") || number.equals("")) {
        /*IF FILLS ARE NOT FILLED*/
                   Toast.makeText(Scan.this, "Please fill out all the fields", LENGTH_LONG).show();
               } else {
                   //  mImageView.disabled(false);
        /*ON clicking the image you can scan your code*/
                   Intent j= new Intent(Scan.this,MainActivity.class);
                   j.putExtra("name",username);
                   j.putExtra("number",number);
                   startActivity(j);
               }
           }
       });


    }

    public void imageclick(View view) {
        Intent i = new Intent(Scan.this, MainActivity.class);
        startActivity(i);
    }
}