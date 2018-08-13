package com.knanilkumar.graphics;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import se.simbio.encryption.Encryption;

public class MainActivity extends AppCompatActivity {
GridLayout gridLayout;
    private byte[] byt=new byte[  ]  {-99, 61, 27, -48, 45, 103, -11, 36, 3, 110, -81, -36, -24, 90, 125, -23} ;
    private String key="'BOUm.kY&^FGK->*?j3U{vbmTPP11K|QyQ`rkQ_Jd47&V#413Q?.7E$ ;~{b_&%";

    private String salt="'b/>CCq}S8r`kBESXG({]B:gu1#[|,uIK?HKH?{wq -dZD7&[0yeWO+ nKA>v@";
    String JsonURL = "https://engineering-grapics.firebaseapp.com/app/secure/beta/intro.txt";
    String data = "";
    // Defining the Volley request queue that handles the URL request concurrently
    RequestQueue requestQueue;

    private ProgressDialog PD;
    private Intent intent = new Intent();
    private SharedPreferences sp;
    private AlertDialog.Builder dialog;

    private String ver;

    private String upurl;

    private String baurl;

    private String churl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridLayout=(GridLayout)findViewById(R.id.mainGrid);

        initialize();
        initializeLogic();

        PD = new ProgressDialog(this);
        PD.setMessage("Loading.....");
        PD.setCancelable(false);
        PD.show();

        requestQueue = Volley.newRequestQueue(this);

        // Casts results into the TextView found within the main layout XML with id jsonData
        // results = (TextView) findViewById(R.id.jsonData);

        // Creating the JsonObjectRequest class called obreq, passing required parameters:
        //GET is used to fetch data from the server, JsonURL is the URL to be fetched from.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JsonURL,
                new Response.Listener<String>() {


                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(String enresponse) {
                        try {Encryption encryption = Encryption . getDefault( key , salt , byt);
                            String decrypted = encryption . decryptOrNull(enresponse);
                            JSONObject response = new JSONObject(decrypted);

                            JSONObject obj = response.getJSONObject("colorObject");
                            // Retrieves the string labeled "colorName" and "description" from
                            //the response JSON Object
                            //and converts them into javascript objects
                            ver = obj.getString("ver");
                            upurl = obj.getString("upurl");
                            baurl = obj.getString("baurl");
                            churl = obj.getString("churl");

                            // Adds strings from object to the "data" string

                            // Adds the data string to the TextView "results"
                            //results.setText(data);

                            PD.dismiss();
                            if(!ver.equals(""+1)){
                                dialog.setTitle("Update");
                                dialog.setMessage("Please update app to continue using..");
                                dialog.setCancelable(false);

                                dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface _dialog, int _which) {
                                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(upurl));
                                        startActivity(browserIntent);
                                        dialog.create().show();
                                    }
                                });
                                dialog.create().show();



                            }
                        }
                        // Try and catch are included to handle any errors due to JSON
                        catch (JSONException e) {
                            // If an error occurs, this prints the error to the log
                            PD.dismiss();
                            e.printStackTrace();
                            dialog.setTitle("Something Went Wrong");
                            dialog.setMessage("Please try again..!!..After some time..");
                            dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface _dialog, int _which) {
                                    finish();
                                }
                            });
                            dialog.create().show();

                        }
                    }
                },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                        PD.dismiss();
                        //showMessage(error.toString());
                        dialog.setTitle("Check Connection");
                        dialog.setMessage("An active internet connection required...\nPlease try again..!!");
                        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface _dialog, int _which) {


                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(upurl));
                                startActivity(browserIntent);
                            }
                        });
                        dialog.create().show();
                    }
                }
        );
        // Adds the JSON object request "obreq" to the request queue
        requestQueue.add(stringRequest);

    }
    private void initialize() {

        sp = getSharedPreferences("opendata", Activity.MODE_PRIVATE);
        dialog = new AlertDialog.Builder(this);




    }
private void setClick(GridLayout gridLayout){
        for(int i=0;i<gridLayout.getChildCount();i++){

            CardView cardview=(CardView)gridLayout.getChildAt(i);
            final int finalI=i;
            cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch(finalI) {
                        case 0:
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setClass(getApplicationContext(), ChapterActivity.class);
                            intent.putExtra("url",baurl);
                            startActivity(intent);
                            break;
                        case 1:
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setClass(getApplicationContext(), ChapterActivity.class);
                            intent.putExtra("url",churl);
                            startActivity(intent);
                            break;

                        case 2:
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setClass(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                            break;
                        case 3:
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setClass(getApplicationContext(), Feedback.class);
                            startActivity(intent);
                            break;
                        case 4:
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setClass(getApplicationContext(), About.class);
                            startActivity(intent);
                            break;
                        case 5:
                            String shareBody = "Hi \nHave a look at this App...'Engineering Graphics by KN Anilkumar'\n Will surely helpful to you.";
                            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                            sharingIntent.setType("text/plain");
                            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Engineering Graphics by KN Anilkumar");
                            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                            startActivity(sharingIntent);

                            break;
                        default:
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setClass(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                    }



                }
            });


        }



}
    public void showMessage(String _s) {
        Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
    }
    private void initializeLogic() {
        if (sp.getString("alert", "").equals("")) {
            dialog.setTitle("Welcome");
            dialog.setMessage("This app is supplimentry for the book - 'Engineering Graphics by KN Anilkumar'\nBest experience if you have the book.");
            dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface _dialog, int _which) {
                    sp.edit().putString("alert", "itsdoneðŸ˜Š").commit();
                }
            });
            dialog.create().show();
        }
        setClick(gridLayout);
    }




    }
