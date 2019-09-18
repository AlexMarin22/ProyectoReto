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
@Table(name = "empleados")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empleados.findAll", query = "SELECT e FROM Empleados e")
    , @NamedQuery(name = "Empleados.findByIdEmpleados", query = "SELECT e FROM Empleados e WHERE e.idEmpleados = :idEmpleados")
    , @NamedQuery(name = "Empleados.findByFechaNacimiento", query = "SELECT e FROM Empleados e WHERE e.fechaNacimiento = :fechaNacimiento")
    , @NamedQuery(name = "Empleados.findByFechaActivacion", query = "SELECT e FROM Empleados e WHERE e.fechaActivacion = :fechaActivacion")
    , @NamedQuery(name = "Empleados.findByFechaInactivacion", query = "SELECT e FROM Empleados e WHERE e.fechaInactivacion = :fechaInactivacion")})
public class Empleados implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_empleados")
    private Integer idEmpleados;
    @Basic(optional = false)
    @Lob
    @Column(name = "cedula")
    private String cedula;
    @Basic(optional = false)
    @Lob
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Lob
    @Column(name = "apellido")
    private String apellido;
    @Basic(optional = false)
    @Lob
    @Column(name = "usuario")
    private String usuario;
    @Basic(optional = false)
    @Lob
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @Basic(optional = false)
    @Lob
    @Column(name = "direccion")
    private String direccion;
    @Basic(optional = false)
    @Lob
    @Column(name = "telefono")
    private String telefono;
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
    @Basic(optional = false)
    @Lob
    @Column(name = "observacion")
    private String observacion;
    @Basic(optional = false)
    @Lob
    @Column(name = "imagen")
    private String imagen;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEmpleado")
    private Collection<Marcaciones> marcacionesCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEmpleados")
    private Collection<RolEmpleados> rolEmpleadosCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEmpleados")
    private Collection<Huellas> huellasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEmpleados")
    private Collection<JornadaEmpleado> jornadaEmpleadoCollection;

    public Empleados() {
    }

    public Empleados(Integer idEmpleados) {
        this.idEmpleados = idEmpleados;
    }

    public Empleados(Integer idEmpleados, String cedula, String nombre, String apellido, String usuario, String password, Date fechaNacimiento, String direccion, String telefono, Date fechaActivacion, Date fechaInactivacion, String estado, String observacion, String imagen) {
        this.idEmpleados = idEmpleados;
        this.cedula = cedula;
        this.nombre = nombre;
        this.apellido = apellido;
        this.usuario = usuario;
        this.password = password;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.telefono = telefono;
        this.fechaActivacion = fechaActivacion;
        this.fechaInactivacion = fechaInactivacion;
        this.estado = estado;
        this.observacion = observacion;
        this.imagen = imagen;
    }

    public Integer getIdEmpleados() {
        return idEmpleados;
    }

    public void setIdEmpleados(Integer idEmpleados) {
        this.idEmpleados = idEmpleados;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
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

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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
    public Collection<Marcaciones> getMarcacionesCollection() {
        return marcacionesCollection;
    }

    public void setMarcacionesCollection(Collection<Marcaciones> marcacionesCollection) {
        this.marcacionesCollection = marcacionesCollection;
    }

    @XmlTransient
    public Collection<RolEmpleados> getRolEmpleadosCollection() {
        return rolEmpleadosCollection;
    }

    public void setRolEmpleadosCollection(Collection<RolEmpleados> rolEmpleadosCollection) {
        this.rolEmpleadosCollection = rolEmpleadosCollection;
    }

    @XmlTransient
    public Collection<Huellas> getHuellasCollection() {
        return huellasCollection;
    }

    public void setHuellasCollection(Collection<Huellas> huellasCollection) {
        this.huellasCollection = huellasCollection;
    }

    @XmlTransient
    public Collection<JornadaEmpleado> getJornadaEmpleadoCollection() {
        return jornadaEmpleadoCollection;
    }

    public void setJornadaEmpleadoCollection(Collection<JornadaEmpleado> jornadaEmpleadoCollection) {
        this.jornadaEmpleadoCollection = jornadaEmpleadoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmpleados != null ? idEmpleados.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empleados)) {
            return false;
        }
        Empleados other = (Empleados) object;
        if ((this.idEmpleados == null && other.idEmpleados != null) || (this.idEmpleados != null && !this.idEmpleados.equals(other.idEmpleados))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Empleados[ idEmpleados=" + idEmpleados + " ]";
    }
    
}
