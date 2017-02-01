package POJOS;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;


// @author Miguel

public class C_Movimiento implements Serializable {
    
    private Date fecha;
    private Time hora;
    private C_Cuenta cuenta;
    private C_Cliente cliente;
    private char operacion;
    private float importe;
    
    public C_Movimiento () {}
    
    public C_Movimiento (Date fecha, Time hora, char operacion, float importe, C_Cliente cliente, C_Cuenta cuenta) {
        
        this.fecha=fecha;
        this.hora=hora;
        this.operacion=operacion;
        this.cuenta=cuenta;
        this.importe=importe;
        this.cliente=cliente;
        
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public C_Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(C_Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public C_Cliente getCliente() {
        return cliente;
    }

    public void setCliente(C_Cliente cliente) {
        this.cliente = cliente;
    }

    public char getOperacion() {
        return operacion;
    }

    public void setOperacion(char operacion) {
        this.operacion = operacion;
    }

    public float getImporte() {
        return importe;
    }

    public void setImporte(float importe) {
        this.importe = importe;
    }
    
}
