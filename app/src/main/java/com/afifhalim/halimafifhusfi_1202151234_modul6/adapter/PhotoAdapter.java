package com.afifhalim.halimafifhusfi_1202151234_modul6.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import com.afifhalim.halimafifhusfi_1202151234_modul6.R;
import com.afifhalim.halimafifhusfi_1202151234_modul6.activity.PhotoDetailActivity;
import com.afifhalim.halimafifhusfi_1202151234_modul6.config.Constant;
import com.afifhalim.halimafifhusfi_1202151234_modul6.model.CommentModel;
import com.afifhalim.halimafifhusfi_1202151234_modul6.model.PhotoModel;

//class adapter untuk row pada photo list
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyViewHolder> {
    //deklarasi variable
    private List<PhotoModel> photoList;
    private Context context;

    //class viewholder untuk declare dan inisialisasi views pada row yang digunakan
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvDesc, tvTitle, tvComment;
        public ImageView imgPhoto;
        public CardView cvPhoto;

        public MyViewHolder(View view) {
            super(view);
            imgPhoto = (ImageView) view.findViewById(R.id.imgPhoto);
            tvName = (TextView) view.findViewById(R.id.tvNama);
            tvDesc = (TextView) view.findViewById(R.id.tvDeskripsi);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvComment = (TextView) view.findViewById(R.id.tvComment);
            cvPhoto = (CardView) view.findViewById(R.id.cvPhoto);
        }
    }

    //konstruktor untuk menerima data yang dikirimkan dari activity ke adapter
    public PhotoAdapter(List<PhotoModel> photoList, Context context) {
        this.photoList = photoList;
        this.context = context;
    }

    //create ke layout row yang dipilih
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_photo, parent, false);

        return new MyViewHolder(itemView);
    }

    int comment = 0;

    //binding antara data yang didapatkan ke dalam views
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final PhotoModel photo = photoList.get(position);
        holder.tvDesc.setText(photo.getDesc());
        holder.tvName.setText(photo.getName());
        holder.tvTitle.setText(photo.getTitle());

        //mengambil data jumlah komentar setiap photo
        Constant.refPhoto.child(photo.getKey()).child("commentList")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        comment = 0;
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.

                        for (final DataSnapshot ds : dataSnapshot.getChildren()) {
                            CommentModel model = ds.getValue(CommentModel.class);
                            comment++;
                        }
                        holder.tvComment.setText(comment + "");
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("", "Failed to read value.", error.toException());
                        //showProgress(false);
                    }
                });

        Picasso.get().load(photo.getImage_url()).into(holder.imgPhoto); //load gambar dengan picasso
        holder.cvPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(context, PhotoDetailActivity.class); //intent menuju detail photo
                in.putExtra("photoData", photo);
                context.startActivity(in);
            }
        });
    }

    //count data
    @Override
    public int getItemCount() {
        return photoList.size();
    }
}