package com.tealium.example;

import android.app.ExpandableListActivity;
import android.os.Bundle;

import com.tealium.example.adapter.ExpandableListAdapter;
import com.tealium.example.helper.TealiumHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControlExpandableListActivity extends ExpandableListActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<Map<String, ?>> groupData = new ArrayList<Map<String, ?>>();
        ArrayList<ArrayList<HashMap<String, String>>> childData = new ArrayList<ArrayList<HashMap<String, String>>>();

        HashMap<String, String> group = new HashMap<String, String>();
        group.put("ROOT_NAME", "Group 1");

        groupData.add(group);

        group = new HashMap<String, String>();
        group.put("ROOT_NAME", "Group 2");

        groupData.add(group);

        // ===

        ArrayList<HashMap<String, String>> childGroup = new ArrayList<HashMap<String, String>>();

        group = new HashMap<String, String>();
        group.put("CHILD_NAME", "child in group 1");

        childGroup.add(group);

        group = new HashMap<String, String>();
        group.put("CHILD_NAME", "child in group 1");

        childGroup.add(group);

        childData.add(childGroup);


        childGroup = new ArrayList<HashMap<String, String>>();

        group = new HashMap<String, String>();
        group.put("CHILD_NAME", "child in group 2");

        childGroup.add(group);

        group = new HashMap<String, String>();
        group.put("CHILD_NAME", "child in group 2");

        childGroup.add(group);

        childData.add(childGroup);

        setListAdapter(new ExpandableListAdapter());
    }

    @Override
    protected void onResume() {
        super.onResume();
        TealiumHelper.trackView("ExpandableListExample", null);
    }

}
