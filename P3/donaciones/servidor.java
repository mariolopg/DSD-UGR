import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;

public class servidor {
    public static void main(String[] args) {
        // Crea e instala el gestor de seguridad
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            // System.setProperty("java.rmi.server.hostname","192.168.1.107");
            Registry reg = LocateRegistry.createRegistry(1099);

            replica replica_1 = new replica("donaciones_rep1");
            replica replica_2 = new replica("donaciones_rep2");
            Naming.rebind("donaciones_rep1", replica_1);
            Naming.rebind("donaciones_rep2", replica_2);

            replica_1.addReplica("donaciones_rep2");
            replica_2.addReplica("donaciones_rep1");
            
            System.out.println("Servidor RemoteException | MalformedURLExceptiondor preparado");
        } catch (RemoteException | MalformedURLException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}