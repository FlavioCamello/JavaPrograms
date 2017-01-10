/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter.compra;

//import aux.table.compra.PadraoGridView;
//import aux.table.compra.SpinnerEditor;


import collection.Compras;
import collection.Fornecedores;
import collection.Produtos;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import model.ModelCompra;
import model.ModelProduto;
import view.compra.ViewCadastrarCompra;
import javax.swing.table.DefaultTableModel;
import model.ModelAbstractCliente;
import model.ModelCompraItem;


/**
 *
 * @author Flavio
 */
public class PresenterCadastrarCompra {

    private ModelCompra compra;
    private Compras compras;
    private ViewCadastrarCompra view;
    private Produtos produtos;
    private ModelProduto produto;
    private boolean primeiraCompra;

    public PresenterCadastrarCompra() {
        primeiraCompra = true;
        compra = new ModelCompra();
        view = new ViewCadastrarCompra();
        compras = new Compras();
        produtos = new Produtos();

        carregarCbFornecedor();

        bloquearBotoes();

        view.getCbFornecedor().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                carregarTabelaProduto();
            }
        });

        view.getTbProduto().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                view.getCbQuantidade().setEnabled(true);
                view.getBtnCarrinho().setEnabled(true);
                carregarComboBoxQuantidade();
                
            }

        });

        view.getBtnCarrinho().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (primeiraCompra == true) {
                    colocaCarrinhoPrimeiraVez();
                    primeiraCompra = false;
                } else {
                    colocaCarrinho();
                }
                controleEstoqueSomar();
                atualizarTela();
                carregarTabelaProduto();
            }
        });

        view.getBtnFinalizar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                compras.adicionarCompra(compra);           
                
                view.dispose();
                PresenterManterCompra pmc = new PresenterManterCompra();
            }

        });

        view.getBtnCancelar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                retornarParaEstoque();
                PresenterManterCompra pmc = new PresenterManterCompra();
            }
        });
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }

    public void atualizarTela() {
        view.getCbFornecedor().setEnabled(false);
        view.getCbQuantidade().removeAllItems();
        view.getCbQuantidade().setEnabled(false);
        view.getBtnCarrinho().setEnabled(false);
        view.getBtnFinalizar().setEnabled(true);
    }
    
    public void bloquearBotoes() {
        view.getCbQuantidade().setEnabled(false);
        view.getBtnCarrinho().setEnabled(false);
        view.getBtnFinalizar().setEnabled(false);
    }
    
    public void carregarComboBoxQuantidade() {
        
        view.getCbQuantidade().removeAllItems();
        for (int i = 1; i <= 1000; i++) {
            view.getCbQuantidade().addItem(i);
        }
    }

    public void retornarParaEstoque() {
        ArrayList retornaVetor = compra.getCompraItem();
        Iterator<ModelCompraItem> itCompraItem = retornaVetor.iterator();
        ModelProduto produtoIterator = new ModelProduto();
        while (itCompraItem.hasNext()) {
            ModelCompraItem compraItemIterator = itCompraItem.next();
            produtoIterator = compraItemIterator.getProduto();
            produtoIterator.setQtdEstoque(produtoIterator.getQtdEstoque());
            produtos.excluir(produtoIterator.getId());
            produtos.adicionarProduto(produtoIterator);
        }
    }

    public void carregarCbFornecedor() {
        Fornecedores fornecedores = new Fornecedores();
        ArrayList<ModelAbstractCliente> listaFornecedores = new ArrayList<>();
        listaFornecedores = fornecedores.retornaVetor();
        Iterator<ModelAbstractCliente> it = listaFornecedores.iterator();
        ModelAbstractCliente fornecedor = null;
        while (it.hasNext()) {
            ModelAbstractCliente d = it.next();
            view.getCbFornecedor().addItem(d.getNome());
        }
    }

    public void colocaCarrinho() {
        int aux = 0;
        int procurado = Integer.parseInt((String) view.getTbProduto().getValueAt(view.getTbProduto().getSelectedRow(), 0));
        produto = produtos.retornaProcurado(procurado);
        int quantidade = (int) view.getCbQuantidade().getSelectedItem();
        ModelCompraItem ci = new ModelCompraItem(produto, quantidade);
        ArrayList retornaVetor = compra.getCompraItem();
        Iterator<ModelCompraItem> itCompraItem = retornaVetor.iterator();
        ModelProduto produtoIterator = new ModelProduto();
        //esse outro while é pra rodar o array de compraItem que esta dentro de compra
        while (itCompraItem.hasNext()) {
            ModelCompraItem compraItemIterator = itCompraItem.next();
            produtoIterator = compraItemIterator.getProduto();
            //se ja estiver na lista, o produto vai receber a quantidade a mais que escolheu
            if (produto.getId() == produtoIterator.getId()) {
                compraItemIterator.setQuantidade(compraItemIterator.getQuantidade() + quantidade);
                aux = 1;
                break;
            }
        }
        if (aux == 0) {
            compra.adicionar(ci);
        }
    }

    public void colocaCarrinhoPrimeiraVez() {
        Fornecedores fornecedores = new Fornecedores();
        ModelAbstractCliente fornecedor;
        int procurado = Integer.parseInt((String) view.getTbProduto().getValueAt(view.getTbProduto().getSelectedRow(), 0));
        produto = produtos.retornaProcurado(procurado);
        int quantidade = (int) view.getCbQuantidade().getSelectedItem();
        String nomeFornecedor = (String) view.getCbFornecedor().getSelectedItem();
        ModelCompraItem ci = new ModelCompraItem(produto, quantidade);
        fornecedor = fornecedores.retornaProcuradoNome(nomeFornecedor);
        Date data = new Date();
        compra = new ModelCompra(compras.getProxIdCompra(), fornecedor, data);
        compra.adicionar(ci);
    }

    public void controleEstoqueSomar() {
        ModelProduto produtoTemp = produtos.retornaProcurado(produto.getId());
        produtoTemp.setQtdEstoque(produto.getQtdEstoque() + (int) view.getCbQuantidade().getSelectedItem());
        produtos.excluir(produto.getId());
        produtos.adicionarProduto(produtoTemp);
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

}
