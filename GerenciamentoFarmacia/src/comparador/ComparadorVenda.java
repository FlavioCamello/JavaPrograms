/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparador;

import java.util.Comparator;
import model.ModelProduto;
import model.ModelVenda;

/**
 *
 * @author Flavio
 */
public class ComparadorVenda implements Comparator<ModelVenda> {  
    public int compare(ModelVenda o1, ModelVenda o2) {  
        if (o1.getId() < o2.getId()) return -1;  
        else if (o1.getId() > o2.getId()) return +1;  
        else return 0;  
    }
    
}
