/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter.fornecedor;

import collection.Compras;
import collection.Fornecedores;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.ModelAbstractCliente;
import presenter.cliente.PresenterManterCliente;

import view.fornecedor.ViewManterFornecedor;

/**
 *
 * @author Flavio
 */
public class PresenterManterFornecedor {

    private ViewManterFornecedor view;
    private ModelAbstractCliente fornecedor;
    private Fornecedores fornecedores;

    public PresenterManterFornecedor() {
        view = new ViewManterFornecedor();
        fornecedores = new Fornecedores();
        carregarTabelaFornecedor();
        view.getBtnCadastrar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                PresenterCadastrarFornecedor pcc = new PresenterCadastrarFornecedor();
            }
        });
        view.getBtnEditar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                PresenterEditarFornecedor pec = new PresenterEditarFornecedor(Integer.parseInt((String) view.getTbFornecedor().getValueAt(view.getTbFornecedor().getSelectedRow(), 0)));
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
                    Compras vendas = new Compras();
                    int idProcurado = (Integer.parseInt((String) view.getTbFornecedor().getValueAt(view.getTbFornecedor().getSelectedRow(), 0)));
                    if (vendas.retornaSePodeExcluirFornecedor(idProcurado)) {
                        excluirFornecedor();
                    } else {
                        JOptionPane.showMessageDialog(null, "Esse fornecedor nao pode ser removido por ja ter realizado uma venda para a empresa!");
                    }
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
        view.getCbFornecedor().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String filtro = (String) view.getCbFornecedor().getSelectedItem();
                carregarTabelaFornecedorFiltrada(filtro);
            }
        });

        view.getBtnPesquisar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                carregarTabelaFornecedorPesquisa(view.getTfPesquisar().getText());
            }
        });
        Dimension screenSize = view.getToolkit().getScreenSize();  
        view.setSize(screenSize);
        view.setVisible(true);
    }

    public void carregarTabelaFornecedor() {
        ArrayList retornaVetor = fornecedores.retornaVetor();
        DefaultTableModel tm = new DefaultTableModel( // jowtable reconhece o tableModel
                new Object[][]{},
                new String[]{"Id", "Nome", "Endereco", "Telefone", "Documento"}) {
                    @Override
                    public boolean isCellEditable(int row, int col) {
                        return false;
                    }
                };
        tm.setNumRows(0);
        Iterator<ModelAbstractCliente> it = retornaVetor.iterator();
        while (it.hasNext()) {
            ModelAbstractCliente fornecedorIterator = it.next();
            String linha = fornecedorIterator.toString();  //o toString tera que ter os nomes separados por virgula
            String valores[] = linha.split(","); //serve para botar uma palavra em cada posicao do vetor, pois a cada virgula pula pos
            tm.addRow(valores);
        }
        view.getTbFornecedor().setModel(tm);
    }

    public void excluirFornecedor() {
        fornecedores.excluir(Integer.parseInt((String) view.getTbFornecedor().getValueAt(view.getTbFornecedor().getSelectedRow(), 0)));
        view.dispose();
        PresenterManterCliente pmp = new PresenterManterCliente();
    }

    public void carregarTabelaFornecedorFiltrada(String filtro) {
        ArrayList retornaVetor = fornecedores.retornaVetor();
        Compras compras = new Compras();
        DefaultTableModel tm = new DefaultTableModel( // jowtable reconhece o tableModel
                new Object[][]{},
                new String[]{"Id", "Nome", "Endereco", "Telefone", "Documento"}) {
                    @Override
                    public boolean isCellEditable(int row, int col) {
                        return false;
                    }
                };
        tm.setNumRows(0);
        Iterator<ModelAbstractCliente> it = retornaVetor.iterator();
        while (it.hasNext()) {
            ModelAbstractCliente fornecedorIterator = it.next();
            String linha = null;
            if (filtro.equals("Possui compra")) {
                if (compras.retornaFornecedorPossuiCompra(fornecedorIterator.getId())) {
                    linha = fornecedorIterator.toString();
                    String valores[] = linha.split(","); //serve para botar uma palavra em cada posicao do vetor, pois a cada virgula pula pos
                    tm.addRow(valores);
                }
            }
            if (filtro.equals("Não possui compra")) {
                if (compras.retornaFornecedorNaoPossuiCompra(fornecedorIterator.getId())) {
                    linha = fornecedorIterator.toString();
                    String valores[] = linha.split(","); //serve para botar uma palavra em cada posicao do vetor, pois a cada virgula pula pos
                    tm.addRow(valores);
                }
            }
            if (filtro.equals("Todos fornecedores")) {
                linha = fornecedorIterator.toString();
                String valores[] = linha.split(","); //serve para botar uma palavra em cada posicao do vetor, pois a cada virgula pula pos
                tm.addRow(valores);
            }
        }
        view.getTbFornecedor().setModel(tm);
    }

    public void carregarTabelaFornecedorPesquisa(String nome) {
        ArrayList retornaVetor = fornecedores.retornaVetor();
        DefaultTableModel tm = new DefaultTableModel( // jowtable reconhece o tableModel
                new Object[][]{},
                new String[]{"Id", "Nome", "Endereco", "Telefone", "Documento"}) {
                    @Override
                    public boolean isCellEditable(int row, int col) {
                        return false;
                    }
                };
        tm.setNumRows(0);
        Iterator<ModelAbstractCliente> it = retornaVetor.iterator();
        while (it.hasNext()) {
            ModelAbstractCliente fornecedorIterator = it.next();
            if (fornecedorIterator.getNome().contains(nome)) {
                String linha = fornecedorIterator.toString();  //o toString tera que ter os nomes separados por virgula
                String valores[] = linha.split(","); //serve para botar uma palavra em cada posicao do vetor, pois a cada virgula pula pos
                tm.addRow(valores);
            }
        }
        view.getTbFornecedor().setModel(tm);
    }
}
