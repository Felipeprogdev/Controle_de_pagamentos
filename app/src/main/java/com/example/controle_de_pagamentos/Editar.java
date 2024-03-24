package com.example.controle_de_pagamentos;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Editar extends AppCompatActivity {

    EditText iD, editCliente, editValorTotal, editParcela, editEntrada, editData;

    //para acessar o banco de dados
    BancoDados db = new BancoDados(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        // Linkando as variáveis com os campos de entrada correspondentes
        iD = findViewById(R.id.id);
        editCliente = findViewById(R.id.nomeDoCliente2);
        editValorTotal = findViewById(R.id.valorTotal2);
        editParcela = findViewById(R.id.parcelas2);
        editEntrada = findViewById(R.id.entrada2);
        editData = findViewById(R.id.dataDePagamento2);
    }

    //Ação ao clicar no botão
    public void Confirmar(View view){
        try {
            //Pega o texto digitado os textos digitados

            //Campo do ID
            int ID = Integer.parseInt(iD.getText().toString());

            //Edição
            Dados dados = new Dados();
            dados.setId(ID);

            Dados save = db.selecionarDados(ID);

            //Campo do cliente
            if(!editCliente.getText().toString().isEmpty()){
                String clie = editCliente.getText().toString();
                dados.setCliente(clie);
            }
            else {
                dados.setCliente(save.getCliente());
            }

            //Campos do valor total e da entrada
            if(!editValorTotal.getText().toString().isEmpty() || !editEntrada.getText().toString().isEmpty()){
                BigDecimal valortot = new BigDecimal(editValorTotal.getText().toString());
                BigDecimal entrad = new BigDecimal(editEntrada.getText().toString());
                BigDecimal valres = valortot.subtract(entrad);
                dados.setEntrada(entrad);
                dados.setValorRestante(valres);
                dados.setValorTotal(valortot);
            }
            else{
                dados.setEntrada(save.getEntrada());
                dados.setValorRestante(save.getValorRestante());
                dados.setValorTotal(save.getValorTotal());
            }

            //Campo da parcela
            if(!editParcela.getText().toString().isEmpty()){
                BigDecimal parcel = new BigDecimal(editParcela.getText().toString());
                dados.setParcela(parcel);
            }
            else {
                dados.setParcela(save.getParcela());
            }

            //Campo da data
            if(!editData.getText().toString().isEmpty()){
                LocalDate datfull = LocalDate.parse(editData.getText().toString(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                dados.setData(datfull);
            }
            else {
                dados.setData(save.getData());
            }

            //Atualiza os dados no banco de dados
            db.atualizarDados(dados);

            //Mostra na tela que os dados foram editados com sucesso
            Toast.makeText(Editar.this,"Editado com sucesso", Toast.LENGTH_LONG).show();

        } catch (NumberFormatException e) {
            //Caso o usuário digite um número inválido
            Toast.makeText(Editar.this,"Digitou um número inválido", Toast.LENGTH_LONG).show();
        }
    }
}
