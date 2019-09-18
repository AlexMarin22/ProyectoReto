/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
    , @NamedQuery(name = "DetalleMarcaciones.findByIdDetalleMarcaciones", query = "SELECT d FROM DetalleMarcaciones d WHERE d.idDetalleMarcaciones = :idDetalleMarcaciones")
    , @NamedQuery(name = "DetalleMarcaciones.findByFechaActivacion", query = "SELECT d FROM DetalleMarcaciones d WHERE d.fechaActivacion = :fechaActivacion")
    , @NamedQuery(name = "DetalleMarcaciones.findByFechaInactivacion", query = "SELECT d FROM DetalleMarcaciones d WHERE d.fechaInactivacion = :fechaInactivacion")})
public class DetalleMarcaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_detalle_marcaciones")
    private Integer idDetalleMarcaciones;
    @Basic(optional = false)
    @Lob
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Lob
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "fecha_activacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActivacion;
    @Basic(optional = false)
    @Column(name = "fecha_inactivacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInactivacion;
    @Basic(optional = false)
    @Lob
    @Column(name = "estado")
    private String estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDetalleMarcacion")
    private Collection<Marcaciones> marcacionesCollection;

    public DetalleMarcaciones() {
    }

    public DetalleMarcaciones(Integer idDetalleMarcaciones) {
        this.idDetalleMarcaciones = idDetalleMarcaciones;
    }

    public DetalleMarcaciones(Integer idDetalleMarcaciones, String nombre, String descripcion, Date fechaActivacion, Date fechaInactivacion, String estado) {
        this.idDetalleMarcaciones = idDetalleMarcaciones;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaActivacion = fechaActivacion;
        this.fechaInactivacion = fechaInactivacion;
        this.estado = estado;
    }

    public Integer getIdDetalleMarcaciones() {
        return idDetalleMarcaciones;
    }

    public void setIdDetalleMarcaciones(Integer idDetalleMarcaciones) {
        this.idDetalleMarcaciones = idDetalleMarcaciones;
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
    public Collection<Marcaciones> getMarcacionesCollection() {
        return marcacionesCollection;
    }

    public void setMarcacionesCollection(Collection<Marcaciones> marcacionesCollection) {
        this.marcacionesCollection = marcacionesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDetalleMarcaciones != null ? idDetalleMarcaciones.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleMarcaciones)) {
            return false;
        }
        DetalleMarcaciones other = (DetalleMarcaciones) object;
        if ((this.idDetalleMarcaciones == null && other.idDetalleMarcaciones != null) || (this.idDetalleMarcaciones != null && !this.idDetalleMarcaciones.equals(other.idDetalleMarcaciones))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.DetalleMarcaciones[ idDetalleMarcaciones=" + idDetalleMarcaciones + " ]";
    }
    
}
