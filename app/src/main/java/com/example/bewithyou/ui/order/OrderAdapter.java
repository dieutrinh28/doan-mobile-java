package com.example.bewithyou.ui.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bewithyou.R;
import com.example.bewithyou.model.Cart;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderAdapter extends BaseAdapter {
    private final List<Cart> cartList;
    private final Context context;

    public OrderAdapter(List<Cart> cartList, Context context) {
        this.cartList = cartList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return cartList.size();
    }

    @Override
    public Object getItem(int i) {
        return cartList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final MyView dataItem;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            dataItem = new MyView();
            view = inflater.inflate(R.layout.order_item, null);
            dataItem.iv_photo = view.findViewById(R.id.imageOrder);
            dataItem.tv_name = view.findViewById(R.id.tvNameOrder);
            dataItem.tv_price = view.findViewById(R.id.tvPriceOrder);
            dataItem.tv_quantity = view.findViewById(R.id.tvQuantityOrder);
            view.setTag(dataItem);
        } else {
            dataItem = (MyView) view.getTag();
        }


        Picasso.get().load(cartList.get(i).getImgLink()).resize(300, 400).centerCrop().into(dataItem.iv_photo);
        dataItem.tv_name.setText(cartList.get(i).getProductName());
        dataItem.tv_price.setText(cartList.get(i).getPrice());
        dataItem.tv_quantity.setText(cartList.get(i).getQuantity());
        return view;
    }

    private static class MyView {
        ImageView iv_photo;
        TextView tv_name;
        TextView tv_price;
        TextView tv_quantity;
    }
}
