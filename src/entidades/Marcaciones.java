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
import javax.persistence.Lob;
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
    , @NamedQuery(name = "Marcaciones.findByIdMarcaciones", query = "SELECT m FROM Marcaciones m WHERE m.idMarcaciones = :idMarcaciones")
    , @NamedQuery(name = "Marcaciones.findByFecha", query = "SELECT m FROM Marcaciones m WHERE m.fecha = :fecha")})
public class Marcaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_marcaciones")
    private Integer idMarcaciones;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @Lob
    @Column(name = "observacion")
    private String observacion;
    @Basic(optional = false)
    @Lob
    @Column(name = "estado")
    private String estado;
    @JoinColumn(name = "id_detalle_marcacion", referencedColumnName = "id_detalle_marcaciones")
    @ManyToOne(optional = false)
    private DetalleMarcaciones idDetalleMarcacion;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleados")
    @ManyToOne(optional = false)
    private Empleados idEmpleado;

    public Marcaciones() {
    }

    public Marcaciones(Integer idMarcaciones) {
        this.idMarcaciones = idMarcaciones;
    }

    public Marcaciones(Integer idMarcaciones, Date fecha, String observacion, String estado) {
        this.idMarcaciones = idMarcaciones;
        this.fecha = fecha;
        this.observacion = observacion;
        this.estado = estado;
    }

    public Integer getIdMarcaciones() {
        return idMarcaciones;
    }

    public void setIdMarcaciones(Integer idMarcaciones) {
        this.idMarcaciones = idMarcaciones;
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

    public DetalleMarcaciones getIdDetalleMarcacion() {
        return idDetalleMarcacion;
    }

    public void setIdDetalleMarcacion(DetalleMarcaciones idDetalleMarcacion) {
        this.idDetalleMarcacion = idDetalleMarcacion;
    }

    public Empleados getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleados idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMarcaciones != null ? idMarcaciones.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Marcaciones)) {
            return false;
        }
        Marcaciones other = (Marcaciones) object;
        if ((this.idMarcaciones == null && other.idMarcaciones != null) || (this.idMarcaciones != null && !this.idMarcaciones.equals(other.idMarcaciones))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Marcaciones[ idMarcaciones=" + idMarcaciones + " ]";
    }
    
}
