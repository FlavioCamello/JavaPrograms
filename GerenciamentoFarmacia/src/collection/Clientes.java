/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collection;

import comparador.ComparadorAbstract;
import comparador.ComparadorProduto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import model.ModelAbstractCliente;
import model.ModelProduto;
import persistence.SerializacaoCliente;
import persistence.TxtCliente;


/**
 *
 * @author Flavio
 */
public class Clientes implements Serializable{
    private static ArrayList<ModelAbstractCliente> clientes = new ArrayList<>();
    private static int idCliente = 0;
        
     public Clientes() {
       
    }
    
    public int getProxIdCliente(){
        idCliente = idCliente + 1;
        return idCliente;
    } 
     
   public void adicionarCliente(ModelAbstractCliente c){
        clientes.add(c);
        ordenarVetor();
    }
   
    public ArrayList retornaVetor() {
        return clientes;
    }

    public void incluirVetor(ArrayList clientes) {
        this.clientes = clientes;
    }
    
    public void excluir(int codCliente) {
        Iterator<ModelAbstractCliente> it = clientes.iterator();
        while (it.hasNext()) {
            ModelAbstractCliente d = it.next();
            if (d.getId() == codCliente) {
                it.remove();
            }
        }
    }
    
    public ModelAbstractCliente retornaProcurado(int codigo) {
        Iterator<ModelAbstractCliente> it = clientes.iterator();
        ModelAbstractCliente cliente = null; // tive que inicializar pois o valor esta dentro do if, e se nao entrar nao tera valor
        while (it.hasNext()) {
            ModelAbstractCliente d = it.next();
            if (d.getId()== codigo) {
                cliente = new ModelAbstractCliente(d.getNome(), d.getEndereco(), d.getTelefone(), d.getDocumento(), d.getLimiteVenda(), d.getId()) {};
            }
        }
        return cliente;
    }
    
    public ModelAbstractCliente retornaProcuradoNome(String nomeCliente) {
        Iterator<ModelAbstractCliente> it = clientes.iterator();
        ModelAbstractCliente cliente = null; // tive que inicializar pois o valor esta dentro do if, e se nao entrar nao tera valor
        while (it.hasNext()) {
            ModelAbstractCliente d = it.next();
            if (d.getNome().equals(nomeCliente)) {
                cliente = new ModelAbstractCliente(d.getNome(), d.getEndereco(), d.getTelefone(), d.getDocumento(), d.getLimiteVenda(), d.getId()) {};
            }
        }
        return cliente;
    }
    
     public void salvarLista() {
        SerializacaoCliente p = new SerializacaoCliente();
        p.grava("cliente");
    }

    public void leitura() {
        SerializacaoCliente pp = new SerializacaoCliente();
        pp.leitura("cliente");
    }
    
    public void salvarListaTxt() {
        TxtCliente p = new TxtCliente();
        p.grava("cliente");
    }

    public void leituraTxt() {
        TxtCliente pp = new TxtCliente();
        pp.leitura("cliente");
    }
    
    public void atualizaId(){
        int idMaior = 0;
        Iterator<ModelAbstractCliente> it = clientes.iterator();
        while (it.hasNext()) {
            ModelAbstractCliente d = it.next();
            if (d.getId() > idMaior) {
                idMaior = d.getId();
            }
        }
        this.idCliente = idMaior;
    }
    
    public void ordenarVetor(){
        List<ModelAbstractCliente> lista = new ArrayList<ModelAbstractCliente>();  
        lista = retornaVetor();
        Collections.sort (lista, new ComparadorAbstract());
        incluirVetor((ArrayList) lista);
    }
}


