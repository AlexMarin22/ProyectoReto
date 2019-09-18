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
@Table(name = "detalle_marcaciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleMarcaciones.findAll", query = "SELECT d FROM DetalleMarcaciones d")
    , @NamedQuery(name = "DetalleMarcaciones.findByIdDetalleMarcacion", query = "SELECT d FROM DetalleMarcaciones d WHERE d.idDetalleMarcacion = :idDetalleMarcacion")
    , @NamedQuery(name = "DetalleMarcaciones.findByNombre", query = "SELECT d FROM DetalleMarcaciones d WHERE d.nombre = :nombre")
    , @NamedQuery(name = "DetalleMarcaciones.findByDescripcion", query = "SELECT d FROM DetalleMarcaciones d WHERE d.descripcion = :descripcion")
    , @NamedQuery(name = "DetalleMarcaciones.findByFechaActivacion", query = "SELECT d FROM DetalleMarcaciones d WHERE d.fechaActivacion = :fechaActivacion")
    , @NamedQuery(name = "DetalleMarcaciones.findByFechaInactivacion", query = "SELECT d FROM DetalleMarcaciones d WHERE d.fechaInactivacion = :fechaInactivacion")
    , @NamedQuery(name = "DetalleMarcaciones.findByEstado", query = "SELECT d FROM DetalleMarcaciones d WHERE d.estado = :estado")})
public class DetalleMarcaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_detalle_marcacion")
    private Integer idDetalleMarcacion;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fecha_activacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActivacion;
    @Column(name = "fecha_inactivacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInactivacion;
    @Column(name = "estado")
    private String estado;
    @OneToMany(mappedBy = "idDetalleMarcacion")
    private List<Marcaciones> marcacionesList;

    public DetalleMarcaciones() {
    }

    public DetalleMarcaciones(Integer idDetalleMarcacion) {
        this.idDetalleMarcacion = idDetalleMarcacion;
    }

    public Integer getIdDetalleMarcacion() {
        return idDetalleMarcacion;
    }

    public void setIdDetalleMarcacion(Integer idDetalleMarcacion) {
        this.idDetalleMarcacion = idDetalleMarcacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
    public List<Marcaciones> getMarcacionesList() {
        return marcacionesList;
    }

    public void setMarcacionesList(List<Marcaciones> marcacionesList) {
        this.marcacionesList = marcacionesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalleMarcacion != null ? idDetalleMarcacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleMarcaciones)) {
            return false;
        }
        DetalleMarcaciones other = (DetalleMarcaciones) object;
        if ((this.idDetalleMarcacion == null && other.idDetalleMarcacion != null) || (this.idDetalleMarcacion != null && !this.idDetalleMarcacion.equals(other.idDetalleMarcacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.DetalleMarcaciones[ idDetalleMarcacion=" + idDetalleMarcacion + " ]";
    }
    
}
