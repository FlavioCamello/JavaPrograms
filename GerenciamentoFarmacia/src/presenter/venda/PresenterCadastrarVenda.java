/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter.venda;

import collection.Clientes;
import collection.Funcionarios;
import collection.Produtos;
import collection.Vendas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.ModelAbstractCliente;
import model.ModelFuncionario;
import model.ModelProduto;
import model.ModelVenda;
import model.ModelVendaItem;
import view.venda.ViewCadastrarVenda;

/**
 *
 * @author Flavio
 */
public class PresenterCadastrarVenda {

    private ModelVenda venda;
    private Vendas vendas;
    private ViewCadastrarVenda view;
    private Produtos produtos;
    private ModelProduto produto;
    private boolean primeiraCompra;

    public PresenterCadastrarVenda() {
        primeiraCompra = true;
        venda = new ModelVenda();
        view = new ViewCadastrarVenda();
        vendas = new Vendas();
        produtos = new Produtos();

        carregarCbCliente();

        bloquearBotoes();

        view.getCbCliente().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (view.getCbCliente().getSelectedItem().equals("Selecione o cliente")) {

                } else {
                    view.getCbFuncionario().setEnabled(true);
                    carregarCbFuncionario();
                }
            }
        });

        view.getCbFuncionario().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (view.getCbFuncionario().getSelectedItem().equals("Selecione o funcionario")) {

                } else {
                view.getCbDesconto().setEnabled(true);
                carregarCbDesconto();
                }
            }
        });

        view.getCbDesconto().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                carregarTabelaProduto();
            }
        });

        view.getTbProduto().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                carregarComboBoxQuantidade();

                view.getCbQuantidade().setEnabled(true);
                view.getBtnCarrinho().setEnabled(true);
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
                controleEstoqueSubtrair();
                atualizarTela();
                carregarTabelaProduto();
            }
        });

        view.getBtnFinalizar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (verificarSeClienteTemCreditoParaCompra()) {
                    view.dispose();
                    vendas.adicionarVenda(venda);
                } else {
                    JOptionPane.showMessageDialog(null, "Essa compra nao pode ser realizada, pois o cliente " + venda.getCliente().getNome() + " possui um credito de " + venda.getCliente().getLimiteVenda() + ". Que é inferior ao valor da compra!");
                }

                PresenterManterVenda pmv = new PresenterManterVenda();
            }

        });

        view.getBtnCancelar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                retornarParaEstoque();
                PresenterManterVenda pmv = new PresenterManterVenda();
            }
        });
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }

    public boolean verificarSeClienteTemCreditoParaCompra() {
        double valorTotal = 0;
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

    private void carregarComboBoxQuantidade() {
        view.getCbQuantidade().removeAllItems();
        int nEstoque = (Integer.parseInt((String) view.getTbProduto().getValueAt(view.getTbProduto().getSelectedRow(), 3)));
        for (int i = 1; i <= nEstoque; i++) {
            view.getCbQuantidade().addItem(i);
        }
    }

    public void retornarParaEstoque() {
        ArrayList retornaVetor = venda.getVendaItem();
        Iterator<ModelVendaItem> itVendaItem = retornaVetor.iterator();
        ModelProduto produtoIterator = new ModelProduto();
        while (itVendaItem.hasNext()) {
            ModelVendaItem vendaItemIterator = itVendaItem.next();
            produtoIterator = vendaItemIterator.getProduto();
            produtoIterator.setQtdEstoque(produtoIterator.getQtdEstoque());
            produtos.excluir(produtoIterator.getId());
            produtos.adicionarProduto(produtoIterator);
        }
    }

    public void atualizarTela() {
        view.getCbCliente().setEnabled(false);
        view.getCbFuncionario().setEnabled(false);
        view.getCbDesconto().setEnabled(false);
        view.getCbQuantidade().removeAllItems();
        view.getCbQuantidade().setEnabled(false);
        view.getBtnCarrinho().setEnabled(false);
        view.getBtnFinalizar().setEnabled(true);
    }

    public void controleEstoqueSubtrair() {
        ModelProduto produtoTemp = produtos.retornaProcurado(produto.getId());
        produtoTemp.setQtdEstoque(produto.getQtdEstoque() - (int) view.getCbQuantidade().getSelectedItem());
        produtos.excluir(produto.getId());
        produtos.adicionarProduto(produtoTemp);
    }

    public void colocaCarrinhoPrimeiraVez() {
        //pegar o funcionario e o cliente selecionado
        Clientes clientes = new Clientes();
        Funcionarios funcionarios = new Funcionarios();
        ModelAbstractCliente cliente;
        ModelFuncionario funcionario;
        String nomeFuncionario = (String) view.getCbFuncionario().getSelectedItem();
        String nomeCliente = (String) view.getCbCliente().getSelectedItem();
        cliente = clientes.retornaProcuradoNome(nomeCliente);
        funcionario = funcionarios.retornaProcuradoNome(nomeFuncionario);

        String descontoString = (String) view.getCbDesconto().getSelectedItem();
        double desconto = transformaDesconto(descontoString);

        int procurado = Integer.parseInt((String) view.getTbProduto().getValueAt(view.getTbProduto().getSelectedRow(), 0));
        produto = produtos.retornaProcurado(procurado);

        Date data = new Date();

        int quantidade = (int) view.getCbQuantidade().getSelectedItem();

        ModelVendaItem vi = new ModelVendaItem(produto, quantidade);
        venda = new ModelVenda(vendas.getProxIdCompra(), funcionario, desconto, cliente, data);
        venda.adicionar(vi);

    }

    public void colocaCarrinho() {
        int procurado = Integer.parseInt((String) view.getTbProduto().getValueAt(view.getTbProduto().getSelectedRow(), 0));
        produto = produtos.retornaProcurado(procurado);

        int quantidade = (int) view.getCbQuantidade().getSelectedItem();

        //verifica se ja existe esse produto nessa compra
        int aux = 0;
        ArrayList retornaVetor = venda.getVendaItem();
        Iterator<ModelVendaItem> itVendaItem = retornaVetor.iterator();
        ModelProduto produtoIterator = new ModelProduto();
        //esse outro while é pra rodar o array de vendaItem que esta dentro de venda
        while (itVendaItem.hasNext()) {
            ModelVendaItem vendaItemIterator = itVendaItem.next();
            produtoIterator = vendaItemIterator.getProduto();
            //se ja estiver na lista, o produto vai receber a quantidade a mais que escolheu
            if (produto.getId() == produtoIterator.getId()) {
                vendaItemIterator.setQuantidade(vendaItemIterator.getQuantidade() + quantidade);
                aux = 1;
                break;
            }
        }
        if (aux == 0) {
            ModelVendaItem vi = new ModelVendaItem(produto, quantidade);
            venda.adicionar(vi);
        }
    }

    public void carregarCbCliente() {
        Clientes clientes = new Clientes();
        ArrayList<ModelAbstractCliente> listaClientes = new ArrayList<>();
        listaClientes = clientes.retornaVetor();
        Iterator<ModelAbstractCliente> it = listaClientes.iterator();
        ModelAbstractCliente cliente = null;
        view.getCbCliente().addItem("Selecione o cliente");
        while (it.hasNext()) {
            ModelAbstractCliente d = it.next();
            view.getCbCliente().addItem(d.getNome());
        }
    }

    public void carregarCbFuncionario() {
        Funcionarios funcionarios = new Funcionarios();
        ArrayList<ModelFuncionario> listaFuncionarios = new ArrayList<>();
        listaFuncionarios = funcionarios.retornaVetor();
        Iterator<ModelFuncionario> it = listaFuncionarios.iterator();
        ModelFuncionario funcionario = null;
        
        view.getCbFuncionario().addItem("Selecione o funcionario");

        while (it.hasNext()) {
            ModelFuncionario d = it.next();
            view.getCbFuncionario().addItem(d.getNome());
        }
    }

    public void carregarCbDesconto() {
        for (int i = 0; i <= 10; i++) {
            view.getCbDesconto().addItem(i * 10 + "%");
        }
    }

    public void bloquearBotoes() {
        view.getCbFuncionario().setEnabled(false);
        view.getCbDesconto().setEnabled(false);
        view.getCbQuantidade().setEnabled(false);
        view.getBtnCarrinho().setEnabled(false);
        view.getBtnFinalizar().setEnabled(false);
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

    public double transformaDesconto(String descontoProcurado) {
        double desconto = 0;
        if (descontoProcurado.equals("10%")) {
            desconto = 0.10;
        }
        if (descontoProcurado.equals("20%")) {
            desconto = 0.20;
        }
        if (descontoProcurado.equals("30%")) {
            desconto = 0.30;
        }
        if (descontoProcurado.equals("40%")) {
            desconto = 0.40;
        }
        if (descontoProcurado.equals("50%")) {
            desconto = 0.50;
        }
        if (descontoProcurado.equals("60%")) {
            desconto = 0.60;
        }
        if (descontoProcurado.equals("70%")) {
            desconto = 0.70;
        }
        if (descontoProcurado.equals("80%")) {
            desconto = 0.80;
        }
        if (descontoProcurado.equals("90%")) {
            desconto = 0.90;
        }
        if (descontoProcurado.equals("100%")) {
            desconto = 1;
        }
        return desconto;
    }
}
