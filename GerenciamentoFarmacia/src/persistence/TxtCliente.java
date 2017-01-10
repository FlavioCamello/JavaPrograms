/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import collection.Clientes;
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
public class TxtCliente {
    Clientes clientes;

    public TxtCliente() {
    }

    public void grava(String nomeArquivo) {
        try {
            FileOutputStream fo = new FileOutputStream("src/" + nomeArquivo + ".txt");
            ObjectOutputStream oo = new ObjectOutputStream(fo);
            clientes = new Clientes();
            oo.writeObject(clientes.retornaVetor());
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
            clientes = new Clientes();
            clientes.incluirVetor((ArrayList) oi.readObject());
            oi.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
