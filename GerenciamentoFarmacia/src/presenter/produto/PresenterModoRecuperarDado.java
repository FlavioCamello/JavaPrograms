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
import view.produto.ViewModoRecuperarDado;

/**
 *
 * @author Flavio
 */
public class PresenterModoRecuperarDado {

    private ViewModoRecuperarDado view;

    public PresenterModoRecuperarDado() {
        view = new ViewModoRecuperarDado();
        view.getBtnSerializacao().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                carregarDadoSerializado();
                view.dispose();
                PresenterPrincipal pp = new PresenterPrincipal();
            }
        });

        view.getBtnTexto().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                carregarDadoTxt();
                view.dispose();
                PresenterPrincipal pp = new PresenterPrincipal();
            }
        });
        Dimension screenSize = view.getToolkit().getScreenSize();  
        view.setSize(screenSize); 
        view.setVisible(true);
    }

    public void carregarDadoTxt(){
        Produtos produtos = new Produtos();
        produtos.leituraTxt();
        produtos.atualizaId();

        Clientes clientes = new Clientes();
        clientes.leituraTxt();
        clientes.atualizaId();

        Fornecedores fornecedores = new Fornecedores();
        fornecedores.leituraTxt();
        fornecedores.atualizaId();

        Funcionarios funcionarios = new Funcionarios();
        funcionarios.leituraTxt();
        funcionarios.atualizaId();

        Compras compras = new Compras();
        compras.leituraTxt();
        compras.atualizaId();

        Vendas vendas = new Vendas();
        vendas.leituraTxt();
        vendas.atualizaId();
    }
    
    public void carregarDadoSerializado() {
        Produtos produtos = new Produtos();
        produtos.leitura();
        produtos.atualizaId();

        Clientes clientes = new Clientes();
        clientes.leitura();
        clientes.atualizaId();

        Fornecedores fornecedores = new Fornecedores();
        fornecedores.leitura();
        fornecedores.atualizaId();

        Funcionarios funcionarios = new Funcionarios();
        funcionarios.leitura();
        funcionarios.atualizaId();

        Compras compras = new Compras();
        compras.leitura();
        compras.atualizaId();

        Vendas vendas = new Vendas();
        vendas.leitura();
        vendas.atualizaId();
    }
}
