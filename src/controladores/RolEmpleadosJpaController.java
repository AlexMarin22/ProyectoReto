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
import entidades.Rol;
import entidades.RolEmpleados;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author user
 */
public class RolEmpleadosJpaController implements Serializable {

    public RolEmpleadosJpaController() {
        this.emf = Persistence.createEntityManagerFactory("Proyecto1PU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RolEmpleados rolEmpleados) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleados idEmpleados = rolEmpleados.getIdEmpleados();
            if (idEmpleados != null) {
                idEmpleados = em.getReference(idEmpleados.getClass(), idEmpleados.getIdEmpleados());
                rolEmpleados.setIdEmpleados(idEmpleados);
            }
            Rol idRol = rolEmpleados.getIdRol();
            if (idRol != null) {
                idRol = em.getReference(idRol.getClass(), idRol.getIdRol());
                rolEmpleados.setIdRol(idRol);
            }
            em.persist(rolEmpleados);
            if (idEmpleados != null) {
                idEmpleados.getRolEmpleadosCollection().add(rolEmpleados);
                idEmpleados = em.merge(idEmpleados);
            }
            if (idRol != null) {
                idRol.getRolEmpleadosCollection().add(rolEmpleados);
                idRol = em.merge(idRol);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RolEmpleados rolEmpleados) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RolEmpleados persistentRolEmpleados = em.find(RolEmpleados.class, rolEmpleados.getIdRolEmpleados());
            Empleados idEmpleadosOld = persistentRolEmpleados.getIdEmpleados();
            Empleados idEmpleadosNew = rolEmpleados.getIdEmpleados();
            Rol idRolOld = persistentRolEmpleados.getIdRol();
            Rol idRolNew = rolEmpleados.getIdRol();
            if (idEmpleadosNew != null) {
                idEmpleadosNew = em.getReference(idEmpleadosNew.getClass(), idEmpleadosNew.getIdEmpleados());
                rolEmpleados.setIdEmpleados(idEmpleadosNew);
            }
            if (idRolNew != null) {
                idRolNew = em.getReference(idRolNew.getClass(), idRolNew.getIdRol());
                rolEmpleados.setIdRol(idRolNew);
            }
            rolEmpleados = em.merge(rolEmpleados);
            if (idEmpleadosOld != null && !idEmpleadosOld.equals(idEmpleadosNew)) {
                idEmpleadosOld.getRolEmpleadosCollection().remove(rolEmpleados);
                idEmpleadosOld = em.merge(idEmpleadosOld);
            }
            if (idEmpleadosNew != null && !idEmpleadosNew.equals(idEmpleadosOld)) {
                idEmpleadosNew.getRolEmpleadosCollection().add(rolEmpleados);
                idEmpleadosNew = em.merge(idEmpleadosNew);
            }
            if (idRolOld != null && !idRolOld.equals(idRolNew)) {
                idRolOld.getRolEmpleadosCollection().remove(rolEmpleados);
                idRolOld = em.merge(idRolOld);
            }
            if (idRolNew != null && !idRolNew.equals(idRolOld)) {
                idRolNew.getRolEmpleadosCollection().add(rolEmpleados);
                idRolNew = em.merge(idRolNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rolEmpleados.getIdRolEmpleados();
                if (findRolEmpleados(id) == null) {
                    throw new NonexistentEntityException("The rolEmpleados with id " + id + " no longer exists.");
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
            RolEmpleados rolEmpleados;
            try {
                rolEmpleados = em.getReference(RolEmpleados.class, id);
                rolEmpleados.getIdRolEmpleados();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rolEmpleados with id " + id + " no longer exists.", enfe);
            }
            Empleados idEmpleados = rolEmpleados.getIdEmpleados();
            if (idEmpleados != null) {
                idEmpleados.getRolEmpleadosCollection().remove(rolEmpleados);
                idEmpleados = em.merge(idEmpleados);
            }
            Rol idRol = rolEmpleados.getIdRol();
            if (idRol != null) {
                idRol.getRolEmpleadosCollection().remove(rolEmpleados);
                idRol = em.merge(idRol);
            }
            em.remove(rolEmpleados);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RolEmpleados> findRolEmpleadosEntities() {
        return findRolEmpleadosEntities(true, -1, -1);
    }

    public List<RolEmpleados> findRolEmpleadosEntities(int maxResults, int firstResult) {
        return findRolEmpleadosEntities(false, maxResults, firstResult);
    }

    private List<RolEmpleados> findRolEmpleadosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RolEmpleados.class));
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

    public RolEmpleados findRolEmpleados(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RolEmpleados.class, id);
        } finally {
            em.close();
        }
    }

    public int getRolEmpleadosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RolEmpleados> rt = cq.from(RolEmpleados.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
