package com.gabotrugomez.androidprototype.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.gabotrugomez.androidprototype.Adapter.ListViewAdapter;
import com.gabotrugomez.androidprototype.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuActivity extends AppCompatActivity
{
    private ListView calendarListView;
    private ArrayList<Integer> days;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        days = new ArrayList<>();
        for (int i = 0; i < 31; i++)
        {
            days.add(i + 1);
        }

        calendarListView = (ListView) findViewById(R.id.list_view_menu_activity);
        ListViewAdapter adapter = new ListViewAdapter(this, days);
        calendarListView.setAdapter(adapter);

        calendarListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Toast.makeText(MenuActivity.this, "Days " + days.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
