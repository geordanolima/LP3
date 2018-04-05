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
public class CampoBatalhaJpaController implements Serializable {

    public CampoBatalhaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CampoBatalha campoBatalha) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(campoBatalha);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CampoBatalha campoBatalha) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            campoBatalha = em.merge(campoBatalha);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = campoBatalha.getId();
                if (findCampoBatalha(id) == null) {
                    throw new NonexistentEntityException("The campoBatalha with id " + id + " no longer exists.");
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
            CampoBatalha campoBatalha;
            try {
                campoBatalha = em.getReference(CampoBatalha.class, id);
                campoBatalha.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The campoBatalha with id " + id + " no longer exists.", enfe);
            }
            em.remove(campoBatalha);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CampoBatalha> findCampoBatalhaEntities() {
        return findCampoBatalhaEntities(true, -1, -1);
    }

    public List<CampoBatalha> findCampoBatalhaEntities(int maxResults, int firstResult) {
        return findCampoBatalhaEntities(false, maxResults, firstResult);
    }

    private List<CampoBatalha> findCampoBatalhaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CampoBatalha.class));
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

    public CampoBatalha findCampoBatalha(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CampoBatalha.class, id);
        } finally {
            em.close();
        }
    }

    public int getCampoBatalhaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CampoBatalha> rt = cq.from(CampoBatalha.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
