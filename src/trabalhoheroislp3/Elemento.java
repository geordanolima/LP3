/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhoheroislp3;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 *
 * @author Geordano
 */
@Entity
public class Elemento {
    
    @Id
    @GeneratedValue
    private int codigoElemento;
    private String nomeElemento;

    public int getCodigoElemento() {
        return codigoElemento;
    }

    public void setCodigoElemento(int codigoElemento) {
        this.codigoElemento = codigoElemento;
    }

    public String getNomeElemento() {
        return nomeElemento;
    }

    public void setNomeElemento(String nomeElemento) {
        this.nomeElemento = nomeElemento;
    }

    @Override
    public String toString() {
        return nomeElemento;
    }
    
    
    
}
