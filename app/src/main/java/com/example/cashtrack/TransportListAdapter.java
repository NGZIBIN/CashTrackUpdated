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

public class TransportListAdapter extends ArrayAdapter {

    List list = new ArrayList();

    public TransportListAdapter(@NonNull Context context, int resource, ArrayList<Transports> al) {
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
        TransportListAdapter.LayoutHandler layoutHandler;
        if(row == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout,parent,false);
            layoutHandler = new TransportListAdapter.LayoutHandler();
            layoutHandler.DESC=(TextView)row.findViewById(R.id.desc);
            layoutHandler.COST=(TextView)row.findViewById(R.id.cost);
            layoutHandler.DATE=(TextView)row.findViewById(R.id.date);
            row.setTag(layoutHandler);
        }
        else{
            layoutHandler = (TransportListAdapter.LayoutHandler) row.getTag();
        }
        Transports transportAdapter = (Transports) this.getItem(position);
        layoutHandler.DESC.setText(transportAdapter.getDesc());
        layoutHandler.COST.setText(Integer.toString(transportAdapter.getCost()));
        layoutHandler.DATE.setText(transportAdapter.getDate());


        return row;
    }
}
