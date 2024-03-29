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
@Table(name = "huellas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Huellas.findAll", query = "SELECT h FROM Huellas h")
    , @NamedQuery(name = "Huellas.findByIdHuellas", query = "SELECT h FROM Huellas h WHERE h.idHuellas = :idHuellas")
    , @NamedQuery(name = "Huellas.findByFechaActivacion", query = "SELECT h FROM Huellas h WHERE h.fechaActivacion = :fechaActivacion")
    , @NamedQuery(name = "Huellas.findByFechaInactivacion", query = "SELECT h FROM Huellas h WHERE h.fechaInactivacion = :fechaInactivacion")})
public class Huellas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_huellas")
    private Integer idHuellas;
    @Basic(optional = false)
    @Lob
    @Column(name = "huella")
    private byte[] huella;
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
    @JoinColumn(name = "id_empleados", referencedColumnName = "id_empleados")
    @ManyToOne(optional = false)
    private Empleados idEmpleados;

    public Huellas() {
    }

    public Huellas(Integer idHuellas) {
        this.idHuellas = idHuellas;
    }

    public Huellas(Integer idHuellas, byte[] huella, Date fechaActivacion, Date fechaInactivacion, String estado) {
        this.idHuellas = idHuellas;
        this.huella = huella;
        this.fechaActivacion = fechaActivacion;
        this.fechaInactivacion = fechaInactivacion;
        this.estado = estado;
    }

    public Integer getIdHuellas() {
        return idHuellas;
    }

    public void setIdHuellas(Integer idHuellas) {
        this.idHuellas = idHuellas;
    }

    public byte[] getHuella() {
        return huella;
    }

    public void setHuella(byte[] huella) {
        this.huella = huella;
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

    public Empleados getIdEmpleados() {
        return idEmpleados;
    }

    public void setIdEmpleados(Empleados idEmpleados) {
        this.idEmpleados = idEmpleados;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHuellas != null ? idHuellas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Huellas)) {
            return false;
        }
        Huellas other = (Huellas) object;
        if ((this.idHuellas == null && other.idHuellas != null) || (this.idHuellas != null && !this.idHuellas.equals(other.idHuellas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Huellas[ idHuellas=" + idHuellas + " ]";
    }
    
}
