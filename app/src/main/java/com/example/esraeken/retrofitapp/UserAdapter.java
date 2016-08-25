package com.example.esraeken.retrofitapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.esraeken.retrofitapp.model.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by esraeken on 27/07/16.
 */
public class UserAdapter extends RecyclerView.Adapter<UserViewHolder>
{
    private List<User> user;
    private Picasso picasso;//fotoğraf kütüphanesi
    private Context context;

    public UserAdapter(Context c){//zorunlu yazılan ctor
        this.context=c;
        user=new ArrayList<>();
        picasso=Picasso.with(context);
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.satir, parent,false);
        UserViewHolder viewHolder=new UserViewHolder(view,context);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position){

        holder.tv1.setText("username: "+user.get(position).getLogin());
        holder.tv2.setText("id: "+user.get(position).getId());

        picasso.load(user.get(position).getAvatarUrl())
                .centerCrop()
                .fit()
                .error(R.mipmap.ic_launcher)
                .into(holder.image);


    }

    public void updateList(List<User> list){
        user=new ArrayList<>(list);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return user.size();
    }
}

