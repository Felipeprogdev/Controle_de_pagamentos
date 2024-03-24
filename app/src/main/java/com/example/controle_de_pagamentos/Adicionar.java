package com.example.controle_de_pagamentos;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.text.TextWatcher;
import android.text.Editable;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Adicionar extends AppCompatActivity {

    EditText editCliente, editValorTotal, editParcela, editEntrada, editData;

    //para acessar o banco de dados
    BancoDados db = new BancoDados(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar);

        // Isso é para linkar a variavel criada atras com o Cliente
        editCliente = findViewById(R.id.nomeDoCliente);

        // Isso é para linkar a variavel criada atras com o Valor total
        editValorTotal = findViewById(R.id.valorTotal);

        // Isso é para linkar a variavel criada atras com o Parcela
        editParcela = findViewById(R.id.parcelas);

        // Isso é para linkar a variavel criada atras com o Entrada
        editEntrada = findViewById(R.id.entrada);

        // Isso é para linkar a variavel criada atras com o Data
        editData = findViewById(R.id.dataDePagamento);

        // Adicionando um TextWatcher ao campo de entrada da data
        editData.addTextChangedListener(new TextWatcher() {
            private static final char SLASH = '/';
            private int length_before = 0;

            // Este método é chamado quando o texto é alterado
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            // Este método é chamado antes do texto ser alterado
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                length_before = s.length();
            }

            // Este método é chamado após o texto ser alterado
            @Override
            public void afterTextChanged(Editable s) {
                // Se o texto foi aumentado (ou seja, o usuário digitou um caractere)
                if (length_before < s.length()) {
                    // Se o usuário acabou de terminar de digitar o dia ou o mês
                    if (s.length() == 2 || s.length() == 5) {
                        // Adiciona uma barra ao texto
                        s.append(SLASH);
                    }
                }
            }
        });

    }

    //Ação ao clicar no botão
    public void Confirmar(View view){
        try {
            //Pega o texto digitado os textos digitados

            //Campo do cliente
            String clie = editCliente.getText().toString();

            //Campo do valor total
            BigDecimal valortot = new BigDecimal(editValorTotal.getText().toString());

            //Campo do valor de entrada
            BigDecimal entrad = new BigDecimal(editEntrada.getText().toString());

            //Campo da parcela
            BigDecimal parcel = new BigDecimal(editParcela.getText().toString());

            //Calcula o valor total - a entrada
            BigDecimal valres = valortot.subtract(entrad);

            //Salva a data como LocalDate para o banco de dados
            LocalDate datfull = LocalDate.parse(editData.getText().toString(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            //Insert no banco de dados
            db.addDados(new Dados(clie, valortot, parcel, entrad, valres, datfull));

            //Moutra na tela que os dados foram salvos
            Toast.makeText(Adicionar.this,"salvo com sucesso", Toast.LENGTH_LONG).show();

        }catch (Exception e){
            //Caso não digite algo errado
            Toast.makeText(Adicionar.this,"Digitou algo errado", Toast.LENGTH_LONG).show();
        }
    }
}
