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

public class ListAdapter extends ArrayAdapter {
    List list = new ArrayList();
    public ListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    static class LayoutHandler{
        TextView DESC, COST, DATE;
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
        LayoutHandler layoutHandler;
        if(row == null){
            LayoutInflater layoutInflater =(LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        ShoppingAdapter shoppingAdapter = (ShoppingAdapter)this.getItem(position);
        layoutHandler.DESC.setText(shoppingAdapter.getDesc());
        layoutHandler.COST.setText(shoppingAdapter.getCost());
        layoutHandler.DATE.setText(shoppingAdapter.getDate());


        return row;

    }
}
