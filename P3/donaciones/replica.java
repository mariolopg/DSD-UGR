import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Map;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class replica extends UnicastRemoteObject implements interfazReplicaReplica {
    String idReplica;
    private int totalDonadoLocal;
    private ArrayList<usuario> usuarios;
    private ArrayList<interfazReplicaReplica> replicas;

    public replica(String idReplica) throws RemoteException {
        this.idReplica = idReplica;
        totalDonadoLocal = 0;
        usuarios = new ArrayList<usuario>();
        replicas = new ArrayList<interfazReplicaReplica>();
    }

    // Operaciones cliente replica
    public int donadoCS() throws RemoteException {
        int donado = totalDonadoLocal;

        for(int i = 0; i < replicas.size(); i++)
            donado += replicas.get(i).donadoRR();

        return donado;
    }

    public boolean donarCS(String userName, String password, int valor) throws RemoteException {
        boolean donacionRealizada = false;
        interfazReplicaReplica replica = replicaUsuario(userName, password);
        
        if(valor > 0 && replica != null){ //* && está registrado
            System.out.println("Donación recibida de " + valor + "€");
            replica.donarRR(valor);
            donacionRealizada = true;
        } 
        else if(valor < 0) System.out.println("Intento de donación con saldo negativo en " + idReplica);
        else System.out.println("Usuario no registrado");

        return donacionRealizada;
    }

    public void registrarCS(String userName, String password) throws RemoteException{
        boolean registrado = existeUsuarioRR(userName);

        for(int i = 0; i < replicas.size() && !registrado; i++)
            registrado = replicas.get(i).existeUsuarioRR(userName);

        if(!registrado){
            int minSize = getNumUsuariosRR();
            int replicaIndex = -1;
    
            for(int i = 0; i < replicas.size() && !registrado; i++)
                if(replicas.get(i).getNumUsuariosRR() < minSize){
                    minSize = replicas.get(i).getNumUsuariosRR();
                    replicaIndex = i;
                }
            
            if(replicaIndex == -1)
                registrarRR(userName, password);
            else
                replicas.get(replicaIndex).registrarRR(userName, password);
        }
    }

    public boolean iniciarSesionCS(String userName, String password) throws RemoteException{
        boolean credencialesCorrectas = comprobarCredencialesRR(userName, password);

        for(int i = 0; i < replicas.size() && !credencialesCorrectas; i++)
            credencialesCorrectas = replicas.get(i).comprobarCredencialesRR(userName, password);

        return credencialesCorrectas;
    }

    
    // Operaciones replica replica

    public void registrarRR(String userName, String password) throws RemoteException{
        System.out.println("Registro realizado en " + idReplica);
        usuarios.add(new usuario(userName, password));
    }

    public void donarRR(int valor) throws RemoteException{
        System.out.println("Donacion realizada en " + idReplica);
        totalDonadoLocal += valor;
    }

    public int donadoRR() throws RemoteException{
        System.out.println("Total donado en " + idReplica);
        return totalDonadoLocal;
    }

    public int getNumUsuariosRR() throws RemoteException{
        return usuarios.size();
    }
    
    public boolean existeUsuarioRR(String userName) throws RemoteException{
        boolean registrado = false;
        for(int i = 0; i < usuarios.size() && !registrado; i++)
            if(compareStrings(userName, usuarios.get(i).getUserName()))
                registrado = true;
        
        return registrado;
    }

    public boolean comprobarCredencialesRR(String userName, String password){
        boolean credencialesCorrectas = false;

        for(int i = 0; i < usuarios.size() && !credencialesCorrectas; i++)
            if(compareStrings(userName, usuarios.get(i).getUserName()) && compareStrings(password, usuarios.get(i).getPassword()))
                credencialesCorrectas = true;

        return credencialesCorrectas;
    }
        
    // Operaciones clase Replica
    private boolean compareStrings(String str1, String str2){
        if(str1.compareTo(str2) == 0)
            return true;
        return false;
    }

    private interfazReplicaReplica replicaUsuario(String userName, String password) throws RemoteException{
        boolean existeUsuario = comprobarCredencialesRR(userName, password);
        if(existeUsuario)
            return this;

        for(int i = 0; i < replicas.size() && !existeUsuario; i++){
            existeUsuario = replicas.get(i).comprobarCredencialesRR(userName, password);
            if(existeUsuario)
                return replicas.get(i);
        }

        return null;
    }

    public void addReplica(String nombre){
        try {
            Registry mireg = LocateRegistry.getRegistry("127.0.0.1", 1099);
            interfazReplicaReplica replica;
            replica = (interfazReplicaReplica) mireg.lookup(nombre);
            replicas.add(replica);
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }
}