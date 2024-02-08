package com.example.controle_de_pagamentos;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.ArrayRes;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Carregar extends AppCompatActivity {

    ListView list;

    BancoDados db = new BancoDados(this);

    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carregar);

        list = (ListView)findViewById(R.id.lista);
        listarDados();

        /*
        Carregar somente 1 arquivo
        Dados dados = db.selecionarDados(7);
        Toast.makeText(Adicionar.this,"Id:" + dados.getId() + "Valor total" + dados.getValorTotal() +
                "Cliente" + dados.getCliente() + "Parcelas" + dados.getParcela() + "Entrada" +
                dados.getEntrada() + "Data" + dados.getData(), Toast.LENGTH_LONG).show();
         */
    }
    public void listarDados(){
        List<Dados> dados = db.listaTodos();

        arrayList = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(Carregar.this, android.R.layout.simple_list_item_1, arrayList);

        list.setAdapter(adapter);


        for(Dados d : dados){
            arrayList.add("ID: " + d.getId() + "\nCliente: " + d.getCliente() + "\nValor total: R$"
                    + d.getValorTotal() + "\nParcela: " + d.getParcela() + "\nEntrada: R$" + d.getEntrada() +
                    "\nValor restante: R$" + d.getValorRestante() + "\nData: " + d.getData());

            adapter.notifyDataSetChanged();
        }

    }
}
