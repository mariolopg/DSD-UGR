public class usuario {
    private String userName, password;
    private int numDonaciones;
    private int totalDonado;

    public usuario(String userName, String password){
        this.userName = userName;
        this.password = password;
        this.totalDonado = 0;
        this.numDonaciones = 0;
    }

    public void addDonacion(int valor){
        totalDonado += valor;
        numDonaciones++;
    }

    public String getUserName(){
        return userName;
    }

    public String getPassword(){
        return password;
    }

    public int getNumDonaciones(){
        return numDonaciones;
    }

    public int getTotalDonado(){
        return totalDonado;
    }

    public boolean haDonado(){
        return totalDonado > 0;
    }
}
