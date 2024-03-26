package com.example.controle_de_pagamentos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class MainActivity extends AppCompatActivity {

    //Está declarando uma variável list do tipo ListView.
    //ListView é uma classe no Android que exibe uma lista de itens roláveis.
    ListView list1, list2;

    TextView texto1, texto2;

    //para acessar o banco de dados
    BancoDados db = new BancoDados(this);

    //Está declarando uma variável adapter do tipo ArrayAdapter<String>. Um ArrayAdapter
    //é uma classe no Android que pode fornecer uma ponte entre ListView e os dados subjacentes para essa vista.
    ArrayAdapter<String> adapter;

    //Está declarando uma variável arrayList do tipo ArrayList<String>.
    //ArrayList é uma classe em Java que implementa a interface List e permite criar listas redimensionáveis.
    ArrayList<String> arrayList;

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1; // Defina esta constante

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        texto1 = (TextView)findViewById(R.id.textView2);
        texto2 = (TextView)findViewById(R.id.textView6);
        // Isso é para linkar a variavel criada atras com a lista
        list1 = (ListView)findViewById(R.id.prestesAVencer);

        texto1.setVisibility(View.INVISIBLE);
        texto2.setVisibility(View.INVISIBLE);

        //Ao iniciar carregue a função prestesAVencer
        prestesAVencer();

        // Isso é para linkar a variavel criada atras com a lista
        list2 = (ListView)findViewById(R.id.vencidas);
        //Ao iniciar carregue a função vencidas
        vencidas();

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
        }

    }
    @Override
    protected void onResume() {
        super.onResume();

        // Chame aqui o método que carrega os dados na tabela
        prestesAVencer();
        vencidas();
    }



    /*
    Alem de colocar essas funções aqui, deve-se adicionar
    um codigo em adroidManifest.xml para que ele funcione

    exemplo:

    <activity
            android:name=".Deletar"
            android:exported="false" />
    */

    //Carrega tabela de faturas prestes a vencer no inicio da tela
    public void prestesAVencer(){

        //Está chamando o método listaTodos() da instância db da classe
        //BancoDados para obter uma lista de todos os dados e armazená-los na variável dados
        List<Dados> dados = db.listaTodos();

        //Está inicializando a variável arrayList como uma nova lista de strings.
        arrayList = new ArrayList<String>();

        //Está inicializando o adapter com um novo ArrayAdapter. O ArrayAdapter está
        //sendo configurado para usar o layout simple_list_item_1 do Android e a lista de strings arrayList.
        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, arrayList);

        //Está configurando o adapter para a ListView list.
        list1.setAdapter(adapter);


        //
        for(Dados d : dados){


            LocalDate dataEmissao = d.getData();
            LocalDate dataVencimento = dataEmissao.plusDays(30); // Adiciona 30 dias à data de emissão para obter a data de vencimento
            LocalDate hoje = LocalDate.now();

            long diasParaVencimento = ChronoUnit.DAYS.between(hoje, dataVencimento);

            if(diasParaVencimento <= 5 && diasParaVencimento >= 0){

                texto2.setVisibility(View.VISIBLE);
                //está adicionando uma string formatada à arrayList. A string contém informações sobre um objeto
                //d do tipo Dados, incluindo o ID, o cliente, o valor total, a parcela, a entrada,
                //o valor restante e a data.
                arrayList.add("ID: " + d.getId());


                //Este método é chamado para notificar o adapter que os dadossubjacentes foram
                //alterados e qualquer View que estava baseada nesses dados agora deve ser atualizada.
                adapter.notifyDataSetChanged();
            }

        }

    }

    //Carrega tabela de vencidas no inicio da tela
    public void vencidas(){

        //Está chamando o método listaTodos() da instância db da classe
        //BancoDados para obter uma lista de todos os dados e armazená-los na variável dados
        List<Dados> dados = db.listaTodos();

        //Está inicializando a variável arrayList como uma nova lista de strings.
        arrayList = new ArrayList<String>();

        //Está inicializando o adapter com um novo ArrayAdapter. O ArrayAdapter está
        //sendo configurado para usar o layout simple_list_item_1 do Android e a lista de strings arrayList.
        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, arrayList);

        //Está configurando o adapter para a ListView list.
        list2.setAdapter(adapter);

        //

        for(Dados d : dados){

            LocalDate dataVencimento = d.getData();
            LocalDate hoje = LocalDate.now();

            // Adiciona 30 dias à data de vencimento
            LocalDate vencimentoMais30 = dataVencimento.plusDays(30);

            // Verifica se hoje é depois de vencimentoMais30
            if(hoje.isAfter(vencimentoMais30)) {
                texto1.setVisibility(View.VISIBLE);

                //está adicionando uma string formatada à arrayList. A string contém informações sobre um objeto
                //d do tipo Dados, incluindo o ID, o cliente, o valor total, a parcela, a entrada,
                //o valor restante e a data.
                arrayList.add("ID: " + d.getId());


                //Este método é chamado para notificar o adapter que os dadossubjacentes foram
                //alterados e qualquer View que estava baseada nesses dados agora deve ser atualizada.
                adapter.notifyDataSetChanged();
            }


        }

    }

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