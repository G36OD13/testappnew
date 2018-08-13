package com.knanilkumar.graphics;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class About extends AppCompatActivity {
    private TextView textview6;

    private Intent mail = new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        textview6 = (TextView) findViewById(R.id.textview6);

        textview6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mail.setAction(Intent.ACTION_VIEW);
                mail.setData(Uri.parse("mailto:aknrit@yahoo.com"));
                startActivity(mail);
            }
        });
    }
}
