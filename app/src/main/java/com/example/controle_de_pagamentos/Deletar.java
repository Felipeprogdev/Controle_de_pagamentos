package com.example.controle_de_pagamentos;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Deletar extends AppCompatActivity {

    EditText iD;

    //para acessar o banco de dados
    BancoDados db = new BancoDados(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deletar);

        iD = findViewById(R.id.id2);


    }

    public void Confirmar(View view) {
        try {
            int ID = Integer.parseInt(iD.getText().toString());

            //Delete
            Dados dados = new Dados();
            dados.setId(ID);
            db.apagarDados(dados);
            Toast.makeText(Deletar.this, "Deletado com sucesso", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(Deletar.this, "Somente números", Toast.LENGTH_LONG).show();
        }
    }

}
