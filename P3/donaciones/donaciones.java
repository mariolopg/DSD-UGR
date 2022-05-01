import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class donaciones extends UnicastRemoteObject implements idonaciones {
    private int donaciones;
    private ArrayList<usuario> registros;

    public donaciones() throws RemoteException {
        donaciones = 0;
        registros = new ArrayList<usuario>();
    }

    public int donado() throws RemoteException {
        return donaciones;
    }

    public boolean donar(String userName, String password, int valor) throws RemoteException {
        boolean donacionRealizada = false;
        boolean usuarioRegistrado = iniciarSesion(userName, password);
        
        if(valor > 0 && usuarioRegistrado){ //* && está registrado
            System.out.println("Donación recibida de " + valor + "€");
            donaciones += valor;
            donacionRealizada = true;
        } 
        else if(valor < 0) System.out.println("Intento de donación con saldo negativo");
        else System.out.println("Usuario no registrado");
        return donacionRealizada;
    }

    public void registrar(String userName, String password){
        boolean registrado = existeUsuario(userName);
        if(!registrado)
            registros.add(new usuario(userName, password));
    }

    public boolean iniciarSesion(String userName, String password){
        boolean puedeIniciarSesion = false;

        for(int i = 0; i < registros.size(); i++)
            if(compareStrings(userName, registros.get(i).getUserName()) && compareStrings(password, registros.get(i).getPassword()))
                puedeIniciarSesion = true;

        return puedeIniciarSesion;
    }

    private boolean existeUsuario(String userName){
        boolean registrado = false;
        for(int i = 0; i < registros.size() && !registrado; i++)
            if(compareStrings(userName, registros.get(i).getUserName()))
                registrado = true;

        return registrado;
    }

    private boolean compareStrings(String str1, String str2){
        if(str1.compareTo(str2) == 0)
            return true;
        return false;
    }
}