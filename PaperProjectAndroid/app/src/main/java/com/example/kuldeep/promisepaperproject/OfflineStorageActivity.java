package com.example.kuldeep.promisepaperproject;

import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

import java.util.ArrayList;

public class OfflineStorageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_storage);
        ArrayList<String> strings1 = new ArrayList<>();
        strings1.add("1");
        strings1.add("2");
        strings1.add("3");
        ArrayList<String> strings2 = new ArrayList<>();
        ArrayList<Button> buttons = new ArrayList<>(10);
        strings2.add("11");
        strings2.add("12");
        strings2.add("13");
        //ListView listView = (ListView) findViewById(R.id.offlistview);
//         ExpandableListView expandableListView = new ExpandableListView(this);
//         ExpandableListAdapter expandableListAdapter = new ExpandableListAdapter() {
//            @Override
//            public void registerDataSetObserver(DataSetObserver observer) {
//
//            }
//
//            @Override
//            public void unregisterDataSetObserver(DataSetObserver observer) {
//
//            }
//
//            @Override
//            public int getGroupCount() {
//                return 0;
//            }
//
//            @Override
//            public int getChildrenCount(int groupPosition) {
//                return 0;
//            }
//
//            @Override
//            public Object getGroup(int groupPosition) {
//                return null;
//            }
//
//            @Override
//            public Object getChild(int groupPosition, int childPosition) {
//                return null;
//            }
//
//            @Override
//            public long getGroupId(int groupPosition) {
//                return 0;
//            }
//
//            @Override
//            public long getChildId(int groupPosition, int childPosition) {
//                return 0;
//            }
//
//            @Override
//            public boolean hasStableIds() {
//                return false;
//            }
//
//            @Override
//            public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
//                return null;
//            }
//
//            @Override
//            public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
//                return null;
//            }
//
//            @Override
//            public boolean isChildSelectable(int groupPosition, int childPosition) {
//                return false;
//            }
//
//            @Override
//            public boolean areAllItemsEnabled() {
//                return false;
//            }
//
//            @Override
//            public boolean isEmpty() {
//                return false;
//            }
//
//            @Override
//            public void onGroupExpanded(int groupPosition) {
//
//            }
//
//            @Override
//            public void onGroupCollapsed(int groupPosition) {
//
//            }
//
//            @Override
//            public long getCombinedChildId(long groupId, long childId) {
//                return 0;
//            }
//
//            @Override
//            public long getCombinedGroupId(long groupId) {
//                return 0;
//            }
//        };
//         ArrayAdapter adapter = new ArrayAdapter<Button>(this, R.layout.buttonitem, buttons);
//         expandableListView.setAdapter(adapter);
        //expandableListView.addView(new Button(this));
        //listView.addView(expandableListView);
//        ExpandableListView expandableListView1 = new ExpandableListView(this);
//        ArrayAdapter adapter1 = new ArrayAdapter<String>(this, R.layout.listitemtext, (ArrayList)strings2);
//        expandableListView1.setAdapter(adapter1);
//        listView.addView(expandableListView1);

    }

    public void LoadList()
    {
        //Load The ListView with ExpendableListsOfPaper per Subject Wise based on SubjectID sorted by year
    }
}
