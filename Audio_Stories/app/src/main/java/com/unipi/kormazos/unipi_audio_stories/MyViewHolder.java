package com.unipi.kormazos.unipi_audio_stories;

import android.text.Layout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView titleView,informationView;
    RelativeLayout  cardView;

    public MyViewHolder(@NonNull View itemView) {

        super(itemView);
        imageView= itemView.findViewById(R.id.image);
        titleView = itemView.findViewById(R.id.name);
        informationView = itemView.findViewById(R.id.information);
        cardView = itemView.findViewById(R.id.main_container);

    }
}
