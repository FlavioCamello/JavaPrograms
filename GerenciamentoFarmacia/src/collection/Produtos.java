/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collection;

import comparador.ComparadorProduto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import model.ModelProduto;
import persistence.SerializacaoProduto;
import persistence.TxtProduto;

/**
 *
 * @author Flavio
 */
public class Produtos {

    static ArrayList<ModelProduto> produtos = new ArrayList<ModelProduto>();
    
     private static int idProduto = 0;

    public Produtos() {

    }

    public int getProxIdProduto() {
        idProduto = idProduto + 1;
        return idProduto;
    }

    public void adicionarProduto(ModelProduto p) {
        produtos.add(p);
        ordenarVetor();
    }

    public ArrayList retornaVetor() {
        return produtos;
    }

    public void incluirVetor(ArrayList produtos) {
        this.produtos = produtos;
    }

    public void excluir(int codProduto) {
        Iterator<ModelProduto> it = produtos.iterator();
        while (it.hasNext()) {
            ModelProduto d = it.next();
            if (d.getId() == codProduto) {
                it.remove();
            }
        }
    }

    public ModelProduto retornaProcurado(int codigo) {
        Iterator<ModelProduto> it = produtos.iterator();
        ModelProduto produto = null; // tive que inicializar pois o valor esta dentro do if, e se nao entrar nao tera valor
        while (it.hasNext()) {
            ModelProduto d = it.next();
            if (d.getId() == codigo) {
                produto = new ModelProduto(d.getId(), d.getNome(), d.getPreco(), d.getQtdEstoque());
            }
        }
        return produto;
    }

    public void salvarLista() {
        SerializacaoProduto p = new SerializacaoProduto();
        p.grava("produto");
    }

    public void leitura() {
        SerializacaoProduto pp = new SerializacaoProduto();
        pp.leitura("produto");
    }
    
    public void salvarListaTxt() {
        TxtProduto p = new TxtProduto();
        p.grava("produto");
    }

    public void leituraTxt() {
        TxtProduto pp = new TxtProduto();
        pp.leitura("produto");
    }
    
    public void atualizaId(){
        int idMaior = 0;
        Iterator<ModelProduto> it = produtos.iterator();
        ModelProduto produto = null; // tive que inicializar pois o valor esta dentro do if, e se nao entrar nao tera valor
        while (it.hasNext()) {
            ModelProduto d = it.next();
            if (d.getId() > idMaior) {
                idMaior = d.getId();
            }
        }
        this.idProduto = idMaior;
    }
    
    public void ordenarVetor(){
        List<ModelProduto> lista = new ArrayList<ModelProduto>();  
        lista = retornaVetor();
        Collections.sort (lista, new ComparadorProduto());
        incluirVetor((ArrayList) lista);
    }
}
