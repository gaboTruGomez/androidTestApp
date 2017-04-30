package com.gabotrugomez.androidprototype.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gabotrugomez.androidprototype.R;

import java.util.ArrayList;

/**
 * Created by gaboTruGomez on 4/29/2017.
 */

public class ListViewAdapter extends ArrayAdapter
{
    private ArrayList daysArray;
    private ViewHolder viewHolder;

    private static class ViewHolder
    {
        TextView dayTxtView;
    }

    public ListViewAdapter(Context context, ArrayList days) {
        super(context, R.layout.day_list_view_row_item, days);
        this.daysArray = days;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int day = (int) daysArray.get(position);


        if (convertView == null)
        {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.day_list_view_row_item, parent, false);

            viewHolder.dayTxtView = (TextView) convertView.findViewById(R.id.day_row_txt);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.dayTxtView.setText("Day: " + day);

        return convertView;
    }
}
