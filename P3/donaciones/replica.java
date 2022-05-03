import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.net.MalformedURLException;
import java.util.ArrayList;
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
    public String donadoCS(String userName, String password) throws RemoteException {
        String donado = "????";

        interfazReplicaReplica replica = replicaUsuario(userName, password);
        if(replica != null){
            if(replica.getUsuarioHaDonadoRR(userName, password)){
                int donadoInt = replica.donadoRR();
                donado = Integer.toString(donadoInt);
            }
        }

        return donado;
    }

    public boolean donarCS(String userName, String password, int valor) throws RemoteException {
        boolean donacionRealizada = false;
        interfazReplicaReplica replica = replicaUsuario(userName, password);
        
        if(valor > 0 && replica != null){
            System.out.println("Donación recibida de " + valor + "€");
            replica.donarRR(userName, password, valor);
            replica.setUsuarioDonacionRR(userName, password, valor);
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

    public int misDonacionesCS(String userName, String password) throws RemoteException{
        interfazReplicaReplica replica = replicaUsuario(userName, password);
        
        if(replica != null)
            return replica.getUsuarioDonacionesRR(userName, password);

        return 0;
    }

    public int numDonacionesCS(String userName, String password) throws RemoteException{
        interfazReplicaReplica replica = replicaUsuario(userName, password);
        
        if(replica != null)
            return replica.getUsuarioNumDonacionesRR(userName, password);

        return 0;
    }

    
    // Operaciones replica replica

    public void registrarRR(String userName, String password) throws RemoteException{
        System.out.println("Registro realizado en " + idReplica);
        usuarios.add(new usuario(userName, password));
    }

    public void donarRR(String userName, String password, int valor) throws RemoteException{
        System.out.println("Donacion realizada en " + idReplica);
        totalDonadoLocal += valor;
    }

    public int donadoRR() throws RemoteException{
        System.out.println("Solicitud total donado en " + idReplica);
        int totalDonado = totalDonadoLocal;
        for(int i = 0; i < replicas.size(); i++)
            totalDonado += replicas.get(i).getDonadoRR();
        return totalDonado;
    }

    public int getDonadoRR() throws RemoteException{
        return totalDonadoLocal;
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

    public int getNumUsuariosRR() throws RemoteException{
        return usuarios.size();
    }

    public boolean getUsuarioHaDonadoRR(String userName, String password) throws RemoteException{
        return getUsuario(userName, password).haDonado();
    }

    public void setUsuarioDonacionRR(String userName, String password, int valor) throws RemoteException{
        getUsuario(userName, password).addDonacion(valor);
    }
    
    public int getUsuarioDonacionesRR(String userName, String password) throws RemoteException{
        return getUsuario(userName, password).getTotalDonado();
    }
    
    public int getUsuarioNumDonacionesRR(String userName, String password) throws RemoteException{
        return getUsuario(userName, password).getNumDonaciones();
    }
 
    // Operaciones clase Replica
    private boolean compareStrings(String str1, String str2){
        if(str1.compareTo(str2) == 0)
            return true;
        return false;
    }

    private usuario getUsuario(String userName, String password){
        for(int i = 0; i < usuarios.size(); i++)
            if(compareStrings(userName, usuarios.get(i).getUserName()) && compareStrings(password, usuarios.get(i).getPassword()))
                return usuarios.get(i);

        return null;
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