package com.pramudana.sam.apijakgo.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pramudana.sam.apijakgo.R;


public class DetailActivity extends AppCompatActivity {

    String nama, jabatan, wilayah, alamat, login, telepon;
    TextView txtNama, txtJabatan, txtWilayah, txtAlamat, txtLogin;
    Button btnCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //TODO get data parse
        nama = getIntent().getStringExtra("nama");
        jabatan = getIntent().getStringExtra("jabatan");
        wilayah = getIntent().getStringExtra("wilayah");
        alamat = getIntent().getStringExtra("alamat");
        login = getIntent().getStringExtra("login");
        telepon = getIntent().getStringExtra("phone");

        //TODO bind TextView
        txtNama = findViewById(R.id.txtNama);
        txtJabatan = findViewById(R.id.txtJabatan);
        txtWilayah = findViewById(R.id.txtWilayah);
        txtAlamat = findViewById(R.id.txtAlamat);
        txtLogin = findViewById(R.id.txtLogin);

        //TODO set TextView text
        txtNama.setText(nama);
        txtJabatan.setText(jabatan);
        txtWilayah.setText(wilayah);
        txtAlamat.setText(alamat);
        txtLogin.setText(login);

        //TODO Bind Button
        btnCall = findViewById(R.id.btnCall);

        //TODO setOnClick Button
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isPermissionGranted()) {
                    call_action();
                }
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void call_action() {
        Intent iCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + telepon));
        startActivity(iCall);
    }

    private boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is Granted");
                return true;
            } else {
                Log.v("TAG","Permission is Revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        } else {
            //Permission is automatically granted pn SDK <23 upon Installation
            Log.v("TAG","Permission is Granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                    call_action();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}
