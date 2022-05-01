import java.net.MalformedURLException;
import java.rmi.registry.LocateRegistry;
import java.rmi.*;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class cliente {

    public static void main(String[] args) {

        // Crea e instala el gestor de seguridad
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            // Crea el stub para el cliente especificando el nombre del servidor
            Registry mireg = LocateRegistry.getRegistry("127.0.0.1", 1099);
            idonaciones donaciones = (idonaciones) mireg.lookup("donaciones");
            
            char operacion = ' ';
            int donacion = 0;
            boolean sesionIniciada = false;
            String userName = "";
            String password = "";
            Scanner sc = new Scanner(System.in);

            while(true){
                System.out.println("\n***************************************");
                System.out.println("*        Seleccionar operación        *");
                System.out.println("***************************************");
                System.out.println("*            i -> iniciar sesion      *");
                System.out.println("*            r -> registro            *");
                System.out.println("*            d -> donar               *");
                System.out.println("*            t -> total donaciones    *");
                System.out.println("*            s -> salir               *");
                System.out.println("***************************************");
                
                operacion = sc.next().charAt(0);

                if(operacion == 's'){
                    sc.close();
                    break;
                }

                switch (operacion) {
                    case 'i':
                        System.out.println("\nOperacion seleccionada -> iniciar sesion");
                        
                        if(!sesionIniciada){
                            System.out.print("\nNombre de usuario: ");
                            sc.nextLine();
                            userName = sc.nextLine();

                            System.out.print("");
                            System.out.print("\nContraseña: ");
                            password = sc.nextLine();
                            
                            sesionIniciada = donaciones.iniciarSesion(userName, password);
                        }
                        
                        if(sesionIniciada)
                            System.out.println("\nSesión iniciada correctamente");  
                        else
                            System.out.println("\nUsuario o contraseña incorrectos");  

                        
                        break;
                    case 'r':
                        System.out.println("\nOperacion seleccionada -> registro");
                        System.out.print("\nNombre de usuario: ");
                        sc.nextLine();
                        userName = sc.nextLine();

                        System.out.print("\nContraseña: ");
                        password = sc.nextLine();

                        
                        donaciones.registrar(userName, password);
                        sesionIniciada = donaciones.iniciarSesion(userName, password);

                        if(sesionIniciada)
                            System.out.println("\nSesión iniciada correctamente");  

                        break;
                    case 't':
                        System.out.println("\nOperacion seleccionada -> total donaciones");
                        System.out.println("Total donado: " + donaciones.donado() + "€");
                        break;

                    case 'd':
                        System.out.println("\nOperacion seleccionada -> donar");
                        System.out.print("Cantidad a donar: ");
                        donacion = sc.nextInt();
                        if(donaciones.donar(userName, password, donacion))
                            System.out.print("Donación de " + donacion + "€ realizada correctamente");
                        else
                            if(donacion < 0)
                                System.out.println("\nERROR -> intento de donación negativa");
                            else
                                System.out.println("\nERROR -> registro necesario para donar");
                        break;

                    default:
                        System.out.println("\nERROR -> carácter inválido detectado");
                        break;
                
                }
            }
              
        } catch (NotBoundException | RemoteException e) {
            System.err.println("Exception del sistema: " + e);
        }
        System.exit(0);
    }
}