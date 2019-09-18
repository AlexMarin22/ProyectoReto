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
    , @NamedQuery(name = "Huellas.findByIdHuella", query = "SELECT h FROM Huellas h WHERE h.idHuella = :idHuella")
    , @NamedQuery(name = "Huellas.findByFechaActivacion", query = "SELECT h FROM Huellas h WHERE h.fechaActivacion = :fechaActivacion")
    , @NamedQuery(name = "Huellas.findByFechaInactivacion", query = "SELECT h FROM Huellas h WHERE h.fechaInactivacion = :fechaInactivacion")
    , @NamedQuery(name = "Huellas.findByEstado", query = "SELECT h FROM Huellas h WHERE h.estado = :estado")})
public class Huellas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_huella")
    private Integer idHuella;
    @Lob
    @Column(name = "huella")
    private byte[] huella;
    @Column(name = "fecha_activacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActivacion;
    @Column(name = "fecha_inactivacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInactivacion;
    @Column(name = "estado")
    private String estado;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne
    private Empleados idEmpleado;

    public Huellas() {
    }

    public Huellas(Integer idHuella) {
        this.idHuella = idHuella;
    }

    public Integer getIdHuella() {
        return idHuella;
    }

    public void setIdHuella(Integer idHuella) {
        this.idHuella = idHuella;
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

    public Empleados getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleados idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHuella != null ? idHuella.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Huellas)) {
            return false;
        }
        Huellas other = (Huellas) object;
        if ((this.idHuella == null && other.idHuella != null) || (this.idHuella != null && !this.idHuella.equals(other.idHuella))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Huellas[ idHuella=" + idHuella + " ]";
    }
    
}
