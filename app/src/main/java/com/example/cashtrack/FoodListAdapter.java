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

public class FoodListAdapter extends ArrayAdapter {
    List list = new ArrayList();


    public FoodListAdapter(@NonNull Context context, int resource, ArrayList<Foods> al) {
        super(context, resource);
    }

    static class LayoutHandler{
        TextView DESC, COST, DATE;
    }

    @Override
    public void add(@Nullable Object object) {
//        super.add(object);
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
        LayoutHandler layoutHandler;
        if(row == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout,parent,false);
            layoutHandler = new LayoutHandler();
            layoutHandler.DESC=(TextView)row.findViewById(R.id.desc);
            layoutHandler.COST=(TextView)row.findViewById(R.id.cost);
            layoutHandler.DATE=(TextView)row.findViewById(R.id.date);
            row.setTag(layoutHandler);
        }
        else{
            layoutHandler = (LayoutHandler) row.getTag();
        }
        Foods foodListAdapter = (Foods)this.getItem(position);
        layoutHandler.DESC.setText(foodListAdapter.getDesc());
        layoutHandler.COST.setText(Integer.toString(foodListAdapter.getCost()));
        layoutHandler.DATE.setText(foodListAdapter.getDate());


        return row;
    }
}
