package com.example.cashtrack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class youOweListAdapter extends ArrayAdapter {
    List list = new ArrayList();

    public youOweListAdapter(@NonNull Context context, int resource, ArrayList<youOwes> al) {
        super(context, resource);
    }

    static class LayoutHandler{
        TextView DESC, NAME, COST, DATE;
    }

    @Override
    public void add(@Nullable Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        youOweListAdapter.LayoutHandler layoutHandler;
        if(row == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_owe_layout,parent,false);
            layoutHandler = new youOweListAdapter.LayoutHandler();
            layoutHandler.DESC=(TextView)row.findViewById(R.id.desc);
            layoutHandler.NAME = (TextView)row.findViewById(R.id.name);
            layoutHandler.COST=(TextView)row.findViewById(R.id.cost);
            layoutHandler.DATE=(TextView)row.findViewById(R.id.date);
            row.setTag(layoutHandler);
        }
        else{
            layoutHandler = (youOweListAdapter.LayoutHandler) row.getTag();
        }
        youOwes oweAdapter = (youOwes) this.getItem(position);
        layoutHandler.NAME.setText(oweAdapter.getDesc());
        layoutHandler.DESC.setText(oweAdapter.getName());
        layoutHandler.COST.setText(Integer.toString(oweAdapter.getCost()));
        layoutHandler.DATE.setText(oweAdapter.getDate());


        return row;
    }
}
