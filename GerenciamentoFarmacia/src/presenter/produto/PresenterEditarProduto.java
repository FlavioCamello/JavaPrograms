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
import view.produto.ViewEditarProduto;

/**
 *
 * @author Flavio
 */
public class PresenterEditarProduto {
       private ModelProduto produto;
    private Produtos produtos;
    private ViewEditarProduto view;

    public PresenterEditarProduto(int idProcurado) {
        view = new ViewEditarProduto();
        produtos = new Produtos();
        produto = produtos.retornaProcurado(idProcurado);
        carregarCampos();
        bloquearCampos();
        view.getBtnSalvar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            try{
                editarProduto();
                view.dispose();
                PresenteManterProduto pmp = new PresenteManterProduto();
            } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Ediçao não foi realizado com sucesso! \n Verifique se os campos estao preenchidos");
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
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }
    public void editarProduto() throws Exception{
        
        String nome = view.getTfNome().getText();
        double preco = Double.parseDouble(view.getTfPreco().getText());
        if (nome.equals("") || preco == 0) {
            throw new Exception();
        }
        produtos.excluir(produto.getId());
        ModelProduto mp = new ModelProduto(produto.getId(), nome, preco, produto.getQtdEstoque());
        Produtos ps = new Produtos();
        ps.adicionarProduto(mp);
    }
    
    public void carregarCampos(){
        view.getTfId().setText(String.valueOf(produto.getId()));
        view.getTfNome().setText(produto.getNome());
        view.getTfPreco().setText(String.valueOf(produto.getPreco()));
        view.getTfEstoque().setText(String.valueOf(produto.getQtdEstoque()));
    }
    
    private void bloquearCampos() {
        view.getTfId().setEditable(false);
        view.getTfEstoque().setEditable(false);
    }
}
