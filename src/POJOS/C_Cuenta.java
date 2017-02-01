package POJOS;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


// @author Miguel

public class C_Cuenta implements Serializable {
    
    private int numero;
    private String sucursal;
    private Set <C_Cliente> clientes;
    
    
    public C_Cuenta () {}
    
    public C_Cuenta (int numero, String sucursal) {
        
        this.numero=numero;
        this.sucursal=sucursal;
        this.clientes=new HashSet();
        
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public Set<C_Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(Set<C_Cliente> clientes) {
        this.clientes = clientes;
    }
}
