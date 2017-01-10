/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Flavio
 */
public class ModelCompra implements Serializable{
    private int id;
    private ModelAbstractCliente fornecedor;
    private ArrayList<ModelCompraItem> compraItem = new ArrayList<>();
    private Date data;

    public ModelCompra() {
    }
    
    public ModelCompra(int id, ModelAbstractCliente fornecedor, Date data) {
        this.id = id;
        this.fornecedor = fornecedor;        
        this.data = data; 
    }

    public ModelCompra(int id, ModelAbstractCliente fornecedor, ArrayList<ModelCompraItem> compraItem, Date data) {
        this.id = id;
        this.fornecedor = fornecedor;
        this.compraItem.addAll(compraItem);
        this.data = data; 
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
        
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ModelAbstractCliente getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(ModelFornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public ArrayList<ModelCompraItem> getCompraItem() {
        return compraItem;
    }

    public void setCompraItem(ArrayList<ModelCompraItem> compraItem) {
        this.compraItem = compraItem;
    }
    
    public void adicionar(ModelCompraItem compraItem) {
        this.compraItem.add(compraItem);
    }
    
}
