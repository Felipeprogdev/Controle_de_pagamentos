package com.example.controle_de_pagamentos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

// Classe para gerenciar o banco de dados
public class BancoDados extends SQLiteOpenHelper {

    // Definindo constantes para o banco de dados e tabela
    private static final int VERSAO_BANCO = 1;
    private static final String BANCO_CLIENTE = "bd_clientes";

    // Definindo constantes para as colunas da tabela
    private static final String TABELA_CLIENTE = "tb_clientes";
    private static final String COLUNA_ID = "ID";
    private static final String COLUNA_CLIENTE = "cliente";
    private static final String COLUNA_VALOR_TOTAL = "total";
    private static final String COLUNA_PARCELA = "parcelas";
    private static final String COLUNA_ENTRADA = "entrada";
    private static final String COLUNA_VALOR_RESTANTE = "restante";
    private static final String COLUNA_DATA = "data";

    // Construtor da classe
    public BancoDados(@Nullable Context context) {
        super(context, BANCO_CLIENTE, null, VERSAO_BANCO);
    }

    // Método para criar a tabela no banco de dados
    @Override
    public void onCreate(SQLiteDatabase db) {

        // String para a criação da tabela no banco de dados ele passa como uma string no banco mesmo
        String QUERY_COLUNA = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY," +
                " %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)", TABELA_CLIENTE,
                COLUNA_ID, COLUNA_CLIENTE, COLUNA_VALOR_TOTAL, COLUNA_PARCELA, COLUNA_ENTRADA,
                COLUNA_VALOR_RESTANTE, COLUNA_DATA);

        // Executando a criação da tabela
        db.execSQL(QUERY_COLUNA);
    }

    // Método para atualizar o banco de dados
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Método para adicionar um cliente ao banco de dados
    void addDados(Dados dados){

        // Abrindo o banco de dados para escrita
        SQLiteDatabase db = this.getWritableDatabase();

        // Criando um novo conjunto de valores para inserir na tabela
        ContentValues values = new ContentValues();

        // Adicionando os valores do cliente ao conjunto
        values.put(COLUNA_CLIENTE, dados.getCliente());
        values.put(COLUNA_VALOR_TOTAL, dados.getValorTotal());
        values.put(COLUNA_PARCELA, dados.getParcela());
        values.put(COLUNA_ENTRADA, dados.getEntrada());
        values.put(COLUNA_VALOR_RESTANTE, dados.getValorRestante());
        values.put(COLUNA_DATA, dados.getData());

        // Inserindo o conjunto de valores na tabela
        db.insert(TABELA_CLIENTE, null, values);

        // Fechando a conexão com o banco de dados
        db.close();

    }

    void apagarDados(Dados dados){

        // Abrindo o banco de dados para escrita
        SQLiteDatabase db = this.getWritableDatabase();

        //QUERY que deleta a linha da tabela baseado no ID escolhido
        db.delete(TABELA_CLIENTE, COLUNA_ID + " = ?" +
                "", new String[] { String.valueOf(dados.getId())});
    }

    Dados selecionarDados(int id){

        // Abrindo o banco de dados para leitura
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABELA_CLIENTE, new String[] {COLUNA_ID, COLUNA_CLIENTE,
                COLUNA_VALOR_TOTAL, COLUNA_PARCELA, COLUNA_ENTRADA, COLUNA_VALOR_RESTANTE, COLUNA_DATA}, COLUNA_ID + " = ?",
                new String[] {String.valueOf(id)}, null, null, null,
                null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        Dados dados = new Dados(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),cursor.getString(2), cursor.getString(3),
                cursor.getString(4), cursor.getString(5), cursor.getString(6));

        return dados;
    }
     void atualizarDados(Dados dados) {

         SQLiteDatabase db = this.getWritableDatabase();

         ContentValues values = new ContentValues();

         values.put(COLUNA_CLIENTE, dados.getCliente());
         values.put(COLUNA_VALOR_TOTAL, dados.getValorTotal());
         values.put(COLUNA_PARCELA, dados.getParcela());
         values.put(COLUNA_ENTRADA, dados.getEntrada());
         values.put(COLUNA_VALOR_RESTANTE, dados.getValorRestante());
         values.put(COLUNA_DATA, dados.getData());

         db.update(TABELA_CLIENTE, values, COLUNA_ID + " = ?",
                 new String[]{String.valueOf(dados.getId())});

     }

     public List<Dados> listaTodos() {
         List<Dados> listaDados = new ArrayList<>();

         String query = "SELECT * FROM " + TABELA_CLIENTE;

         SQLiteDatabase db = this.getWritableDatabase();
         Cursor c = db.rawQuery(query, null);

         if (c.moveToFirst()){
            do {
                Dados dados = new Dados();
                dados.setId(Integer.parseInt(c.getString(0)));
                dados.setCliente(c.getString(1));
                dados.setValorTotal(c.getString(2));
                dados.setParcela(c.getString(3));
                dados.setEntrada(c.getString(4));
                dados.setValorRestante(c.getString(5));
                dados.setData(c.getString(6));

                listaDados.add(dados);
            } while (c.moveToNext());
         }

         return listaDados;
     }
}
