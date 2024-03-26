package com.example.controle_de_pagamentos;

import com.example.controle_de_pagamentos.GerarECarregarPdf;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.*;
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

            // Adiciona 1 mês à data existente
            LocalDate novaDat = save.getData().plusMonths(1);


            LocalDate hoje = LocalDate.now();
            int dia = hoje.getDayOfMonth();
            int mes = hoje.getMonthValue();
            int ano = hoje.getYear();
            int hora = 18;
            int minuto = 48;

            LocalTime horario = LocalTime.of(hora, minuto);

            LocalDate novaData = LocalDate.of(ano, mes, dia);

            // Se o mês da nova data é janeiro, então a data original era dezembro, então adiciona 1 ano
            if (novaDat.getMonth() == Month.JANUARY) {
                novaDat = novaDat.plusYears(1);
            }


            dados.setData(novaDat);

            //Verificar os campos digitados
            if (editValor.getText().toString().isEmpty()) {
                BigDecimal novoVal = save.getValorRestante().divide(save.getParcela(), 2, RoundingMode.DOWN);
                BigDecimal novoValor = save.getValorRestante().subtract(novoVal);
                dados.setValorRestante(novoValor);

                BigDecimal um = new BigDecimal(1);
                BigDecimal subtracaoDeParcela = save.getParcela().subtract(um);
                dados.setParcela(subtracaoDeParcela);

                //Deleta o cliente que quitou a conta da lista
                if (subtracaoDeParcela.compareTo(BigDecimal.ZERO) <= 0) {

                    GerarECarregarPdf.gerarPdf(ID, save.getCliente(), save.getValorTotal(), novoVal, save.getParcela(), novoValor,
                            novaData, horario,this);
                    //Delete
                    db.apagarDados(dados);
                    Toast.makeText(Pagou.this, "Conta quitada", Toast.LENGTH_LONG).show();

                }

                //Se não quitou, atualiza
                else {

                    GerarECarregarPdf.gerarPdf(ID, save.getCliente(), save.getValorTotal(), novoVal, save.getParcela(), novoValor,
                            novaData, horario,this);
                    db.atualizarDados(dados);
                    Toast.makeText(Pagou.this, "Conta atualizada", Toast.LENGTH_LONG).show();



                }
            }

            //Caso a pessoa seja paga com um valor diferente "geralmente acontece com que não
            //trabalha com contratos
            else {
                //Pega o que foi digitado em editValor que é o que falta para pagar
                BigDecimal editValorText = new BigDecimal(editValor.getText().toString());

                //Pega o valor restante atual
                BigDecimal valor1 = save.getValorRestante();

                //Compara se o valor digitado é maior que o atual
                if (editValorText.compareTo(valor1) > 0) {
                    Toast.makeText(Pagou.this,"Se for adicionar um valor maior, coloqueo em editar", Toast.LENGTH_LONG).show();
                }

                else {
                    //Pega o valor pago da fatura
                    BigDecimal novoVal = valor1.subtract(editValorText);

                    //Seta o valor em dados
                    dados.setValorRestante(editValorText);
                    //Pega o que foi digitado em edit_Parcela
                    BigDecimal parcel = new BigDecimal(edit_Parcela.getText().toString());
                    //Seta o valor em parcel
                    dados.setParcela(parcel);

                    //Caso a parcela fique em 0, a conta sera quitada
                    if (parcel.compareTo(BigDecimal.ZERO) <= 0) {

                        GerarECarregarPdf.gerarPdf(ID, save.getCliente(), save.getValorTotal(), novoVal, save.getParcela(), editValorText,
                                novaData, horario,this);
                        //Delete
                        db.apagarDados(dados);
                        Toast.makeText(Pagou.this, "Conta quitada", Toast.LENGTH_LONG).show();



                    }

                    else {

                        GerarECarregarPdf.gerarPdf(ID, save.getCliente(), save.getValorTotal(), novoVal, save.getParcela(), editValorText,
                            novaData, horario,this);
                        db.atualizarDados(dados);
                        Toast.makeText(Pagou.this, "Valores modificados e atualizados", Toast.LENGTH_LONG).show();
                    }
                    }


            }


        }catch (Exception e){
            Toast.makeText(Pagou.this,"Digitou algo errado", Toast.LENGTH_LONG).show();
        }
        finally {
            try {
                // pausa a execução por 5 segundos
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                int a = 0;
            }

        }
    }
}
