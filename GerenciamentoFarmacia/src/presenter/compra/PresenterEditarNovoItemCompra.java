/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter.compra;

import collection.Compras;
import collection.Produtos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.ModelCompra;
import model.ModelCompraItem;
import model.ModelProduto;
import view.compra.ViewEditarNovoItemCompra;

/**
 *
 * @author Flavio
 */
public class PresenterEditarNovoItemCompra {

    private ModelCompra compra;
    private Compras compras;
    private ViewEditarNovoItemCompra view;
    private Produtos produtos;
    private ModelProduto produto;

    PresenterEditarNovoItemCompra(int procurado) {

        view = new ViewEditarNovoItemCompra();
        compras = new Compras();
        produtos = new Produtos();
        compra = compras.retornaProcurado(procurado);
        carregarTabelaProduto();
        view.getTbProduto().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                carregarComboBoxQuantidade();
            }
        });
        view.getBtnAdicionar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                adicionarCompraItem();
                controleEstoqueSomar();
                view.dispose();
                PresenterEditarCompra pec = new PresenterEditarCompra(procurado);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Verifique se algum item foi selecionado! ");
                }
            }
        });

        view.getBtnCancelar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
            }
        });
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }

    public void adicionarCompraItem() {
        int aux = 0;
        int produtoProcurado = Integer.parseInt((String) view.getTbProduto().getValueAt(view.getTbProduto().getSelectedRow(), 0));
        int quantidade = (int) view.getCbQuantidade().getSelectedItem();
        produto = produtos.retornaProcurado(produtoProcurado);
        //verificar a existencia ou nao do produto nesse compra
        ArrayList retornaVetor = compra.getCompraItem();
        Iterator<ModelCompraItem> itCompraItem = retornaVetor.iterator();
        ModelProduto produtoIterator = new ModelProduto();
        //esse outro while é pra rodar o array de compraItem que esta dentro de compra
        while (itCompraItem.hasNext()) {
            ModelCompraItem compraItemIterator = itCompraItem.next();
            produtoIterator = compraItemIterator.getProduto();
            //se ja estiver na lista, o produto vai receber a quantidade a mais que escolheu
            if (produto.getId() == produtoIterator.getId()) {
                compraItemIterator.setQuantidade(compraItemIterator.getQuantidade() + quantidade);
                aux = 1;
                compras.excluir(compra.getId());
                compras.adicionarCompra(compra);
                break;
            }
        }
        if (aux == 0) {
            ModelCompraItem compraItem = new ModelCompraItem(produto, quantidade);
            compra.adicionar(compraItem);
            compras.excluir(compra.getId());
            compras.adicionarCompra(compra);
        }

    }

    public void carregarTabelaProduto() {
        ArrayList retornaVetor = produtos.retornaVetor();
        DefaultTableModel tm = new DefaultTableModel( // jowtable reconhece o tableModel
                new Object[][]{},
                new String[]{"Id", "Nome", "Preço", "Estoque"}) {
                    @Override
                    public boolean isCellEditable(int row, int col) {
                        return false;
                    }
                };
        tm.setNumRows(0);
        Iterator<ModelProduto> it = retornaVetor.iterator();
        while (it.hasNext()) {
            ModelProduto produtoIterator = it.next();
            String linha = produtoIterator.toString();  //o toString tera que ter os nomes separados por virgula
            String valores[] = linha.split(","); //serve para botar uma palavra em cada posicao do vetor, pois a cada virgula pula pos
            tm.addRow(valores);
        }
        view.getTbProduto().setModel(tm);
    }

    public void controleEstoqueSomar() {
        ModelProduto produtoTemp = new ModelProduto();
        produtoTemp = produtos.retornaProcurado(Integer.parseInt((String) view.getTbProduto().getValueAt(view.getTbProduto().getSelectedRow(), 0)));

        produtoTemp.setQtdEstoque(produtoTemp.getQtdEstoque() + (int) view.getCbQuantidade().getSelectedItem());
        produtos.excluir(produtoTemp.getId());
        produtos.adicionarProduto(produtoTemp);
    }

    private void carregarComboBoxQuantidade(){
        view.getCbQuantidade().removeAllItems();
        for (int i = 1; i <= 1000; i++) {
            view.getCbQuantidade().addItem(i);
        }
    }
}
