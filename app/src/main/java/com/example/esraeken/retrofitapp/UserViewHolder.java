package com.example.esraeken.retrofitapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import android.widget.TextView;

/**
 * Created by esraeken on 27/07/16.
 */
public class UserViewHolder  extends RecyclerView.ViewHolder {

    public TextView tv1;
    public TextView tv2;
    public AppCompatImageView image;
    private Context context;

    public UserViewHolder(View itemView, Context c) {
        super(itemView);
        context=c;
        tv1= (TextView) itemView.findViewById(R.id.tv1);
        tv2= (TextView) itemView.findViewById(R.id.tv2);
        image=(AppCompatImageView)itemView.findViewById(R.id.image);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=  new Intent(context,ProfileActivity.class);
                i.putExtra("username",tv1.getText());
                context.startActivity(i);
            }
        });
    }
}
