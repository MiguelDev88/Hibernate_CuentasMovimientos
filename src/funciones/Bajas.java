package funciones;
import POJOS.*;
import cuentacorrientemiguel.HibernateUtil;
import java.io.BufferedReader;
import java.io.IOException;
import org.hibernate.Session;


// @author Miguel

public class Bajas {
    
    
    public static void bajas (BufferedReader leer) throws IOException {
        
        byte op=1;

        
        while(op!=0){
            op=Menu.menuBajas(leer);
            switch(op){
                case 1:
                    bajaCliente(leer);
                    break;
                case 2:
                    bajaCuenta(leer);
                    break;
            }
        }
    }
    
    public static void bajaCliente (BufferedReader leer) throws IOException {
        
        C_Cliente cliente;
        Session sesion;
        String dni;
        
        
        sesion=HibernateUtil.getSession();
        
        try{
            System.out.println("Introduzca el dni del cliente a eliminar");
            dni=leer.readLine();
            
            cliente=(C_Cliente)sesion.get(C_Cliente.class, dni);
            
            if(cliente!=null){

                if(Menu.menuConfirmar(leer)==1)
                {
                    sesion.beginTransaction();
                    sesion.delete(cliente);
                    sesion.getTransaction().commit();
                    System.out.println("\n Cliente eliminado \n");
                }
            }else
                System.out.println("\n Cliente no encontrado \n");

            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        sesion.close();
        
    }
    
    public static void bajaCuenta (BufferedReader leer) throws IOException {
        
        Session sesion;
        C_Cuenta cuenta;
        int numero;
        C_Cliente cliente;
        String dni;
        
        sesion=HibernateUtil.getSession();

        try{
            System.out.println("Introduzca el número de la cuenta a eliminar");
            numero=Integer.parseInt(leer.readLine());
            
            System.out.println("Introduzca dni de un titular de la cuenta");
            dni=leer.readLine();

            cuenta=(C_Cuenta)sesion.get(C_Cuenta.class, numero);
            cliente=(C_Cliente)sesion.get(C_Cliente.class, dni);
            
            if(cuenta!=null && cuenta.getClientes().contains(cliente)){

                if(Menu.menuConfirmar(leer)==1)
                {
                    sesion.beginTransaction();
                    sesion.delete(cuenta);
                    sesion.getTransaction().commit();
                    System.out.println("\n Cuenta eliminada \n");
                }

            }else
                System.out.println("\n Cuenta no encontrada \n");

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        sesion.close();
        
    }
    
}
