package com.androidproject.queuehint;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {
    private Button b1;
    String number;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = (Button) findViewById(R.id.scan_btn);
        //b2 = (Button) findViewById(R.id.button);
        final Activity activity = this;
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

    }

    @Override
    protected void onActivityResult(int RequestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(RequestCode, resultCode, data);
        /*check if we are getting an scan code or not*/
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Thanks for visit the app", Toast.LENGTH_LONG).show();

            } else {
                //Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                String s3 = result.getContents();
               /* Intent i = new Intent(this,second_page.class);
                System.out.println(s3);
                i.putExtra("uid",s3);
                this.startActivity(i);*/
                //Create new Intent Object, and specify class
                Intent intent = new Intent();
                intent.setClass(this,second_page.class);

                number = getIntent().getStringExtra("number");
                name = getIntent().getStringExtra("name");
                Log.d("number",number);
                Log.d("name",name);
                //Set your data using putExtra method which take
                //any key and value which we want to send
                intent.putExtra("senddata",s3);
                intent.putExtra("number",number);
                intent.putExtra("name",name);

                //Use startActivity or startActivityForResult for Starting New Activity
                this.startActivity(intent);
            }
        } else

        {
            super.onActivityResult(RequestCode, resultCode, data);

        }
    }



}