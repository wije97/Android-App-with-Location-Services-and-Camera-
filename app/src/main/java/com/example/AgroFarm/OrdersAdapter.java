package com.example.AgroFarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class OrdersAdapter extends BaseAdapter {

    private Context mContext;
    DBHandler dbHandler;

    private ArrayList<String> order_id = new ArrayList<String>();
    private ArrayList<String> pro_name = new ArrayList<String>();
    private ArrayList<String> pro_price = new ArrayList<String>();
    private ArrayList<String> sup_email = new ArrayList<String>();


    public OrdersAdapter(Context  context,ArrayList<String> order_id, ArrayList<String> pro_name, ArrayList<String> pro_price, ArrayList<String> sup_email
    )
    {
        this.mContext = context;
        this.order_id= order_id;
        this.pro_name = pro_name;
        this.pro_price = pro_price;
        this.sup_email = sup_email;
    }
    @Override
    public int getCount() {
        return pro_name.size();
    }
    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final OrdersAdapter.viewHolder holder;
        dbHandler =new DBHandler(mContext);
        LayoutInflater layoutInflater;
        if (convertView == null) {
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.orderlist, null);
            holder = new OrdersAdapter.viewHolder();
            holder.oid = (TextView) convertView.findViewById(R.id.tv_order_id);
            holder.proname = (TextView) convertView.findViewById(R.id.tv_name_orders);
            holder.proprice = (TextView) convertView.findViewById(R.id.tv_price_orders);
            holder.supmail = (TextView) convertView.findViewById(R.id.tv_sup_orders);
            convertView.setTag(holder);
        } else {
            holder = (OrdersAdapter.viewHolder) convertView.getTag();
        }

        holder.oid.setText(order_id.get(position));
        holder.proname.setText(pro_name.get(position));
        holder.proprice.setText(pro_price.get(position));
        holder.supmail.setText(sup_email.get(position));
        return convertView;

    }
    public class viewHolder {

        TextView oid;
        TextView proname;
        TextView proprice;
        TextView supmail;
    }
}
