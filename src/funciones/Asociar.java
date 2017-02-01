package funciones;
import POJOS.*;
import java.io.BufferedReader;
import java.io.IOException;
import org.hibernate.Session;


// @author Miguel

public class Asociar {
    
    public static void asociarCliente (C_Cuenta cuenta, Session sesion, BufferedReader leer) throws IOException {
        
        C_Cliente cliente;
        byte op=0;
        String dni;
        
        do{
            try{
                System.out.println("¿Desea asociar un cliente nuevo o uno existente?"
                        + "\n1.Nuevo"
                        + "\n2.Existente"
                        + "\n0.Finalizar");
                op=Byte.parseByte(leer.readLine());
                switch(op){
                    case 1:
                        cliente=Altas.nuevoCliente(leer);
                        sesion.beginTransaction();
                        cuenta.getClientes().add(cliente);
                        sesion.getTransaction().commit();
                        System.out.println("\n - Cliente Asociado - \n");
                        break;
                    case 2:
                        System.out.println("Introduzca dni del cliente a asociar:");
                        dni=leer.readLine();
                        cliente=(C_Cliente)sesion.get(C_Cliente.class, dni);

                        if(cliente!=null)
                        {
                            sesion.beginTransaction();
                            cuenta.getClientes().add(cliente);
                            sesion.getTransaction().commit();
                            System.out.println("\n - Cliente Asociado - \n");
                        } 
                        else
                            System.out.println("\n No se ha encontrado al cliente \n");
                        break;
                }
            }catch(Exception e) {
                System.out.println("\n - Error en la Asociación - \n");
            }
        }while(op!=0);
        
    }
    
    
    public static void asociarCuenta (C_Cliente cliente, Session sesion, BufferedReader leer) throws IOException {
        
        C_Cuenta cuenta;
        byte op=0;
        int numero;
        
        do{
            try{
                System.out.println("¿Desea asociar una cuenta nueva o una existente?"
                        + "\n1.Nueva"
                        + "\n2.Existente"
                        + "\n0.Finalizar");
                op=Byte.parseByte(leer.readLine());
                switch(op){
                    case 1:
                        System.out.println("¿Cuenta Corriente o Cuenta a Plazo"
                                + "\n1.Cuenta Corriente"
                                + "\n2.Cuenta a Plazo");
                        op=Byte.parseByte(leer.readLine());
                        
                        if(op==1)
                            cuenta=Altas.nuevaCuentaCorriente(leer);
                        else
                            cuenta=Altas.nuevaCuentaPlazo(leer);
                        
                        sesion.beginTransaction();
                        cuenta.getClientes().add(cliente);
                        sesion.save(cuenta);
                        sesion.getTransaction().commit();
                        System.out.println("\n - Cuenta Asociada - \n");
                        break;
                    case 2:
                        System.out.println("Introduzca número de la cuenta a asociar:");
                        numero=Integer.parseInt(leer.readLine());
                        cuenta=(C_Cuenta)sesion.get(C_Cuenta.class, numero);

                        if(cuenta!=null)
                        {
                            sesion.beginTransaction();
                            cuenta.getClientes().add(cliente);
                            sesion.getTransaction().commit();
                            System.out.println("\n - Cuenta Asociada - \n");
                        } 
                        else
                            System.out.println("\n No se ha encontrado la cuenta \n");
                        break;
                }
            }catch(Exception e) {
                System.out.println("\n - Error en la Asociación - \n");
            }
        }while(op!=0);
        
    }
    
    
}
