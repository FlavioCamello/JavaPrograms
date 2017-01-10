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
public class ModelVenda implements Serializable{
    private int id;
    private ArrayList<ModelVendaItem> vendaItem = new ArrayList<>();
    private ModelFuncionario funcionario; 
    private double desconto;
    private ModelAbstractCliente cliente;
    private Date data;

    public ModelVenda() {
    }
    
    public ModelVenda(int id, ModelFuncionario funcionario, double desconto, ModelAbstractCliente cliente, Date data, ArrayList<ModelVendaItem> vendaItem) {
        this.id = id;
        this.funcionario = funcionario;
        this.desconto = desconto;
        this.cliente = cliente;
        this.data = data;
        this.vendaItem = vendaItem;
    }
    
    public ModelVenda(int id, ModelFuncionario funcionario, double desconto, ModelAbstractCliente cliente, Date data) {
        this.id = id;
        this.funcionario = funcionario;
        this.desconto = desconto;
        this.cliente = cliente;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<ModelVendaItem> getVendaItem() {
        return vendaItem;
    }

    public void setVendaItem(ArrayList<ModelVendaItem> vendaItem) {
        this.vendaItem = vendaItem;
    }

    public ModelFuncionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(ModelFuncionario funcionario) {
        this.funcionario = funcionario;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public ModelAbstractCliente getCliente() {
        return cliente;
    }

    public void setCliente(ModelAbstractCliente cliente) {
        this.cliente = cliente;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
    
    public void adicionar(ModelVendaItem vendaItem) {
        this.vendaItem.add(vendaItem);
    }
    
}
