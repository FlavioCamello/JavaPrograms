/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Locale;

/**
 *
 * @author Flavio
 */
public class ModelProduto implements Serializable {

    private int id;
    private String nome;
    private double preco;
    private int qtdEstoque;

    public ModelProduto(String nome, double preco, int id) {
        this.nome = nome;
        this.preco = preco;
        this.qtdEstoque = 0;
        this.id = id;
    }

    //esse construtor é para a colecao produtos
    public ModelProduto(int id, String nome, double preco, int qtdEstoque) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.qtdEstoque = qtdEstoque;
    }

    public ModelProduto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return Double.valueOf(String.format(Locale.US, "%.2f", preco));
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQtdEstoque() {
        return qtdEstoque;
    }

    public void setQtdEstoque(int qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    @Override
    public String toString() {
        return this.id + "," + nome + ", R$ " + Double.valueOf(String.format(Locale.US, "%.2f", preco)) + "," + qtdEstoque;
    }

}
