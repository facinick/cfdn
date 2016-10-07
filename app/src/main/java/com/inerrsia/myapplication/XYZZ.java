package com.inerrsia.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ExpandableListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class XYZZ extends AppCompatActivity {

    public static final String X = "bhejo";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xyzz);
        Intent intent = getIntent();
        ArrayList<String> list = (ArrayList) intent.getSerializableExtra(X);


        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);

        List<String> listDataHeader;
        HashMap<String, List<String>> listDataChild;
        ExpandableListView expaListView = (ExpandableListView) findViewById(R.id.expandableListView2);
        expaListView.setVisibility(View.VISIBLE);
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        ExpandableListAdapter1 listAdapter = new ExpandableListAdapter1(this, listDataHeader, listDataChild);
        int i = 1;

        databaseAccess.open();
        for (String m : list) {
            listDataHeader.add(m);
            List<String> m1 = new ArrayList<>();
            String mean = databaseAccess.getmean(m);
            String syni = databaseAccess.getsyn(m);
            if (mean != "null") {
                if (syni.length() < 2){
                    syni = "Not found";
                }
                m1.add("Meaning: " + mean);
                m1.add("Synonyms: " + syni);
                try {
                    listDataChild.put(listDataHeader.get(i), m1);
                    i++;
                } catch (IndexOutOfBoundsException e) {
                }
            }
        }
        databaseAccess.close();
        // setting list adapter
        expaListView.setAdapter(listAdapter);

        Intent popup = new Intent(getBaseContext(),XYZZ.class);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // Listview Group click listener
        expaListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                return false;
            }
        });

        // Listview Group expanded listener
        expaListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        // Listview Group collasped listener
        expaListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {

            }
        });
        // Listview on child click listener
        expaListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub

                return false;
            }
        });
    }
}
