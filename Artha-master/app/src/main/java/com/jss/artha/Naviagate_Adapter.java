package com.jss.artha;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.artha.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Naviagate_Adapter extends RecyclerView.Adapter<Naviagate_Adapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<NaviagteModel> dataModelArrayList;

    public Naviagate_Adapter(Context ctx, ArrayList<NaviagteModel> dataModelArrayList){

        inflater = LayoutInflater.from(ctx);
        this.dataModelArrayList = dataModelArrayList;
    }

    @Override
    public Naviagate_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.naviagte_recycler_layout, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(Naviagate_Adapter.MyViewHolder holder, int position) {

        holder.name.setText(dataModelArrayList.get(position).getName());


    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView country, name, city;
        ImageView iv;

        public MyViewHolder(View itemView) {
            super(itemView);

//            country = (TextView) itemView.findViewById(R.id.country);
            name = (TextView) itemView.findViewById(R.id.name);
//            city = (TextView) itemView.findViewById(R.id.city);

        }

    }
}

