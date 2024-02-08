package com.example.controle_de_pagamentos;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Editar extends AppCompatActivity {

    EditText iD, editCliente, editValorTotal, editParcela, editEntrada, editData;

    BancoDados db = new BancoDados(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        // Isso é para linkar a variavel criada atras com o valor
        iD = findViewById(R.id.id);

        // Isso é para linkar a variavel criada atras com o valor
        editCliente = findViewById(R.id.nomeDoCliente2);

        // Isso é para linkar a variavel criada atras com o valor
        editValorTotal = findViewById(R.id.valorTotal2);

        // Isso é para linkar a variavel criada atras com o valor
        editParcela = findViewById(R.id.parcelas2);

        // Isso é para linkar a variavel criada atras com o valor
        editEntrada = findViewById(R.id.entrada2);

        // Isso é para linkar a variavel criada atras com o valor
        editData = findViewById(R.id.dataDePagamento2);

    }

    //Ação ao clicar no botão
    public void Confirmar(View view){
        try {
            int ID = Integer.parseInt(iD.getText().toString());

            //Edição
            Dados dados = new Dados();
            dados.setId(ID);

            Dados save = db.selecionarDados(ID);

            if(!editCliente.getText().toString().isEmpty()){
                String clie = editCliente.getText().toString();

                dados.setCliente(clie);
            }
            else {
                dados.setCliente(save.getCliente());
            }

            if(!editValorTotal.getText().toString().isEmpty() || !editEntrada.getText().toString().isEmpty()){
                double valortot = Double.parseDouble(editValorTotal.getText().toString());
                double entrad = Double.parseDouble(editEntrada.getText().toString());
                double valres = valortot - entrad;
                String entrada = String.valueOf(entrad);
                dados.setEntrada(entrada);
                String valorRestante = String.valueOf(valres);
                dados.setValorRestante(valorRestante);
                String valortots =  String.valueOf(valortot);
                dados.setValorTotal(valortots);
            }
            else{
                dados.setEntrada(save.getEntrada());
                dados.setValorRestante(save.getValorRestante());
                dados.setValorTotal(save.getValorTotal());
            }

            if(!editParcela.getText().toString().isEmpty()){
                String parcel = editParcela.getText().toString();
                dados.setParcela(parcel);
            }
            else {
                dados.setParcela(save.getParcela());
            }


            if(!editData.getText().toString().isEmpty()){
                String datfull = editData.getText().toString();
                dados.setData(datfull);
            }
            else {
                dados.setData(save.getData());
            }



            db.atualizarDados(dados);

            Toast.makeText(Editar.this,"Editado com sucesso", Toast.LENGTH_LONG).show();

        }catch (Exception e){
            Toast.makeText(Editar.this,"Digitou algo errado", Toast.LENGTH_LONG).show();
        }

    }
}
