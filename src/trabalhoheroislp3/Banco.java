/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhoheroislp3;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author 20161cmq.ads0220
 */
public class Banco {

    public EntityManagerFactory emf;

    public EntityManager em;

    public Banco() {
        emf = Persistence.createEntityManagerFactory("TrabalhoHeroisLP3PU");
        em = emf.createEntityManager();
    }

    public void inserir(Personagem personagem) {
        em.getTransaction().begin();
        em.persist(personagem);
        em.getTransaction().commit();
    }

    public Personagem ler(int id) {
        em.getTransaction().begin();
        Personagem personagem = em.find(Personagem.class, id);
        em.getTransaction().commit();
        return personagem;
    }

    public List<Personagem> lerTodos() {
        List<Personagem> personagens;
        Query query = em.createQuery("select * from personagem");
        personagens = query.getResultList();
        return personagens;
    }

    public void atualiza(Personagem personagem) {
        em.getTransaction().begin();
        em.merge(personagem);
        em.getTransaction().commit();
    }

    public void remove(int id) {
        Personagem personagem = em.find(Personagem.class, id);
        if (personagem != null) {
            em.getTransaction().begin();
            em.remove(personagem);
            em.getTransaction().commit();
        } else {
            System.err.println("Nao foi possivel remover. ID nao encontrado");
        }
    }

    public void fechar() {
        em.close();
        emf.close();
    }

}
