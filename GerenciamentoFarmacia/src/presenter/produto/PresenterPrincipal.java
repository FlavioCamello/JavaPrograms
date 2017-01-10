/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter.produto;

import collection.Clientes;
import collection.Compras;
import collection.Fornecedores;
import collection.Funcionarios;
import collection.Produtos;
import collection.Vendas;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import presenter.cliente.PresenterManterCliente;
import presenter.compra.PresenterManterCompra;
import presenter.fornecedor.PresenterManterFornecedor;
import presenter.funcionario.PresenterManterFuncionario;
import presenter.venda.PresenterManterVenda;
import view.produto.ViewPrincipal;

/**
 *
 * @author Flavio
 */
public class PresenterPrincipal {
    private ViewPrincipal view;

    public PresenterPrincipal() {
        view = new ViewPrincipal();
        view.getMenuProduto().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                PresenteManterProduto pp = new PresenteManterProduto();
            }
        });
        
        view.getMenuCliente().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                PresenterManterCliente pmc = new PresenterManterCliente();
            }
        });
        
        view.getMenuForenecedor().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                PresenterManterFornecedor pmc = new PresenterManterFornecedor();
            }
        });
        
        view.getMenuFuncionario().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                PresenterManterFuncionario pmf = new PresenterManterFuncionario();
            }
        });
        
        view.getMenuCompra().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                PresenterManterCompra pmc = new PresenterManterCompra();
            }
        });
        
        view.getMenuVenda().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                PresenterManterVenda pmc = new PresenterManterVenda();
                
            }
        });
        
        view.getMenuSair().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    System.exit(1);
                } catch (Throwable ex) {
                    Logger.getLogger(PresenterPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        view.getMenuSalvarSerializacao().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gravarDadoSerializado();
            }
        });
        
        view.getMenuSalvarTexto().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gravarDadoTxt();
            }
        });
        Dimension screenSize = view.getToolkit().getScreenSize();  
        view.setSize(screenSize);
        view.setVisible(true);
    }
    
    public void gravarDadoTxt(){
        Produtos p = new Produtos();
        p.salvarListaTxt();
        
        Clientes c = new Clientes();
        c.salvarListaTxt();
        
        Fornecedores fornecedores = new Fornecedores();
        fornecedores.salvarListaTxt();
        
        Funcionarios funcionarios = new Funcionarios();
        funcionarios.salvarListaTxt();
        
        Compras compras =  new Compras();
        compras.salvarListaTxt();
        
        Vendas vendas = new Vendas();
        vendas.salvarListaTxt();
    }
    
    public void gravarDadoSerializado(){
        Produtos p = new Produtos();
        p.salvarLista();
        Clientes c = new Clientes();
        c.salvarLista();
        Fornecedores fornecedores = new Fornecedores();
        fornecedores.salvarLista();
        Funcionarios funcionarios = new Funcionarios();
        funcionarios.salvarLista();
        Compras compras =  new Compras();
        compras.salvarLista();
        Vendas vendas = new Vendas();
        vendas.salvarLista();
    }
    
    
}
