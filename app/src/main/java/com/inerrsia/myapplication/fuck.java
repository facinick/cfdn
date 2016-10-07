package com.inerrsia.myapplication;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class fuck extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "json";
    public static final String X = "codx";
    public static final String Y = "cody";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuck);
        ArrayList<String> wwww = new ArrayList<>();
        ArrayList<String> xxxx = new ArrayList<>();

        Intent intent = getIntent();
        double x = Double.parseDouble(intent.getStringExtra(X));
        x = x*2988/1440;


        double y = Double.parseDouble(intent.getStringExtra(Y));
        y = y*5312/2560;
        try {

            JSONObject root = new JSONObject(intent.getStringExtra(EXTRA_MESSAGE));
            JSONArray regions = root.getJSONArray("regions");
            for(int i=0; i<regions.length(); i++)
            {
                JSONObject bbregions = regions.getJSONObject(i);
                JSONArray lines = bbregions.getJSONArray("lines");
                for(int j=0; j<lines.length(); j++)
                {
                JSONObject bblines = lines.getJSONObject(j);
                JSONArray words = bblines.getJSONArray("words");
                    for(int l=0; l<words.length(); l++)
                    {
                        JSONObject bbcoord = words.getJSONObject(l);
                      JSONObject bbtext = words.getJSONObject(l);
                        String coord = bbcoord.getString("boundingBox");
                      String name = bbtext.getString("text");
                      xxxx.add(coord);
                      wwww.add(name);

                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        String[] splits = new String[4];
        ArrayList<Integer> x1 = new ArrayList<>();
        ArrayList<Integer> y1 = new ArrayList<>();
        ArrayList<Integer> x2 = new ArrayList<>();
        ArrayList<Integer> y2 = new ArrayList<>();

/////////xxxx is coordonates

        ArrayList<String> xxx = new ArrayList<>();

        String temp;

        for(int i=0;i<xxxx.size(); i++)
        {
            temp = xxxx.get(i);
            splits = temp.split(",");
            x1.add(Integer.parseInt(splits[0]));
            y1.add(Integer.parseInt(splits[1]));
            x2.add(Integer.parseInt(splits[0])+Integer.parseInt(splits[2]));
            y2.add(Integer.parseInt(splits[1])+Integer.parseInt(splits[3]));
            if( x > x1.get(i)-200 && x < x2.get(i)+200 && y > y1.get(i)-200 && y <y2.get(i)+200 )
            {
                xxx.add(wwww.get(i));
            }
        }

        Intent popup = new Intent(getBaseContext(),XYZZ.class);


        popup.putExtra("bhejo", xxx);

        DisplayMetrics m2 = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(m2);

        int ww = m2.widthPixels;
        int hh = m2.heightPixels;

        getWindow().setLayout((int) (ww*.8) , (int) (hh*.8));


/////////////////////?????? WE HAVE WORDS AND COORDINATES///////////////////////////////////////////




            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);

            List<String> listDataHeader;
            HashMap<String, List<String>> listDataChild;
            ExpandableListView expListView = (ExpandableListView) findViewById(R.id.expandableListView);
            expListView.setVisibility(View.VISIBLE);
            listDataHeader = new ArrayList<String>();
            listDataChild = new HashMap<String, List<String>>();
            ExpandableListAdapter1 listAdapter = new ExpandableListAdapter1(this, listDataHeader, listDataChild);
            int i = 1;
            databaseAccess.open();
            for (String m : xxx) {
                listDataHeader.add(m);
                List<String> m1 = new ArrayList<>();
                m1.add("Meaning: " + databaseAccess.getmean(m));
                m1.add("Synonyms: " + databaseAccess.getsyn(m));
                try {
                    listDataChild.put(listDataHeader.get(i), m1);
                    i++;
                } catch (IndexOutOfBoundsException e) {
                }
            }
            databaseAccess.close();
            // setting list adapter
            expListView.setAdapter(listAdapter);

            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });
        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub

                return false;
            }
        });
    }










//
//        ListView FUCK = (ListView) findViewById(R.id.FUCK);
//        ArrayAdapter<String> adp = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, wwww);
//        FUCK.setAdapter(adp);
//    }
}



