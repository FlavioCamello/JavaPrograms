/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter.funcionario;

import collection.Funcionarios;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.ModelFuncionario;
import view.Funcionario.ViewCadastrarFuncionario;

/**
 *
 * @author Flavio
 */
public class PresenterCadastrarFuncionario {

    private ViewCadastrarFuncionario view;
    private Funcionarios funcionarios;
    private ModelFuncionario funcionario;

    public PresenterCadastrarFuncionario() {
        view = new ViewCadastrarFuncionario();
        view.getBtnSalvar().addActionListener(new ActionListener() {
        
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    cadastrar();
                    view.dispose();
                    PresenterManterFuncionario pmf = new PresenterManterFuncionario();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Cadastro não foi realizado com sucesso! \n Verifique se todos os campos foram preenchidos! ");
                }
            }
        });
        view.getBtnCancelar().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                PresenterManterFuncionario pmf = new PresenterManterFuncionario();
            }
        });
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }

    public void cadastrar() throws Exception{
        String nome = ((String) view.getTfNome().getText());
        if(nome.equals("")){
            throw new Exception();
        }
        funcionarios = new Funcionarios();
        funcionario = new ModelFuncionario(funcionarios.getProxIdFuncionario(), nome);
        funcionarios.adicionarFuncionario(funcionario);
    }
}
