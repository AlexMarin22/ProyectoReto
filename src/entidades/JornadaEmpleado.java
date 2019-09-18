/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author user
 */
@Entity
@Table(name = "jornada_empleado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "JornadaEmpleado.findAll", query = "SELECT j FROM JornadaEmpleado j")
    , @NamedQuery(name = "JornadaEmpleado.findByIdJornadaEmpleado", query = "SELECT j FROM JornadaEmpleado j WHERE j.idJornadaEmpleado = :idJornadaEmpleado")
    , @NamedQuery(name = "JornadaEmpleado.findByEstado", query = "SELECT j FROM JornadaEmpleado j WHERE j.estado = :estado")})
public class JornadaEmpleado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_jornada_empleado")
    private Integer idJornadaEmpleado;
    @Column(name = "estado")
    private String estado;
    @JoinColumn(name = "id_empleado", referencedColumnName = "id_empleado")
    @ManyToOne
    private Empleados idEmpleado;
    @JoinColumn(name = "id_jornada", referencedColumnName = "id_jornada")
    @ManyToOne
    private Jornada idJornada;

    public JornadaEmpleado() {
    }

    public JornadaEmpleado(Integer idJornadaEmpleado) {
        this.idJornadaEmpleado = idJornadaEmpleado;
    }

    public Integer getIdJornadaEmpleado() {
        return idJornadaEmpleado;
    }

    public void setIdJornadaEmpleado(Integer idJornadaEmpleado) {
        this.idJornadaEmpleado = idJornadaEmpleado;
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

    public Jornada getIdJornada() {
        return idJornada;
    }

    public void setIdJornada(Jornada idJornada) {
        this.idJornada = idJornada;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idJornadaEmpleado != null ? idJornadaEmpleado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JornadaEmpleado)) {
            return false;
        }
        JornadaEmpleado other = (JornadaEmpleado) object;
        if ((this.idJornadaEmpleado == null && other.idJornadaEmpleado != null) || (this.idJornadaEmpleado != null && !this.idJornadaEmpleado.equals(other.idJornadaEmpleado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.JornadaEmpleado[ idJornadaEmpleado=" + idJornadaEmpleado + " ]";
    }
    
}
