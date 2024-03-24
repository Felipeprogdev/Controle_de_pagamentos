package com.example.controle_de_pagamentos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;


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

    //Método para criar a tabela no banco de dados
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    // Método para criar a tabela no banco de dados
    @Override
    public void onCreate(SQLiteDatabase db) {

        // String para a criação da tabela no banco de dados
        // A tabela é criada com colunas para ID (inteiro), cliente (texto), valor total (real),
        // parcela (real), entrada (real), valor restante (real) e data (texto)
        String QUERY_COLUNA = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY," +
                        " %s TEXT, %s REAL, %s REAL, %s REAL, %s REAL, %s TEXT)", TABELA_CLIENTE,
                COLUNA_ID, COLUNA_CLIENTE, COLUNA_VALOR_TOTAL, COLUNA_PARCELA, COLUNA_ENTRADA,
                COLUNA_VALOR_RESTANTE, COLUNA_DATA);

        // Executando a criação da tabela
        db.execSQL(QUERY_COLUNA);
    }

    // Método para adicionar um cliente ao banco de dados
    void addDados(Dados dados){

        // Abrindo o banco de dados para escrita
        SQLiteDatabase db = this.getWritableDatabase();

        // Criando um novo conjunto de valores para inserir na tabela
        ContentValues values = new ContentValues();

        // Adicionando os valores do cliente ao conjunto
        // Os valores são convertidos para string antes de serem inseridos, pois o SQLite não tem
        // tipos específicos para BigDecimal ou LocalDate
        values.put(COLUNA_CLIENTE, dados.getCliente());
        values.put(COLUNA_VALOR_TOTAL, dados.getValorTotal().toString());
        values.put(COLUNA_PARCELA, dados.getParcela().toString());
        values.put(COLUNA_ENTRADA, dados.getEntrada().toString());
        values.put(COLUNA_VALOR_RESTANTE, dados.getValorRestante().toString());
        values.put(COLUNA_DATA, dados.getData().toString());

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

    // Método para selecionar um cliente do banco de dados
    Dados selecionarDados(int id){

        //Abrindo o banco de dados para leitura
        SQLiteDatabase db = this.getReadableDatabase();

        // Executando uma consulta para selecionar um cliente com um ID específico
        Cursor cursor = db.query(TABELA_CLIENTE, new String[] {COLUNA_ID, COLUNA_CLIENTE,
                        COLUNA_VALOR_TOTAL, COLUNA_PARCELA, COLUNA_ENTRADA, COLUNA_VALOR_RESTANTE, COLUNA_DATA}, COLUNA_ID + " = ?",
                new String[] {String.valueOf(id)}, null, null, null,
                null);

        // Movendo o cursor para a primeira linha dos resultados
        if(cursor != null){
            cursor.moveToFirst();
        }

        // Criando um novo objeto Dados com os valores da linha selecionada
        // Os valores são convertidos de volta para BigDecimal e LocalDate após serem recuperados do banco de dados
        Dados dados = new Dados(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), new BigDecimal(cursor.getString(2)), new BigDecimal(cursor.getString(3)),
                new BigDecimal(cursor.getString(4)), new BigDecimal(cursor.getString(5)), LocalDate.parse(cursor.getString(6)));

        // Retornando o objeto Dados
        return dados;
    }

    // Método para atualizar os dados de um cliente no banco de dados
    void atualizarDados(Dados dados) {

        // Abrindo o banco de dados para escrita
        SQLiteDatabase db = this.getWritableDatabase();

        // Criando um novo conjunto de valores com os dados atualizados do cliente
        ContentValues values = new ContentValues();

        // Adicionando os valores atualizados do cliente ao conjunto
        values.put(COLUNA_CLIENTE, dados.getCliente());
        values.put(COLUNA_VALOR_TOTAL, dados.getValorTotal().toString());
        values.put(COLUNA_PARCELA, dados.getParcela().toString());
        values.put(COLUNA_ENTRADA, dados.getEntrada().toString());
        values.put(COLUNA_VALOR_RESTANTE, dados.getValorRestante().toString());
        values.put(COLUNA_DATA, dados.getData().toString());

        // Atualizando a linha do cliente no banco de dados
        db.update(TABELA_CLIENTE, values, COLUNA_ID + " = ?",
                new String[]{String.valueOf(dados.getId())});
    }


    // Método para listar todos os dados do banco de dados
    public List<Dados> listaTodos() {
        // Cria uma nova lista para armazenar os dados
        List<Dados> listaDados = new ArrayList<>();

        // Define a consulta SQL para selecionar todos os dados
        String query = "SELECT * FROM " + TABELA_CLIENTE;

        // Abre o banco de dados para leitura
        SQLiteDatabase db = this.getWritableDatabase();

        // Executa a consulta SQL e armazena os resultados em um Cursor
        Cursor c = db.rawQuery(query, null);

        // Verifica se o Cursor contém pelo menos um resultado
        if (c.moveToFirst()){
            // Percorre todos os resultados no Cursor
            do {
                // Cria um novo objeto Dados
                Dados dados = new Dados();

                // Define os valores do objeto Dados com os valores do resultado atual do Cursor
                // Os valores são convertidos para o tipo correto
                dados.setId(Integer.parseInt(c.getString(0)));
                dados.setCliente(c.getString(1));
                dados.setValorTotal(new BigDecimal(c.getString(2)));
                dados.setParcela(new BigDecimal(c.getString(3)));
                dados.setEntrada(new BigDecimal(c.getString(4)));
                dados.setValorRestante(new BigDecimal(c.getString(5)));
                dados.setData(LocalDate.parse(c.getString(6)));

                // Adiciona o objeto Dados à lista
                listaDados.add(dados);
            } while (c.moveToNext()); // Move para o próximo resultado no Cursor
        }

        // Retorna a lista de dados
        return listaDados;
    }

}
