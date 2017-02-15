package com.cfd.freya.digipay.adapter;

/**
 * Created by FREYA on 11-02-2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cfd.freya.digipay.Models.Item;
import com.cfd.freya.digipay.Models.QRModel;
import com.cfd.freya.digipay.R;

import java.util.ArrayList;

public class CustomAdapterForList extends BaseAdapter{

    Context context;
    ArrayList<QRModel>list;
    private static LayoutInflater inflater=null;
    public CustomAdapterForList(Context context, ArrayList<QRModel> qrModel)
    {
        this.context=context;
        this.list=qrModel;
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
        TextView t1,t2,t3,t4,t5;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        View rowView;
        rowView=inflater.inflate(R.layout.list_qr_item,null);
        holder.t1 = (TextView) rowView.findViewById(R.id.storename);
        holder.t2 = (TextView) rowView.findViewById(R.id.username);
        holder.t3 = (TextView) rowView.findViewById(R.id.date);
        holder.t4 = (TextView) rowView.findViewById(R.id.item);
        holder.t5 = (TextView) rowView.findViewById(R.id.totalvalue);
        holder.t1.setText(list.get(position).getStoreName());
        holder.t2.setText(list.get(position).getUserName());
        holder.t3.setText(list.get(position).getDate().substring(0,15));
       holder.t4.setText(makeString(list.get(position).getItemArrayList()));
        holder.t5.setText("Rs"+list.get(position).getGrandTotal());
        return rowView;
    }
    public String makeString(ArrayList<Item> a){
        StringBuilder sb=new StringBuilder();
        for(Item item:a)
        {
            sb.append("\t"+item.itName.trim()+"\t\t\t\t\t\t\t"+item.itQuantity+"\t\t\t\t\t\t\t"+item.itPrice+"\t\t\t\t\t\t\t\t"+item.itSubtotal+"\n");
        }
        return sb.toString();
    }
}
