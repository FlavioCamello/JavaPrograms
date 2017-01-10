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
public class ModelCompraItem implements Serializable{
    private ModelProduto produto;
    private int quantidade;

    public ModelCompraItem(ModelProduto produto, int quantidade) {
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
