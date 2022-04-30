import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class donaciones extends UnicastRemoteObject implements idonaciones {
    private int donaciones;
    private ArrayList<Integer> registros;

    public donaciones() throws RemoteException {
        donaciones = 0;
        registros = new ArrayList<Integer>();
    }

    public int donado() throws RemoteException {
        return donaciones;
    }

    public boolean donar(Integer id, int valor) throws RemoteException {
        boolean donacionRealizada = false;
        boolean usuarioRegistrado = registros.contains(id);
        if(valor > 0 && usuarioRegistrado){ //* && está registrado
            System.out.println("Donación recibida de " + valor + "€");
            donaciones += valor;
            donacionRealizada = true;
        } 
        else if(valor < 0) System.out.println("Intento de donación con saldo negativo");
        else System.out.println("Usuario no registrado");
        return donacionRealizada;
    }

    public Integer registrar(Integer id){
        boolean registrado = registros.contains(id);
        
        if(!registrado){
            id = registros.size();
            registros.add(id);
        }

        return id;
    }
}