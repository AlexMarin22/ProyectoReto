/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.IllegalOrphanException;
import controladores.exceptions.NonexistentEntityException;
import entidades.Empleados;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Marcaciones;
import java.util.ArrayList;
import java.util.Collection;
import entidades.RolEmpleados;
import entidades.Huellas;
import entidades.JornadaEmpleado;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author user
 */
public class EmpleadosJpaController implements Serializable {

    public EmpleadosJpaController() {
        this.emf = Persistence.createEntityManagerFactory("Proyecto1PU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empleados empleados) {
        if (empleados.getMarcacionesCollection() == null) {
            empleados.setMarcacionesCollection(new ArrayList<Marcaciones>());
        }
        if (empleados.getRolEmpleadosCollection() == null) {
            empleados.setRolEmpleadosCollection(new ArrayList<RolEmpleados>());
        }
        if (empleados.getHuellasCollection() == null) {
            empleados.setHuellasCollection(new ArrayList<Huellas>());
        }
        if (empleados.getJornadaEmpleadoCollection() == null) {
            empleados.setJornadaEmpleadoCollection(new ArrayList<JornadaEmpleado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Marcaciones> attachedMarcacionesCollection = new ArrayList<Marcaciones>();
            for (Marcaciones marcacionesCollectionMarcacionesToAttach : empleados.getMarcacionesCollection()) {
                marcacionesCollectionMarcacionesToAttach = em.getReference(marcacionesCollectionMarcacionesToAttach.getClass(), marcacionesCollectionMarcacionesToAttach.getIdMarcaciones());
                attachedMarcacionesCollection.add(marcacionesCollectionMarcacionesToAttach);
            }
            empleados.setMarcacionesCollection(attachedMarcacionesCollection);
            Collection<RolEmpleados> attachedRolEmpleadosCollection = new ArrayList<RolEmpleados>();
            for (RolEmpleados rolEmpleadosCollectionRolEmpleadosToAttach : empleados.getRolEmpleadosCollection()) {
                rolEmpleadosCollectionRolEmpleadosToAttach = em.getReference(rolEmpleadosCollectionRolEmpleadosToAttach.getClass(), rolEmpleadosCollectionRolEmpleadosToAttach.getIdRolEmpleados());
                attachedRolEmpleadosCollection.add(rolEmpleadosCollectionRolEmpleadosToAttach);
            }
            empleados.setRolEmpleadosCollection(attachedRolEmpleadosCollection);
            Collection<Huellas> attachedHuellasCollection = new ArrayList<Huellas>();
            for (Huellas huellasCollectionHuellasToAttach : empleados.getHuellasCollection()) {
                huellasCollectionHuellasToAttach = em.getReference(huellasCollectionHuellasToAttach.getClass(), huellasCollectionHuellasToAttach.getIdHuellas());
                attachedHuellasCollection.add(huellasCollectionHuellasToAttach);
            }
            empleados.setHuellasCollection(attachedHuellasCollection);
            Collection<JornadaEmpleado> attachedJornadaEmpleadoCollection = new ArrayList<JornadaEmpleado>();
            for (JornadaEmpleado jornadaEmpleadoCollectionJornadaEmpleadoToAttach : empleados.getJornadaEmpleadoCollection()) {
                jornadaEmpleadoCollectionJornadaEmpleadoToAttach = em.getReference(jornadaEmpleadoCollectionJornadaEmpleadoToAttach.getClass(), jornadaEmpleadoCollectionJornadaEmpleadoToAttach.getIdJornadaEmpleado());
                attachedJornadaEmpleadoCollection.add(jornadaEmpleadoCollectionJornadaEmpleadoToAttach);
            }
            empleados.setJornadaEmpleadoCollection(attachedJornadaEmpleadoCollection);
            em.persist(empleados);
            for (Marcaciones marcacionesCollectionMarcaciones : empleados.getMarcacionesCollection()) {
                Empleados oldIdEmpleadoOfMarcacionesCollectionMarcaciones = marcacionesCollectionMarcaciones.getIdEmpleado();
                marcacionesCollectionMarcaciones.setIdEmpleado(empleados);
                marcacionesCollectionMarcaciones = em.merge(marcacionesCollectionMarcaciones);
                if (oldIdEmpleadoOfMarcacionesCollectionMarcaciones != null) {
                    oldIdEmpleadoOfMarcacionesCollectionMarcaciones.getMarcacionesCollection().remove(marcacionesCollectionMarcaciones);
                    oldIdEmpleadoOfMarcacionesCollectionMarcaciones = em.merge(oldIdEmpleadoOfMarcacionesCollectionMarcaciones);
                }
            }
            for (RolEmpleados rolEmpleadosCollectionRolEmpleados : empleados.getRolEmpleadosCollection()) {
                Empleados oldIdEmpleadosOfRolEmpleadosCollectionRolEmpleados = rolEmpleadosCollectionRolEmpleados.getIdEmpleados();
                rolEmpleadosCollectionRolEmpleados.setIdEmpleados(empleados);
                rolEmpleadosCollectionRolEmpleados = em.merge(rolEmpleadosCollectionRolEmpleados);
                if (oldIdEmpleadosOfRolEmpleadosCollectionRolEmpleados != null) {
                    oldIdEmpleadosOfRolEmpleadosCollectionRolEmpleados.getRolEmpleadosCollection().remove(rolEmpleadosCollectionRolEmpleados);
                    oldIdEmpleadosOfRolEmpleadosCollectionRolEmpleados = em.merge(oldIdEmpleadosOfRolEmpleadosCollectionRolEmpleados);
                }
            }
            for (Huellas huellasCollectionHuellas : empleados.getHuellasCollection()) {
                Empleados oldIdEmpleadosOfHuellasCollectionHuellas = huellasCollectionHuellas.getIdEmpleados();
                huellasCollectionHuellas.setIdEmpleados(empleados);
                huellasCollectionHuellas = em.merge(huellasCollectionHuellas);
                if (oldIdEmpleadosOfHuellasCollectionHuellas != null) {
                    oldIdEmpleadosOfHuellasCollectionHuellas.getHuellasCollection().remove(huellasCollectionHuellas);
                    oldIdEmpleadosOfHuellasCollectionHuellas = em.merge(oldIdEmpleadosOfHuellasCollectionHuellas);
                }
            }
            for (JornadaEmpleado jornadaEmpleadoCollectionJornadaEmpleado : empleados.getJornadaEmpleadoCollection()) {
                Empleados oldIdEmpleadosOfJornadaEmpleadoCollectionJornadaEmpleado = jornadaEmpleadoCollectionJornadaEmpleado.getIdEmpleados();
                jornadaEmpleadoCollectionJornadaEmpleado.setIdEmpleados(empleados);
                jornadaEmpleadoCollectionJornadaEmpleado = em.merge(jornadaEmpleadoCollectionJornadaEmpleado);
                if (oldIdEmpleadosOfJornadaEmpleadoCollectionJornadaEmpleado != null) {
                    oldIdEmpleadosOfJornadaEmpleadoCollectionJornadaEmpleado.getJornadaEmpleadoCollection().remove(jornadaEmpleadoCollectionJornadaEmpleado);
                    oldIdEmpleadosOfJornadaEmpleadoCollectionJornadaEmpleado = em.merge(oldIdEmpleadosOfJornadaEmpleadoCollectionJornadaEmpleado);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empleados empleados) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleados persistentEmpleados = em.find(Empleados.class, empleados.getIdEmpleados());
            Collection<Marcaciones> marcacionesCollectionOld = persistentEmpleados.getMarcacionesCollection();
            Collection<Marcaciones> marcacionesCollectionNew = empleados.getMarcacionesCollection();
            Collection<RolEmpleados> rolEmpleadosCollectionOld = persistentEmpleados.getRolEmpleadosCollection();
            Collection<RolEmpleados> rolEmpleadosCollectionNew = empleados.getRolEmpleadosCollection();
            Collection<Huellas> huellasCollectionOld = persistentEmpleados.getHuellasCollection();
            Collection<Huellas> huellasCollectionNew = empleados.getHuellasCollection();
            Collection<JornadaEmpleado> jornadaEmpleadoCollectionOld = persistentEmpleados.getJornadaEmpleadoCollection();
            Collection<JornadaEmpleado> jornadaEmpleadoCollectionNew = empleados.getJornadaEmpleadoCollection();
            List<String> illegalOrphanMessages = null;
            for (Marcaciones marcacionesCollectionOldMarcaciones : marcacionesCollectionOld) {
                if (!marcacionesCollectionNew.contains(marcacionesCollectionOldMarcaciones)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Marcaciones " + marcacionesCollectionOldMarcaciones + " since its idEmpleado field is not nullable.");
                }
            }
            for (RolEmpleados rolEmpleadosCollectionOldRolEmpleados : rolEmpleadosCollectionOld) {
                if (!rolEmpleadosCollectionNew.contains(rolEmpleadosCollectionOldRolEmpleados)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RolEmpleados " + rolEmpleadosCollectionOldRolEmpleados + " since its idEmpleados field is not nullable.");
                }
            }
            for (Huellas huellasCollectionOldHuellas : huellasCollectionOld) {
                if (!huellasCollectionNew.contains(huellasCollectionOldHuellas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Huellas " + huellasCollectionOldHuellas + " since its idEmpleados field is not nullable.");
                }
            }
            for (JornadaEmpleado jornadaEmpleadoCollectionOldJornadaEmpleado : jornadaEmpleadoCollectionOld) {
                if (!jornadaEmpleadoCollectionNew.contains(jornadaEmpleadoCollectionOldJornadaEmpleado)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain JornadaEmpleado " + jornadaEmpleadoCollectionOldJornadaEmpleado + " since its idEmpleados field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Marcaciones> attachedMarcacionesCollectionNew = new ArrayList<Marcaciones>();
            for (Marcaciones marcacionesCollectionNewMarcacionesToAttach : marcacionesCollectionNew) {
                marcacionesCollectionNewMarcacionesToAttach = em.getReference(marcacionesCollectionNewMarcacionesToAttach.getClass(), marcacionesCollectionNewMarcacionesToAttach.getIdMarcaciones());
                attachedMarcacionesCollectionNew.add(marcacionesCollectionNewMarcacionesToAttach);
            }
            marcacionesCollectionNew = attachedMarcacionesCollectionNew;
            empleados.setMarcacionesCollection(marcacionesCollectionNew);
            Collection<RolEmpleados> attachedRolEmpleadosCollectionNew = new ArrayList<RolEmpleados>();
            for (RolEmpleados rolEmpleadosCollectionNewRolEmpleadosToAttach : rolEmpleadosCollectionNew) {
                rolEmpleadosCollectionNewRolEmpleadosToAttach = em.getReference(rolEmpleadosCollectionNewRolEmpleadosToAttach.getClass(), rolEmpleadosCollectionNewRolEmpleadosToAttach.getIdRolEmpleados());
                attachedRolEmpleadosCollectionNew.add(rolEmpleadosCollectionNewRolEmpleadosToAttach);
            }
            rolEmpleadosCollectionNew = attachedRolEmpleadosCollectionNew;
            empleados.setRolEmpleadosCollection(rolEmpleadosCollectionNew);
            Collection<Huellas> attachedHuellasCollectionNew = new ArrayList<Huellas>();
            for (Huellas huellasCollectionNewHuellasToAttach : huellasCollectionNew) {
                huellasCollectionNewHuellasToAttach = em.getReference(huellasCollectionNewHuellasToAttach.getClass(), huellasCollectionNewHuellasToAttach.getIdHuellas());
                attachedHuellasCollectionNew.add(huellasCollectionNewHuellasToAttach);
            }
            huellasCollectionNew = attachedHuellasCollectionNew;
            empleados.setHuellasCollection(huellasCollectionNew);
            Collection<JornadaEmpleado> attachedJornadaEmpleadoCollectionNew = new ArrayList<JornadaEmpleado>();
            for (JornadaEmpleado jornadaEmpleadoCollectionNewJornadaEmpleadoToAttach : jornadaEmpleadoCollectionNew) {
                jornadaEmpleadoCollectionNewJornadaEmpleadoToAttach = em.getReference(jornadaEmpleadoCollectionNewJornadaEmpleadoToAttach.getClass(), jornadaEmpleadoCollectionNewJornadaEmpleadoToAttach.getIdJornadaEmpleado());
                attachedJornadaEmpleadoCollectionNew.add(jornadaEmpleadoCollectionNewJornadaEmpleadoToAttach);
            }
            jornadaEmpleadoCollectionNew = attachedJornadaEmpleadoCollectionNew;
            empleados.setJornadaEmpleadoCollection(jornadaEmpleadoCollectionNew);
            empleados = em.merge(empleados);
            for (Marcaciones marcacionesCollectionNewMarcaciones : marcacionesCollectionNew) {
                if (!marcacionesCollectionOld.contains(marcacionesCollectionNewMarcaciones)) {
                    Empleados oldIdEmpleadoOfMarcacionesCollectionNewMarcaciones = marcacionesCollectionNewMarcaciones.getIdEmpleado();
                    marcacionesCollectionNewMarcaciones.setIdEmpleado(empleados);
                    marcacionesCollectionNewMarcaciones = em.merge(marcacionesCollectionNewMarcaciones);
                    if (oldIdEmpleadoOfMarcacionesCollectionNewMarcaciones != null && !oldIdEmpleadoOfMarcacionesCollectionNewMarcaciones.equals(empleados)) {
                        oldIdEmpleadoOfMarcacionesCollectionNewMarcaciones.getMarcacionesCollection().remove(marcacionesCollectionNewMarcaciones);
                        oldIdEmpleadoOfMarcacionesCollectionNewMarcaciones = em.merge(oldIdEmpleadoOfMarcacionesCollectionNewMarcaciones);
                    }
                }
            }
            for (RolEmpleados rolEmpleadosCollectionNewRolEmpleados : rolEmpleadosCollectionNew) {
                if (!rolEmpleadosCollectionOld.contains(rolEmpleadosCollectionNewRolEmpleados)) {
                    Empleados oldIdEmpleadosOfRolEmpleadosCollectionNewRolEmpleados = rolEmpleadosCollectionNewRolEmpleados.getIdEmpleados();
                    rolEmpleadosCollectionNewRolEmpleados.setIdEmpleados(empleados);
                    rolEmpleadosCollectionNewRolEmpleados = em.merge(rolEmpleadosCollectionNewRolEmpleados);
                    if (oldIdEmpleadosOfRolEmpleadosCollectionNewRolEmpleados != null && !oldIdEmpleadosOfRolEmpleadosCollectionNewRolEmpleados.equals(empleados)) {
                        oldIdEmpleadosOfRolEmpleadosCollectionNewRolEmpleados.getRolEmpleadosCollection().remove(rolEmpleadosCollectionNewRolEmpleados);
                        oldIdEmpleadosOfRolEmpleadosCollectionNewRolEmpleados = em.merge(oldIdEmpleadosOfRolEmpleadosCollectionNewRolEmpleados);
                    }
                }
            }
            for (Huellas huellasCollectionNewHuellas : huellasCollectionNew) {
                if (!huellasCollectionOld.contains(huellasCollectionNewHuellas)) {
                    Empleados oldIdEmpleadosOfHuellasCollectionNewHuellas = huellasCollectionNewHuellas.getIdEmpleados();
                    huellasCollectionNewHuellas.setIdEmpleados(empleados);
                    huellasCollectionNewHuellas = em.merge(huellasCollectionNewHuellas);
                    if (oldIdEmpleadosOfHuellasCollectionNewHuellas != null && !oldIdEmpleadosOfHuellasCollectionNewHuellas.equals(empleados)) {
                        oldIdEmpleadosOfHuellasCollectionNewHuellas.getHuellasCollection().remove(huellasCollectionNewHuellas);
                        oldIdEmpleadosOfHuellasCollectionNewHuellas = em.merge(oldIdEmpleadosOfHuellasCollectionNewHuellas);
                    }
                }
            }
            for (JornadaEmpleado jornadaEmpleadoCollectionNewJornadaEmpleado : jornadaEmpleadoCollectionNew) {
                if (!jornadaEmpleadoCollectionOld.contains(jornadaEmpleadoCollectionNewJornadaEmpleado)) {
                    Empleados oldIdEmpleadosOfJornadaEmpleadoCollectionNewJornadaEmpleado = jornadaEmpleadoCollectionNewJornadaEmpleado.getIdEmpleados();
                    jornadaEmpleadoCollectionNewJornadaEmpleado.setIdEmpleados(empleados);
                    jornadaEmpleadoCollectionNewJornadaEmpleado = em.merge(jornadaEmpleadoCollectionNewJornadaEmpleado);
                    if (oldIdEmpleadosOfJornadaEmpleadoCollectionNewJornadaEmpleado != null && !oldIdEmpleadosOfJornadaEmpleadoCollectionNewJornadaEmpleado.equals(empleados)) {
                        oldIdEmpleadosOfJornadaEmpleadoCollectionNewJornadaEmpleado.getJornadaEmpleadoCollection().remove(jornadaEmpleadoCollectionNewJornadaEmpleado);
                        oldIdEmpleadosOfJornadaEmpleadoCollectionNewJornadaEmpleado = em.merge(oldIdEmpleadosOfJornadaEmpleadoCollectionNewJornadaEmpleado);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = empleados.getIdEmpleados();
                if (findEmpleados(id) == null) {
                    throw new NonexistentEntityException("The empleados with id " + id + " no longer exists.");
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
            Empleados empleados;
            try {
                empleados = em.getReference(Empleados.class, id);
                empleados.getIdEmpleados();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleados with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Marcaciones> marcacionesCollectionOrphanCheck = empleados.getMarcacionesCollection();
            for (Marcaciones marcacionesCollectionOrphanCheckMarcaciones : marcacionesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empleados (" + empleados + ") cannot be destroyed since the Marcaciones " + marcacionesCollectionOrphanCheckMarcaciones + " in its marcacionesCollection field has a non-nullable idEmpleado field.");
            }
            Collection<RolEmpleados> rolEmpleadosCollectionOrphanCheck = empleados.getRolEmpleadosCollection();
            for (RolEmpleados rolEmpleadosCollectionOrphanCheckRolEmpleados : rolEmpleadosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empleados (" + empleados + ") cannot be destroyed since the RolEmpleados " + rolEmpleadosCollectionOrphanCheckRolEmpleados + " in its rolEmpleadosCollection field has a non-nullable idEmpleados field.");
            }
            Collection<Huellas> huellasCollectionOrphanCheck = empleados.getHuellasCollection();
            for (Huellas huellasCollectionOrphanCheckHuellas : huellasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empleados (" + empleados + ") cannot be destroyed since the Huellas " + huellasCollectionOrphanCheckHuellas + " in its huellasCollection field has a non-nullable idEmpleados field.");
            }
            Collection<JornadaEmpleado> jornadaEmpleadoCollectionOrphanCheck = empleados.getJornadaEmpleadoCollection();
            for (JornadaEmpleado jornadaEmpleadoCollectionOrphanCheckJornadaEmpleado : jornadaEmpleadoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empleados (" + empleados + ") cannot be destroyed since the JornadaEmpleado " + jornadaEmpleadoCollectionOrphanCheckJornadaEmpleado + " in its jornadaEmpleadoCollection field has a non-nullable idEmpleados field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(empleados);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empleados> findEmpleadosEntities() {
        return findEmpleadosEntities(true, -1, -1);
    }

    public List<Empleados> findEmpleadosEntities(int maxResults, int firstResult) {
        return findEmpleadosEntities(false, maxResults, firstResult);
    }

    private List<Empleados> findEmpleadosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empleados.class));
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

    public Empleados findEmpleados(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empleados.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpleadosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empleados> rt = cq.from(Empleados.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
