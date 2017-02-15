package com.cfd.freya.digipay.adapter;

/**
 * Created by FREYA on 11-02-2017.
 */

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cfd.freya.digipay.Models.Item;
import com.cfd.freya.digipay.Models.QRModel;
import com.cfd.freya.digipay.R;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter{

    Context context;
    ArrayList<Item>list;
    private static LayoutInflater inflater=null;
    public CustomAdapter(Context context, QRModel qrModel)
    {
        this.context=context;
        this.list=qrModel.getItemArrayList();
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class Holder
    {
        TextView t1,t2,t3,t4;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        View rowView;
        rowView=inflater.inflate(R.layout.list_item,null);
        holder.t1 = (TextView) rowView.findViewById(R.id.item);
        holder.t2 = (TextView) rowView.findViewById(R.id.quantity);
        holder.t3 = (TextView) rowView.findViewById(R.id.price);
        holder.t4 = (TextView) rowView.findViewById(R.id.subtotal);
        holder.t1.setText(list.get(position).getItName());
        holder.t2.setText(list.get(position).getItQuanitity());
        holder.t3.setText(list.get(position).getItPrice());
        holder.t4.setText(list.get(position).getItSubTotal());
        return rowView;
    }
}
