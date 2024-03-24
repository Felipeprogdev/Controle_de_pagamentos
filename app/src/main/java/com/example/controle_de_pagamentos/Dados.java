package com.example.controle_de_pagamentos;

import java.math.BigDecimal;
import java.time.LocalDate;


//Classe construtora
public class Dados {

    //Define cada item e seu tipo que será salvo no banco de dados
    int id;
    String cliente;
    BigDecimal valorTotal;
    BigDecimal parcela;
    BigDecimal entrada;
    BigDecimal valorRestante;
    LocalDate data;

    //Construtor vazio de instancia
    public Dados(){

    }

    //Construtor de update
    public Dados(int _id, String _cliente, BigDecimal _valorTotal, BigDecimal _parcela, BigDecimal _entrada, BigDecimal _restante, LocalDate _data){
        this.id = _id;
        this.cliente = _cliente;
        this.valorTotal = _valorTotal;
        this.parcela = _parcela;
        this.entrada = _entrada;
        this.valorRestante = _restante;
        this.data = _data;
    }

    //Construtor de cadastro (Insert)
    public Dados(String _cliente, BigDecimal _valorTotal, BigDecimal _parcela, BigDecimal _entrada, BigDecimal _restante, LocalDate _data){
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

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getParcela() {
        return parcela;
    }

    public void setParcela(BigDecimal parcela) {
        this.parcela = parcela;
    }

    public BigDecimal getEntrada() {
        return entrada;
    }

    public void setEntrada(BigDecimal entrada) {
        this.entrada = entrada;
    }

    public BigDecimal getValorRestante() {
        return valorRestante;
    }

    public void setValorRestante(BigDecimal valorRestante) {
        this.valorRestante = valorRestante;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}
