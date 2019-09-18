/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.IllegalOrphanException;
import controladores.exceptions.NonexistentEntityException;
import entidades.DetalleMarcaciones;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Marcaciones;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author user
 */
public class DetalleMarcacionesJpaController implements Serializable {

    public DetalleMarcacionesJpaController() {
        this.emf = Persistence.createEntityManagerFactory("Proyecto1PU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetalleMarcaciones detalleMarcaciones) {
        if (detalleMarcaciones.getMarcacionesCollection() == null) {
            detalleMarcaciones.setMarcacionesCollection(new ArrayList<Marcaciones>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Marcaciones> attachedMarcacionesCollection = new ArrayList<Marcaciones>();
            for (Marcaciones marcacionesCollectionMarcacionesToAttach : detalleMarcaciones.getMarcacionesCollection()) {
                marcacionesCollectionMarcacionesToAttach = em.getReference(marcacionesCollectionMarcacionesToAttach.getClass(), marcacionesCollectionMarcacionesToAttach.getIdMarcaciones());
                attachedMarcacionesCollection.add(marcacionesCollectionMarcacionesToAttach);
            }
            detalleMarcaciones.setMarcacionesCollection(attachedMarcacionesCollection);
            em.persist(detalleMarcaciones);
            for (Marcaciones marcacionesCollectionMarcaciones : detalleMarcaciones.getMarcacionesCollection()) {
                DetalleMarcaciones oldIdDetalleMarcacionOfMarcacionesCollectionMarcaciones = marcacionesCollectionMarcaciones.getIdDetalleMarcacion();
                marcacionesCollectionMarcaciones.setIdDetalleMarcacion(detalleMarcaciones);
                marcacionesCollectionMarcaciones = em.merge(marcacionesCollectionMarcaciones);
                if (oldIdDetalleMarcacionOfMarcacionesCollectionMarcaciones != null) {
                    oldIdDetalleMarcacionOfMarcacionesCollectionMarcaciones.getMarcacionesCollection().remove(marcacionesCollectionMarcaciones);
                    oldIdDetalleMarcacionOfMarcacionesCollectionMarcaciones = em.merge(oldIdDetalleMarcacionOfMarcacionesCollectionMarcaciones);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetalleMarcaciones detalleMarcaciones) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleMarcaciones persistentDetalleMarcaciones = em.find(DetalleMarcaciones.class, detalleMarcaciones.getIdDetalleMarcaciones());
            Collection<Marcaciones> marcacionesCollectionOld = persistentDetalleMarcaciones.getMarcacionesCollection();
            Collection<Marcaciones> marcacionesCollectionNew = detalleMarcaciones.getMarcacionesCollection();
            List<String> illegalOrphanMessages = null;
            for (Marcaciones marcacionesCollectionOldMarcaciones : marcacionesCollectionOld) {
                if (!marcacionesCollectionNew.contains(marcacionesCollectionOldMarcaciones)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Marcaciones " + marcacionesCollectionOldMarcaciones + " since its idDetalleMarcacion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Marcaciones> attachedMarcacionesCollectionNew = new ArrayList<Marcaciones>();
            for (Marcaciones marcacionesCollectionNewMarcacionesToAttach : marcacionesCollectionNew) {
                marcacionesCollectionNewMarcacionesToAttach = em.getReference(marcacionesCollectionNewMarcacionesToAttach.getClass(), marcacionesCollectionNewMarcacionesToAttach.getIdMarcaciones());
                attachedMarcacionesCollectionNew.add(marcacionesCollectionNewMarcacionesToAttach);
            }
            marcacionesCollectionNew = attachedMarcacionesCollectionNew;
            detalleMarcaciones.setMarcacionesCollection(marcacionesCollectionNew);
            detalleMarcaciones = em.merge(detalleMarcaciones);
            for (Marcaciones marcacionesCollectionNewMarcaciones : marcacionesCollectionNew) {
                if (!marcacionesCollectionOld.contains(marcacionesCollectionNewMarcaciones)) {
                    DetalleMarcaciones oldIdDetalleMarcacionOfMarcacionesCollectionNewMarcaciones = marcacionesCollectionNewMarcaciones.getIdDetalleMarcacion();
                    marcacionesCollectionNewMarcaciones.setIdDetalleMarcacion(detalleMarcaciones);
                    marcacionesCollectionNewMarcaciones = em.merge(marcacionesCollectionNewMarcaciones);
                    if (oldIdDetalleMarcacionOfMarcacionesCollectionNewMarcaciones != null && !oldIdDetalleMarcacionOfMarcacionesCollectionNewMarcaciones.equals(detalleMarcaciones)) {
                        oldIdDetalleMarcacionOfMarcacionesCollectionNewMarcaciones.getMarcacionesCollection().remove(marcacionesCollectionNewMarcaciones);
                        oldIdDetalleMarcacionOfMarcacionesCollectionNewMarcaciones = em.merge(oldIdDetalleMarcacionOfMarcacionesCollectionNewMarcaciones);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detalleMarcaciones.getIdDetalleMarcaciones();
                if (findDetalleMarcaciones(id) == null) {
                    throw new NonexistentEntityException("The detalleMarcaciones with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleMarcaciones detalleMarcaciones;
            try {
                detalleMarcaciones = em.getReference(DetalleMarcaciones.class, id);
                detalleMarcaciones.getIdDetalleMarcaciones();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalleMarcaciones with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Marcaciones> marcacionesCollectionOrphanCheck = detalleMarcaciones.getMarcacionesCollection();
            for (Marcaciones marcacionesCollectionOrphanCheckMarcaciones : marcacionesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This DetalleMarcaciones (" + detalleMarcaciones + ") cannot be destroyed since the Marcaciones " + marcacionesCollectionOrphanCheckMarcaciones + " in its marcacionesCollection field has a non-nullable idDetalleMarcacion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(detalleMarcaciones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetalleMarcaciones> findDetalleMarcacionesEntities() {
        return findDetalleMarcacionesEntities(true, -1, -1);
    }

    public List<DetalleMarcaciones> findDetalleMarcacionesEntities(int maxResults, int firstResult) {
        return findDetalleMarcacionesEntities(false, maxResults, firstResult);
    }

    private List<DetalleMarcaciones> findDetalleMarcacionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetalleMarcaciones.class));
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

    public DetalleMarcaciones findDetalleMarcaciones(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetalleMarcaciones.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetalleMarcacionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetalleMarcaciones> rt = cq.from(DetalleMarcaciones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
