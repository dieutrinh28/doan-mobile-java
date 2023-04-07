package com.example.bewithyou.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.bewithyou.R;
import com.example.bewithyou.model.Store;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StoreAdapter extends BaseAdapter {
    private List<Store> store_list;
    private Context context;

    public StoreAdapter(List<Store> store_list, Context context) {
        this.store_list = store_list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return store_list.size();
    }

    @Override
    public Object getItem(int position) {
        return store_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(store_list.get(position).getStoreName());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MyView dataitem;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            dataitem = new MyView();
            convertView = inflater.inflate(R.layout.activity_item_store, null);

            dataitem.iv_photo = convertView.findViewById(R.id.imgViewStore);
            dataitem.tv_name = convertView.findViewById(R.id.txtName);
            dataitem.tv_description = convertView.findViewById(R.id.txtDescription);
            dataitem.ratingBar = convertView.findViewById(R.id.ratingBar);

            convertView.setTag(dataitem);
        } else {
            dataitem = (MyView) convertView.getTag();
        }
        // new DownloadImage(dataitem.iv_photo).execute(photo_list.get(position).getSource_photo());
        //  Picasso.get().load(photo_list.get(position).getSource_photo()).resize(300, 400).centerCrop().into(dataitem.iv_photo);

        //  Picasso.get().load((PhotoData.getPhotoById(id).getSource_photo())).resize(400,500).centerCrop().into(iv_detail);
        Picasso.get().load(store_list.get(position).getStoreImg()).resize(300, 400).centerCrop().into(dataitem.iv_photo);
        dataitem.tv_name.setText(store_list.get(position).getStoreName());
        dataitem.tv_description.setText(store_list.get(position).getStoreDescription());
        dataitem.ratingBar.setRating(Float.parseFloat(store_list.get(position).getRating()));

        return convertView;
    }
    private static class MyView {
        ImageView iv_photo;
        TextView tv_name, tv_description;
        RatingBar ratingBar;
    }
}
