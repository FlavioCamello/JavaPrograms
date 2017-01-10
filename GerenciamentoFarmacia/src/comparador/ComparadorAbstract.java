/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparador;

import java.util.Comparator;
import model.ModelAbstractCliente;
import model.ModelProduto;

/**
 *
 * @author Flavio
 */
public class ComparadorAbstract implements Comparator<ModelAbstractCliente> {  
    public int compare(ModelAbstractCliente o1, ModelAbstractCliente o2) {  
        if (o1.getId() < o2.getId()) return -1;  
        else if (o1.getId() > o2.getId()) return +1;  
        else return 0;  
    }
    
}
