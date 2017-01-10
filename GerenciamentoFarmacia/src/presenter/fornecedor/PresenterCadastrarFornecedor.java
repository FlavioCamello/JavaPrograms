/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter.fornecedor;

import collection.Fornecedores;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.ModelAbstractCliente;

import model.ModelFornecedor;
import view.fornecedor.ViewCadastrarFornecedor;

/**
 *
 * @author Flavio
 */
public class PresenterCadastrarFornecedor {

    private ViewCadastrarFornecedor view;
    private ModelAbstractCliente fornecedor;
    private Fornecedores fornecedores;

    public PresenterCadastrarFornecedor() {
        view = new ViewCadastrarFornecedor();
        view.getBtnSalvar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    cadastrar();
                    view.dispose();
                    PresenterManterFornecedor pmf = new PresenterManterFornecedor();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Cadastro não foi realizado com sucesso! \n Verifique se todos os campos foram preenchidos! ");
                }
            }
        });
        view.getBtnCancelar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                PresenterManterFornecedor pmf = new PresenterManterFornecedor();
            }
        });
         view.setLocationRelativeTo(null);
        view.setVisible(true);

    }

    public void cadastrar() throws Exception{
        String nome = (String) view.getTfNome().getText();
        String telefone = (String) view.getTfTelefone().getText();
        String documento = (String) view.getTfCnpj().getText();
        String endereco = (String) view.getTfEndereco().getText();
        if(nome.equals("") || telefone.equals("") || documento.equals("") || endereco.equals("")) {
            throw new Exception("Preencha todos os campos!");
        } 
        fornecedores = new Fornecedores();
        fornecedor = new ModelFornecedor(nome, endereco, telefone, documento, fornecedores.getProxIdCliente());
        fornecedores.adicionarFornecedor(fornecedor);
    }
}
