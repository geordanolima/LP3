/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhoheroislp3;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 *
 * @author Geordano
 */
@Entity

public class Personagem extends Atributos implements Serializable, Comparable {

    @ManyToOne(cascade = CascadeType.ALL)
    private Elemento elemento;

    @Override
    public String toString() {
        return this.getNome();
    }
    @ManyToOne(cascade = CascadeType.ALL)
    private Arma arma;
    private String caminho;
    private int numVitorias;

    public int getNumVitorias() {
        return numVitorias;
    }

    public void setNumVitorias(int numVitorias) {
        this.numVitorias = numVitorias;
    }

    public Elemento getElemento() {
        return elemento;
    }

    public void setElemento(Elemento elemento) {
        this.elemento = elemento;
    }

    public Arma getArma() {
        return arma;
    }

    public void setArma(Arma arma) {
        this.arma = arma;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }
    
    @Override
    public int compareTo(Object o) {
        if (numVitorias == ((Personagem) o).getNumVitorias()) {
            return 0;
        } else {
            if (numVitorias < ((Personagem) o).getNumVitorias()) {
                return 1;
            } else {
                return -1;
            }
        }
    }

}
