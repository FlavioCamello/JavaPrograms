/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter.venda;

import collection.Produtos;
import collection.Vendas;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.ModelProduto;
import model.ModelVenda;
import model.ModelVendaItem;
import view.venda.ViewManterVenda;

/**
 *
 * @author Flavio
 */
public class PresenterManterVenda {

    private ModelVenda venda;
    private Vendas vendas;
    private ViewManterVenda view;

    public PresenterManterVenda() {
        vendas = new Vendas();
        view = new ViewManterVenda();
        carregarTabela();

        view.getCbFiltrar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                carregarTabelaVendaFiltradaPreco((String) view.getCbFiltrar().getSelectedItem());
            }
        });

        view.getBtnNovaCompra().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                PresenterCadastrarVenda pcv = new PresenterCadastrarVenda();
            }
        });

        view.getBtnExcluir().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controleEstoqueSubtrair();
                    excluir();
                    carregarTabela();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Verifique se algum item foi selecionado!  ");
                }
            }
        });

        view.getBtnVisualizar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                   
                    PresenterVisualizarVenda pvv = new PresenterVisualizarVenda(Integer.parseInt((String) view.getTbVenda().getValueAt(view.getTbVenda().getSelectedRow(), 0)));
                    view.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Verifique se algum item foi selecionado!  ");
                }
            }
        });

        view.getBtnEditar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {                    
                    PresenterEditarVenda pvv = new PresenterEditarVenda(Integer.parseInt((String) view.getTbVenda().getValueAt(view.getTbVenda().getSelectedRow(), 0)));
                    view.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Verifique se algum item foi selecionado!  ");
                }
            }
        });

        view.getBtnSair().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
            }
        });
        Dimension screenSize = view.getToolkit().getScreenSize();  
        view.setSize(screenSize);
        view.setVisible(true);
    }

    public void controleEstoqueSubtrair() {
        Produtos produtos = new Produtos();
        ArrayList retornaVetor = vendas.retornaVetor();
        Iterator<ModelVenda> itVenda = retornaVetor.iterator();
        while (itVenda.hasNext()) {
            ModelVenda vendaIterator = itVenda.next();
            if (vendaIterator.getId() == (Integer.parseInt((String) view.getTbVenda().getValueAt(view.getTbVenda().getSelectedRow(), 0)))) {
                ArrayList retornaVetorVendaItem = vendaIterator.getVendaItem();
                Iterator<ModelVendaItem> itVendaItem = retornaVetorVendaItem.iterator();
                //esse outro while é pra rodar o array de compraItem que esta dentro de compra
                while (itVendaItem.hasNext()) {
                    ModelProduto produtoIterator = new ModelProduto();
                    ModelVendaItem vendaItemIterator = itVendaItem.next();
                    produtoIterator = produtos.retornaProcurado(vendaItemIterator.getProduto().getId());
                    produtoIterator.setQtdEstoque(vendaItemIterator.getProduto().getQtdEstoque());
                    produtos.excluir(produtoIterator.getId());
                    produtos.adicionarProduto(produtoIterator);
                }
            }
        }
    }

    public void excluir() throws Exception {
        int procurado = Integer.parseInt((String) view.getTbVenda().getValueAt(view.getTbVenda().getSelectedRow(), 0));
        vendas.excluir(procurado);
    }

    public void carregarTabela() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        ArrayList retornaVetor = vendas.retornaVetor();
        int numeroItens = 0;
        double valorTotal = 0;
        DefaultTableModel tm = new DefaultTableModel( // jowtable reconhece o tableModel
                new Object[][]{},
                new String[]{"Id", "Numero de Itens", "Valor sem desconto", "Desconto", "Valor Final", "Funcionario", "Cliente", "Data"}) {
                    @Override
                    public boolean isCellEditable(int row, int col) {
                        return false;
                    }
                };
        tm.setNumRows(0);
        Iterator<ModelVenda> itVenda = retornaVetor.iterator();
        while (itVenda.hasNext()) {
            ModelVenda vendaIterator = itVenda.next();
            ArrayList retornaVetorVendaItem = vendaIterator.getVendaItem();
            Iterator<ModelVendaItem> itVendaItem = retornaVetorVendaItem.iterator();
            //esse outro while é pra rodar o array de compraItem que esta dentro de compra
            while (itVendaItem.hasNext()) {
                ModelVendaItem vendaItemIterator = itVendaItem.next();
                numeroItens = numeroItens + vendaItemIterator.getQuantidade();
                ModelProduto produto = new ModelProduto();
                produto = vendaItemIterator.getProduto();
                valorTotal = valorTotal + (produto.getPreco() * vendaItemIterator.getQuantidade());
            }
            String linha = vendaIterator.getId() + "," + numeroItens + ", R$ " + Double.valueOf(String.format(Locale.US, "%.2f", valorTotal)) + "," + padronizarDesconto(vendaIterator.getDesconto()) + "," + Double.valueOf(String.format(Locale.US, "%.2f", (valorTotal - (valorTotal * vendaIterator.getDesconto())))) + ","
                    + vendaIterator.getFuncionario().getNome() + "," + vendaIterator.getCliente().getNome() + "," + sdf.format(vendaIterator.getData());
            String valores[] = linha.split(","); //serve para botar uma palavra em cada posicao do vetor, pois a cada virgula pula pos
            tm.addRow(valores);
            valorTotal = 0;
            numeroItens = 0;
        }
        view.getTbVenda().setModel(tm);
    }

    public String padronizarDesconto(double desconto) {
        if (desconto == 0.1) {
            return "10%";
        }
        if (desconto == 0.2) {
            return "20%";
        }
        if (desconto == 0.3) {
            return "30%";
        }
        if (desconto == 0.4) {
            return "40%";
        }
        if (desconto == 0.5) {
            return "50%";
        }
        if (desconto == 0.6) {
            return "60%";
        }
        if (desconto == 0.7) {
            return "70%";
        }
        if (desconto == 0.8) {
            return "80%";
        }
        if (desconto == 0.9) {
            return "90%";
        }
        if (desconto == 1) {
            return "100%";
        }
        return "0%";
    }

    public void carregarTabelaVendaFiltradaPreco(String filtro) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        ArrayList retornaVetor = vendas.retornaVetor();
        int numeroItens = 0;
        double valorTotal = 0;
        DefaultTableModel tm = new DefaultTableModel( // jowtable reconhece o tableModel
                new Object[][]{},
                new String[]{"Id", "Numero de Itens", "Valor sem desconto", "Desconto", "Valor Final", "Funcionario", "Cliente", "Data"}) {
                    @Override
                    public boolean isCellEditable(int row, int col) {
                        return false;
                    }
                };
        tm.setNumRows(0);
        Iterator<ModelVenda> itVenda = retornaVetor.iterator();
        while (itVenda.hasNext()) {
            ModelVenda vendaIterator = itVenda.next();
            ArrayList retornaVetorVendaItem = vendaIterator.getVendaItem();
            Iterator<ModelVendaItem> itVendaItem = retornaVetorVendaItem.iterator();
            //esse outro while é pra rodar o array de compraItem que esta dentro de compra
            while (itVendaItem.hasNext()) {
                ModelVendaItem vendaItemIterator = itVendaItem.next();
                numeroItens = numeroItens + vendaItemIterator.getQuantidade();
                ModelProduto produto = new ModelProduto();
                produto = vendaItemIterator.getProduto();
                valorTotal = valorTotal + (produto.getPreco() * vendaItemIterator.getQuantidade());
            }

            if (filtro.equals("Todos os valores")) {
                String linha = vendaIterator.getId() + "," + numeroItens + ", R$ " + Double.valueOf(String.format(Locale.US, "%.2f", valorTotal)) + "," + padronizarDesconto(vendaIterator.getDesconto()) + "," + Double.valueOf(String.format(Locale.US, "%.2f", (valorTotal - (valorTotal * vendaIterator.getDesconto())))) + ","
                        + vendaIterator.getFuncionario().getNome() + "," + vendaIterator.getCliente().getNome() + "," + sdf.format(vendaIterator.getData());
                String valores[] = linha.split(","); //serve para botar uma palavra em cada posicao do vetor, pois a cada virgula pula pos
                tm.addRow(valores);
            }
            if (filtro.equals("Até R$ 500.00")) {
                if (valorTotal <= 500) {
                    String linha = vendaIterator.getId() + "," + numeroItens + ", R$ " + Double.valueOf(String.format(Locale.US, "%.2f", valorTotal)) + "," + padronizarDesconto(vendaIterator.getDesconto()) + "," + Double.valueOf(String.format(Locale.US, "%.2f", (valorTotal - (valorTotal * vendaIterator.getDesconto())))) + ","
                            + vendaIterator.getFuncionario().getNome() + "," + vendaIterator.getCliente().getNome() + "," + sdf.format(vendaIterator.getData());
                    String valores[] = linha.split(","); //serve para botar uma palavra em cada posicao do vetor, pois a cada virgula pula pos
                    tm.addRow(valores);
                }
            }
            if (filtro.equals("De R$ 500.01 a R$ 1000.00")) {
                if (valorTotal > 500 && valorTotal <= 1000) {
                    String linha = vendaIterator.getId() + "," + numeroItens + ", R$ " + Double.valueOf(String.format(Locale.US, "%.2f", valorTotal)) + "," + padronizarDesconto(vendaIterator.getDesconto()) + "," + Double.valueOf(String.format(Locale.US, "%.2f", (valorTotal - (valorTotal * vendaIterator.getDesconto())))) + ","
                            + vendaIterator.getFuncionario().getNome() + "," + vendaIterator.getCliente().getNome() + "," + sdf.format(vendaIterator.getData());
                    String valores[] = linha.split(","); //serve para botar uma palavra em cada posicao do vetor, pois a cada virgula pula pos
                    tm.addRow(valores);
                }
            }
            if (filtro.equals("De R$ 1000.01 a R$ 2000.00")) {
                if (valorTotal > 1000 && valorTotal <= 2000) {
                    String linha = vendaIterator.getId() + "," + numeroItens + ", R$ " + Double.valueOf(String.format(Locale.US, "%.2f", valorTotal)) + "," + padronizarDesconto(vendaIterator.getDesconto()) + "," + Double.valueOf(String.format(Locale.US, "%.2f", (valorTotal - (valorTotal * vendaIterator.getDesconto())))) + ","
                            + vendaIterator.getFuncionario().getNome() + "," + vendaIterator.getCliente().getNome() + "," + sdf.format(vendaIterator.getData());
                    String valores[] = linha.split(","); //serve para botar uma palavra em cada posicao do vetor, pois a cada virgula pula pos
                    tm.addRow(valores);
                }
            }
            if (filtro.equals("Acima de R$ 2000.00")) {
                if (valorTotal > 2000) {
                    String linha = vendaIterator.getId() + "," + numeroItens + ", R$ " + Double.valueOf(String.format(Locale.US, "%.2f", valorTotal)) + "," + padronizarDesconto(vendaIterator.getDesconto()) + "," + Double.valueOf(String.format(Locale.US, "%.2f", (valorTotal - (valorTotal * vendaIterator.getDesconto())))) + ","
                            + vendaIterator.getFuncionario().getNome() + "," + vendaIterator.getCliente().getNome() + "," + sdf.format(vendaIterator.getData());
                    String valores[] = linha.split(","); //serve para botar uma palavra em cada posicao do vetor, pois a cada virgula pula pos
                    tm.addRow(valores);
                }
            }
            valorTotal = 0;
            numeroItens = 0;
        }
        view.getTbVenda().setModel(tm);
    }
}
