/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collection;

import comparador.ComparadorCompra;
import comparador.ComparadorProduto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import model.ModelCompra;
import model.ModelCompraItem;
import model.ModelProduto;
import persistence.SerializacaoCompra;
import persistence.TxtCompra;

/**
 *
 * @author Flavio
 */
public class Compras {
    private static ArrayList<ModelCompra> compras = new ArrayList<>();
    private static int idCompra = 0;
    public int getProxIdCompra(){
        idCompra = idCompra + 1;
        return idCompra;
    } 
     
   public void adicionarCompra(ModelCompra c){
        compras.add(c);
        ordenarVetor();
    }
   
    public ArrayList retornaVetor() {
        return compras;
    }

    public void incluirVetor(ArrayList compras) {
        this.compras = compras;
    }
    
    public void excluir(int codCompra) {
        Iterator<ModelCompra> it = compras.iterator();
        while (it.hasNext()) {
            ModelCompra d = it.next();
            if (d.getId() == codCompra) {
                it.remove();
            }
        }
    }
    
    public ModelCompra retornaProcurado(int codigo){
        Iterator<ModelCompra> it = compras.iterator();
        ModelCompra compra = null; // tive que inicializar pois o valor esta dentro do if, e se nao entrar nao tera valor
        while (it.hasNext()) {
            ModelCompra d = it.next();
            if (d.getId()== codigo) {
                compra = new ModelCompra(d.getId(), d.getFornecedor(), d.getCompraItem(), d.getData());
            }
        }
        return compra;
    }
    
    public void salvarLista() {
        SerializacaoCompra p = new SerializacaoCompra();
        p.grava("compra");
    }

    public void leitura() {
        SerializacaoCompra pp = new SerializacaoCompra();
        pp.leitura("compra");
    }
    
     public void salvarListaTxt() {
        TxtCompra p = new TxtCompra();
        p.grava("compra");
    }

    public void leituraTxt() {
        TxtCompra pp = new TxtCompra();
        pp.leitura("compra");
    }
    
    public void atualizaId(){
        int idMaior = 0;
        Iterator<ModelCompra> it = compras.iterator();
        while (it.hasNext()) {
            ModelCompra d = it.next();
            if (d.getId() > idMaior) {
                idMaior = d.getId();
            }
        }
        this.idCompra = idMaior;
    }
    
    public boolean retornaSePodeExcluirFornecedor(int idProcurado){
        Iterator<ModelCompra> it = compras.iterator();
        while (it.hasNext()) {
            ModelCompra d = it.next();
            if (d.getFornecedor().getId() == idProcurado) {
                return false;
            }
        }
        return true;
    }
    
    public boolean retornaSePodeExcluirProduto(int idProcurado) {
        Iterator<ModelCompra> it = compras.iterator();
        while (it.hasNext()) {
            ModelCompra d = it.next();
            Iterator<ModelCompraItem> itCompraItem = d.getCompraItem().iterator();
            while (itCompraItem.hasNext()) {
                ModelCompraItem compraItemIterator = itCompraItem.next();
                if (compraItemIterator.getProduto().getId() == idProcurado) {
                    return false;
                }
            }

        }
        return true;
    }
    
    public boolean retornaFornecedorPossuiCompra(int idProcurado) {
        Iterator<ModelCompra> it = compras.iterator();
        while (it.hasNext()) {
            ModelCompra d = it.next();
                if (d.getFornecedor().getId() == idProcurado) {
                    return true;
                }
            }
        return false;
    }
    
    public boolean retornaFornecedorNaoPossuiCompra(int idProcurado) {
        Iterator<ModelCompra> it = compras.iterator();
        while (it.hasNext()) {
            ModelCompra d = it.next();
                if (d.getFornecedor().getId() == idProcurado) {
                    return false;
                }
            }
        return true;
    }
    
    public void ordenarVetor(){
        List<ModelCompra> lista = new ArrayList<ModelCompra>();  
        lista = retornaVetor();
        Collections.sort (lista, new ComparadorCompra());
        incluirVetor((ArrayList) lista);
    }
}
