/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhoheroislp3;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Persistence;

/**
 *
 * @author Geordano
 */
public class Controle {
    
    Personagem perdedor = new Personagem();

    public Personagem getPerdedor() {
        return perdedor;
    }

    public void setPerdedor(Personagem perdedor) {
        this.perdedor = perdedor;
    }
    
    
    public void IniciaJogo(){
        // iniciar o jogo
    }
    
    public CampoBatalha GerarCampoBatalha(){
        CampoBatalha campo = new CampoBatalha();
        
        return campo;
    }
   
    public void insereRanking(Personagem p1, Arma a1){
      insereRankingPersonagem(p1);
      insereRankingArma(a1);
    }
    
    public void insereRankingPersonagem(Personagem p1){
        p1.setNumVitorias(p1.getNumVitorias()+1);
        
    //inicio gravaçao
        PersonagemJpaController c = new PersonagemJpaController(Persistence.createEntityManagerFactory("TrabalhoHeroisLP3PU"));
        try {
            c.edit(p1);
        } catch (Exception ex) {
            Logger.getLogger(criarPersonagem.class.getName()).log(Level.SEVERE, null, ex);
        }
   //fim gravaçao  
    }
    
    public void insereRankingArma(Arma a1){
        a1.setNumVitorias(a1.getNumVitorias()+1); 
   //inicio gravaçao
         ArmaJpaController c = new ArmaJpaController(Persistence.createEntityManagerFactory("TrabalhoHeroisLP3PU"));
        try {
            c.edit(a1);
        } catch (Exception ex) {
            Logger.getLogger(JanelaArma.class.getName()).log(Level.SEVERE, null, ex);
        }
    //fim gravaçao
    }
    
    public void BuscarRankingPersonagem(){
        
    }
    
    public void BuscarRankingArma(){
        
    }
    
    public CampoBatalha GetCodigoElementoRandom(){
        int max;
        int randomico;
        CampoBatalhaJpaController campoBatalhaJpaController = new CampoBatalhaJpaController(Persistence.createEntityManagerFactory("TrabalhoHeroisLP3PU"));
        max = campoBatalhaJpaController.getCampoBatalhaCount();
        randomico = ((int)(Math.random() * max));        
        return campoBatalhaJpaController.findCampoBatalha(randomico);
    }
    
    public Personagem GetInicioJogo(Personagem p1, Personagem p2, CampoBatalha cp){
        Personagem person = new Personagem();
        if (p1.getIniciativa() > p2.getIniciativa()) {
            person = p1;
        } else if (p1.getIniciativa() < p2.getIniciativa()){
            person = p2;
        } else {
            if (p1.getDefesa() > p2.getDefesa()){
                person = p1;
            } else if (p1.getDefesa() < p2.getDefesa()){
                person = p2;
            } else{
                if (p1.getVida() > p2.getVida()){
                    person = p1;
                } else if (p1.getVida() < p2.getVida()){
                    person = p2;
                } else{
                    if (p1.getAtaque() > p2.getAtaque()){
                        person = p1;
                    } else if (p1.getAtaque() < p2.getAtaque()){
                        person = p2;
                    } else{
                        if (p1.getElemento().getCodigoElemento() == cp.getElemento().getCodigoElemento()){
                            person = p1;
                        } else if (p2.getElemento().getCodigoElemento() == cp.getElemento().getCodigoElemento()){
                            person = p2;
                        } else{
                            if ((int) (Math.random() * 2) == 1) {
                                person = p1;
                            } else {
                                person = p2;
                            }
                        }
                    }        
                }
            }
        }
        return person;
    }
    
    public Personagem GetPersonagemUpgrade(Personagem p1, CampoBatalha cp){
        if (p1.getElemento() == cp.getElemento()) {
            p1.setAtaque(((int)(p1.getAtaque()+(p1.getAtaque()*0.1))));
            p1.setDefesa(((int)(p1.getDefesa()+(p1.getDefesa()*0.1))));
            p1.setVida(((int)(p1.getVida()+(p1.getVida()*0.1))));
            p1.setIniciativa(((int)(p1.getIniciativa()+(p1.getIniciativa()*0.1))));
        }
        return p1;
    }
    
    private int calculaDano(int def, int ata){
        return def - ata;        
    }
    
    public Personagem ComparadorAtributos(Personagem p1, Personagem p2){
        
        
        while ((p1.getVida() > 0) || (p2.getVida() > 0)){
            if ((GetInicioJogo(p1, p2, GerarCampoBatalha())) == p1) {
                if (p2.getDefesa()>0) {
                    if (calculaDano(p2.getDefesa(), p1.getAtaque()) >= 0){
                         p2.setDefesa(calculaDano(p2.getDefesa(), p1.getAtaque()));
                    }else{
                        p2.setDefesa(0);
                        p2.setVida(p2.getVida()+calculaDano(p2.getDefesa(), p1.getAtaque()));
                    }
                }
            } else {
               if (p1.getDefesa()>0) {
                    if (calculaDano(p1.getDefesa(), p2.getAtaque()) >= 0){
                         p1.setDefesa(calculaDano(p1.getDefesa(), p2.getAtaque()));
                    }else{
                        p1.setDefesa(0);
                        p1.setVida(p1.getVida()+calculaDano(p1.getDefesa(), p2.getAtaque()));
                    }
                } 
            }
        }
        if ((p1.getVida() > 0 ) && (p2.getVida() <= 0)){
            setPerdedor(p2);
            insereRanking();
            return p1;
        } else {
            setPerdedor(p1);
            return p2;
        }
    
    }

    
}
