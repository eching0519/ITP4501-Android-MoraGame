package com.example.moragame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class History_ListAdapter extends ArrayAdapter<Record> {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Record> records;
    private int mResource;

    public History_ListAdapter(Context context, int resource, ArrayList<Record> records) {
        super(context, resource, records);
        mContext = context;
        mResource = resource;
    }

    public View getView(int position, View convertView, ViewGroup parents) {
        String gameDate = getItem(position).getGameDate();
        String gameTime = getItem(position).getGameTime();
        String opponentName = getItem(position).getOpponentName();
        String winOrLost = getItem(position).getWinOrLost();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parents,false);

        TextView tvGameDate = convertView.findViewById(R.id.tvGameDate);
        TextView tvGameTime = convertView.findViewById(R.id.tvGameTime);
        TextView tvOpponentName = convertView.findViewById(R.id.tvOpponentName);
        TextView tvWinOrLost = convertView.findViewById(R.id.tvWinOrLost);

        tvGameDate.setText(gameDate);
        tvGameTime.setText(gameTime);
        tvOpponentName.setText(opponentName);
        tvWinOrLost.setText(winOrLost);

        return convertView;
    }
}
