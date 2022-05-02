import java.rmi.Remote;
import java.rmi.RemoteException;

public interface interfazClienteServidor extends Remote {
    public int donadoCS() throws RemoteException;

    public boolean donarCS(String userName, String password, int valor) throws RemoteException;

    public void registrarCS(String userName, String password) throws RemoteException;
    
    public boolean iniciarSesionCS(String userName, String password) throws RemoteException;
    
}