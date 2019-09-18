/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import entidades.Jornada;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.JornadaEmpleado;
import java.util.ArrayList;
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
               this.emf = Persistence.createEntityManagerFactory("ProyectoPU") ;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Jornada jornada) {
        if (jornada.getJornadaEmpleadoList() == null) {
            jornada.setJornadaEmpleadoList(new ArrayList<JornadaEmpleado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<JornadaEmpleado> attachedJornadaEmpleadoList = new ArrayList<JornadaEmpleado>();
            for (JornadaEmpleado jornadaEmpleadoListJornadaEmpleadoToAttach : jornada.getJornadaEmpleadoList()) {
                jornadaEmpleadoListJornadaEmpleadoToAttach = em.getReference(jornadaEmpleadoListJornadaEmpleadoToAttach.getClass(), jornadaEmpleadoListJornadaEmpleadoToAttach.getIdJornadaEmpleado());
                attachedJornadaEmpleadoList.add(jornadaEmpleadoListJornadaEmpleadoToAttach);
            }
            jornada.setJornadaEmpleadoList(attachedJornadaEmpleadoList);
            em.persist(jornada);
            for (JornadaEmpleado jornadaEmpleadoListJornadaEmpleado : jornada.getJornadaEmpleadoList()) {
                Jornada oldIdJornadaOfJornadaEmpleadoListJornadaEmpleado = jornadaEmpleadoListJornadaEmpleado.getIdJornada();
                jornadaEmpleadoListJornadaEmpleado.setIdJornada(jornada);
                jornadaEmpleadoListJornadaEmpleado = em.merge(jornadaEmpleadoListJornadaEmpleado);
                if (oldIdJornadaOfJornadaEmpleadoListJornadaEmpleado != null) {
                    oldIdJornadaOfJornadaEmpleadoListJornadaEmpleado.getJornadaEmpleadoList().remove(jornadaEmpleadoListJornadaEmpleado);
                    oldIdJornadaOfJornadaEmpleadoListJornadaEmpleado = em.merge(oldIdJornadaOfJornadaEmpleadoListJornadaEmpleado);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Jornada jornada) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Jornada persistentJornada = em.find(Jornada.class, jornada.getIdJornada());
            List<JornadaEmpleado> jornadaEmpleadoListOld = persistentJornada.getJornadaEmpleadoList();
            List<JornadaEmpleado> jornadaEmpleadoListNew = jornada.getJornadaEmpleadoList();
            List<JornadaEmpleado> attachedJornadaEmpleadoListNew = new ArrayList<JornadaEmpleado>();
            for (JornadaEmpleado jornadaEmpleadoListNewJornadaEmpleadoToAttach : jornadaEmpleadoListNew) {
                jornadaEmpleadoListNewJornadaEmpleadoToAttach = em.getReference(jornadaEmpleadoListNewJornadaEmpleadoToAttach.getClass(), jornadaEmpleadoListNewJornadaEmpleadoToAttach.getIdJornadaEmpleado());
                attachedJornadaEmpleadoListNew.add(jornadaEmpleadoListNewJornadaEmpleadoToAttach);
            }
            jornadaEmpleadoListNew = attachedJornadaEmpleadoListNew;
            jornada.setJornadaEmpleadoList(jornadaEmpleadoListNew);
            jornada = em.merge(jornada);
            for (JornadaEmpleado jornadaEmpleadoListOldJornadaEmpleado : jornadaEmpleadoListOld) {
                if (!jornadaEmpleadoListNew.contains(jornadaEmpleadoListOldJornadaEmpleado)) {
                    jornadaEmpleadoListOldJornadaEmpleado.setIdJornada(null);
                    jornadaEmpleadoListOldJornadaEmpleado = em.merge(jornadaEmpleadoListOldJornadaEmpleado);
                }
            }
            for (JornadaEmpleado jornadaEmpleadoListNewJornadaEmpleado : jornadaEmpleadoListNew) {
                if (!jornadaEmpleadoListOld.contains(jornadaEmpleadoListNewJornadaEmpleado)) {
                    Jornada oldIdJornadaOfJornadaEmpleadoListNewJornadaEmpleado = jornadaEmpleadoListNewJornadaEmpleado.getIdJornada();
                    jornadaEmpleadoListNewJornadaEmpleado.setIdJornada(jornada);
                    jornadaEmpleadoListNewJornadaEmpleado = em.merge(jornadaEmpleadoListNewJornadaEmpleado);
                    if (oldIdJornadaOfJornadaEmpleadoListNewJornadaEmpleado != null && !oldIdJornadaOfJornadaEmpleadoListNewJornadaEmpleado.equals(jornada)) {
                        oldIdJornadaOfJornadaEmpleadoListNewJornadaEmpleado.getJornadaEmpleadoList().remove(jornadaEmpleadoListNewJornadaEmpleado);
                        oldIdJornadaOfJornadaEmpleadoListNewJornadaEmpleado = em.merge(oldIdJornadaOfJornadaEmpleadoListNewJornadaEmpleado);
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

    public void destroy(Integer id) throws NonexistentEntityException {
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
            List<JornadaEmpleado> jornadaEmpleadoList = jornada.getJornadaEmpleadoList();
            for (JornadaEmpleado jornadaEmpleadoListJornadaEmpleado : jornadaEmpleadoList) {
                jornadaEmpleadoListJornadaEmpleado.setIdJornada(null);
                jornadaEmpleadoListJornadaEmpleado = em.merge(jornadaEmpleadoListJornadaEmpleado);
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
