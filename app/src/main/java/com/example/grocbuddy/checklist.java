package com.example.grocbuddy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;
import java.net.*;

public class checklist extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);

        Intent intent = getIntent();
        String itemToPopulate = intent.getExtras().getString("checklistItem");
        System.out.println("ItemToPopulate:" + itemToPopulate);
        final List<String> items = new ArrayList<String>();
        items.add(itemToPopulate);

        final List<String> stores = new ArrayList<String>();
        stores.add("Walmart");
        stores.add("Cosco");
        stores.add("Tom Thumb");

        TextView checkListText = (TextView) findViewById(R.id.checklisttext);
        checkListText.setText(itemToPopulate);

        Button btnSearch = (Button) findViewById(R.id.btnDisplay);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Runnable r = new SocketConn(stores,items);
                new Thread(r).start();
            }
        });

    }
    public class SocketConn implements Runnable{
        List<String> stores;
        List<String> items;
        public SocketConn(List<String> storeList,List<String> itemList){
            stores = storeList;
            items = itemList;

        }
        public void run(){
            StringBuilder storeString = new StringBuilder();
            for(String a:stores) {
                storeString.append(a);
                storeString.append(",");
            }
            StringBuilder itemString = new StringBuilder();
            for(String i:items) {
                storeString.append(i);
                storeString.append(",");
            }
            try {
                System.out.println("Search clicked");
                Socket user_soc = new Socket("10.21.73.112",17999);
                System.out.println("Contacting server");
                InputStream in = user_soc.getInputStream();
                OutputStream out = user_soc.getOutputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                PrintWriter pw = new PrintWriter(out,true);

                String line = "";
                line = br.readLine();

                if(line.equals("0")){
                    pw.println("REQUESTER");
                }
                line = br.readLine();
                if(line.equals("1")){
                    pw.println("userA");
                }
                if(line.equals("2")){
                    pw.println(itemString.toString());
                }
                line = br.readLine();
                if(line.equals("3")){
                    pw.println("100");
                }
                if(line.equals("4")){
                    pw.println(storeString.toString());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
