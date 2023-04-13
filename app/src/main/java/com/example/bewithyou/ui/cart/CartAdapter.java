package com.example.bewithyou.ui.cart;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bewithyou.R;
import com.example.bewithyou.model.Cart;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends BaseAdapter {

    private final List<Cart> Cart_list;
    private final Context context;

    public CartAdapter(List<Cart> photo_list, Context context) {
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MyView dataitem;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            dataitem = new MyView();
            convertView = inflater.inflate(R.layout.cart_item, null);
            dataitem.incrementButton = convertView.findViewById(R.id.btnAdd);
            dataitem.decrementButton = convertView.findViewById(R.id.btnMinus);
            dataitem.iv_photo = convertView.findViewById(R.id.imageView_cart);
            dataitem.tv_name = convertView.findViewById(R.id.textView_productName);
            dataitem.tv_price = convertView.findViewById(R.id.textView_price);
            dataitem.tv_quantity = convertView.findViewById(R.id.textView_quantity);
            dataitem.deleteButton = convertView.findViewById(R.id.btnDelete);
            convertView.setTag(dataitem);
        } else {
            dataitem = (MyView) convertView.getTag();
        }

        Picasso.get().load(Cart_list.get(position).getImgLink()).resize(300, 400).centerCrop().into(dataitem.iv_photo);
        dataitem.tv_name.setText(Cart_list.get(position).getProductName());
        dataitem.tv_price.setText(Cart_list.get(position).getPrice());
        dataitem.tv_quantity.setText(Cart_list.get(position).getQuantity());
        final Cart cartItem = Cart_list.get(position);
        if (dataitem.incrementButton != null) {
            dataitem.incrementButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int quantity = Integer.parseInt(dataitem.tv_quantity.getText().toString());
                    quantity++;
                    dataitem.tv_quantity.setText(String.valueOf(quantity));
                    cartItem.setQuantity(String.valueOf(quantity));
                    updateCartItem(cartItem, String.valueOf(quantity));
                }
            });
        }

        if (dataitem.decrementButton != null) {
            dataitem.decrementButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int quantity = Integer.parseInt(dataitem.tv_quantity.getText().toString());
                    if (quantity > 1) {
                        quantity--;
                        dataitem.tv_quantity.setText(String.valueOf(quantity));
                        cartItem.setQuantity(String.valueOf(quantity));
                        updateCartItem(cartItem, String.valueOf(quantity));
                    }
                }
            });
        }
        if (dataitem.deleteButton != null) {
            dataitem.deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteCartItem(cartItem.getProductName());
                }
            });
        }


        return convertView;
    }

    private void updateCartItem(Cart cart, String newQuantity) {
        SharedPreferences preferences = context.getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String username = preferences.getString("username", "default_value");


        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("cart").child(username).child(cart.getProductName());

        cart.setQuantity(newQuantity);

        cartRef.setValue(cart)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Update the UI to reflect the new quantity
                        notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle the error
                        Toast.makeText(context, "Failed to update cart item", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void deleteCartItem(String cartId) {
        SharedPreferences preferences = context.getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String username = preferences.getString("username", "default_value");
        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference().child("cart").child(username).child(cartId);
        cartRef.removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                if (error == null) {

                    Toast.makeText(context, "Item deleted from cart", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle error
                    Toast.makeText(context, "Error deleting item from cart", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private static class MyView {
        ImageView iv_photo;
        TextView tv_name;
        TextView tv_price;
        TextView tv_quantity;

        ImageButton incrementButton;
        ImageButton decrementButton;
        ImageButton deleteButton;

    }
}
