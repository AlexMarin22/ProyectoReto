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
import entidades.RolEmpleados;
import java.util.ArrayList;
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
        this.emf = Persistence.createEntityManagerFactory("ProyectoPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RolEmpleados rolEmpleados) {
        if (rolEmpleados.getEmpleadosList() == null) {
            rolEmpleados.setEmpleadosList(new ArrayList<Empleados>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Empleados> attachedEmpleadosList = new ArrayList<Empleados>();
            for (Empleados empleadosListEmpleadosToAttach : rolEmpleados.getEmpleadosList()) {
                empleadosListEmpleadosToAttach = em.getReference(empleadosListEmpleadosToAttach.getClass(), empleadosListEmpleadosToAttach.getIdEmpleado());
                attachedEmpleadosList.add(empleadosListEmpleadosToAttach);
            }
            rolEmpleados.setEmpleadosList(attachedEmpleadosList);
            em.persist(rolEmpleados);
            for (Empleados empleadosListEmpleados : rolEmpleados.getEmpleadosList()) {
                RolEmpleados oldIdRolEmpleadosOfEmpleadosListEmpleados = empleadosListEmpleados.getIdRolEmpleados();
                empleadosListEmpleados.setIdRolEmpleados(rolEmpleados);
                empleadosListEmpleados = em.merge(empleadosListEmpleados);
                if (oldIdRolEmpleadosOfEmpleadosListEmpleados != null) {
                    oldIdRolEmpleadosOfEmpleadosListEmpleados.getEmpleadosList().remove(empleadosListEmpleados);
                    oldIdRolEmpleadosOfEmpleadosListEmpleados = em.merge(oldIdRolEmpleadosOfEmpleadosListEmpleados);
                }
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
            List<Empleados> empleadosListOld = persistentRolEmpleados.getEmpleadosList();
            List<Empleados> empleadosListNew = rolEmpleados.getEmpleadosList();
            List<Empleados> attachedEmpleadosListNew = new ArrayList<Empleados>();
            for (Empleados empleadosListNewEmpleadosToAttach : empleadosListNew) {
                empleadosListNewEmpleadosToAttach = em.getReference(empleadosListNewEmpleadosToAttach.getClass(), empleadosListNewEmpleadosToAttach.getIdEmpleado());
                attachedEmpleadosListNew.add(empleadosListNewEmpleadosToAttach);
            }
            empleadosListNew = attachedEmpleadosListNew;
            rolEmpleados.setEmpleadosList(empleadosListNew);
            rolEmpleados = em.merge(rolEmpleados);
            for (Empleados empleadosListOldEmpleados : empleadosListOld) {
                if (!empleadosListNew.contains(empleadosListOldEmpleados)) {
                    empleadosListOldEmpleados.setIdRolEmpleados(null);
                    empleadosListOldEmpleados = em.merge(empleadosListOldEmpleados);
                }
            }
            for (Empleados empleadosListNewEmpleados : empleadosListNew) {
                if (!empleadosListOld.contains(empleadosListNewEmpleados)) {
                    RolEmpleados oldIdRolEmpleadosOfEmpleadosListNewEmpleados = empleadosListNewEmpleados.getIdRolEmpleados();
                    empleadosListNewEmpleados.setIdRolEmpleados(rolEmpleados);
                    empleadosListNewEmpleados = em.merge(empleadosListNewEmpleados);
                    if (oldIdRolEmpleadosOfEmpleadosListNewEmpleados != null && !oldIdRolEmpleadosOfEmpleadosListNewEmpleados.equals(rolEmpleados)) {
                        oldIdRolEmpleadosOfEmpleadosListNewEmpleados.getEmpleadosList().remove(empleadosListNewEmpleados);
                        oldIdRolEmpleadosOfEmpleadosListNewEmpleados = em.merge(oldIdRolEmpleadosOfEmpleadosListNewEmpleados);
                    }
                }
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
            List<Empleados> empleadosList = rolEmpleados.getEmpleadosList();
            for (Empleados empleadosListEmpleados : empleadosList) {
                empleadosListEmpleados.setIdRolEmpleados(null);
                empleadosListEmpleados = em.merge(empleadosListEmpleados);
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
