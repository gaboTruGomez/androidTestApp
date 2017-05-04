package com.gabotrugomez.androidprototype.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gabotrugomez.androidprototype.Activity.AnimalDetailActivity;
import com.gabotrugomez.androidprototype.Model.Animal;
import com.gabotrugomez.androidprototype.Views.CircleImgTransform;
import com.gabotrugomez.androidprototype.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gaboTruGomez on 5/1/2017.
 */

public class RecyclerViewCustomAdapter extends RecyclerView.Adapter<RecyclerViewCustomAdapter.ViewHolder>
{
    private List<Animal> mDataSet;
    private Context mContext;
    private Activity mActivity;

    public RecyclerViewCustomAdapter(Context context, ArrayList<Animal> list, Activity activity)
    {
        mDataSet = list;
        mContext = context;
        mActivity = activity;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView titleTextView;
        public TextView descTextView;
        public TextView typeTextView;
        public ImageView imgView;
        public LinearLayout wrapperLayout;

        public ViewHolder(View v)
        {
            super(v);
            titleTextView = (TextView) v.findViewById(R.id.animal_title_item);
            descTextView = (TextView) v.findViewById(R.id.animal_description_item);
            typeTextView = (TextView) v.findViewById(R.id.animal_type_item);
            imgView = (ImageView) v.findViewById(R.id.animal_img_view_item);
            wrapperLayout = (LinearLayout) v.findViewById(R.id.animal_list_wrapper_layout_item);
        }
    }

    @Override
    public RecyclerViewCustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(mContext).inflate(R.layout.animal_list_row_item,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerViewCustomAdapter.ViewHolder holder, final int position)
    {
        holder.titleTextView.setText(mDataSet.get(position).getName());
        holder.descTextView.setText("Life: " + mDataSet.get(position).getLife());
        holder.typeTextView.setText("Type: " + mDataSet.get(position).getType());

        if (!mDataSet.get(position).getPictureURL().isEmpty())
        {
            Picasso.with(mContext)
                    .load(mDataSet.get(position).getPictureURL())
                    .error(R.drawable.img_not_found)
                    .transform(new CircleImgTransform())
                    .into(holder.imgView);
        }
        else
        {
            holder.imgView.setImageResource(R.drawable.img_not_found);
        }

        holder.wrapperLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO: DEPENDING ON "TYPE" ATTRIBUTE, START ACTIVITY 1 OR 2

                    Intent intent = new Intent(mContext, AnimalDetailActivity.class);
                    intent.putExtra("type", mDataSet.get(position).getType());
                    mActivity.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
