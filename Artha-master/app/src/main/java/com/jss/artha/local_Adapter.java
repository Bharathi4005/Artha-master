package com.jss.artha;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.artha.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class local_Adapter extends RecyclerView.Adapter<local_Adapter.MyViewHolderDemand> {
    ArrayList<String> Dataset_username = new ArrayList<String>();
    ArrayList<String> Dataset_body = new ArrayList<String>();
    ArrayList<String> Dataset_time = new ArrayList<String>();



    public static class MyViewHolderDemand extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView post_time, post_body, username;
        public CardView cardView;
        public MyViewHolderDemand(View v) {
            super(v);
            cardView = v.findViewById(R.id.card);
            username = v.findViewById(R.id.username);
            post_body = v.findViewById(R.id.post_body);
            post_time = v.findViewById(R.id.post_time);
        }
    }

    public local_Adapter(ArrayList<String> myDataset1, ArrayList<String> myDataset2, ArrayList<String> myDataset3) {
        this.Dataset_username = myDataset1;
        this.Dataset_body = myDataset2;
        this.Dataset_time = myDataset3;
    }

    @Override
    public local_Adapter.MyViewHolderDemand onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_local_stories, parent, false);
        //MyViewHolder vh = new MyViewHolder(v);
        //return vh;
        return new local_Adapter.MyViewHolderDemand(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderDemand holder, int position) {
        String user = Dataset_username.get(position);
        String body = Dataset_body.get(position);
        String time = Dataset_time.get(position);


        holder.username.setText(user);
        holder.post_body.setText(body);
       //
        // holder.post_time.setText(time);

    }

    @Override
    public int getItemCount() {
        return Dataset_username.size();
    }

    public void filterlist(ArrayList<String> fltr) {
        Dataset_username = fltr;
        notifyDataSetChanged();
    }
//    public static Bitmap getBitmapFromURL(String src) {
//        try {
//            Log.e("src",src);
//            URL url = new URL(src);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setDoInput(true);
//            connection.connect();
//            InputStream input = connection.getInputStream();
//            Bitmap myBitmap = BitmapFactory.decodeStream(input);
//            Log.e("Bitmap","returned");
//            return myBitmap;
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.e("Exception",e.getMessage());
//            return null;
//        }


    public Bitmap downloadImage(View view, String url) {

        url.replace("/", "");
        url.replace(" ", "");
        Log.i("Button", "Tapped");

        DownloadImage task = new DownloadImage();
        Bitmap result = null;
        try {
            result = task.execute(url).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        //dp.setImageBitmap(result);

        return result;
    }

    public class DownloadImage extends AsyncTask<String, Void, Bitmap> {


        @Override
        protected Bitmap doInBackground(String... imageurls) {


            URL url;
            HttpURLConnection httpURLConnection;

            try {
                url = new URL(imageurls[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                InputStream in = httpURLConnection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(in);
                return myBitmap;

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (Exception e) {
                return null;
            }
        }
    }

}


