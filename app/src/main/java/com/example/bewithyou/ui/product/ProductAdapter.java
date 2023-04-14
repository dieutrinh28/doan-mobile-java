package com.example.bewithyou.ui.product;

import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bewithyou.R;
import com.example.bewithyou.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    private final List<Product> product_list;
    private final Context context;

    public ProductAdapter(List<Product> product_list, Context context) {
        this.product_list = product_list;
        this.context = context;
    }
    @Override
    public int getCount() {
        return product_list.size();
    }

    @Override
    public Object getItem(int position) {
        return product_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(product_list.get(position).getProductName());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MyView dataitem;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            dataitem = new MyView();
            convertView = inflater.inflate(R.layout.product_item, null);

            dataitem.iv_photo = convertView.findViewById(R.id.imv_product);
            dataitem.tv_name = convertView.findViewById(R.id.tv_itemname);
            dataitem.tv_price = convertView.findViewById(R.id.tv_itemprice);
            dataitem.tv_description = convertView.findViewById(R.id.tv_itemdescription);

            convertView.setTag(dataitem);
        } else {
            dataitem = (MyView) convertView.getTag();
        }

        Picasso.get().load(product_list.get(position).getProductImg()).into(dataitem.iv_photo);
        dataitem.tv_name.setText(product_list.get(position).getProductName());
        dataitem.tv_price.setText(product_list.get(position).getPrice());
        dataitem.tv_description.setText((product_list.get(position).getProductDescription()));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailProductActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("product_name",product_list.get(position).getProductName());
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    private static class MyView {
        ImageView iv_photo;
        TextView tv_name, tv_price, tv_description;
    }
}
