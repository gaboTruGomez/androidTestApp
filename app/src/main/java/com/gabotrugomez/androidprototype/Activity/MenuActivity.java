package com.gabotrugomez.androidprototype.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.gabotrugomez.androidprototype.Adapter.ListViewAdapter;
import com.gabotrugomez.androidprototype.Adapter.RecyclerViewCustomAdapter;
import com.gabotrugomez.androidprototype.Model.Animal;
import com.gabotrugomez.androidprototype.R;
import com.gabotrugomez.androidprototype.Views.SimpleDividerItemDecorator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MenuActivity extends AppCompatActivity implements Runnable
{
    private ListView animalsListView;
    private ArrayList<Animal> animalsList;
    private OkHttpClient okHttpClient;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        downloadJsonAndSetAdapter();
    }

    private void setUpUI()
    {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_menu_activity);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);

        recyclerView.setLayoutManager(layoutManager);

        RecyclerViewCustomAdapter adapter = new RecyclerViewCustomAdapter(getApplicationContext(), animalsList, MenuActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SimpleDividerItemDecorator(this));
    }

    private void downloadJsonAndSetAdapter()
    {
        okHttpClient = new OkHttpClient();

        Request request = new Request.Builder()
                                .url("https://storage.googleapis.com/test.moovinfood.com/altomobile_prototype_step3.json")
                                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e)
            {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful())
                {
                    throw new IOException("Unexpected code " + response);
                }
                else
                {
                    try {
                        String responseString = response.body().string();
                        JSONArray animalsJsonArray = new JSONArray(responseString);

                        if (animalsJsonArray.length() > 0)
                        {
                            Log.i("APP-INFO", animalsJsonArray.toString(4));
                            getAnimalsFromJson(animalsJsonArray);
                        }
                        else
                        {
                            Log.e("APP-INFO", "JSON WAS EMPTY");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void getAnimalsFromJson(JSONArray jsonArray) throws JSONException
    {
        Gson gson = new Gson();

        Type type = new TypeToken<List<Animal>>() {}.getType();
        animalsList = gson.fromJson(jsonArray.toString(), type);

        setUIElements();
    }

    private void setUIElements()
    {

        /*
        MenuActivity.this.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {

                //setUpUI();

                // STEP 2 PROTOTYPE
                /*
                animalsListView = (ListView) findViewById(R.id.list_view_menu_activity);
                ListViewAdapter adapter = new ListViewAdapter(MenuActivity.this, animalsList);
                animalsListView.setAdapter(adapter);

                animalsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                    {
                        Toast.makeText(MenuActivity.this, "Life: " + animalsList.get(position).getLife(), Toast.LENGTH_SHORT).show();
                    }
                });
                */
            //}
        //});
        runOnUiThread(this);
    }

    @Override
    public void run()
    {
        setUpUI();
    }
}
