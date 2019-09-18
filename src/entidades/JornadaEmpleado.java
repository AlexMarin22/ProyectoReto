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
import javax.persistence.Lob;
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
    , @NamedQuery(name = "JornadaEmpleado.findByIdJornadaEmpleado", query = "SELECT j FROM JornadaEmpleado j WHERE j.idJornadaEmpleado = :idJornadaEmpleado")})
public class JornadaEmpleado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_jornada_empleado")
    private Integer idJornadaEmpleado;
    @Basic(optional = false)
    @Lob
    @Column(name = "estado")
    private String estado;
    @JoinColumn(name = "id_jornada", referencedColumnName = "id_jornada")
    @ManyToOne(optional = false)
    private Jornada idJornada;
    @JoinColumn(name = "id_empleados", referencedColumnName = "id_empleados")
    @ManyToOne(optional = false)
    private Empleados idEmpleados;

    public JornadaEmpleado() {
    }

    public JornadaEmpleado(Integer idJornadaEmpleado) {
        this.idJornadaEmpleado = idJornadaEmpleado;
    }

    public JornadaEmpleado(Integer idJornadaEmpleado, String estado) {
        this.idJornadaEmpleado = idJornadaEmpleado;
        this.estado = estado;
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

    public Jornada getIdJornada() {
        return idJornada;
    }

    public void setIdJornada(Jornada idJornada) {
        this.idJornada = idJornada;
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
