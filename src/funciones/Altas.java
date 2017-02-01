package funciones;
import POJOS.*;
import cuentacorrientemiguel.HibernateUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import org.hibernate.Session;


// @author Miguel

public class Altas {
    
    public static void altas (BufferedReader leer) throws IOException {
        
        
        byte op=1;

        while(op!=0){
            op=Menu.menuAltas(leer);
            switch(op){
                case 1:
                case 2:
                    altaCuenta(leer, op);
                    break;
                case 3:
                    altaCliente(leer);
                    break;
                case 4:
                    altaMovimiento(leer);
                    break;
            }
        }
    }
    
    public static void altaCuenta (BufferedReader leer, byte op) throws IOException {
        
        
        C_Cuenta cuenta;
        Session sesion;
                
        
        sesion=HibernateUtil.getSession();
        
        try{
            if(op==1)
                cuenta=nuevaCuentaCorriente(leer);
            else
                cuenta=nuevaCuentaPlazo(leer);
            
            sesion.beginTransaction();
            sesion.save(cuenta);
            sesion.getTransaction().commit();
            System.out.println("\n - Cuenta Registrada - \n");
            
            Asociar.asociarCliente(cuenta, sesion, leer);
            
        }catch(Exception e){
            System.out.println("\n - Error en el Alta de la cuenta \n");
        }
        
        sesion.close();
        
    }
    
    public static void altaCliente (BufferedReader leer) throws IOException {
        
        
        C_Cliente cliente;
        Session sesion;
                
        sesion=HibernateUtil.getSession();
        
        try{
            cliente=nuevoCliente(leer);
            
            sesion.beginTransaction();
            sesion.save(cliente);
            sesion.getTransaction().commit();
            System.out.println("\n - Cliente Registrado - \n");

            Asociar.asociarCuenta(cliente, sesion, leer);
            
        }catch(Exception e)
        {
            System.out.println("\n - Error en el Alta del Cliente \n");
        }
        
        sesion.close();
        
    }
    
    public static void altaMovimiento (BufferedReader leer) throws IOException {
        
        
        C_Movimiento movimiento;
        C_Cuenta cuenta;
        C_Cliente cliente;
        Session sesion;
        int numero;
        String dni;
        
                
        sesion=HibernateUtil.getSession();
        
        try{
            System.out.println("Introduzca dni del cliente que realiza la operación:");
            dni=leer.readLine();
            cliente=(C_Cliente)sesion.get(C_Cliente.class, dni);
            
            if(cliente!=null){
                
                System.out.println("Introduzca número de cuenta en la que desea realizar la operación:");
                numero=Integer.parseInt(leer.readLine());
                cuenta=(C_Cuenta)sesion.get(C_Cuenta.class,numero);
                
                if(cuenta!=null && cuenta.getClientes().contains(cliente))
                {
                    sesion.beginTransaction();
                    movimiento=nuevoMovimiento(leer,cuenta,cliente);
                    sesion.save(movimiento);
                    sesion.getTransaction().commit();
                    System.out.println(" Movimiento Registrado ");
                }
                else
                    System.out.println("Cuenta no encontrada");
            }
            else
            {
                System.out.println("Cliente no encontrado");
            }

            sesion.close();
            
            
        }catch(Exception e)
        {
            System.out.println("\n - Error en el Alta del movimiento \n");
        }
        
    }
    
    public static C_Cliente nuevoCliente (BufferedReader leer) throws IOException {
        
        
        String dni, nombre,direccion;
        
        System.out.println("Introducir dni:");
        dni=leer.readLine();
        System.out.println("Introducir nombre");
        nombre=leer.readLine();
        System.out.println("Introducir dirección:");
        direccion=leer.readLine();
        
        C_Cliente cliente=new C_Cliente(dni,nombre,direccion);
        
        return cliente;
        
    }
    
    public static C_Cuenta nuevaCuentaCorriente (BufferedReader leer) throws IOException {
        
        
        int numero, saldoActual;
        String sucursal;
        
        System.out.println("Introducir número de cuenta:");
        numero=Integer.parseInt(leer.readLine());
        System.out.println("Introducir sucursal:");
        sucursal=leer.readLine();
        System.out.println("Introducir saldoActual:");
        saldoActual=Integer.parseInt(leer.readLine());
        
        C_Cuenta cuenta=new C_CuentaCorriente(numero,sucursal,saldoActual);
        
        return cuenta;
    
    }
    
    public static C_Cuenta nuevaCuentaPlazo (BufferedReader leer) throws IOException {
        
        
        int numero, intereses;
        String sucursal, fechaVencimiento;
        float depositoPlazo;
        
        System.out.println("Introducir número de cuenta:");
        numero=Integer.parseInt(leer.readLine());
        System.out.println("Introducir sucursal:");
        sucursal=leer.readLine();
        System.out.println("Introducir fechaVencimiento:");
        fechaVencimiento=leer.readLine();
        System.out.println("Introducir intereses:");
        intereses=Integer.parseInt(leer.readLine());
        System.out.println("Introducir depositoPlazo");
        depositoPlazo=Float.parseFloat(leer.readLine());
        
        C_Cuenta cuenta=new C_CuentaPlazo(numero,sucursal,intereses,fechaVencimiento,depositoPlazo);
        
        return cuenta;
    
    }
    
    public static C_Movimiento nuevoMovimiento (BufferedReader leer, C_Cuenta cuenta, C_Cliente cliente) throws IOException {
        
        
        C_Movimiento movimiento=null;
        Date fecha=new Date(new java.util.Date().getTime());
        Time hora=new Time(fecha.getTime());
        char operacion;
        float importe;

        System.out.println("Introduzca operación:");
        operacion=leer.readLine().toLowerCase().charAt(0);
        System.out.println("Introducir importe:");
        importe=Float.parseFloat(leer.readLine());
        
        switch(operacion){
            case 'i':
                if(cuenta instanceof C_CuentaCorriente)
                    ((C_CuentaCorriente)cuenta).setSaldoActual(((C_CuentaCorriente)cuenta).getSaldoActual()+(int)importe);
                else
                    ((C_CuentaPlazo)cuenta).setDepositoPlazo(((C_CuentaPlazo)cuenta).getDepositoPlazo()+(int)importe);
                
                movimiento=new C_Movimiento(fecha,hora,operacion,importe,cliente,cuenta);
                break;
            case 'r': //if raro-- cambiar!
                    if(cuenta instanceof C_CuentaCorriente && ((C_CuentaCorriente)cuenta).getSaldoActual()>=importe ){
                        ((C_CuentaCorriente)cuenta).setSaldoActual(((C_CuentaCorriente)cuenta).getSaldoActual()-(int)importe);
                        movimiento=new C_Movimiento(fecha,hora,operacion,importe,cliente,cuenta);
                        
                    }else if (cuenta instanceof C_CuentaPlazo && ((C_CuentaPlazo)cuenta).getDepositoPlazo()>= importe ){
                        ((C_CuentaPlazo)cuenta).setDepositoPlazo(((C_CuentaPlazo)cuenta).getDepositoPlazo()-(int)importe);
                        movimiento=new C_Movimiento(fecha,hora,operacion,importe,cliente,cuenta);
                    }else
                        System.out.println("\n - No es posible retirar esa cantidad de la cuenta - ");
                break;
        }

        return movimiento;
  
    }
    
}
