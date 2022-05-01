import java.rmi.Remote;
import java.rmi.RemoteException;

public interface idonaciones extends Remote {
    int donado() throws RemoteException;

    boolean donar(String userName, String password, int valor) throws RemoteException;

    public void registrar(String userName, String password) throws RemoteException;
    
    public boolean iniciarSesion(String userName, String password) throws RemoteException;
    
}