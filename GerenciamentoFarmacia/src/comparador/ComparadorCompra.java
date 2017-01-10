/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparador;

import java.util.Comparator;
import model.ModelCompra;
import model.ModelProduto;

/**
 *
 * @author Flavio
 */
public class ComparadorCompra implements Comparator<ModelCompra> {  
    public int compare(ModelCompra o1, ModelCompra o2) {  
        if (o1.getId() < o2.getId()) return -1;  
        else if (o1.getId() > o2.getId()) return +1;  
        else return 0;  
    }
    
}
