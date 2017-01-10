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
import view.fornecedor.ViewEditarFornecedor;

/**
 *
 * @author Flavio
 */
public class PresenterEditarFornecedor {

    private Fornecedores fornecedores;
    private ModelAbstractCliente fornecedor;
    private ViewEditarFornecedor view;

    PresenterEditarFornecedor(int idProcurado) {
        view = new ViewEditarFornecedor();
        fornecedores = new Fornecedores();
        fornecedor = fornecedores.retornaProcurado(idProcurado);
        carregarCampos();
        bloquearCampos();
        view.getBtnSalvar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    editarFornecedor();
                    view.dispose();
                    PresenterManterFornecedor pmf = new PresenterManterFornecedor();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "A edição não foi realizado com sucesso! \n Verifique se todos os campos foram preenchidos! ");
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

    public void editarFornecedor() throws Exception {

        int id = Integer.parseInt(view.getTfId().getText());
        String nome = view.getTfNome().getText();
        String endereco = view.getTfEndereco().getText();
        String telefone = view.getTfTelefone().getText();
        String documento = view.getTfCnpj().getText();
        if (nome.equals("") || telefone.equals("") || documento.equals("") || endereco.equals("")) {
            throw new Exception();
        }

        fornecedores.excluir(fornecedor.getId());

        fornecedor = new ModelFornecedor(nome, endereco, telefone, documento, id);
        fornecedores.adicionarFornecedor(fornecedor);
    }

    public void carregarCampos() {
        view.getTfId().setText(String.valueOf(fornecedor.getId()));
        view.getTfNome().setText(fornecedor.getNome());
        view.getTfCnpj().setText(fornecedor.getDocumento());
        view.getTfTelefone().setText(fornecedor.getTelefone());
        view.getTfEndereco().setText(fornecedor.getEndereco());
    }

    private void bloquearCampos() {
        view.getTfId().setEditable(false);
    }
}
