package com.knanilkumar.graphics;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class Feedback extends AppCompatActivity {
    private String name = "";
    private String contact = "";
    private String feedback = "";
    private Button button1;
    private TextView textview4;
    private TextView textview2;
    private EditText edittext1;
    private TextView textview3;
    private EditText edittext2;
    private TextView textview5;
    private EditText edittext3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initialize();
        initializeLogic();
    }
    private void initialize() {
        textview4 = (TextView) findViewById(R.id.textview4);
        textview2 = (TextView) findViewById(R.id.textview2);
        edittext1 = (EditText) findViewById(R.id.edittext1);
        textview3 = (TextView) findViewById(R.id.textview3);
        edittext2 = (EditText) findViewById(R.id.edittext2);
        textview5 = (TextView) findViewById(R.id.textview5);
        edittext3 = (EditText) findViewById(R.id.edittext3);

        button1 = (Button) findViewById(R.id.button1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = edittext1.getText().toString();
                contact = edittext2.getText().toString();
                feedback = edittext3.getText().toString();

                String mob= Build.MANUFACTURER.concat(" ".concat(Build.MODEL))+"   "+Build.VERSION.RELEASE;
                String aid = null;
                try{
                    android.content.pm.PackageInfo pInfo =Feedback.this.getPackageManager().getPackageInfo(getPackageName(), 0);
                    aid=pInfo.versionName;
                } catch (android.content.pm.PackageManager.NameNotFoundException e){

                }

                String fid="";
                String key="";
                send(name,contact,feedback,mob,aid,fid,key);
            }
        });
    }
    private void initializeLogic() {
        name = edittext1.getText().toString();
        contact = edittext2.getText().toString();
        feedback = edittext3.getText().toString();
    }

    private void send(String name, String cls, String bra, String mob, String aid, String fid, String key)
    {
        // TODO: Implement this method

        class RegisterUser extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            Update ruc = new Update( );



            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Feedback.this, "Please Wait..",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if (s != null && !s.isEmpty()) {

                    showMessage("Submitted");

                    finish();
                }



            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String,String>();
                data.put("entry.871671503", params[0]);
                data.put("entry.951769419",params[1]);
                data.put("entry.281604020",params[2]);
                data.put("entry.1432093122", params[3]);
                data.put("entry.449612518",params[4]);
                data.put("entry.1325897551",params[5]);
                data.put("entry.1105207150", params[6]);





                String Url = "https://docs.google.com/forms/d/e/1FAIpQLSeS4M8-IG4oKNlXM35ix6aXkcSWP5atsE2mZlfUNb1YaeDGlA/formResponse";
                String result = ruc.sendPostRequest(Url,data);
                return  result;


            }



        }

        RegisterUser ru = new RegisterUser();
        ru.execute(name,cls,bra,mob,aid,fid,key);







    }
    public void showMessage(String _s) {
        Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
    }


}
