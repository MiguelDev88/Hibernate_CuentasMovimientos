package funciones;
import POJOS.*;
import cuentacorrientemiguel.HibernateUtil;
import java.io.BufferedReader;
import java.io.IOException;
import org.hibernate.Session;


// @author Miguel

public class Modificar {
    
    
    public static void modificar (BufferedReader leer) throws IOException {
        
        byte op=1;
        int numero;
        String dni;
        Session sesion;
        C_Cuenta cuenta;
        C_Cliente cliente;
        

        while(op!=0){
            try{
                op=Menu.menuModificar(leer);
                switch(op){
                    case 1:
                        System.out.println("Introducir dni del cliente a modificar:");
                        dni=leer.readLine();

                        sesion=HibernateUtil.getSession();
                        cliente=(C_Cliente)sesion.get(C_Cliente.class, dni);

                        if(cliente!=null){
                                sesion.beginTransaction();
                                modificarCliente(cliente, leer);
                                sesion.getTransaction().commit();
                        }else
                            System.out.println("\n Cliente no encontrado \n");
                        break;
                    case 2:
                        System.out.println("Introducir número de cuenta a modificar");
                        numero=Integer.parseInt(leer.readLine());

                        System.out.println("Introducir dni del cliente:");
                        dni=leer.readLine();

                        sesion=HibernateUtil.getSession();
                        cuenta=(C_Cuenta)sesion.get(C_Cuenta.class, numero);
                        cliente=(C_Cliente)sesion.get(C_Cliente.class, dni);

                        if(cuenta!=null && cuenta instanceof C_CuentaPlazo && cuenta.getClientes().contains(cliente)){
                                sesion.beginTransaction();
                                modificarCuentaPlazo(cuenta, leer);
                                sesion.getTransaction().commit();
                        }else
                            System.out.println("\n Cuenta no encontrada \n");
                        break;
                }
            }catch(Exception e) {
                
            }
        }
    }
    
    public static void modificarCuentaPlazo (C_Cuenta cuenta, BufferedReader leer) throws IOException {
        
        String fechaVencimiento;
        int intereses;
        byte op;
        
        
        System.out.println("¿Qué desea modificar?"
                + "\n1.Intereses"
                + "\n2.Fecha de Vencimiento"
                + "\n0.Finalizar");
        op=Byte.parseByte(leer.readLine());
        
        switch(op){
            case 1:
                System.out.println("Introducir nuevo Interés:");
                intereses=Integer.parseInt(leer.readLine());
                ((C_CuentaPlazo)cuenta).setIntereses(intereses);
                System.out.println("\n Intereses modificados \n");
                break;
            case 2:
                System.out.println("Introducir nueva Fecha de Vencimiento:");
                fechaVencimiento=leer.readLine();
                ((C_CuentaPlazo)cuenta).setFechaVencimiento(fechaVencimiento);
                System.out.println("\n Fecha modificada \n");
                break;
        }
        
    }
    
    public static void modificarCliente (C_Cliente cliente, BufferedReader leer) throws IOException {
        
        String dirección;
        byte op;
        
        
        System.out.println("¿Qué desea modificar?"
                + "\n1.Dirección"
                + "\n0.Finalizar");
        op=Byte.parseByte(leer.readLine());
        
        switch(op){
            case 1:
                System.out.println("Introducir nueva Dirección:");
                dirección=leer.readLine();
                cliente.setDireccion(dirección);
                System.out.println("\n Dirección modificada \n");
                break;
        }
        
    }
    
    
}
