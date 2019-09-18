/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import entidades.Empleados;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.RolEmpleados;
import entidades.Marcaciones;
import java.util.ArrayList;
import java.util.List;
import entidades.Huellas;
import entidades.JornadaEmpleado;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author user
 */
public class EmpleadosJpaController implements Serializable {

    public EmpleadosJpaController() {
    this.emf = Persistence.createEntityManagerFactory("ProyectoPU") ;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empleados empleados) {
        if (empleados.getMarcacionesList() == null) {
            empleados.setMarcacionesList(new ArrayList<Marcaciones>());
        }
        if (empleados.getHuellasList() == null) {
            empleados.setHuellasList(new ArrayList<Huellas>());
        }
        if (empleados.getJornadaEmpleadoList() == null) {
            empleados.setJornadaEmpleadoList(new ArrayList<JornadaEmpleado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RolEmpleados idRolEmpleados = empleados.getIdRolEmpleados();
            if (idRolEmpleados != null) {
                idRolEmpleados = em.getReference(idRolEmpleados.getClass(), idRolEmpleados.getIdRolEmpleados());
                empleados.setIdRolEmpleados(idRolEmpleados);
            }
            List<Marcaciones> attachedMarcacionesList = new ArrayList<Marcaciones>();
            for (Marcaciones marcacionesListMarcacionesToAttach : empleados.getMarcacionesList()) {
                marcacionesListMarcacionesToAttach = em.getReference(marcacionesListMarcacionesToAttach.getClass(), marcacionesListMarcacionesToAttach.getIdMarcacion());
                attachedMarcacionesList.add(marcacionesListMarcacionesToAttach);
            }
            empleados.setMarcacionesList(attachedMarcacionesList);
            List<Huellas> attachedHuellasList = new ArrayList<Huellas>();
            for (Huellas huellasListHuellasToAttach : empleados.getHuellasList()) {
                huellasListHuellasToAttach = em.getReference(huellasListHuellasToAttach.getClass(), huellasListHuellasToAttach.getIdHuella());
                attachedHuellasList.add(huellasListHuellasToAttach);
            }
            empleados.setHuellasList(attachedHuellasList);
            List<JornadaEmpleado> attachedJornadaEmpleadoList = new ArrayList<JornadaEmpleado>();
            for (JornadaEmpleado jornadaEmpleadoListJornadaEmpleadoToAttach : empleados.getJornadaEmpleadoList()) {
                jornadaEmpleadoListJornadaEmpleadoToAttach = em.getReference(jornadaEmpleadoListJornadaEmpleadoToAttach.getClass(), jornadaEmpleadoListJornadaEmpleadoToAttach.getIdJornadaEmpleado());
                attachedJornadaEmpleadoList.add(jornadaEmpleadoListJornadaEmpleadoToAttach);
            }
            empleados.setJornadaEmpleadoList(attachedJornadaEmpleadoList);
            em.persist(empleados);
            if (idRolEmpleados != null) {
                idRolEmpleados.getEmpleadosList().add(empleados);
                idRolEmpleados = em.merge(idRolEmpleados);
            }
            for (Marcaciones marcacionesListMarcaciones : empleados.getMarcacionesList()) {
                Empleados oldIdEmpleadoOfMarcacionesListMarcaciones = marcacionesListMarcaciones.getIdEmpleado();
                marcacionesListMarcaciones.setIdEmpleado(empleados);
                marcacionesListMarcaciones = em.merge(marcacionesListMarcaciones);
                if (oldIdEmpleadoOfMarcacionesListMarcaciones != null) {
                    oldIdEmpleadoOfMarcacionesListMarcaciones.getMarcacionesList().remove(marcacionesListMarcaciones);
                    oldIdEmpleadoOfMarcacionesListMarcaciones = em.merge(oldIdEmpleadoOfMarcacionesListMarcaciones);
                }
            }
            for (Huellas huellasListHuellas : empleados.getHuellasList()) {
                Empleados oldIdEmpleadoOfHuellasListHuellas = huellasListHuellas.getIdEmpleado();
                huellasListHuellas.setIdEmpleado(empleados);
                huellasListHuellas = em.merge(huellasListHuellas);
                if (oldIdEmpleadoOfHuellasListHuellas != null) {
                    oldIdEmpleadoOfHuellasListHuellas.getHuellasList().remove(huellasListHuellas);
                    oldIdEmpleadoOfHuellasListHuellas = em.merge(oldIdEmpleadoOfHuellasListHuellas);
                }
            }
            for (JornadaEmpleado jornadaEmpleadoListJornadaEmpleado : empleados.getJornadaEmpleadoList()) {
                Empleados oldIdEmpleadoOfJornadaEmpleadoListJornadaEmpleado = jornadaEmpleadoListJornadaEmpleado.getIdEmpleado();
                jornadaEmpleadoListJornadaEmpleado.setIdEmpleado(empleados);
                jornadaEmpleadoListJornadaEmpleado = em.merge(jornadaEmpleadoListJornadaEmpleado);
                if (oldIdEmpleadoOfJornadaEmpleadoListJornadaEmpleado != null) {
                    oldIdEmpleadoOfJornadaEmpleadoListJornadaEmpleado.getJornadaEmpleadoList().remove(jornadaEmpleadoListJornadaEmpleado);
                    oldIdEmpleadoOfJornadaEmpleadoListJornadaEmpleado = em.merge(oldIdEmpleadoOfJornadaEmpleadoListJornadaEmpleado);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empleados empleados) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleados persistentEmpleados = em.find(Empleados.class, empleados.getIdEmpleado());
            RolEmpleados idRolEmpleadosOld = persistentEmpleados.getIdRolEmpleados();
            RolEmpleados idRolEmpleadosNew = empleados.getIdRolEmpleados();
            List<Marcaciones> marcacionesListOld = persistentEmpleados.getMarcacionesList();
            List<Marcaciones> marcacionesListNew = empleados.getMarcacionesList();
            List<Huellas> huellasListOld = persistentEmpleados.getHuellasList();
            List<Huellas> huellasListNew = empleados.getHuellasList();
            List<JornadaEmpleado> jornadaEmpleadoListOld = persistentEmpleados.getJornadaEmpleadoList();
            List<JornadaEmpleado> jornadaEmpleadoListNew = empleados.getJornadaEmpleadoList();
            if (idRolEmpleadosNew != null) {
                idRolEmpleadosNew = em.getReference(idRolEmpleadosNew.getClass(), idRolEmpleadosNew.getIdRolEmpleados());
                empleados.setIdRolEmpleados(idRolEmpleadosNew);
            }
            List<Marcaciones> attachedMarcacionesListNew = new ArrayList<Marcaciones>();
            for (Marcaciones marcacionesListNewMarcacionesToAttach : marcacionesListNew) {
                marcacionesListNewMarcacionesToAttach = em.getReference(marcacionesListNewMarcacionesToAttach.getClass(), marcacionesListNewMarcacionesToAttach.getIdMarcacion());
                attachedMarcacionesListNew.add(marcacionesListNewMarcacionesToAttach);
            }
            marcacionesListNew = attachedMarcacionesListNew;
            empleados.setMarcacionesList(marcacionesListNew);
            List<Huellas> attachedHuellasListNew = new ArrayList<Huellas>();
            for (Huellas huellasListNewHuellasToAttach : huellasListNew) {
                huellasListNewHuellasToAttach = em.getReference(huellasListNewHuellasToAttach.getClass(), huellasListNewHuellasToAttach.getIdHuella());
                attachedHuellasListNew.add(huellasListNewHuellasToAttach);
            }
            huellasListNew = attachedHuellasListNew;
            empleados.setHuellasList(huellasListNew);
            List<JornadaEmpleado> attachedJornadaEmpleadoListNew = new ArrayList<JornadaEmpleado>();
            for (JornadaEmpleado jornadaEmpleadoListNewJornadaEmpleadoToAttach : jornadaEmpleadoListNew) {
                jornadaEmpleadoListNewJornadaEmpleadoToAttach = em.getReference(jornadaEmpleadoListNewJornadaEmpleadoToAttach.getClass(), jornadaEmpleadoListNewJornadaEmpleadoToAttach.getIdJornadaEmpleado());
                attachedJornadaEmpleadoListNew.add(jornadaEmpleadoListNewJornadaEmpleadoToAttach);
            }
            jornadaEmpleadoListNew = attachedJornadaEmpleadoListNew;
            empleados.setJornadaEmpleadoList(jornadaEmpleadoListNew);
            empleados = em.merge(empleados);
            if (idRolEmpleadosOld != null && !idRolEmpleadosOld.equals(idRolEmpleadosNew)) {
                idRolEmpleadosOld.getEmpleadosList().remove(empleados);
                idRolEmpleadosOld = em.merge(idRolEmpleadosOld);
            }
            if (idRolEmpleadosNew != null && !idRolEmpleadosNew.equals(idRolEmpleadosOld)) {
                idRolEmpleadosNew.getEmpleadosList().add(empleados);
                idRolEmpleadosNew = em.merge(idRolEmpleadosNew);
            }
            for (Marcaciones marcacionesListOldMarcaciones : marcacionesListOld) {
                if (!marcacionesListNew.contains(marcacionesListOldMarcaciones)) {
                    marcacionesListOldMarcaciones.setIdEmpleado(null);
                    marcacionesListOldMarcaciones = em.merge(marcacionesListOldMarcaciones);
                }
            }
            for (Marcaciones marcacionesListNewMarcaciones : marcacionesListNew) {
                if (!marcacionesListOld.contains(marcacionesListNewMarcaciones)) {
                    Empleados oldIdEmpleadoOfMarcacionesListNewMarcaciones = marcacionesListNewMarcaciones.getIdEmpleado();
                    marcacionesListNewMarcaciones.setIdEmpleado(empleados);
                    marcacionesListNewMarcaciones = em.merge(marcacionesListNewMarcaciones);
                    if (oldIdEmpleadoOfMarcacionesListNewMarcaciones != null && !oldIdEmpleadoOfMarcacionesListNewMarcaciones.equals(empleados)) {
                        oldIdEmpleadoOfMarcacionesListNewMarcaciones.getMarcacionesList().remove(marcacionesListNewMarcaciones);
                        oldIdEmpleadoOfMarcacionesListNewMarcaciones = em.merge(oldIdEmpleadoOfMarcacionesListNewMarcaciones);
                    }
                }
            }
            for (Huellas huellasListOldHuellas : huellasListOld) {
                if (!huellasListNew.contains(huellasListOldHuellas)) {
                    huellasListOldHuellas.setIdEmpleado(null);
                    huellasListOldHuellas = em.merge(huellasListOldHuellas);
                }
            }
            for (Huellas huellasListNewHuellas : huellasListNew) {
                if (!huellasListOld.contains(huellasListNewHuellas)) {
                    Empleados oldIdEmpleadoOfHuellasListNewHuellas = huellasListNewHuellas.getIdEmpleado();
                    huellasListNewHuellas.setIdEmpleado(empleados);
                    huellasListNewHuellas = em.merge(huellasListNewHuellas);
                    if (oldIdEmpleadoOfHuellasListNewHuellas != null && !oldIdEmpleadoOfHuellasListNewHuellas.equals(empleados)) {
                        oldIdEmpleadoOfHuellasListNewHuellas.getHuellasList().remove(huellasListNewHuellas);
                        oldIdEmpleadoOfHuellasListNewHuellas = em.merge(oldIdEmpleadoOfHuellasListNewHuellas);
                    }
                }
            }
            for (JornadaEmpleado jornadaEmpleadoListOldJornadaEmpleado : jornadaEmpleadoListOld) {
                if (!jornadaEmpleadoListNew.contains(jornadaEmpleadoListOldJornadaEmpleado)) {
                    jornadaEmpleadoListOldJornadaEmpleado.setIdEmpleado(null);
                    jornadaEmpleadoListOldJornadaEmpleado = em.merge(jornadaEmpleadoListOldJornadaEmpleado);
                }
            }
            for (JornadaEmpleado jornadaEmpleadoListNewJornadaEmpleado : jornadaEmpleadoListNew) {
                if (!jornadaEmpleadoListOld.contains(jornadaEmpleadoListNewJornadaEmpleado)) {
                    Empleados oldIdEmpleadoOfJornadaEmpleadoListNewJornadaEmpleado = jornadaEmpleadoListNewJornadaEmpleado.getIdEmpleado();
                    jornadaEmpleadoListNewJornadaEmpleado.setIdEmpleado(empleados);
                    jornadaEmpleadoListNewJornadaEmpleado = em.merge(jornadaEmpleadoListNewJornadaEmpleado);
                    if (oldIdEmpleadoOfJornadaEmpleadoListNewJornadaEmpleado != null && !oldIdEmpleadoOfJornadaEmpleadoListNewJornadaEmpleado.equals(empleados)) {
                        oldIdEmpleadoOfJornadaEmpleadoListNewJornadaEmpleado.getJornadaEmpleadoList().remove(jornadaEmpleadoListNewJornadaEmpleado);
                        oldIdEmpleadoOfJornadaEmpleadoListNewJornadaEmpleado = em.merge(oldIdEmpleadoOfJornadaEmpleadoListNewJornadaEmpleado);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = empleados.getIdEmpleado();
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

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empleados empleados;
            try {
                empleados = em.getReference(Empleados.class, id);
                empleados.getIdEmpleado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empleados with id " + id + " no longer exists.", enfe);
            }
            RolEmpleados idRolEmpleados = empleados.getIdRolEmpleados();
            if (idRolEmpleados != null) {
                idRolEmpleados.getEmpleadosList().remove(empleados);
                idRolEmpleados = em.merge(idRolEmpleados);
            }
            List<Marcaciones> marcacionesList = empleados.getMarcacionesList();
            for (Marcaciones marcacionesListMarcaciones : marcacionesList) {
                marcacionesListMarcaciones.setIdEmpleado(null);
                marcacionesListMarcaciones = em.merge(marcacionesListMarcaciones);
            }
            List<Huellas> huellasList = empleados.getHuellasList();
            for (Huellas huellasListHuellas : huellasList) {
                huellasListHuellas.setIdEmpleado(null);
                huellasListHuellas = em.merge(huellasListHuellas);
            }
            List<JornadaEmpleado> jornadaEmpleadoList = empleados.getJornadaEmpleadoList();
            for (JornadaEmpleado jornadaEmpleadoListJornadaEmpleado : jornadaEmpleadoList) {
                jornadaEmpleadoListJornadaEmpleado.setIdEmpleado(null);
                jornadaEmpleadoListJornadaEmpleado = em.merge(jornadaEmpleadoListJornadaEmpleado);
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
