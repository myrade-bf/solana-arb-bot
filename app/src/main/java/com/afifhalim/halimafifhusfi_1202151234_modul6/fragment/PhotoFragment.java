package com.afifhalim.halimafifhusfi_1202151234_modul6.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ogaclejapan.smarttablayout.utils.v4.Bundler;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.afifhalim.halimafifhusfi_1202151234_modul6.R;
import com.afifhalim.halimafifhusfi_1202151234_modul6.adapter.PhotoAdapter;
import com.afifhalim.halimafifhusfi_1202151234_modul6.config.Constant;
import com.afifhalim.halimafifhusfi_1202151234_modul6.model.PhotoModel;

public class PhotoFragment extends Fragment {
    //deklarasi variable dan views
    private static final String KEY_PARAM = "key_param";
    private ArrayList<PhotoModel> photoList;
    private PhotoAdapter mAdapter;

    @BindView(R.id.rvFoto) //@BindView declare sekaligus inisialisasi view dengan menggunakan library ButterKnife
    RecyclerView rvFoto;
    @BindView(R.id.swipeRefresh) //@BindView declare sekaligus inisialisasi view dengan menggunakan library ButterKnife
            SwipeRefreshLayout swipeRefresh;

    public PhotoFragment() {
        // Required empty public constructor
    }

    //method untuk menerima data yang dikirimkan/passing dari activity
    public static Bundle arguments(String param) {
        return new Bundler()
                .putString(KEY_PARAM, param)
                .get();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    String menu;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        ButterKnife.bind(this, view); //binding ButterKnife dengan fragment
        Bundle bundle = getArguments(); //mengambil nilai/data yang didapatkan dari activity
        menu = bundle.getString(KEY_PARAM);

        photoList = new ArrayList<>();

        //konfig recyclerview layout manager dan adapter
        rvFoto.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new PhotoAdapter(photoList, getActivity());
        rvFoto.setAdapter(mAdapter);
        loadData();

        // refresh saat di load ulang dengan pull to refresh
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
        return view;
    }

    int commentCount = 0;

    //method untuk loaddata photo dari firebase
    private void loadData() {
        if(menu.equals("terbaru")) { //semua foto berdasarkan yang terbaru
            swipeRefresh.setRefreshing(true);
            Constant.refPhoto.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    photoList.clear();
                    commentCount = 0;
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.

                    for (final DataSnapshot ds : dataSnapshot.getChildren()) {
                        PhotoModel model = ds.getValue(PhotoModel.class);
                        photoList.add(model); //dimasukkan list photo
                        mAdapter.notifyDataSetChanged(); //refresh adapter
                    }
                    swipeRefresh.setRefreshing(false);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("", "Failed to read value.", error.toException());
                    //showProgress(false);
                }
            });
        } else { //hanya foto user tsb yang login
            swipeRefresh.setRefreshing(true);
            Constant.refPhoto.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    photoList.clear();
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.

                    for (final DataSnapshot ds : dataSnapshot.getChildren()) {
                        PhotoModel photo = ds.getValue(PhotoModel.class);

                        if(photo.getEmail().equals(Constant.currentUser.getEmail())) {
                            photoList.add(photo); //dimasukkan list photo
                            mAdapter.notifyDataSetChanged(); //refresh adapter
                        }
                        swipeRefresh.setRefreshing(false);
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
    }
}
