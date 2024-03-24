package com.example.controle_de_pagamentos;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class Pagou extends AppCompatActivity {

    // Declaração das variáveis EditText para ID, valor e parcela
    EditText iD, editValor, edit_Parcela;

    // Declaração de um objeto BancoDados para acessar o banco de dados
    BancoDados db = new BancoDados(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagou_parcela);

        // Vinculação das variáveis EditText com os campos correspondentes no layout
        iD = findViewById(R.id.id8);
        editValor = findViewById(R.id.valor5);
        edit_Parcela = findViewById(R.id.nova_parcela);
    }

    // Método chamado quando o botão Pago é clicado
    public void Pago(View view){
        try {
            // Conversão do ID digitado para um inteiro
            int ID = Integer.parseInt(iD.getText().toString());

            // Criação de um novo objeto Dados e definição de seu ID
            Dados dados = new Dados();
            dados.setId(ID);

            // Obtenção dos dados salvos para o ID digitado
            Dados save = db.selecionarDados(ID);

            // Definição dos campos restantes do objeto Dados com os dados salvos
            dados.setCliente(save.getCliente());
            dados.setValorTotal(save.getValorTotal());
            dados.setEntrada(save.getEntrada());
            dados.setData(save.getData());


            if (editValor.getText().toString().isEmpty()) {
                BigDecimal novoVal = save.getValorRestante().divide(save.getParcela(), 2, RoundingMode.DOWN);
                BigDecimal novoValor = save.getValorRestante().subtract(novoVal);
                dados.setValorRestante(novoValor);

                BigDecimal um = new BigDecimal(1);
                BigDecimal subtracaoDeParcela = save.getParcela().subtract(um);
                dados.setParcela(subtracaoDeParcela);

                // Atualiza os valores no objeto Dados
                if (subtracaoDeParcela.compareTo(BigDecimal.ZERO) <= 0) {

                    //Delete
                    db.apagarDados(dados);
                    Toast.makeText(Pagou.this, "Conta quitada", Toast.LENGTH_LONG).show();

                }
                else {

                    db.atualizarDados(dados);
                    Toast.makeText(Pagou.this, "Conta atualizada", Toast.LENGTH_LONG).show();
                }
            }

            //Caso a pessoa seja paga com um valor diferente "geralmente acontece com que não
            //trabalha com contratos
            else {
                BigDecimal editValorText = new BigDecimal(editValor.getText().toString());
                dados.setValorRestante(editValorText);


                BigDecimal parcel = new BigDecimal(edit_Parcela.getText().toString());
                dados.setParcela(parcel);

                //Caso a parcela fique em 0, a conta sera quitada
                if (parcel.compareTo(BigDecimal.ZERO) <= 0) {

                    //Delete
                    db.apagarDados(dados);
                    Toast.makeText(Pagou.this, "Conta quitada", Toast.LENGTH_LONG).show();

                }

                else {
                    db.atualizarDados(dados);
                    Toast.makeText(Pagou.this, "Valores modificados e atualizados", Toast.LENGTH_LONG).show();
                }
            }


        }catch (Exception e){
            Toast.makeText(Pagou.this,"Digitou algo errado", Toast.LENGTH_LONG).show();
        }
    }
}
