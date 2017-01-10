/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter.cliente;

import collection.Clientes;
import collection.Vendas;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.ModelAbstractCliente;
import view.cliente.ViewManterCliente;

/**
 *
 * @author Flavio
 */
public class PresenterManterCliente {

    private Clientes clientes;
    private ModelAbstractCliente cliente;
    private ViewManterCliente view;

    public PresenterManterCliente() {
        view = new ViewManterCliente();
        clientes = new Clientes();
        carregarTabelaCliente();
        view.getBtnCadastrar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                PresenterCadastrarCliente pcc = new PresenterCadastrarCliente();
            }
        });
        view.getBtnEditar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    PresenterEditarCliente pec = new PresenterEditarCliente(Integer.parseInt((String) view.getTbCliente().getValueAt(view.getTbCliente().getSelectedRow(), 0)));
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
                    Vendas vendas = new Vendas();
                    int idProcurado = (Integer.parseInt((String) view.getTbCliente().getValueAt(view.getTbCliente().getSelectedRow(), 0)));
                    if (vendas.retornaSePodeExcluirCliente(idProcurado)) {
                        excluirCliente();
                    } else {
                        JOptionPane.showMessageDialog(null, "Esse cliente nao pode ser removido por ja ter realizado uma compra!");
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
        view.getCbTipoCliente().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String filtro = (String) view.getCbTipoCliente().getSelectedItem();
                carregarTabelaClienteFiltradaTipo(filtro);
            }
        });
        view.getBtnPesquisar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                carregarTabelaClientePesquisa(view.getTfPesquisa().getText());
            }
        });
        Dimension screenSize = view.getToolkit().getScreenSize();  
        view.setSize(screenSize);
        view.setVisible(true);
    }

    public void carregarTabelaCliente() {
        ArrayList retornaVetor = clientes.retornaVetor();
        DefaultTableModel tm = new DefaultTableModel( // jowtable reconhece o tableModel
                new Object[][]{},
                new String[]{"Id", "Nome", "Endereco", "Telefone", "Documento", "Limite Venda"}) {
                    @Override
                    public boolean isCellEditable(int row, int col) {
                        return false;
                    }
                };
        tm.setNumRows(0);
        Iterator<ModelAbstractCliente> it = retornaVetor.iterator();
        while (it.hasNext()) {
            ModelAbstractCliente clienteIterator = it.next();
            String linha = clienteIterator.toString();  //o toString tera que ter os nomes separados por virgula
            String valores[] = linha.split(","); //serve para botar uma palavra em cada posicao do vetor, pois a cada virgula pula pos
            tm.addRow(valores);
        }
        view.getTbCliente().setModel(tm);
    }

    public void excluirCliente() {
        clientes.excluir(Integer.parseInt((String) view.getTbCliente().getValueAt(view.getTbCliente().getSelectedRow(), 0)));
        view.dispose();
        PresenterManterCliente pmp = new PresenterManterCliente();
    }

    public void carregarTabelaClienteFiltradaTipo(String filtro) {
        ArrayList retornaVetor = clientes.retornaVetor();
        DefaultTableModel tm = new DefaultTableModel( // jowtable reconhece o tableModel
                new Object[][]{},
                new String[]{"Id", "Nome", "Endereco", "Telefone", "Documento", "Limite Venda"}) {
                    @Override
                    public boolean isCellEditable(int row, int col) {
                        return false;
                    }
                };
        tm.setNumRows(0);
        Iterator<ModelAbstractCliente> it = retornaVetor.iterator();
        while (it.hasNext()) {
            ModelAbstractCliente clienteIterator = it.next();
            String linha = null;
            if (filtro.equals("Físico")) {
                if (clienteIterator.getLimiteVenda() == 1000.0) {
                    linha = clienteIterator.toString();
                    String valores[] = linha.split(","); //serve para botar uma palavra em cada posicao do vetor, pois a cada virgula pula pos
                    tm.addRow(valores);
                }
            }
            if (filtro.equals("Jurídico")) {
                if (clienteIterator.getLimiteVenda() == 5000.0) {
                    linha = clienteIterator.toString();
                    String valores[] = linha.split(","); //serve para botar uma palavra em cada posicao do vetor, pois a cada virgula pula pos
                    tm.addRow(valores);
                }
            }
            if (filtro.equals("Todos")) {
                linha = clienteIterator.toString();
                String valores[] = linha.split(","); //serve para botar uma palavra em cada posicao do vetor, pois a cada virgula pula pos
                tm.addRow(valores);
            }
        }
        view.getTbCliente().setModel(tm);
    }

    public void carregarTabelaClientePesquisa(String nome) {
        ArrayList retornaVetor = clientes.retornaVetor();
        DefaultTableModel tm = new DefaultTableModel( // jowtable reconhece o tableModel
                new Object[][]{},
                new String[]{"Id", "Nome", "Endereco", "Telefone", "Documento", "Limite Venda"}) {
                    @Override
                    public boolean isCellEditable(int row, int col) {
                        return false;
                    }
                };
        tm.setNumRows(0);
        Iterator<ModelAbstractCliente> it = retornaVetor.iterator();
        while (it.hasNext()) {
            ModelAbstractCliente clienteIterator = it.next();
            if (clienteIterator.getNome().contains(nome)) {
                String linha = clienteIterator.toString();  //o toString tera que ter os nomes separados por virgula
                String valores[] = linha.split(","); //serve para botar uma palavra em cada posicao do vetor, pois a cada virgula pula pos
                tm.addRow(valores);
            }

        }
        view.getTbCliente().setModel(tm);
    }

}
