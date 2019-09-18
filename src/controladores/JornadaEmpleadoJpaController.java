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
import entidades.Jornada;
import entidades.Empleados;
import entidades.JornadaEmpleado;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author user
 */
public class JornadaEmpleadoJpaController implements Serializable {

    public JornadaEmpleadoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("Proyecto1PU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(JornadaEmpleado jornadaEmpleado) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Jornada idJornada = jornadaEmpleado.getIdJornada();
            if (idJornada != null) {
                idJornada = em.getReference(idJornada.getClass(), idJornada.getIdJornada());
                jornadaEmpleado.setIdJornada(idJornada);
            }
            Empleados idEmpleados = jornadaEmpleado.getIdEmpleados();
            if (idEmpleados != null) {
                idEmpleados = em.getReference(idEmpleados.getClass(), idEmpleados.getIdEmpleados());
                jornadaEmpleado.setIdEmpleados(idEmpleados);
            }
            em.persist(jornadaEmpleado);
            if (idJornada != null) {
                idJornada.getJornadaEmpleadoCollection().add(jornadaEmpleado);
                idJornada = em.merge(idJornada);
            }
            if (idEmpleados != null) {
                idEmpleados.getJornadaEmpleadoCollection().add(jornadaEmpleado);
                idEmpleados = em.merge(idEmpleados);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(JornadaEmpleado jornadaEmpleado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            JornadaEmpleado persistentJornadaEmpleado = em.find(JornadaEmpleado.class, jornadaEmpleado.getIdJornadaEmpleado());
            Jornada idJornadaOld = persistentJornadaEmpleado.getIdJornada();
            Jornada idJornadaNew = jornadaEmpleado.getIdJornada();
            Empleados idEmpleadosOld = persistentJornadaEmpleado.getIdEmpleados();
            Empleados idEmpleadosNew = jornadaEmpleado.getIdEmpleados();
            if (idJornadaNew != null) {
                idJornadaNew = em.getReference(idJornadaNew.getClass(), idJornadaNew.getIdJornada());
                jornadaEmpleado.setIdJornada(idJornadaNew);
            }
            if (idEmpleadosNew != null) {
                idEmpleadosNew = em.getReference(idEmpleadosNew.getClass(), idEmpleadosNew.getIdEmpleados());
                jornadaEmpleado.setIdEmpleados(idEmpleadosNew);
            }
            jornadaEmpleado = em.merge(jornadaEmpleado);
            if (idJornadaOld != null && !idJornadaOld.equals(idJornadaNew)) {
                idJornadaOld.getJornadaEmpleadoCollection().remove(jornadaEmpleado);
                idJornadaOld = em.merge(idJornadaOld);
            }
            if (idJornadaNew != null && !idJornadaNew.equals(idJornadaOld)) {
                idJornadaNew.getJornadaEmpleadoCollection().add(jornadaEmpleado);
                idJornadaNew = em.merge(idJornadaNew);
            }
            if (idEmpleadosOld != null && !idEmpleadosOld.equals(idEmpleadosNew)) {
                idEmpleadosOld.getJornadaEmpleadoCollection().remove(jornadaEmpleado);
                idEmpleadosOld = em.merge(idEmpleadosOld);
            }
            if (idEmpleadosNew != null && !idEmpleadosNew.equals(idEmpleadosOld)) {
                idEmpleadosNew.getJornadaEmpleadoCollection().add(jornadaEmpleado);
                idEmpleadosNew = em.merge(idEmpleadosNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = jornadaEmpleado.getIdJornadaEmpleado();
                if (findJornadaEmpleado(id) == null) {
                    throw new NonexistentEntityException("The jornadaEmpleado with id " + id + " no longer exists.");
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
            JornadaEmpleado jornadaEmpleado;
            try {
                jornadaEmpleado = em.getReference(JornadaEmpleado.class, id);
                jornadaEmpleado.getIdJornadaEmpleado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The jornadaEmpleado with id " + id + " no longer exists.", enfe);
            }
            Jornada idJornada = jornadaEmpleado.getIdJornada();
            if (idJornada != null) {
                idJornada.getJornadaEmpleadoCollection().remove(jornadaEmpleado);
                idJornada = em.merge(idJornada);
            }
            Empleados idEmpleados = jornadaEmpleado.getIdEmpleados();
            if (idEmpleados != null) {
                idEmpleados.getJornadaEmpleadoCollection().remove(jornadaEmpleado);
                idEmpleados = em.merge(idEmpleados);
            }
            em.remove(jornadaEmpleado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<JornadaEmpleado> findJornadaEmpleadoEntities() {
        return findJornadaEmpleadoEntities(true, -1, -1);
    }

    public List<JornadaEmpleado> findJornadaEmpleadoEntities(int maxResults, int firstResult) {
        return findJornadaEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<JornadaEmpleado> findJornadaEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(JornadaEmpleado.class));
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

    public JornadaEmpleado findJornadaEmpleado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(JornadaEmpleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getJornadaEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<JornadaEmpleado> rt = cq.from(JornadaEmpleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
