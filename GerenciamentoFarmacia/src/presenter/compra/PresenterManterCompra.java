/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter.compra;

import collection.Compras;
import collection.Produtos;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.ModelCompra;
import model.ModelCompraItem;
import model.ModelProduto;
import view.compra.ViewManterCompra;

/**
 *
 * @author Flavio
 */
public class PresenterManterCompra {

    private ModelCompra compra;
    private Compras compras;
    private ViewManterCompra view;

    public PresenterManterCompra() {
        view = new ViewManterCompra();
        compras = new Compras();
        carregarTabela();

        view.getCbFiltrar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                carregarTabelaCompraFiltradaPreco((String) view.getCbFiltrar().getSelectedItem());
            }
        });

        view.getBtnCadastrar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                PresenterCadastrarCompra pcc = new PresenterCadastrarCompra();
            }
        });

        view.getBtnEditar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    PresenterEditarCompra pec = new PresenterEditarCompra(Integer.parseInt((String) view.getTbCompras().getValueAt(view.getTbCompras().getSelectedRow(), 0)));
                    view.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Verifique se algum item foi selecionado!  ");
                }
            }
        });

        view.getBtnExcluir().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (verificarSePodeExcluir()) {
                        controleEstoqueSubtrair();
                        excluir();
                        view.dispose();
                        PresenterManterCompra pmc = new PresenterManterCompra();
                    } else {
                        JOptionPane.showMessageDialog(null, "Essa compra, nao pode ser removida por ja ter sido vendido produtos dela!");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Verifique se algum item foi selecionado!  ");
                }
                //PresenterManterCompra pmc = new PresenterManterCompra();
            }
        });

        view.getBtnVisualizar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    PresenterVisualizarCompra pvc = new PresenterVisualizarCompra(Integer.parseInt((String) view.getTbCompras().getValueAt(view.getTbCompras().getSelectedRow(), 0)));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Verifique se algum item foi selecionado!  ");
                }
            }
        });

        view.getBtnCancelar().addActionListener(new ActionListener() {

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
        ArrayList retornaVetor = compras.retornaVetor();
        Iterator<ModelCompra> itCompra = retornaVetor.iterator();
        while (itCompra.hasNext()) {
            ModelCompra compraIterator = itCompra.next();
            if (compraIterator.getId() == (Integer.parseInt((String) view.getTbCompras().getValueAt(view.getTbCompras().getSelectedRow(), 0)))) {
                ArrayList retornaVetorCompraItem = compraIterator.getCompraItem();
                Iterator<ModelCompraItem> itCompraItem = retornaVetorCompraItem.iterator();
                //esse outro while é pra rodar o array de compraItem que esta dentro de compra
                while (itCompraItem.hasNext()) {
                    ModelProduto produtoIterator = new ModelProduto();
                    ModelCompraItem compraItemIterator = itCompraItem.next();
                    produtoIterator = produtos.retornaProcurado(compraItemIterator.getProduto().getId());
                    produtoIterator.setQtdEstoque(produtoIterator.getQtdEstoque() - compraItemIterator.getQuantidade());
                    produtos.excluir(produtoIterator.getId());
                    produtos.adicionarProduto(produtoIterator);
                }
            }
        }
    }

    public void carregarTabela() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        ArrayList retornaVetor = compras.retornaVetor();
        int numeroItens = 0;
        double valorTotal = 0;
        DefaultTableModel tm = new DefaultTableModel( // jowtable reconhece o tableModel
                new Object[][]{},
                new String[]{"Id", "Numero de Itens", "Valor Total", "Data"}) {
                    @Override
                    public boolean isCellEditable(int row, int col) {
                        return false;
                    }
                };
        tm.setNumRows(0);
        Iterator<ModelCompra> itCompra = retornaVetor.iterator();
        while (itCompra.hasNext()) {
            ModelCompra compraIterator = itCompra.next();
            ArrayList retornaVetorCompraItem = compraIterator.getCompraItem();
            Iterator<ModelCompraItem> itCompraItem = retornaVetorCompraItem.iterator();
            //esse outro while é pra rodar o array de compraItem que esta dentro de compra
            while (itCompraItem.hasNext()) {
                ModelCompraItem compraItemIterator = itCompraItem.next();
                numeroItens = numeroItens + compraItemIterator.getQuantidade();
                ModelProduto produto = new ModelProduto();
                produto = compraItemIterator.getProduto();
                valorTotal = valorTotal + (produto.getPreco() * compraItemIterator.getQuantidade());
            }
            String linha = compraIterator.getId() + "," + numeroItens + ", R$ " + Double.valueOf(String.format(Locale.US, "%.2f", valorTotal)) + "," + sdf.format(compraIterator.getData());
            String valores[] = linha.split(","); //serve para botar uma palavra em cada posicao do vetor, pois a cada virgula pula pos
            tm.addRow(valores);
            valorTotal = 0;
            numeroItens = 0;
        }
        view.getTbCompras().setModel(tm);
    }

    public void carregarTabelaCompraFiltradaPreco(String filtro) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        ArrayList retornaVetor = compras.retornaVetor();
        int numeroItens = 0;
        double valorTotal = 0;
        DefaultTableModel tm = new DefaultTableModel( // jowtable reconhece o tableModel
                new Object[][]{},
                new String[]{"Id", "Numero de Itens", "Valor Total", "Data"}) {
                    @Override
                    public boolean isCellEditable(int row, int col) {
                        return false;
                    }
                };
        tm.setNumRows(0);
        Iterator<ModelCompra> itCompra = retornaVetor.iterator();
        while (itCompra.hasNext()) {
            ModelCompra compraIterator = itCompra.next();
            ArrayList retornaVetorCompraItem = compraIterator.getCompraItem();
            Iterator<ModelCompraItem> itCompraItem = retornaVetorCompraItem.iterator();
            //esse outro while é pra rodar o array de compraItem que esta dentro de compra
            while (itCompraItem.hasNext()) {
                ModelCompraItem compraItemIterator = itCompraItem.next();
                numeroItens = numeroItens + compraItemIterator.getQuantidade();
                ModelProduto produto = new ModelProduto();
                produto = compraItemIterator.getProduto();
                valorTotal = valorTotal + (produto.getPreco() * compraItemIterator.getQuantidade());
            }
            if (filtro.equals("Todos os valores")) {
                String linha = compraIterator.getId() + "," + numeroItens + ", R$ " + valorTotal + "," + sdf.format(compraIterator.getData());
                String valores[] = linha.split(","); //serve para botar uma palavra em cada posicao do vetor, pois a cada virgula pula pos
                tm.addRow(valores);
            }
            if (filtro.equals("Até R$ 500.00")) {
                if (valorTotal <= 500) {
                    String linha = compraIterator.getId() + "," + numeroItens + ", R$ " + valorTotal + "," + sdf.format(compraIterator.getData());
                    String valores[] = linha.split(","); //serve para botar uma palavra em cada posicao do vetor, pois a cada virgula pula pos
                    tm.addRow(valores);
                }
            }
            if (filtro.equals("De R$ 500.01 a R$ 1000.00")) {
                if (valorTotal > 500 && valorTotal <= 1000) {
                    String linha = compraIterator.getId() + "," + numeroItens + ", R$ " + valorTotal + "," + sdf.format(compraIterator.getData());
                    String valores[] = linha.split(","); //serve para botar uma palavra em cada posicao do vetor, pois a cada virgula pula pos
                    tm.addRow(valores);
                }
            }
            if (filtro.equals("De R$ 1000.01 a R$ 2000.00")) {
                if (valorTotal > 1000 && valorTotal <= 2000) {
                    String linha = compraIterator.getId() + "," + numeroItens + ", R$ " + valorTotal + "," + sdf.format(compraIterator.getData());
                    String valores[] = linha.split(","); //serve para botar uma palavra em cada posicao do vetor, pois a cada virgula pula pos
                    tm.addRow(valores);
                }
            }
            if (filtro.equals("Acima de R$ 2000.00")) {
                if (valorTotal > 2000) {
                    String linha = compraIterator.getId() + "," + numeroItens + ", R$ " + valorTotal + "," + sdf.format(compraIterator.getData());
                    String valores[] = linha.split(","); //serve para botar uma palavra em cada posicao do vetor, pois a cada virgula pula pos
                    tm.addRow(valores);
                }
            }

            valorTotal = 0;
            numeroItens = 0;
        }
        view.getTbCompras().setModel(tm);
    }

    public boolean verificarSePodeExcluir() {
        int numItem = (Integer.parseInt((String) view.getTbCompras().getValueAt(view.getTbCompras().getSelectedRow(), 0)));
        Produtos produtos = new Produtos();
        ArrayList retornaVetor = compras.retornaVetor();
        Iterator<ModelCompra> itCompra = retornaVetor.iterator();
        while (itCompra.hasNext()) {
            ModelCompra compraIterator = itCompra.next();
            if (compraIterator.getId() == (Integer.parseInt((String) view.getTbCompras().getValueAt(view.getTbCompras().getSelectedRow(), 0)))) {
                ArrayList retornaVetorCompraItem = compraIterator.getCompraItem();
                Iterator<ModelCompraItem> itCompraItem = retornaVetorCompraItem.iterator();
                //esse outro while é pra rodar o array de compraItem que esta dentro de compra
                while (itCompraItem.hasNext()) {
                    ModelProduto produtoIterator = new ModelProduto();
                    ModelCompraItem compraItemIterator = itCompraItem.next();
                    produtoIterator = produtos.retornaProcurado(compraItemIterator.getProduto().getId());

                    if (produtoIterator.getQtdEstoque() < compraItemIterator.getQuantidade()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void excluir() throws Exception {
        int procurado = Integer.parseInt((String) view.getTbCompras().getValueAt(view.getTbCompras().getSelectedRow(), 0));
        compras.excluir(procurado);
    }
}
