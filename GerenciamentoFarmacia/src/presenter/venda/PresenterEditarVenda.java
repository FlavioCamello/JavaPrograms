/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter.venda;

import collection.Produtos;
import collection.Vendas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.ModelVendaItem;
import model.ModelProduto;
import model.ModelVenda;
import view.venda.ViewEditarVenda;

/**
 *
 * @author Flavio
 */
public class PresenterEditarVenda {

    private ModelVenda venda;
    private Vendas vendas;
    private ViewEditarVenda view;
    private Produtos produtos;
    private ModelProduto produto;

    PresenterEditarVenda(int procurado) {
        view = new ViewEditarVenda();
        vendas = new Vendas();
        produtos = new Produtos();
        venda = vendas.retornaVenda(procurado);
        carregarTabela();
        view.getBtnSalvarModificacao().setEnabled(false);
        view.getCbQuantidade().setEnabled(false);
        view.getBtnExcluir().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    excluir(procurado);
                    controleEstoqueSubtrair();
                    carregarTabela();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao excluir! \nVerifique se algum item foi selecionado!  ");
                }

            }
        });

        view.getBtnNovo().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                PresenterEditarNovoItemVenda penic = new PresenterEditarNovoItemVenda(procurado);
            }
        });

        view.getBtnEditar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                carregarComboBoxQuantidade();
                atualizarBotao();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao editar! \nVerifique se algum item foi selecionado!  ");
                }
            }
        });

        view.getBtnSalvarModificacao().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (verificarSeClienteTemCreditoParaCompra()) {
                    controleEstoqueSubtrair();
                    editar();
                    controleEstoqueSomar();

                } else {
                    JOptionPane.showMessageDialog(null, "Nao é possível adicionar pois ultrapassa o credito de " + venda.getCliente().getLimiteVenda() + " que o " + venda.getCliente().getNome() + " possui!");
                }
                carregarTabela();
                view.getBtnSalvarModificacao().setEnabled(false);
                view.getCbQuantidade().setEnabled(false);
                view.getBtnEditar().setEnabled(true);
                view.getBtnExcluir().setEnabled(true);
                view.getBtnNovo().setEnabled(true);
            }
        });

        view.getBtnVoltar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e
            ) {
                view.dispose();
                PresenterManterVenda pmc = new PresenterManterVenda();
            }
        }
        );
        view.setLocationRelativeTo(null);
        view.setVisible(true);

    }

    private void atualizarBotao() {
        view.getBtnEditar().setEnabled(false);
        view.getBtnExcluir().setEnabled(false);
        view.getBtnNovo().setEnabled(false);
        view.getBtnSalvarModificacao().setEnabled(true);
        view.getCbQuantidade().setEnabled(true);
    }

    private void excluir(int procurado) throws Exception {
        int procuradoTabela = Integer.parseInt((String) view.getTbVendaItem().getValueAt(view.getTbVendaItem().getSelectedRow(), 0));
        ModelProduto produto = new ModelProduto();
        ArrayList retornaVetor = venda.getVendaItem();
        Iterator<ModelVendaItem> itVendaItem = retornaVetor.iterator();
        //esse outro while é pra rodar o array de vendaItem que esta dentro de venda
        while (itVendaItem.hasNext()) {
            ModelVendaItem vendaItemIterator = itVendaItem.next();
            produto = vendaItemIterator.getProduto();
            if (produto.getId() == procuradoTabela) {
                venda.getVendaItem().remove(vendaItemIterator);
                //retira e coloca a nova venda no array static
                vendas.excluir(venda.getId());
                vendas.adicionarVenda(venda);
                break;
            }
        }
    }

    public boolean verificarSeClienteTemCreditoParaCompra() {
        double valorTotal = 0;
        int produtoProcurado = Integer.parseInt((String) view.getTbVendaItem().getValueAt(view.getTbVendaItem().getSelectedRow(), 0));
        int quantidade = (int) view.getCbQuantidade().getSelectedItem();
        produto = produtos.retornaProcurado(produtoProcurado);

        valorTotal = produto.getPreco() * (Integer.parseInt((String) view.getTbVendaItem().getValueAt(view.getTbVendaItem().getSelectedRow(), 2)));
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

    private void editar() {
        int procurado;
        procurado = Integer.parseInt((String) view.getTbVendaItem().getValueAt(view.getTbVendaItem().getSelectedRow(), 0));
        ArrayList retornaVetor = venda.getVendaItem();
        Iterator<ModelVendaItem> itVendaItem = retornaVetor.iterator();
        //esse outro while é pra rodar o array de vendaItem que esta dentro de venda
        while (itVendaItem.hasNext()) {
            ModelVendaItem vendaItemIterator = itVendaItem.next();
            if (procurado == vendaItemIterator.getProduto().getId()) {
                vendaItemIterator.setQuantidade((int) view.getCbQuantidade().getSelectedItem());
            }
        }
    }

    public void carregarTabela() {
        ModelProduto produto = new ModelProduto();
        ArrayList retornaVetor = venda.getVendaItem();
        DefaultTableModel tm = new DefaultTableModel( // jowtable reconhece o tableModel
                new Object[][]{},
                new String[]{"Id Produto", "Produto", "Numero de Itens ", "Valor Unitario ", "Valor"}) {
                    @Override
                    public boolean isCellEditable(int row, int col) {
                        return false;
                    }
                };
        tm.setNumRows(0);
        Iterator<ModelVendaItem> itVendaItem = retornaVetor.iterator();
        //esse outro while é pra rodar o array de vendaItem que esta dentro de venda
        while (itVendaItem.hasNext()) {
            ModelVendaItem vendaItemIterator = itVendaItem.next();
            produto = vendaItemIterator.getProduto();
            String linha = produto.getId() + "," + produto.getNome() + "," + vendaItemIterator.getQuantidade() + ", R$ " + produto.getPreco() + ", R$ " + Double.valueOf(String.format(Locale.US, "%.2f", (produto.getPreco() * vendaItemIterator.getQuantidade())));
            String valores[] = linha.split(","); //serve para botar uma palavra em cada posicao do vetor, pois a cada virgula pula pos
            tm.addRow(valores);
        }
        view.getTbVendaItem().setModel(tm);
    }

    private void carregarComboBoxQuantidade() throws Exception {
        view.getCbQuantidade().removeAllItems();
        ModelProduto produtoTemp = produtos.retornaProcurado(Integer.parseInt((String) view.getTbVendaItem().getValueAt(view.getTbVendaItem().getSelectedRow(), 0)));
        int nItens = Integer.parseInt((String) view.getTbVendaItem().getValueAt(view.getTbVendaItem().getSelectedRow(), 2));
        for (int i = 1; i <= produtoTemp.getQtdEstoque() + nItens; i++) {
            view.getCbQuantidade().addItem(i);
        }
        view.getCbQuantidade().setSelectedItem(Integer.parseInt((String) view.getTbVendaItem().getValueAt(view.getTbVendaItem().getSelectedRow(), 2)));
    }

    public void controleEstoqueSomar() {
        ModelProduto produtoTemp = produtos.retornaProcurado(Integer.parseInt((String) view.getTbVendaItem().getValueAt(view.getTbVendaItem().getSelectedRow(), 0)));
        produtoTemp.setQtdEstoque(produtoTemp.getQtdEstoque() - (int) view.getCbQuantidade().getSelectedItem());
        produtos.excluir(produtoTemp.getId());
        produtos.adicionarProduto(produtoTemp);
    }

    public void controleEstoqueSubtrair() {
        ModelProduto produtoTemp = produtos.retornaProcurado(Integer.parseInt((String) view.getTbVendaItem().getValueAt(view.getTbVendaItem().getSelectedRow(), 0)));
        produtoTemp.setQtdEstoque(produtoTemp.getQtdEstoque() + (Integer.parseInt((String) view.getTbVendaItem().getValueAt(view.getTbVendaItem().getSelectedRow(), 2))));
        produtos.excluir(produtoTemp.getId());
        produtos.adicionarProduto(produtoTemp);
    }

}
