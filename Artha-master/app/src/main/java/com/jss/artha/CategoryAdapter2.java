package com.jss.artha;



import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.artha.R;

import java.util.List;

public class CategoryAdapter2 extends RecyclerView.Adapter<CategoryAdapter2.CategoryViewHolder>{
    private List<Category> horizontalCategoryList;
    public session sess;
    Context context;
    public static int last=0;
    public TextView tv;
    public static String SelectedCategory;

    public CategoryAdapter2(List<Category> horizontalCategoryList, Context context){
        this.horizontalCategoryList= horizontalCategoryList;
        this.context = context;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View categoryProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list, parent, false);
        CategoryViewHolder gvh = new CategoryViewHolder(categoryProductView);
        sess=new session(categoryProductView.getContext());
        tv=categoryProductView.findViewById(R.id.categoryName);

        return gvh;
    }

    @Override
    public void onBindViewHolder(final CategoryViewHolder holder, final int position) {


        if(horizontalCategoryList.get(position).getCategoryName().equalsIgnoreCase(Fragment_stories.s)){
            holder.txtview.setBackgroundColor(Color.parseColor("#22A246"));
            holder.txtview.setTextColor(Color.parseColor("#FFFFFF"));

        }
//else {
//    if(horizontalCategoryList.get(position).getCategoryName().equalsIgnoreCase("all")) {
//        holder.txtview.setBackgroundColor(Color.parseColor("#22A246"));
//        holder.txtview.setTextColor(Color.parseColor("#FFFFFF"));
//    }
//}


        holder.txtview.setText(horizontalCategoryList.get(position).getCategoryName());

        holder.txtview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // SelectedCategory= horizontalCategoryList.get(position).getCategoryName().toString();

                //holder.txtview.setBackgroundColor(Color.YELLOW);
                notifyDataSetChanged();


//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        holder.txtview.setBackgroundColor(Color.YELLOW);
//                    }
//                }, 2000);

                String categoryName = horizontalCategoryList.get(position).getCategoryName().toString();
                //SelectedCategory= horizontalCategoryList.get(position).getCategoryName().toString();
                sess.set_cat1(horizontalCategoryList.get(position).getCategoryName().toString());
              //  Toast.makeText(context, categoryName + " is selected", Toast.LENGTH_SHORT).show();


            }
        });
        if(last==position){
            holder.txtview.setBackgroundColor(Color.parseColor("#22A246"));
            holder.txtview.setTextColor(Color.parseColor("#FFFFFF"));
            SelectedCategory= horizontalCategoryList.get(position).getCategoryName().toString();
        }
        else {
            holder.txtview.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.txtview.setTextColor(Color.parseColor("#000000"));

        }
//        else {
//            holder.txtview.setBackgroundColor(Color.parseColor("#C0EA7C"));
//        }

    }

    @Override
    public int getItemCount() {
        return horizontalCategoryList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView txtview;
        public CategoryViewHolder(View view) {
            super(view);
            txtview=view.findViewById(R.id.categoryName);
        }
    }
}
