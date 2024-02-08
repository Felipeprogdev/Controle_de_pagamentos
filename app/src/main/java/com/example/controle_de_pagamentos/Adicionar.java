package com.example.controle_de_pagamentos;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Adicionar extends AppCompatActivity {


    EditText editCliente, editValorTotal, editParcela, editEntrada, editData;

    BancoDados db = new BancoDados(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar);

        // Isso é para linkar a variavel criada atras com o valor
        editCliente = findViewById(R.id.nomeDoCliente);

        // Isso é para linkar a variavel criada atras com o valor
        editValorTotal = findViewById(R.id.valorTotal);

        // Isso é para linkar a variavel criada atras com o valor
        editParcela = findViewById(R.id.parcelas);

        // Isso é para linkar a variavel criada atras com o valor
        editEntrada = findViewById(R.id.entrada);

        // Isso é para linkar a variavel criada atras com o valor
        editData = findViewById(R.id.dataDePagamento);


    }


    //Ação ao clicar no botão
    public void Confirmar(View view){
        try {
            String clie = editCliente.getText().toString();

            double valortot = Double.parseDouble(editValorTotal.getText().toString());
            double entrad = Double.parseDouble(editEntrada.getText().toString());
            String entrada = String.valueOf(entrad);

            String parcel = editParcela.getText().toString();

            double valres = valortot - entrad;

            String datfull = editData.getText().toString();



            String valortots =  String.valueOf(valortot);
            String valorRestante = String.valueOf(valres);


            //Inset
            db.addDados(new Dados(clie, valortots, parcel, entrada, valorRestante, datfull));
            Toast.makeText(Adicionar.this,"salvo com sucesso", Toast.LENGTH_LONG).show();

        }catch (Exception e){
            Toast.makeText(Adicionar.this,"Digitou algo errado", Toast.LENGTH_LONG).show();
    }

    }

}
