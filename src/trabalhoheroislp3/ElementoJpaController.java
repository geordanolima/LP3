/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhoheroislp3;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import trabalhoheroislp3.exceptions.NonexistentEntityException;

/**
 *
 * @author 20161cmq.ads0220
 */
public class ElementoJpaController implements Serializable {

    public ElementoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Elemento elemento) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(elemento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Elemento elemento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            elemento = em.merge(elemento);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = elemento.getCodigoElemento();
                if (findElemento(id) == null) {
                    throw new NonexistentEntityException("The elemento with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Elemento elemento;
            try {
                elemento = em.getReference(Elemento.class, id);
                elemento.getCodigoElemento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The elemento with id " + id + " no longer exists.", enfe);
            }
            em.remove(elemento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Elemento> findElementoEntities() {
        return findElementoEntities(true, -1, -1);
    }

    public List<Elemento> findElementoEntities(int maxResults, int firstResult) {
        return findElementoEntities(false, maxResults, firstResult);
    }

    private List<Elemento> findElementoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Elemento.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Elemento findElemento(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Elemento.class, id);
        } finally {
            em.close();
        }
    }

    public int getElementoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Elemento> rt = cq.from(Elemento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
