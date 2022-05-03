import java.net.MalformedURLException;
import java.rmi.registry.LocateRegistry;
import java.rmi.*;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class cliente {

    public static void main(String[] args) {
        String idReplica = "donaciones_rep1";

        if(args.length == 1){
            idReplica = args[0];
        }

        // Crea e instala el gestor de seguridad
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            // Crea el stub para el cliente especificando el nombre del servidor
            
            Registry mireg = LocateRegistry.getRegistry("127.0.0.1", 1099);
            interfazClienteServidor donaciones = (interfazClienteServidor) mireg.lookup(idReplica);

            System.out.println("Conectado a réplica con id: " + idReplica);
            
            char operacion = ' ';
            int donacion = 0;
            boolean sesionIniciada = false;
            String userName = "";
            String password = "";
            Scanner sc = new Scanner(System.in);

            while(true){
                System.out.println("\n*******************************************");
                System.out.println("*          Seleccionar operación          *");
                System.out.println("*******************************************");
                System.out.println("*            i -> iniciar sesion          *");
                System.out.println("*            r -> registro                *");
                System.out.println("*            d -> donar                   *");
                System.out.println("*            t -> total donaciones        *");
                System.out.println("*            m -> mis donaciones          *");
                System.out.println("*            n -> mi numero donaciones    *");
                System.out.println("*            c -> cerrar sesion           *");
                System.out.println("*            s -> salir                   *");
                System.out.println("*******************************************");
                
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
                            
                            sesionIniciada = donaciones.iniciarSesionCS(userName, password);
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

                        
                        donaciones.registrarCS(userName, password);
                        sesionIniciada = donaciones.iniciarSesionCS(userName, password);

                        if(sesionIniciada)
                            System.out.println("\nSesión iniciada correctamente");  

                        break;
                    case 't':
                        System.out.println("\nOperacion seleccionada -> total donaciones");
                        System.out.println("Total donado: " + donaciones.donadoCS(userName, password) + "€");
                        break;

                    case 'm':
                        System.out.println("\nHas donado " + donaciones.misDonacionesCS(userName, password) + "€");
                        break;

                    case 'n':
                        System.out.println("\nHas donado " + donaciones.numDonacionesCS(userName, password) + " veces");
                        break;

                    case 'd':
                        System.out.println("\nOperacion seleccionada -> donar");
                        System.out.print("Cantidad a donar: ");
                        donacion = sc.nextInt();
                        if(donaciones.donarCS(userName, password, donacion))
                            System.out.println("Donación de " + donacion + "€ realizada correctamente");
                        else
                            if(donacion < 0)
                                System.out.println("\nERROR -> intento de donación negativa");
                            else
                                System.out.println("\nERROR -> registro necesario para donar");
                        break;

                    case 'c':
                        sesionIniciada = false;
                        userName = "";
                        password = "";
                        System.out.println("\nSesión cerrada correctamente");
                        break;
                    default:
                        System.out.println("\nERROR -> carácter inválido detectado");
                        break;
                
                }
                System.out.println(" ");
            }
              
        } catch (NotBoundException | RemoteException e) {
            System.err.println("Exception del sistema: " + e);
        }
        System.exit(0);
    }
}