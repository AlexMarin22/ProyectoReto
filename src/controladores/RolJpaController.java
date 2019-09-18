/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.IllegalOrphanException;
import controladores.exceptions.NonexistentEntityException;
import entidades.Rol;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.RolEmpleados;
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
public class RolJpaController implements Serializable {

    public RolJpaController() {
        this.emf = Persistence.createEntityManagerFactory("Proyecto1PU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Rol rol) {
        if (rol.getRolEmpleadosCollection() == null) {
            rol.setRolEmpleadosCollection(new ArrayList<RolEmpleados>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<RolEmpleados> attachedRolEmpleadosCollection = new ArrayList<RolEmpleados>();
            for (RolEmpleados rolEmpleadosCollectionRolEmpleadosToAttach : rol.getRolEmpleadosCollection()) {
                rolEmpleadosCollectionRolEmpleadosToAttach = em.getReference(rolEmpleadosCollectionRolEmpleadosToAttach.getClass(), rolEmpleadosCollectionRolEmpleadosToAttach.getIdRolEmpleados());
                attachedRolEmpleadosCollection.add(rolEmpleadosCollectionRolEmpleadosToAttach);
            }
            rol.setRolEmpleadosCollection(attachedRolEmpleadosCollection);
            em.persist(rol);
            for (RolEmpleados rolEmpleadosCollectionRolEmpleados : rol.getRolEmpleadosCollection()) {
                Rol oldIdRolOfRolEmpleadosCollectionRolEmpleados = rolEmpleadosCollectionRolEmpleados.getIdRol();
                rolEmpleadosCollectionRolEmpleados.setIdRol(rol);
                rolEmpleadosCollectionRolEmpleados = em.merge(rolEmpleadosCollectionRolEmpleados);
                if (oldIdRolOfRolEmpleadosCollectionRolEmpleados != null) {
                    oldIdRolOfRolEmpleadosCollectionRolEmpleados.getRolEmpleadosCollection().remove(rolEmpleadosCollectionRolEmpleados);
                    oldIdRolOfRolEmpleadosCollectionRolEmpleados = em.merge(oldIdRolOfRolEmpleadosCollectionRolEmpleados);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rol rol) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rol persistentRol = em.find(Rol.class, rol.getIdRol());
            Collection<RolEmpleados> rolEmpleadosCollectionOld = persistentRol.getRolEmpleadosCollection();
            Collection<RolEmpleados> rolEmpleadosCollectionNew = rol.getRolEmpleadosCollection();
            List<String> illegalOrphanMessages = null;
            for (RolEmpleados rolEmpleadosCollectionOldRolEmpleados : rolEmpleadosCollectionOld) {
                if (!rolEmpleadosCollectionNew.contains(rolEmpleadosCollectionOldRolEmpleados)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RolEmpleados " + rolEmpleadosCollectionOldRolEmpleados + " since its idRol field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<RolEmpleados> attachedRolEmpleadosCollectionNew = new ArrayList<RolEmpleados>();
            for (RolEmpleados rolEmpleadosCollectionNewRolEmpleadosToAttach : rolEmpleadosCollectionNew) {
                rolEmpleadosCollectionNewRolEmpleadosToAttach = em.getReference(rolEmpleadosCollectionNewRolEmpleadosToAttach.getClass(), rolEmpleadosCollectionNewRolEmpleadosToAttach.getIdRolEmpleados());
                attachedRolEmpleadosCollectionNew.add(rolEmpleadosCollectionNewRolEmpleadosToAttach);
            }
            rolEmpleadosCollectionNew = attachedRolEmpleadosCollectionNew;
            rol.setRolEmpleadosCollection(rolEmpleadosCollectionNew);
            rol = em.merge(rol);
            for (RolEmpleados rolEmpleadosCollectionNewRolEmpleados : rolEmpleadosCollectionNew) {
                if (!rolEmpleadosCollectionOld.contains(rolEmpleadosCollectionNewRolEmpleados)) {
                    Rol oldIdRolOfRolEmpleadosCollectionNewRolEmpleados = rolEmpleadosCollectionNewRolEmpleados.getIdRol();
                    rolEmpleadosCollectionNewRolEmpleados.setIdRol(rol);
                    rolEmpleadosCollectionNewRolEmpleados = em.merge(rolEmpleadosCollectionNewRolEmpleados);
                    if (oldIdRolOfRolEmpleadosCollectionNewRolEmpleados != null && !oldIdRolOfRolEmpleadosCollectionNewRolEmpleados.equals(rol)) {
                        oldIdRolOfRolEmpleadosCollectionNewRolEmpleados.getRolEmpleadosCollection().remove(rolEmpleadosCollectionNewRolEmpleados);
                        oldIdRolOfRolEmpleadosCollectionNewRolEmpleados = em.merge(oldIdRolOfRolEmpleadosCollectionNewRolEmpleados);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rol.getIdRol();
                if (findRol(id) == null) {
                    throw new NonexistentEntityException("The rol with id " + id + " no longer exists.");
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
            Rol rol;
            try {
                rol = em.getReference(Rol.class, id);
                rol.getIdRol();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rol with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<RolEmpleados> rolEmpleadosCollectionOrphanCheck = rol.getRolEmpleadosCollection();
            for (RolEmpleados rolEmpleadosCollectionOrphanCheckRolEmpleados : rolEmpleadosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Rol (" + rol + ") cannot be destroyed since the RolEmpleados " + rolEmpleadosCollectionOrphanCheckRolEmpleados + " in its rolEmpleadosCollection field has a non-nullable idRol field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(rol);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rol> findRolEntities() {
        return findRolEntities(true, -1, -1);
    }

    public List<Rol> findRolEntities(int maxResults, int firstResult) {
        return findRolEntities(false, maxResults, firstResult);
    }

    private List<Rol> findRolEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rol.class));
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

    public Rol findRol(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rol.class, id);
        } finally {
            em.close();
        }
    }

    public int getRolCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rol> rt = cq.from(Rol.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
