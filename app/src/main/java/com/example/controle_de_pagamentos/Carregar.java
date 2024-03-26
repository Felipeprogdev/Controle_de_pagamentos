package com.example.controle_de_pagamentos;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Carregar extends AppCompatActivity {

    //Está declarando uma variável list do tipo ListView.
    //ListView é uma classe no Android que exibe uma lista de itens roláveis.
    ListView list;

    //para acessar o banco de dados
    BancoDados db = new BancoDados(this);

    //Está declarando uma variável adapter do tipo ArrayAdapter<String>. Um ArrayAdapter
    //é uma classe no Android que pode fornecer uma ponte entre ListView e os dados subjacentes para essa vista.
    ArrayAdapter<String> adapter;

    //Está declarando uma variável arrayList do tipo ArrayList<String>.
    //ArrayList é uma classe em Java que implementa a interface List e permite criar listas redimensionáveis.
    ArrayList<String> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carregar);

        // Isso é para linkar a variavel criada atras com a lista
        list = (ListView)findViewById(R.id.faturas);

        //Ao iniciar carregue a função listarDados
        listarDados();

        /*
        Carregar somente 1 arquivo
        Dados dados = db.selecionarDados(7);
        Toast.makeText(Adicionar.this,"Id:" + dados.getId() + "Valor total" + dados.getValorTotal() +
                "Cliente" + dados.getCliente() + "Parcelas" + dados.getParcela() + "Entrada" +
                dados.getEntrada() + "Data" + dados.getData(), Toast.LENGTH_LONG).show();
         */
    }

    //Carrega tabela de faturas
    public void listarDados(){

        //Está chamando o método listaTodos() da instância db da classe
        //BancoDados para obter uma lista de todos os dados e armazená-los na variável dados
        List<Dados> dados = db.listaTodos();

        //Está inicializando a variável arrayList como uma nova lista de strings.
        arrayList = new ArrayList<String>();

        //Está inicializando o adapter com um novo ArrayAdapter. O ArrayAdapter está
        //sendo configurado para usar o layout simple_list_item_1 do Android e a lista de strings arrayList.
        adapter = new ArrayAdapter<String>(Carregar.this, android.R.layout.simple_list_item_1, arrayList);

        //Está configurando o adapter para a ListView list.
        list.setAdapter(adapter);

        //
        for(Dados d : dados){
            //está adicionando uma string formatada à arrayList. A string contém informações sobre um objeto
            //d do tipo Dados, incluindo o ID, o cliente, o valor total, a parcela, a entrada,
            //o valor restante e a data.
            arrayList.add("ID: " + d.getId() + "\nCliente: " + d.getCliente() + "\nValor total: R$"
                    + d.getValorTotal().setScale(2, BigDecimal.ROUND_HALF_UP) + "\nParcela: " +
                    d.getParcela() + "\nEntrada: R$" + d.getEntrada().setScale(2, BigDecimal.ROUND_HALF_UP) +
                    "\nValor restante: R$" + d.getValorRestante().setScale(2, BigDecimal.ROUND_HALF_UP) +
                    "\nData: " + d.getData());


            //Este método é chamado para notificar o adapter que os dadossubjacentes foram
            //alterados e qualquer View que estava baseada nesses dados agora deve ser atualizada.
            adapter.notifyDataSetChanged();
        }

    }
}
