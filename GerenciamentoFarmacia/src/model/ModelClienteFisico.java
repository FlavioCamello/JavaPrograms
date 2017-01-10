/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Flavio
 */
public class ModelClienteFisico extends ModelAbstractCliente{

    public ModelClienteFisico(String cpf, String nome, String Endereco, String telefone, int id) {
        super(nome, Endereco, telefone, cpf, 1000, id);
    }   
}
