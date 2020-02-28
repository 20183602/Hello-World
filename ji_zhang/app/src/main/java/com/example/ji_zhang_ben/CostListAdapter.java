package com.example.ji_zhang_ben;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CostListAdapter extends BaseAdapter {
    private List<Costbean> mList;
    private Context mContxet;
    private LayoutInflater mLayoutInflater;

    public CostListAdapter(Context context,List<Costbean> list) {
        mContxet = context;
        mList = list;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.list_item,null);
            viewHolder.mCostTile = convertView.findViewById(R.id.tile);
            viewHolder.mCostDate = convertView.findViewById(R.id.date);
            viewHolder.mCostMoney = convertView.findViewById(R.id.money);
            convertView.setTag(viewHolder);                                  //诞生新的convertView
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Costbean costbean = mList.get(position);
        viewHolder.mCostTile.setText(costbean.cost_tile);
        viewHolder.mCostDate.setText(costbean.cost_date);
        viewHolder.mCostMoney.setText(costbean.cost_money);
        return convertView;
    }
    private  static class ViewHolder{
        public TextView mCostTile;
        public TextView mCostDate;
        public TextView mCostMoney;
    }
}
