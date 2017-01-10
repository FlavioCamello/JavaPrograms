/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter.cliente;

import collection.Clientes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.ModelAbstractCliente;
import model.ModelClienteFisico;
import model.ModelClienteJuridico;
import presenter.fornecedor.PresenterManterFornecedor;
import presenter.produto.PresenteManterProduto;
import view.cliente.ViewEditarCliente;
import view.cliente.ViewManterCliente;
import view.produto.ViewEditarProduto;

/**
 *
 * @author Flavio
 */
public class PresenterEditarCliente {

    private Clientes clientes;
    private ModelAbstractCliente cliente;
    private ViewEditarCliente view;

    public PresenterEditarCliente(int idProcurado) {

        view = new ViewEditarCliente();
        clientes = new Clientes();
        cliente = clientes.retornaProcurado(idProcurado);
        carregarCampos();
        bloquearCampos();
        view.getCbTipoCliente().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (((String) view.getCbTipoCliente().getSelectedItem()).equals("Jurídico")) {
                    view.getTfLimite().setText("R$ 5000.00");
                }
                if (((String) view.getCbTipoCliente().getSelectedItem()).equals("Físico")) {
                    view.getTfLimite().setText("R$ 1000.00");
                }
            }
        });
        view.getBtnSalvar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    editarCliente();
                    view.dispose();
                    PresenterManterCliente pmc = new PresenterManterCliente();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Cadastro não foi realizado com sucesso! \n Verifique se todos os campos foram preenchidos! ");
                }
            }
        });
        view.getBtnCancelar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                PresenterManterCliente pmp = new PresenterManterCliente();
            }
        });
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }

    public void editarCliente() throws Exception {

        int id = Integer.parseInt(view.getTfId().getText());
        String nome = view.getTfNome().getText();
        String endereco = view.getTfEndereco().getText();
        String telefone = view.getTfTelefone().getText();
        String documento = view.getTfDocumento().getText();
        String tipo = (String) view.getCbTipoCliente().getSelectedItem();

        if (nome.equals("") || telefone.equals("") || documento.equals("") || endereco.equals("") || tipo.equals("Tipo Cliente")) {
            throw new Exception("Preencha todos os campos!");
        }
        clientes.excluir(cliente.getId());
        if (((String) view.getCbTipoCliente().getSelectedItem()).equals("Jurídico")) {
            cliente = new ModelClienteJuridico(documento, nome, endereco, telefone, id);
        }
        if (((String) view.getCbTipoCliente().getSelectedItem()).equals("Físico")) {
            cliente = new ModelClienteFisico(documento, nome, endereco, telefone, id);
        }
        clientes.adicionarCliente(cliente);
    }

    public void carregarCampos() {
        view.getTfId().setText(String.valueOf(cliente.getId()));
        view.getTfNome().setText(cliente.getNome());
        view.getTfDocumento().setText(cliente.getDocumento());
        view.getTfTelefone().setText(cliente.getTelefone());
        view.getTfEndereco().setText(cliente.getEndereco());
        if (cliente.getLimiteVenda() == 5000.0) {
            view.getCbTipoCliente().setSelectedItem("Jurídico");
            view.getTfLimite().setText("R$ 5000.00");
        }
        if (cliente.getLimiteVenda() == 1000.0) {
            view.getCbTipoCliente().setSelectedItem("Físico");
            view.getTfLimite().setText("R$ 1000.00");
        }

    }

    private void bloquearCampos() {
        view.getTfId().setEditable(false);
        view.getTfLimite().setEditable(false);
    }
}
