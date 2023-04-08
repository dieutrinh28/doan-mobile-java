package com.example.bewithyou.ui.review;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.bewithyou.R;
import com.example.bewithyou.model.Review;
import com.squareup.picasso.Picasso;

import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class ReviewAdapter extends BaseAdapter {

    private List<Review> reviewList;

    private Context context;

    public ReviewAdapter(List<Review> reviewList, Context context) {
        this.reviewList = reviewList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return reviewList.size();
    }

    @Override
    public Object getItem(int i) {
        return reviewList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
        /*return Long.parseLong(reviewList.get(i).getComment());*/
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final MyView dataItem;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            dataItem = new MyView();
            view = inflater.inflate(R.layout.review_item, null);
            dataItem.avatarUser = view.findViewById(R.id.imgUser);
            dataItem.nameUser = view.findViewById(R.id.txtUsername);
            dataItem.rating = view.findViewById(R.id.ratingBarShow);
            dataItem.date = view.findViewById(R.id.txtDate);
            dataItem.comment = view.findViewById(R.id.txtComment);
            view.setTag(dataItem);
        }
        else {
            dataItem = (MyView) view.getTag();
        }

        /* lấy avatar user và name user từ id user*/
        /*Picasso.get().load(reviewList.get(i).)*/

        dataItem.nameUser.setText(reviewList.get(i).getUserId());
        dataItem.date.setText(reviewList.get(i).getDate());
        dataItem.comment.setText(reviewList.get(i).getComment());
        dataItem.rating.setRating(Float.parseFloat(reviewList.get(i).getRating()));
        return view;
    }

    private static class MyView {
        ImageView avatarUser;
        TextView nameUser;
        TextView date;
        TextView comment;
        RatingBar rating;
    }
}
