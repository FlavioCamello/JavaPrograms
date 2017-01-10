/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import model.ModelFuncionario;
import persistence.SerializacaoFuncionario;
import persistence.TxtFuncionario;


/**
 *
 * @author Flavio
 */
public class Funcionarios implements Serializable {
    private static ArrayList<ModelFuncionario> funcionarios = new ArrayList<>();
    private static int idfuncionario = 0;
        
     public Funcionarios() {
       
    }
    
    public int getProxIdFuncionario(){
        idfuncionario = idfuncionario + 1;
        return idfuncionario;
    } 
     
   public void adicionarFuncionario(ModelFuncionario c){
        funcionarios.add(c);
    }
   
    public ArrayList retornaVetor() {
        return funcionarios;
    }

    public void incluirVetor(ArrayList funcionarios) {
        this.funcionarios = funcionarios;
    }
    
    public void excluir(int codCliente) {
        Iterator<ModelFuncionario> it = funcionarios.iterator();
        while (it.hasNext()) {
            ModelFuncionario d = it.next();
            if (d.getId() == codCliente) {
                it.remove();
            }
        }
    }
    
    public ModelFuncionario retornaProcurado(int codigo) {
        Iterator<ModelFuncionario> it = funcionarios.iterator();
        ModelFuncionario funcionario = null; // tive que inicializar pois o valor esta dentro do if, e se nao entrar nao tera valor
        while (it.hasNext()) {
            ModelFuncionario d = it.next();
            if (d.getId()== codigo) {
                funcionario = new ModelFuncionario( d.getId(), d.getNome()) {};
            }
        }
        return funcionario;
    }
    
    public ModelFuncionario retornaProcuradoNome(String nomeFornecedor) {
        Iterator<ModelFuncionario> it = funcionarios.iterator();
        ModelFuncionario funcionario = null; // tive que inicializar pois o valor esta dentro do if, e se nao entrar nao tera valor
        while (it.hasNext()) {
            ModelFuncionario d = it.next();
            if (d.getNome().equals(nomeFornecedor)) {
                funcionario = new ModelFuncionario( d.getId(), d.getNome()) {};
            }
        }
        return funcionario;
    }
    
    public void salvarLista() {
        SerializacaoFuncionario p = new SerializacaoFuncionario();
        p.grava("funcionario");
    }

    public void leitura() {
        SerializacaoFuncionario pp = new SerializacaoFuncionario();
        pp.leitura("funcionario");
    }
    
    public void salvarListaTxt() {
        TxtFuncionario p = new TxtFuncionario();
        p.grava("funcionario");
    }

    public void leituraTxt() {
        TxtFuncionario pp = new TxtFuncionario();
        pp.leitura("funcionario");
    }
    
    public void atualizaId(){
        int idMaior = 0;
        Iterator<ModelFuncionario> it = funcionarios.iterator();
        while (it.hasNext()) {
            ModelFuncionario d = it.next();
            if (d.getId() > idMaior) {
                idMaior = d.getId();
            }
        }
        this.idfuncionario = idMaior;
    }
}