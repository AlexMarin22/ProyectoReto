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
import entidades.DetalleMarcaciones;
import entidades.Marcaciones;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author user
 */
public class MarcacionesJpaController implements Serializable {

    public MarcacionesJpaController() {
                this.emf = Persistence.createEntityManagerFactory("ProyectoPU") ;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Marcaciones marcaciones) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleados idEmpleado = marcaciones.getIdEmpleado();
            if (idEmpleado != null) {
                idEmpleado = em.getReference(idEmpleado.getClass(), idEmpleado.getIdEmpleado());
                marcaciones.setIdEmpleado(idEmpleado);
            }
            DetalleMarcaciones idDetalleMarcacion = marcaciones.getIdDetalleMarcacion();
            if (idDetalleMarcacion != null) {
                idDetalleMarcacion = em.getReference(idDetalleMarcacion.getClass(), idDetalleMarcacion.getIdDetalleMarcacion());
                marcaciones.setIdDetalleMarcacion(idDetalleMarcacion);
            }
            em.persist(marcaciones);
            if (idEmpleado != null) {
                idEmpleado.getMarcacionesList().add(marcaciones);
                idEmpleado = em.merge(idEmpleado);
            }
            if (idDetalleMarcacion != null) {
                idDetalleMarcacion.getMarcacionesList().add(marcaciones);
                idDetalleMarcacion = em.merge(idDetalleMarcacion);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Marcaciones marcaciones) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Marcaciones persistentMarcaciones = em.find(Marcaciones.class, marcaciones.getIdMarcacion());
            Empleados idEmpleadoOld = persistentMarcaciones.getIdEmpleado();
            Empleados idEmpleadoNew = marcaciones.getIdEmpleado();
            DetalleMarcaciones idDetalleMarcacionOld = persistentMarcaciones.getIdDetalleMarcacion();
            DetalleMarcaciones idDetalleMarcacionNew = marcaciones.getIdDetalleMarcacion();
            if (idEmpleadoNew != null) {
                idEmpleadoNew = em.getReference(idEmpleadoNew.getClass(), idEmpleadoNew.getIdEmpleado());
                marcaciones.setIdEmpleado(idEmpleadoNew);
            }
            if (idDetalleMarcacionNew != null) {
                idDetalleMarcacionNew = em.getReference(idDetalleMarcacionNew.getClass(), idDetalleMarcacionNew.getIdDetalleMarcacion());
                marcaciones.setIdDetalleMarcacion(idDetalleMarcacionNew);
            }
            marcaciones = em.merge(marcaciones);
            if (idEmpleadoOld != null && !idEmpleadoOld.equals(idEmpleadoNew)) {
                idEmpleadoOld.getMarcacionesList().remove(marcaciones);
                idEmpleadoOld = em.merge(idEmpleadoOld);
            }
            if (idEmpleadoNew != null && !idEmpleadoNew.equals(idEmpleadoOld)) {
                idEmpleadoNew.getMarcacionesList().add(marcaciones);
                idEmpleadoNew = em.merge(idEmpleadoNew);
            }
            if (idDetalleMarcacionOld != null && !idDetalleMarcacionOld.equals(idDetalleMarcacionNew)) {
                idDetalleMarcacionOld.getMarcacionesList().remove(marcaciones);
                idDetalleMarcacionOld = em.merge(idDetalleMarcacionOld);
            }
            if (idDetalleMarcacionNew != null && !idDetalleMarcacionNew.equals(idDetalleMarcacionOld)) {
                idDetalleMarcacionNew.getMarcacionesList().add(marcaciones);
                idDetalleMarcacionNew = em.merge(idDetalleMarcacionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = marcaciones.getIdMarcacion();
                if (findMarcaciones(id) == null) {
                    throw new NonexistentEntityException("The marcaciones with id " + id + " no longer exists.");
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
            Marcaciones marcaciones;
            try {
                marcaciones = em.getReference(Marcaciones.class, id);
                marcaciones.getIdMarcacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The marcaciones with id " + id + " no longer exists.", enfe);
            }
            Empleados idEmpleado = marcaciones.getIdEmpleado();
            if (idEmpleado != null) {
                idEmpleado.getMarcacionesList().remove(marcaciones);
                idEmpleado = em.merge(idEmpleado);
            }
            DetalleMarcaciones idDetalleMarcacion = marcaciones.getIdDetalleMarcacion();
            if (idDetalleMarcacion != null) {
                idDetalleMarcacion.getMarcacionesList().remove(marcaciones);
                idDetalleMarcacion = em.merge(idDetalleMarcacion);
            }
            em.remove(marcaciones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Marcaciones> findMarcacionesEntities() {
        return findMarcacionesEntities(true, -1, -1);
    }

    public List<Marcaciones> findMarcacionesEntities(int maxResults, int firstResult) {
        return findMarcacionesEntities(false, maxResults, firstResult);
    }

    private List<Marcaciones> findMarcacionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Marcaciones.class));
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

    public Marcaciones findMarcaciones(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Marcaciones.class, id);
        } finally {
            em.close();
        }
    }

    public int getMarcacionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Marcaciones> rt = cq.from(Marcaciones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
