/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collection;

import comparador.ComparadorAbstract;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import model.ModelAbstractCliente;
import persistence.SerializacaoFornecedor;
import persistence.TxtFornecedor;

/**
 *
 * @author Flavio
 */
public class Fornecedores implements Serializable {
    private static ArrayList<ModelAbstractCliente> fornecedores = new ArrayList<>();
    private static int idFornecedor = 0;
    
     public Fornecedores() {
       
    }
    
    public int getProxIdCliente(){
        idFornecedor = idFornecedor + 1;
        return idFornecedor;
    } 
     
   public void adicionarFornecedor(ModelAbstractCliente c){
        fornecedores.add(c);
        ordenarVetor();
    }
   
    public ArrayList retornaVetor() {
        return fornecedores;
    }

    public void incluirVetor(ArrayList fornecedores) {
        this.fornecedores = fornecedores;
    }
    
    public void excluir(int codFornecedor) {
        Iterator<ModelAbstractCliente> it = fornecedores.iterator();
        while (it.hasNext()) {
            ModelAbstractCliente d = it.next();
            if (d.getId() == codFornecedor) {
                it.remove();
            }
        }
    }
    
    public ModelAbstractCliente retornaProcurado(int codigo) {
        Iterator<ModelAbstractCliente> it = fornecedores.iterator();
        ModelAbstractCliente fornecedor = null; // tive que inicializar pois o valor esta dentro do if, e se nao entrar nao tera valor
        while (it.hasNext()) {
            ModelAbstractCliente d = it.next();
            if (d.getId()== codigo) {
                fornecedor = new ModelAbstractCliente(d.getNome(), d.getEndereco(), d.getTelefone(), d.getDocumento(), d.getId()) {};
            }
        }
        return fornecedor;
    }

    public ModelAbstractCliente retornaProcuradoNome(String nomeFornecedor) {
        Iterator<ModelAbstractCliente> it = fornecedores.iterator();
        ModelAbstractCliente fornecedor = null; // tive que inicializar pois o valor esta dentro do if, e se nao entrar nao tera valor
        while (it.hasNext()) {
            ModelAbstractCliente d = it.next();
            if (d.getNome().equals(nomeFornecedor)) {
                fornecedor = new ModelAbstractCliente(d.getNome(), d.getEndereco(), d.getTelefone(), d.getDocumento(), d.getId()) {};
            }
        }
        return fornecedor;
    }
    
    public void salvarLista() {
        SerializacaoFornecedor p = new SerializacaoFornecedor();
        p.grava("fornecedor");
    }

    public void leitura() {
        SerializacaoFornecedor pp = new SerializacaoFornecedor();
        pp.leitura("fornecedor");
    }
    
    public void salvarListaTxt() {
        TxtFornecedor p = new TxtFornecedor();
        p.grava("fornecedor");
    }

    public void leituraTxt() {
        TxtFornecedor pp = new TxtFornecedor();
        pp.leitura("fornecedor");
    }
    
    public void atualizaId(){
        int idMaior = 0;
        Iterator<ModelAbstractCliente> it = fornecedores.iterator();
        while (it.hasNext()) {
            ModelAbstractCliente d = it.next();
            if (d.getId() > idMaior) {
                idMaior = d.getId();
            }
        }
        this.idFornecedor = idMaior;
    }
    
    public void ordenarVetor(){
        List<ModelAbstractCliente> lista = new ArrayList<ModelAbstractCliente>();  
        lista = retornaVetor();
        Collections.sort (lista, new ComparadorAbstract());
        incluirVetor((ArrayList) lista);
    }
}
