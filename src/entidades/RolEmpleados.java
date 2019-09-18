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
@Table(name = "rol_empleados")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RolEmpleados.findAll", query = "SELECT r FROM RolEmpleados r")
    , @NamedQuery(name = "RolEmpleados.findByIdRolEmpleados", query = "SELECT r FROM RolEmpleados r WHERE r.idRolEmpleados = :idRolEmpleados")})
public class RolEmpleados implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_rol_empleados")
    private Integer idRolEmpleados;
    @JoinColumn(name = "id_empleados", referencedColumnName = "id_empleados")
    @ManyToOne(optional = false)
    private Empleados idEmpleados;
    @JoinColumn(name = "id_rol", referencedColumnName = "id_rol")
    @ManyToOne(optional = false)
    private Rol idRol;

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

    public Empleados getIdEmpleados() {
        return idEmpleados;
    }

    public void setIdEmpleados(Empleados idEmpleados) {
        this.idEmpleados = idEmpleados;
    }

    public Rol getIdRol() {
        return idRol;
    }

    public void setIdRol(Rol idRol) {
        this.idRol = idRol;
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
