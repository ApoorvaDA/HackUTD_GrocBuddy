package com.example.grocbuddy;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import java.net.*;
import java.io.*;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import android.util.*;
import android.widget.*;
import java.util.*;


public class RequestorActivity extends AppCompatActivity {

public static boolean flag = false;
public static String[] array;
    boolean flag1 = false;
    public void setflag1(boolean g)
    {
        flag1 = g;
    }
    public void setflag(boolean g)
    {
        flag = g;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requestor);
        Button addItem = (Button)findViewById(R.id.addItem);
        final LinearLayout reqRelativeLayout = (LinearLayout)findViewById(R.id.LinearLayout);

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText text = new EditText(getApplicationContext());
                ActionBar.LayoutParams layout = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
                layout.setMargins(10, 10, 10, 10);
                text.setLayoutParams(layout);

                text.setText("Enter Item");
                text.setBackgroundColor(0x80808080);
                text.setPaddingRelative(30, 30, 30, 30);
                reqRelativeLayout.addView(text);
                Button search = new Button(getApplicationContext());
                ActionBar.LayoutParams layout1 = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

                search.setLayoutParams(layout1);
                search.setText("Search");
                reqRelativeLayout.addView(search);
                search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setflag1(true);
                        String input = text.getText().toString();
                        System.out.println(input);
                        Runnable cp = new connectionParser(input);
                        new Thread(cp).start();
                     //   while (!flag) ;
                    }

                });
            }

        });
    }

    public class connectionParser implements Runnable{
        String input = "";
        public connectionParser(String input) {
            this.input = input;
        }
        public void run()
        {
            System.out.println(input);
            String URL1 = "http://www.supermarketapi.com/api.asmx/GetGroceries?APIKEY=654dc3dbac&SearchText=" + input;
            System.out.println(URL1);
            ArrayList<String> list = new ArrayList<>();

            InputStream in = null;
            try {
                URL url = new URL(URL1);
                URLConnection urlConn = url.openConnection();
                HttpURLConnection httpConn = (HttpURLConnection) urlConn;
                httpConn.setAllowUserInteraction(false);
                httpConn.setInstanceFollowRedirects(true);
                httpConn.setRequestMethod("GET");
                httpConn.connect();
                int resCode = httpConn.getResponseCode();

                if (resCode == HttpURLConnection.HTTP_OK) {
                    in = httpConn.getInputStream();


                    XmlPullParserFactory pullParserFactory;
                    try {
                        pullParserFactory = XmlPullParserFactory.newInstance();
                        XmlPullParser parser = pullParserFactory.newPullParser();

                        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                        parser.setInput(in, null);

                        int event;
                        String text=null;

                        try {
                            event = parser.getEventType();

                            while (event != XmlPullParser.END_DOCUMENT) {
                                String name = parser.getName();

                                switch (event) {
                                    case XmlPullParser.START_TAG:
                                        break;

                                    case XmlPullParser.TEXT:
                                        text = parser.getText();
                                        System.out.println(text);
                                        list.add(text.toString().trim());
                                        break;

                                    case XmlPullParser.END_TAG:
                                        break;
                                }
                                event = parser.next();
                            }
                        }

                        catch (Exception e) {
                            e.printStackTrace();
                        }

                    } catch (XmlPullParserException e) {
                        e.printStackTrace();
                    }

                    String[] arr = new String[list.size()];
                    arr = list.toArray(arr);
                    array = arr;
                    flag = true;
                    setflag(true);
                    Log.i(flag + "", "flag");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if(flag1)
                            {

                                for(int i=0;i<array.length;i++)
                                {
                                    System.out.println(array[i]);
                                }
                                ListAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_listview, array);
                                ListView lv = (ListView) findViewById(R.id.listView);
                                lv.setAdapter(adapter);

                                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Toast.makeText(getApplicationContext(), "Added to checklist", Toast.LENGTH_SHORT).show();
                                        String selectedItem=(String)parent.getItemAtPosition(position);
                                        System.out.println("Selected Item:"+selectedItem);
                                        Intent intent = new Intent(getApplicationContext(),checklist.class);
                                        intent.putExtra("checklistItem",selectedItem);
                                        startActivity(intent);
                                    }
                                });
                            }
                        }
                    });
                }
            }

            catch (MalformedURLException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}