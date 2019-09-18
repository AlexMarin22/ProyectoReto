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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "empleados")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empleados.findAll", query = "SELECT e FROM Empleados e")
    , @NamedQuery(name = "Empleados.findByIdEmpleado", query = "SELECT e FROM Empleados e WHERE e.idEmpleado = :idEmpleado")
    , @NamedQuery(name = "Empleados.findByCedula", query = "SELECT e FROM Empleados e WHERE e.cedula = :cedula")
    , @NamedQuery(name = "Empleados.findByNombres", query = "SELECT e FROM Empleados e WHERE e.nombres = :nombres")
    , @NamedQuery(name = "Empleados.findByApellidos", query = "SELECT e FROM Empleados e WHERE e.apellidos = :apellidos")
    , @NamedQuery(name = "Empleados.findByUsuario", query = "SELECT e FROM Empleados e WHERE e.usuario = :usuario")
    , @NamedQuery(name = "Empleados.findByPassword", query = "SELECT e FROM Empleados e WHERE e.password = :password")
    , @NamedQuery(name = "Empleados.findByFechaDeNacimiento", query = "SELECT e FROM Empleados e WHERE e.fechaDeNacimiento = :fechaDeNacimiento")
    , @NamedQuery(name = "Empleados.findByDireccion", query = "SELECT e FROM Empleados e WHERE e.direccion = :direccion")
    , @NamedQuery(name = "Empleados.findByTelefono", query = "SELECT e FROM Empleados e WHERE e.telefono = :telefono")
    , @NamedQuery(name = "Empleados.findByFechaActivacion", query = "SELECT e FROM Empleados e WHERE e.fechaActivacion = :fechaActivacion")
    , @NamedQuery(name = "Empleados.findByFechaInactivacion", query = "SELECT e FROM Empleados e WHERE e.fechaInactivacion = :fechaInactivacion")
    , @NamedQuery(name = "Empleados.findByEstado", query = "SELECT e FROM Empleados e WHERE e.estado = :estado")
    , @NamedQuery(name = "Empleados.findByObservacion", query = "SELECT e FROM Empleados e WHERE e.observacion = :observacion")
    , @NamedQuery(name = "Empleados.findByImagen", query = "SELECT e FROM Empleados e WHERE e.imagen = :imagen")})
public class Empleados implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_empleado")
    private Integer idEmpleado;
    @Column(name = "cedula")
    private String cedula;
    @Column(name = "nombres")
    private String nombres;
    @Column(name = "apellidos")
    private String apellidos;
    @Column(name = "usuario")
    private String usuario;
    @Column(name = "password")
    private String password;
    @Column(name = "fecha_de_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaDeNacimiento;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "fecha_activacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActivacion;
    @Column(name = "fecha_inactivacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInactivacion;
    @Column(name = "estado")
    private String estado;
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "imagen")
    private String imagen;
    @OneToMany(mappedBy = "idEmpleado")
    private List<Marcaciones> marcacionesList;
    @JoinColumn(name = "id_rol_empleados", referencedColumnName = "id_rol_empleados")
    @ManyToOne
    private RolEmpleados idRolEmpleados;
    @OneToMany(mappedBy = "idEmpleado")
    private List<Huellas> huellasList;
    @OneToMany(mappedBy = "idEmpleado")
    private List<JornadaEmpleado> jornadaEmpleadoList;

    public Empleados() {
    }

    public Empleados(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Integer getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Integer idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Date fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @XmlTransient
    public List<Marcaciones> getMarcacionesList() {
        return marcacionesList;
    }

    public void setMarcacionesList(List<Marcaciones> marcacionesList) {
        this.marcacionesList = marcacionesList;
    }

    public RolEmpleados getIdRolEmpleados() {
        return idRolEmpleados;
    }

    public void setIdRolEmpleados(RolEmpleados idRolEmpleados) {
        this.idRolEmpleados = idRolEmpleados;
    }

    @XmlTransient
    public List<Huellas> getHuellasList() {
        return huellasList;
    }

    public void setHuellasList(List<Huellas> huellasList) {
        this.huellasList = huellasList;
    }

    @XmlTransient
    public List<JornadaEmpleado> getJornadaEmpleadoList() {
        return jornadaEmpleadoList;
    }

    public void setJornadaEmpleadoList(List<JornadaEmpleado> jornadaEmpleadoList) {
        this.jornadaEmpleadoList = jornadaEmpleadoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmpleado != null ? idEmpleado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empleados)) {
            return false;
        }
        Empleados other = (Empleados) object;
        if ((this.idEmpleado == null && other.idEmpleado != null) || (this.idEmpleado != null && !this.idEmpleado.equals(other.idEmpleado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Empleados[ idEmpleado=" + idEmpleado + " ]";
    }
    
}
