package com.inerrsia.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuck);

        Intent intent = getIntent();
        ArrayList<String> wwww = new ArrayList<>();
        Log.e("XXXXXXXXXXXXXXXXXXX", intent.getStringExtra(EXTRA_MESSAGE));

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
                      JSONObject bbtext = words.getJSONObject(l);
                      String name = bbtext.getString("text");
                      Log.e("got", bbtext.getString("text"));
                      wwww.add(name);

                    }
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);

        List<String> listDataHeader;
        HashMap<String, List<String>> listDataChild;
        ExpandableListView expListView = (ExpandableListView) findViewById(R.id.expandableListView);

        expListView.setVisibility(View.VISIBLE);

        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        ExpandableListAdapter1 listAdapter = new ExpandableListAdapter1(this, listDataHeader, listDataChild);
        int i=1;
        databaseAccess.open();
        for(String m : wwww)
        {

            listDataHeader.add(m);
            List<String> m1 = new ArrayList<>();
            m1.add(databaseAccess.getmean(m));
            m1.add(databaseAccess.getsyn(m));
            try{
            listDataChild.put(listDataHeader.get(i), m1);
            i++;}
            catch( IndexOutOfBoundsException e)
            {

            }

        }
        databaseAccess.close();

        // setting list adapter
        expListView.setAdapter(listAdapter);

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



