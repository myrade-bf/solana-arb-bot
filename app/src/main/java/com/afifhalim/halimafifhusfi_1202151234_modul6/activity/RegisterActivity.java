package com.afifhalim.halimafifhusfi_1202151234_modul6.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.afifhalim.halimafifhusfi_1202151234_modul6.R;
import com.afifhalim.halimafifhusfi_1202151234_modul6.config.Constant;

public class RegisterActivity extends AppCompatActivity {

    //Deklarasi view
    @BindView(R.id.email) //@BindView declare sekaligus inisialisasi view dengan menggunakan library ButterKnife
    TextInputEditText tvEmail;
    @BindView(R.id.password) //@BindView declare sekaligus inisialisasi view dengan menggunakan library ButterKnife
    TextInputEditText tvPassword;
    @BindView(R.id.email_sign_in_button) //@BindView declare sekaligus inisialisasi view dengan menggunakan library ButterKnife
    Button btnRegister;
    private ProgressDialog pbDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this); //Binding ButterKnife dengan activity ini
        pbDialog = new ProgressDialog(this);
    }

    //method handling btnregister
    @OnClick(R.id.email_sign_in_button)
    public void register() {
        //validasi kosong
        if(tvEmail.getText().toString().isEmpty()) {
            tvEmail.setError("Required");
            return;
        }
        //validasi kosong
        if(tvPassword.getText().toString().isEmpty()) {
            tvPassword.setError("Required");
            return;
        }

        pbDialog.setMessage("Proses Mendaftarkan");
        pbDialog.setIndeterminate(true);
        pbDialog.show();

        createUser();
    }

    //method untuk register user, dengan fitur email authentication di firebase
    private void createUser() {
        final String email = tvEmail.getText().toString();
        String password = tvPassword.getText().toString();

        Constant.mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            pbDialog.dismiss();
                            Log.d("", "createUserWithEmail:success");
                            Constant.currentUser = Constant.mAuth.getCurrentUser(); //simpan data user
                            Toast.makeText(RegisterActivity.this, "Berhasil mendaftar, Silahkan login!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class)); //panggil login activity
                            finish();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            pbDialog.dismiss();
                            Log.w("", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }
}
