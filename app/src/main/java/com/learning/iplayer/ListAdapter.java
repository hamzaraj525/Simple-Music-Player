package com.learning.iplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ListAdapter extends ArrayAdapter {

    int[] imageArray;
    String[] itemsArray;

    public ListAdapter(@NonNull Context context, String[] items, int[] image) {
        super(context, R.layout.custom_list, R.id.textView1, items);
        imageArray = image;
        itemsArray = items;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.custom_list, parent, false);
      /*  ImageView imageView = row.findViewById(R.id.img);
        imageView.setImageResource(imageArray[position]);*/
        TextView textView = row.findViewById(R.id.textView1);
        textView.setText(itemsArray[position]);
        return row;
    }
}
