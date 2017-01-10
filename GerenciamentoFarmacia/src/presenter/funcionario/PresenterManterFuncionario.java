/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter.funcionario;

import collection.Funcionarios;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.table.DefaultTableModel;
import model.ModelFuncionario;
import view.Funcionario.ViewManterFuncionario;

/**
 *
 * @author Flavio
 */
public class PresenterManterFuncionario {
    private ViewManterFuncionario view;
    private Funcionarios funcionarios;
    private ModelFuncionario funcionario;

    public PresenterManterFuncionario() {
        view = new ViewManterFuncionario();
        funcionarios = new Funcionarios();
        carregarTabelaFuncionario();
        view.getBtnCadastrar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                PresenterCadastrarFuncionario pcf = new PresenterCadastrarFuncionario();
                view.dispose();
            }
        });
        view.getBtnCancelar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
            }
        });
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }
     public void carregarTabelaFuncionario(){
        ArrayList retornaVetor = funcionarios.retornaVetor();
        DefaultTableModel tm = new DefaultTableModel( // jowtable reconhece o tableModel
                new Object[][]{},
                new String[]{"Id", "Nome"}) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tm.setNumRows(0);
        Iterator<ModelFuncionario> it = retornaVetor.iterator();
        while (it.hasNext()) {
            ModelFuncionario clienteIterator = it.next();
            String linha = clienteIterator.toString();  //o toString tera que ter os nomes separados por virgula
            String valores[] = linha.split(","); //serve para botar uma palavra em cada posicao do vetor, pois a cada virgula pula pos
            tm.addRow(valores);
        }
        view.getTbFuncionario().setModel(tm);
        
    }
}
