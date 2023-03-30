package com.unipi.kormazos.unipi_audio_stories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter  extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<Item> items;
    SelectListener listener;

    public MyAdapter(Context context, List<Item> items,SelectListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //Item item = items.get(position);
        holder.titleView.setText(items.get(position).getBookName());
        holder.informationView.setText(items.get(position).getInformation());
        holder.imageView.setImageResource(items.get(position).getImage());



        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(items.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }



        private List<Item> mItems;

        public MyAdapter(List<Item> items) {

        }

        public void setItems(List<Item> items) {
            mItems = items;
        }





}

