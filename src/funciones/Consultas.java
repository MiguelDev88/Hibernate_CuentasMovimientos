package funciones;
import POJOS.*;
import cuentacorrientemiguel.HibernateUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;
import java.util.Iterator;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;


// @author Miguel

public class Consultas {
    
    public static void consultas (BufferedReader leer) throws IOException {
        
        byte op=1;

        while(op!=0){
            op=Menu.menuConsultas(leer);
            switch(op){
                case 1:
                    datosCliente(leer);
                    break;
                case 2:
                    datosMovimiento(leer);
                    break;
                    

            }
        }
    }
    
    public static void datosCliente (BufferedReader leer) throws IOException {
        
        C_Cliente cliente;
        C_Cuenta cuenta;
        Session sesion;
        String nombre;
        
        sesion=HibernateUtil.getSession();
        
        System.out.println("Introduzca del nombre del cliente a buscar:");
        nombre=leer.readLine();
        
        try{
            
            cliente=(C_Cliente)sesion.createQuery("FROM POJOS.C_Cliente s WHERE s.nombre='"+nombre+"'").uniqueResult();
            
            if(cliente!=null)
            {
                Iterator cuentas=cliente.getCuentas().iterator();
                System.out.println("CUENTAS DEL CLIENTE "+cliente.getNombre()+":");
                
                while(cuentas.hasNext())
                {
                    cuenta=(C_Cuenta)cuentas.next();
                    
                    if(cuenta instanceof C_CuentaCorriente)
                        verCuentaCorriente(cuenta);
                    else
                        verCuentaPlazo(cuenta); 
                }  
            }
            else 
                System.out.println("\n No se ha encontrado al cliente \n");
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        sesion.close();
        
    }
    
    public static void datosMovimiento (BufferedReader leer) throws IOException {
        
        C_Cuenta cuenta;
        C_Movimiento movimiento;
        Session sesion;
        Date fechaInicio,fechaFin;
        int numero;
        
        
        sesion=HibernateUtil.getSession();
        
        try{
            
            System.out.println("Introduzca del número de la cuenta a buscar:");
            numero=Integer.parseInt(leer.readLine());
            
            cuenta=(C_Cuenta)sesion.get(C_Cuenta.class,numero);
            
            if(cuenta!=null)
            {
                System.out.println("Introducir fecha de inicio de consulta (año-mes-día):");
                fechaInicio=Date.valueOf(leer.readLine());
                System.out.println("Introducir fecha de fin de consulta (año-mes-día):");
                fechaFin=Date.valueOf(leer.readLine());
                
                Iterator movimientos=sesion.createQuery("FROM POJOS.C_Movimiento m WHERE m.cuenta='"+cuenta.getNumero()
                                                          + "'AND m.fecha BETWEEN '"+fechaInicio+"' AND '"+fechaFin+"'").iterate();
                                                          
                System.out.println("MOVIMIENTOS DE LA CUENTA: "+cuenta.getNumero()+":");
                System.out.println("--------------------------------------------------");
                System.out.println("|   FECHA   |   HORA   |   CLIENTE   | OPERACION | IMPORTE |");
                
                while(movimientos.hasNext()){
                    
                    movimiento=(C_Movimiento)movimientos.next();
                    System.out.printf("|%11s|%10s|%13s|%11s|%9s|%n",movimiento.getFecha(),movimiento.getHora(),movimiento.getCliente().getNombre(),
                                                            movimiento.getOperacion(),movimiento.getImporte());

                }
                System.out.println("--------------------------------------------------");
                
            }
            else 
                System.out.println("\n No se ha encontrado la cuenta \n");
            
        }catch(Exception e){
            System.out.println("\n - No hay movimientos que mostrar -");
        }
        
        sesion.close();
        
    }
    
    public static void verCuentaCorriente (C_Cuenta cuenta) throws IOException {
     
        System.out.println("----------------------------------------------------------------------");
        System.out.println("|   NUMERO   |  SUCURSAL  |    TIPO    | SALDO_ACTUAL |");
        System.out.printf("|%12s|%12s|%12s|%14s|%n",cuenta.getNumero(),cuenta.getSucursal(),"C_Corriente",
                                                ((C_CuentaCorriente)cuenta).getSaldoActual());
        System.out.println("----------------------------------------------------------------------\n");

    }
    
    public static void verCuentaPlazo (C_Cuenta cuenta) throws IOException {
     
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("|   NUMERO   |  SUCURSAL  |    TIPO    |   INTERESES   | FECHA_FIN | DEPOSITO |");
        System.out.printf("|%12s|%12s|%12s|%15s|%14s|%10s|%n",cuenta.getNumero(),cuenta.getSucursal(),"C_Plazo",((C_CuentaPlazo)cuenta).getIntereses(),
                                                ((C_CuentaPlazo)cuenta).getFechaVencimiento(),((C_CuentaPlazo)cuenta).getDepositoPlazo());
        System.out.println("-------------------------------------------------------------------------------\n");

    }

}