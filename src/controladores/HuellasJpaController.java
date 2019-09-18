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
        this.emf = Persistence.createEntityManagerFactory("Proyecto1PU");
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
            Empleados idEmpleados = huellas.getIdEmpleados();
            if (idEmpleados != null) {
                idEmpleados = em.getReference(idEmpleados.getClass(), idEmpleados.getIdEmpleados());
                huellas.setIdEmpleados(idEmpleados);
            }
            em.persist(huellas);
            if (idEmpleados != null) {
                idEmpleados.getHuellasCollection().add(huellas);
                idEmpleados = em.merge(idEmpleados);
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
            Huellas persistentHuellas = em.find(Huellas.class, huellas.getIdHuellas());
            Empleados idEmpleadosOld = persistentHuellas.getIdEmpleados();
            Empleados idEmpleadosNew = huellas.getIdEmpleados();
            if (idEmpleadosNew != null) {
                idEmpleadosNew = em.getReference(idEmpleadosNew.getClass(), idEmpleadosNew.getIdEmpleados());
                huellas.setIdEmpleados(idEmpleadosNew);
            }
            huellas = em.merge(huellas);
            if (idEmpleadosOld != null && !idEmpleadosOld.equals(idEmpleadosNew)) {
                idEmpleadosOld.getHuellasCollection().remove(huellas);
                idEmpleadosOld = em.merge(idEmpleadosOld);
            }
            if (idEmpleadosNew != null && !idEmpleadosNew.equals(idEmpleadosOld)) {
                idEmpleadosNew.getHuellasCollection().add(huellas);
                idEmpleadosNew = em.merge(idEmpleadosNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = huellas.getIdHuellas();
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
                huellas.getIdHuellas();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The huellas with id " + id + " no longer exists.", enfe);
            }
            Empleados idEmpleados = huellas.getIdEmpleados();
            if (idEmpleados != null) {
                idEmpleados.getHuellasCollection().remove(huellas);
                idEmpleados = em.merge(idEmpleados);
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
