package com.androidproject.queuehint;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


public class second_page extends AppCompatActivity {
    private TextView queueId;
    String s1, s2;
    ProgressDialog pd;
    TextView tv1, tv2;
    String strdata;
    int prevcount = 0;
    String queueid;
    String ipaddr = "192.168.43.18";
    String name,number;

    private static final String TAG = second_page.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page);
        tv1 = (TextView) findViewById(R.id.editText1);
        tv2 = (TextView) findViewById(R.id.editText2);

        Intent intent = this.getIntent();
        strdata = intent.getExtras().getString("senddata");
        name = intent.getExtras().getString("name");
        number = intent.getExtras().getString("number");
        GetData get = new GetData();
        get.execute();
    }

    private class GetData extends AsyncTask <Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            String get_req = "http://"+ipaddr+"/getid.php?uid="+strdata+"&name="+ name + "&number="+number;
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(get_req);
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    String error = jsonObj.getString("error");
                    if(error.equals("0")){
                        queueid = jsonObj.getString("queueid");
                        if(prevcount == 0)
                            prevcount = jsonObj.getInt("prevcount");
                        //Saveing in the Pevious Strings the Two Variables

                    }else{
                        Toast.makeText(second_page.this,"Error Occured",Toast.LENGTH_SHORT).show();
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            tv1.setText(queueid);
            tv2.setText("People Before you: " + prevcount);

            tv1.setEnabled(false);
            tv2.setEnabled(false);

        }
    }

}
