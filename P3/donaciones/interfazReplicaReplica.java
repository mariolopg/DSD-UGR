import java.rmi.Remote;
import java.rmi.RemoteException;

public interface interfazReplicaReplica extends interfazClienteServidor{

    public void registrarRR(String userName, String password) throws RemoteException;

    public void donarRR(String userName, String password, int valor) throws RemoteException;

    public int donadoRR() throws RemoteException;

    public int getDonadoRR() throws RemoteException;

    public boolean existeUsuarioRR(String userName) throws RemoteException;
    
    public boolean comprobarCredencialesRR(String userName, String password) throws RemoteException;

    public int getNumUsuariosRR() throws RemoteException;

    public boolean getUsuarioHaDonadoRR(String userName, String password) throws RemoteException;

    public void setUsuarioDonacionRR(String userName, String password, int valor) throws RemoteException;
    
    public int getUsuarioDonacionesRR(String userName, String password) throws RemoteException;
    
    public int getUsuarioNumDonacionesRR(String userName, String password) throws RemoteException;
}
