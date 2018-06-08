package com.example.mynote;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
    private List<String> list;
    private Context context;

    public MyAdapter(Context context,List list){
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.recyclerview_item,parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.tvItemMonth.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG );

        holder.tvItemMonth.setText(list.get(position));


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{
        TextView tvItemMonth;
        TextView tvItemTitle;
        TextView tvItemContent;
        TextView tvItemDate;

        public MyHolder(View itemView) {
            super(itemView);
            tvItemMonth=itemView.findViewById(R.id.tv_item_month);
            tvItemTitle=itemView.findViewById(R.id.tv_item_title);
            tvItemContent=itemView.findViewById(R.id.tv_item_content);
            tvItemDate=itemView.findViewById(R.id.tv_item_date);
        }
    }
}
