import java.rmi.Remote;
import java.rmi.RemoteException;

public interface interfazReplicaReplica extends interfazClienteServidor{

    public void registrarRR(String userName, String password) throws RemoteException;

    public void donarRR(int valor) throws RemoteException;

    public int donadoRR() throws RemoteException;

    public boolean comprobarCredencialesRR(String userName, String password) throws RemoteException;

    public boolean existeUsuarioRR(String userName) throws RemoteException;

    public int getNumUsuariosRR() throws RemoteException;
}
