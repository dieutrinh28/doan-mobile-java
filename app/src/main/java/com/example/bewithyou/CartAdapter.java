package com.example.bewithyou;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

//public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {
//
//    private List<Cart> cartList;
//    private Context context;
//
//    public CartAdapter(Context context) {
//        this.cartList = cartList;
//        this.context = context;
//    }
//
//    @NonNull
//    @Override
//    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartview, parent, false);
//
//        return new CartViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
//        Cart cart = cartList.get(position);
//
//        holder.textView_productName.setText(cart.getProductName());
//        holder.textView_price.setText(String.valueOf(cart.getPrice()));
//        holder.textView_quantity.setText(String.valueOf(cart.getQuantity()));
//        Picasso.get().load(cart.getImgLink()).into(holder.imageView_cart);
//    }
//    public void setData(List<Cart> cartList) {
//        this.cartList = cartList;
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
//
//
//}
public class CartAdapter extends BaseAdapter {

    private List<Cart> Cart_list;
    private Context context;
    public CartAdapter(List<Cart> photo_list, Context context){
        this.Cart_list = photo_list;
        this.context = context;
    }
    @Override
    public int getCount() {
        return Cart_list.size();
    }

    @Override
    public Object getItem(int position) {
        return Cart_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(Cart_list.get(position).getProductName());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MyView dataitem;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null){
            dataitem = new MyView();
            convertView = inflater.inflate(R.layout.cartview, null);
            dataitem.iv_photo = convertView.findViewById(R.id.imageView_cart);
            dataitem.tv_name = convertView.findViewById(R.id.textView_productName);
            dataitem.tv_price=convertView.findViewById(R.id.textView_price);
            dataitem.tv_quantity=convertView.findViewById(R.id.textView_quantity);
            convertView.setTag(dataitem);
        }else {
            dataitem = (MyView) convertView.getTag();
        }
        // new DownloadImage(dataitem.iv_photo).execute(photo_list.get(position).getSource_photo());
        //  Picasso.get().load(photo_list.get(position).getSource_photo()).resize(300, 400).centerCrop().into(dataitem.iv_photo);

        //  Picasso.get().load((PhotoData.getPhotoById(id).getSource_photo())).resize(400,500).centerCrop().into(iv_detail);
        Picasso.get().load(Cart_list.get(position).getImgLink()).resize(300, 400).centerCrop().into(dataitem.iv_photo);
        dataitem.tv_name.setText(Cart_list.get(position).getProductName());
        dataitem.tv_price.setText(Cart_list.get(position).getPrice());
        dataitem.tv_quantity.setText(Cart_list.get(position).getQuantity());

        return convertView;
    }
    private static class MyView{
        ImageView iv_photo;
        TextView tv_name;
        TextView tv_price;
        TextView tv_quantity;

    }
}
