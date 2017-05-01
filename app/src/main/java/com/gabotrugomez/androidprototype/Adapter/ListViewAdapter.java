package com.gabotrugomez.androidprototype.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gabotrugomez.androidprototype.Model.Animal;
import com.gabotrugomez.androidprototype.R;

import java.util.ArrayList;

/**
 * Created by gaboTruGomez on 4/29/2017.
 */

public class ListViewAdapter extends ArrayAdapter<Animal>
{
    private ArrayList animalsArray;
    private ViewHolder viewHolder;

    private static class ViewHolder
    {
        TextView animalTxtView;
    }

    public ListViewAdapter(Context context, ArrayList animals) {
        super(context, R.layout.animal_list_view_row_item, animals);
        this.animalsArray = animals;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Animal animal = (Animal) animalsArray.get(position);


        if (convertView == null)
        {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.animal_list_view_row_item, parent, false);

            viewHolder.animalTxtView = (TextView) convertView.findViewById(R.id.animal_row_txt);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.animalTxtView.setText(animal.getName());

        return convertView;
    }
}
