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
import view.cliente.ViewCadastrarCliente;
import view.cliente.ViewManterCliente;

/**
 *
 * @author Flavio
 */
public class PresenterCadastrarCliente {

    private ViewCadastrarCliente view;
    private Clientes clientes;
    private ModelAbstractCliente cliente;

    public PresenterCadastrarCliente() {
        view = new ViewCadastrarCliente();
        clientes = new Clientes();
        view.getBtnSalvar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    cadastrar();
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
                PresenterManterCliente pmc = new PresenterManterCliente();
            }
        });
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }

    public void cadastrar() throws Exception {
        String nome = (String) view.getTfNome().getText();
        String telefone = (String) view.getTfTelefone().getText();
        String documento = (String) view.getTfDocumento().getText();
        String endereco = (String) view.getTfEndereco().getText();
        String tipo = (String) view.getCbTipoCliente().getSelectedItem();
        if (nome.equals("") || telefone.equals("") || documento.equals("") || endereco.equals("") || tipo.equals("Tipo Cliente")) {
            throw new Exception("Preencha todos os campos!");
        }
        if (((String) view.getCbTipoCliente().getSelectedItem()).equals("Jurídico")) {
            cliente = new ModelClienteJuridico(documento, nome, endereco, telefone, clientes.getProxIdCliente());
            clientes.adicionarCliente(cliente);
        }
        if (((String) view.getCbTipoCliente().getSelectedItem()).equals("Físico")) {
            cliente = new ModelClienteFisico(documento, nome, endereco, telefone, clientes.getProxIdCliente());
            clientes.adicionarCliente(cliente);
        }

    }

}
