package com.pdscjxy.serverapp.view.iosspinner;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.pdscjxy.serverapp.R;

import java.util.List;

/**
 * Created by dell on 2017/8/28.
 */

public abstract class BaseSpinnerAdapter extends BaseAdapter {
    private List mData;
    private Context mContext;

    public abstract String getTitle(int position);

    public BaseSpinnerAdapter(Context context, List mData) {
        this.mContext = context;
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_pop_spinner, null);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.popText);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(getTitle(position));
        if (mContext.getResources().getString(R.string.cancel).equals(getTitle(position))) {
            holder.title.setTextColor(ContextCompat.getColor(mContext, R.color.color_2772FF));
        } else {
            holder.title.setTextColor(ContextCompat.getColor(mContext, R.color.color_333333));
        }
        return convertView;
    }

    private class ViewHolder {
        TextView title;
    }
}
