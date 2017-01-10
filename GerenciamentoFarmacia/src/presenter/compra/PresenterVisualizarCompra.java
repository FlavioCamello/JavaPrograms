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
import javax.swing.table.DefaultTableModel;
import model.ModelCompra;
import model.ModelCompraItem;
import model.ModelProduto;
import view.compra.ViewVisualizarCompra;

/**
 *
 * @author Flavio
 */
public class PresenterVisualizarCompra {
    private ModelCompra compra; 
    private Compras compras;
    private ViewVisualizarCompra view;
    private Produtos produtos;
    private ModelProduto produto;
    PresenterVisualizarCompra(int procurado) {
        view = new ViewVisualizarCompra();
        compras = new Compras();
        compra = compras.retornaProcurado(procurado);
        carregarTabela();
        view.getBtnVoltar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
            }
        });
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }
    
    public void carregarTabela(){
        ModelProduto produto = new ModelProduto();
        ArrayList retornaVetor = compra.getCompraItem();
        DefaultTableModel tm = new DefaultTableModel( // jowtable reconhece o tableModel
                new Object[][]{},
                new String[]{"Id", "Produto", "Numero de Itens ","Valor Unitario ",  "Valor"}){
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tm.setNumRows(0);
        Iterator<ModelCompraItem> itCompraItem = retornaVetor.iterator();
            //esse outro while é pra rodar o array de compraItem que esta dentro de compra
            while (itCompraItem.hasNext()){
                ModelCompraItem compraItemIterator = itCompraItem.next();
                produto = compraItemIterator.getProduto();
                String linha = produto.getId()+","+produto.getNome()+","+compraItemIterator.getQuantidade()+", R$ "
                        +produto.getPreco()+", R$ "+ Double.valueOf(String.format(Locale.US, "%.2f",(produto.getPreco()*compraItemIterator.getQuantidade())));
                 String valores[] = linha.split(","); //serve para botar uma palavra em cada posicao do vetor, pois a cada virgula pula pos
                tm.addRow(valores);
            }
        view.getTbCompra().setModel(tm);
    }
}
