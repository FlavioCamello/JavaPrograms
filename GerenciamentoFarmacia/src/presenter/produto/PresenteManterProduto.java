/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter.produto;

import java.util.Collections;
import collection.Compras;
import collection.Produtos;
import collection.Vendas;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.ModelProduto;
import view.produto.ViewManterProduto;

/**
 *
 * @author Flavio
 */
public class PresenteManterProduto {

    private ModelProduto produto;
    private Produtos produtos;
    private ViewManterProduto view;

    public PresenteManterProduto() {
        view = new ViewManterProduto();
        produtos = new Produtos();
        carregarTabelaProduto();
        view.getBtnCadastrar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                    view.dispose();
                    PresenterCadastrarProduto pcp = new PresenterCadastrarProduto();
                
            }
        });
        view.getBtnEditar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    PresenterEditarProduto pep = new PresenterEditarProduto(Integer.parseInt((String) view.getTbProduto().getValueAt(view.getTbProduto().getSelectedRow(), 0)));
                    view.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Verifique se algum item foi selecionado! ");
                }
            }
        });
        view.getBtnExcluir().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Compras compras = new Compras();
                    Vendas vendas = new Vendas();
                    int idProcurado = (Integer.parseInt((String) view.getTbProduto().getValueAt(view.getTbProduto().getSelectedRow(), 0)));
                    if (vendas.retornaSePodeExcluirProduto(idProcurado) && compras.retornaSePodeExcluirProduto(idProcurado)) {
                        excluirProduto();
                    } else {
                        JOptionPane.showMessageDialog(null, "Esse produto nao pode ser removido por ja ter sido realizado ao menos uma venda!");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Verifique se algum item, foi selecionado! ");
                }
            }
        });
        view.getBtnCancelar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
            }
        });
        view.getCbFiltroPreco().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String filtro = (String) view.getCbFiltroPreco().getSelectedItem();
                carregarTabelaProdutoFiltradaPreco(filtro);
            }
        });
        view.getBtnPesquisar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                carregarTabelaProdutoPesquisa(view.getTfPesquisar().getText());
            }
        });
        Dimension screenSize = view.getToolkit().getScreenSize();  
        view.setSize(screenSize);
        view.setVisible(true);
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

    public void excluirProduto() {
        produtos.excluir(Integer.parseInt((String) view.getTbProduto().getValueAt(view.getTbProduto().getSelectedRow(), 0)));
        view.dispose();
        PresenteManterProduto pmp = new PresenteManterProduto();
    }

    public void carregarTabelaProdutoFiltradaPreco(String filtro) {
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
            String linha = null;
            if (filtro.equals("Até R$ 100.00")) {
                if (produtoIterator.getPreco() <= 100.00) {
                    linha = produtoIterator.toString();
                    String valores[] = linha.split(","); //serve para botar uma palavra em cada posicao do vetor, pois a cada virgula pula pos
                    tm.addRow(valores);
                }
            }
            if (filtro.equals("De R$ 100.00 a R$ 500.00")) {
                if (produtoIterator.getPreco() > 100.00 && produtoIterator.getPreco() <= 500.00) {
                    linha = produtoIterator.toString();
                    String valores[] = linha.split(","); //serve para botar uma palavra em cada posicao do vetor, pois a cada virgula pula pos
                    tm.addRow(valores);
                }
            }
            if (filtro.equals("De R$ 500.00 a R$ 1000.00")) {
                if (produtoIterator.getPreco() > 500.00 && produtoIterator.getPreco() <= 1000.00) {
                    linha = produtoIterator.toString();
                    String valores[] = linha.split(","); //serve para botar uma palavra em cada posicao do vetor, pois a cada virgula pula pos
                    tm.addRow(valores);
                }
            }
            if (filtro.equals("Acima de 1000.00")) {
                if (produtoIterator.getPreco() > 1000.00) {
                    linha = produtoIterator.toString();
                    String valores[] = linha.split(","); //serve para botar uma palavra em cada posicao do vetor, pois a cada virgula pula pos
                    tm.addRow(valores);
                }
            }
            if (filtro.equals("Todos os valores")) {
                linha = produtoIterator.toString();
                String valores[] = linha.split(","); //serve para botar uma palavra em cada posicao do vetor, pois a cada virgula pula pos
                tm.addRow(valores);
            }
        }
        view.getTbProduto().setModel(tm);
    }

    public void carregarTabelaProdutoPesquisa(String nome) {
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
            if (produtoIterator.getNome().contains(nome)) {
                String linha = produtoIterator.toString();  //o toString tera que ter os nomes separados por virgula
                String valores[] = linha.split(","); //serve para botar uma palavra em cada posicao do vetor, pois a cada virgula pula pos
                tm.addRow(valores);
            }

        }
        view.getTbProduto().setModel(tm);
    }
}
