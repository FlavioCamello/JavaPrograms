/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter.venda;

import collection.Vendas;
import collection.Produtos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.ModelVenda;
import model.ModelVendaItem;
import model.ModelProduto;
import view.venda.ViewEditarNovoItemVenda;

/**
 *
 * @author Flavio
 */
public class PresenterEditarNovoItemVenda {

    private ModelVenda venda;
    private Vendas vendas;
    private ViewEditarNovoItemVenda view;
    private Produtos produtos;
    private ModelProduto produto;

    PresenterEditarNovoItemVenda(int procurado) {
        view = new ViewEditarNovoItemVenda();
        vendas = new Vendas();
        produtos = new Produtos();
        venda = vendas.retornaVenda(procurado);
        carregarTabelaProduto();
        view.getTbProduto().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                carregarComboBoxQuantidade();
            }
        });
        view.getBtnAdicionar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (verificarSeClienteTemCreditoParaCompra()) {
                        adicionarVendaItem();
                        controleEstoqueSubtrair();
                        view.dispose();
                        PresenterEditarVenda pec = new PresenterEditarVenda(procurado);

                    } else {
                        JOptionPane.showMessageDialog(null, "Nao é possível adicionar pois ultrapassa o credito de " + venda.getCliente().getLimiteVenda() + " que o " + venda.getCliente().getNome() + " possui!");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Verifique se algum item foi selecionado! ");
                }
            }
        });

        view.getBtnVoltar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                PresenterEditarVenda pec = new PresenterEditarVenda(procurado);

            }
        });
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }

    public boolean verificarSeClienteTemCreditoParaCompra() {
        double valorTotal = 0;
        int produtoProcurado = Integer.parseInt((String) view.getTbProduto().getValueAt(view.getTbProduto().getSelectedRow(), 0));
        int quantidade = (int) view.getCbQuantidade().getSelectedItem();
        produto = produtos.retornaProcurado(produtoProcurado);

        valorTotal = quantidade * produto.getPreco();

        ArrayList retornaVetorVendaItem = venda.getVendaItem();
        Iterator<ModelVendaItem> itVendaItem = retornaVetorVendaItem.iterator();
        while (itVendaItem.hasNext()) {
            ModelVendaItem vendaItemIterator = itVendaItem.next();
            valorTotal = valorTotal + vendaItemIterator.getProduto().getPreco();
        }
        if (valorTotal < venda.getCliente().getLimiteVenda()) {
            return true;
        } else {
            return false;
        }
    }

    public void adicionarVendaItem() {
        int aux = 0;
        int produtoProcurado = Integer.parseInt((String) view.getTbProduto().getValueAt(view.getTbProduto().getSelectedRow(), 0));
        int quantidade = (int) view.getCbQuantidade().getSelectedItem();
        produto = produtos.retornaProcurado(produtoProcurado);
        //verificar a existencia ou nao do produto nesse venda
        ArrayList retornaVetor = venda.getVendaItem();
        Iterator<ModelVendaItem> itVendaItem = retornaVetor.iterator();
        ModelProduto produtoIterator = new ModelProduto();
        //esse outro while é pra rodar o array de vendaItem que esta dentro de venda
        while (itVendaItem.hasNext()) {
            ModelVendaItem vendaItemIterator = itVendaItem.next();
            produtoIterator = vendaItemIterator.getProduto();
            //se ja estiver na lista, o produto vai receber a quantidade a mais que escolheu
            if (produto.getId() == produtoIterator.getId()) {
                vendaItemIterator.setQuantidade(vendaItemIterator.getQuantidade() + quantidade);
                aux = 1;
                vendas.excluir(venda.getId());
                vendas.adicionarVenda(venda);
                break;
            }
        }
        if (aux == 0) {
            ModelVendaItem vendaItem = new ModelVendaItem(produto, quantidade);
            venda.adicionar(vendaItem);
            vendas.excluir(venda.getId());
            vendas.adicionarVenda(venda);
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

    public void controleEstoqueSubtrair() {

        ModelProduto produtoTemp = new ModelProduto();
        produtoTemp = produtos.retornaProcurado(Integer.parseInt((String) view.getTbProduto().getValueAt(view.getTbProduto().getSelectedRow(), 0)));

        produtoTemp.setQtdEstoque(produtoTemp.getQtdEstoque() - (int) view.getCbQuantidade().getSelectedItem());
        produtos.excluir(produtoTemp.getId());
        produtos.adicionarProduto(produtoTemp);
    }

    private void carregarComboBoxQuantidade() {
        view.getCbQuantidade().removeAllItems();
        ModelProduto produtoTemp = produtos.retornaProcurado(Integer.parseInt((String) view.getTbProduto().getValueAt(view.getTbProduto().getSelectedRow(), 0)));

        for (int i = 1; i <= produtoTemp.getQtdEstoque(); i++) {
            view.getCbQuantidade().addItem(i);
        }
    }

}
