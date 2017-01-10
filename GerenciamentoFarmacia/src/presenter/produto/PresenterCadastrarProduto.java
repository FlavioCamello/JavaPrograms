/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter.produto;

import collection.Produtos;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.ModelProduto;
import view.produto.ViewCadastrarProduto;

/**
 *
 * @author Flavio
 */
public class PresenterCadastrarProduto {

    private ModelProduto produto;
    private Produtos produtos;
    private ViewCadastrarProduto view;

    public PresenterCadastrarProduto() {
        view = new ViewCadastrarProduto();
        produtos = new Produtos();
        view.getBtnSalvar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    cadastrarProduto();
                    PresenteManterProduto pmp = new PresenteManterProduto();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Cadastro não foi realizado com sucesso!" + ex.getMessage());
                }
            }
        });
        view.getBtnCancelar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                PresenteManterProduto pmp = new PresenteManterProduto();
            }
        });
        view.getTfPreco().setText("0,00");
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }

    public void cadastrarProduto() throws Exception {
        String nome = view.getTfNome().getText();
        double preco = Double.parseDouble(view.getTfPreco().getText().replace(",", "."));
        if (nome.equals("") || preco == 0) {
            throw new Exception("Preencha todos os campos!");
        }
        ModelProduto mp = new ModelProduto(nome, preco, produtos.getProxIdProduto());
        Produtos ps = new Produtos();
        ps.adicionarProduto(mp);
        view.dispose();

    }

}
