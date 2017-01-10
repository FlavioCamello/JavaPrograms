/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter.venda;

import collection.Vendas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import javax.swing.table.DefaultTableModel;
import model.ModelVendaItem;
import model.ModelProduto;
import model.ModelVenda;
import view.venda.ViewVisualizarVenda;

/**
 *
 * @author Flavio
 */
public class PresenterVisualizarVenda {

    private ModelVenda venda;
    private Vendas vendas;
    private ViewVisualizarVenda view;

    public PresenterVisualizarVenda(int procurado) {
        view = new ViewVisualizarVenda();
        vendas = new Vendas();
        venda = vendas.retornaVenda(procurado);
        carregarTabela();
        view.getBtnVoltar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                PresenterManterVenda pmv = new PresenterManterVenda();
            }
        });
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }

    public void carregarTabela() {
        ModelProduto produto = new ModelProduto();
        ArrayList retornaVetor = venda.getVendaItem();
        DefaultTableModel tm = new DefaultTableModel( // jowtable reconhece o tableModel
                new Object[][]{},
                new String[]{"Id", "Produto", "Numero de Itens ", "Valor Unitario ", "Valor"}) {
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
            String linha = produto.getId() + "," + produto.getNome() + "," + vendaItemIterator.getQuantidade() + ", R$ " + produto.getPreco() + ", R$ " + Double.valueOf(String.format(Locale.US, "%.2f",(produto.getPreco() * vendaItemIterator.getQuantidade())));
            String valores[] = linha.split(","); //serve para botar uma palavra em cada posicao do vetor, pois a cada virgula pula pos
            tm.addRow(valores);
        }
        view.getTbVenda().setModel(tm);
    }

}
