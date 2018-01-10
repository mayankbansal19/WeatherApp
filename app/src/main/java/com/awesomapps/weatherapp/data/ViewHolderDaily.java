package com.awesomapps.weatherapp.data;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import weatherapp.awesomeapps.com.weatherapp.R;

/**
 * Created by Mayank on 7/16/2017.
 */
public class ViewHolderDaily extends RecyclerView.ViewHolder {

    public CardView cv;
    public TextView time;
    public TextView temp;
    public ImageView imageView;
    public TextView humidity;
    public RelativeLayout relativeLayout;


    public ViewHolderDaily(View itemView) {
        super(itemView);
        relativeLayout = (RelativeLayout)itemView.findViewById(R.id.rl_cardview);
        cv = (CardView) itemView.findViewById(R.id.cardView);
        time = (TextView) itemView.findViewById(R.id.timetext);
        temp = (TextView) itemView.findViewById(R.id.temp);
        imageView = (ImageView) itemView.findViewById(R.id.iconimage);
        humidity = (TextView) itemView.findViewById(R.id.humidity) ;

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                    if (humidity.getVisibility() == View.VISIBLE) {
                        humidity.setVisibility(View.GONE);
                    } else {
                        humidity.setVisibility(View.VISIBLE);
                    }
                }
        });
    }


}
