/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;



/**
 *
 * @author Flavio
 */
public class ModelVendaItem implements Serializable{
    private int quantidade;
    private ModelProduto produto;
    
    public ModelVendaItem(ModelProduto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public ModelProduto getProduto() {
        return produto;
    }

    public void setProduto(ModelProduto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
}
