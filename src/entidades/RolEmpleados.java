/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author user
 */
@Entity
@Table(name = "rol_empleados")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RolEmpleados.findAll", query = "SELECT r FROM RolEmpleados r")
    , @NamedQuery(name = "RolEmpleados.findByIdRolEmpleados", query = "SELECT r FROM RolEmpleados r WHERE r.idRolEmpleados = :idRolEmpleados")
    , @NamedQuery(name = "RolEmpleados.findByObservacion", query = "SELECT r FROM RolEmpleados r WHERE r.observacion = :observacion")
    , @NamedQuery(name = "RolEmpleados.findByFechaActivacion", query = "SELECT r FROM RolEmpleados r WHERE r.fechaActivacion = :fechaActivacion")
    , @NamedQuery(name = "RolEmpleados.findByFechaInactivacion", query = "SELECT r FROM RolEmpleados r WHERE r.fechaInactivacion = :fechaInactivacion")
    , @NamedQuery(name = "RolEmpleados.findByEstado", query = "SELECT r FROM RolEmpleados r WHERE r.estado = :estado")})
public class RolEmpleados implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_rol_empleados")
    private Integer idRolEmpleados;
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "fecha_activacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActivacion;
    @Column(name = "fecha_inactivacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInactivacion;
    @Column(name = "estado")
    private String estado;
    @OneToMany(mappedBy = "idRolEmpleados")
    private List<Empleados> empleadosList;

    public RolEmpleados() {
    }

    public RolEmpleados(Integer idRolEmpleados) {
        this.idRolEmpleados = idRolEmpleados;
    }

    public Integer getIdRolEmpleados() {
        return idRolEmpleados;
    }

    public void setIdRolEmpleados(Integer idRolEmpleados) {
        this.idRolEmpleados = idRolEmpleados;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getFechaActivacion() {
        return fechaActivacion;
    }

    public void setFechaActivacion(Date fechaActivacion) {
        this.fechaActivacion = fechaActivacion;
    }

    public Date getFechaInactivacion() {
        return fechaInactivacion;
    }

    public void setFechaInactivacion(Date fechaInactivacion) {
        this.fechaInactivacion = fechaInactivacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<Empleados> getEmpleadosList() {
        return empleadosList;
    }

    public void setEmpleadosList(List<Empleados> empleadosList) {
        this.empleadosList = empleadosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRolEmpleados != null ? idRolEmpleados.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RolEmpleados)) {
            return false;
        }
        RolEmpleados other = (RolEmpleados) object;
        if ((this.idRolEmpleados == null && other.idRolEmpleados != null) || (this.idRolEmpleados != null && !this.idRolEmpleados.equals(other.idRolEmpleados))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.RolEmpleados[ idRolEmpleados=" + idRolEmpleados + " ]";
    }
    
}
