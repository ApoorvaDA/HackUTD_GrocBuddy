package com.example.grocbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button requestor_button = (Button) findViewById(R.id.requestor_button);
        Button buyer_button = (Button) findViewById(R.id.buyer_button);

        requestor_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(MainActivity.this, RequestorActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

        buyer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(MainActivity.this, BuyerActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

    }

}
