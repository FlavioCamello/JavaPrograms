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
abstract public class ModelAbstractCliente implements Serializable{
    private int id;
    private String nome;
    private String Endereco;
    private String telefone;
    private String documento;
    private double limiteVenda;

    public ModelAbstractCliente(String nome, String Endereco, String telefone, String documento, double limiteVenda, int id) {
        this.id = id;
        this.nome = nome;
        this.Endereco = Endereco;
        this.telefone = telefone;
        this.documento = documento;
        this.limiteVenda = limiteVenda;        
    }
    
    public ModelAbstractCliente(String nome, String Endereco, String telefone, String documento, int id) {
        this.id = id;
        this.nome = nome;
        this.Endereco = Endereco;
        this.telefone = telefone;
        this.documento = documento;       
    }

    @Override
    public String toString() { 
        return   id + "," + nome + ","+ Endereco + ","+ telefone + "," + documento + ","+ Double.valueOf(String.format(Locale.US, "%.2f", limiteVenda));
    }
    
    public String toStringFornecedor() {
        return   id + "," + nome + ","+ Endereco + ","+ telefone + "," + documento;
    }
    public double getLimiteVenda() {
        return limiteVenda;
    }

    public void setLimiteVenda(double limiteVenda) {
        this.limiteVenda = limiteVenda;
    }
    
    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
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

    public String getEndereco() {
        return Endereco;
    }

    public void setEndereco(String Endereco) {
        this.Endereco = Endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
}
