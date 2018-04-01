package com.afifhalim.halimafifhusfi_1202151234_modul6.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.afifhalim.halimafifhusfi_1202151234_modul6.R;
import com.afifhalim.halimafifhusfi_1202151234_modul6.adapter.CommentAdapter;
import com.afifhalim.halimafifhusfi_1202151234_modul6.config.Constant;
import com.afifhalim.halimafifhusfi_1202151234_modul6.model.CommentModel;
import com.afifhalim.halimafifhusfi_1202151234_modul6.model.PhotoModel;

public class PhotoDetailActivity extends AppCompatActivity {
    //Deklarasi View
    @BindView(R.id.imgEvent) //@BindView declare sekaligus inisialisasi view dengan menggunakan library ButterKnife
    ImageView imgEvent;
    @BindView(R.id.tvDescription) //@BindView declare sekaligus inisialisasi view dengan menggunakan library ButterKnife
    TextView tvDescription;
    @BindView(R.id.rvKomentar) //@BindView declare sekaligus inisialisasi view dengan menggunakan library ButterKnife
    RecyclerView rvKomentar;
    @BindView(R.id.etKomentar) //@BindView declare sekaligus inisialisasi view dengan menggunakan library ButterKnife
    TextInputEditText etKomentar;
    @BindView(R.id.btnKirim) //@BindView declare sekaligus inisialisasi view dengan menggunakan library ButterKnife
    Button btnKirim;
    @BindView(R.id.toolbar) //@BindView declare sekaligus inisialisasi view dengan menggunakan library ButterKnife
    android.support.v7.widget.Toolbar toolbar;
    private ArrayList<CommentModel> commentList; //arraylist untuk menyimpan hasil load komentar
    private CommentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        ButterKnife.bind(this); //Binding ButterKnife pada activity ini
        setSupportActionBar(toolbar);
        displayHomeAsUpEnabled();
        commentList = new ArrayList<>();

        //setting layout dari recyclerview dan adapter
        LinearLayoutManager llManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvKomentar.setLayoutManager(llManager);
        mAdapter = new CommentAdapter(commentList);
        rvKomentar.setAdapter(mAdapter);

        loadIntent();
    }

    PhotoModel photo;

    private void loadIntent() { //mengambil value yang dipassing dari selected photo di PhotoAdapter
        if (getIntent().getExtras() != null) {
            photo = (PhotoModel) getIntent().getSerializableExtra("photoData"); //ambil model yg dipassing
            Picasso.get().load(photo.getImage_url()).into(imgEvent); //load gambar menggukanan picasso
            tvDescription.setText(photo.getDesc() + "\nby: " + photo.getName());
            setTitle(photo.getTitle()); //set judul toolbar
            loadComment(); //load comment
        }
    }

    //method ini berfungsi untuk load komentar dari key/photo yang dipilih
    private void loadComment() {
        Constant.refPhoto.child(photo.getKey()).child("commentList")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                commentList.clear();
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                for (final DataSnapshot ds : dataSnapshot.getChildren()) {
                    CommentModel model = ds.getValue(CommentModel.class);
                    commentList.add(model); //dimasukkan kedalam list
                    mAdapter.notifyDataSetChanged(); //refresh data
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("", "Failed to read value.", error.toException());
                //showProgress(false);
            }
        });
    }

    //menampilkan tombol back button diatas kiri
    private void displayHomeAsUpEnabled() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    //method untuk handling btnKirim
    @OnClick(R.id.btnKirim)
    public void kirim() {
        //validasi kosong
        if (etKomentar.getText().toString().isEmpty()) {
            Toast.makeText(this, "Harap isi komentar", Toast.LENGTH_SHORT).show();
            return;
        }

        //Insert atau push data komentar ke firebase
        Constant.refPhoto.child(photo.getKey()).child("commentList").push().setValue(new CommentModel(
                Constant.currentUser.getEmail().split("@")[0],
                Constant.currentUser.getEmail(),
                etKomentar.getText().toString()
        ));

        etKomentar.setText("");
    }

    //handler jika back button di klik
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
}
