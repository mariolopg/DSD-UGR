import java.rmi.Remote;
import java.rmi.RemoteException;

public interface idonaciones extends Remote {
    int donado() throws RemoteException;

    boolean donar(Integer id, int valor) throws RemoteException;

    public Integer registrar(Integer id) throws RemoteException;
}