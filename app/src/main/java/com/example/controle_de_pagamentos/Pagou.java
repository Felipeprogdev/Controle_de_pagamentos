package com.example.controle_de_pagamentos;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Pagou extends AppCompatActivity {

    EditText iD, editValor, edit_Parcela;
    BancoDados db = new BancoDados(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagou_parcela);

        // Isso é para linkar a variavel criada atras com o valor
        iD = findViewById(R.id.id8);

        // Isso é para linkar a variavel criada atras com o valor
        editValor = findViewById(R.id.valor5);

        // Isso é para linkar a variavel criada atras com o valor
        edit_Parcela = findViewById(R.id.nova_parcela);

    }

    public void Pago(View view){
        try {
            int ID = Integer.parseInt(iD.getText().toString());

            Dados dados = new Dados();
            dados.setId(ID);

            Dados save = db.selecionarDados(ID);

            dados.setCliente(save.getCliente());
            dados.setValorTotal(save.getValorTotal());
            dados.setEntrada(save.getEntrada());
            dados.setData(save.getData());


            try {
                String parcela = edit_Parcela.getText().toString();
                double valor_Digitado = Double.parseDouble(editValor.getText().toString());

                double calculo = Double.parseDouble(save.getValorRestante());

                valor_Digitado = calculo - valor_Digitado;


                dados.setValorRestante(String.valueOf(valor_Digitado));
                dados.setParcela(parcela);

                if(valor_Digitado <= 0){
                    Dados apaga = new Dados();
                    apaga.setId(ID);
                    db.apagarDados(apaga);
                    Toast.makeText(Pagou.this, "A conta foi quitada", Toast.LENGTH_LONG).show();
                }
                else {
                    db.atualizarDados(dados);
                    Toast.makeText(Pagou.this, "Novos valores adicionados", Toast.LENGTH_LONG).show();
                }



            }catch (Exception e){
                double calculo0 = Double.parseDouble(save.getValorRestante());

                int parce = Integer.parseInt(save.getParcela());

                double calcul1 = calculo0 / parce;

                double calculo2 = calculo0 - calcul1;


                parce = parce - 1;


                dados.setValorRestante(String.valueOf(calculo2));
                dados.setParcela(String.valueOf(parce));

                if(parce <= 0){
                    Dados apaga = new Dados();
                    apaga.setId(ID);
                    db.apagarDados(apaga);
                    Toast.makeText(Pagou.this, "A conta foi quitada", Toast.LENGTH_LONG).show();
                }
                else {
                    db.atualizarDados(dados);
                    Toast.makeText(Pagou.this, "A mensalidade foi paga", Toast.LENGTH_LONG).show();
                }


            }


        }catch (Exception e){
            Toast.makeText(Pagou.this,"Digitou algo errado", Toast.LENGTH_LONG).show();


        }

    }
}