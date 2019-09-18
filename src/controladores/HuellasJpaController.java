/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Empleados;
import entidades.Huellas;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author user
 */
public class HuellasJpaController implements Serializable {

    public HuellasJpaController() {
        this.emf = Persistence.createEntityManagerFactory("ProyectoPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Huellas huellas) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleados idEmpleado = huellas.getIdEmpleado();
            if (idEmpleado != null) {
                idEmpleado = em.getReference(idEmpleado.getClass(), idEmpleado.getIdEmpleado());
                huellas.setIdEmpleado(idEmpleado);
            }
            em.persist(huellas);
            if (idEmpleado != null) {
                idEmpleado.getHuellasList().add(huellas);
                idEmpleado = em.merge(idEmpleado);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Huellas huellas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Huellas persistentHuellas = em.find(Huellas.class, huellas.getIdHuella());
            Empleados idEmpleadoOld = persistentHuellas.getIdEmpleado();
            Empleados idEmpleadoNew = huellas.getIdEmpleado();
            if (idEmpleadoNew != null) {
                idEmpleadoNew = em.getReference(idEmpleadoNew.getClass(), idEmpleadoNew.getIdEmpleado());
                huellas.setIdEmpleado(idEmpleadoNew);
            }
            huellas = em.merge(huellas);
            if (idEmpleadoOld != null && !idEmpleadoOld.equals(idEmpleadoNew)) {
                idEmpleadoOld.getHuellasList().remove(huellas);
                idEmpleadoOld = em.merge(idEmpleadoOld);
            }
            if (idEmpleadoNew != null && !idEmpleadoNew.equals(idEmpleadoOld)) {
                idEmpleadoNew.getHuellasList().add(huellas);
                idEmpleadoNew = em.merge(idEmpleadoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = huellas.getIdHuella();
                if (findHuellas(id) == null) {
                    throw new NonexistentEntityException("The huellas with id " + id + " no longer exists.");
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
            Huellas huellas;
            try {
                huellas = em.getReference(Huellas.class, id);
                huellas.getIdHuella();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The huellas with id " + id + " no longer exists.", enfe);
            }
            Empleados idEmpleado = huellas.getIdEmpleado();
            if (idEmpleado != null) {
                idEmpleado.getHuellasList().remove(huellas);
                idEmpleado = em.merge(idEmpleado);
            }
            em.remove(huellas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Huellas> findHuellasEntities() {
        return findHuellasEntities(true, -1, -1);
    }

    public List<Huellas> findHuellasEntities(int maxResults, int firstResult) {
        return findHuellasEntities(false, maxResults, firstResult);
    }

    private List<Huellas> findHuellasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Huellas.class));
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

    public Huellas findHuellas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Huellas.class, id);
        } finally {
            em.close();
        }
    }

    public int getHuellasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Huellas> rt = cq.from(Huellas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
