package cuentacorrientemiguel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


// @author Miguel

public class CrearTablas {
    
    public static void crearTablas() {
        
        try{
            Connection conexion=DriverManager.getConnection("jdbc:mysql://localhost:3307/?user=root&password=usbw");
            Statement sentencia=conexion.createStatement();
            
            sentencia.execute("CREATE DATABASE IF NOT EXISTS CUENTA_CORRIENTE_MIGUEL");
            sentencia.execute("USE CUENTA_CORRIENTE_MIGUEL");
            
            sentencia.execute("CREATE TABLE IF NOT EXISTS clientes ("
                            + "dni CHAR(9) NOT NULL, "
                            + "nombre VARCHAR(20) NOT NULL, "
                            + "direccion VARCHAR(30) NOT NULL, "
                            + "PRIMARY KEY (dni))");
            
            sentencia.execute("CREATE TABLE IF NOT EXISTS cuentas ( "
                            + "numero INT NOT NULL, "
                            + "sucursal VARCHAR(20) NOT NULL, "
                            + "PRIMARY KEY (numero))");
            
            sentencia.execute("CREATE TABLE IF NOT EXISTS cuentaCorriente ("
                            + "numero INT NOT NULL, "
                           // + "operacion VARCHAR(20) NOT NULL,"
                            + "saldoActual INT NOT NULL, "
                            + "PRIMARY KEY (numero), "
                            + "FOREIGN KEY (numero) REFERENCES cuentas(numero) "
                            + "ON UPDATE CASCADE "
                            + "ON DELETE CASCADE) "
                            + "ENGINE INNODB");
            
            sentencia.execute("CREATE TABLE IF NOT EXISTS cuentaPlazo ("
                            + "numero INT NOT NULL, "
                            + "intereses INT NOT NULL, "
                            + "fechaVencimiento VARCHAR(10) NOT NULL, "
                            + "depositoPlazo FLOAT NOT NULL, "
                            + "PRIMARY KEY (numero), "
                            + "FOREIGN KEY (numero) REFERENCES cuentas(numero) "
                            + "ON UPDATE CASCADE "
                            + "ON DELETE CASCADE) "
                            + "ENGINE INNODB");
            
            sentencia.execute("CREATE TABLE IF NOT EXISTS cuentas_clientes ( "
                            + "cuenta INT NOT NULL, "
                            + "cliente CHAR(9) NOT NULL, "
                            + "PRIMARY KEY (cuenta,cliente), "
                            + "FOREIGN KEY (cuenta) REFERENCES cuentas(numero) "
                            + "ON UPDATE CASCADE "
                            + "ON DELETE CASCADE, "
                            + "FOREIGN KEY (cliente) REFERENCES clientes(dni) "
                            + "ON UPDATE CASCADE "
                            + "ON DELETE RESTRICT) "
                            + "ENGINE INNODB");
            
            sentencia.execute("CREATE TABLE IF NOT EXISTS movimientos ("
                    + "fecha DATE NOT NULL, "
                    + "hora TIME NOT NULL, "
                    + "cuenta INT NOT NULL, "
                    + "cliente CHAR(9) NOT NULL,"
                    + "operacion CHAR NOT NULL, "
                    + "importe FLOAT NOT NULL, "
                    + "PRIMARY KEY (fecha,hora,cuenta), "
                    + "FOREIGN KEY (cuenta) REFERENCES cuentas(numero) "
                    + "ON UPDATE CASCADE "
                    + "ON DELETE CASCADE, "
                    + "FOREIGN KEY (cliente) REFERENCES clientes(dni) "
                    + "ON UPDATE CASCADE "
                    + "ON DELETE CASCADE )"
                    + "ENGINE INNODB");
            
            System.out.println("\n - BASE DE DATOS LISTA - \n");
            
            conexion.close();
            
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("\n - ERROR DE CONEXIÓN CON LA BASE DE DATOS - \n");
            System.exit(0);
        }
    }

}
