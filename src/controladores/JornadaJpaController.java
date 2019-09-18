/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.IllegalOrphanException;
import controladores.exceptions.NonexistentEntityException;
import entidades.Jornada;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.JornadaEmpleado;
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
public class JornadaJpaController implements Serializable {

    public JornadaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("Proyecto1PU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Jornada jornada) {
        if (jornada.getJornadaEmpleadoCollection() == null) {
            jornada.setJornadaEmpleadoCollection(new ArrayList<JornadaEmpleado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<JornadaEmpleado> attachedJornadaEmpleadoCollection = new ArrayList<JornadaEmpleado>();
            for (JornadaEmpleado jornadaEmpleadoCollectionJornadaEmpleadoToAttach : jornada.getJornadaEmpleadoCollection()) {
                jornadaEmpleadoCollectionJornadaEmpleadoToAttach = em.getReference(jornadaEmpleadoCollectionJornadaEmpleadoToAttach.getClass(), jornadaEmpleadoCollectionJornadaEmpleadoToAttach.getIdJornadaEmpleado());
                attachedJornadaEmpleadoCollection.add(jornadaEmpleadoCollectionJornadaEmpleadoToAttach);
            }
            jornada.setJornadaEmpleadoCollection(attachedJornadaEmpleadoCollection);
            em.persist(jornada);
            for (JornadaEmpleado jornadaEmpleadoCollectionJornadaEmpleado : jornada.getJornadaEmpleadoCollection()) {
                Jornada oldIdJornadaOfJornadaEmpleadoCollectionJornadaEmpleado = jornadaEmpleadoCollectionJornadaEmpleado.getIdJornada();
                jornadaEmpleadoCollectionJornadaEmpleado.setIdJornada(jornada);
                jornadaEmpleadoCollectionJornadaEmpleado = em.merge(jornadaEmpleadoCollectionJornadaEmpleado);
                if (oldIdJornadaOfJornadaEmpleadoCollectionJornadaEmpleado != null) {
                    oldIdJornadaOfJornadaEmpleadoCollectionJornadaEmpleado.getJornadaEmpleadoCollection().remove(jornadaEmpleadoCollectionJornadaEmpleado);
                    oldIdJornadaOfJornadaEmpleadoCollectionJornadaEmpleado = em.merge(oldIdJornadaOfJornadaEmpleadoCollectionJornadaEmpleado);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Jornada jornada) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Jornada persistentJornada = em.find(Jornada.class, jornada.getIdJornada());
            Collection<JornadaEmpleado> jornadaEmpleadoCollectionOld = persistentJornada.getJornadaEmpleadoCollection();
            Collection<JornadaEmpleado> jornadaEmpleadoCollectionNew = jornada.getJornadaEmpleadoCollection();
            List<String> illegalOrphanMessages = null;
            for (JornadaEmpleado jornadaEmpleadoCollectionOldJornadaEmpleado : jornadaEmpleadoCollectionOld) {
                if (!jornadaEmpleadoCollectionNew.contains(jornadaEmpleadoCollectionOldJornadaEmpleado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain JornadaEmpleado " + jornadaEmpleadoCollectionOldJornadaEmpleado + " since its idJornada field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<JornadaEmpleado> attachedJornadaEmpleadoCollectionNew = new ArrayList<JornadaEmpleado>();
            for (JornadaEmpleado jornadaEmpleadoCollectionNewJornadaEmpleadoToAttach : jornadaEmpleadoCollectionNew) {
                jornadaEmpleadoCollectionNewJornadaEmpleadoToAttach = em.getReference(jornadaEmpleadoCollectionNewJornadaEmpleadoToAttach.getClass(), jornadaEmpleadoCollectionNewJornadaEmpleadoToAttach.getIdJornadaEmpleado());
                attachedJornadaEmpleadoCollectionNew.add(jornadaEmpleadoCollectionNewJornadaEmpleadoToAttach);
            }
            jornadaEmpleadoCollectionNew = attachedJornadaEmpleadoCollectionNew;
            jornada.setJornadaEmpleadoCollection(jornadaEmpleadoCollectionNew);
            jornada = em.merge(jornada);
            for (JornadaEmpleado jornadaEmpleadoCollectionNewJornadaEmpleado : jornadaEmpleadoCollectionNew) {
                if (!jornadaEmpleadoCollectionOld.contains(jornadaEmpleadoCollectionNewJornadaEmpleado)) {
                    Jornada oldIdJornadaOfJornadaEmpleadoCollectionNewJornadaEmpleado = jornadaEmpleadoCollectionNewJornadaEmpleado.getIdJornada();
                    jornadaEmpleadoCollectionNewJornadaEmpleado.setIdJornada(jornada);
                    jornadaEmpleadoCollectionNewJornadaEmpleado = em.merge(jornadaEmpleadoCollectionNewJornadaEmpleado);
                    if (oldIdJornadaOfJornadaEmpleadoCollectionNewJornadaEmpleado != null && !oldIdJornadaOfJornadaEmpleadoCollectionNewJornadaEmpleado.equals(jornada)) {
                        oldIdJornadaOfJornadaEmpleadoCollectionNewJornadaEmpleado.getJornadaEmpleadoCollection().remove(jornadaEmpleadoCollectionNewJornadaEmpleado);
                        oldIdJornadaOfJornadaEmpleadoCollectionNewJornadaEmpleado = em.merge(oldIdJornadaOfJornadaEmpleadoCollectionNewJornadaEmpleado);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = jornada.getIdJornada();
                if (findJornada(id) == null) {
                    throw new NonexistentEntityException("The jornada with id " + id + " no longer exists.");
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
            Jornada jornada;
            try {
                jornada = em.getReference(Jornada.class, id);
                jornada.getIdJornada();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The jornada with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<JornadaEmpleado> jornadaEmpleadoCollectionOrphanCheck = jornada.getJornadaEmpleadoCollection();
            for (JornadaEmpleado jornadaEmpleadoCollectionOrphanCheckJornadaEmpleado : jornadaEmpleadoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Jornada (" + jornada + ") cannot be destroyed since the JornadaEmpleado " + jornadaEmpleadoCollectionOrphanCheckJornadaEmpleado + " in its jornadaEmpleadoCollection field has a non-nullable idJornada field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(jornada);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Jornada> findJornadaEntities() {
        return findJornadaEntities(true, -1, -1);
    }

    public List<Jornada> findJornadaEntities(int maxResults, int firstResult) {
        return findJornadaEntities(false, maxResults, firstResult);
    }

    private List<Jornada> findJornadaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Jornada.class));
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

    public Jornada findJornada(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Jornada.class, id);
        } finally {
            em.close();
        }
    }

    public int getJornadaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Jornada> rt = cq.from(Jornada.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
