/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collection;

import comparador.ComparadorCompra;
import comparador.ComparadorVenda;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import model.ModelCompra;
import model.ModelVenda;
import model.ModelVendaItem;
import persistence.SerializacaoVenda;
import persistence.TxtVenda;

/**
 *
 * @author Flavio
 */
public class Vendas {

    private static ArrayList<ModelVenda> vendas = new ArrayList<>();
    private static int idVenda = 0;

    public int getProxIdCompra() {
        idVenda = idVenda + 1;
        return idVenda;
    }

    public void adicionarVenda(ModelVenda c) {
        vendas.add(c);
        ordenarVetor();
    }

    public ArrayList retornaVetor() {
        return vendas;
    }

    public void incluirVetor(ArrayList vendas) {
        this.vendas = vendas;
    }

    public void excluir(int codVenda) {
        Iterator<ModelVenda> it = vendas.iterator();
        while (it.hasNext()) {
            ModelVenda d = it.next();
            if (d.getId() == codVenda) {
                it.remove();
            }
        }
    }

    public ModelVenda retornaVenda(int codigo) {
        Iterator<ModelVenda> it = vendas.iterator();
        ModelVenda venda = null; // tive que inicializar pois o valor esta dentro do if, e se nao entrar nao tera valor
        while (it.hasNext()) {
            ModelVenda d = it.next();
            if (d.getId() == codigo) {
                venda = new ModelVenda(d.getId(), d.getFuncionario(), d.getDesconto(), d.getCliente(), d.getData(), d.getVendaItem());
            }
        }
        return venda;
    }

    public void salvarLista() {
        SerializacaoVenda p = new SerializacaoVenda();
        p.grava("venda");
    }

    public void leitura() {
        SerializacaoVenda pp = new SerializacaoVenda();
        pp.leitura("venda");
    }

    public void salvarListaTxt() {
        TxtVenda p = new TxtVenda();
        p.grava("venda");
    }

    public void leituraTxt() {
        TxtVenda pp = new TxtVenda();
        pp.leitura("venda");
    }

    public void atualizaId() {
        int idMaior = 0;
        Iterator<ModelVenda> it = vendas.iterator();
        while (it.hasNext()) {
            ModelVenda d = it.next();
            if (d.getId() > idMaior) {
                idMaior = d.getId();
            }
        }
        this.idVenda = idMaior;
    }

    public boolean retornaSePodeExcluirCliente(int idProcurado) {
        Iterator<ModelVenda> it = vendas.iterator();
        while (it.hasNext()) {
            ModelVenda d = it.next();
            if (d.getCliente().getId() == idProcurado) {
                return false;
            }
        }
        return true;
    }

    public boolean retornaSePodeExcluirProduto(int idProcurado) {
        Iterator<ModelVenda> it = vendas.iterator();
        while (it.hasNext()) {
            ModelVenda d = it.next();
            Iterator<ModelVendaItem> itVendaItem = d.getVendaItem().iterator();
            while (itVendaItem.hasNext()) {
                ModelVendaItem vendaItemIterator = itVendaItem.next();
                if (vendaItemIterator.getProduto().getId() == idProcurado) {
                    return false;
                }
            }

        }
        return true;
    }
    
    public void ordenarVetor(){
        List<ModelVenda> lista = new ArrayList<ModelVenda>();  
        lista = retornaVetor();
        Collections.sort (lista, new ComparadorVenda());
        incluirVetor((ArrayList) lista);
    }
}
