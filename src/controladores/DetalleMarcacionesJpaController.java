/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import entidades.DetalleMarcaciones;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Marcaciones;
import java.util.ArrayList;
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
        this.emf = Persistence.createEntityManagerFactory("ProyectoPU") ;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetalleMarcaciones detalleMarcaciones) {
        if (detalleMarcaciones.getMarcacionesList() == null) {
            detalleMarcaciones.setMarcacionesList(new ArrayList<Marcaciones>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Marcaciones> attachedMarcacionesList = new ArrayList<Marcaciones>();
            for (Marcaciones marcacionesListMarcacionesToAttach : detalleMarcaciones.getMarcacionesList()) {
                marcacionesListMarcacionesToAttach = em.getReference(marcacionesListMarcacionesToAttach.getClass(), marcacionesListMarcacionesToAttach.getIdMarcacion());
                attachedMarcacionesList.add(marcacionesListMarcacionesToAttach);
            }
            detalleMarcaciones.setMarcacionesList(attachedMarcacionesList);
            em.persist(detalleMarcaciones);
            for (Marcaciones marcacionesListMarcaciones : detalleMarcaciones.getMarcacionesList()) {
                DetalleMarcaciones oldIdDetalleMarcacionOfMarcacionesListMarcaciones = marcacionesListMarcaciones.getIdDetalleMarcacion();
                marcacionesListMarcaciones.setIdDetalleMarcacion(detalleMarcaciones);
                marcacionesListMarcaciones = em.merge(marcacionesListMarcaciones);
                if (oldIdDetalleMarcacionOfMarcacionesListMarcaciones != null) {
                    oldIdDetalleMarcacionOfMarcacionesListMarcaciones.getMarcacionesList().remove(marcacionesListMarcaciones);
                    oldIdDetalleMarcacionOfMarcacionesListMarcaciones = em.merge(oldIdDetalleMarcacionOfMarcacionesListMarcaciones);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetalleMarcaciones detalleMarcaciones) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleMarcaciones persistentDetalleMarcaciones = em.find(DetalleMarcaciones.class, detalleMarcaciones.getIdDetalleMarcacion());
            List<Marcaciones> marcacionesListOld = persistentDetalleMarcaciones.getMarcacionesList();
            List<Marcaciones> marcacionesListNew = detalleMarcaciones.getMarcacionesList();
            List<Marcaciones> attachedMarcacionesListNew = new ArrayList<Marcaciones>();
            for (Marcaciones marcacionesListNewMarcacionesToAttach : marcacionesListNew) {
                marcacionesListNewMarcacionesToAttach = em.getReference(marcacionesListNewMarcacionesToAttach.getClass(), marcacionesListNewMarcacionesToAttach.getIdMarcacion());
                attachedMarcacionesListNew.add(marcacionesListNewMarcacionesToAttach);
            }
            marcacionesListNew = attachedMarcacionesListNew;
            detalleMarcaciones.setMarcacionesList(marcacionesListNew);
            detalleMarcaciones = em.merge(detalleMarcaciones);
            for (Marcaciones marcacionesListOldMarcaciones : marcacionesListOld) {
                if (!marcacionesListNew.contains(marcacionesListOldMarcaciones)) {
                    marcacionesListOldMarcaciones.setIdDetalleMarcacion(null);
                    marcacionesListOldMarcaciones = em.merge(marcacionesListOldMarcaciones);
                }
            }
            for (Marcaciones marcacionesListNewMarcaciones : marcacionesListNew) {
                if (!marcacionesListOld.contains(marcacionesListNewMarcaciones)) {
                    DetalleMarcaciones oldIdDetalleMarcacionOfMarcacionesListNewMarcaciones = marcacionesListNewMarcaciones.getIdDetalleMarcacion();
                    marcacionesListNewMarcaciones.setIdDetalleMarcacion(detalleMarcaciones);
                    marcacionesListNewMarcaciones = em.merge(marcacionesListNewMarcaciones);
                    if (oldIdDetalleMarcacionOfMarcacionesListNewMarcaciones != null && !oldIdDetalleMarcacionOfMarcacionesListNewMarcaciones.equals(detalleMarcaciones)) {
                        oldIdDetalleMarcacionOfMarcacionesListNewMarcaciones.getMarcacionesList().remove(marcacionesListNewMarcaciones);
                        oldIdDetalleMarcacionOfMarcacionesListNewMarcaciones = em.merge(oldIdDetalleMarcacionOfMarcacionesListNewMarcaciones);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detalleMarcaciones.getIdDetalleMarcacion();
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

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleMarcaciones detalleMarcaciones;
            try {
                detalleMarcaciones = em.getReference(DetalleMarcaciones.class, id);
                detalleMarcaciones.getIdDetalleMarcacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalleMarcaciones with id " + id + " no longer exists.", enfe);
            }
            List<Marcaciones> marcacionesList = detalleMarcaciones.getMarcacionesList();
            for (Marcaciones marcacionesListMarcaciones : marcacionesList) {
                marcacionesListMarcaciones.setIdDetalleMarcacion(null);
                marcacionesListMarcaciones = em.merge(marcacionesListMarcaciones);
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
