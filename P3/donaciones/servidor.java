import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import java.util.ArrayList;

public class servidor {
    public static void main(String[] args) {
        int numReplicas = 2;
        if(args.length == 1 && Integer.parseInt(args[0]) > 0)
            numReplicas = Integer.parseInt(args[0]);
        // Crea e instala el gestor de seguridad
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            // System.setProperty("java.rmi.server.hostname","192.168.1.107");
            Registry reg = LocateRegistry.createRegistry(1099);
            ArrayList<replica> replicas = new ArrayList<replica>();
            ArrayList<String> idReplicas = new ArrayList<String>();
            

            for(int i = 0; i < numReplicas; i++){
                idReplicas.add("donaciones_rep" + (i+1));
                replica replica = new replica(idReplicas.get(i));
                Naming.rebind(idReplicas.get(i), replica);
                replicas.add(replica);
            }

            for(int i = 0; i < replicas.size(); i++)
                for(int j = 0; j < replicas.size(); j++)
                    if(i != j)
                        replicas.get(i).addReplica(idReplicas.get(j));
            
            System.out.println("Servidor RemoteException | MalformedURLExceptiondor preparado");
        } catch (RemoteException | MalformedURLException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}