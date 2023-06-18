package com.example.AgroFarm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class StockListAdapter extends BaseAdapter {

    private Context mContext;
    DBHandler dbHandler;


    private ArrayList<String> product_name = new ArrayList<String>();
    private ArrayList<String> product_quantity = new ArrayList<String>();
    private ArrayList<String> product_price = new ArrayList<String>();
    private ArrayList<String> email = new ArrayList<String>();
    private ArrayList<String> product_code = new ArrayList<String>();
    private ArrayList<byte[]> product_image = new ArrayList<byte[]>();


    public StockListAdapter(Context  context, ArrayList<String> product_name,ArrayList<String> product_quantity, ArrayList<String> product_price,ArrayList<String> product_code, ArrayList<String> email, ArrayList<byte[]> product_image
    )
    {
        this.mContext = context;
        this.product_name = product_name;
        this.product_quantity = product_quantity;
        this.product_price = product_price;
        this.product_code= product_code;
        this.email=email;
        this.product_image=product_image;
    }
    @Override
    public int getCount() {
        return product_name.size();
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
        final    viewHolder holder;
        dbHandler =new DBHandler(mContext);
        LayoutInflater layoutInflater;
        if (convertView == null) {
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.stocklist, null);
            holder = new viewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.et_proname);
            holder.quantity = (TextView) convertView.findViewById(R.id.et_prostock);
            holder.price = (TextView) convertView.findViewById(R.id.et_proprice);
            holder.pcode = (TextView) convertView.findViewById(R.id.et_procode);
            holder.uname = (TextView) convertView.findViewById(R.id.et_proemail);
            holder.image = (ImageView) convertView.findViewById(R.id.iv_image_stock);
            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();
        }
        holder.name.setText(product_name.get(position));
        holder.quantity.setText(product_quantity.get(position));
        holder.price.setText(product_price.get(position));
        holder.uname.setText(email.get(position));
        holder.pcode.setText(product_code.get(position));

        Bitmap bitmap = BitmapFactory.decodeByteArray(product_image.get(position), 0, product_image.get(position).length);
        holder.image.setImageBitmap(bitmap);
        return convertView;

    }

    public class viewHolder {
        TextView name;
        TextView quantity;
        TextView price;
        TextView uname;
        TextView pcode;
        ImageView image;
    }
}
