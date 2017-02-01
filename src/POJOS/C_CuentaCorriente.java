package POJOS;


// @author Miguel

public class C_CuentaCorriente extends C_Cuenta {
    
    private int saldoActual;
    
    
    public C_CuentaCorriente () {}
    
    public C_CuentaCorriente (int numero, String sucursal, int saldoActual) {
        
        super(numero,sucursal);
        this.saldoActual=saldoActual;

    }

    public int getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(int saldoActual) {
        this.saldoActual = saldoActual;
    }

}
