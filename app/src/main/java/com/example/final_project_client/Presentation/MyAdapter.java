package com.example.final_project_client.Presentation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.final_project_client.ApartmentRecord;
import com.example.final_project_client.R;

/**
 * Created by TAMIR on 3/22/2018.
 */

public class MyAdapter extends ArrayAdapter<String> {
    private ApartmentRecord[] records;
    private Context context;
    public MyAdapter(Context context, ApartmentRecord[] records) {
        super(context, R.layout.listview_item);
        this.records = records;
        this.context = context;
    }

    @Override
    public int getCount() {
        return records.length;
    }



    @Override
    public View getView(int position,View convertView,  ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.listview_item, parent, false);
        }
        return convertView;
    }
}
