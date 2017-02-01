package POJOS;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


// @author Miguel

public class C_Cliente implements Serializable {
    
    private String dni;
    private String nombre,direccion;
    private Set <C_Cuenta> cuentas;
    
    
    public C_Cliente () {}
    
    public C_Cliente (String dni, String nombre, String direccion) {
        
        this.dni=dni;
        this.nombre=nombre;
        this.direccion=direccion;
        this.cuentas= new HashSet();
        
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Set<C_Cuenta> getCuentas() {
        return cuentas;
    }

    public void setCuentas(Set<C_Cuenta> cuentas) {
        this.cuentas = cuentas;
    }
    
}
