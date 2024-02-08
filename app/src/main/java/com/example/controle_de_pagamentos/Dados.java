package com.example.controle_de_pagamentos;

//Classe construtora
public class Dados {

    int id;
    String cliente;
    String valorTotal;
    String parcela;
    String entrada;
    String valorRestante;
    String data;


    //Construtor vazio de instancia
    public Dados(){

    }

    //Construtor de update
    public Dados(int _id, String _cliente, String _valorTotal, String _parcela, String _entrada, String _restante, String _data){
        this.id = _id;
        this.cliente = _cliente;
        this.valorTotal = _valorTotal;
        this.parcela = _parcela;
        this.entrada = _entrada;
        this.valorRestante = _restante;
        this.data = _data;
    }

    //Construtor de cadastro (Insert)
    public Dados(String _cliente, String _valorTotal, String _parcela, String _entrada, String _restante, String _data){
        this.cliente = _cliente;
        this.valorTotal = _valorTotal;
        this.parcela = _parcela;
        this.entrada = _entrada;
        this.valorRestante = _restante;
        this.data = _data;
    }

    //Geters e Setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getParcela() {
        return parcela;
    }

    public void setParcela(String parcela) {
        this.parcela = parcela;
    }

    public String getEntrada() {
        return entrada;
    }

    public void setEntrada(String entrada) {
        this.entrada = entrada;
    }

    public String getValorRestante() {
        return valorRestante;
    }

    public void setValorRestante(String valorRestante) {
        this.valorRestante = valorRestante;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
