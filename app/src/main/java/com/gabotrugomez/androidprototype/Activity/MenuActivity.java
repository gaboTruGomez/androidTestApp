package com.gabotrugomez.androidprototype.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.gabotrugomez.androidprototype.Adapter.ListViewAdapter;
import com.gabotrugomez.androidprototype.Model.Animal;
import com.gabotrugomez.androidprototype.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MenuActivity extends AppCompatActivity
{
    private ListView animalsListView;
    private ArrayList<Animal> animalsList;
    private OkHttpClient okHttpClient;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        okHttpClient = new OkHttpClient();

        downloadJsonAndSetAdapter();
    }

    private void downloadJsonAndSetAdapter()
    {
        Request request = new Request.Builder()
                                .url("https://flavioruben.herokuapp.com/data.json")
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
        ArrayList<Animal> animals = new ArrayList<>();
        Gson gson = new Gson();

        for (int i = 0; i < jsonArray.length(); i++)
        {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Animal animal = gson.fromJson(jsonObject.toString(), Animal.class);
            animals.add(animal);
        }

        animalsList = animals;
        setUIElements();
    }

    private void setUIElements()
    {
        MenuActivity.this.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
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
            }
        });

    }
}
