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
public class ModelClienteJuridico extends ModelAbstractCliente{

    public ModelClienteJuridico(String cpf, String nome, String Endereco, String telefone, int id) {
        super(nome, Endereco, telefone, cpf, 5000, id);
    }
}
