package com.example.controle_de_pagamentos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1; // Defina esta constante

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        } else {
            // Permission has already been granted
        }


    }


    /*
    Alem de colocar essas funções aqui, deve-se adicionar
    um codigo em adroidManifest.xml para que ele funcione

    exemplo:

    <activity
            android:name=".Deletar"
            android:exported="false" />
    */

    public void Adicione(View view){
        Intent in = new Intent(MainActivity.this, Adicionar.class);
        startActivity(in);
    }

    public void Carregue(View view){
        Intent in = new Intent(MainActivity.this, Carregar.class);
        startActivity(in);
    }

    public void Delet(View view){
        Intent in = new Intent(MainActivity.this, Deletar.class);
        startActivity(in);
    }

    public void Edit(View view){
        Intent in = new Intent(MainActivity.this, Editar.class);
        startActivity(in);
    }

    public void Pago(View view){
        Intent in = new Intent(MainActivity.this, Pagou.class);
        startActivity(in);
    }


}