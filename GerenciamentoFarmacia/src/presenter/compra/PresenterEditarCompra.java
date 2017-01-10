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
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.ModelCompra;
import model.ModelCompraItem;
import model.ModelProduto;
import view.compra.ViewEditarCompra;

/**
 *
 * @author Flavio
 */
public class PresenterEditarCompra {

    private ModelCompra compra;
    private Compras compras;
    private ViewEditarCompra view;
    private Produtos produtos;
    private ModelProduto produto;

    public PresenterEditarCompra(int procurado) {
        view = new ViewEditarCompra();
        compras = new Compras();
        produtos = new Produtos();
        compra = compras.retornaProcurado(procurado);
        carregarTabela();
        view.getBtnSalvarModificacao().setEnabled(false);
        view.getCbQuantidade().setEnabled(false);
        view.getBtnExcluir().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    excluir(procurado);
                    carregarTabela();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Verifique se algum item foi selecionado! ");
                }
            }
        });

        view.getBtnNovo().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                PresenterEditarNovoItemCompra penic = new PresenterEditarNovoItemCompra(procurado);
            }
        });

        view.getBtnEditar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    compra = compras.retornaProcurado(procurado);

                    carregarComboBoxQuantidade();
                    atualizarBotoes();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Verifique se algum item foi selecionado! ");
                }
            }
        });

        view.getBtnSalvarModificacao().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    compra = compras.retornaProcurado(procurado);
                    controleEstoqueSubtrair();
                    editar();
                    controleEstoqueSomar();
                    carregarTabela();
                    view.getBtnSalvarModificacao().setEnabled(false);
                    view.getCbQuantidade().setEnabled(false);
                    view.getBtnEditar().setEnabled(true);
                    view.getBtnExcluir().setEnabled(true);
                    view.getBtnNovo().setEnabled(true);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Problema ao editar! \n Verifique se algum item foi selecionado! ");
                }
            }
        });

        view.getBtnVoltar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                PresenterManterCompra pmc = new PresenterManterCompra();
            }
        });
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }

    private void atualizarBotoes() {
        view.getBtnEditar().setEnabled(false);
        view.getBtnExcluir().setEnabled(false);
        view.getBtnNovo().setEnabled(false);
        view.getBtnSalvarModificacao().setEnabled(true);
        view.getCbQuantidade().setEnabled(true);
    }

    private void excluir(int procurado) throws Exception {
        compra = compras.retornaProcurado(procurado);
        int procuradoTabela = Integer.parseInt((String) view.getTbCompraItem().getValueAt(view.getTbCompraItem().getSelectedRow(), 0));
        ModelProduto produto = new ModelProduto();
        ArrayList retornaVetor = compra.getCompraItem();
        Iterator<ModelCompraItem> itCompraItem = retornaVetor.iterator();
        //esse outro while é pra rodar o array de compraItem que esta dentro de compra
        while (itCompraItem.hasNext()) {
            ModelCompraItem compraItemIterator = itCompraItem.next();
            produto = compraItemIterator.getProduto();
            produto = produtos.retornaProcurado(produto.getId());
            if (produto.getId() == procuradoTabela) {
                if (produto.getQtdEstoque() >= Integer.parseInt((String) view.getTbCompraItem().getValueAt(view.getTbCompraItem().getSelectedRow(), 2))) {
                    compra.getCompraItem().remove(compraItemIterator);
                    //retira e coloca a nova compra no array static
                    compras.excluir(compra.getId());
                    compras.adicionarCompra(compra);
                    controleEstoqueSubtrair();
                    break;
                } else {
                    JOptionPane.showMessageDialog(null, "Esse item da compra, nao pode ser removido por ja ter sido vendido produtos dessa compra!");
                }
            }
        }
    }

    private void editar() throws Exception {
        int procurado;
        procurado = Integer.parseInt((String) view.getTbCompraItem().getValueAt(view.getTbCompraItem().getSelectedRow(), 0));
        ArrayList retornaVetor = compra.getCompraItem();
        Iterator<ModelCompraItem> itCompraItem = retornaVetor.iterator();
        //esse outro while é pra rodar o array de compraItem que esta dentro de compra
        while (itCompraItem.hasNext()) {
            ModelCompraItem compraItemIterator = itCompraItem.next();
            if (procurado == compraItemIterator.getProduto().getId()) {
                compraItemIterator.setQuantidade((int) view.getCbQuantidade().getSelectedItem());
            }
        }
    }

    public void carregarTabela() {
        ModelProduto produto = new ModelProduto();
        ArrayList retornaVetor = compra.getCompraItem();
        DefaultTableModel tm = new DefaultTableModel( // jowtable reconhece o tableModel
                new Object[][]{},
                new String[]{"Id Produto", "Produto", "Numero de Itens ", "Valor Unitario ", "Valor"}) {
                    @Override
                    public boolean isCellEditable(int row, int col) {
                        return false;
                    }
                };
        tm.setNumRows(0);
        Iterator<ModelCompraItem> itCompraItem = retornaVetor.iterator();
        //esse outro while é pra rodar o array de compraItem que esta dentro de compra
        while (itCompraItem.hasNext()) {
            ModelCompraItem compraItemIterator = itCompraItem.next();
            produto = compraItemIterator.getProduto();
            String linha = produto.getId() + "," + produto.getNome() + "," + compraItemIterator.getQuantidade() + ", R$ "
                    + produto.getPreco() + ", R$ " + Double.valueOf(String.format(Locale.US, "%.2f", produto.getPreco() * compraItemIterator.getQuantidade()));
            String valores[] = linha.split(","); //serve para botar uma palavra em cada posicao do vetor, pois a cada virgula pula pos
            tm.addRow(valores);
        }
        view.getTbCompraItem().setModel(tm);
    }

    private void carregarComboBoxQuantidade() throws Exception {
        view.getCbQuantidade().removeAllItems();
        ModelProduto produtoTemp = produtos.retornaProcurado(Integer.parseInt((String) view.getTbCompraItem().getValueAt(view.getTbCompraItem().getSelectedRow(), 0)));
        int numMinimo = ((Integer.parseInt((String) view.getTbCompraItem().getValueAt(view.getTbCompraItem().getSelectedRow(), 2))) - produtoTemp.getQtdEstoque());
        if (numMinimo <= 0) {
            numMinimo = 1;
        }
        for (int i = numMinimo; i <= 1000; i++) {
            view.getCbQuantidade().addItem(i);
        }
        view.getCbQuantidade().setSelectedItem(Integer.parseInt((String) view.getTbCompraItem().getValueAt(view.getTbCompraItem().getSelectedRow(), 2)));

    }

    public void controleEstoqueSomar() {
        ModelProduto produtoTemp = produtos.retornaProcurado(Integer.parseInt((String) view.getTbCompraItem().getValueAt(view.getTbCompraItem().getSelectedRow(), 0)));
        produtoTemp.setQtdEstoque(produtoTemp.getQtdEstoque() + (int) view.getCbQuantidade().getSelectedItem());
        produtos.excluir(produtoTemp.getId());
        produtos.adicionarProduto(produtoTemp);
    }

    public void controleEstoqueSubtrair() {
        ModelProduto produtoTemp = produtos.retornaProcurado(Integer.parseInt((String) view.getTbCompraItem().getValueAt(view.getTbCompraItem().getSelectedRow(), 0)));
        produtoTemp.setQtdEstoque(produtoTemp.getQtdEstoque() - (Integer.parseInt((String) view.getTbCompraItem().getValueAt(view.getTbCompraItem().getSelectedRow(), 2))));
        produtos.excluir(produtoTemp.getId());
        produtos.adicionarProduto(produtoTemp);
    }

}
