/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "marcaciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Marcaciones.findAll", query = "SELECT m FROM Marcaciones m")
    , @NamedQuery(name = "Marcaciones.findByIdMarcacion", query = "SELECT m FROM Marcaciones m WHERE m.idMarcacion = :idMarcacion")
    , @NamedQuery(name = "Marcaciones.findByFecha", query = "SELECT m FROM Marcaciones m WHERE m.fecha = :fecha")
    , @NamedQuery(name = "Marcaciones.findByObservacion", query = "SELECT m FROM Marcaciones m WHERE m.observacion = :observacion")
    , @NamedQuery(name = "Marcaciones.findByEstado", query = "SELECT m FROM Marcaciones m WHERE m.estado = :estado")
    , @NamedQuery(name = "Marcaciones.findByMarcacionescol", query = "SELECT m FROM Marcaciones m WHERE m.marcacionescol = :marcacionescol")})
public class Marcaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_marcacion")
    private Integer idMarcacion;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "estado")
    private String estado;
    @Column(name = "marcacionescol")
    private String marcacionescol;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne
    private Empleados idEmpleado;
    @JoinColumn(name = "id_detalle_marcacion", referencedColumnName = "id_detalle_marcacion")
    @ManyToOne
    private DetalleMarcaciones idDetalleMarcacion;

    public Marcaciones() {
    }

    public Marcaciones(Integer idMarcacion) {
        this.idMarcacion = idMarcacion;
    }

    public Integer getIdMarcacion() {
        return idMarcacion;
    }

    public void setIdMarcacion(Integer idMarcacion) {
        this.idMarcacion = idMarcacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMarcacionescol() {
        return marcacionescol;
    }

    public void setMarcacionescol(String marcacionescol) {
        this.marcacionescol = marcacionescol;
    }

    public Empleados getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleados idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public DetalleMarcaciones getIdDetalleMarcacion() {
        return idDetalleMarcacion;
    }

    public void setIdDetalleMarcacion(DetalleMarcaciones idDetalleMarcacion) {
        this.idDetalleMarcacion = idDetalleMarcacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMarcacion != null ? idMarcacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Marcaciones)) {
            return false;
        }
        Marcaciones other = (Marcaciones) object;
        if ((this.idMarcacion == null && other.idMarcacion != null) || (this.idMarcacion != null && !this.idMarcacion.equals(other.idMarcacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Marcaciones[ idMarcacion=" + idMarcacion + " ]";
    }
    
}
