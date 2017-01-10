/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import collection.Fornecedores;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 *
 * @author Flavio
 */
public class TxtFornecedor {
     Fornecedores fornecedores;

    public TxtFornecedor() {
    }
    
    public void grava(String nomeArquivo) {
        try {
            FileOutputStream fo = new FileOutputStream("src/" + nomeArquivo + ".txt");
            ObjectOutputStream oo = new ObjectOutputStream(fo);
            fornecedores = new Fornecedores();
            oo.writeObject(fornecedores.retornaVetor());
            oo.close();
            System.out.println("Dados gravados com sucesso");
        } catch (Exception e) {
            System.err.println("Erro ao serializar " + e.getMessage());
        }
    }

    public void leitura(String nomeArquivo) {
        try {
            File arq = new File("src/" + nomeArquivo + ".txt");
            FileInputStream fi = new FileInputStream(arq.getAbsolutePath());
            ObjectInputStream oi = new ObjectInputStream(fi);
            fornecedores = new Fornecedores();
            fornecedores.incluirVetor((ArrayList) oi.readObject());
            oi.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
