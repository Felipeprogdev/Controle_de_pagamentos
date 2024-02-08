package com.example.controle_de_pagamentos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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