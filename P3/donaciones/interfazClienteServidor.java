import java.rmi.Remote;
import java.rmi.RemoteException;

public interface interfazClienteServidor extends Remote {
    public String donadoCS(String userName, String password) throws RemoteException;
    
    public boolean donarCS(String userName, String password, int valor) throws RemoteException;
    
    public void registrarCS(String userName, String password) throws RemoteException;
    
    public boolean iniciarSesionCS(String userName, String password) throws RemoteException;
    
    public int misDonacionesCS(String userName, String password) throws RemoteException;

    public int numDonacionesCS(String userName, String password) throws RemoteException;
}