package com.afifhalim.halimafifhusfi_1202151234_modul6.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.afifhalim.halimafifhusfi_1202151234_modul6.R;
import com.afifhalim.halimafifhusfi_1202151234_modul6.model.CommentModel;

//class adapter untuk row pada comment list
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {
    //deklarasi variable
    private List<CommentModel> commentList;

    //class viewholder untuk declare dan inisialisasi views pada row yang digunakan
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvComment;
        public ImageView imgAvatar;

        public MyViewHolder(View view) {
            super(view);
            imgAvatar = (ImageView) view.findViewById(R.id.imgAvatar);
            tvName = (TextView) view.findViewById(R.id.tvNama);
            tvComment = (TextView) view.findViewById(R.id.tvKomentar);
        }
    }

    //konstruktor untuk menerima data yang dikirimkan dari activity ke adapter
    public CommentAdapter(List<CommentModel> commentList) {
        this.commentList = commentList;
    }

    //create ke layout row yang dipilih
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_comment, parent, false);

        return new MyViewHolder(itemView);
    }

    //binding antara data yang didapatkan ke dalam views
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CommentModel food = commentList.get(position);
        holder.tvComment.setText(food.getComment());
        holder.tvName.setText(food.getName());
    }

    //count data
    @Override
    public int getItemCount() {
        return commentList.size();
    }
}